package com.example.storagetask.ui.review_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.storagetask.data.Review
import com.example.storagetask.databinding.ItemReviewBinding

typealias EditReviewHandler = (Review) -> Unit
typealias DeleteReviewHandler = (Review) -> Unit

class ReviewListAdapter(
    private val editCallback: EditReviewHandler,
    private val deleteCallback: DeleteReviewHandler
) : ListAdapter<Review, ReviewViewHolder>(itemComparator) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemReviewBinding.inflate(inflater, parent, false)
        return ReviewViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        holder.bind(getItem(position), editCallback, deleteCallback)
    }

    private companion object {
        val itemComparator = object : DiffUtil.ItemCallback<Review>() {
            override fun areItemsTheSame(oldItem: Review, newItem: Review): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Review, newItem: Review): Boolean {
                return oldItem.title == newItem.title
                        && oldItem.rating == newItem.rating
                        && oldItem.summary == newItem.summary
            }

        }
    }
}

