package fr.isen.bert.rsspeedrun

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fr.isen.bert.rsspeedrun.EditProfileUserActivity
import fr.isen.bert.rsspeedrun.ui.theme.RSSpeedrunTheme

@Suppress("DEPRECATION")
class UserProfileActivity : ComponentActivity() {
    private var username by mutableStateOf("John Doe")
    private var bio by mutableStateOf("Lorem ipsum dolor sit amet") // Ajout de la variable bio

    private val EDIT_PROFILE_REQUEST_CODE = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RSSpeedrunTheme {
                UserProfileScreen(
                    username = username,
                    bio = bio, // Passer la bio à UserProfileScreen
                    onEditProfileClicked = { launchEditProfileActivity() }
                )
            }
        }
    }

    private fun launchEditProfileActivity() {
        val intent = Intent(this@UserProfileActivity, EditProfileUserActivity::class.java)
        startActivityForResult(intent, EDIT_PROFILE_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == EDIT_PROFILE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val newUsername = data?.getStringExtra("newUsername")
            val newBio = data?.getStringExtra("newBio") // Récupérer la nouvelle bio depuis l'intent de retour
            newUsername?.let {
                username = it
            }
            newBio?.let {
                bio = it // Mettre à jour la bio
            }
        }
    }
}

@Composable
fun UserProfileScreen(
    username: String,
    bio: String, // Ajouter la bio comme paramètre
    onEditProfileClicked: () -> Unit
) {


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 24.dp)
    ) {
        ProfileImageSection()

        Spacer(modifier = Modifier.height(24.dp))

        Text(text = "Hello,", style = MaterialTheme.typography.headlineMedium)
        Text(text = username, style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = bio, style = MaterialTheme.typography.bodyLarge) // Afficher la bio

        Spacer(modifier = Modifier.height(24.dp))

        Button(onClick = onEditProfileClicked) {
            Text(text = "Edit Profile")
        }
    }
}

@Composable
fun ProfileImageSection() {
    val profileImage = painterResource(id = R.drawable.profilutilisateur)

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(120.dp)
            .padding(vertical = 16.dp)
    ) {
        Image(
            painter = profileImage,
            contentDescription = "Profile Image",
            modifier = Modifier
                .size(120.dp)
                .background(Color.Gray, shape = CircleShape),
            contentScale = ContentScale.Crop
        )
    }
}

@Preview(showBackground = true)
@Composable
fun UserProfilePreview() {
    val username = "John Doe" // Définir une valeur pour username ici
    val bio = "Lorem ipsum dolor sit amet, consectetur adipiscing elit." // Définir une valeur pour bio ici
    RSSpeedrunTheme {
        UserProfileScreen(username = username, bio = bio) {}
    }
}