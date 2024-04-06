package fr.isen.bert.rsspeedrun

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import java.io.Serializable

class UserProfileActivity : ComponentActivity() {
    private var userProfile by mutableStateOf(UserProfile(pseudo = "", description = "", email = "", profileImageUri = null))
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                ProfileScreen(userProfile) { updatedProfile ->
                    updateUserProfile(updatedProfile)
                }
            }
        }
    }

    fun updateUserProfile(updatedProfile: UserProfile) {
        userProfile = updatedProfile
    }
}

@Composable
fun TopAppBar(onBackClick: () -> Unit, onSettingsClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        IconButton(onClick = onBackClick) {
            Icon(Icons.Filled.ArrowBack, contentDescription = "Back", tint = Color(0xFF6FCB9A))
        }
        IconButton(onClick = onSettingsClick) {
            Icon(Icons.Filled.Settings, contentDescription = "Settings", tint = Color(0xFF6FCB9A))
        }
    }
}

@Composable
fun ProfileScreen(userProfile: UserProfile, onSave: (UserProfile) -> Unit) {
    val context = LocalContext.current
    var userProfileState by remember { mutableStateOf(userProfile) }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            userProfileState = userProfileState.copy(profileImageUri = it.toString())
        }
    }

    val startForResult = rememberLauncherForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val updatedUserProfile = result.data?.getSerializableExtra("UPDATED_PROFILE") as? UserProfile
            updatedUserProfile?.let {
                userProfileState = it
                onSave(it)
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF344347)) // Set the background color for the entire screen
    ) {
        TopAppBar(
            onBackClick = {}, // Appel de la fonction pour revenir en arrière
            onSettingsClick = {
                // Launch the settings activity here for result
                val intent = Intent(context, EditUserProfileActivity::class.java).apply {
                    putExtra("CURRENT_PROFILE", userProfileState)
                }
                startForResult.launch(intent)
            })

        ProfileContent(userProfileState) { updatedUserProfile ->
            userProfileState = updatedUserProfile
            onSave(updatedUserProfile) // Appeler onSave pour sauvegarder les modifications
        }
    }
}


@Composable
fun ProfileContent(userProfile: UserProfile, onUpdateUserProfile: (UserProfile) -> Unit) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(Color(0xFF344347)), // Main background color
        horizontalAlignment = Alignment.Start // Align content to the start (left) of the column
    ) {
        // Profile Image and Update Button
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start, // Align content to the start (left) of the row
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .padding(vertical = 16.dp)
            ) {
                Image(
                    painter = rememberImagePainter(
                        data = userProfile.profileImageUri ?: "",
                        builder = {
                            var contentScale = ContentScale.Crop
                        }
                    ),
                    contentDescription = "Profile Image",
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF9DADAC)) // Grey color for the image background
                        .clickable { /* Handle image click */ },
                    contentScale = ContentScale.Crop // Crop the image to fill the circle without distortion
                )
            }

            // Pseudo and Description
            Column(
                modifier = Modifier.padding(start = 16.dp)
            ) {
                userProfile.pseudo?.let { pseudo ->
                    Text(
                        fontWeight = FontWeight.Bold,
                        text = pseudo,
                        color = Color(0xFF6FCB9A),
                        style = MaterialTheme.typography.headlineSmall
                    )
                }

                userProfile.description?.let { description ->
                    Text(
                        fontWeight = FontWeight.Bold,
                        text = description,
                        color = Color(0xFF9DADAC),
                        style = MaterialTheme.typography.titleLarge
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(52.dp)) // Add space between profile info and publications

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly, // Align items evenly in the row
            verticalAlignment = Alignment.CenterVertically // Center align items vertically
        ) {
            PublicationsAndLikes(userProfile)
        }

        Spacer(modifier = Modifier.height(16.dp)) // Add space between publications and posts

        Divider(
            color = Color(0xFF6FCB9A),
            thickness = 2.dp,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(16.dp)) // Add space after the divider

        // Cards representing posts
        repeat(3) { index ->
            PostCard()
            Spacer(modifier = Modifier.height(16.dp)) // Add space between posts
        }
    }
}


@Composable
fun PostCard() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .padding(16.dp)
            .clip(MaterialTheme.shapes.medium)
            .background(Color.Gray)
            .border(2.dp, Color(0xFF6FCB9A), MaterialTheme.shapes.medium)
    ) {
        Text(
            text = "This is a post",
            color = Color.White,
            style = MaterialTheme.typography.labelLarge,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
fun PublicationsAndLikes(userProfile: UserProfile) {
    Text(
        text = buildAnnotatedString {
            withStyle(style = SpanStyle(color = Color(0xFF6FCB9A), fontWeight = FontWeight.Bold)) {
                append(userProfile.numPublications.toString())
            }
            append(" publications")
        },
        style = MaterialTheme.typography.headlineSmall,
        modifier = Modifier.padding(end = 16.dp),
        color = Color(0xFF9DADAC)
    )
    Text(
        text = buildAnnotatedString {
            withStyle(style = SpanStyle(color = Color(0xFF6FCB9A), fontWeight = FontWeight.Bold)) {
                append(userProfile.numLikes.toString())
            }
            append(" likes")
        },
        style = MaterialTheme.typography.headlineSmall,
        color = Color(0xFF9DADAC),
        modifier = Modifier.padding(end = 16.dp)
    )
}

data class UserProfile(
    val name: String = "",
    val description: String = "",
    val email: String = "",
    val pseudo: String? = null,
    var profileImageUri: String? = null,
    val numPublications: Int = 10, // Example number of publications
    val numLikes: Int = 100 // Example number of likes
) : Serializable
