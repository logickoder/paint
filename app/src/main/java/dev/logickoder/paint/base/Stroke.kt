package dev.logickoder.paint.base

import android.graphics.Path

/**
 * Represents a single stroke on the canvas
 * @property color the color of the stroke
 * @property strokeWidth the width of the stroke
 * @property path a [Path] object to represent the path drawn
 */
data class Stroke(val color: Int, val strokeWidth: Float, val path: Path)
