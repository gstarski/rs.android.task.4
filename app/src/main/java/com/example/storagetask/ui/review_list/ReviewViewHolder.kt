package com.example.storagetask.ui.review_list

import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.example.storagetask.R
import com.example.storagetask.data.Review
import com.example.storagetask.databinding.ItemReviewBinding
import java.text.SimpleDateFormat
import java.util.*

class ReviewViewHolder(
    private val binding: ItemReviewBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(review: Review, editCallback: EditReviewHandler, deleteCallback: DeleteReviewHandler) {
        binding.title.text = "[${review.id}] ${review.title}"
        binding.createdAt.text = formatter.format(review.createdAt)
        binding.summary.text = review.summary
        binding.rating.rating = review.rating

        binding.overflowMenu.setOnClickListener {
            val popupMenu = PopupMenu(binding.root.context, it)
            popupMenu.menuInflater.inflate(R.menu.review_item_actions, popupMenu.menu)
            popupMenu.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.action_edit_review -> {
                        editCallback(review)
                        true
                    }
                    R.id.action_delete_review -> {
                        deleteCallback(review)
                        true
                    }
                    else -> false
                }
            }
            popupMenu.show()
        }
    }

    private val formatter = SimpleDateFormat("dd MMM yyyy @ HH:mm", Locale.ENGLISH)
}