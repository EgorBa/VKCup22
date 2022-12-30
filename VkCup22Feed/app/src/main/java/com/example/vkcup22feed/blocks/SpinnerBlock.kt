package com.example.vkcup22feed.blocks

data class SpinnerBlock(val title: String, val list: List<SpinnerItem>) : UIBlock {
    override fun copy(): UIBlock {
        return copy(title = title, list = list)
    }
}