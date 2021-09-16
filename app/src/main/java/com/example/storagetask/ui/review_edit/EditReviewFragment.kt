package com.example.storagetask.ui.review_edit

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.storagetask.R
import com.example.storagetask.data.Review
import com.example.storagetask.databinding.FragmentEditReviewBinding
import com.example.storagetask.viewmodel.ReviewsViewModel
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.collect

class EditReviewFragment : Fragment() {

    private var _binding: FragmentEditReviewBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ReviewsViewModel by activityViewModels()

    private val args: EditReviewFragmentArgs by navArgs()

    private var updateReview: () -> Unit = { }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditReviewBinding.inflate(inflater, container, false)
        lockFields()
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            try {
                viewModel.reviews.collect {
                    it.find { rev -> rev.id == args.reviewId }?.also { rev ->
                        Log.d("TAG", "Got a review to edit: $rev")
                        loadReview(rev)
                        this.cancel()
                    } ?: fallBack()
                }
            } finally {
                Log.d("TAG", "Stop waiting for review")
            }
        }

        binding.saveButton.setOnClickListener {
            updateReview()
            findNavController().navigateUp()
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.review_edit_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_save -> {
                updateReview()
                findNavController().navigateUp()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun lockFields() {
        binding.editTitle.isEnabled = false
        binding.editSummary.isEnabled = false
        binding.ratingBar.isEnabled = false
    }

    private fun unlockFields() {
        binding.editTitle.isEnabled = true
        binding.editSummary.isEnabled = true
        binding.ratingBar.isEnabled = true
    }

    private fun loadReview(review: Review) {
        binding.editTitle.setText(review.title)
        binding.editSummary.setText(review.summary)
        binding.ratingBar.rating = review.rating

        updateReview = {
            viewModel.update(review.copy(
                title = binding.editTitle.text.toString(),
                rating = binding.ratingBar.rating,
                summary = binding.editSummary.text.toString()
            ))
        }

        unlockFields()
    }

    private fun fallBack() {
        Toast.makeText(context, "Unable to load the review", Toast.LENGTH_LONG).show()
        findNavController().navigateUp()
    }

    // TODO: validation, separate vm
}