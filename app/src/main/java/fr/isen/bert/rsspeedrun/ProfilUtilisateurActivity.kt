package fr.isen.bert.rsspeedrun

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import fr.isen.bert.rsspeedrun.R
import fr.isen.bert.rsspeedrun.ui.theme.RSSpeedrunTheme

data class User(
    var name: String,
    var username: String,
    var description: String,
    var birthDate: String,
    var profileImageUrl: String
)

class ProfilUtilisateurActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var user = User(
            name = "John Doe",
            username = "john_doe",
            description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
            birthDate = "01/01/1990",
            profileImageUrl = "https://example.com/profile.jpg"
        )

        setContent {
            RSSpeedrunTheme {
                UserProfilePageScreen(
                    user = user,
                    onEditProfileClicked = {
                        // Mettez ici le code pour démarrer l'activité EditProfilUtilisateurActivity
                        startActivity(Intent(this@ProfilUtilisateurActivity, EditProfilUtilisateurActivity::class.java))
                    },
                    onLogoutClicked = {
                        // Mettez ici le code pour gérer la déconnexion de l'utilisateur
                        // Par exemple, vous pourriez appeler une fonction de déconnexion ou afficher une boîte de dialogue de confirmation.
                    }
                )
            }
        }
    }
}

@Composable
fun UserProfilePageScreen(user: User, onEditProfileClicked: () -> Unit, onLogoutClicked: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        ProfileHeader(user = user)
        Spacer(modifier = Modifier.height(16.dp))
        ProfileInfo(user = user)
        Spacer(modifier = Modifier.height(32.dp))
        ActionButtons(
            onEditProfileClicked = onEditProfileClicked,
            onLogoutClicked = onLogoutClicked
        )
    }
}

@Composable
fun ProfileHeader(user: User) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_action_name),
            contentDescription = "Profile Image",
            modifier = Modifier
                .size(72.dp)
                .clip(CircleShape)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(text = user.name, style = MaterialTheme.typography.headlineSmall)
            Text(text = "@${user.username}", style = MaterialTheme.typography.bodyLarge)
        }
    }
}

@Composable
fun ProfileInfo(user: User) {
    Column {
        ProfileInfoItem(title = "Description:", content = user.description)
        ProfileInfoItem(title = "Date de naissance:", content = user.birthDate)
    }
}

@Composable
fun ProfileInfoItem(title: String, content: String) {
    Column(
        modifier = Modifier.padding(bottom = 8.dp)
    ) {
        Text(text = title, style = MaterialTheme.typography.labelSmall)
        Text(text = content, style = MaterialTheme.typography.bodyMedium)
    }
}

@Composable
fun ActionButtons(onEditProfileClicked: () -> Unit, onLogoutClicked: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = onEditProfileClicked,
            modifier = Modifier.padding(8.dp)
        ) {
            Text("Editer le profil")
        }
        Button(
            onClick = onLogoutClicked,
            modifier = Modifier.padding(8.dp)
        ) {
            Text("Se déconnecter")
        }
    }
}
