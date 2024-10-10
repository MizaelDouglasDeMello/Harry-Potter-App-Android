package br.com.mizaeldouglas.harry_potter_app.model

data class Book(
    val cover: String,
    val description: String,
    val index: Int,
    val number: Int,
    val originalTitle: String,
    val pages: Int,
    val releaseDate: String,
    val title: String
)