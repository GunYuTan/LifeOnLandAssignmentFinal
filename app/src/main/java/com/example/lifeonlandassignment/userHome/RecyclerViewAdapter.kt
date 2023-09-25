package com.example.lifeonlandassignment.userHome

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.lifeonlandassignment.databinding.ItemsLayoutBinding

// Move this class definition outside of your ViewHolder
data class MyItem(
    val eventId: Int?,
    val imageResource: Int,
    val eventName: String,
    val eventDetail: String,
    val startDate: String,
    val startDateDetail: String,
    val endDate: String,
    val endDateDetail: String,
    val donationAmount: String,
    val donationAmountDetail: String,
    val imageResourceEditor: Int,
    val imageResourceDelete: Int
)

class RecyclerViewAdapter(private val itemList: List<MyItem>) : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemsLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: MyItem) {
            binding.textInvisibleEventId.text = item.eventId.toString()
            binding.itemImageView.setImageResource(item.imageResource)
            binding.itemEventNameView.text = item.eventName
            binding.eventNameDetail.text = item.eventDetail
            binding.itemStartDateView.text = item.startDate
            binding.startDateDetail.text = item.startDateDetail
            binding.itemEndDateView.text = item.endDate
            binding.endDateDetail.text = item.endDateDetail
            binding.itemDonationAmount.text = item.donationAmount
            binding.donationAmountDetail.text = item.donationAmountDetail
            binding.btnEditor.setImageResource(item.imageResourceEditor)
            binding.btnDelete.setImageResource(item.imageResourceDelete)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemsLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(itemList[position])
    }

    override fun getItemCount(): Int = itemList.size
}