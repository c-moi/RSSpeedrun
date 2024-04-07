package fr.isen.bert.rsspeedrun.homefeed

import android.content.Intent
import androidx.compose.ui.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fr.isen.bert.rsspeedrun.ui.theme.RSSpeedrunTheme
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import fr.isen.bert.rsspeedrun.data.comment.ManageComment
import fr.isen.bert.rsspeedrun.data.post.ManagePost
import fr.isen.bert.rsspeedrun.data.user.CurrentUser
import fr.isen.bert.rsspeedrun.data.user.ManageUser
import fr.isen.bert.rsspeedrun.model.Post
import fr.isen.bert.rsspeedrun.profile.UserProfileActivity
import fr.isen.bert.rsspeedrun.ui.theme.background
import fr.isen.bert.rsspeedrun.ui.theme.grey
import fr.isen.bert.rsspeedrun.ui.theme.secondary

class HomeActivity : ComponentActivity() {

    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = FirebaseAuth.getInstance()

        var currentUser = auth.currentUser
        val user = CurrentUser()
        val chgUser = ManageUser()
        val post = ManagePost()
        val comm = ManageComment()

        val context = this

        setContent {
            val postDetails = remember { mutableStateOf<List<Post>>(emptyList()) }
            if (currentUser == null) {
                user.logInUser(auth, "clement.charabot@gmail.com", "cpalebon")

                auth = FirebaseAuth.getInstance()
                currentUser = auth.currentUser
            }
            Log.d("test", "${currentUser?.email}")

            LaunchedEffect(Unit) {
                chgUser.findUser("") { _, id ->
                    post.listPost(id) { ids ->
                        val details = mutableListOf<Post>()
                        for (postId in ids) {
                            post.getPost(postId) { post ->
                                if (post != null) {
                                    details.add(post)
                                }
                                postDetails.value = details
                            }
                        }
                    }
                }
            }

            RSSpeedrunTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Header()
                    ProfileButton(auth, currentUser)
                    Column{

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                        ){

                            PostButton()
                        }


                        Column {
                            DisplayPostList(postDetails)
                    }


                    }
                }
            }
        }
    }
}

@Composable
fun Header() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = background)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
                .background(color = Color.Black),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "RSSpeedrun",
                color = secondary,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .align(Alignment.TopStart)
            )
        }
    }
}

@Composable
fun ProfileButton(auth:FirebaseAuth, currentUser: FirebaseUser?) {
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 2.dp, vertical = 60.dp), // Ajoute des marges horizontales et verticales
        horizontalArrangement = Arrangement.Absolute.Left
    ) {
        Spacer(modifier = Modifier.width(16.dp)) // Ajoute un espace à gauche du bouton
        Box(
            modifier = Modifier
                .size(80.dp) // Taille fixe du bouton
                .background(color = secondary, shape = CircleShape)
                .clickable {
                    if (currentUser != null) {
                        Log.d("mail", "${currentUser.email}")
                        val intent = Intent(context, UserProfileActivity::class.java)
                        intent.putExtra("user", currentUser)
                        launcher.launch(intent)
                    } else {
                        //"faire le lien avec l'activité de log in de sony"
                        //val intent = Intent(context, UserProfileActivity::class.java)
                        //launcher.launch(intent)
                    }
                },
            contentAlignment = Alignment.Center // Centrer le contenu dans le cercle
        ) {
            Text(
                text = "P",
                color = Color.White,
                textAlign = TextAlign.Center,
                style = TextStyle(fontSize = 45.sp, fontWeight = FontWeight.Bold) // Ajuster la taille de la police
            )
        }
        Spacer(modifier = Modifier.width(16.dp)) // Ajoute un espace à droite du bouton
    }
}

@Composable
fun PostButton() {
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 90.dp, vertical = 60.dp), // Ajoute des marges horizontales
        horizontalArrangement = Arrangement.Absolute.Left
    ) {
        Spacer(modifier = Modifier.width(16.dp)) // Ajoute un espace à gauche du bouton
        Box(
            modifier = Modifier
                .size(80.dp) // Taille fixe du bouton
                .background(color = secondary, shape = CircleShape)
                .clickable {
                    val intent = Intent(context, FeedActivity::class.java)
                    launcher.launch(intent)
                },
            contentAlignment = Alignment.Center // Centrer le contenu dans le cercle
        ) {
            Text(
                text = "+",
                style = TextStyle(fontSize = 50.sp, fontWeight = FontWeight.Bold),
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .size(256.dp) // Taille du texte
                    .padding(horizontal = 30.dp, vertical = 2.dp),

                )
        }
        Spacer(modifier = Modifier.width(16.dp)) // Ajoute un espace à droite du bouton
    }
}


@Composable
fun DisplayPostList(postDetails: MutableState<List<Post>>) {
    val posts = postDetails.value

    LazyColumn(
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(posts) { post ->
            Log.d("Post", "Title: ${post.title}, Picture: ${post.picture}, " +
                    "Description: ${post.description}")
            PostItem(
                listOf(
                    post.title,
                    post.picture,
                    post.description
                )
            )

        }
    }
}

@Composable
fun PostItem(post: List<String>) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clip(shape = RoundedCornerShape(8.dp))
            .background(grey)
            .border(5.dp, secondary),
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Emplacement pour la photo de profil (à remplacer par votre code)
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .background(Color.Gray)
                ) {
                    // Placeholder pour la photo de profil
                }

                Column {
                    // Afficher le titre en gras
                    Text(
                        text = post[0],
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold, // Met le texte en gras
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                    // Afficher le jeu avec une taille de texte plus grande
                    Text(
                        text = post[1],
                        fontSize = 18.sp,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                }
            }

            // Afficher le contenu
            Text(
                text = post[2],
                modifier = Modifier.padding(bottom = 4.dp)
            )

            // Boutons "Like" et "Comment"
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Bouton "Like"
                Button(
                    onClick = {
                        // Action lorsque le bouton "Like" est cliqué
                    },
                    colors = ButtonDefaults.buttonColors(background)
                ) {
                    Text("Like")
                }

                // Bouton "Comment"
                Button(
                    onClick = {
                        // Action lorsque le bouton "Comment" est cliqué
                    },
                    colors = ButtonDefaults.buttonColors(Color.Gray)
                ) {
                    Text("Comment")
                }
            }
        }
    }
}



/// partie de tanguy encore non implémenté
/*
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
 */