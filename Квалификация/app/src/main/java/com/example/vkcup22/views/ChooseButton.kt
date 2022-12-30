package com.example.vkcup22.views

import android.content.Context
import android.graphics.Typeface.BOLD
import android.util.AttributeSet
import android.view.Gravity.CENTER
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import com.example.vkcup22.R
import com.example.vkcup22.Utils.dp

class ChooseButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr) {

    var state: State = State.ACTIVE
    private var callback: (() -> Unit)? = null

    init {
        setBackgroundResource(R.drawable.bg_choose_grey)
        setPadding(8.dp, 8.dp, 8.dp, 8.dp)
        setPlus()
        setTextColor(ContextCompat.getColor(context, R.color.white))
        setTypeface(null, BOLD)
        gravity = CENTER
        setOnClickListener {
            when (state) {
                State.ACTIVE -> {
                    state = State.SELECTED
                    setBackgroundResource(R.drawable.bg_choose_red)
                    setCheck()
                }
                State.SELECTED -> {
                    state = State.ACTIVE
                    setBackgroundResource(R.drawable.bg_choose_grey)
                    setPlus()
                }
            }
            callback?.invoke()
        }
    }

    fun setCallback(func: () -> Unit) {
        callback = func
    }

    private fun setPlus() {
        setCompoundDrawablesWithIntrinsicBounds(
            null,
            null, AppCompatResources.getDrawable(context, R.drawable.ic_baseline_add_24), null
        )
    }

    private fun setCheck() {
        setCompoundDrawablesWithIntrinsicBounds(
            null,
            null, AppCompatResources.getDrawable(context, R.drawable.ic_baseline_check_24), null
        )
    }

    enum class State {
        ACTIVE, SELECTED
    }

}