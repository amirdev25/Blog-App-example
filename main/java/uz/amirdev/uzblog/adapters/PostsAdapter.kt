package uz.amirdev.uzblog.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import uz.amirdev.uzblog.R
import uz.amirdev.uzblog.databinding.LayoutPostsBinding
import uz.amirdev.uzblog.models.posts.PostData
import uz.amirdev.uzblog.models.user.Data

class PostsAdapter(private val postsList: List<PostData>) :
    RecyclerView.Adapter<PostsAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.layout_posts, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        LayoutPostsBinding.bind(holder.itemView).apply {
            tvPostTitle.text = postsList[position].text
            Picasso.get().load(postsList[position].image).into(imgPost)
            tvPostDate.text = postsList[position].publishDate
            countLikes.text = postsList[position].likes.toString()
        }
    }

    override fun getItemCount(): Int = postsList.size
}