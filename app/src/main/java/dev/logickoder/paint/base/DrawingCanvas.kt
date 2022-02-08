package dev.logickoder.paint.base

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import dev.logickoder.paint.base.Canvas as PaintCanvas
import dev.logickoder.paint.base.Drawable as PaintDrawable


/**
 *
 */
class DrawingCanvas @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr), PaintCanvas {

    override var currentColor: Int = 0

    private var currentDrawable: PaintDrawable? = null
    private var point = 0 cord 0

    // the paint class encapsulates the color amd style information
    // about how to draw the geometrics.text and bitmaps
    private val paint: Paint = Paint().apply {
        isAntiAlias = true
        isDither = true
        color = Color.GREEN
        style = Paint.Style.STROKE
        strokeJoin = Paint.Join.ROUND
        strokeCap = Paint.Cap.ROUND
        alpha = 0xff
    }
    private val bitmapPaint = Paint(Paint.DITHER_FLAG)

    // list to store all the strokes drawn by the user on the canvas
    private val drawables = mutableListOf<PaintDrawable>()
    private var strokeWidth: Float = 10f

    private var bitmap: Bitmap? = null
    private var drawingCanvas: Canvas? = null
    private var drawableFactory: DrawableFactory? = null

    override fun init(width: Int, height: Int, color: Int, drawableFactory: DrawableFactory) {
        bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888).also {
            drawingCanvas = Canvas(it)
        }
        currentColor = color
        this.drawableFactory = drawableFactory
    }

    override fun undo() {
        // check whether there is any stroke left to remove
        drawables.removeLastOrNull()?.let { invalidate() }
    }

    override fun save() = bitmap!!

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (canvas == null) return
        // save the current state of the canvas before, to draw the background of the canvas
        canvas.save()
        drawingCanvas?.run {
            // default color of the canvas
            drawColor(Color.WHITE)
            // iterate over each stroke and draw it on the canvas
            drawables.forEach { it.draw(this, paint) }
        }
        bitmap?.let { canvas.drawBitmap(it, 0f, 0f, bitmapPaint) }
        canvas.restore()
    }

    // the below methods manages the touch response of the user on the screen

    private fun touchStart(x: Float, y: Float) {
        // save the current coordinates of the finger
        point = x cord y
        // create a new drawable and add it to the list
        if (currentDrawable == null) {
            currentDrawable = drawableFactory?.invoke(
                DrawableProperties(
                    point, point, currentColor, Stroke(currentColor, strokeWidth, Path())
                )
            )?.also { drawables += it }
        }
    }

    private fun touchMove(x: Float, y: Float) {
        val change = ((x cord y) - point).absoluteValue()

        if (change.x >= TOUCH_TOLERANCE || change.y >= TOUCH_TOLERANCE) {
            point = x cord y
            currentDrawable?.end = point
        }
    }

    private fun touchUp() {
        currentDrawable = null
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event == null) return false
        val x = event.x
        val y = event.y

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                touchStart(x, y)
                invalidate()
            }
            MotionEvent.ACTION_MOVE -> {
                touchMove(x, y)
                invalidate()
            }
            MotionEvent.ACTION_UP -> {
                touchUp()
                invalidate()
            }
        }
        return true
    }

    companion object {
        private const val TOUCH_TOLERANCE = 4
    }
}