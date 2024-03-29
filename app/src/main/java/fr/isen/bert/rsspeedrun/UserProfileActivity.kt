package fr.isen.bert.rsspeedrun

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import fr.isen.bert.rsspeedrun.ui.theme.RSSpeedrunTheme

class UserProfileActivity : ComponentActivity() {

    private val username: MutableState<String> = mutableStateOf("Clement Charabot")
    private val pseudo: MutableState<String> = mutableStateOf("clement83c")
    private val description: MutableState<String> = mutableStateOf("Etudiant ISEN")
    private val dateOfBirth: MutableState<String> = mutableStateOf("17/02/2000")
    private val profileImageUri: MutableState<String> = mutableStateOf("") // Added profile image URI

    private val editProfileLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                result.data?.let { data ->
                    username.value = data.getStringExtra("username") ?: username.value
                    pseudo.value = data.getStringExtra("pseudo") ?: pseudo.value
                    description.value = data.getStringExtra("description") ?: description.value
                    dateOfBirth.value = data.getStringExtra("date_of_birth") ?: dateOfBirth.value
                    profileImageUri.value = data.getStringExtra("profile_image_uri") ?: profileImageUri.value // Update profile image URI
                }
            }
        }

    val numberOfPosts = 5
    val numberOfLikes = 10
    val posts = listOf(
        "Post 1: Contenu du post 1",
        "Post 2: Contenu du post 2",
        "Post 3: Contenu du post 3",
        "Post 4: Contenu du post 4",
        "Post 5: Contenu du post 5"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            RSSpeedrunTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color(0xFF344347) // Use the background_grey color
                ) {
                    UserProfileContent(
                        username = username.value,
                        pseudo = pseudo.value,
                        description = description.value,
                        dateOfBirth = dateOfBirth.value,
                        profileImageUri = profileImageUri.value,
                        numberOfPosts = numberOfPosts,
                        numberOfLikes = numberOfLikes,
                        posts = posts,
                        onSettingsClick = { navigateToEditProfile() },
                        onLogoutClick = { performLogout() }
                    )
                }
            }
        }
    }

    private fun performLogout() {
        TODO("Not yet implemented")
    }

    @Composable
    fun ProfilePicture() {
        Box(
            modifier = Modifier
                .size(120.dp) // Size of the profile picture
                .padding(8.dp) // Space from the edges of the box
                .clip(CircleShape) // Clip the image to a circle shape
                .background(Color(0xFF6FCB9A)) // Green circle background color
                .clickable { /* Handle click action */ },
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.profilutilisateur),
                contentDescription = "Profile picture",
                modifier = Modifier
                    .size(100.dp) // Slightly smaller size to create a border effect
                    .clip(CircleShape)
            )
        }
    }

    private fun navigateToEditProfile() {
        val intent = Intent(this, EditUserProfileActivity::class.java).apply {
            putExtra("username", username.value)
            putExtra("pseudo", pseudo.value)
            putExtra("description", description.value)
            putExtra("date_of_birth", dateOfBirth.value)
            putExtra("profile_image_uri", profileImageUri.value) // Pass profile image URI
        }
        editProfileLauncher.launch(intent)
    }

    @Composable
    fun UserProfileContent(
        username: String,
        pseudo: String,
        description: String,
        dateOfBirth: String,
        profileImageUri: String,
        numberOfPosts: Int,
        numberOfLikes: Int,
        posts: List<String>, // Liste des posts
        onSettingsClick: () -> Unit,
        onLogoutClick: () -> Unit
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 16.dp)
            ) {
                ProfilePicture() // Display the profile picture
                Spacer(modifier = Modifier.width(16.dp)) // Add spacing between profile picture and user info

                Column {
                    // ProfileItem(label = "Username:", value = username)
                    // Divider(color = Color(0xFF6FCB9A), thickness = 1.dp) // Green divider
                    ProfileItem(label="",value = pseudo)
                    // ProfileItem(label = "Description:", value = description)
                    // Divider(color = Color(0xFF6FCB9A), thickness = 1.dp) // Green divider
                    ProfileItem(label="",value = description)
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                IconButtonWithBackground(
                    icon = Icons.Default.Settings,
                    contentDescription = "Settings",
                    onIconClick = onSettingsClick
                )
                Spacer(modifier = Modifier.width(8.dp)) // Space between buttons
                IconButtonWithBackground(
                    icon = Icons.Default.ExitToApp,
                    contentDescription = "Logout",
                    onIconClick = onLogoutClick
                )
            }

            // Affichage du nombre de publications
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Nombre de publications: $numberOfPosts",
                    style = MaterialTheme.typography.labelLarge,
                    color = Color.White
                )
            }

            // Affichage du nombre de likes
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Nombre de likes: $numberOfLikes",
                    style = MaterialTheme.typography.labelLarge,
                    color = Color.White
                )
            }

            // Affichage des posts
            posts.forEach { post ->
                GreenRectangle(text = post)
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }




    @Composable
    fun IconButtonWithBackground(
        icon: ImageVector,
        contentDescription: String,
        onIconClick: () -> Unit,
        backgroundColor: Color = Color(0xFF6FCB9A), // Green background
        iconTint: Color = Color(0xFF9DADAC) // Grey icon
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .background(backgroundColor, shape = CircleShape)
                .padding(8.dp)
                .size(48.dp)
                .clickable(onClick = onIconClick)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = contentDescription,
                tint = iconTint
            )
        }
    }

    @Composable
    fun ProfileItem(label: String, value: String) {
        Column(modifier = Modifier.padding(vertical = 8.dp)) {
            Text(
                text = value,
                style = MaterialTheme.typography.headlineSmall, // Utiliser une taille de texte plus grande
                color = Color(0xFF6FCB9A)
            )
        }
    }


    @Composable
    fun GreenRectangle(text: String) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .height(100.dp) // Hauteur du rectangle
                .background(Color.Gray, shape = MaterialTheme.shapes.medium)
                .border(2.dp, Color(0xFF6FCB9A), shape = MaterialTheme.shapes.medium) // Green border
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.titleLarge,
                color = Color.White,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}
