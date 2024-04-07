package fr.isen.bert.rsspeedrun

import android.content.Intent
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
import com.google.firebase.auth.FirebaseAuth
import fr.isen.bert.rsspeedrun.data.comment.ManageComment
import fr.isen.bert.rsspeedrun.data.post.ManagePost
import fr.isen.bert.rsspeedrun.data.user.CurrentUser
import fr.isen.bert.rsspeedrun.data.user.ManageUser
import fr.isen.bert.rsspeedrun.model.User
import fr.isen.bert.rsspeedrun.ui.theme.RSSpeedrunTheme

class MainActivity : ComponentActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            // pour checker si un utilisateur est connecté
            auth = FirebaseAuth.getInstance()

            var currentUser = auth.currentUser
            val user = CurrentUser()
            val chgUser = ManageUser()
            val post = ManagePost()
            val comm = ManageComment()

            RSSpeedrunTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    if (currentUser != null) {
                        // rediriger vers une activité de l'appli
                        Greeting("Android")

                        // supprimer l'utilisateur
                        /*user.deleteUser(auth, "emmanuel.bert@gmail.com", currentUser) {updatedUser ->
                            currentUser = updatedUser
                        }
                        if (currentUser != null) {
                            auth.signOut()
                        }*/

                        // changer les infos d'un utilisateur
                        /*chgUser.modifUser("emmanuel.bert@gmail.com",
                            User("emmanuel.bert@gmail.com",
                                        "emmanuel", "bert",
                                        "cpamoi", "","",
                                "cpalebon"))*/

                        // déconnecter un utilisateur
                        //user.logOutUser(auth)

                        // ajouter un post
                        /*var userId:String = ""
                        chgUser.findUser("clement.charabot@gmail.com") { _, id ->
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
                        /*chgUser.findUser("") { _, id ->
                            var postIds:List<String> = emptyList()

                            post.listPost(id) { ids ->
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
                        }*/

                        // pour ajouter un commentaire
                        /*var userId:String = ""
                        chgUser.findUser("clement.charabot@gmail.com") { _, id ->
                            if (id != null) {

                            } else {

                            }
                        }*/

                    } else {
                        // rediriger vers l'activité d'authent
                        Greeting("Android2")

                        // créer un nouvel utilisateur
                        /*user.createUser(auth,
                            User("clement.charabot@gmail.com",
                                "clement", "charabot",
                               "clui", "", "", "cpalebon")
                        )*/

                        // connecter un utilisateur
                        user.logInUser(auth, "clement.charabot@gmail.com", "cpalebon")
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}