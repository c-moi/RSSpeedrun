package fr.isen.bert.rsspeedrun

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import fr.isen.bert.rsspeedrun.ui.theme.RSSpeedrunTheme

class EditUserProfileActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val currentUserInfo = getCurrentUserInfoFromIntent()

        setContent {
            RSSpeedrunTheme {
                // Use Surface to set the background color of the activity to grey
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    color = Color(0xFF344347) // Use the provided grey color
                ) {
                    // The content lambda starts here
                    EditUserProfileContent(
                        currentUserInfo = currentUserInfo,
                        onSaveClick = { updatedUserInfo ->
                            setResult(Activity.RESULT_OK, Intent().apply {
                                putExtra("username", updatedUserInfo.username)
                                putExtra("pseudo", updatedUserInfo.pseudo)
                                putExtra("description", updatedUserInfo.description)
                                putExtra("date_of_birth", updatedUserInfo.dateOfBirth)
                                putExtra("profile_image_uri", updatedUserInfo.profileImageUri)
                            })
                            finish()
                        }
                    )
                } // The content lambda ends here
            }
        }
    }

    @Composable
    fun EditUserProfileContent(
        currentUserInfo: UserInfo,
        onSaveClick: (UserInfo) -> Unit
    ) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color(0xFF344347)
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    // Flèche verte pour retourner en arrière à gauche
                    IconButton(
                        onClick = { finish() },
                        modifier = Modifier.size(48.dp)
                    ){
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.Green // Couleur verte
                        )
                    }
                    // Espace pour séparer les éléments
                    Spacer(modifier = Modifier.size(16.dp))

                    // Bouton rouge "Enregistrer" en haut à droite
                    Button(
                        onClick = { /* Action lorsque le bouton est cliqué */ },
                        modifier = Modifier.size(48.dp),
                        colors = ButtonDefaults.buttonColors(Color.Red)
                    ) {
                        Text("Enregistrer", color = Color.White)
                    }
                }

                // Espace pour séparer les éléments
                Spacer(modifier = Modifier.size(16.dp))

                // Photo de profil au centre en haut
                ProfileImage(painterResource(id = R.drawable.profilutilisateur))

                // Espace pour séparer les éléments
                Spacer(modifier = Modifier.size(16.dp))

                // Champs de texte pour les informations utilisateur
                UserInfoTextFields(currentUserInfo, onSaveClick)
            }
        }
    }


    @Composable
    fun ProfileImage(painterResource: Painter) {
        Image(
            painter = painterResource,
            contentDescription = "Profile Image",
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
        )
    }

    private fun getCurrentUserInfoFromIntent() = UserInfo(
        username = intent.getStringExtra("username") ?: "",
        pseudo = intent.getStringExtra("pseudo") ?: "",
        description = intent.getStringExtra("description") ?: "",
        dateOfBirth = intent.getStringExtra("date_of_birth") ?: "",
        profileImageUri = intent.getStringExtra("profile_image_uri") ?: ""
    )


    @Composable
    fun UserInfoTextFields(currentUserInfo: UserInfo, onSaveClick: (UserInfo) -> Unit) {
        Column(modifier = Modifier.fillMaxWidth()) {
            val (username, setUsername) = remember { mutableStateOf(currentUserInfo.username) }
            val (pseudo, setPseudo) = remember { mutableStateOf(currentUserInfo.pseudo) }
            val (description, setDescription) = remember { mutableStateOf(currentUserInfo.description) }
            val (dateOfBirth, setDateOfBirth) = remember { mutableStateOf(currentUserInfo.dateOfBirth) }

            // Texte "Username" en vert
            Text("Username", color = Color.Green)

            // Username TextField
            OutlinedTextField(
                value = username,
                onValueChange = setUsername,
                label = { Text("Username", color = Color.Green) },
                modifier = Modifier.fillMaxWidth(),
                colors = textFieldColors(),
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                enabled = true // Activer la saisie
            )

            // Trait vert entre les textes
            Divider(color = Color.Green, thickness = 1.dp, modifier = Modifier.padding(vertical = 8.dp))

            // Texte "Pseudo" en vert
            Text("Pseudo", color = Color.Green)

            // Pseudo TextField
            OutlinedTextField(
                value = pseudo,
                onValueChange = setPseudo,
                label = { Text("Pseudo", color = Color.Green) },
                modifier = Modifier.fillMaxWidth(),
                colors = textFieldColors(),
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                enabled = true // Activer la saisie
            )

            // Trait vert entre les textes
            Divider(color = Color.Green, thickness = 1.dp, modifier = Modifier.padding(vertical = 8.dp))

            // Texte "Description" en vert
            Text("Description", color = Color.Green)

            // Description TextField
            OutlinedTextField(
                value = description,
                onValueChange = setDescription,
                label = { Text("Description", color = Color.Green) },
                modifier = Modifier.fillMaxWidth(),
                colors = textFieldColors(),
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                enabled = true // Activer la saisie
            )

            // Trait vert entre les textes
            Divider(color = Color.Green, thickness = 1.dp, modifier = Modifier.padding(vertical = 8.dp))

            // Texte "Date of Birth" en vert
            Text("Date of Birth", color = Color.Green)

            // Date of Birth TextField
            OutlinedTextField(
                value = dateOfBirth,
                onValueChange = setDateOfBirth,
                label = { Text("Date of Birth", color = Color.Green) },
                modifier = Modifier.fillMaxWidth(),
                colors = textFieldColors(),
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                enabled = true // Activer la saisie
            )

            // Bouton "Enregistrer les modifications"
            Button(
                onClick = { onSaveClick(UserInfo(username, pseudo, description, dateOfBirth, currentUserInfo.profileImageUri)) },
                modifier = Modifier
                    .padding(top = 16.dp)
                    .fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(Color.Green)
            ) {
                Text("Enregistrer les modifications", color = Color.White)
            }
        }
    }


    @Composable
    fun textFieldColors(): TextFieldColors {
        return OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color(0xFF6FCB9A),
            unfocusedBorderColor = Color(0xFF9DADAC),
            focusedLabelColor = Color.Green,
            unfocusedLabelColor = Color(0xFF9DADAC),
        )
    }

    data class UserInfo(
        val username: String,
        val pseudo: String,
        val description: String,
        val dateOfBirth: String,
        val profileImageUri: String
    )
}
