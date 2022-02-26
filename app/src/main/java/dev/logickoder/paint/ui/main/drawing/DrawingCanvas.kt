package dev.logickoder.paint.ui.main.drawing

import android.view.MotionEvent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInteropFilter
import dev.logickoder.paint.base.Drawable
import dev.logickoder.paint.base.DrawableProperties
import dev.logickoder.paint.utils.absoluteValue
import dev.logickoder.paint.utils.pos

typealias DrawableFactory = (DrawableProperties) -> Drawable
typealias ColorFactory = () -> Color

private const val TOUCH_TOLERANCE = 4
private val stroke = Stroke(
    width = 10f,
    cap = StrokeCap.Round,
    join = StrokeJoin.Round,
)

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun DrawingCanvas(
    modifier: Modifier = Modifier,
    drawableFactory: DrawableFactory,
    colorFactory: ColorFactory
) {
    val drawables = rememberSaveable { mutableListOf<Drawable>() }
    var currentOffset = remember { 0 pos 0 }
    var invalidate by remember { mutableStateOf(0) }

    fun touchStart(offset: Offset) {
        // save the current coordinates of the finger
        currentOffset = offset
        // create a new drawable and add it to the list
        drawables += drawableFactory(DrawableProperties(offset, offset, stroke, colorFactory()))
    }

    fun touchMove(offset: Offset) {
        val change = (offset - currentOffset).absoluteValue()

        if (change.x >= TOUCH_TOLERANCE || change.y >= TOUCH_TOLERANCE) {
            currentOffset = offset
            drawables.lastOrNull()?.end = offset
        }
    }

    Canvas(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
            .pointerInteropFilter {
                when (it.action) {
                    MotionEvent.ACTION_DOWN -> {
                        touchStart(it.x pos it.y)
                        invalidate++
                        true
                    }
                    MotionEvent.ACTION_MOVE -> {
                        touchMove(it.x pos it.y)
                        invalidate++
                        true
                    }
                    MotionEvent.ACTION_UP -> {
                        invalidate++
                        true
                    }
                    else -> false
                }
            }
    ) {
        invalidate.let {
            // iterate over each stroke and draw it on the canvas
            drawables.forEach { it.draw(this) }
        }
    }
}