package com.example.lifeonlandassignment.userHome

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.lifeonlandassignment.databinding.ItemsLayoutDonationBinding
import com.example.lifeonlandassignment.manage.DonationDataViewModel
import kotlinx.coroutines.runBlocking

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
    val DonationAmountDetail: String,
    val DonationSendMessage: Int
)

class RecyclerViewDonationAdapter(private val itemDonationList: List<MyDonationItem>, private val donationDataViewModel: DonationDataViewModel) : RecyclerView.Adapter<RecyclerViewDonationAdapter.ViewDonationHolder>() {

    inner class ViewDonationHolder(private val binding: ItemsLayoutDonationBinding) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("IntentReset")
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
            binding.itemDonationSendMessage.setOnClickListener {
                val recipient = runBlocking { donationDataViewModel.getUserId(item.DonationUserIDDetail.toInt()) }
                val recipientEmail = recipient!!.email
                Log.i("donator id", recipientEmail)
                val subject = "Thank You for Your Donation"
                val message = "Dear " + recipient.username + ",\n" +
                        "\n" +
                        "We're thrilled to thank you for your generous donation. Your contribution directly supports our ongoing initiatives and makes a meaningful impact.\n" +
                        "\n" +
                        "We also invite you to join us for the next event to see firsthand the work your donation supports. We believe it will be a rewarding experience for you.\n" +
                        "\n" +
                        "Looking forward to your presence.\n" +
                        "\n" +
                        "Sincerely,\n" +
                        "LifeOnLand Association"

                val emailIntent = Intent(Intent.ACTION_SENDTO)
                emailIntent.setData(Uri.parse("mailto:$recipientEmail?subject=$subject&body=$message"));
                it.context.startActivity(Intent.createChooser(emailIntent, "Send Email"))
            }
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