package com.seeitgrow.supervisor.ui.main.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.seeitgrow.supervisor.DataBase.Model.FarmerDetails
import com.seeitgrow.supervisor.R
import com.seeitgrow.supervisor.databinding.FarmerListViewBinding
import com.seeitgrow.supervisor.ui.NavTest.ChampionList_FragmentDirections
import com.seeitgrow.supervisor.ui.main.adapter.ChampionAdaptor.DataViewHolder
import com.seeitgrow.supervisor.ui.main.view.SubFarmerList
import com.seeitgrow.supervisor.utils.AppUtils.CHAMPION_ID


class ChampionAdaptor(private val users: List<FarmerDetails>, private val mContext: Context) :
    RecyclerView.Adapter<DataViewHolder>() {
    lateinit var binding: FarmerListViewBinding

    class DataViewHolder(private val binding: FarmerListViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {

            }
        }

        fun bind(currentNote: FarmerDetails) {
            binding.apply {
                binding.txtChampionName.text = currentNote.FirstName

                binding.txtPendingCount.text = "Pending Count: ${currentNote.TotalPendingImage}"

                binding.imgFrwd.setOnClickListener {
//                    val intent = Intent(root.context, SubFarmerList::class.java)
//                    intent.putExtra(CHAMPION_ID, currentNote.FarmerID)
//                    root.context.startActivity(intent)

                    val action =
                        ChampionList_FragmentDirections.actionChampionListNavToSubFarmerFragment(
                            currentNote.FarmerID!!
                        )
//        navController.navigate(R.id.action_championList_Nav_to_subFarmer_Fragment,action)
                    Navigation.findNavController(binding.root).navigate(action)
                }

                binding.imgFarmerImage.load(let {
                    "http://52.183.134.41/PBINSURANCE/Pictures/2020/HR/Rabi2020/Sites/L51114F00964C05S00946Ip.jpg"
                }) {
                    crossfade(true)
                    placeholder(R.drawable.ic_account)
                    transformations(CircleCropTransformation())
                }
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        binding = FarmerListViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DataViewHolder(binding)
    }

    override fun getItemCount(): Int = users.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {

        val currentNote = users[position]

        if (currentNote != null && position != RecyclerView.NO_POSITION) {
            holder.bind(currentNote)
        }
    }


}