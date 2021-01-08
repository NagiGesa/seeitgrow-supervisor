package com.seeitgrow.supervisor.ui.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.seeitgrow.supervisor.DataBase.User
import com.seeitgrow.supervisor.databinding.ItemLayoutBinding
import com.seeitgrow.supervisor.ui.main.adapter.MainAdapter.DataViewHolder


class MainAdapter(private val users: ArrayList<User>) : RecyclerView.Adapter<DataViewHolder>() {
    lateinit var binding: ItemLayoutBinding

    class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(user: User, binding: ItemLayoutBinding) {
            itemView.apply {
                binding.textViewUserName.text = user.name
                binding.textViewUserEmail.text = user.phoneNumber
//                Glide.with(binding.imageViewAvatar.context)
//                    .load(user.avatar)
//                    .into(binding.imageViewAvatar)

                binding.imageViewAvatar.load("https://www.google.com/search?q=ronaldo+images&safe=active&sxsrf=ALeKk02vLxuk2AwomJkmYyh4joeQPPadFw:1610104452563&tbm=isch&source=iu&ictx=1&fir=2h-DQviDqXMJrM%252COpIx0nfyjErx-M%252C_&vet=1&usg=AI4_-kRjwuYj9EDdchp6ZoVB3aAw8X3fAA&sa=X&ved=2ahUKEwiwr8i_mozuAhUa7WEKHdJ1AucQ9QF6BAgTEAE&biw=1920&bih=937#imgrc=2h-DQviDqXMJrM") {
                    crossfade(true)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        binding = ItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DataViewHolder(binding.root)
    }

    override fun getItemCount(): Int = users.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {

        holder.bind(users[position], binding)
    }

    fun addUsers(users: User) {
        this.users.apply {
            clear()
            add(users)
        }

    }
}