package fr.isen.bert.rsspeedrun

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import fr.isen.bert.rsspeedrun.ui.theme.RSSpeedrunTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RSSpeedrunTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Pass the required parameters to UserProfilePage function
                    UserProfilePageScreen(
                        user = User(
                            name = "John Doe",
                            username = "john_doe",
                            description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
                            birthDate = "01/01/1990",
                            profileImageUrl = "https://example.com/profile.jpg"
                        ),
                        onEditProfileClicked = { handleEditProfile() },
                        onLogoutClicked = { handleLogout() }
                    )
                }
            }
        }
    }

    // Function to navigate to the edit profile activity
    private fun handleEditProfile() {
        val intent = Intent(this, EditProfilUtilisateurActivity::class.java)
        startActivity(intent)
    }

    // Function to handle logout click
    private fun handleLogout() {
        // Replace this with your actual implementation to logout the user
        Toast.makeText(this, "Logout Clicked", Toast.LENGTH_SHORT).show()
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    RSSpeedrunTheme {
        Greeting("Android")
    }
}
