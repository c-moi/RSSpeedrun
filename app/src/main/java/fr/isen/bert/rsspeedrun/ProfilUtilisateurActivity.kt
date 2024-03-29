package fr.isen.bert.rsspeedrun

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import fr.isen.bert.rsspeedrun.ui.theme.RSSpeedrunTheme
import fr.isen.bert.rsspeedrun.EditProfilUtilisateurActivity

class ProfilUtilisateurActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RSSpeedrunTheme {
                UserProfileContent(intent)
            }
        }
    }
}

@Composable
fun UserProfileContent(intent: Intent) {
    val username = intent.getStringExtra("username") ?: "Username"
    val pseudo = intent.getStringExtra("pseudo") ?: "Pseudo"
    val description = intent.getStringExtra("description") ?: "Description"
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        ProfileHeader(username = username, pseudo = pseudo, description = description, context = context)
        Spacer(modifier = Modifier.height(16.dp))
        PostsSection()
    }
}

@Composable
fun ProfileHeader(username: String, pseudo: String, description: String, context: Context) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Image(
                painter = painterResource(id = R.drawable.ic_action_name), // Replace with actual profile image
                contentDescription = "Profile Image",
                modifier = Modifier
                    .size(100.dp)
                    .clip(MaterialTheme.shapes.small)
                    .clickable { navigateToEditProfile(context) }
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = username,
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "@$pseudo",
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
            )
        }
        IconButton(onClick = { navigateToEditProfile(context) }) {
            Icon(Icons.Filled.Settings, contentDescription = "Settings")
        }
        IconButton(onClick = { performLogout(context) }) {
            Icon(Icons.Filled.ExitToApp, contentDescription = "Logout")
        }
    }
    Text(
        text = description,
        style = MaterialTheme.typography.bodyLarge,
        modifier = Modifier.padding(top = 8.dp, bottom = 16.dp)
    )
}

@Composable
fun PostsSection() {
    // Fetch or generate the user's posts here
    val posts = listOf("Post 1", "Post 2", "Post 3", "Post 4", "Post 5", "Post 6")

    LazyColumn(
        contentPadding = PaddingValues(8.dp)
    ) {
        items(posts) { post ->
            PostItem(post = post)
        }
    }
}

@Composable
fun PostItem(post: String) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .clickable { /* Handle post click */ },
    ) {
        Column {
            Image(
                painter = painterResource(id = R.drawable.ic_action_name), // Replace with actual post image
                contentDescription = "Post Image",
                modifier = Modifier
                    .height(150.dp)
                    .fillMaxWidth()
                    .clip(MaterialTheme.shapes.medium)
            )
            Text(
                text = post,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}

fun navigateToEditProfile(context: Context) {
    val intent = Intent(context, EditProfilUtilisateurActivity::class.java)
    context.startActivity(intent)
}

fun performLogout(context: Context) {
    (context as? ProfilUtilisateurActivity)?.finishAffinity()
}