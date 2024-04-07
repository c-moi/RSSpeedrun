package fr.isen.bert.rsspeedrun.homefeed

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import fr.isen.bert.rsspeedrun.homefeed.HomeActivity
import fr.isen.bert.rsspeedrun.ui.theme.RSSpeedrunTheme

class ProfilActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RSSpeedrunTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting3("Android")
                    val intent = Intent(this, HomeActivity::class.java)

                    // Démarrez l'activité HomeActivity
                    startActivity(intent)

                    // Terminez l'activité MainActivity
                    finish()
                }
            }
        }
    }
}

@Composable
fun Greeting3(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview3() {
    RSSpeedrunTheme {
        Greeting3("Android")
    }
}