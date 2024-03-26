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

    fun addLike() {
        likes++
    }

    // Cette fonction n'est peut-être plus nécessaire si vous manipulez directement la liste 'comments'
    fun addComment(comment: Comment) {
        comments.add(comment)
    }
}