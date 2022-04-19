package com.demo.gfg.ui.feeds.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.demo.gfg.ui.feeds.model.FeedsResponse
import com.demo.gfg.network.ApiInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * ViewModel for all the logical data operations
 */
class FeedsViewModel : ViewModel() {

    private val _feedsResult = MutableLiveData<FeedsResult>()
    val feedsResult: LiveData<FeedsResult> = _feedsResult

    /**
     * Method to fetch feeds and put the response into result
     */
    fun fetchFeeds(apiInterface: ApiInterface) {
        apiInterface.fetchFeeds()!!.enqueue(object : Callback<FeedsResponse?> {
            override fun onResponse(
                call: Call<FeedsResponse?>,
                response: Response<FeedsResponse?>
            ) {
                if (response.isSuccessful) {
                    if (response.body()!!.status == "ok") {
                        _feedsResult.value = FeedsResult(success = response.body()!!)
                    } else {
                        _feedsResult.value = FeedsResult(error = "Error in fetching feeds.")
                    }
                } else {
                    _feedsResult.value = FeedsResult(error = "Error in fetching feeds.")
                }
            }

            override fun onFailure(call: Call<FeedsResponse?>, t: Throwable) {
                Log.i("Failure", "Failure")
            }
        })
    }
}