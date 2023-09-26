package com.example.lifeonlandassignment.userHome

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.lifeonlandassignment.Global
import com.example.lifeonlandassignment.R
import com.example.lifeonlandassignment.databinding.ItemsLayoutEventBinding
import com.example.lifeonlandassignment.manage.EventDataViewModel
import com.example.lifeonlandassignment.manage.UpdateEventFragment

// Move this class definition outside of your ViewHolder
data class MyEventItem(
    val EventID: Int?,
    val imageResource: Int,
    val EventName: String,
    val EventNameDetail: String,
    val EventStartDate: String,
    val EventStartDateDetail: String,
    val EventEndDate: String,
    val EventEndDateDetail: String,
    val EventTotalDonation: String,
    val EventTotalDonationDetail: String,
    val imageResourceEditor: Int,
    val imageResourceDelete: Int
)

class RecyclerViewEventAdapter(private val itemList: List<MyEventItem>, private val eventDataViewModel: EventDataViewModel) : RecyclerView.Adapter<RecyclerViewEventAdapter.ViewEventHolder>() {
    inner class ViewEventHolder(private val binding: ItemsLayoutEventBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: MyEventItem) {
            binding.invisibleEventID.text = item.EventID.toString()
            binding.itemImageView.setImageResource(item.imageResource)
            binding.itemEventName.text = item.EventName
            binding.itemEventNameDetail.text = item.EventNameDetail
            binding.itemStartDate.text = item.EventStartDate
            binding.itemStartDateDetail.text = item.EventStartDateDetail
            binding.itemEndDate.text = item.EventEndDate
            binding.itemEndDateDetail.text = item.EventEndDateDetail
            binding.itemTotalDonation.text = item.EventTotalDonation
            binding.itemTotalDonationDetail.text = item.EventTotalDonationDetail
            binding.btnEditor.setOnClickListener {
                eventDataViewModel.hideAlertDialog()
                Global.editEventId = item.EventID!!
                val fragmentManager = (binding.root.context as AppCompatActivity).supportFragmentManager
                val fragmentTransaction = fragmentManager.beginTransaction()
                fragmentTransaction.replace(R.id.fragment_container, UpdateEventFragment())
                fragmentTransaction.addToBackStack(null)
                fragmentTransaction.commit() }
            binding.btnDelete.setOnClickListener {
                Global.editEventId = item.EventID!!
                eventDataViewModel.showAlertDialog()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewEventHolder {
        val binding = ItemsLayoutEventBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewEventHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewEventHolder, position: Int) {
        holder.bind(itemList[position])
    }

    override fun getItemCount(): Int = itemList.size
}