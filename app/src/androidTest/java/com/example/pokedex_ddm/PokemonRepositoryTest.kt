package com.example.pokedex_ddm

import com.example.pokedex_ddm.api.PokemonService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PokemonRepositoryTest {
    private lateinit var mockWebServer: MockWebServer
    private lateinit var apiService: PokemonService

    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
        mockWebServer.start()

        val retrofit = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        apiService = retrofit.create(PokemonService::class.java)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun testGetPokemon() {
        val mockResponse = MockResponse()
            .setResponseCode(200)
            .setBody("pokemon_response.json")
        mockWebServer.enqueue(mockResponse)

        runBlocking {
            val pokemon = withContext(Dispatchers.IO) {
                apiService.getPokemon(1)
            }

            assertEquals("pikachu", pokemon)
            assertEquals(25, pokemon)
        }
    }
}