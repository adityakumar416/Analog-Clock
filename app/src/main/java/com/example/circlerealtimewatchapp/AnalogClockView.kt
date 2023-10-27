package com.example.circlerealtimewatchapp

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import java.util.Calendar
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin

class AnalogClockView(context: Context, attrs: AttributeSet) : View(context, attrs) {

    private val circlePaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val textPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val hourHandPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val minuteHandPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val secondHandPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)

    private var centerX: Float = 0f
    private var centerY: Float = 0f
    private var radius: Float = 0f

    init {
        circlePaint.color = Color.BLACK
        circlePaint.style = Paint.Style.STROKE
        circlePaint.strokeWidth = 16f

        textPaint.color = Color.BLACK
        textPaint.textSize = 48f
        textPaint.textAlign = Paint.Align.CENTER

        hourHandPaint.color = Color.BLUE
        hourHandPaint.strokeWidth = 12f

        minuteHandPaint.color = Color.GREEN
        minuteHandPaint.strokeWidth = 8f

        secondHandPaint.color = Color.RED
        secondHandPaint.strokeWidth = 4f
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        centerX = width / 2f
        centerY = height / 2f
        radius = min(centerX, centerY) - 32f

        // Draw circle
        canvas?.drawCircle(centerX, centerY, radius, circlePaint)

        // Draw time numbers
        for (i in 1..12) {
            val numberText = i.toString()
            val angle = Math.toRadians((i * 30 - 90).toDouble())
            val x = centerX + (radius - 72f) * cos(angle).toFloat()
            val y = centerY + (radius - 72f) * sin(angle).toFloat()
            canvas?.drawText(numberText, x, y, textPaint)
        }

        // Get current time
        val calendar = Calendar.getInstance()
        val hours = calendar.get(Calendar.HOUR_OF_DAY) % 12
        val minutes = calendar.get(Calendar.MINUTE)
        val seconds = calendar.get(Calendar.SECOND)

        // Draw hour hand
        val hourAngle = Math.toRadians((hours + minutes / 60.0) * 30 - 90)
        drawHand(canvas, hourAngle, radius * 0.6f, hourHandPaint)

        // Draw minute hand
        val minuteAngle = Math.toRadians((minutes + seconds / 60.0) * 6 - 90)
        drawHand(canvas, minuteAngle, radius * 0.7f, minuteHandPaint)

        // Draw second hand
        val secondAngle = Math.toRadians((seconds * 6 - 90).toDouble())
        drawHand(canvas, secondAngle, radius * 0.8f, secondHandPaint)

        // Redraw the view every second
        postInvalidateDelayed(1000)
    }

    private fun drawHand(canvas: Canvas?, angle: Double, length: Float, paint: Paint) {
        val x = centerX + length * cos(angle).toFloat()
        val y = centerY + length * sin(angle).toFloat()
        canvas?.drawLine(centerX, centerY, x, y, paint)
    }
}

