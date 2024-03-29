package fr.isen.bert.rsspeedrun

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ExitToApp
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
        val currentUserInfo = getCurrentUserInfoFromIntent(intent)

        setContent {
            RSSpeedrunTheme {
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    color = Color(0xFF344347)
                ) {
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
                        },
                        onLogoutClick = {
                            Toast.makeText(this@EditUserProfileActivity, "Vous êtes déconnecté", Toast.LENGTH_SHORT).show()
                        },
                        onDeleteAccountClick = {
                            Toast.makeText(this@EditUserProfileActivity, "Compte supprimé", Toast.LENGTH_SHORT).show()
                        }
                    )
                }
            }
        }
    }

    @Composable
    fun EditUserProfileContent(
        currentUserInfo: UserInfo,
        onSaveClick: (UserInfo) -> Unit,
        onLogoutClick: () -> Unit,
        onDeleteAccountClick: () -> Unit // Ajout du callback pour supprimer le compte
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
                // Déplacement du bouton "Déconnexion" en haut à droite
                IconButton(
                    onClick = onLogoutClick,
                    modifier = Modifier.align(Alignment.End)
                ){
                    Icon(
                        imageVector = Icons.Default.ExitToApp, // Changer l'icône en ExitToApp
                        contentDescription = "Déconnexion",
                        tint = Color.Red // Utilisation de la couleur rouge
                    )
                }

                Spacer(modifier = Modifier.size(16.dp))

                ProfileImage(painterResource(id = R.drawable.profilutilisateur))

                Spacer(modifier = Modifier.size(16.dp))

                UserInfoTextFields(currentUserInfo, onSaveClick)

                // Ajout du bouton "Supprimer ce compte" en bas de la page
                Button(
                    onClick = onDeleteAccountClick,
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(Color.Red)
                ) {
                    Text("Supprimer ce compte", color = Color.White)
                }
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

    private fun getCurrentUserInfoFromIntent(intent: Intent?): UserInfo {
        return if (intent != null) {
            UserInfo(
                username = intent.getStringExtra("username") ?: "",
                pseudo = intent.getStringExtra("pseudo") ?: "",
                description = intent.getStringExtra("description") ?: "",
                dateOfBirth = intent.getStringExtra("date_of_birth") ?: "",
                profileImageUri = intent.getStringExtra("profile_image_uri") ?: ""
            )
        } else {
            // Gérer le cas où l'intent est null, par exemple en renvoyant des valeurs par défaut
            UserInfo(
                username = "",
                pseudo = "",
                description = "",
                dateOfBirth = "",
                profileImageUri = ""
            )
        }
    }

    @Composable
    fun UserInfoTextFields(currentUserInfo: UserInfo, onSaveClick: (UserInfo) -> Unit) {
        Column(modifier = Modifier.fillMaxWidth()) {
            val (username, setUsername) = remember { mutableStateOf(currentUserInfo.username) }
            val (pseudo, setPseudo) = remember { mutableStateOf(currentUserInfo.pseudo) }
            val (description, setDescription) = remember { mutableStateOf(currentUserInfo.description) }
            val (dateOfBirth, setDateOfBirth) = remember { mutableStateOf(currentUserInfo.dateOfBirth) }

            Text("Username", color = Color.Green)

            OutlinedTextField(
                value = username,
                onValueChange = setUsername,
                modifier = Modifier.fillMaxWidth(),
                colors = textFieldColors(),
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                enabled = true
            )

            Divider(color = Color.Green, thickness = 1.dp, modifier = Modifier.padding(vertical = 8.dp))

            Text("Pseudo", color = Color.Green)

            OutlinedTextField(
                value = pseudo,
                onValueChange = setPseudo,
                modifier = Modifier.fillMaxWidth(),
                colors = textFieldColors(),
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                enabled = true
            )

            Divider(color = Color.Green, thickness = 1.dp, modifier = Modifier.padding(vertical = 8.dp))

            Text("Description", color = Color.Green)

            OutlinedTextField(
                value = description,
                onValueChange = setDescription,
                modifier = Modifier.fillMaxWidth(),
                colors = textFieldColors(),
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                enabled = true
            )

            Divider(color = Color.Green, thickness = 1.dp, modifier = Modifier.padding(vertical = 8.dp))

            Text("Date of Birth", color = Color.Green)

            OutlinedTextField(
                value = dateOfBirth,
                onValueChange = setDateOfBirth,
                modifier = Modifier.fillMaxWidth(),
                colors = textFieldColors(),
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                enabled = true
            )

            Button(
                onClick = { onSaveClick(UserInfo(username, pseudo, description, dateOfBirth, currentUserInfo.profileImageUri)) },
                modifier = Modifier
                    .padding(top = 16.dp)
                    .fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(Color(0xFF6FCB9A))
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
