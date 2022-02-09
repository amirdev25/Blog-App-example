package uz.amirdev.uzblog.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import uz.amirdev.uzblog.R
import uz.amirdev.uzblog.databinding.LayoutUserBinding
import uz.amirdev.uzblog.models.user.Data


class UserAdapter(private val userList: List<Data>, private val listener: OnItemClickListener) :
    RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    interface OnItemClickListener {
        fun onClick(user: Data)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.layout_user, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        LayoutUserBinding.bind(holder.itemView).apply {
            tvUsername.text = userList[position].firstName
            Picasso.get().load(userList[position].picture).into(imgUser)
        }
        holder.itemView.setOnClickListener {
            listener.onClick(userList[position])
        }
    }

    override fun getItemCount(): Int = userList.size
}