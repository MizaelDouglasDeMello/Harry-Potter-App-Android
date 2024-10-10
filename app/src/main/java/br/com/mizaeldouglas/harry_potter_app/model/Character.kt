package br.com.mizaeldouglas.harry_potter_app.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Character(
    val birthdate: String,
    val children: List<String>,
    val fullName: String,
    val hogwartsHouse: String,
    val image: String,
    val index: Int,
    val interpretedBy: String,
    val nickname: String
) : Parcelable