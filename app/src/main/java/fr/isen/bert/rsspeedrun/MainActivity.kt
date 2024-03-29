package fr.isen.bert.rsspeedrun

import Comment
import Post
import android.os.Bundle
import android.widget.Toast
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.outlined.ArrowForward
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Send
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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.ui.platform.LocalContext
import fr.isen.bert.rsspeedrun.ui.theme.customgreen
import java.util.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RSSpeedrunTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = Color(0xFF242424)) {
                    val updateCounter = remember { mutableStateOf(0) }
                    val posts = remember { mutableStateListOf<Post>() }
                    if (posts.isEmpty()) {
                        posts.add(Post(id = "1", content = "Ceci est un post de test"))
                    }
                    PostList(posts, updateCounter)
                }
            }
        }
    }
}

@Composable
fun PostList(posts: List<Post>, updateCounter: MutableState<Int>) {
    LazyColumn(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        items(posts) { post ->
            PostView(post = post, onLike = {
                post.toggleLike()
                updateCounter.value++
            }, onCommentAdded = { commentText ->
                post.addComment(Comment(id = UUID.randomUUID().toString(), postId = post.id, content = commentText))
                updateCounter.value++
            }, updateCounter = updateCounter)
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostView(
    post: Post,
    onLike: () -> Unit,
    onCommentAdded: (String) -> Unit,
    updateCounter: MutableState<Int>,
    modifier: Modifier = Modifier
) {
    var commentText by remember { mutableStateOf("") }
    var isCommenting by remember { mutableStateOf(false) }
    var showComments by remember { mutableStateOf(false) }
    val context = LocalContext.current


    Surface(
        modifier = modifier.padding(8.dp),
        shape = RoundedCornerShape(8.dp),
        color = Color(0xFF344347),
        border = BorderStroke(1.dp, Color(0xFF6FCB9A))
    ) {
        Column(modifier = modifier.padding(16.dp)) {
            Text(text = post.content, color = Color.White)
            Text(text = "Likes: ${post.likes}", color = Color.White)
            Row(modifier = modifier.padding(top = 8.dp)) {
                IconButton(onClick = {
                    post.toggleLike()
                    updateCounter.value = updateCounter.value xor 1
                }) {
                    LikeIcon(liked = post.liked)
                }
                Spacer(modifier = Modifier.width(8.dp))
                IconButton(onClick = { isCommenting = !isCommenting }) {
                    CommentIcon()
                }
            }
            if (isCommenting) {
                Box {
                    OutlinedTextField(
                        value = commentText,
                        onValueChange = { commentText = it },
                        label = { Text("Ajouter un commentaire", color = Color.White) },
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            cursorColor = Color.White,
                            focusedBorderColor = customgreen,
                            unfocusedBorderColor = Color.Gray,
                            unfocusedTextColor = Color.White,
                            focusedTextColor = Color.White,
                            focusedLabelColor = customgreen,
                            unfocusedLabelColor = Color.Gray
                        ),
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
                    text = if (showComments) "Cacher réponses (${post.comments.size})" else "Voir réponses (${post.comments.size})",
                    color = customgreen,
                    modifier = Modifier.clickable { showComments = !showComments }
                )
            }
            if (showComments) {
                post.comments.forEach { comment ->
                    CommentView(
                        comment = comment,
                        onLike = { comment.toggleLike() },
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
                            post.comments.remove(commentToDelete)
                            Toast.makeText(context, "Commentaire supprimé", Toast.LENGTH_SHORT).show()
                        },
                        updateCounter = updateCounter,
                        modifier = Modifier.padding(start = 16.dp)
                    )
                }
            }
        }
    }
}



@OptIn(ExperimentalMaterial3Api::class)
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
    val showResponses = remember { mutableStateOf(false) }
    val context = LocalContext.current

    Surface(
        modifier = modifier.padding(8.dp),
        color = Color(0xFF344347),
        shape = RoundedCornerShape(4.dp),
        border = BorderStroke(1.dp, Color(0xFF6FCB9A))
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Text(text = if (!comment.isDeleted) comment.content else "Ce commentaire a été supprimé", color = Color.White)
            if (!comment.isDeleted) {
                Text(text = "${comment.likes} likes", color = Color.White)
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(onClick = {
                        comment.toggleLike()
                        updateCounter.value++ // Idem, ajustez en fonction de vos besoins de mise à jour d'UI
                    }) {
                        LikeIcon(liked = comment.liked)
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    IconButton(onClick = { isReplying = !isReplying }) { ResponseIcon() }
                    Spacer(modifier = Modifier.width(8.dp))
                    IconButton(onClick = { onDelete(comment); updateCounter.value++ }) { DeleteIcon() }
                }
                if (isReplying) {
                    OutlinedTextField(
                        value = responseText,
                        onValueChange = { responseText = it },
                        label = { Text("Ajouter une réponse", color = Color.White) },
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            cursorColor = Color.White,
                            focusedBorderColor = customgreen,
                            unfocusedBorderColor = Color.Gray,
                            unfocusedTextColor = Color.White,
                            focusedTextColor = Color.White,
                            focusedLabelColor = customgreen,
                            unfocusedLabelColor = Color.Gray
                        ),
                        trailingIcon = {
                            IconButton(onClick = {
                                if (responseText.isNotBlank()) {
                                    onRespond(responseText)
                                    responseText = ""
                                    isReplying = false
                                    updateCounter.value++
                                }
                            }) { SendIcon() }
                        },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }


            if (comment.responses.isNotEmpty()) {
                Text(
                    text = if (showResponses.value) "Cacher réponses (${comment.responses.size})" else "Voir réponses (${comment.responses.size})",
                    modifier = Modifier.clickable { showResponses.value = !showResponses.value }.padding(vertical = 4.dp),
                    color = customgreen
                )
            }
            if (showResponses.value) {
                comment.responses.forEach { response ->
                    CommentView(
                        comment = response,
                        onLike = { response.toggleLike(); updateCounter.value++ },
                        onRespond = { newText ->
                            val newResponse = Comment(id = UUID.randomUUID().toString(), postId = comment.postId, content = newText, isDeleted = false)
                            response.addResponse(newResponse)
                            updateCounter.value++
                        },
                        onDelete = { responseToDelete ->
                            comment.responses.remove(responseToDelete)
                            Toast.makeText(context, "Réponse supprimé", Toast.LENGTH_SHORT).show()
                        },
                        updateCounter = updateCounter,
                        modifier = Modifier.padding(start = 16.dp)
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

        val posts = remember { mutableStateListOf(
            Post(
                id = "1",
                content = "Hello Compose",
                comments = mutableStateListOf(
                    Comment(
                        id = "comment1",
                        postId = "1",
                        content = "This is a test comment"
                    )
                )
            )
        )}

        PostList(posts = posts, updateCounter = updateCounter)
    }
}

@Composable
fun LikeIcon(liked: Boolean) {
    if (liked) {
        Icon(
            imageVector = Icons.Filled.Favorite,
            contentDescription = "Unlike",
            tint = Color.Red
        )
    } else {
        Icon(
            imageVector = Icons.Filled.FavoriteBorder,
            contentDescription = "Like",
            tint = Color.White
        )
    }
}

@Composable
fun CommentIcon() { Icon(imageVector = Icons.Filled.Add, contentDescription = "Comment", tint = Color.White) }

@Composable
fun SendIcon() { Icon(imageVector = Icons.Outlined.Send, contentDescription = "Send", tint = Color.White) }

@Composable
fun ResponseIcon() { Icon(imageVector = Icons.Outlined.ArrowForward, contentDescription = "Response", tint = Color.White) }

@Composable
fun DeleteIcon() { Icon(imageVector = Icons.Outlined.Delete, contentDescription = "Response", tint = Color.White) }