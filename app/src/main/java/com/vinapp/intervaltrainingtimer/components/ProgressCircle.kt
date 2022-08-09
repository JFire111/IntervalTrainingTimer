package com.vinapp.intervaltrainingtimer.components

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator
import java.lang.Exception
import kotlin.math.min

class ProgressCircle(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    private var innerCircleProgress = 0
    private var outerCircleProgress = 0
    private var innerCircleSweepAngle = 0F
    private var outerCircleSweepAngle = 0F
    private val backgroundCircleStrokeWidth = 30F
    private val innerCircleStrokeWidth = 15F
    private val outerCircleStrokeWidth = 15F
    private val backgroundOval = RectF()
    private val innerOval = RectF()
    private val outerOval = RectF()

    private val animator = ValueAnimator.ofFloat(0.0F, 1.0F).apply {
        duration = 90
        interpolator = LinearInterpolator()
        addUpdateListener {
            innerCircleSweepAngle = 3.6F * (innerCircleProgress - 1F + (it.animatedValue as Float))
            outerCircleSweepAngle = 3.6F * (outerCircleProgress - 1F + (it.animatedValue as Float))
            invalidate()
        }
    }

    private val backgroundCirclePaint = Paint().apply {
        color = Color.GRAY
        style = Paint.Style.STROKE
        strokeWidth = backgroundCircleStrokeWidth
    }
    private val innerCirclePaint = Paint().apply {
        color = Color.RED
        style = Paint.Style.STROKE
        strokeWidth = innerCircleStrokeWidth
    }
    private val outerCirclePaint = Paint().apply {
        color = Color.WHITE
        style = Paint.Style.STROKE
        strokeWidth = outerCircleStrokeWidth
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val desiredWidth = suggestedMinimumWidth + paddingLeft + paddingRight
        val desiredHeight = suggestedMinimumWidth + paddingTop + paddingBottom
        val size = min(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(size, size)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        backgroundOval.set(0F + backgroundCircleStrokeWidth / 2, 0F + backgroundCircleStrokeWidth / 2, width - backgroundCircleStrokeWidth / 2, width - backgroundCircleStrokeWidth / 2)
        innerOval.set(0F + backgroundCircleStrokeWidth / 2 + outerCircleStrokeWidth / 2, 0F + backgroundCircleStrokeWidth / 2 + outerCircleStrokeWidth / 2, width - backgroundCircleStrokeWidth / 2 - outerCircleStrokeWidth / 2, width - backgroundCircleStrokeWidth / 2 - outerCircleStrokeWidth / 2)
        outerOval.set(0F + outerCircleStrokeWidth / 2, 0F + outerCircleStrokeWidth / 2, width - outerCircleStrokeWidth / 2, width - outerCircleStrokeWidth / 2)
        canvas?.drawArc(backgroundOval, 270F, 360F, false, backgroundCirclePaint)
        canvas?.drawArc(innerOval, 270F, innerCircleSweepAngle, false, innerCirclePaint)
        canvas?.drawArc(outerOval, 270F, outerCircleSweepAngle, false, outerCirclePaint)
    }

    fun setInnerProgressInPercent(progress: Int) {
        if (progress > 100) {
            throw Exception("Progress can't be larger than 100")
        } else {
            if (this.innerCircleProgress != progress) {
                this.innerCircleProgress = progress
                animator.cancel()
                animator.start()
            }
        }
    }

    fun setOuterInnerProgressInPercent(progress: Int) {
        if (progress > 100) {
            throw Exception("Progress can't be larger than 100")
        } else {
            if (this.outerCircleProgress != progress) {
                this.outerCircleProgress = progress
            }
        }
    }
}