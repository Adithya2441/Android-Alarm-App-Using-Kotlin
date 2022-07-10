package com.example.alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.Ringtone
import android.media.RingtoneManager
import android.os.Handler
import android.os.Vibrator
import android.widget.Button
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import androidx.appcompat.app.AppCompatActivity
import com.example.alarm.MainActivity

open class AlarmReceiver:BroadcastReceiver(){

    override fun onReceive(context: Context?, intent: Intent?) {
        val vibrator = context!!.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        vibrator.vibrate(4000)
        Toast.makeText(context,"Alarm ! Wake Up! Wake Up!",Toast.LENGTH_SHORT).show()

        var alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)

        if (alarmUri == null){
            alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        }
        val ringtone: Ringtone = RingtoneManager.getRingtone(context,alarmUri)
        ringtone.play()
        off(ringtone)
    }
        private fun off(ringtone :Ringtone){
        Handler().postDelayed({
            ringtone.stop()
        }, 20000)

    }

}