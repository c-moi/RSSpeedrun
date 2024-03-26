package fr.isen.bert.rsspeedrun

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import fr.isen.bert.rsspeedrun.databinding.ActivitySignInBinding

class SignInActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignInBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sign_in)

        firebaseAuth = FirebaseAuth.getInstance()


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        binding.textView.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

        binding.button.setOnClickListener {
            val email = binding.emailEt.text.toString()
            val pass = binding.passET.text.toString()

            if (email.isNotEmpty() && pass.isNotEmpty()) {

                firebaseAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener {
                    if (it.isSuccessful) {
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()

                    }
                }
            } else {
                Toast.makeText(this, "Empty Fields Are not Allowed !!", Toast.LENGTH_SHORT).show()

            }
        }

        // Récupérer la référence de la TextView avec l'identifiant R.id.textView
        val textView = findViewById<TextView>(R.id.textView)

        // Ajouter un écouteur de clic à la TextView
        textView.setOnClickListener {
            // Créer une intention pour démarrer SignUpActivity lorsque la TextView est cliquée
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
    }
    /**private fun checkUserLoggedIn() {
        // Vérifier si un utilisateur est déjà connecté
        val currentUser = firebaseAuth.currentUser
        if (currentUser != null) {
            // Si un utilisateur est déjà connecté, rediriger vers MainActivity
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish() // Optionnel : terminer SignInActivity pour éviter de revenir en arrière
        } else {
            // Si aucun utilisateur n'est connecté, afficher un message d'invite pour se connecter
            Toast.makeText(this, "Please Sign In", Toast.LENGTH_SHORT).show()
        }
    }**/

}