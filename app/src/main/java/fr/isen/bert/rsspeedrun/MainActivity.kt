package fr.isen.bert.rsspeedrun

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import fr.isen.bert.rsspeedrun.EditProfileUserActivity
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
                    UserProfileScreen {
                        val intent = Intent(this@MainActivity, EditProfileUserActivity::class.java)
                        startActivity(intent)
                    }
                }
            }
        }
    }
}

@Composable
fun UserProfileScreen(onEditProfileClicked: () -> Unit) {
    Column {
        Text(text = "User Profile Screen")
        Button(onClick = onEditProfileClicked) {
            Text("Edit Profile")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    RSSpeedrunTheme {
        UserProfileActivity()
    }
}