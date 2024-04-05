package fr.isen.bert.rsspeedrun

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fr.isen.bert.rsspeedrun.ui.theme.RSSpeedrunTheme
import fr.isen.bert.rsspeedrun.ui.theme.secondary

data class PostContent(
    val Title: String,
    val Game: String,
    val Content: String
)

class EditPostActivity : ComponentActivity() {
    private var Title by mutableStateOf("")
    private var Game by mutableStateOf("")
    private var Content by mutableStateOf("")
    val postsList = listOf(
        PostContent("Titre 1", "Jeu 1", "Contenu 1"),
        PostContent("Titre 2", "Jeu 2", "Contenu 2"),
        PostContent("Titre 3", "Jeu 3", "Contenu 3")
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RSSpeedrunTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                )

                {
                    Header()
                    DisplayPosts(postsList)
                    ProfileButton()
                    BackButton()
                    EditPostContent(
                        Title = Title,
                        Game = Game,
                        Content = Content,
                        onTitleChange = { Title = it },
                        onGameChange = { Game = it },
                        onContentChange = { Content = it },
                        onSaveChanges = { saveChanges() }
                    )


                }
            }
        }
    }

    private fun saveChanges() {
        // Save changes logic
        val PostContent = PostContent(Title, Game, Content)
        val resultIntent = Intent().apply {
            putExtra("Title", Title)
            putExtra("Game", Game)
            putExtra("Content", Content)
        }
        setResult(Activity.RESULT_OK, resultIntent)
        finish()
    }

    @Composable
    fun EditPostContent(
        Title: String,
        Game: String,
        Content: String,
        onTitleChange: (String) -> Unit,
        onGameChange: (String) -> Unit,
        onContentChange: (String) -> Unit,
        onSaveChanges: () -> Unit
    ) {
        var postsList by remember { mutableStateOf(mutableListOf<List<String>>()) }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 150.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "New Post",
                //style = MaterialTheme.typography.headline4,
                color = secondary
            )

            // Title TextField
            OutlinedTextField(
                value = Title,
                onValueChange = onTitleChange,
                label = { Text("Title") },
                modifier = Modifier.fillMaxWidth(),
                //keyboardOptions = androidx.compose.ui.text.input.KeyboardOptions(keyboardType = KeyboardType.Text),
                singleLine = true
            )

            // Game TextField
            OutlinedTextField(
                value = Game,
                onValueChange = onGameChange,
                label = { Text("Game") },
                modifier = Modifier.fillMaxWidth(),
                //keyboardOptions = androidx.compose.ui.text.input.KeyboardOptions(keyboardType = KeyboardType.Text),
                singleLine = true
            )

            // Content TextField
            OutlinedTextField(
                value = Content,
                onValueChange = onContentChange,
                label = { Text("Content") },
                modifier = Modifier.fillMaxWidth(),
                //keyboardOptions = androidx.compose.ui.text.input.KeyboardOptions(keyboardType = KeyboardType.Text),
                singleLine = true
            )

            // Button to Save Changes
            Button(
                onClick = {
                    // Create a unique post
                    val postUnique = listOf(Title, Game, Content)
                    // Add the unique post to the postsList
                    postsList.add(postUnique)
                    // Clear the fields after adding
                    onTitleChange("")
                    onGameChange("")
                    onContentChange("")

                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Post")
            }
        }
    }
}

@Composable
fun BackButton() {
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
                    //val intent = Intent(context, HomeActivity::class.java)
                    //launcher.launch(intent)
                },
            contentAlignment = Alignment.Center // Centrer le contenu dans le cercle
        ) {
            Text(
                text = "<",
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
fun textFieldColors(): TextFieldColors {
    return OutlinedTextFieldDefaults.colors(
        focusedBorderColor = secondary,
        unfocusedBorderColor = secondary,
        focusedLabelColor = Color.Green,
        unfocusedLabelColor = secondary,
    )
}

@Composable
fun DisplayPosts(postsList: List<PostContent>) {
    val posts by remember { mutableStateOf(postsList) }

    LazyColumn (
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(vertical = 500.dp, horizontal = 10.dp)
    ){
        items(posts) { post ->
            PostItem(post = post)
        }
    }
}

@Composable
fun PostItem(post: PostContent) {
    val textColor = secondary

    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        // Accédez aux propriétés de l'objet PostContent correctement
        Text(text = "Title: ${post.Title}", color = textColor)
        Text(text = "Game: ${post.Game}", color = textColor)
        Text(text = "Content: ${post.Content}", color = textColor)
    }
}