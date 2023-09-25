package com.example.lifeonlandassignment.userHome

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.lifeonlandassignment.databinding.ItemsLayoutDonationBinding

// Move this class definition outside of your ViewHolder
data class MyDonationItem(
    val imageResource: Int,
    val DonationID: String,
    val DonationIDDetail: String,
    val DonationUserID: String,
    val DonationUserIDDetail: String,
    val DonationEventID: String,
    val DonationEventIDDetail: String,
    val DonationAmount: String,
    val DonationAmountDetail: String
)

class RecyclerViewDonationAdapter(private val itemDonationList: List<MyDonationItem>) : RecyclerView.Adapter<RecyclerViewDonationAdapter.ViewDonationHolder>() {

    inner class ViewDonationHolder(private val binding: ItemsLayoutDonationBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: MyDonationItem) {
            binding.itemImageView.setImageResource(item.imageResource)
            binding.itemDonationID.text = item.DonationID
            binding.itemDonationIDDetail.text = item.DonationIDDetail
            binding.itemDonationUserID.text = item.DonationUserID
            binding.itemDonationUserIDDetail.text = item.DonationUserIDDetail
            binding.itemDonationEventID.text = item.DonationEventID
            binding.itemDonationEventIDDetail.text = item.DonationEventIDDetail
            binding.itemDonationAmount.text = item.DonationAmount
            binding.itemDonationAmountDetail.text = item.DonationAmountDetail
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewDonationHolder {
        val binding = ItemsLayoutDonationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewDonationHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewDonationHolder, position: Int) {
        holder.bind(itemDonationList[position])
    }

    override fun getItemCount(): Int = itemDonationList.size
}