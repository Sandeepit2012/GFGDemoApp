package com.demo.gfg.network

import com.demo.gfg.ui.feeds.model.FeedsResponse
import retrofit2.Call
import retrofit2.http.*

/**
 * Retrofit api interface for app the api methods
 */
interface ApiInterface {
    @GET("api.json?rss_url=http://www.abc.net.au/news/feed/51120/rss.xml")
    fun fetchFeeds(): Call<FeedsResponse?>?
}