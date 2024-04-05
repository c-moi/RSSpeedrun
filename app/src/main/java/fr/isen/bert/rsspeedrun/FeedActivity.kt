package fr.isen.bert.rsspeedrun

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fr.isen.bert.rsspeedrun.ui.theme.background

class FeedActivity : ComponentActivity() {
    // Liste mutable pour stocker les posts
    private val postList = mutableStateListOf<List<String>>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = background,
            ) {
                Column {
                    // Utiliser LazyColumn pour afficher les posts
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
            isError = titleError
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
            isError = gameError
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
            isError = contentError
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
                val newPost = listOf(title, game, content)
                if (title.isNotBlank() && game.isNotBlank() && content.isNotBlank()) {
                    onAddPost(newPost)
                    title = ""
                    game = ""
                    content = ""
                } else {
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
fun PostItem(post: List<String>) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .background(Color.Green)
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
                    }
                ) {
                    Text("Like")
                }

                // Bouton "Comment"
                Button(
                    onClick = {
                        // Action lorsque le bouton "Comment" est cliqué
                    }
                ) {
                    Text("Comment")
                }
            }
        }
    }
}

@Composable
fun DisplayPosts(postsList: List<List<String>>) {
    LazyColumn (
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(vertical = 16.dp, horizontal = 10.dp)
    ){
        items(postsList) { post ->
            PostItem(post = post)
        }
    }
}
