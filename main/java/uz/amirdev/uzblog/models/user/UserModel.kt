package uz.amirdev.uzblog.models.user

data class UserModel(
    val `data`: List<Data>,
    val limit: Int,
    val page: Int,
    val total: Int
)