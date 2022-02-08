package dev.logickoder.paint.ui.main.drawing

/**
 *@property currentTool the currently selected tool
 */
interface CanvasContainer {
    fun updateItem(item: PaintItem)
    fun colorPalette(action: Action)
}

enum class Action {
    SHOW, HIDE
}
