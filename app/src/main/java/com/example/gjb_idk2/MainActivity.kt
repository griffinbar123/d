package com.example.gjb_idk2

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import java.util.*

class MainActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    var day=0;
    var month=0;
    var year=0;
    var hour=0;
    var minute=0;

    var day2=0;
    var month2=0;
    var year2=0;
    var hour2=0;
    var minute2=0;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getDate()
    }

    private fun getDateTimeCalendar(){
        val cal: Calendar = Calendar.getInstance()
        day = cal.get(Calendar.DAY_OF_MONTH)
        minute = cal.get(Calendar.MINUTE)
        hour = cal.get(Calendar.HOUR)
        year = cal.get(Calendar.YEAR)
        month = cal.get(Calendar.MONTH)
    }

    fun getDate(){
        var timeButton = findViewById<Button>(R.id.time_button)
        timeButton.setOnClickListener{
            getDateTimeCalendar()
            DatePickerDialog(this, this, year, month, day).show()
        }
    }

    override fun onDateSet(p0: DatePicker?, p1: Int, p2: Int, p3: Int) {
        day2=p1
        month2=p2
        year2=p3

        getDateTimeCalendar()
        TimePickerDialog(this, this, hour, minute, true).show()
    }

    @SuppressLint("SetTextI18n")
    override fun onTimeSet(p0: TimePicker?, p1: Int, p2: Int) {
        hour2 = p1
        minute2=p2

        var textTime = findViewById<TextView>(R.id.text_time)
        textTime.text="$day2-$month2-$year2\n $hour2:$minute2"
    }

}