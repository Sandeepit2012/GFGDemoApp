package com.demo.gfg.ui.feeds

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.demo.gfg.R
import com.demo.gfg.ui.feeds.model.Item
import com.demo.gfg.utils.AppUtils

/**
 * Adapter for feeds as per 0 index header view and rest as summary view
 */
class FeedsListAdapter(private val mContext: Context) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var mDataset: ArrayList<Item> = ArrayList()

    companion object {
        const val VIEW_TYPE_HEADER = 0
        const val VIEW_TYPE_ITEM = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, i: Int): RecyclerView.ViewHolder {
        if (i == VIEW_TYPE_HEADER) {
            return HeaderViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.header_summary_item, parent, false)
            )
        }
        return ItemViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.summary_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is HeaderViewHolder) {
            holder.setData(
                mContext = mContext,
                item = mDataset!![position]
            )
        }
        if (holder is ItemViewHolder) {
            holder.setData(
                mContext = mContext,
                item = mDataset!![position]
            )
        }

    }

    override fun getItemViewType(position: Int): Int {
        if (position == 0) {
            return VIEW_TYPE_HEADER
        }
        return VIEW_TYPE_ITEM
    }

    override fun getItemCount(): Int {
        return if (mDataset == null) 0 else mDataset!!.size
    }

    /**
     * Add list items to the adapter data
     */
    fun addData(list: List<Item>) {
        mDataset = list as ArrayList<Item>
        notifyDataSetChanged()
    }

    /**
     *ViewHolder for Header item
     */
    internal class HeaderViewHolder(vi: View) : RecyclerView.ViewHolder(vi) {
        fun setData(mContext: Context, item: Item) {
            textViewTitle.text = item.title
            textViewDate.text = AppUtils.changeDateFormat(item.pubDate)
            Glide.with(mContext)
                .load(item.enclosure.link)
                .placeholder(R.mipmap.ic_launcher)
                .into(imageViewFeed)
        }

        private var textViewTitle: TextView = vi.findViewById(R.id.textViewTitle)
        private var textViewDate: TextView = vi.findViewById(R.id.textViewDate)
        private var imageViewFeed: ImageView = vi.findViewById(R.id.imageViewFeed)
    }

    /**
     *ViewHolder for summary item
     */
    internal class ItemViewHolder(vi: View) : RecyclerView.ViewHolder(vi) {
        fun setData(mContext: Context, item: Item) {
            textViewTitle.text = item.title
            textViewDate.text = AppUtils.changeDateFormat(item.pubDate)
            Glide.with(mContext)
                .load(item.thumbnail)
                .placeholder(R.mipmap.ic_launcher)
                .into(imageViewFeed)
        }

        private var textViewTitle: TextView = vi.findViewById(R.id.textViewTitle)
        private var textViewDate: TextView = vi.findViewById(R.id.textViewDate)
        private var imageViewFeed: ImageView = vi.findViewById(R.id.imageViewFeed)
    }
}