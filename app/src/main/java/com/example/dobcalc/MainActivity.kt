package com.example.dobcalc

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.DatePicker
import android.widget.TextView
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
   private var tvSelectedDate : TextView ? = null
    private var tvAgeMinutes : TextView ? = null

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnDatePicker : Button = findViewById(R.id.buttonDate)
        tvSelectedDate = findViewById(R.id.tvSelectedDate)
        tvAgeMinutes = findViewById(R.id.tvAgeMinutes)

        btnDatePicker.setOnClickListener {
            clickDatePicker()
        }
    }

    private fun clickDatePicker(){

        val myCalendar = Calendar.getInstance()
        val year = myCalendar.get(Calendar.YEAR)
        val month = myCalendar.get(Calendar.MONTH)
        val day = myCalendar.get(Calendar.DAY_OF_MONTH)

        //get the id of the textview from the layout
        val tvSelectedDate:TextView = findViewById(R.id.tvSelectedDate)
        val tvSelectedDateInMinutes:TextView = findViewById(R.id.tvAgeMinutes)

        val dpd = DatePickerDialog(this,{ _, selectedYear, selectedMonth, selectedDayOfMonth ->

            val selectedDate = "$selectedDayOfMonth/${selectedMonth+1}/$selectedYear"

            tvSelectedDate.text = selectedDate

            val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)

            val theDate = sdf.parse(selectedDate)
            //use the safe call operator with .let to avoid app crashing it theDate is null
            theDate?.let {

                val selectedDateInMinutes = theDate.time/60000

                val currentDate = sdf.parse(sdf.format(System.currentTimeMillis()))

                //use the safe call operator with .let to avoid app crashing it currentDate is null
                currentDate?.let {
                    val currentDateToMinutes = currentDate.time/60000
                    val differenceInMinutes = currentDateToMinutes - selectedDateInMinutes
                    tvSelectedDateInMinutes.text = differenceInMinutes.toString()
                }
            }
        },
            year,month,day)
        dpd.datePicker.maxDate = System.currentTimeMillis() - 86400000
        dpd.show()
    }
}