package com.elnaggar.stepcounter

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.tbruyelle.rxpermissions3.RxPermissions

class MainActivity : AppCompatActivity() {
    private lateinit var stepsCountingReceiver: StepsCountingReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        RxPermissions(this).request(Manifest.permission.ACTIVITY_RECOGNITION)
            .subscribe { isGranted ->
                Log.d("TAG", "Is ACTIVITY_RECOGNITION permission granted: $isGranted")
            }


        startService(Intent(this, StepCountingService::class.java))
        stepsCountingReceiver = StepsCountingReceiver()

    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(stepsCountingReceiver)
    }

    override fun onStart() {
        super.onStart()
        registerReceiver(stepsCountingReceiver, IntentFilter(BROADCAST_ACTION))
    }

    fun onNewSteps(steps: Int) {
        findViewById<TextView>(R.id.stepsTextView).setText(
            buildString {
                append("Steps: ")
                append(steps)
            }
        )

    }

    private inner class StepsCountingReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val values = intent?.getIntExtra(SENSOR_VALUE_KEY, 0)
            if (values != null) {
                onNewSteps(values)
            }
        }

    }

}