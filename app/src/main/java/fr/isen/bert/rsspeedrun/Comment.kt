import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue


data class Comment(
    val id: String,
    val postId: String,
    val content: String,
    var isDeleted: Boolean = false,
    val responses: SnapshotStateList<Comment> = mutableStateListOf()
) {
    var likes by mutableStateOf(0)
    var liked by mutableStateOf(false)

    fun toggleLike() {
        if (liked) {
            likes--
        } else {
            likes++
        }
        liked = !liked
    }

    fun addResponse(response: Comment) {
        responses.add(response)
    }
}