package com.example.lifeonlandassignment.userHome

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.lifeonlandassignment.databinding.ItemsLayoutClientBinding

// Move this class definition outside of your ViewHolder
data class MyClientItem(
    val imageResource: Int,
    val ClientID: String,
    val ClientIDDetail: String,
    val ClientUsername: String,
    val ClientUsernameDetail: String,
    val ClientEmail: String,
    val ClientEmailDetail: String,
    val ClientPhone: String,
    val ClientPhoneDetail: String
)

class RecyclerViewClientAdapter(private val itemClientList: List<MyClientItem>) : RecyclerView.Adapter<RecyclerViewClientAdapter.ViewClientHolder>() {

    inner class ViewClientHolder(private val binding: ItemsLayoutClientBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: MyClientItem) {
            binding.itemImageView.setImageResource(item.imageResource)
            binding.itemClientID.text = item.ClientID
            binding.itemClientIDDetail.text = item.ClientIDDetail
            binding.itemClientUsername.text = item.ClientUsername
            binding.itemClientUsernameDetail.text = item.ClientUsernameDetail
            binding.itemClientEmail.text = item.ClientEmail
            binding.itemClientEmailDetail.text = item.ClientEmailDetail
            binding.itemClientPhone.text = item.ClientPhone
            binding.itemClientPhoneDetail.text = item.ClientPhoneDetail
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewClientHolder {
        val binding = ItemsLayoutClientBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewClientHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewClientHolder, position: Int) {
        holder.bind(itemClientList[position])
    }

    override fun getItemCount(): Int = itemClientList.size
}