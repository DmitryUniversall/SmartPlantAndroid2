package com.smartplant.core_android.ui

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import com.google.android.material.button.MaterialButton
import com.google.android.material.color.MaterialColors
import com.smartplant.core_android.R
import com.google.android.material.R as MaterialR

fun MaterialButton.makePrimary() {
    val colorPrimary = getThemeColor(context, MaterialR.attr.colorPrimary)
    val colorOnPrimary = getThemeColor(context, MaterialR.attr.colorOnPrimary)

    backgroundTintList = ColorStateList.valueOf(colorPrimary)
    setTextColor(colorOnPrimary)
    strokeWidth = 0
}

fun MaterialButton.makeOutlinedPrimary() {
    val colorPrimary = getThemeColor(context, MaterialR.attr.colorPrimary)

    backgroundTintList = ColorStateList.valueOf(Color.TRANSPARENT)
    setTextColor(colorPrimary)
    strokeColor = ColorStateList.valueOf(colorPrimary)
    strokeWidth = context.resources.getDimensionPixelSize(R.dimen.smartplantButtonStrokeWidth)
}

private fun getThemeColor(context: Context, attr: Int): Int {
    return MaterialColors.getColor(context, attr, Color.BLACK)
}
