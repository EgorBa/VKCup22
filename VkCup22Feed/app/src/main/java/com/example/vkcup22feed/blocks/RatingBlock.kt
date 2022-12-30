package com.example.vkcup22feed.blocks

data class RatingBlock(val title: String, var rating: Float = 0f) : UIBlock {
    override fun copy(): UIBlock {
        return copy(title = title, rating = rating)
    }
}