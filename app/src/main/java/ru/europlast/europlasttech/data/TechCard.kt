package ru.europlast.europlasttech.data

import com.google.gson.annotations.SerializedName

data class TechCard(
    @SerializedName("Сборочный комплекс")
    val telerobot: List<String>? = null,

    @SerializedName("Прессформа")
    val pressform: List<String>? = null,

    @SerializedName("ТПА")
    val tpa: List<String>? = null,

    @SerializedName("GreenBox")
    val greenBox: List<String>? = null,

    @SerializedName("ОТК")
    val otk: List<String>? = null,

)
