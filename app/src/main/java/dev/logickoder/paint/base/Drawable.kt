package dev.logickoder.paint.base

import android.graphics.Canvas
import android.graphics.Paint
import kotlin.math.absoluteValue

/**
 * Specifies that the class can draw something
 *
 * @property start the point where the drawable starts from on the [DrawingCanvas]
 * @property end the point where the drawable ends on the [DrawingCanvas]
 * @property color the color of the drawable, if it is [filled]
 * @property stroke the stroke of the drawable
 * @property filled is the drawable filled with a color
 * @property height the height of the drawable
 * @property width the width of the drawable
 */
abstract class Drawable(
    val start: Point,
    open var end: Point,
    val color: Int,
    val stroke: Stroke,
    val filled: Boolean = false
) {

    constructor(props: DrawableProperties) : this(
        props.start, props.end, props.color, props.stroke, props.filled
    )

    val width: Float get() = (start - end).x.absoluteValue

    val height: Float get() = (start - end).y.absoluteValue

    /**
     * Draws the drawable on the canvas
     *
     * @param canvas the object used to draw the drawable
     * @param paint the styling to apply to the drawable
     * */
    abstract fun draw(canvas: Canvas, paint: Paint)

    override fun toString() = "${this::class.simpleName} from start: $start to end: $end"
}

/**
 *
 * @property start the point where the drawable starts from on the [DrawingCanvas]
 * @property end the point where the drawable ends on the [DrawingCanvas]
 * @property color the color of the stroke or fill of the drawable
 * @property stroke the stroke of the drawable
 * @property filled is the drawable filled with a color
 */
data class DrawableProperties(
    val start: Point,
    val end: Point,
    val color: Int,
    val stroke: Stroke,
    val filled: Boolean = false
)