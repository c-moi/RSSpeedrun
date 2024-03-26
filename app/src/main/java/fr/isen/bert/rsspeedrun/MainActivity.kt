package fr.isen.bert.rsspeedrun

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.ArrowForward
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Send
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fr.isen.bert.rsspeedrun.ui.theme.RSSpeedrunTheme
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import java.util.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RSSpeedrunTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    val updateCounter = remember { mutableStateOf(0) }
                    val posts = remember { mutableStateListOf<Post>() }
                    if (posts.isEmpty()) {
                        posts.add(Post(id = "1", content = "Ceci est un post de test"))
                    }
                    // Passez updateCounter comme paramètre à PostList
                    PostList(posts, updateCounter)
                }
            }
        }
    }
}


@Composable
fun LikeIcon() {
    Icon(imageVector = Icons.Filled.Favorite, contentDescription = "Like")
}

@Composable
fun CommentIcon() {
    Icon(imageVector = Icons.Filled.Add, contentDescription = "Comment")
}

@Composable
fun SendIcon() {
    Icon(imageVector = Icons.Outlined.Send, contentDescription = "Send")
}

@Composable
fun ResponseIcon() {
    Icon(imageVector = Icons.Outlined.ArrowForward, contentDescription = "Response")
}

@Composable
fun DeleteIcon() {
    Icon(imageVector = Icons.Outlined.Delete, contentDescription = "Response")
}

@Composable
fun PostList(posts: List<Post>, updateCounter: MutableState<Int>) {
    Column(modifier = Modifier.padding(16.dp)) {
        posts.forEach { post ->
            PostView(post, onLike = { post.likes++ },
                onCommentAdded = { commentText ->
                    post.comments.add(Comment(id = UUID.randomUUID().toString(), postId = post.id, content = commentText))
                }, updateCounter)
        }
    }
}

@Composable
fun PostView(
    post: Post,
    onLike: () -> Unit,
    onCommentAdded: (String) -> Unit,
    updateCounter: MutableState<Int>,
    modifier: Modifier = Modifier
) {
    // Utiliser un état local pour gérer le texte du commentaire
    var commentText by remember { mutableStateOf("") }
    var isCommenting by remember { mutableStateOf(false) } // État local pour gérer la visibilité du champ de commentaire
    var showComments by remember { mutableStateOf(false) } // État local pour gérer la visibilité des commentaires

    Surface(
        modifier = modifier.padding(8.dp),
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(1.dp, Color.Black) // Ajoute un contour rectangulaire autour du post de base
    ) {
        Column(modifier = modifier.padding(16.dp)) {
            Text(text = post.content)
            Text(text = "Likes: ${post.likes}")
            Row(modifier = modifier.padding(top = 8.dp)) {
                IconButton(onClick = { onLike() }) {
                    LikeIcon()
                }
                Spacer(modifier = Modifier.width(8.dp))
                IconButton(onClick = { isCommenting = !isCommenting }) {
                    CommentIcon()
                }
            }
            if (isCommenting) { // Affiche le champ de commentaire uniquement lorsque isCommenting est vrai
                Box {
                    OutlinedTextField(
                        value = commentText,
                        onValueChange = { commentText = it },
                        label = { Text("Add a comment") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    if (commentText.isNotBlank()) {
                        IconButton(
                            onClick = {
                                onCommentAdded(commentText)
                                commentText = ""
                            },
                            modifier = Modifier.align(Alignment.CenterEnd).padding(end = 8.dp)
                        ) {
                            SendIcon()
                        }
                    }
                }
            }
            if (post.comments.isNotEmpty()) {
                Text(
                    text = if (showComments) "Hide Responses (${post.comments.size})" else "Show Responses (${post.comments.size})",
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.clickable { showComments = !showComments }
                )
            }
            if (showComments) {
                post.comments.forEach { comment ->
                    CommentView(
                        comment = comment,
                        onLike = { comment.addLike() },
                        onRespond = { responseText ->
                            val newResponse = Comment(
                                id = UUID.randomUUID().toString(),
                                postId = comment.postId,
                                content = responseText,
                                isDeleted = false
                            )
                            comment.addResponse(newResponse)
                        },
                        onDelete = { commentToDelete ->
                            commentToDelete.isDeleted = true
                            updateCounter.value++  // Ceci force la recomposition via l'incrémentation
                        },
                        updateCounter = updateCounter,  // Assurez-vous de passer updateCounter ici
                        modifier = Modifier.padding(start = 16.dp)
                    )
                }
            }
        }
    }
}



@Composable
fun CommentView(
    comment: Comment,
    onLike: () -> Unit,
    onRespond: (String) -> Unit,
    onDelete: (Comment) -> Unit,
    updateCounter: MutableState<Int>,
    modifier: Modifier = Modifier
) {

    var responseText by remember { mutableStateOf("") }
    var isReplying by remember { mutableStateOf(false) }
    var showResponses by remember { mutableStateOf(false) } // Pour gérer l'affichage des réponses

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        color = if (!comment.isDeleted) Color.LightGray else Color(0xFFE0E0E0),
        shape = RoundedCornerShape(4.dp)
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            if (!comment.isDeleted) {
                Text(text = comment.content)
                Text(text = "Likes: ${comment.likes}")
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(onClick = { onLike() }) { LikeIcon() }
                    Spacer(modifier = Modifier.width(8.dp))
                    IconButton(onClick = { isReplying = !isReplying }) { ResponseIcon() }
                    Spacer(modifier = Modifier.width(8.dp))
                    IconButton(onClick = { onDelete(comment) }) { DeleteIcon() }
                }
                if (isReplying) {
                    OutlinedTextField(
                        value = responseText,
                        onValueChange = { responseText = it },
                        label = { Text("Add a response") },
                        singleLine = true,
                        trailingIcon = {
                            if (responseText.isNotBlank()) {
                                IconButton(onClick = {
                                    onRespond(responseText)
                                    responseText = ""
                                    isReplying = false
                                }) {
                                    SendIcon()
                                }
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            } else {
                Text("Ce commentaire a été supprimé")
            }

            if (comment.responses.isNotEmpty()) {
                Text(
                    text = if (showResponses) "Hide Responses (${comment.responses.size})" else "Show Responses (${comment.responses.size})",
                    modifier = Modifier.clickable { showResponses = !showResponses }.padding(vertical = 4.dp),
                    color = MaterialTheme.colorScheme.primary
                )
            }
            if (showResponses) {
                comment.responses.forEach { response ->
                    CommentView(
                        comment = response,
                        onLike = { response.addLike() },
                        onRespond = { newText ->
                            val newResponse = Comment(
                                id = UUID.randomUUID().toString(),
                                postId = comment.postId,
                                content = newText,
                                isDeleted = false,
                                initialLikes = 0
                            )
                            response.addResponse(newResponse)
                        },
                        onDelete = { commentToDelete ->
                            commentToDelete.isDeleted = true
                            updateCounter.value++
                        },
                        updateCounter = updateCounter,
                        modifier = Modifier.padding(start = 16.dp) // Décalage pour montrer l'imbrication
                    )
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    RSSpeedrunTheme {
        val updateCounter = remember { mutableStateOf(0) }

        PostList(
            posts = mutableListOf(
                Post(
                    id = "1",
                    content = "Hello Compose",
                    comments = mutableListOf(
                        Comment(
                            id = "comment1",
                            postId = "1",
                            content = "This is a test comment"
                        )
                    )
                )
            ),
            updateCounter = updateCounter
        )
    }
}
