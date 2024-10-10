package br.com.mizaeldouglas.harry_potter_app.service.api

import br.com.mizaeldouglas.harry_potter_app.model.Book
import br.com.mizaeldouglas.harry_potter_app.model.Character
import br.com.mizaeldouglas.harry_potter_app.model.House
import br.com.mizaeldouglas.harry_potter_app.model.Spell
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("characters")
    suspend fun getCharacters(): Response<List<Character>>

    @GET("houses")
    suspend fun getHouses(): Response<House>

    @GET("books")
    suspend fun getBooks(): Response<Book>

    @GET("spells")
    suspend fun getSpells(): Response<Spell>
}