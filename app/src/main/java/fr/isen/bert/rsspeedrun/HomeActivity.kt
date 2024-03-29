package fr.isen.bert.rsspeedrun
import android.content.Intent
import androidx.compose.ui.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fr.isen.bert.rsspeedrun.ui.theme.RSSpeedrunTheme
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import fr.isen.bert.rsspeedrun.ui.theme.background
import fr.isen.bert.rsspeedrun.ui.theme.secondary

data class Post(
    val user: String,
    val game: String,
    val content: String
)
val posts: List<Post> = listOf(
    Post(user = "Utilisateur1", game = "Jeu1", content = "Contenu1"),
    Post(user = "Utilisateur2", game = "Jeu2", content = "Contenu2"),
    // Ajoutez autant d'éléments de post que nécessaire
)

class HomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val posts: List<Post> = listOf(
            Post(user = "Utilisateur1", game = "Jeu1", content = "Contenu1"),
            Post(user = "Utilisateur2", game = "Jeu2", content = "Contenu2"),
            // Ajoutez autant d'éléments de post que nécessaire
        )
        setContent {
            RSSpeedrunTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Header();
                    //ArtistCardsList(Post = posts)

                    //Greeting2("zzzzzzz")

                    ProfileButton()
                    PostButton()
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
                    val intent = Intent(context, EditPostActivity::class.java)
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
fun ArtistCardModifiers(artist: Post) {
    val padding = 16.dp
    Column(
        modifier = Modifier
            .padding(padding)
            .fillMaxWidth()
    ) {

    }
}


@Composable
fun ArtistCardsList(Post: List<Post>) {
    LazyColumn {
        items(Post) { Post ->
            ArtistCardModifiers(Post)
        }
    }

}



