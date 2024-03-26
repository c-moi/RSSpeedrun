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
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
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

                firebaseAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener {task ->
                    if (task.isSuccessful) {
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                }.addOnFailureListener { exception ->
                    // Cette partie du code sera exécutée si la connexion échoue
                    if (exception is FirebaseAuthInvalidCredentialsException) {
                        // Le mot de passe est incorrect
                        Toast.makeText(this, "Mot de passe incorrect", Toast.LENGTH_SHORT).show()
                    } else {
                        // Une autre erreur s'est produite
                        Toast.makeText(this, exception.message, Toast.LENGTH_SHORT).show()
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


}