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
                    //ArtistCardsList(Post = posts)

                    //Greeting2("zzzzzzz")

                    ProfileButton()
                    PostButton()

                    Column {
                        DisplayPostList(postDetails)
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
fun ProfileButton() {
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
                    val intent = Intent(context, ProfilActivity::class.java)
                    launcher.launch(intent)
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