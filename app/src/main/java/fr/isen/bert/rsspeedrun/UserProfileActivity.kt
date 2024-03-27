package fr.isen.bert.rsspeedrun

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import fr.isen.bert.rsspeedrun.ui.theme.RSSpeedrunTheme

class UserProfileActivity : ComponentActivity() {

    private val username: MutableState<String> = mutableStateOf("Clement Charabot")
    private val pseudo: MutableState<String> = mutableStateOf("clement83c")
    private val description: MutableState<String> = mutableStateOf("Etudiant ISEN")
    private val dateOfBirth: MutableState<String> = mutableStateOf("17/02/2000")

    private val editProfileLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                result.data?.let { data ->
                    username.value = data.getStringExtra("username") ?: username.value
                    pseudo.value = data.getStringExtra("pseudo") ?: pseudo.value
                    description.value = data.getStringExtra("description") ?: description.value
                    dateOfBirth.value = data.getStringExtra("date_of_birth") ?: dateOfBirth.value
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            RSSpeedrunTheme {
                UserProfileContent(
                    username.value,
                    pseudo.value,
                    description.value,
                    dateOfBirth.value,
                    onSettingsClick = { navigateToEditProfile() },
                    onLogoutClick = { performLogout() }
                )
            }
        }
    }

    private fun navigateToEditProfile() {
        val intent = Intent(this, EditUserProfileActivity::class.java).apply {
            putExtra("username", username.value)
            putExtra("pseudo", pseudo.value)
            putExtra("description", description.value)
            putExtra("date_of_birth", dateOfBirth.value)
        }
        editProfileLauncher.launch(intent)
    }

    @Composable
    fun UserProfileContent(
        username: String,
        pseudo: String,
        description: String,
        dateOfBirth: String,
        onSettingsClick: () -> Unit,
        onLogoutClick: () -> Unit
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                IconButton(onClick = onSettingsClick) {
                    Icon(
                        imageVector = Icons.Default.Settings,
                        contentDescription = "Settings",
                        tint = Color.Black
                    )
                }
                IconButton(onClick = onLogoutClick) {
                    Icon(
                        imageVector = Icons.Default.ExitToApp,
                        contentDescription = "Logout",
                        tint = Color.Black
                    )
                }
            }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
                    .background(color = Color.LightGray)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Profile",
                        style = MaterialTheme.typography.headlineSmall,
                        modifier = Modifier.padding(bottom = 8.dp),
                        color = Color.Black
                    )
                    ProfileItem(label = "Username:", value = username)
                    ProfileItem(label = "Pseudo:", value = pseudo)
                    ProfileItem(label = "Description:", value = description)
                    ProfileItem(label = "Date of Birth:", value = dateOfBirth)
                }
            }

            Text(
                text = "Posts",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(vertical = 16.dp),
                color = Color.Black
            )

            for (i in 1..6) {
                PostItem(index = i)
            }
        }
    }

    @Composable
    fun ProfileItem(label: String, value: String) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.labelLarge,
                modifier = Modifier.width(120.dp),
                color = Color.Black
            )
            Text(
                text = value,
                style = MaterialTheme.typography.labelLarge,
                modifier = Modifier.padding(start = 8.dp),
                color = Color.Black
            )
        }
    }

    // Fonction utilitaire pour obtenir l'ID de la ressource drawable en fonction de l'index
    fun getDrawableResourceId(index: Int): Int {
        // Ici, vous pouvez implémenter la logique pour obtenir l'ID de la ressource en fonction de l'index
        // Par exemple, vous pouvez stocker les noms des images dans un tableau et accéder à l'élément correspondant à l'index
        // puis convertir le nom de l'image en identifiant de ressource.
        // Pour cet exemple, je vais simplement renvoyer une ressource par défaut.
        return when (index % 3) {
            0 -> R.drawable.post1
            1 -> R.drawable.post2
            else -> R.drawable.post3
        }
    }

    @Composable
    fun PostItem(index: Int) {
        // Charge l'image à partir des ressources
        val image = painterResource(id = getDrawableResourceId(index))

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "Post $index",
                    style = MaterialTheme.typography.labelLarge,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(8.dp))
                // Affiche l'image
                Image(
                    painter = image,
                    contentDescription = null,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Description of post $index",
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.Black
                )
            }
        }
    }


    private fun performLogout() {
        finish()
    }
}

