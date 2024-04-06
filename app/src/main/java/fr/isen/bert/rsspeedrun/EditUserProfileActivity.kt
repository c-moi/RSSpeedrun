package fr.isen.bert.rsspeedrun

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import java.io.Serializable
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color


class EditUserProfileActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val userProfile = intent.getSerializableExtra("CURRENT_PROFILE") as UserProfile
        setContent {
            MaterialTheme {
                ProfileSettingsScreen(
                    userProfile = userProfile,
                    onSave = { updatedProfile ->
                        setResult(Activity.RESULT_OK, Intent().apply {
                            putExtra("UPDATED_PROFILE", updatedProfile as Serializable)
                        })
                        finish() // Fermez l'activité une fois les modifications enregistrées
                    },
                    onBackClick = { onBackPressed() }, // Gérer le retour en arrière de l'activité
                    onLogoutClick = { // Fonction provisoire pour afficher un message
                        // Afficher un message d'utilisateur déconnecté
                        showMessage("Vous êtes déconnecté")
                    },
                    onDeleteAccountClick = { /* Action de suppression du compte */ }
                )
            }
        }
    }

    private fun showMessage(message: String) {
        Log.d("LogoutAction", message)
    }

}

@Composable
fun ProfileSettingsScreen(
    userProfile: UserProfile,
    onSave: (UserProfile) -> Unit,
    onBackClick: () -> Unit,
    onLogoutClick: () -> Unit,
    onDeleteAccountClick: () -> Unit
) {
    var userProfileState by remember { mutableStateOf(userProfile) }
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            userProfileState = userProfileState.copy(profileImageUri = it.toString())
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF344347)) // Modifier le background de l'application
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        PrivateTopAppBar(
            onBackClick = onBackClick,
            onLogoutClick = onLogoutClick
        )
        ProfileImage(
            imageUrl = userProfileState.profileImageUri ?: "",
            onClick = { imagePickerLauncher.launch("image/*") }
        )
        ProfileTextField("Name", userProfileState.name) { name ->
            userProfileState = userProfileState.copy(name = name)
        }

        ProfileTextField("Pseudo", userProfileState.pseudo ?: "") { pseudo ->
            userProfileState = userProfileState.copy(pseudo = pseudo)
        }

        ProfileTextField("Email", userProfileState.email ?: "") { email ->
            userProfileState = userProfileState.copy(email = email)
        }

        ProfileTextField("Description", userProfileState.description) { description ->
            userProfileState = userProfileState.copy(description = description)
        }

        Spacer(modifier = Modifier.height(16.dp)) // Ajouter de l'espace entre le formulaire et les boutons

        Button(
            onClick = {
                onSave(userProfileState)
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(Color(0xFF6FCB9A))
        ) {
            Text("Enregistrer les modifications")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onDeleteAccountClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(Color.Red)
        ) {
            Text("Supprimer un compte")
        }

        Spacer(modifier = Modifier.height(16.dp))

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ProfileTextField(label: String, value: String, onValueChange: (String) -> Unit) {
    Surface(
        color = Color(0xFF344347),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Text(
                text = label,
                color = Color.White,
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            TextField(
                value = value,
                onValueChange = onValueChange,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    Color(0xFF344347)
                ),
                textStyle = MaterialTheme.typography.titleLarge.copy(Color.White),
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun PrivateTopAppBar(onBackClick: () -> Unit, onLogoutClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onBackClick) {
            Icon(Icons.Filled.ArrowBack, contentDescription = "Back", tint = Color(0xFF6FCB9A))
        }
        Spacer(modifier = Modifier.width(16.dp)) // Ajouter un espace entre les boutons
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(Color.Red)
                .clickable(onClick = onLogoutClick),
            contentAlignment = Alignment.Center
        ) {
            Icon(Icons.Filled.ExitToApp, contentDescription = "Logout", tint = Color.White)
        }
    }
}




@Composable
fun ProfileImage(imageUrl: String, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(120.dp)
            .clip(CircleShape) // Rendre la forme de la Box circulaire
            .background(Color.White)
            .clickable(onClick = onClick), // Permet de cliquer pour changer l'image
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = rememberImagePainter(imageUrl),
            contentDescription = "Profile Picture",
            contentScale = ContentScale.Crop, // Utiliser ContentScale.Crop
            modifier = Modifier
                .fillMaxSize() // Remplir entièrement l'espace disponible
                .clip(CircleShape) // Rognée en cercle
        )
    }
}
