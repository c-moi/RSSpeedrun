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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import fr.isen.bert.rsspeedrun.ui.theme.RSSpeedrunTheme

class EditUserProfileActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Récupère les données envoyées depuis UserProfileActivity
        val username = intent.getStringExtra("username") ?: ""
        val pseudo = intent.getStringExtra("pseudo") ?: ""
        val description = intent.getStringExtra("description") ?: ""
        val dateOfBirth = intent.getStringExtra("date_of_birth") ?: ""

        setContent {
            // Utilise le thème RSSpeedrun
            RSSpeedrunTheme {
                // Affiche le contenu de l'activité
                EditUserProfileContent(username, pseudo, description, dateOfBirth)
            }
        }
    }

    // Fonction pour sauvegarder les modifications et retourner à l'activité précédente
    private fun saveChanges(username: String, pseudo: String, description: String, dateOfBirth: String) {
        val resultIntent = Intent().apply {
            putExtra("username", username)
            putExtra("pseudo", pseudo)
            putExtra("description", description)
            putExtra("date_of_birth", dateOfBirth)
        }
        setResult(RESULT_OK, resultIntent)
        finish()
    }

    // Fonction pour afficher le contenu de l'activité
    @Composable
    fun EditUserProfileContent(username: String, pseudo: String, description: String, dateOfBirth: String) {
        // Utilise un état mutable pour stocker les valeurs modifiées
        val newUsername = remember { mutableStateOf(username) }
        val newPseudo = remember { mutableStateOf(pseudo) }
        val newDescription = remember { mutableStateOf(description) }
        val newDateOfBirth = remember { mutableStateOf(dateOfBirth) }

        // Affiche les champs de texte et le bouton pour sauvegarder les modifications
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Titre de la page
            // Champ de texte pour le nom d'utilisateur
            TextField(
                value = newUsername.value,
                onValueChange = { newUsername.value = it },
                label = { Text("Username") }
            )
            Spacer(modifier = Modifier.height(16.dp))
            // Champ de texte pour le pseudo
            TextField(
                value = newPseudo.value,
                onValueChange = { newPseudo.value = it },
                label = { Text("Pseudo") }
            )
            Spacer(modifier = Modifier.height(16.dp))
            // Champ de texte pour la description
            TextField(
                value = newDescription.value,
                onValueChange = { newDescription.value = it },
                label = { Text("Description") }
            )
            Spacer(modifier = Modifier.height(16.dp))
            // Champ de texte pour la date de naissance
            TextField(
                value = newDateOfBirth.value,
                onValueChange = { newDateOfBirth.value = it },
                label = { Text("Date of Birth") }
            )
            Spacer(modifier = Modifier.height(16.dp))
            // Bouton pour sauvegarder les modifications
            Button(onClick = {
                saveChanges(newUsername.value, newPseudo.value, newDescription.value, newDateOfBirth.value)
            }) {
                Text("Save Changes")
            }
        }
    }
}
