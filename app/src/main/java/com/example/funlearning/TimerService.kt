package com.example.funlearning

import android.app.Service
import android.content.Intent
import android.os.Bundle
import android.os.IBinder
import android.os.SystemClock
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_timer_service.*
import java.util.*

class TimerService : AppCompatActivity(){
  var  pauseAt:Long=0
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timer_service)
        btnstart.setOnClickListener(){
          stopwatch.base= SystemClock.elapsedRealtime()-pauseAt
          stopwatch.start()
        }
        btnpause.setOnClickListener(){
            pauseAt=SystemClock.elapsedRealtime()-stopwatch.base
            stopwatch.stop()
        }
        btnreset.setOnClickListener(){
stopwatch.base=SystemClock.elapsedRealtime()
        }
    }

}