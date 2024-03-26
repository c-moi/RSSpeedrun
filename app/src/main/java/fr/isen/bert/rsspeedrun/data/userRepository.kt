package fr.isen.bert.rsspeedrun.data

import com.google.firebase.Firebase
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.database
import fr.isen.bert.rsspeedrun.model.User


class userRepository {

    private val database = Firebase.database
    private val usersRef = database.getReference("users")

    fun readUser() {

    }

    fun addUser(
        user_id:String = "",
        first_name:String = "",
        last_name:String = "",
        username:String = "",
        pp:String = "",
        date2naiss:String = "",
        email:String = "",
        psw:String = ""
    ) {

        val user = User(
            user_id,
            first_name,
            last_name,
            username,
            pp,
            date2naiss,
            email,
            psw
        )
        usersRef.child(user_id).setValue(user)
        //.addOnSuccessListener {
        // L'utilisateur a été ajouté avec succès à la base de données
        // Vous pouvez effectuer des actions supplémentaires ici si nécessaire
        //}
        //.addOnFailureListener { e ->
        // Une erreur s'est produite lors de l'ajout de l'utilisateur
        // Vous pouvez gérer l'erreur ici en conséquence
        //}
    }

    fun delUser() {

    }

    fun chgUser() {

    }
}