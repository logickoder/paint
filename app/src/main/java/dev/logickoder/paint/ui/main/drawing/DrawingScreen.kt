package dev.logickoder.paint.ui.main.drawing

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import dev.logickoder.paint.R
import dev.logickoder.paint.base.Arrow
import dev.logickoder.paint.base.Oval
import dev.logickoder.paint.base.Pencil
import dev.logickoder.paint.base.Rectangle
import dev.logickoder.paint.ui.base.theme.PaintTheme

@Composable
fun DrawingScreen() {
    var drawableFactory: DrawableFactory by rememberSaveable { mutableStateOf({ Pencil(it) }) }
    var colorFactory: ColorFactory by rememberSaveable { mutableStateOf({ Colors.last() }) }

    Box(modifier = Modifier.fillMaxSize()) {
        DrawingCanvas(colorFactory = colorFactory, drawableFactory = drawableFactory)
        Box(modifier = Modifier.align(BiasAlignment(0f, -0.9f))) {
            Toolbar(
                color = { color ->
                    colorFactory = { color }
                },
                tool = { imageRes ->
                    drawableFactory = { props ->
                        when (imageRes) {
                            R.drawable.ic_pencil -> Pencil(props)
                            R.drawable.ic_arrow -> Arrow(props)
                            R.drawable.ic_square -> Rectangle(props)
                            R.drawable.ic_circle -> Oval(props)
                            else -> drawableFactory(props)
                        }
                    }
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    PaintTheme {
        DrawingScreen()
    }
}