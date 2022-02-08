package dev.logickoder.paint.base

import android.graphics.Bitmap

typealias DrawableFactory = (DrawableProperties) -> Drawable?

/**
 * Defines the drawing canvas
 *
 * @property currentColor the current color used to draw in this canvas
 */
interface Canvas {
    var currentColor: Int

    /**
     * Initialize the canvas
     *
     * @param width the width of the canvas
     * @param height the height of the canvas
     * @param color the initial color of the drawing
     * @param drawableFactory the factory used to create drawables
     */
    fun init(width: Int, height: Int, color: Int, drawableFactory: DrawableFactory)

    /**
     * Undo the last drawable
     */
    fun undo()

    /**
     * Save the drawing
     */
    fun save(): Bitmap
}