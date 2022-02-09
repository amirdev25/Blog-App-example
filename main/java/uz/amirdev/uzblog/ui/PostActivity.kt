package uz.amirdev.uzblog.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import uz.amirdev.uzblog.R
import uz.amirdev.uzblog.adapters.PostsAdapter
import uz.amirdev.uzblog.api.Api
import uz.amirdev.uzblog.api.ApiService
import uz.amirdev.uzblog.databinding.ActivityMainBinding
import uz.amirdev.uzblog.databinding.ActivityPostBinding
import uz.amirdev.uzblog.models.posts.PostData
import uz.amirdev.uzblog.models.posts.PostModel
import uz.amirdev.uzblog.models.user.Data

class PostActivity : AppCompatActivity() {

    lateinit var user: Data
    lateinit var binding: ActivityPostBinding
    lateinit var adapterPost: PostsAdapter
    lateinit var postsList: ArrayList<PostData>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPostBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.swipeRefreshLayout.isRefreshing = true
        postsList = ArrayList()
        user = intent.getSerializableExtra("extre_data") as Data
        loadPostData()

        binding.apply {
            Picasso.get().load(user.picture).into(imgUser)
            tvUserName.text = user.firstName
        }

        binding.swipeRefreshLayout.setOnRefreshListener {
            loadPostData()
        }
    }

    private fun loadPostData() {
        ApiService.getRetrofit().create(Api::class.java).getPostsByUser(user.id)
            .enqueue(object : Callback<PostModel> {
                override fun onResponse(call: Call<PostModel>, response: Response<PostModel>) {
                    if (response.isSuccessful) {
                        val postModel = response.body()
                        postsList = postModel?.data as ArrayList<PostData>
                        setPostData()
                        binding.swipeRefreshLayout.isRefreshing = false
                    }
                }

                override fun onFailure(call: Call<PostModel>, t: Throwable) {
                    binding.swipeRefreshLayout.isRefreshing = false
                    Toast.makeText(binding.root.context, "Failed!", Toast.LENGTH_SHORT).show()
                }

            })
    }

    fun setPostData() {
        adapterPost = PostsAdapter(postsList)
        binding.recyclerView.layoutManager = LinearLayoutManager(binding.root.context)
        binding.recyclerView.adapter = adapterPost
    }
}