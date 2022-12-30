package com.example.vkcup22feed.blocks

data class PollBlock(var activePoll: Int = 0, val polls: List<Poll>) : UIBlock {
    fun getCurrentPoll(): Poll = polls[activePoll]
    override fun copy(): UIBlock {
        return copy(activePoll = activePoll, polls = polls)
    }
}