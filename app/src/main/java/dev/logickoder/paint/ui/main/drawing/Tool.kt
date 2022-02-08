package dev.logickoder.paint.ui.main.drawing

import android.graphics.PorterDuff
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import dev.logickoder.paint.R
import dev.logickoder.paint.databinding.ItemToolBinding
import dev.logickoder.paint.ui.base.BaseItem
import dev.logickoder.paint.ui.base.BaseViewHolder

/**
 * A tool that can be shown in the toolbar
 *
 * @param icon the id of the image for the tool
 */
abstract class Tool(
    icon: Int,
    protected val container: CanvasContainer
) : BaseItem<Unit, ItemToolBinding>(Unit, icon, icon), PaintItem {

    var isSelected = false

    open fun onClick() {}

    override fun inflate(parent: ViewGroup): ItemToolBinding {
        return ItemToolBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    }

    override fun bind(holder: BaseViewHolder<*>) = with(holder.binding as ItemToolBinding) {
        toolImage.setImageResource(this@Tool.itemId)
        if (isSelected) {
            toolImage.setColorFilter(
                ContextCompat.getColor(root.context, R.color.item_selected),
                PorterDuff.Mode.MULTIPLY
            )
            root.background.setTint(
                ContextCompat.getColor(
                    root.context,
                    R.color.item_selected_background
                )
            )
        }
        root.setOnClickListener {
            container.updateItem(this@Tool)
            container.colorPalette(Action.HIDE)
            onClick()
        }
    }

}

class Pencil(container: CanvasContainer) : Tool(R.drawable.ic_pencil, container)

class Arrow(container: CanvasContainer) : Tool(R.drawable.ic_arrow, container)

class Rectangle(container: CanvasContainer) : Tool(R.drawable.ic_square, container)

class Oval(container: CanvasContainer) : Tool(R.drawable.ic_circle, container)

class ColorPalette(container: CanvasContainer) : Tool(R.drawable.ic_color_palette, container) {
    override fun onClick() {
        container.colorPalette(Action.SHOW)
    }
}

internal fun tools(container: CanvasContainer) = listOf(
    Pencil(container),
    Arrow(container),
    Rectangle(container),
    Oval(container),
    ColorPalette(container)
)