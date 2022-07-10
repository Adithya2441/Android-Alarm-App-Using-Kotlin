package com.example.alarm


import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TimePicker
import android.widget.Toast
import android.widget.ToggleButton
import androidx.core.content.IntentCompat
import androidx.core.content.getSystemService
import java.util.*
import kotlinx.android.synthetic.main.activity_main.*
import android.media.AudioManager
import kotlin.time.Duration.Companion.minutes


class MainActivity : AppCompatActivity() {
    var alarmTimePicker : TimePicker? = null
    var pendingIntent :PendingIntent? = null
    var alarmManager :AlarmManager? = null
    lateinit var audioManager :AudioManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        alarmTimePicker = findViewById(R.id.timePicker)
        alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
        audioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager

        ringing_on.setOnClickListener(){
            Toast.makeText(this,"Sound ON",Toast.LENGTH_SHORT).show()
            audioManager.setStreamVolume(AudioManager.STREAM_RING,100,1)
        }
        ringing_off.setOnClickListener(){
            Toast.makeText(this,"Sound OFF",Toast.LENGTH_SHORT).show()
            audioManager.setStreamVolume(AudioManager.STREAM_RING,0,1)
        }

    }

    fun onToggleClicked(view: View) {

        var time :Long


        if((view as ToggleButton).isChecked){
            Toast.makeText(this,"Alarm ON",Toast.LENGTH_SHORT).show()

            var calendar = Calendar.getInstance()

            calendar[Calendar.HOUR_OF_DAY] = alarmTimePicker!!.currentHour
            calendar[Calendar.MINUTE] = alarmTimePicker!!.currentMinute

            val intent = Intent(this,AlarmReceiver::class.java)

            pendingIntent = PendingIntent.getBroadcast(this,0,intent,0)

            time = calendar.timeInMillis - calendar.timeInMillis % 60000

            if(System.currentTimeMillis() > time){
                time = if(Calendar.AM_PM == 0){
                    time +1000*60*60*12
                }
                else{
                    time +1000*60*60*24
                }
            }
            alarmManager!!.setRepeating(AlarmManager.RTC_WAKEUP,time,1000,pendingIntent)



        }
        else{
            alarmManager!!.cancel(pendingIntent)
            Toast.makeText(this,"Alarm OFF",Toast.LENGTH_SHORT).show()
        }

    }
}