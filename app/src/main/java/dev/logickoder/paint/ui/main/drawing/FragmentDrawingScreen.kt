package dev.logickoder.paint.ui.main.drawing

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import dev.logickoder.paint.R
import dev.logickoder.paint.base.Drawable
import dev.logickoder.paint.base.DrawableProperties
import dev.logickoder.paint.databinding.FragmentDrawingScreenBinding
import dev.logickoder.paint.ui.base.BaseFragment
import dev.logickoder.paint.ui.base.BaseListAdapter
import dev.logickoder.paint.utils.view.viewBinding

/**
 * Houses the canvas for drawing
 */
class FragmentDrawingScreen : BaseFragment(R.layout.fragment_drawing_screen), CanvasContainer {
    private lateinit var currentTool: Tool

    override val binding by viewBinding(FragmentDrawingScreenBinding::bind)

    private val tools by lazy { tools(this) }
    private val colors by lazy { colors(this) }
    private val adapter by lazy {
        BaseListAdapter().apply { submitList(tools) }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
    }

    private fun setupUI() = with(binding) {
        canvas.viewTreeObserver.addOnGlobalLayoutListener(
            object : ViewTreeObserver.OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    canvas.viewTreeObserver.removeOnGlobalLayoutListener(this)
                    canvas.init(
                        canvas.measuredWidth,
                        canvas.measuredHeight,
                        colors.last().itemId,
                        this@FragmentDrawingScreen::drawableFactory
                    )
                }
            }
        )
        toolbar.root.also {
            it.adapter = adapter
            it.layoutManager = GridLayoutManager(context, tools.size)
            updateItem(tools[0])
        }
        colorPicker.root.also {
            it.adapter = BaseListAdapter().apply { submitList(colors) }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun updateItem(item: PaintItem) {
        when (item) {
            is Tool -> {
                currentTool = item
                tools.forEach { it.isSelected = false }
                item.isSelected = true
                binding.toolbar.root.adapter = adapter
            }
            is ColorItem -> {
                binding.canvas.currentColor = item.itemId
            }
        }
    }

    override fun colorPalette(action: Action) {
        binding.colorPicker.root.isVisible = when (action) {
            Action.SHOW -> true
            Action.HIDE -> false
        }
    }

    private fun drawableFactory(properties: DrawableProperties): Drawable? {
        return when (currentTool) {
            is Pencil -> dev.logickoder.paint.base.Pencil(properties)
            is Arrow -> dev.logickoder.paint.base.Arrow(properties)
            is Rectangle -> dev.logickoder.paint.base.Rectangle(properties)
            is Oval -> dev.logickoder.paint.base.Oval(properties)
            else -> null
        }
    }
}