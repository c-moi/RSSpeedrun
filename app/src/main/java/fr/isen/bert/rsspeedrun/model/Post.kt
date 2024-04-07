package fr.isen.bert.rsspeedrun.model

data class Post (
    val id:String,
    val title:String,
    val picture:String,
    val game:String,
    val description:String,
    val date: String,
    val userId:String,
    val selected:Boolean
)