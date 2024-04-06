package fr.isen.bert.rsspeedrun

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import fr.isen.bert.rsspeedrun.ui.theme.RSSpeedrunTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RSSpeedrunTheme {
                val intent = Intent(this@MainActivity, UserProfileActivity::class.java)
                startActivity(intent)
            }
        }
    }
}
