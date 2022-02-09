package uz.amirdev.uzblog.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import uz.amirdev.uzblog.adapters.PostsAdapter
import uz.amirdev.uzblog.adapters.UserAdapter
import uz.amirdev.uzblog.api.Api
import uz.amirdev.uzblog.api.ApiService
import uz.amirdev.uzblog.databinding.ActivityMainBinding
import uz.amirdev.uzblog.models.posts.PostData
import uz.amirdev.uzblog.models.posts.PostModel
import uz.amirdev.uzblog.models.user.Data
import uz.amirdev.uzblog.models.user.UserModel
import java.io.Serializable

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var usersList: ArrayList<Data>
    private lateinit var postsList: ArrayList<PostData>
    private lateinit var userAdapter: UserAdapter
    private lateinit var postAdapter: PostsAdapter
    private var userModel: UserModel? = null
    private var postModel: PostModel? = null
    var userLaoded = false
    var postLaoded = false


    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.swipeRefreshLayout.isRefreshing = true
        binding.swipeRefreshLayout.setOnRefreshListener {
            loadUsers()
            loadPosts()
        }

        usersList = ArrayList()
        postsList = ArrayList()
        setUserData()
        setPostData()
        loadUsers()
        loadPosts()
        binding.recyclerPosts.layoutManager = LinearLayoutManager(this)
        binding.recyclerPosts.adapter = PostsAdapter(postsList)

    }

    private fun loadPosts() {
        ApiService.getRetrofit().create(Api::class.java).getPosts()
            .enqueue(object : Callback<PostModel> {
                override fun onResponse(call: Call<PostModel>, response: Response<PostModel>) {
                    if (response.isSuccessful) {
                        postModel = response.body()
                        postsList = postModel?.data as ArrayList<PostData>
                        setPostData()
                        postLaoded = true
                        if (postLaoded && userLaoded)
                            binding.swipeRefreshLayout.isRefreshing = false
                    }
                }

                override fun onFailure(call: Call<PostModel>, t: Throwable) {
                    Toast.makeText(binding.root.context, "Failed :(", Toast.LENGTH_SHORT).show()
                    binding.swipeRefreshLayout.isRefreshing = false
                }
            })
    }

    private fun loadUsers() {
        ApiService.getRetrofit().create(Api::class.java).getUsers()
            .enqueue(object : Callback<UserModel> {
                @SuppressLint("NotifyDataSetChanged")
                override fun onResponse(call: Call<UserModel>, response: Response<UserModel>) {
                    if (response.isSuccessful) {
                        userModel = response.body()
                        usersList = userModel?.data as ArrayList<Data>
                        setUserData()
                        userLaoded = true
                        if (postLaoded && userLaoded)
                            binding.swipeRefreshLayout.isRefreshing = false
                    }
                }

                override fun onFailure(call: Call<UserModel>, t: Throwable) {
                    Toast.makeText(binding.root.context, "Failed!", Toast.LENGTH_SHORT).show()
                }

            })
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setPostData() {
        postAdapter = PostsAdapter(postsList)
        binding.recyclerPosts.adapter = postAdapter
    }

    private fun setUserData() {
        userAdapter = UserAdapter(usersList, object : UserAdapter.OnItemClickListener {
            override fun onClick(user: Data) {
                val intent = Intent(binding.root.context, PostActivity::class.java)
                intent.putExtra("extre_data", user)
                startActivity(intent)
            }
        })
        binding.recyclerUsers.layoutManager =
            LinearLayoutManager(binding.root.context, LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerUsers.adapter = userAdapter
    }


    //ekranga teginishni sezadi

//    override fun onUserInteraction() {
//        super.onUserInteraction()
//        Toast.makeText(binding.root.context, "ok", Toast.LENGTH_SHORT).show()
//    }
}