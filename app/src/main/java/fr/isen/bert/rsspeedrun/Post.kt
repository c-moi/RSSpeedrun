package fr.isen.bert.rsspeedrun

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

data class Post(
    val id: String,
    val content: String,
    val initialLikes: Int = 0,
    val comments: MutableList<Comment> = mutableListOf()
) {
    var likes by mutableStateOf(initialLikes)

    fun addLike() {
        likes++
    }

    fun addComment(comment: Comment) {
        comments.add(comment)
    }
}
