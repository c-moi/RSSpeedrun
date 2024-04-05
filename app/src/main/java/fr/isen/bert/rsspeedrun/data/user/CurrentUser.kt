package fr.isen.bert.rsspeedrun.data.user

import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import fr.isen.bert.rsspeedrun.model.User

class CurrentUser {

    private val database = Firebase.database
    private val userRef = database.getReference("users")

    fun createUser(auth: FirebaseAuth, newUser: User) {

        val dataUser = ManageUser()
        dataUser.findUser(newUser.email) { currentUser: User?, _:String? ->
            if (currentUser != null) {
                Log.d("pb", "L'utilisateur avec l'adresse e-mail ${newUser.email} est déjà enregistré dans la base de données.")
            } else {
                auth.createUserWithEmailAndPassword(newUser.email, newUser.psw)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Log.d("signupSuccess", "Création de compte réussie pour l'utilisateur avec l'adresse e-mail : ${newUser.email}")
                            auth.signInWithEmailAndPassword(newUser.email, newUser.psw)
                                .addOnCompleteListener { signinTask ->
                                    if (signinTask.isSuccessful) {
                                        Log.d("signinSuccess", "Authentification réussie pour l'utilisateur avec l'adresse e-mail : ${newUser.email}")
                                        dataUser.addUser(newUser.email,
                                            newUser.firstname,
                                            newUser.lastname,
                                            newUser.username,
                                            newUser.bio,
                                            newUser.pp,
                                            newUser.psw)
                                    } else {
                                        Log.d("signinErr", "Échec de l'authentification pour l'utilisateur avec l'adresse e-mail : ${newUser.email}")
                                        val exception = signinTask.exception
                                        exception?.let {
                                            println("Erreur : ${it.message}")
                                        }
                                    }
                                }
                        } else {
                            Log.d("SignupErr", "Échec de la création de compte pour l'utilisateur avec l'adresse e-mail : ${newUser.email}")
                        }
                    }
            }
        }
    }

    fun deleteUser(auth:FirebaseAuth, email:String, user:FirebaseUser?, completion: (FirebaseUser?) -> Unit) {
        val rmUser = ManageUser()
        rmUser.delUser(auth, email) { success ->
            if (success) {
                if (user == null) {
                    Log.d("null", "gros naze")
                }
                user?.delete()
                    ?.addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Log.d("successDelAuth", "l'utilisateur ${email} est supprimé avec succès")
                            completion(null)
                        } else {
                            Log.d("failDelAuth", "l'utilisateur ${email} n'a pas pu être supprimé de FirebaseAuth")
                            completion(auth.currentUser)
                        }
                    }
            } else {
                Log.d("failDelBDD", "L'utilisateur ${email} n'a pas pu être supprimé de la BDD")
                completion(auth.currentUser)
            }
        }
    }

    fun logInUser(auth:FirebaseAuth, email: String, password:String) {
        userRef.orderByChild("email").equalTo(email)
            .addListenerForSingleValueEvent(
                object: ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if(snapshot.exists()) {
                            val user = snapshot.children.first()
                            val storedPassword = user.child("psw").getValue(String::class.java)

                            if (storedPassword == password) {
                                auth.signInWithEmailAndPassword(email, password)
                            } else {
                                //mettre un toast pour prévenir de l'erreur
                            }
                        } else {
                            //same
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        //same
                        TODO("Not yet implemented")
                    }
                }
            )
    }

    fun logOutUser(auth:FirebaseAuth?) {
        auth?.signOut()
    }
}