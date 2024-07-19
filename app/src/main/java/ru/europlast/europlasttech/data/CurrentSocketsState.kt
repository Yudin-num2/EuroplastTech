package ru.europlast.europlasttech.data

import com.google.gson.annotations.SerializedName

data class CurrentSocketsState(
    @SerializedName("Lid")
    val lid: Map<String, String>? = null,

    @SerializedName("Frame")
    val frame: Map<String, String>? = null,

    @SerializedName("Cutter")
    val cutter: Map<String, String>? = null,

    @SerializedName("Lid12")
    val lid12: Map<String, String>? = null,

    @SerializedName("Frame12")
    val frame12: Map<String, String>? = null,

    @SerializedName("Cutter12")
    val cutter12: Map<String, String>? = null,

    @SerializedName("Husky 11")
    val husky11: Map<String, String>? = null,

    @SerializedName("Husky 12")
    val husky12: Map<String, String>? = null,

    @SerializedName("Husky 16")
    val husky16: Map<String, String>? = null,

    @SerializedName("Husky 22")
    val husky22: Map<String, String>? = null,
    )
