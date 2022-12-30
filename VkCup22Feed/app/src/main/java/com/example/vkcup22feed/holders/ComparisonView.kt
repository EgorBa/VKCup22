package com.example.vkcup22feed.holders

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.view.animation.AnimationUtils
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.isVisible
import androidx.core.view.setPadding
import com.example.vkcup22feed.R
import com.example.vkcup22feed.Utils.dp
import com.example.vkcup22feed.blocks.ComparisonBlock

class ComparisonView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private var block: ComparisonBlock? = null
    private val container: LinearLayout
    private val approveButton: TextView
    private val title: TextView
    private val rightViews: ArrayList<View> = arrayListOf()
    private val leftViews: ArrayList<View> = arrayListOf()

    private val fadeOutAnimation = AnimationUtils.loadAnimation(context, R.anim.fadeout)
    private val fadeInAnimation = AnimationUtils.loadAnimation(context, R.anim.fadein)

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
        orientation = VERTICAL
        background =
            AppCompatResources.getDrawable(context, R.drawable.bg_vh)
        container = LinearLayout(context).apply {
            orientation = VERTICAL
        }
        title = TextView(context).apply {
            setPadding(7.dp, 0, 0, 0)
            textSize = 18f
            typeface = Typeface.DEFAULT_BOLD
            setTextColor(AppCompatResources.getColorStateList(context, R.color.black))
        }
        gravity = Gravity.CENTER
        approveButton = TextView(context).apply {
            textSize = 14f
            text = "Создать пару"
            gravity = Gravity.CENTER
            setPadding(5.dp)
            foreground = AppCompatResources.getDrawable(context, R.drawable.ripple_effect)
            setOnClickListener {
                block?.apply {
                    if (selectedLeft != -1 && selectedRight != -1) {
                        val leftValue = left[selectedLeft]
                        val rightValue = right[selectedRight]
                        leftViews[selectedLeft].startAnimation(fadeOutAnimation)
                        rightViews[selectedRight].startAnimation(fadeOutAnimation)
                        right.removeAt(selectedRight)
                        left.removeAt(selectedLeft)
                        selectedLeft = -1
                        selectedRight = -1
                        pairs.add(Pair(leftValue, rightValue))
                        postDelayed({
                            bind(this, delayedLastReady = true)
                        }, 300)
                    } else {
                        val toast = Toast.makeText(
                            context,
                            "Выберите по одному варианту с каждой стороны",
                            Toast.LENGTH_SHORT
                        )
                        toast.show()
                    }
                }
            }
        }
        updateApproveButtonState()
        addView(title, MarginLayoutParams(MATCH_PARENT, WRAP_CONTENT).apply {
            topMargin = 5.dp
            bottomMargin = 10.dp
        })
        addView(container, LayoutParams(MATCH_PARENT, WRAP_CONTENT))
        addView(approveButton, LayoutParams(WRAP_CONTENT, WRAP_CONTENT).apply {
            topMargin = 10.dp
            bottomMargin = 5.dp
        })
    }

    fun bind(comparisonBlock: ComparisonBlock,
             delayedLastReady: Boolean = false,
             delayedLastChoose: Boolean = false
    ) {
        block = comparisonBlock
        container.removeAllViews()
        rightViews.clear()
        leftViews.clear()
        title.text = comparisonBlock.title
        for (i in comparisonBlock.left.indices) {
            val isLast = i == comparisonBlock.left.size - 1 && delayedLastChoose
            val view = createChooseView(
                comparisonBlock.left[i],
                comparisonBlock.right[i],
                i
            )
            container.addView(view)
            if (isLast) {
                view.startAnimation(fadeInAnimation)
            }
        }
        for (i in comparisonBlock.pairs.indices) {
            val isLast = i == comparisonBlock.pairs.size - 1 && delayedLastReady
            val view = createReadyView(comparisonBlock.pairs[i])
            container.addView(view)
            if (isLast) {
                view.startAnimation(fadeInAnimation)
            }
        }
        updateApproveButtonState()
    }

    private fun updateApproveButtonState() {
        val isAvailable = block?.selectedLeft != -1 && block?.selectedRight != -1
        approveButton.setTextColor(
            AppCompatResources.getColorStateList(
                context, if (isAvailable)
                    R.color.black
                else
                    R.color.grey
            )
        )
        approveButton.background =
            AppCompatResources.getDrawable(
                context, if (isAvailable)
                    R.drawable.bg_button
                else
                    R.drawable.bg_button_not_active
            )
    }

    private fun createChooseView(left: String, right: String, pos: Int): View {
        val view = LinearLayout(context).apply {
            layoutParams = FrameLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT).apply {
                weightSum = 2f
            }
            orientation = HORIZONTAL

            val rightChoose = TextView(context).apply {
                text = right
                setPadding(7.dp)
                background = AppCompatResources.getDrawable(
                    context,
                    if (pos == block?.selectedRight) R.drawable.bg_poll_active else R.drawable.bg_poll
                )
                textSize = 15f
                setTextColor(AppCompatResources.getColorStateList(context, R.color.black))
                setOnClickListener {
                    block?.apply {
                        selectedRight = pos
                    }
                    for (v in rightViews) {
                        if (v === it) continue
                        v.background = AppCompatResources.getDrawable(context, R.drawable.bg_poll)
                    }
                    background = AppCompatResources.getDrawable(context, R.drawable.bg_poll_active)
                    updateApproveButtonState()
                }
            }
            rightViews.add(rightChoose)
            val leftChoose = TextView(context).apply {
                text = left
                setPadding(7.dp)
                background = AppCompatResources.getDrawable(
                    context,
                    if (pos == block?.selectedLeft) R.drawable.bg_poll_active else R.drawable.bg_poll
                )
                textSize = 15f
                setTextColor(AppCompatResources.getColorStateList(context, R.color.black))
                setOnClickListener {
                    block?.apply {
                        selectedLeft = pos
                    }
                    for (v in leftViews) {
                        if (v === it) continue
                        v.background = AppCompatResources.getDrawable(context, R.drawable.bg_poll)
                    }
                    background = AppCompatResources.getDrawable(context, R.drawable.bg_poll_active)
                    updateApproveButtonState()
                }
            }
            leftViews.add(leftChoose)

            addView(
                leftChoose,
                LayoutParams(0, WRAP_CONTENT).apply {
                    weight = 1f
                    topMargin = 5.dp
                    leftMargin = 10.dp
                    rightMargin = 10.dp
                }
            )

            addView(
                rightChoose,
                LayoutParams(0, WRAP_CONTENT).apply {
                    weight = 1f
                    topMargin = 5.dp
                    leftMargin = 10.dp
                    rightMargin = 10.dp
                }
            )
        }
        return view
    }

    private fun createReadyView(pair: Pair<String, String>): View {
        val view = LinearLayout(context).apply {
            layoutParams = FrameLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT)
            orientation = HORIZONTAL

            val textView = TextView(context).apply {
                text = "${pair.first} - ${pair.second}"
                setCompoundDrawablesWithIntrinsicBounds(
                    null, null, AppCompatResources.getDrawable(
                        context,
                        R.drawable.baseline_clear_24
                    ), null
                )
                textSize = 15f
                setOnClickListener {
                    block?.apply {
                        left.add(pair.first)
                        right.add(pair.second)
                        pairs.remove(pair)
                        it.startAnimation(fadeOutAnimation)
                        postDelayed({
                            bind(this, delayedLastChoose = true)
                        }, 300)
                    }
                }
                gravity = Gravity.CENTER_VERTICAL
                setPadding(7.dp)
                setTextColor(AppCompatResources.getColorStateList(context, R.color.black))
                background = AppCompatResources.getDrawable(context, R.drawable.bg_poll_active)
            }

            addView(
                textView,
                LayoutParams(MATCH_PARENT, WRAP_CONTENT).apply {
                    topMargin = 5.dp
                    leftMargin = 10.dp
                    rightMargin = 10.dp
                }
            )

        }
        return view
    }

}