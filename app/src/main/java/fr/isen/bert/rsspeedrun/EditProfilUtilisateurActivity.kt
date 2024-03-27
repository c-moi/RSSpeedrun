package fr.isen.bert.rsspeedrun

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import fr.isen.bert.rsspeedrun.ui.theme.RSSpeedrunTheme

data class UserProfile(
    val username: String,
    val pseudo: String,
    val description: String,
    val imageUri: String
)

class EditProfilUtilisateurActivity : ComponentActivity() {
    private var username by mutableStateOf("")
    private var pseudo by mutableStateOf("")
    private var description by mutableStateOf("")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RSSpeedrunTheme {
                EditUserProfileContent(
                    username = username,
                    pseudo = pseudo,
                    description = description,
                    onUsernameChange = { username = it },
                    onPseudoChange = { pseudo = it },
                    onDescriptionChange = { description = it },
                    onSaveChanges = { saveChanges() }
                )
            }
        }
    }

    private fun saveChanges() {
        // Save changes logic
        val userProfile = UserProfile(username, pseudo, description, "")
        val resultIntent = Intent().apply {
            putExtra("username", username)
            putExtra("pseudo", pseudo)
            putExtra("description", description)
        }
        setResult(Activity.RESULT_OK, resultIntent)
        finish()
    }

    @Composable
    fun EditUserProfileContent(
        username: String,
        pseudo: String,
        description: String,
        onUsernameChange: (String) -> Unit,
        onPseudoChange: (String) -> Unit,
        onDescriptionChange: (String) -> Unit,
        onSaveChanges: () -> Unit
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Edit User Profile", style = MaterialTheme.typography.headlineSmall)
            // Username TextField
            OutlinedTextField(
                value = username,
                onValueChange = onUsernameChange,
                label = { Text("Username") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                singleLine = true
            )
            // Pseudo TextField
            OutlinedTextField(
                value = pseudo,
                onValueChange = onPseudoChange,
                label = { Text("Pseudo") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                singleLine = true
            )
            // Description TextField
            OutlinedTextField(
                value = description,
                onValueChange = onDescriptionChange,
                label = { Text("Description") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                singleLine = true
            )
            // Button to Save Changes
            Button(onClick = onSaveChanges, modifier = Modifier.fillMaxWidth()) {
                Text("Save Changes")
            }
        }
    }
}