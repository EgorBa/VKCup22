package com.example.vkcup22feed.blocks

data class Poll(
    val question: String,
    val variants: List<String>,
    val rightIndex: Int,
    var userAnswer: Int = -1,
    var isDone: Boolean = false,
    val variantsPercent: List<Float>
)