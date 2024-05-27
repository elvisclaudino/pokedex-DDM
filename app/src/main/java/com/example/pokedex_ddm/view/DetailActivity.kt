package com.example.pokedex_ddm.view

import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.pokedex_ddm.R
import com.example.pokedex_ddm.databinding.ActivityDetailBinding
import com.example.pokedex_ddm.domain.Pokemon
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import java.io.File
import java.io.FileOutputStream

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private var pokemon: Pokemon? = null

    private val REQUEST_CODE_PERMISSION = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        overridePendingTransition(R.anim.slide_up, 0)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_back)

        pokemon = intent.getSerializableExtra("POKEMON_DETAIL") as? Pokemon
        pokemon?.let { loadPokemonDetails(it) }
    }

    private fun loadPokemonDetails(pokemon: Pokemon) {
        with(binding) {
            Glide.with(this@DetailActivity).load(pokemon.imageUrl).into(ivDetails)
            tvNumberDetails.text = "N° ${pokemon.formattedNumber}"
            tvNameDetails.text = pokemon.formattedName
            tvType1Detail.text = pokemon.types[0].name.capitalize()

            if (pokemon.types.size > 1) {
                tvType2Detail.visibility = View.VISIBLE
                tvType2Detail.text = pokemon.types[1].name.capitalize()
            } else {
                tvType2Detail.visibility = View.GONE
            }

            btnSaveImage.setOnClickListener {
                saveImageToLocal(pokemon)
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        overridePendingTransition(0, R.anim.slide_down)
        return true
    }

    private fun saveImageToLocal(pokemon: Pokemon) {
        if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            performSaveImage(pokemon)
        } else {
            requestPermissions(arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE), REQUEST_CODE_PERMISSION)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                performSaveImage(pokemon!!)
            } else {
                Toast.makeText(this, "Permissão de armazenamento negada", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun performSaveImage(pokemon: Pokemon) {
        val url = pokemon.imageUrl
        val target = File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "pokemon_${pokemon.formattedName}.jpg")

        Picasso.get().load(url).into(object : Target {
            override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
                bitmap?.let {
                    try {
                        FileOutputStream(target).use { out ->
                            it.compress(Bitmap.CompressFormat.JPEG, 100, out)
                        }
                        Toast.makeText(this@DetailActivity, "Imagem salva com sucesso", Toast.LENGTH_SHORT).show()
                    } catch (e: Exception) {
                        e.printStackTrace()
                        Toast.makeText(this@DetailActivity, "Erro ao salvar a imagem", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {
                Toast.makeText(this@DetailActivity, "Erro ao carregar a imagem", Toast.LENGTH_SHORT).show()
            }

            override fun onPrepareLoad(placeHolderDrawable: Drawable?) {}
        })
    }
}
