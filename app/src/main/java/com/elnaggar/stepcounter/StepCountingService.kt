package com.elnaggar.stepcounter


import android.app.Service
import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener2
import android.hardware.SensorManager
import android.os.Binder
import android.os.IBinder
import androidx.core.app.NotificationCompat


const val BROADCAST_ACTION = "STEP_SENSOR_ACTION"
const val SENSOR_VALUE_KEY = "step_sensor_value"

class StepCountingService : Service(), SensorEventListener2 {
    override fun onBind(intent: Intent?): IBinder {
        return Binder()
    }

    override fun onCreate() {
        super.onCreate()
        val sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val stepCounterSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)
        stepCounterSensor?.let {
            sensorManager.registerListener(
                this@StepCountingService,
                it,
                SensorManager.SENSOR_DELAY_FASTEST
            )
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        var builder = NotificationCompat.Builder(this, STEP_COUNTING_CHANNEL_ID)
            .setSmallIcon(android.R.drawable.stat_notify_chat)
            .setContentTitle("Step counting")
            .setContentText("Counting steps")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        val notification = builder.build()

        startForeground(1000,notification)
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onSensorChanged(event: SensorEvent) {
        val intent = Intent(BROADCAST_ACTION)
        intent.putExtra(SENSOR_VALUE_KEY, event.values[0].toInt())
        sendBroadcast(intent)
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

    }

    override fun onFlushCompleted(sensor: Sensor?) {

    }
}