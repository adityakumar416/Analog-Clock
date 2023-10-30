package com.example.circlerealtimewatchapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val analogClockView = findViewById<AnalogClockView>(R.id.analogClockView)
        val timeTextView = findViewById<TextView>(R.id.timeTextView)

        // Update the time every second
        val handler = Handler(Looper.getMainLooper())
        handler.post(object : Runnable {
            override fun run() {
                // Get current time
                val currentTime = getCurrentTime()
                // Update TextView
                timeTextView.text = "Time- "+currentTime
                // Redraw the analog clock
                analogClockView.invalidate()
                // Call this runnable again after 1 second
                handler.postDelayed(this, 1000)
            }
        })
    }

    private fun getCurrentTime(): String {
        val dateFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
        val currentTime = Date()
        return dateFormat.format(currentTime)
    }
}








