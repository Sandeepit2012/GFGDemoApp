package com.demo.gfg.ui.feeds.model

/**
 * Model for Feeds response
 */
data class FeedsResponse(
    val feed: Feed,
    val items: List<Item>,
    val status: String
)