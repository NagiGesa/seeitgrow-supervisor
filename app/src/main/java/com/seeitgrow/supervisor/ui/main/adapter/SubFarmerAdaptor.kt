package com.seeitgrow.supervisor.ui.main.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.seeitgrow.supervisor.DataBase.Model.FarmerDetails
import com.seeitgrow.supervisor.databinding.FarmerListViewBinding
import com.seeitgrow.supervisor.ui.main.view.SiteDetails.SiteList_Activity
import com.seeitgrow.supervisor.utils.AppUtils
import com.seeitgrow.supervisor.utils.NetworkUtil

class SubFarmerAdaptor(
    private val users: List<FarmerDetails>,
    private val mContext: Context
) :
    RecyclerView.Adapter<SubFarmerAdaptor.DataViewHolder>() {
    lateinit var binding: FarmerListViewBinding

    class DataViewHolder(private val binding: FarmerListViewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {

            }
        }

        fun bind(currentNote: FarmerDetails) {
            binding.apply {
                txtChampionName.text = currentNote.FirstName
                txtPendingCount.text = "Pending Count:   ${currentNote.TotalPendingImage}"

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

        binding.imgFrwd.setOnClickListener {
            if (!NetworkUtil.getConnectivityStatusString(mContext)
                    .equals("Not connected to Internet")
            ) {
                val details = users[holder.adapterPosition]
                val intent = Intent(mContext, SiteList_Activity::class.java)
                intent.putExtra(AppUtils.FARMER_ID, details.FarmerID)
                intent.putExtra(AppUtils.FARMER_NAME, details.FirstName)
                mContext.startActivity(intent)
//                val action =
//                    SubFarmerList_FragmentDirections.actionSubFarmerFragmentToSiteListFragment(
//                        details.FarmerID!!,details.FirstName!!
//                    )
//
//                Navigation.findNavController(binding.root).navigate(action)
            } else {
                AppUtils.showMessage(mContext, "Not connected to Internet")
            }
        }
    }

}
