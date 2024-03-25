package fr.isen.bert.rsspeedrun

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fr.isen.bert.rsspeedrun.ui.theme.RSSpeedrunTheme

class EditProfileUserActivity : ComponentActivity() {
    private var username by mutableStateOf("John Doe")
    private var bio by mutableStateOf("Lorem ipsum dolor sit amet")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RSSpeedrunTheme {
                EditProfileScreen(
                    username = username,
                    bio = bio,
                    onSaveClicked = { saveChangesAndReturn() }
                )
            }
        }
    }

    private fun saveChangesAndReturn() {
        val intent = Intent().apply {
            putExtra("newUsername", username)
            putExtra("newBio", bio)
        }
        setResult(Activity.RESULT_OK, intent)
        finish()
    }
}

@Composable
fun EditProfileScreen(
    username: String,
    bio: String,
    onSaveClicked: () -> Unit
) {
    var newUsername by remember { mutableStateOf(username) }
    var newBio by remember { mutableStateOf(bio) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = newUsername,
            onValueChange = { newUsername = it },
            label = { Text(text = "Username") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = newBio,
            onValueChange = { newBio = it },
            label = { Text(text = "Bio") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(onClick = onSaveClicked) {
            Text(text = "Save Changes")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EditProfilePreview() {
    RSSpeedrunTheme {
        EditProfileScreen(username = "John Doe", bio = "Lorem ipsum dolor sit amet") {}
    }
}
