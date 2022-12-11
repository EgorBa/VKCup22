package com.example.vkcup22

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity.CENTER
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.LinearLayout
import android.widget.LinearLayout.HORIZONTAL
import com.example.vkcup22.Utils.dp
import com.example.vkcup22.views.ChooseButton
import com.example.vkcup22.views.NextButton

class MainActivity : AppCompatActivity() {

    private companion object {
        private const val MAX_LINE_CATEGORIES = 3
        private val ALL_CATEGORIES = listOf(
            "Игры",
            "Юмор",
            "Еда",
            "Кино",
            "Рестораны",
            "Прогулки",
            "Политика",
            "Новости",
            "Сериалы",
            "Рецепты",
            "Работа",
            "Отдых",
            "Спорт",
            "VK Cup",
            "Автомобили",
            "Клипы"
        )
    }

    private lateinit var container: LinearLayout
    private lateinit var nextButton: NextButton
    private val allButtons = arrayListOf<ChooseButton>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        container = findViewById(R.id.container)
        initCategories()
        initNextButton()
    }

    private fun initCategories() {
        val categories = arrayListOf<String>()
        for (category in ALL_CATEGORIES) {
            categories.add(category)
            if (categories.size == MAX_LINE_CATEGORIES) {
                addLine(categories)
                categories.clear()
            }
        }
        if (categories.size > 0) {
            addLine(categories)
        }
    }

    private fun addLine(categories: List<String>) {
        val linearLayout = LinearLayout(container.context).apply {
            orientation = HORIZONTAL
        }
        for (name in categories) {
            val button = ChooseButton(container.context).apply {
                text = name
                setCallback(::stateCallback)
            }
            allButtons.add(button)
            linearLayout.addView(
                button,
                LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT).apply {
                    setMargins(0, 0, 8.dp, 8.dp)
                }
            )
        }
        container.addView(
            linearLayout,
            LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
        )
    }

    private fun initNextButton() {
        val linearLayout = LinearLayout(container.context).apply {
            orientation = HORIZONTAL
            gravity = CENTER
        }
        nextButton = NextButton(container.context).apply {
            visibility = GONE
        }
        linearLayout.addView(
            nextButton,
            LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
        )
        container.addView(
            linearLayout,
            LinearLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT).apply {
                setMargins(0, 8.dp, 0, 0)
            }
        )
    }

    private fun stateCallback() {
        nextButton.visibility = if (isSelectedState()) {
            VISIBLE
        } else {
            GONE
        }
    }

    private fun isSelectedState(): Boolean {
        for (button in allButtons) {
            if (button.state == ChooseButton.State.SELECTED) {
                return true
            }
        }
        return false
    }

}