package com.example.solutionchallenge.user

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.*
import com.bumptech.glide.Glide
import com.example.solutionchallenge.api.Item
import com.example.solutionchallenge.databinding.UserItemBinding

class UserAdapter : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder(
            UserItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    private val differCallback = object : DiffUtil.ItemCallback<Item>() {
        override fun areItemsTheSame(old: Item, new: Item): Boolean =
            old == new

        override fun areContentsTheSame(old: Item, new: Item): Boolean =
            old == new
    }

    val differ = AsyncListDiffer(this,differCallback)

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val item = differ.currentList[position]
        holder.bind(item)
    }


    inner class UserViewHolder(private val binding: UserItemBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Item){
            binding.apply {
                Glide.with(root).load(item.avatarUrl).into(userImage)
                userTitle.text = item.login
                userType.text = item.type
            }
        }
    }

}