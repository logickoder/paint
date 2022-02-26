package dev.logickoder.paint.base

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke

/**
 * Specifies that the class can draw something
 */
abstract class Drawable(
    val start: Offset,
    open var end: Offset,
    val stroke: Stroke,
    val color: Color,
) {

    constructor(props: DrawableProperties) : this(
        props.start, props.end, props.stroke, props.color,
    )

    /**
     * Draws the drawable on the canvas
     *
     * @param scope the object used to draw the drawable
     * */
    abstract fun draw(scope: DrawScope)

    override fun toString() = "${this::class.simpleName} from start: $start to end: $end"
}

data class DrawableProperties(
    val start: Offset,
    val end: Offset,
    val stroke: Stroke,
    val color: Color,
)