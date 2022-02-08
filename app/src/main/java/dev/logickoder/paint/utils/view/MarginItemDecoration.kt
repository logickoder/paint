package dev.logickoder.paint.utils.view

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * Adds spacing between items in a recycler view
 *
 * @param space the space between each item in pixels
 */
class MarginItemDecoration(
    private val space: Int
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ): Unit = with(outRect) {
        left = space
        right = space
    }
}