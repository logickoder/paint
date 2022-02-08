package dev.logickoder.paint.base

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import kotlin.math.PI
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin

class Pencil(properties: DrawableProperties) : Drawable(properties) {
    override var end: Point = start.x cord start.y
        set(value) {
            stroke.path.quadTo(end.x, end.y, (end.x + value.x) / 2, (end.y + value.y) / 2)
            stroke.path.lineTo(value.x, value.y)
            field = value
        }

    init {
        stroke.path.moveTo(start.x, start.y)
    }

    override fun draw(canvas: Canvas, paint: Paint) {
        // iterate over each stroke and draw it on the canvas
        paint.apply {
            strokeWidth = stroke.strokeWidth
            color = this@Pencil.color
        }
        canvas.drawPath(stroke.path, paint)
    }
}

open class Line(properties: DrawableProperties) : Drawable(properties) {

    override fun draw(canvas: Canvas, paint: Paint) {
        canvas.drawLine(start.x, start.y, end.x, end.y, paint.apply {
            strokeWidth = stroke.strokeWidth
            color = this@Line.color
        })
    }
}

class Arrow(properties: DrawableProperties) : Line(properties) {

    override fun draw(canvas: Canvas, paint: Paint) {
        // calls the super method to draw the line
        super.draw(canvas, paint)
        // draw the arrow head
        val (radius, angle) = 30f to 60f
        val angleInRad = PI * angle / 180f
        val lineAngle = atan2(end.y - start.y, end.x - start.x)
        val path = Path().apply {
            fillType = Path.FillType.EVEN_ODD
            moveTo(end.x, end.y)
            lineTo(
                (end.x - radius * cos(lineAngle - (angleInRad / 2))).toFloat(),
                (end.y - radius * sin(lineAngle - (angleInRad / 2))).toFloat(),
            )
            moveTo(end.x, end.y)
            lineTo(
                (end.x - radius * cos(lineAngle + (angleInRad / 2))).toFloat(),
                (end.y - radius * sin(lineAngle + (angleInRad / 2))).toFloat(),
            )
        }
        canvas.drawPath(path, paint)
    }
}

class Rectangle(properties: DrawableProperties) : Drawable(properties) {

    override fun draw(canvas: Canvas, paint: Paint) {
        canvas.drawRect(start.x, start.y, end.x, end.y, paint.apply {
            strokeWidth = stroke.strokeWidth
            color = this@Rectangle.color
        })
    }
}

class Oval(properties: DrawableProperties) : Drawable(properties) {

    override fun draw(canvas: Canvas, paint: Paint) {
        canvas.drawOval(start.x, start.y, end.x, end.y, paint.apply {
            strokeWidth = stroke.strokeWidth
            color = this@Oval.color
        })
    }
}