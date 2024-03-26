package fr.isen.bert.rsspeedrun

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fr.isen.bert.rsspeedrun.R
import fr.isen.bert.rsspeedrun.ui.theme.RSSpeedrunTheme

class EditProfilUtilisateurActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RSSpeedrunTheme {
                EditUserProfileScreen()
            }
        }
    }
}

@Composable
fun EditUserProfileScreen() {
    var description by remember { mutableStateOf("") }
    var birthDate by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var pseudo by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 24.dp)
    ) {
        Text("Modifier le profil", style = MaterialTheme.typography.headlineSmall)

        Spacer(modifier = Modifier.height(16.dp))

        // Profile image section
        ProfileImageSection()

        Spacer(modifier = Modifier.height(16.dp))

        // Profile form fields
        ProfileFormFields(
            description = description,
            onDescriptionChange = { description = it },
            birthDate = birthDate,
            onBirthDateChange = { birthDate = it },
            username = username,
            onUsernameChange = { username = it },
            pseudo = pseudo,
            onPseudoChange = { pseudo = it }
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Buttons for additional options
        ActionButtonsEditProfile(
            onSaveClick = {
                // Save logic here
            },
            onCancelClick = {
                // Cancel logic here
            }
        )
    }
}

@Composable
fun ProfileImageSection() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(120.dp)
            .padding(vertical = 16.dp)
            .clickable { /* Handle click event */ }
    ) {
        Image(
            painter = painterResource(id = R.drawable.profilutilisateur),
            contentDescription = "Profile Image",
            modifier = Modifier
                .size(120.dp)
                .background(Color.Gray),
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
fun ProfileFormFields(
    description: String,
    onDescriptionChange: (String) -> Unit,
    birthDate: String,
    onBirthDateChange: (String) -> Unit,
    username: String,
    onUsernameChange: (String) -> Unit,
    pseudo: String,
    onPseudoChange: (String) -> Unit,
) {
    Column {
        TextFieldWithLabel(label = "Description", value = description, onValueChange = onDescriptionChange)
        Spacer(modifier = Modifier.height(16.dp))
        TextFieldWithLabel(label = "Date de naissance", value = birthDate, onValueChange = onBirthDateChange)
        Spacer(modifier = Modifier.height(16.dp))
        TextFieldWithLabel(label = "Username", value = username, onValueChange = onUsernameChange)
        Spacer(modifier = Modifier.height(16.dp))
        TextFieldWithLabel(label = "Pseudo", value = pseudo, onValueChange = onPseudoChange)
    }
}

@Composable
fun ActionButtonsEditProfile(
    onSaveClick: () -> Unit,
    onCancelClick: () -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = onSaveClick,
            modifier = Modifier.padding(8.dp)
        ) {
            Text("Enregistrer les modifications")
        }
        Button(
            onClick = onCancelClick,
            modifier = Modifier.padding(8.dp)
        ) {
            Text("Annuler")
        }
    }
}

@Composable
fun TextFieldWithLabel(label: String, value: String, onValueChange: (String) -> Unit) {
    Column {
        Text(text = label, style = MaterialTheme.typography.bodyMedium)
        TextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun EditUserProfilePreview() {
    RSSpeedrunTheme {
        EditUserProfileScreen()
    }
}
