package dev.logickoder.paint.utils

import android.content.res.Resources
import android.util.TypedValue

/**
 * @param resources the android resource of an activity or fragment
 *
 * @return the dp equivalent of this number in int
 * */
fun Number.dp(resources: Resources) = TypedValue.applyDimension(
    TypedValue.COMPLEX_UNIT_DIP, toFloat(), resources.displayMetrics
).toInt()