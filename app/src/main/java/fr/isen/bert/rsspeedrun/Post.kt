import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue


data class Post(
    val id: String,
    val content: String,
    val initialLikes: Int = 0,
    val comments: SnapshotStateList<Comment> = mutableStateListOf()
) {
    var likes by mutableStateOf(initialLikes)
    var liked by mutableStateOf(false)

    fun toggleLike() {
        liked = !liked
        if (liked) {
            likes++
        } else {
            likes = maxOf(0, likes - 1) // Évite les nombres négatifs
        }
    }

    fun addComment(comment: Comment) {
        comments.add(comment)
    }
}
