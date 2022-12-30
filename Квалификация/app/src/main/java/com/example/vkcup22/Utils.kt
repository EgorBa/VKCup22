package com.example.vkcup22

import android.content.res.Resources
import android.util.TypedValue

object Utils {

    val Number.dp
        get() = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            this.toFloat(),
            Resources.getSystem().displayMetrics
        ).toInt()

}