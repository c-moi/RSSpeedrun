package fr.isen.bert.rsspeedrun.data.post

import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import fr.isen.bert.rsspeedrun.model.Post

class ManagePost {

    private val database = Firebase.database
    private val postRef = database.getReference("posts")

    fun addPost(
        title:String = "",
        picture:String = "",
        desc:String = "",
        date:String = "",
        userId:String = ""
    ) {
        val idPost = postRef.push()

        val post = idPost.key?.let {
            Post(
                id = it,
                title = title,
                picture = picture,
                description = desc,
                date = date,
                userId = userId,
                selected = false
            )
        }

        idPost.setValue(post)
    }

    fun delPost(postId:String) {
        postRef.child(postId).removeValue()
    }

    fun modifPost(postId:String, updatedPost: Post) {
        postRef.child(postId).setValue(updatedPost)
    }

    fun getPost(postId: String, onComplete: (Post?) -> Unit) {
        postRef.child(postId)
            .addListenerForSingleValueEvent(
                object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (snapshot.exists()) {
                            val post = Post(
                                postId,
                                snapshot.child("title").getValue(String::class.java) ?: "",
                                snapshot.child("picture").getValue(String::class.java) ?: "",
                                snapshot.child("description").getValue(String::class.java) ?: "",
                                snapshot.child("date").getValue(String::class.java) ?: "",
                                snapshot.child("userId").getValue(String::class.java) ?: "",
                                snapshot.child("selected").getValue(Boolean::class.java) ?: false
                            )
                            onComplete(post)
                        } else {
                            onComplete(null)
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        Log.e("getPost", "Erreur lors de la récupération du post: ${error.message}")
                        onComplete(null)
                    }
                })
    }

    fun listPostByOwner(userId:String, onComplete: (List<String>) -> Unit) {
        postRef.orderByChild("userId").equalTo(userId)
            .addListenerForSingleValueEvent(
                object:ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {

                        val postIds = mutableListOf<String>()

                        for (postSnapshot in snapshot.children) {
                            val postId = postSnapshot.key
                            postId?.let { postIds.add(it) }
                        }

                        onComplete(postIds)
                    }

                    override fun onCancelled(error: DatabaseError) {
                        Log.e("listUserPostsIds", "Erreur lors de la récupération des IDs des posts de l'utilisateur: ${error.message}")
                        onComplete(emptyList())
                    }
                }
            )
    }

    fun findPosts(postIds:List<String>, onComplete: (List<Post>) -> Unit) {

        var listposts = mutableListOf<Post>()

        for (postId in postIds) {
            postRef.child(postId)
                .addListenerForSingleValueEvent(
                    object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            val post = Post(
                                postId,
                                snapshot.child("title").getValue(String::class.java) ?: "",
                                snapshot.child("picture").getValue(String::class.java) ?: "",
                                snapshot.child("description").getValue(String::class.java) ?: "",
                                snapshot.child("date").getValue(String::class.java) ?: "",
                                snapshot.child("userId").getValue(String::class.java) ?: "",
                                snapshot.child("selected").getValue(Boolean::class.java) ?: false
                            )
                            post.let { listposts.add(it) }

                            if (listposts.size == postIds.size) {
                                onComplete(listposts)
                            }
                        }
                        override fun onCancelled(error: DatabaseError) {
                            Log.e("listUserPosts", "Erreur lors de la récupération des détails des posts: ${error.message}")
                            onComplete(emptyList())
                        }
                    }
                )
        }
    }
}