package com.example.vkcup22feed.holders

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
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
import com.example.vkcup22feed.blocks.PollBlock

class PollView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val questionNumber: TextView
    private val question: TextView
    private val var1: TextView
    private val var2: TextView
    private val var3: TextView
    private val var4: TextView

    private val varPer1: TextView
    private val varPer2: TextView
    private val varPer3: TextView
    private val varPer4: TextView

    private val allVars: List<TextView>
    private val allVarPers: List<TextView>
    private val allVarContainers: List<View>
    private val animViews: ArrayList<View> = arrayListOf()
    private val approveButton: TextView
    private val nextButton: TextView
    private val prevButton: TextView
    private var block: PollBlock? = null

    private val fadeOutAnimation = AnimationUtils.loadAnimation(context, R.anim.fadeout)
    private val fadeInAnimation = AnimationUtils.loadAnimation(context, R.anim.fadein)

    init {
        layoutParams = MarginLayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        ).apply {
            topMargin = 10.dp
            leftMargin = 10.dp
            rightMargin = 10.dp
            bottomMargin = 10.dp
        }
        background =
            AppCompatResources.getDrawable(context, R.drawable.bg_vh)
        orientation = VERTICAL

        questionNumber = TextView(context).apply {
            textSize = 12f
        }
        question = TextView(context).apply {
            textSize = 18f
            typeface = Typeface.DEFAULT_BOLD
            setTextColor(AppCompatResources.getColorStateList(context, R.color.black))
        }
        var1 = createVariantTextView()
        var2 = createVariantTextView()
        var3 = createVariantTextView()
        var4 = createVariantTextView()
        varPer1 = TextView(context)
        varPer2 = TextView(context)
        varPer3 = TextView(context)
        varPer4 = TextView(context)
        approveButton = TextView(context)
        nextButton = TextView(context)
        prevButton = TextView(context)

        allVars = listOf(var1, var2, var3, var4)
        allVarPers = listOf(varPer1, varPer2, varPer3, varPer4)
        val arrayList = arrayListOf<View>()
        for (i in allVars.indices) {
            arrayList.add(createContainerForVariant(allVars[i], allVarPers[i]))
        }
        allVarContainers = arrayList.toList()
        animViews.addAll(allVarContainers)
        animViews.add(questionNumber)
        animViews.add(question)

        for (i in allVarContainers.indices) {
            allVarContainers[i].setOnClickListener {
                block?.apply {
                    if (!getCurrentPoll().isDone) {
                        getCurrentPoll().userAnswer = i
                        for (container in allVarContainers) {
                            if (container === allVarContainers[i]) continue
                            container.background =
                                AppCompatResources.getDrawable(context, R.drawable.bg_poll)
                        }
                        updateApproveButtonState()
                        allVarContainers[i].background =
                            AppCompatResources.getDrawable(context, R.drawable.bg_poll_active)
                    }
                }
            }
        }

        approveButton.setOnClickListener {
            block?.let {
                if (it.getCurrentPoll().userAnswer != -1) {
                    it.getCurrentPoll().isDone = true
                    bind(it)
                } else {
                    val toast = Toast.makeText(
                        context, "Сначала выберите вариант ответа", Toast.LENGTH_SHORT
                    )
                    toast.show()
                }
            }
        }

        nextButton.setOnClickListener {
            block?.let {
                if (it.activePoll < it.polls.size - 1) {
                    it.activePoll += 1

                    for (v in animViews) {
                        v.startAnimation(fadeOutAnimation)
                    }

                    postDelayed({
                        for (v in animViews) {
                            v.startAnimation(fadeInAnimation)
                        }
                        bind(it)
                    }, 300)
                }
            }
        }

        prevButton.setOnClickListener {
            block?.let {
                if (it.activePoll > 0) {
                    it.activePoll -= 1

                    for (v in animViews) {
                        v.startAnimation(fadeOutAnimation)
                    }

                    postDelayed({
                        for (v in animViews) {
                            v.startAnimation(fadeInAnimation)
                        }
                        bind(it)
                    }, 300)
                }
            }
        }

        addView(questionNumber, MarginLayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT
        ).apply {
            topMargin = 5.dp
        })
        addView(question, MarginLayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT
        ).apply {
            topMargin = 5.dp
            bottomMargin = 10.dp
        })
        for (container in allVarContainers) {
            addView(container, MarginLayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT
            ).apply {
                topMargin = 5.dp
            })
        }
        addView(
            createContainerForButtons(prevButton, approveButton, nextButton).apply {
                updateApproveButtonState()
            }, MarginLayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            ).apply {
                topMargin = 10.dp
            }
        )
    }

    fun bind(pollBlock: PollBlock) {
        block = pollBlock
        questionNumber.text = "Вопрос ${pollBlock.activePoll + 1}/${pollBlock.polls.size}"
        val currentPoll = pollBlock.getCurrentPoll()
        question.text = currentPoll.question

        for (i in allVars.indices) {
            if (i < currentPoll.variants.size) {
                allVarContainers[i].isVisible = true
                allVars[i].text = currentPoll.variants[i]
                allVarPers[i].isVisible = currentPoll.isDone
                allVarPers[i].text = "${currentPoll.variantsPercent[i].toInt()}%"
            } else {
                allVarContainers[i].isVisible = false
            }
        }

        for (container in allVarContainers) {
            container.background = AppCompatResources.getDrawable(context, R.drawable.bg_poll)
        }
        updateApproveButtonState()
        prevButton.visibility = if (pollBlock.activePoll > 0) VISIBLE else INVISIBLE
        nextButton.visibility =
            if (pollBlock.activePoll < pollBlock.polls.size - 1) VISIBLE else INVISIBLE

        if (currentPoll.isDone) {
            allVarContainers[currentPoll.userAnswer].background =
                AppCompatResources.getDrawable(context, R.drawable.bg_poll_error)
            allVarContainers[currentPoll.rightIndex].background =
                AppCompatResources.getDrawable(context, R.drawable.bg_poll_right)
        } else {
            if (currentPoll.userAnswer != -1) {
                allVarContainers[currentPoll.userAnswer].background =
                    AppCompatResources.getDrawable(context, R.drawable.bg_poll_active)
            }
        }
    }

    private fun createVariantTextView():TextView{
        return TextView(context).apply {
            textSize = 15f
            setTextColor(AppCompatResources.getColorStateList(context, R.color.black))
        }
    }

    private fun createContainerForVariant(variant: TextView, variantPercent: TextView): View {
        val view = FrameLayout(context).apply {
            layoutParams = FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )

            setPadding(10.dp)
            background = AppCompatResources.getDrawable(context, R.drawable.bg_poll)
            addView(
                variant, FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    Gravity.START
                )
            )
            addView(
                variantPercent, FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    Gravity.END
                )
            )
        }
        return view
    }

    private fun updateApproveButtonState() {
        val isAvailable = block?.getCurrentPoll()?.userAnswer != -1
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

    private fun createContainerForButtons(
        prevButton: TextView,
        approveButton: TextView,
        nextButton: TextView
    ): View {
        val view = LinearLayout(context).apply {
            weightSum = 3f
            layoutParams = MarginLayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            ).apply {
                topMargin = 10.dp
            }
            orientation = HORIZONTAL
            addView(
                prevButton.apply {
                    setCompoundDrawablesWithIntrinsicBounds(
                        AppCompatResources.getDrawable(
                            context,
                            R.drawable.baseline_arrow_back_24
                        ), null, null, null
                    )
                }, LayoutParams(
                    0,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                ).apply {
                    weight = 1f
                }
            )
            addView(
                approveButton.apply {
                    gravity = Gravity.CENTER
                    textSize = 14f
                    text = "Подтвердить"
                    foreground = AppCompatResources.getDrawable(context, R.drawable.ripple_effect)
                }, LayoutParams(
                    0,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                ).apply {
                    weight = 1f
                    bottomMargin = 5.dp
                }
            )
            addView(
                nextButton.apply {
                    setCompoundDrawablesWithIntrinsicBounds(
                        null, null, AppCompatResources.getDrawable(
                            context,
                            R.drawable.baseline_arrow_forward_24
                        ), null
                    )
                }, LayoutParams(
                    0,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                ).apply {
                    weight = 1f
                }
            )
        }
        return view
    }

}