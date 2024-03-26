package fr.isen.bert.rsspeedrun
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.*


data class User(val username: String? = null, val email: String? = null, val name: String? = null, val surname: String? = null, val birthdate: String? = null, val bio: String? = null) {
    // Null default values create a no-argument default constructor, which is needed
    // for deserialization from a DataSnapshot.
}


fun updateUserNode(userId: String, childNode: String, childValue: String, email: String, name: String, surname: String) {
    val database: FirebaseDatabase = FirebaseDatabase.getInstance()

    val userRef: DatabaseReference = database.getReference("users").child(userId)

    val newChildRef: DatabaseReference = userRef.child(childNode).push()
    newChildRef.setValue(childValue)
        .addOnSuccessListener {
            println("Child node updated successfully")
        }
        .addOnFailureListener { e ->
            println("Error updating child node: $e")
        }
}

fun updateUser(userId: String, updates: Map<String, Any>) {
    // Obtenir une référence à la base de données Firebase
    val database: FirebaseDatabase = Firebase.Database.getInstance()

    // Obtenir une référence au nœud de l'utilisateur spécifié
    val userRef: DatabaseReference = database.getReference("users").child(userId)

    // Utiliser updateChildren() pour mettre à jour plusieurs nœuds enfants en une seule opération
    userRef.updateChildren(updates)
        .addOnSuccessListener {
            println("User data updated successfully")
        }
        .addOnFailureListener { e ->
            println("Error updating user data: $e")
        }
}

fun main() {
    // Exemple de mises à jour à effectuer
    val updates = mapOf(
        "name" to "John",
        "surname" to "Doe",
        "birthdate" to "1990-01-01",
        "bio" to "Updated bio"
    )

    // Appel de la méthode pour mettre à jour l'utilisateur avec les mises à jour spécifiées
    val userId = "id_de_l_utilisateur"
    updateUser(userId, updates)
}