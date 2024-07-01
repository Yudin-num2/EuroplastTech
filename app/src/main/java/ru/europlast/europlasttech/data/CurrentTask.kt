package ru.europlast.europlasttech.data

data class CurrentTask(
    val id: Int = 0,
    val task: String,
    val workers: List<String>,
    val status: String,
    val techcard: String?,
    val pathtophoto: String,
    val createtime: String,
    val author: String,
    val spentrepairparts: List<String>?,
    )



