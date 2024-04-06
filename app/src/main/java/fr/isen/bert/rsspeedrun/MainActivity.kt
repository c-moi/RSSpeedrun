package fr.isen.bert.rsspeedrun

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import fr.isen.bert.rsspeedrun.UserProfileActivity
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import com.google.firebase.auth.FirebaseAuth
import fr.isen.bert.rsspeedrun.data.post.ManagePost
import fr.isen.bert.rsspeedrun.data.user.CurrentUser
import fr.isen.bert.rsspeedrun.data.user.ManageUser
import fr.isen.bert.rsspeedrun.model.Post
import fr.isen.bert.rsspeedrun.model.User
import fr.isen.bert.rsspeedrun.ui.theme.RSSpeedrunTheme

class MainActivity : ComponentActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // pour checker si un utilisateur est connecté
            auth = FirebaseAuth.getInstance()

            val currentUser = auth.currentUser
            val user = CurrentUser()
            val chgUser = ManageUser()
            val post = ManagePost()
            RSSpeedrunTheme {
                val intent = Intent(this@MainActivity, UserProfileActivity::class.java)
                startActivity(intent)
            }
        }
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Call the goToProfile function to navigate to UserProfileActivity
                    goToProfile()
                }
            }
        }
    }
                    goToProfile()
                    /*if (currentUser != null) {
                        // rediriger vers une activité de l'appli
                        Greeting("Android")

                        // supprimer l'utilisateur
                        //user.deleteUser(auth, "emmanuel.bert2@gmail.com", currentUser) {updatedUser ->
                        //    currentUser = updatedUser
                        //}
                        //if (currentUser != null) {
                        //    auth.signOut()
                        //}

                        // changer les infos d'un utilisateur
                        //chgUser.modifUser("emmanuel.bert2@gmail.com",
                        //    User("emmanuel.bert2@gmail.com",
                        //                "emmanuel", "bert",
                        //                "cpamoi", "cpalebon"))

                        // déconnecter un utilisateur
                        //user.logOutUser(auth)

                        // ajouter un post
                        /*var userId:String = ""
                        chgUser.findUser("emmanuel.bert@gmail.com") { _, id ->
                            if (id != null){
                                userId = id

                                //Log.d("idname", "ceci est mon id $userId")
                                post.addPost(
                                    "hello",
                                    "image",
                                    "ceci est un post",
                                    "2024/03/29",
                                    userId)
                            } else {
                                Log.d("idnameErr", "aucun id récupéré")
                            }
                        }*/

                        // lister les posts
                        /*chgUser.findUser("emmanuel.bert@gmail.com") { _, id ->
                            if (id != null) {
                                var postIds:List<String> = emptyList()

                                post.listPostByOwner(id) { ids ->
                                    postIds = ids

                                    Log.d("UserPostsIds", "IDs des posts de l'utilisateur: $postIds")

                                    if (postIds.isNotEmpty())
                                    {
                                        post.findPosts(postIds) { postList ->
                                            Log.d("Posts", "Liste des posts: ")
                                            for (unpost in postList) {
                                                Log.d("Post", "Title: ${unpost.title}, Picture: ${unpost.picture}, " +
                                                        "Description: ${unpost.description}")
                                            }
                                        }
                                    }
                                }
                            } else {
                                Log.d("idnameErr", "aucun id récupéré")
                            }
                        }*/

                        //pour supprimer un post
                        /*chgUser.findUser("emmanuel.bert@gmail.com") { _, id ->
                            if (id != null) {
                                var postIds:List<String> = emptyList()

                                post.listPostByOwner(id) { ids ->
                                    postIds = ids

                                    Log.d("UserPostsIds", "IDs des posts de l'utilisateur: $postIds")

                                    for (postId in postIds) {
                                        post.getPost(postId) { unpost ->
                                            // Vérifier si selected est true
                                            if (unpost != null && unpost.selected) {
                                                // Supprimer le post
                                                post.delPost(postId)
                                            }
                                        }
                                    }
                                }
                            } else {
                                Log.d("idnameErr", "aucun id récupéré")
                            }
                        }*/

                        // pour modifier un post
                        chgUser.findUser("emmanuel.bert@gmail.com") { _, id ->
                            if (id != null) {
                                var postIds:List<String> = emptyList()

                                post.listPostByOwner(id) { ids ->
                                    postIds = ids

                                    Log.d("UserPostsIds", "IDs des posts de l'utilisateur: $postIds")

                                    for (postId in postIds) {
                                        post.getPost(postId) { unpost ->
                                            // Vérifier si selected est true
                                            if (unpost != null && unpost.selected) {

    // Function to navigate to UserProfileActivity
    private fun goToProfile() {
        val intent = Intent(this, UserProfileActivity::class.java)
                                                post.modifPost(postId, Post(
                                                    postId,"helloNEW","",
                                                    "c'est gregours","newDate",
                                                    id,true
                                                ))
                                            }
                                        }
                                    }
                                }
                            } else {
                                Log.d("idnameErr", "aucun id récupéré")
                            }
                        }

                    } else {
                        // rediriger vers l'activité d'authent
                        Greeting("Android2")*/

                        // créer un nouvel utilisateur
                        /*user.createUser(auth,
                            User("emmanuel.bert@gmail.com",
                                "emmanuel", "bert",
                               "cmoi", "ouai c greg", "osef", "cpalebon")*/
                        //)

                        // connecter un utilisateur
                        //user.logInUser(auth, "emmanuel.bert@gmail.com", "cpalebon")
                    //}
                }
            }
        }
    }

    private fun goToProfile() {
        val intent = Intent(this@MainActivity, ProfilUtilisateurActivity::class.java)
        startActivity(intent)
    }
}
