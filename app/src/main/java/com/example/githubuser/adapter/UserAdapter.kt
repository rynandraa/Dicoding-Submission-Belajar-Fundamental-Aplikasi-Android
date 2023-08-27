package com.example.githubuser.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubuser.ui.detail.DetailActivity
import com.example.githubuser.ItemsItem
import com.example.githubuser.R

class UserAdapter (private val listUser: List<ItemsItem>): RecyclerView.Adapter<UserAdapter.ViewHolder>(){


    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int) =
        ViewHolder(LayoutInflater.from(viewGroup.context).inflate(R.layout.user_list, viewGroup, false))

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val getAccount = listUser[position]
        viewHolder.tvAccount.text = getAccount.login
        Glide.with(viewHolder.itemView)
            .load(getAccount.avatarUrl)
            .into(viewHolder.tvImage)

        viewHolder.itemView.setOnClickListener {
            val goDetail = Intent(viewHolder.itemView.context, DetailActivity::class.java)
            goDetail.putExtra("Data", getAccount.login)
            viewHolder.itemView.context.startActivity(goDetail)
            Toast.makeText(viewHolder.itemView.context, "Kamu memilih " + listUser[viewHolder.adapterPosition].login, Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount() = listUser.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvImage: ImageView = view.findViewById(R.id.img_avatar)
        val tvAccount: TextView = view.findViewById(R.id.tv_name)
    }
}