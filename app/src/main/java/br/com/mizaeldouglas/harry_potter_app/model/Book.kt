package br.com.mizaeldouglas.harry_potter_app.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Book(
    val cover: String,
    val description: String,
    val index: Int,
    val number: Int,
    val originalTitle: String,
    val pages: Int,
    val releaseDate: String,
    val title: String
): Parcelable