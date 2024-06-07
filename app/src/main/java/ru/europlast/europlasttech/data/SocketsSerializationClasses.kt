package ru.europlast.europlasttech.data

import kotlinx.serialization.Serializable

@Serializable
data class ButtonColorData(
    val page: String,
    val buttonIndex: Int,
    val color: String
)

@Serializable
data class ButtonColorsList(
    val buttonColors: List<ButtonColorData>
)
