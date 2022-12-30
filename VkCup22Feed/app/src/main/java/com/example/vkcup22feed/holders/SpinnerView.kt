package com.example.vkcup22feed.holders

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.setPadding
import com.example.vkcup22feed.R
import com.example.vkcup22feed.Utils.dp
import com.example.vkcup22feed.blocks.SpinnerBlock
import com.example.vkcup22feed.blocks.SpinnerItem

class SpinnerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private var block: SpinnerBlock? = null
    private val title: TextView
    private val container: LinearLayout

    init {
        layoutParams = MarginLayoutParams(
            MATCH_PARENT,
            WRAP_CONTENT
        ).apply {
            topMargin = 10.dp
            leftMargin = 10.dp
            rightMargin = 10.dp
            bottomMargin = 10.dp
        }
        background =
            AppCompatResources.getDrawable(context, R.drawable.bg_vh)
        orientation = VERTICAL
        container = LinearLayout(context).apply {
            orientation = VERTICAL
            setPadding(0, 0, 0, 7.dp)
        }
        title = TextView(context).apply {
            textSize = 18f
            typeface = Typeface.DEFAULT_BOLD
            setTextColor(AppCompatResources.getColorStateList(context, R.color.black))
        }
        addView(title, MarginLayoutParams(MATCH_PARENT, WRAP_CONTENT).apply {
            topMargin = 5.dp
            bottomMargin = 5.dp
        })
        addView(container, LayoutParams(MATCH_PARENT, WRAP_CONTENT))
    }

    fun bind(spinnerBlock: SpinnerBlock) {
        block = spinnerBlock
        container.removeAllViews()
        title.text = spinnerBlock.title
        for (item in spinnerBlock.list) {
            container.addView(createSentence(item))
        }
    }

    private fun createSentence(spinnerInfo: SpinnerItem): View {
        return LinearLayout(context).apply {
            layoutParams = MarginLayoutParams(
                MATCH_PARENT,
                WRAP_CONTENT
            ).apply {
                topMargin = 10.dp
            }

            orientation = HORIZONTAL
            setPadding(7.dp)
            background = AppCompatResources.getDrawable(context, R.drawable.bg_poll)

            val textView = TextView(context).apply {
                setTextColor(AppCompatResources.getColorStateList(context, R.color.black))
                text = spinnerInfo.question
            }

            val spinner = Spinner(context).apply {
                val spinnerAdapter = SpinnerAdapter(
                    context,
                    android.R.layout.simple_spinner_item,
                    spinnerInfo.variants.toTypedArray()
                )
                spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        spinnerInfo.safePos = position
                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) {}
                }
                adapter = spinnerAdapter
                if (spinnerInfo.safePos != -1) {
                    setSelection(spinnerInfo.safePos)
                }
            }

            addView(textView, LayoutParams(0, WRAP_CONTENT).apply {
                weight = 1f
            })
            addView(spinner, LayoutParams(WRAP_CONTENT, WRAP_CONTENT))
        }
    }

    private class SpinnerAdapter(
        context: Context,
        private val resource: Int,
        private val objects: Array<String>
    ) :
        ArrayAdapter<String>(context, resource, objects)
}