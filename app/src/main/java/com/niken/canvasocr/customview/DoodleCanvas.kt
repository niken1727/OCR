package com.niken.canvasocr.customview


import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.core.view.drawToBitmap
import kotlin.math.abs

private const val TAG = "DoodleCanvas"

class DoodleCanvas @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    var paint = Paint()
    private var latestPath = Path()
    private var mX = 0f
    private  var mY:Float = 0f
    private val TOUCH_TOLERANCE = 4f
    private lateinit var dCanvas: Canvas
    init {
        paint.color = Color.BLACK
        paint.strokeCap = Paint.Cap.ROUND
        paint.isAntiAlias = true
        paint.isDither = true
        paint.strokeWidth = 10f
        paint.style = Paint.Style.STROKE
        paint.strokeJoin = Paint.Join.ROUND

    }


    fun reset() {
        latestPath.reset()
        invalidate()
    }

    fun getCanvas(): Canvas {
        return dCanvas
    }

    fun getBitmap(): Bitmap {
        return  this.drawToBitmap()
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        var x = event?.x
        var y = event?.y
        when (event?.action) {
            //triggers when user touch the screen
            MotionEvent.ACTION_DOWN -> {
                Log.d(TAG, "onTouchEvent: ${"down"}")
                startPath(x!!, y!!)
            }
            //triggers when user lifts finger from the screen
            MotionEvent.ACTION_UP -> {
                Log.d(TAG, "onTouchEvent: ${"up"}")
                endPath(x!!, y!!)
            }
            MotionEvent.ACTION_MOVE -> {
                Log.d(TAG, "onTouchEvent: ${"move"}")
                updatePath(x!!, y!!)
            }
        }
        invalidate()
        return true
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas!!.drawPath(latestPath, paint)
        dCanvas = canvas
    }


    private fun startPath(x: Float, y: Float) {
        latestPath.moveTo(x, y)
        mX = x
        mY = y
        Log.d(TAG, "startPath: x is ${x} , y is ${y}, mx is ${mX}, my is ${mY}")
    }

    private fun updatePath(x: Float, y: Float) {
//        latestPath.quadTo(x, y, x, y)
        val dx = abs(x - mX)
        val dy = abs(y - mY)
        Log.d(TAG, "startPath: x is ${x} , y is ${y}, mx is ${mX}, my is ${mY}, dX is ${dx} , dY is ${dy}")

        if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
            latestPath.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2)
            mX = x
            mY = y
        }
    }

    private fun endPath(x: Float, y: Float) {
        latestPath.lineTo(mX, mY)
        invalidate()
    }

}