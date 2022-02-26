package dev.logickoder.paint.ui.main.drawing

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.CombinedModifier
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import dev.logickoder.paint.R
import dev.logickoder.paint.ui.base.theme.SelectedTool
import dev.logickoder.paint.ui.base.theme.Shapes
import dev.logickoder.paint.ui.base.theme.Tool
import dev.logickoder.paint.ui.base.theme.Toolbar

val Tools = listOf(
    "Pencil" to R.drawable.ic_pencil,
    "Arrow" to R.drawable.ic_arrow,
    "Rectangle" to R.drawable.ic_square,
    "Oval" to R.drawable.ic_circle,
    "Color Palette" to R.drawable.ic_color_palette,
)

val Colors = listOf(
    Color(251, 0, 8),
    Color(0, 127, 8),
    Color(0, 120, 122),
    Color(0, 0, 0),
)

val bar = Modifier
    .clip(Shapes.large)
    .background(Toolbar)
    .padding(5.dp)

@Composable
fun Toolbar(modifier: Modifier = Modifier, color: (Color) -> Unit, tool: (Int) -> Unit) {
    // holds the currently selected tool index
    var selected by rememberSaveable { mutableStateOf(0) }
    var showColorPalette by rememberSaveable { mutableStateOf(false) }
    var lastSelected = remember { 0 }

    fun updateColor(color: Color) {
        // revert the selected item back to the tool that was selected before
        selected = lastSelected
        // hide the color palette once a color is clicked
        showColorPalette = false
        // update the color used for drawing
        color(color)
    }

    fun updateTool(tool: Pair<String, Int>, index: Int) {
        selected = index
        showColorPalette = (tool.second == R.drawable.ic_color_palette).also {
            if (!it) {
                lastSelected = index
                tool(tool.second)
            }
        }
    }

    Column(
        modifier = modifier.fillMaxWidth(0.8f),
        horizontalAlignment = Alignment.End,
        verticalArrangement = Arrangement.Top,
    ) {
        Row(
            modifier = bar.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
        ) {
            Tools.mapIndexed { index, tool ->
                Tool(
                    name = tool.first, image = tool.second,
                    selected = selected == index, onClick = { (::updateTool)(tool, index) })
            }
        }
        Spacer(
            modifier = Modifier
                .height(8.dp)
                .fillMaxWidth()
        )
        ColorPalette(isVisible = showColorPalette, onClick = ::updateColor)
    }
}

@Composable
fun Tool(name: String, @DrawableRes image: Int, selected: Boolean, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .clip(Shapes.medium)
            .background(if (selected) SelectedTool else Color.Transparent)
            .clickable { onClick() },
    ) {
        Image(
            modifier = Modifier
                .padding(8.dp)
                .size(24.dp),
            painter = painterResource(image),
            contentDescription = name,
            colorFilter = ColorFilter.tint(if (selected) Color.Black else Tool),
        )
    }
}

@Composable
fun ColorPalette(isVisible: Boolean, onClick: (Color) -> Unit) {
    Row(modifier = CombinedModifier(Modifier.alpha(if (isVisible) 1f else 0f), bar)) {
        Colors.forEach { Color(it) { onClick(it) } }
    }
}

@Composable
fun Color(color: Color, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .padding(4.dp)
            .clip(Shapes.medium)
            .background(color)
            .size(32.dp)
            .clickable { onClick() },
    )
}