package fr.isen.bert.rsspeedrun

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

data class Comment(
    val id: String,
    val postId: String,
    val content: String,
    var isDeleted: Boolean = false,
    val initialLikes: Int = 0,
    val responses: MutableList<Comment> = mutableListOf()
) {
    var likes by mutableStateOf(initialLikes)

    fun addLike() {
        likes++
    }

    fun addResponse(response: Comment) {
        responses.add(response)
    }
}
