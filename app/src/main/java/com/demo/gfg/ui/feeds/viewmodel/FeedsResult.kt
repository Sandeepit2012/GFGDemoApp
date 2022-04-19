package com.demo.gfg.ui.feeds.viewmodel

import com.demo.gfg.ui.feeds.model.FeedsResponse

/**
 * Feeds result for success and error..
 */
data class FeedsResult(
    val success: FeedsResponse? = null,
    val error: String? = null
)