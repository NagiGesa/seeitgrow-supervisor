package com.seeitgrow.supervisor.ui.main.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.Placeholder
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.seeitgrow.supervisor.DataBase.Model.FarmerDetails
import com.seeitgrow.supervisor.R
import com.seeitgrow.supervisor.databinding.ItemLayoutBinding
import com.seeitgrow.supervisor.ui.main.adapter.MainAdapter.DataViewHolder


class MainAdapter(private val users: List<FarmerDetails>, mContext: Context) :
    RecyclerView.Adapter<DataViewHolder>() {
    lateinit var binding: ItemLayoutBinding

    class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        binding = ItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DataViewHolder(binding.root)
    }

    override fun getItemCount(): Int = users.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        val Far = users[position]

        binding.textViewUserName.text = Far.FirstName

        binding.imageViewAvatar.load(let {
            "http://52.183.134.41/PBINSURANCE/Pictures/2020/HR/Rabi2020/Sites/L51114F00964C05S00946Ip.jpg"
        }) {
            crossfade(true)
            placeholder(R.drawable.ronaldo)
            transformations(CircleCropTransformation())
        }
    }


}