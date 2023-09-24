package com.example.lifeonlandassignment

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.DatePicker
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class DatePickerFragment(private val onDateSelected: (String) -> Unit) : DialogFragment(), DatePickerDialog.OnDateSetListener {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // Use the current date as the default date in the picker
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(requireContext(), this, year, month, day)

        datePickerDialog.datePicker.calendarViewShown = true
        datePickerDialog.datePicker.minDate = c.timeInMillis

        return datePickerDialog
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        // Check if the selected date is a past date
        val selectedDate = Calendar.getInstance()
        selectedDate.set(year, month, dayOfMonth)
        val currentDate = Calendar.getInstance()
        if (selectedDate.before(currentDate)) {
            Toast.makeText(requireContext(), "Past date are not allow to choose", Toast.LENGTH_SHORT).show()
            return
        }

        val formattedDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(selectedDate.time)
        onDateSelected(formattedDate)
    }
}