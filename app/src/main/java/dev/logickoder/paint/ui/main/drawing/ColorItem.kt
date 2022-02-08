package dev.logickoder.paint.ui.main.drawing

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import dev.logickoder.paint.databinding.ItemColorBinding
import dev.logickoder.paint.ui.base.BaseItem
import dev.logickoder.paint.ui.base.BaseViewHolder

/**
 * @param color the color
 */
abstract class ColorItem(
    color: Int,
    private val container: CanvasContainer
) : BaseItem<Unit, ItemColorBinding>(Unit, color, color), PaintItem {

    override fun inflate(parent: ViewGroup): ItemColorBinding {
        return ItemColorBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    }

    override fun bind(holder: BaseViewHolder<*>) = with(holder.binding as ItemColorBinding) {
        root.background.setTint(itemId)
        root.setOnClickListener {
            container.updateItem(this@ColorItem)
            container.colorPalette(Action.HIDE)
        }
    }
}

class Red(container: CanvasContainer) : ColorItem(Color.rgb(251, 0, 8), container)
class Green(container: CanvasContainer) : ColorItem(Color.rgb(0, 127, 8), container)
class Blue(container: CanvasContainer) : ColorItem(Color.rgb(0, 120, 222), container)
class Black(container: CanvasContainer) : ColorItem(Color.rgb(0, 0, 0), container)

fun colors(container: CanvasContainer) = listOf(
    Red(container), Green(container), Blue(container), Black(container)
)