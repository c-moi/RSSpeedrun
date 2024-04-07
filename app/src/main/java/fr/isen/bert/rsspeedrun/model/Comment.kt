package fr.isen.bert.rsspeedrun.model

import java.util.Date

data class Comment (
    val idAnswered: String,
    val idAuthor: String,
    val content: String,
    val time: Date
)