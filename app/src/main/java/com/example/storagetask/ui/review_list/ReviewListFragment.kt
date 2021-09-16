package com.example.storagetask.ui.review_list

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.SimpleItemAnimator
import com.example.storagetask.R
import com.example.storagetask.data.Review
import com.example.storagetask.databinding.FragmentReviewListBinding
import com.example.storagetask.viewmodel.ReviewsViewModel
import kotlinx.coroutines.flow.collect

class ReviewListFragment : Fragment() {
    private var _binding: FragmentReviewListBinding? = null
    private val binding get() = _binding!!
    
    private val viewModel: ReviewsViewModel by activityViewModels()

    private val reviewAdapter by lazy { ReviewListAdapter(this::navigateToEdit, this::deleteReview) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        Log.d("TAG", "Fragment created")
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.review_list_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> {
                navigateToSettings()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentReviewListBinding.inflate(inflater, container, false)
        binding.recyclerReviews.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = reviewAdapter
            (itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
        }
        binding.addFab.setOnClickListener { navigateToCreate() }
        Log.d("TAG", "View created")
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d("TAG", "View destroyed")
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            try {
                viewModel.sortedReviews.collect {
                    Log.d("TAG", "Got list update (${it.size} elements)")
                    reviewAdapter.submitList(it)
                }
            } finally {
                Log.d("TAG", "Stop listening for list updates")
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.title.collect {
                (activity as? AppCompatActivity)?.supportActionBar?.title = it
            }
        }
    }

    private fun navigateToCreate() {
        val action = ReviewListFragmentDirections.actionReviewListFragmentToCreateReviewFragment()
        findNavController().navigate(action)
    }

    private fun navigateToEdit(review: Review) {
        val action = ReviewListFragmentDirections.actionReviewListFragmentToEditReviewFragment(review.id)
        findNavController().navigate(action)
    }

    private fun navigateToSettings() {
        val action = ReviewListFragmentDirections.actionReviewListFragmentToSettingsFragment()
        findNavController().navigate(action)
    }

    private fun deleteReview(review: Review) {
        viewModel.delete(review)
    }
}