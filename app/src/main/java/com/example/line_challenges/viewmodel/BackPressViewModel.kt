package com.example.line_challenges.viewmodel


import android.app.Activity
import android.widget.Toast
import androidx.lifecycle.ViewModel

class BackPressViewModel : ViewModel()
{
    private var backKeyPressedTime: Long = 0
    private val TIME_INTERVAL: Long = 2000
    private var toast: Toast? = null
    lateinit var activity : Activity


    fun onBackPressed(activity : Activity)
    {

        this.activity=activity

        if (System.currentTimeMillis() > backKeyPressedTime + TIME_INTERVAL)
        {

            backKeyPressedTime = System.currentTimeMillis()
            showMessage()
        }
        else
        {
            toast!!.cancel()
            activity.moveTaskToBack(true)
            activity.finishAndRemoveTask()
            android.os.Process.killProcess(android.os.Process.myPid())
        }
    }

    fun showMessage() {
        toast = Toast.makeText(activity, "'뒤로' 버튼을 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT)
        toast!!.show()
    }
}