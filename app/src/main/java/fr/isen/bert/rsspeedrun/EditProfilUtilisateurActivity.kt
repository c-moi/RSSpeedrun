package fr.isen.bert.rsspeedrun

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
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

data class UserProfile(
    var description: String = "",
    var birthDate: String = "",
    var username: String = "",
    var pseudo: String = ""
)

private fun updateUserDetails(context: Context, user: UserProfile) {
    val currentUser = FirebaseAuth.getInstance().currentUser
    if (currentUser != null) {
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("users").child(currentUser.uid)

        myRef.setValue(user).addOnSuccessListener {
            Toast.makeText(context, "User details updated successfully", Toast.LENGTH_SHORT).show()
        }.addOnFailureListener {
            Toast.makeText(context, "Failed to update user details", Toast.LENGTH_SHORT).show()
        }
    } else {
        Toast.makeText(context, "User is not authenticated", Toast.LENGTH_SHORT).show()
    }
}
@Composable
fun EditUserProfileScreen() {
    val context = LocalContext.current // Accès au contexte
    var userProfile by remember { mutableStateOf(UserProfile()) }

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
        ProfileFormFields(userProfile) { userProfile = it }

        Spacer(modifier = Modifier.height(32.dp))

        // Buttons for additional options
        ActionButtonsEditProfile(
            onSaveClick = {

                updateUserDetails(context, userProfile)
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
            painter = painterResource(id = R.drawable.ic_action_name),
            contentDescription = "Profile Image",
            modifier = Modifier
                .size(120.dp)
                .background(Color.Gray),
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
fun ProfileFormFields(userProfile: UserProfile, onUserProfileChange: (UserProfile) -> Unit) {
    Column {
        TextFieldWithLabel("Description", userProfile.description) {
            onUserProfileChange(userProfile.copy(description = it))
        }
        Spacer(modifier = Modifier.height(16.dp))
        TextFieldWithLabel("Date de naissance", userProfile.birthDate) {
            onUserProfileChange(userProfile.copy(birthDate = it))
        }
        Spacer(modifier = Modifier.height(16.dp))
        TextFieldWithLabel("Username", userProfile.username) {
            onUserProfileChange(userProfile.copy(username = it))
        }
        Spacer(modifier = Modifier.height(16.dp))
        TextFieldWithLabel("Pseudo", userProfile.pseudo) {
            onUserProfileChange(userProfile.copy(pseudo = it))
        }
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
