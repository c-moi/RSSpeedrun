package fr.isen.bert.rsspeedrun.data.comment

import com.google.firebase.Firebase
import com.google.firebase.database.database
import fr.isen.bert.rsspeedrun.model.Comment
import java.util.Date


class ManageComment {

    private val database = Firebase.database
    private val commentRef = database.getReference("comments")

    fun createComment (
        content:String = "",
        idAnswered:String = "",
        idAuthor:String = ""
    ) {
        val currentDateTime = Date()
        val commentId = commentRef.push()

        val comment = commentId.key?.let {
            Comment(
                idAnswered = idAnswered,
                idAuthor = idAuthor,
                content = content,
                time = currentDateTime
            )
        }

        commentId.setValue(comment)
    }

    fun deleteComment(commentId:String) {
        commentRef.child(commentId).removeValue()
    }
}