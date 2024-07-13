package ru.europlast.europlasttech.data

import kotlinx.serialization.json.Json
import java.util.UUID

data class CurrentTask(
    val id: UUID,
    val task: String,
    val workers: List<String>,
    val status: String,
    val techcard: String?,
    val pathtophoto: String,
    val createtime: String,
    val author: String,
    val spentrepairparts: List<String>?,
    val checklist: Json,

    )



