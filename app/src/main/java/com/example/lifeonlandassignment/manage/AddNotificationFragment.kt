package com.example.lifeonlandassignment.manage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.lifeonlandassignment.databinding.ReportDataScreenBinding

class AddNotificationFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = ReportDataScreenBinding.inflate(inflater, container, false)
        return view
    }
}