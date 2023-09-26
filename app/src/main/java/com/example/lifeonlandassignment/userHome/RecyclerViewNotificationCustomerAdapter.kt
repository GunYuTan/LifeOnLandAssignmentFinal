package com.example.lifeonlandassignment.userHome

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.lifeonlandassignment.databinding.ItemsLayoutNotificationCustomerBinding

// Move this class definition outside of your ViewHolder
data class MyNotificationCustomerItem(
    val imageResource: Int,
    val notificationCustomerTitle: String,
    val notificationCustomerDetail: String
)

class RecyclerViewNotificationCustomerAdapter(private val itemList: List<MyNotificationCustomerItem>) : RecyclerView.Adapter<RecyclerViewNotificationCustomerAdapter.ViewNotificationCustomerHolder>() {

    inner class ViewNotificationCustomerHolder(private val binding: ItemsLayoutNotificationCustomerBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: MyNotificationCustomerItem) {
            binding.itemImageView.setImageResource(item.imageResource)
            binding.itemNotificationCustomerTitle.text = item.notificationCustomerTitle
            binding.txtNotificationCustomerDetail.text = item.notificationCustomerDetail
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewNotificationCustomerHolder {
        val binding = ItemsLayoutNotificationCustomerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewNotificationCustomerHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewNotificationCustomerHolder, position: Int) {
        holder.bind(itemList[position])
    }

    override fun getItemCount(): Int = itemList.size
}