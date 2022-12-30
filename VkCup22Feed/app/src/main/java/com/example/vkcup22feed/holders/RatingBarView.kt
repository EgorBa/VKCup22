package com.example.vkcup22feed.holders

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.Gravity
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.RatingBar
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.core.view.marginTop
import com.example.vkcup22feed.R
import com.example.vkcup22feed.Utils.dp
import com.example.vkcup22feed.blocks.RatingBlock

class RatingBarView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val ratingBar: RatingBar
    private val textView: TextView
    private var block: RatingBlock? = null

    init {
        layoutParams = MarginLayoutParams(MATCH_PARENT, WRAP_CONTENT).apply {
            topMargin = 10.dp
            leftMargin = 10.dp
            rightMargin = 10.dp
            bottomMargin = 10.dp
        }
        background =
            AppCompatResources.getDrawable(context, R.drawable.bg_vh)
        orientation = VERTICAL
        textView = TextView(context).apply {
            textSize = 18f
            typeface = Typeface.DEFAULT_BOLD
            setTextColor(AppCompatResources.getColorStateList(context, R.color.black))
        }
        textView.text = "Оцените качество статьи"
        ratingBar = RatingBar(context).apply {
            setOnRatingBarChangeListener { ratingBar, rating, _ ->
                ratingBar.rating = rating
                block?.rating = rating
            }
            numStars = 5
            stepSize = 0.5f
            secondaryProgressTintList = ColorStateList.valueOf(ContextCompat.getColor(context, R.color.dark_golden))
            progressBackgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(context, R.color.light_grey_active))
            progressTintList = ColorStateList.valueOf(ContextCompat.getColor(context, R.color.golden))
        }
        addView(textView, LayoutParams(WRAP_CONTENT, WRAP_CONTENT).apply {
            bottomMargin = 5.dp
        })
        addView(ratingBar, LayoutParams(WRAP_CONTENT, WRAP_CONTENT))
    }

    fun bind(ratingBlock: RatingBlock) {
        block = ratingBlock
        ratingBar.rating = ratingBlock.rating
        textView.text = ratingBlock.title
    }

}