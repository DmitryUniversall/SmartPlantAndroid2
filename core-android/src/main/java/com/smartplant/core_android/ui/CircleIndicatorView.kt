package com.smartplant.core_android.ui

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import com.smartplant.core_android.R

class CircleIndicatorView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : LinearLayout(context, attrs, defStyleAttr) {

    private var color: Int = Color.BLACK
    private var count: Int = 0
    private var size: Int = 8.dpToPx()
    private var margin: Int = 4.dpToPx()

    private var activeIndex: Int = 0

    init {
        orientation = HORIZONTAL

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CircleIndicatorView)
        color = typedArray.getColor(R.styleable.CircleIndicatorView_color, color)
        count = typedArray.getInt(R.styleable.CircleIndicatorView_count, 0)
        size = typedArray.getDimensionPixelSize(R.styleable.CircleIndicatorView_size, 0)
        margin = typedArray.getDimensionPixelSize(R.styleable.CircleIndicatorView_margin, 0)
        setupIndicators()

        typedArray.recycle()
    }

    private fun Int.dpToPx(): Int = (this * resources.displayMetrics.density).toInt()

    private fun createCircleDrawable(color: Int): GradientDrawable {
        return GradientDrawable().apply {
            shape = GradientDrawable.OVAL
            setColor(color)
        }
    }

    fun setupIndicators() {
        removeAllViews()

        val params = LayoutParams(size, size).apply { setMargins(margin, 0, margin, 0) }

        (0 until count).forEach {
            val view = View(context).apply {
                background = createCircleDrawable(color)
                layoutParams = params
            }

            addView(view)
        }
    }

    fun setActive(index: Int) {
        if (index >= count) return

        activeIndex = index
        for (i in 0 until childCount) {
            val circle = getChildAt(i)
            val targetAlpha = if (i != index) 0.5f else 1f

            if (circle.alpha != targetAlpha) {
                ObjectAnimator.ofFloat(circle, "alpha", circle.alpha, targetAlpha).apply {
                    duration = 300
                    start()
                }
            }
        }
    }
}
