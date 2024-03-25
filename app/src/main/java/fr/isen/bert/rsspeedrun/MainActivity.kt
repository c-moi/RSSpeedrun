package fr.isen.bert.rsspeedrun

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
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
import androidx.compose.ui.graphics.Color
import java.util.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RSSpeedrunTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val posts = remember { mutableStateListOf<Post>() }
                    // Ajoutez des données de test ici si nécessaire
                    if (posts.isEmpty()) {
                        posts.add(Post(id = "1", content = "Ceci est un post de test"))
                    }

                    PostList(posts)
                }
            }
        }
    }
}



@Composable
fun PostList(posts: MutableList<Post>, modifier: Modifier = Modifier) {
    Column(modifier = modifier.padding(16.dp)) {
        posts.forEach { post ->
            PostView(
                post = post,
                onLike = { post.likes++ }, // Cette ligne modifie l'état observé
                onCommentAdded = { commentText ->
                    post.comments.add(Comment(id = UUID.randomUUID().toString(), postId = post.id, content = commentText))
                }
            )
        }
    }
}

@Composable
fun PostView(
    post: Post,
    onLike: () -> Unit,
    onCommentAdded: (String) -> Unit,
    modifier: Modifier = Modifier
){
    // Utiliser un état local pour gérer le texte du commentaire
    var commentText by remember { mutableStateOf("") }

    Surface(
        modifier = modifier.padding(8.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(modifier = modifier.padding(16.dp)) {
            Text(text = post.content)
            Text(text = "Likes: ${post.likes}")
            Row(modifier = modifier.padding(top = 8.dp)) {
                Button(onClick = { onLike() }) {
                    Text("Like")
                }
                Spacer(modifier = Modifier.width(8.dp))
                Button(onClick = {
                    if (commentText.isNotBlank()) {
                        onCommentAdded(commentText)
                        commentText = ""
                    }
                }) {
                    Text("Comment")
                }
            }
            OutlinedTextField(
                value = commentText,
                onValueChange = { commentText = it },
                label = { Text("Add a comment") },
                modifier = Modifier.fillMaxWidth()
            )
            post.comments.forEach { comment ->
                CommentView(
                    comment = comment,
                    onLike = { /* Ajoutez la logique pour gérer le like ici. Par exemple: */ comment.addLike() },
                    onRespond = { responseText ->
                        // Ajoutez la logique pour ajouter une réponse au commentaire ici.
                        // Cela pourrait ressembler à ajouter un nouveau Comment à la liste des réponses du commentaire.
                        val newResponse = Comment(id = UUID.randomUUID().toString(), postId = post.id, content = responseText)
                        comment.addResponse(newResponse)
                    }
                )
            }
        }
    }
}

@Composable
fun CommentView(
    comment: Comment,
    onLike: () -> Unit,
    onRespond: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var responseText by remember { mutableStateOf("") }

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        color = Color.LightGray,
        shape = RoundedCornerShape(4.dp)
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Text(text = comment.content)
            Text(text = "Likes: ${comment.likes}")
            Row(modifier = Modifier.padding(top = 8.dp)) {
                Button(onClick = { onLike() }) {
                    Text("Like")
                }
                Spacer(modifier = Modifier.width(8.dp))
                Button(onClick = {
                    if (responseText.isNotBlank()) {
                        onRespond(responseText)
                        responseText = ""
                    }
                }) {
                    Text("Respond")
                }
            }
            OutlinedTextField(
                value = responseText,
                onValueChange = { responseText = it },
                label = { Text("Add a response") },
                modifier = Modifier.fillMaxWidth()
            )
            comment.responses.forEach { response ->
                // Affichage récursif des réponses comme des commentaires
                CommentView(comment = response, onLike = { response.addLike() }, onRespond = { newResponseText ->
                    response.addResponse(Comment(id = UUID.randomUUID().toString(), postId = response.postId, content = newResponseText))
                })
            }
        }
    }
}




@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    RSSpeedrunTheme {
        PostList(mutableListOf(Post("1", "Hello Compose")))
    }
}