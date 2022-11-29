package com.example.gjb_idk2


import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import com.firebase.ui.database.FirebaseListAdapter
import com.firebase.ui.database.FirebaseListOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.task.*
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

    var email:String=""

    var adapter: FirebaseListAdapter<TaskModel>? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getDate()
        email = intent.getStringExtra("email").toString()

        checkForUser()
        setUpLogout()
        handleAddTask()
        getAndDisplayTasks()
    }

    override fun onStart() {
        var mAuth = FirebaseAuth.getInstance()
        var currentUser: FirebaseUser? = mAuth.currentUser
        if(currentUser!=null) {
            super.onStart()
            adapter!!.startListening()
        }
    }


    fun checkForUser(){
        var mAuth = FirebaseAuth.getInstance()
        var currentUser: FirebaseUser? = mAuth.currentUser
        if(currentUser==null){
            startActivity(Intent(this, SignUpActivity::class.java))
            finish()
        } else {
            email = currentUser.email.toString()
        }
    }

    fun handleAddTask(){
        exit.setOnClickListener {
            clearPopUp()
        }
        add_task_button.setOnClickListener{
            list_of_tasks.visibility=View.GONE
            add_task_layout.visibility=View.VISIBLE
            changeColors(true)
        }
        add_task.setOnClickListener{

            var task = input_task.text.toString()
            var date = text_time.text.toString()

            if(addtoFirebase(task, date)) {

                input_task.text.clear()
                text_time.text = " "
                clearPopUp()
            }
        }
    }

    fun clearPopUp(){
        add_task_layout.visibility = View.GONE
        list_of_tasks.visibility = View.VISIBLE
        changeColors(false)
    }

    fun changeColors(flag:Boolean){
        if(flag){
            main_layout.background = this.getDrawable(R.drawable.altbg)
        } else {
            main_layout.background = this.getDrawable(R.drawable.main1)
        }
    }


    fun getAndDisplayTasks(){
        val options: FirebaseListOptions<TaskModel> = FirebaseListOptions.Builder<TaskModel>()
            .setQuery(FirebaseDatabase.getInstance().reference, TaskModel::class.java)
            .setLayout(R.layout.task).build()

        val listOfMessages = findViewById<ListView>(R.id.list_of_tasks)

        adapter = object : FirebaseListAdapter<TaskModel>(options) {
            override fun populateView(
                @NonNull v: View,
                @NonNull model: TaskModel,
                position: Int
            ) {
                if(email==model.getTaskEmail()) {
                    Log.d("idk3", model.getTaskTask()+" "+model.getTaskDate()+" "+email)
                    val task_task1 = v.findViewById(R.id.task_task) as TextView
                    val task_date1 = v.findViewById(R.id.task_date) as TextView

                    task_task1.text = model.getTaskTask()
                    task_date1.text = model.getTaskDate()
                }
            }
        }

        listOfMessages.adapter = adapter
    }


    fun addtoFirebase(task:String, date:String): Boolean {
        Log.d("idk3", task+" "+date+" "+email)
//        var tmod = TaskModel().TaskModel(task, date, email)
        if (task.equals("") || task.equals(" ")) {
            Toast.makeText(
                getApplicationContext(),
                "Task Needs Content",
                Toast.LENGTH_LONG)
                .show();
        } else if(date.equals("") || date.equals(" ")) {
            Toast.makeText(
                getApplicationContext(),
                "Date Needs Content",
                Toast.LENGTH_LONG)
                .show()
        } else {
            FirebaseDatabase.getInstance()
                .reference
                .push()
                .setValue(TaskModel(task, date, email))
            return true
        }
        return false
    }

    fun setUpLogout(){
        logout_button.setOnClickListener{
            FirebaseAuth.getInstance().signOut()
            adapter!!.stopListening()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
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

    override fun onTimeSet(p0: TimePicker?, p1: Int, p2: Int) {
        hour2 = p1
        minute2=p2
        var minute3 = minute2.toString()
        if(minute3.length==1){
            minute3="0"+minute3
        }

        var textTime = findViewById<TextView>(R.id.text_time)
        textTime.text="$day2-$month2-$year2 $hour2:$minute3"
    }

}