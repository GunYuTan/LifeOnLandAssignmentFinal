package com.example.lifeonlandassignment.userHome

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.lifeonlandassignment.databinding.ItemsLayoutNotificationBinding

// Move this class definition outside of your ViewHolder
data class MyNotificationItem(
    val imageResource: Int,
    val notificationTitle: String,
    val notificationDetail: String
)

class RecyclerViewNotificationAdapter(private val itemList: List<MyNotificationItem>) : RecyclerView.Adapter<RecyclerViewNotificationAdapter.ViewNotificationHolder>() {

    inner class ViewNotificationHolder(private val binding: ItemsLayoutNotificationBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: MyNotificationItem) {
            binding.itemImageView.setImageResource(item.imageResource)
            binding.itemNotificationTitleView.text = item.notificationTitle
            binding.txtNotificationDetail.text = item.notificationDetail
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewNotificationHolder {
        val binding = ItemsLayoutNotificationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewNotificationHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewNotificationHolder, position: Int) {
        holder.bind(itemList[position])
    }

    override fun getItemCount(): Int = itemList.size
}