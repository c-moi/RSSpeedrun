package fr.isen.bert.rsspeedrun.homefeed

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.auth.FirebaseAuth
import fr.isen.bert.rsspeedrun.data.comment.ManageComment
import fr.isen.bert.rsspeedrun.data.post.ManagePost
import fr.isen.bert.rsspeedrun.data.user.CurrentUser
import fr.isen.bert.rsspeedrun.data.user.ManageUser
import fr.isen.bert.rsspeedrun.profile.UserProfileActivity
import fr.isen.bert.rsspeedrun.ui.theme.background
import fr.isen.bert.rsspeedrun.ui.theme.grey
import fr.isen.bert.rsspeedrun.ui.theme.secondary

class FeedActivity : ComponentActivity() {
    // Liste mutable pour stocker les posts
    private val postList = mutableStateListOf<List<String>>()
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
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = background,
            ) {
                Header()
                BackButton()
                ProfileButton(auth, currentUser)


                Column {
                    // Utiliser LazyColumn pour afficher les posts
                    Row(
                        modifier = Modifier
                            .height(150.dp)
                            .fillMaxWidth()
                    ) {
                        // Contenu de la Row
                    }
                    LazyColumn (
                        modifier = Modifier.weight(1f),
                        contentPadding = PaddingValues(vertical = 16.dp, horizontal = 10.dp)
                    ){
                        items(postList.reversed()) { post ->
                            PostItem(post = post)
                        }
                    }

                    EditPostContent(
                        onAddPost = { post ->
                            if (post.isNotEmpty()) {
                                postList.add(post)
                            }
                        }
                    )
                }
            }
        }
    }

}


@Composable
fun EditPostContent(
    onAddPost: (List<String>) -> Unit
) {
    var title by remember { mutableStateOf("") }
    var game by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }
    var titleError by remember { mutableStateOf(false) }
    var gameError by remember { mutableStateOf(false) }
    var contentError by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .padding(16.dp)
    ) {
        // TextField pour le titre
        OutlinedTextField(
            value = title,
            onValueChange = {
                title = it
                titleError = false // Enlever l'erreur lorsque le texte est modifié
            },
            label = { Text("Title") },
            modifier = Modifier.fillMaxWidth(),
            isError = titleError,
            colors = textFieldColors()
        )

        // Afficher le message d'erreur en rouge s'il y a une erreur
        if (titleError) {
            Text(
                text = "Title cannot be empty",
                color = Color.Red,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Start
            )
        }

        // TextField pour le jeu
        OutlinedTextField(
            value = game,
            onValueChange = {
                game = it
                gameError = false // Enlever l'erreur lorsque le texte est modifié
            },
            label = { Text("Game") },
            modifier = Modifier.fillMaxWidth(),
            isError = gameError,
            colors = textFieldColors()
        )

        // Afficher le message d'erreur en rouge s'il y a une erreur
        if (gameError) {
            Text(
                text = "Game cannot be empty",
                color = Color.Red,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Start
            )
        }

        // TextField pour le contenu
        OutlinedTextField(
            value = content,
            onValueChange = {
                content = it
                contentError = false // Enlever l'erreur lorsque le texte est modifié
            },
            label = { Text("Content") },
            modifier = Modifier.fillMaxWidth(),
            isError = contentError,
            colors = textFieldColors()
        )

        // Afficher le message d'erreur en rouge s'il y a une erreur
        if (contentError) {
            Text(
                text = "Content cannot be empty",
                color = Color.Red,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Start
            )
        }

        // Bouton pour ajouter le post
        Button(
            onClick = {
                // c'est ici que tu mets dans ta liste locale pour le moment
                val newPost = listOf(title, game, content)
                if (title.isNotBlank() && game.isNotBlank() && content.isNotBlank()) {
                    onAddPost(newPost)
                    title = ""
                    game = ""
                    content = ""
                }

                // c'est ici que tu récupères la liste depuis la bdd
                else {
                    // Afficher une erreur si l'un des champs est vide
                    if (title.isBlank()) {
                        // Afficher une erreur pour le champ du titre
                        titleError = true
                    }
                    if (game.isBlank()) {
                        // Afficher une erreur pour le champ du jeu
                        gameError = true
                    }
                    if (content.isBlank()) {
                        // Afficher une erreur pour le champ du contenu
                        contentError = true
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Add Post")
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
                    val intent = Intent(context, HomeActivity::class.java)
                    launcher.launch(intent)
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