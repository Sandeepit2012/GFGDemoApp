package com.demo.gfg.ui.feeds

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.demo.gfg.R
import com.demo.gfg.databinding.FragmentFeedsBinding
import com.demo.gfg.network.ApiInterface
import com.demo.gfg.network.RetrofitInstance
import com.demo.gfg.ui.feeds.model.FeedsResponse
import com.demo.gfg.ui.feeds.viewmodel.FeedsViewModel

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 * Load feeds from the api and fetch data
 */
class FeedsFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener {

    private lateinit var mSwipeRefreshLayout: SwipeRefreshLayout
    private var _binding: FragmentFeedsBinding? = null
    private val binding get() = _binding!!
    private lateinit var mFeedsViewModel: FeedsViewModel
    private lateinit var mApiInterface: ApiInterface

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mFeedsViewModel = ViewModelProvider(this)[FeedsViewModel::class.java]
        _binding = FragmentFeedsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mApiInterface = RetrofitInstance.getClientWithHeader()!!.create(ApiInterface::class.java)
        binding.loading.visibility = View.VISIBLE
        mSwipeRefreshLayout = binding.swipeRefreshLayout

        mFeedsViewModel.fetchFeeds(mApiInterface)
        mFeedsViewModel.feedsResult.observe(this, Observer {
            val feedsResult = it ?: return@Observer
            binding.loading.visibility = View.GONE
            mSwipeRefreshLayout.isRefreshing = false
            if (feedsResult.error != null) {
                Toast.makeText(activity, feedsResult.error, Toast.LENGTH_LONG)
            }
            if (feedsResult.success != null) {
                updateUi(feedsResult.success)
            }
        })
        mSwipeRefreshLayout.setOnRefreshListener(this)
    }

    /**
     * Update the rv with list items
     */
    private fun updateUi(feeds: FeedsResponse) {
        val recyclerViewFeeds = binding.recyclerViewFeeds
        val textViewNoFeeds = binding.textViewNoFeeds

        if (feeds != null) {
            var mAdapter = FeedsListAdapter(requireContext())
            textViewNoFeeds.visibility = View.GONE
            recyclerViewFeeds.adapter = mAdapter
            mAdapter.addData(feeds.items)
        } else {
            textViewNoFeeds.visibility = View.VISIBLE
            textViewNoFeeds.text = getString(R.string.no_feeds_yet)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /**
     * Pull to refresh feeds
     */
    override fun onRefresh() {
        mSwipeRefreshLayout.isRefreshing = true
        binding.loading.visibility = View.VISIBLE
        mFeedsViewModel.fetchFeeds(mApiInterface)
    }
}