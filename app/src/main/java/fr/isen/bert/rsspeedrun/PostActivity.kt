package fr.isen.bert.rsspeedrun

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fr.isen.bert.rsspeedrun.ui.theme.RSSpeedrunTheme
import fr.isen.bert.rsspeedrun.ui.theme.background
import fr.isen.bert.rsspeedrun.ui.theme.secondary
import fr.isen.bert.rsspeedrun.ui.theme.white
data class Post_info(
    var Title : String,
    var game : String,
    var Content : String,
)
class PostActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RSSpeedrunTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Header()
                    ProfileButton()
                    ThreeFields()
                }
            }
        }
    }
}
@Composable
fun ThreeFields() {
    Column(
        modifier = Modifier.padding(16.dp)
            .padding(horizontal = 2.dp, vertical = 150.dp),
    ) {
        SimpleOutlinedTextFieldSampleTitre(label = "Jeu")
        Spacer(modifier = Modifier.height(16.dp))
        SimpleOutlinedTextFieldSampleJeu(label = "Temps")
        Spacer(modifier = Modifier.height(16.dp))
        SimpleOutlinedTextFieldSampleContent(label = "Contenu")
    }
}

@Composable
fun SimpleOutlinedTextFieldSampleTitre(label :String) {
    var text by remember { mutableStateOf("") }

    OutlinedTextField(
        value = text,
        onValueChange = { text = it },
        colors = textFieldColors(),
        label = { Text("Title") }
    )
}

@Composable
fun SimpleOutlinedTextFieldSampleJeu(label :String) {
    var text by remember { mutableStateOf("") }

    OutlinedTextField(
        value = text,
        onValueChange = { text = it },
        colors = textFieldColors(),
        label = { Text("Game") }
    )
}

@Composable
fun SimpleOutlinedTextFieldSampleContent(label :String) {
    var text by remember { mutableStateOf("") }

    OutlinedTextField(
        value = text,
        onValueChange = { text = it },
        colors = textFieldColors(),
        label = { Text("Content") }
    )
}




