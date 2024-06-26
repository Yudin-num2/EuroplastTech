package ru.europlast.europlasttech.data

data class User(
    val id: Int,
    val login: String,
    val password: String,
    val name: String,
    val surname: String,
    val room: String,
    val post: String,
)
