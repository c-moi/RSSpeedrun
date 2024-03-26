package fr.isen.bert.rsspeedrun.data

import com.google.firebase.auth.FirebaseAuth

class userManager {

    fun createUser(
        firstname:String = "",
        lastname:String = "",
        username:String = "",
        pp:String = "",
        date2naiss:String = "",
        email:String = "",
        psw:String = "",
    ) {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, psw)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val firebaseUser = task.result?.user
                    firebaseUser?.let {
                        val userId = it.uid

                        val userRepository = userRepository()
                        userRepository.addUser(
                            userId,
                            firstname,
                            lastname,
                            username,
                            pp,
                            date2naiss,
                            email,
                            psw
                        )

                        //onSuccess.invoke()
                    } ?: run {
                        //onFailure.invoke("FirebaseUser is null")
                    }
                } else {
                    //onFailure.invoke(task.exception?.message ?: "Unknown error")
                }
            }
    }
}