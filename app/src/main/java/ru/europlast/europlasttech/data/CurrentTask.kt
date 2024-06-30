package ru.europlast.europlasttech.data

data class CurrentTask(
    val id: Int = 0,
    val task: String = "Пусто",
    val workers: List<String> = listOf("Пусто"),
    val status: String = "Пусто",
    val techCard: String = "Пусто",
    val pathToPhoto: String = "Пусто",
    val createTime: String = "Пусто",
    val author: String = "Пусто",
    val spentRepairParts: List<String> = listOf("Пусто"),
)



