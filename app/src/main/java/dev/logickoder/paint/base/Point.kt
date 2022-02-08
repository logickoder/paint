package dev.logickoder.paint.base

import kotlin.math.absoluteValue

/**
 * Represents the x and y coordinates of a [Stroke] on the [DrawingCanvas]
 */
data class Point(val x: Float, val y: Float)

operator fun Point.plus(point: Point) = Point(x + point.x, y + point.y)
operator fun Point.minus(point: Point) = Point(x - point.x, y - point.y)
operator fun Point.times(point: Point) = Point(x * point.x, y * point.y)
operator fun Point.div(point: Point) = Point(x / point.x, y / point.y)
fun Point.isPositive() = x >= 0 && y >= 0
fun Point.absoluteValue() = x.absoluteValue cord y.absoluteValue

infix fun Number.cord(other: Number) = Point(toFloat(), other.toFloat())