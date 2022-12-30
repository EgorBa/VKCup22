package com.example.vkcup22feed

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vkcup22feed.blocks.ComparisonBlock
import com.example.vkcup22feed.blocks.Poll
import com.example.vkcup22feed.blocks.PollBlock
import com.example.vkcup22feed.blocks.RatingBlock
import com.example.vkcup22feed.blocks.SpinnerBlock
import com.example.vkcup22feed.blocks.SpinnerItem
import com.example.vkcup22feed.blocks.UIBlock

class MainActivity : ComponentActivity() {

    private companion object{
        val ratingBlocks = listOf(
            RatingBlock("Оцените качество", 0f),
            RatingBlock("Оцените приложение", 0f),
            RatingBlock("Оцените ленту", 0f),
            RatingBlock("Любите звкзды?", 2.5f),
            RatingBlock("Оцените рекомендации", 0f),
        )

        val spinnerBlocks = listOf(
            SpinnerBlock(
                "Выбире вариант продолжения", listOf(
                    SpinnerItem("Мой любимый язык : ", listOf("Java", "Kotlin", "C++", "Python")),
                    SpinnerItem("Главное в жизни : ", listOf("Работа", "Деньги", "Дети"))
                )
            ),
            SpinnerBlock(
                "Закончите поговорки", listOf(
                    SpinnerItem("Хлеб всему - ", listOf("хлеб", "голова", "народу")),
                    SpinnerItem("Работа не волк, ", listOf("волк - это гулять", "в лес не убежит"))
                )
            ),
            SpinnerBlock(
                "Решите примеры", listOf(
                    SpinnerItem("3-5*5 = ", listOf("0", "-22", "28")),
                    SpinnerItem("100/4-8 =", listOf("17", "-17", "34", "56")),
                    SpinnerItem("3-3+3-3-3 =", listOf("0", "-3", "3")),
                )
            ),
            SpinnerBlock(
                "Оцените утверждение (Да/Нет)", listOf(
                    SpinnerItem("Мне нравится оливки", listOf("Да", "Нет")),
                    SpinnerItem("Я люблю кофе", listOf("Да", "Нет")),
                    SpinnerItem("Я обожаю чай", listOf("Да", "Нет"))
                )
            )
        )

        val pollBlocks = listOf(
            PollBlock(
                activePoll = 0, polls = listOf(
                    Poll(
                        question = "Столица Аргентины?",
                        variants = listOf("Копенгаген", "Буэнос-Айрос", "Москва", "Бразилиа"),
                        rightIndex = 1,
                        userAnswer = -1,
                        isDone = false,
                        variantsPercent = listOf(0f, 50f, 50f, 0f)
                    ),
                    Poll(
                        question = "Столица Алжиры?",
                        variants = listOf("Алжир", "Берн", "Лондон", "Каир"),
                        rightIndex = 0,
                        userAnswer = -1,
                        isDone = false,
                        variantsPercent = listOf(100f, 0f, 0f, 0f)
                    ),
                    Poll(
                        question = "Столица Росии?",
                        variants = listOf("Санкт-Петербург", "Казань", "Москва", "Новосибирск"),
                        rightIndex = 2,
                        userAnswer = -1,
                        isDone = false,
                        variantsPercent = listOf(25f, 25f, 25f, 25f)
                    )
                )
            ),
            PollBlock(
                activePoll = 0, polls = listOf(
                    Poll(
                        question = "Формула воды?",
                        variants = listOf("H2O", "CO2", "H2SO4", "O2"),
                        rightIndex = 0,
                        userAnswer = -1,
                        isDone = false,
                        variantsPercent = listOf(0f, 50f, 50f, 0f)
                    ),
                    Poll(
                        question = "Формула кислорода?",
                        variants = listOf("H2O", "CO2", "H2SO4", "O2"),
                        rightIndex = 3,
                        userAnswer = -1,
                        isDone = false,
                        variantsPercent = listOf(100f, 0f, 0f, 0f)
                    )
                )
            ),
            PollBlock(
                activePoll = 0, polls = listOf(
                    Poll(
                        question = "Как будет по-английски 'дом'?",
                        variants = listOf("house", "flat", "horse", "dog"),
                        rightIndex = 0,
                        userAnswer = -1,
                        isDone = false,
                        variantsPercent = listOf(33f, 33f, 34f, 0f)
                    )
                )
            ),
            PollBlock(
                activePoll = 0, polls = listOf(
                    Poll(
                        question = "В чем сила заключается?",
                        variants = listOf("В правде", "В любви", "В ньютонах", "В честности"),
                        rightIndex = 2,
                        userAnswer = -1,
                        isDone = false,
                        variantsPercent = listOf(25f, 25f, 50f, 0f)
                    )
                )
            )
        )

        val comparisonBlocks = listOf(
            ComparisonBlock(
                title = "Сопоставьте формулу веществу",
                right = arrayListOf("Вода", "Водород", "Серная кислота", "Кислород"),
                left = arrayListOf("H2", "H20", "O2", "H2SO4")
            ),
            ComparisonBlock(
                title = "Сопоставьте страну ее столице",
                right = arrayListOf("США", "Россия", "Испания"),
                left = arrayListOf("Мадрид", "Вашингтон", "Москва")
            ),
            ComparisonBlock(
                title = "Сопоставьте цвет его переводу",
                right = arrayListOf("Зеленый", "Красный", "Синий", "Желтый"),
                left = arrayListOf("Yelow", "Red", "Green", "Blue")
            ),
            ComparisonBlock(
                title = "Сопоставьте валюты",
                right = arrayListOf("Доллар", "Евро", "Рубль"),
                left = arrayListOf("€", "$", "₽")
            )
        )

        const val DATA_SIZE = 200
    }

    private lateinit var recycler: RecyclerView

    private fun generateData(): List<UIBlock> {
        val data: ArrayList<UIBlock> = arrayListOf()
        val allData = listOf(
            ratingBlocks,
            spinnerBlocks,
            pollBlocks,
            comparisonBlocks
        )
        for (i in 0..DATA_SIZE) {
            val blockSectionNumber = (0..3).random()
            val size = allData[blockSectionNumber].size - 1
            val blockNumber = (0..size).random()
            data.add(allData[blockSectionNumber][blockNumber].copy())
        }
        return data
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_layout)
        recycler = findViewById(R.id.recycler)
        recycler.layoutManager = LinearLayoutManager(this)
        val adapter = FeedAdapter(generateData())
        recycler.adapter = adapter

    }
}
