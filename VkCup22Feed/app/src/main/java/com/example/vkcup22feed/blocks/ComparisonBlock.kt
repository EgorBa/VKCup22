package com.example.vkcup22feed.blocks

data class ComparisonBlock(
    val title: String,
    val right: ArrayList<String>,
    var selectedRight: Int = -1,
    val left: ArrayList<String>,
    var selectedLeft: Int = -1,
    val pairs: ArrayList<Pair<String, String>> = arrayListOf()
) : UIBlock {
    override fun copy(): UIBlock {
        return copy(
            title = title,
            right = right,
            selectedRight = selectedRight,
            left = left,
            selectedLeft = selectedLeft,
            pairs = pairs
        )
    }
}