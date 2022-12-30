package com.example.vkcup22feed

import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.FrameLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.vkcup22feed.blocks.ComparisonBlock
import com.example.vkcup22feed.blocks.PollBlock
import com.example.vkcup22feed.blocks.RatingBlock
import com.example.vkcup22feed.blocks.SpinnerBlock
import com.example.vkcup22feed.blocks.UIBlock
import com.example.vkcup22feed.holders.ComparisonView
import com.example.vkcup22feed.holders.PollView
import com.example.vkcup22feed.holders.RatingBarView
import com.example.vkcup22feed.holders.SpinnerView

class FeedAdapter(private val data: List<UIBlock>) :
    RecyclerView.Adapter<FeedAdapter.FeedViewHolder>() {

    class FeedViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val container: FrameLayout = itemView.findViewById(R.id.container)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedViewHolder {
        val view = FrameLayout(parent.context)
        view.layoutParams = FrameLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT)
        view.id = R.id.container
        return FeedViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: FeedViewHolder, position: Int) {
        val view = when (val block = data[position]) {
            is RatingBlock -> RatingBarView(holder.itemView.context).apply { bind(block) }
            is PollBlock -> PollView(holder.itemView.context).apply { bind(block) }
            is ComparisonBlock -> ComparisonView(holder.itemView.context).apply { bind(block) }
            is SpinnerBlock -> SpinnerView(holder.itemView.context).apply { bind(block) }
            else -> null
        }
        view?.let {
            holder.container.removeAllViews()
            holder.container.addView(it)
        }
    }
}