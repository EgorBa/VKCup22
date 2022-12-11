package com.example.vkcup22.views

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.widget.Toast
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import com.example.vkcup22.R
import com.example.vkcup22.Utils.dp

class NextButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr) {

    init {
        text = "Продолжить"
        setBackgroundResource(R.drawable.bg_next)
        setTextColor(ContextCompat.getColor(context, R.color.black))
        setTypeface(null, Typeface.BOLD)
        setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18f)
        setPadding(50.dp, 30.dp, 50.dp, 30.dp)
        gravity = Gravity.CENTER
        setOnClickListener {
            val toast =
                Toast.makeText(context, "Вы выбрали самые интересные категории", Toast.LENGTH_SHORT)
            toast.show()
        }
    }
}