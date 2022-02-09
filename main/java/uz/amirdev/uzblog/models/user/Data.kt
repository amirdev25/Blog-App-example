package uz.amirdev.uzblog.models.user

import java.io.Serializable

data class Data(
    val firstName: String,
    val id: String,
    val lastName: String,
    val picture: String,
    val title: String
) : Serializable