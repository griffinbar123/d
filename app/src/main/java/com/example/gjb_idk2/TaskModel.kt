package com.example.gjb_idk2

import android.annotation.SuppressLint

public class TaskModel (){
//    var task:String=task
//    var date:String =date
//    var email:String = email
    var task:String=""
    var date:String =""
    var email:String = ""

    constructor(task:String, date:String, email:String) : this(){
        this.task=task
        this.date=date
        this.email=email
    }

//    @SuppressLint("NotConstructor")
//    fun TaskModel(task:String, date:String, email:String){
//        this.task=task
//        this.date=date
//        this.email=email
//    }

    fun getTaskTask(): String? {
        return task
    }

    fun setTaskTask(task: String) {
        this.task = task
    }

    fun getTaskDate(): String? {
        return date
    }

    fun setTaskDate(date: String) {
        this.date = date
    }

    fun getTaskEmail(): String? {
        return email
    }

    fun setTaskEmail(email: String) {
        this.email = email
    }

}