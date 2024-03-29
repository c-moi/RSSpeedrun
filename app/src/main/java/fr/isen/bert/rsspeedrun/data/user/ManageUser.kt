package fr.isen.bert.rsspeedrun.data.user

import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import fr.isen.bert.rsspeedrun.model.User

class ManageUser {

    private val database = Firebase.database
    private val userRef = database.getReference("users")

    fun addUser(
        email:String = "",
        firstname:String = "",
        lastname:String = "",
        username:String = "",
        bio:String = "",
        pp:String = "",
        psw:String = ""
    ) {

        val user = User(
            email = email,
            firstname = firstname,
            lastname = lastname,
            username = username,
            bio = bio,
            pp = pp,
            psw = psw
        )

        userRef.push().setValue(user)
    }

    fun delUser(auth: FirebaseAuth, email:String, completion: (Boolean) -> Unit) {
        userRef.orderByChild("email").equalTo(email)
            .addListenerForSingleValueEvent(
                object: ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (snapshot.exists()) {
                            for (userSnapshot in snapshot.children) {
                                val userId = userSnapshot.key
                                val userDel = userRef.child(userId!!)
                                userDel.removeValue()
                                    .addOnSuccessListener {
                                        Log.d("successDelBDD", "l'utilisateur ${email} a été supprimé de la BDD avec succès")
                                        completion(true)
                                    }
                                    .addOnFailureListener {
                                        Log.d("failDelBDD", "L'utilisateur ${email} n'a pas pu être supprimé de la BDD")
                                        completion(false)
                                    }
                            }
                        }
                    }
                    override fun onCancelled(error: DatabaseError) {
                        Log.e("deleteUserByEmail", "Erreur lors de la récupération des données de la base de données en temps réel: ${error.message}")
                        completion(false)
                    }
                }
            )
    }

    fun modifUser(
        email:String = "",
        updatedUser: User
    ) {
        userRef.orderByChild("email").equalTo(email)
            .addListenerForSingleValueEvent(
                object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if(snapshot.exists()) {
                            for (userSnaphot in snapshot.children) {
                                val userId = userSnaphot.key
                                userId?.let {
                                    val updatedUserData = hashMapOf<String, Any>()

                                    updatedUser.firstname.let { updatedUserData["firstname"] = it }
                                    updatedUser.lastname.let { updatedUserData["lastname"] = it }
                                    updatedUser.username.let { updatedUserData["username"] = it }
                                    updatedUser.bio.let { updatedUserData["bio"] = it }
                                    updatedUser.pp.let { updatedUserData["pp"] = it }

                                    userRef.child(userId).updateChildren(updatedUserData)
                                }
                            }
                        } else {

                        }
                    }
                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }
                }
            )
    }

    fun findUser(email:String,
                 onComplete: (user:User?, userId:String?) -> Unit
    ) {
        userRef.orderByChild("email").equalTo(email)
            .addListenerForSingleValueEvent(
                object:ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (snapshot.exists()) {
                            val userId = snapshot.children.first().key

                            val user = User(snapshot.children.first().child("email")
                                .getValue(String::class.java) ?: "",
                                snapshot.children.first().child("firstname")
                                    .getValue(String::class.java) ?: "",
                                snapshot.children.first().child("lastname")
                                    .getValue(String::class.java) ?: "",
                                snapshot.children.first().child("username")
                                    .getValue(String::class.java) ?: "",
                                snapshot.children.first().child("bio")
                                    .getValue(String::class.java) ?: "",
                                snapshot.children.first().child("pp")
                                    .getValue(String::class.java) ?: "",
                                snapshot.children.first().child("psw")
                                    .getValue(String::class.java) ?: "",
                            )

                            onComplete(user, userId)
                        } else {
                            Log.d("noMailExist", "le mail n'a pas été trouvé")
                            onComplete(null, null)
                        }
                    }
                    override fun onCancelled(error: DatabaseError) {
                        Log.d("errResearch", "Erreur lors de la recherche de l'utilisateur : ${error.message}")
                        onComplete(null, null)
                    }
                }
            )
    }
}