package fr.isen.bert.rsspeedrun

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
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
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    //Greeting("Android")

                    val database = Firebase.database


                    // Write a message to the database
                    BDDWriting(database, "test", "ok1")

                    // Read a message to the database
                    BDDReading(database, "test")
                }
            }
        }
    }
}

@Composable
fun BDDWriting(database: FirebaseDatabase, name: String, content: String) {

    val myRef = database.getReference(name)
    myRef.setValue(content)
}

@Composable
fun BDDReading(database: FirebaseDatabase, name: String) {

    val myRef = database.getReference(name)
    var value:Any? = ""
    myRef.addValueEventListener(object: ValueEventListener {

        override fun onDataChange(snapshot: DataSnapshot) {
            // This method is called once with the initial value and again
            // whenever data at this location is updated.
            value = snapshot.getValue()
        }

        override fun onCancelled(error: DatabaseError) {
            value = "error"
        }

    })

    Text(text = "ceci est $value")
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