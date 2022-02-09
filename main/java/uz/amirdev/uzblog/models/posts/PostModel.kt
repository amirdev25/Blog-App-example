package uz.amirdev.uzblog.models.posts

data class PostModel(
    val `data`: List<PostData>,
    val limit: Int,
    val page: Int,
    val total: Int
)