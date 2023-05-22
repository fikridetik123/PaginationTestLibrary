package com.example.paginationlibrary

import androidx.recyclerview.widget.RecyclerView

/**
 * Created by Muhammad Fikri Fadilah on 22/05/2023.
 */
abstract class BasePagedListAdapter :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    companion object {
        const val PAGING_LOAD = "PAGING_LOAD"
        const val PAGING_FAIL = "PAGING_FAIL"
    }
    var items = mutableListOf<Any>()

    lateinit var mPagingListener: ListenerPaging
    private var mPagingLoadingPosition = 0
    private var mPagingLoadingState = false

    fun insert(data: MutableList<Any>) {
        items.apply {
            clear()
            addAll(data)
        }
        notifyDataSetChanged()
    }

    fun update(data: MutableList<Any>) {
        val lastPosition = items.size
        items.apply {
            addAll(data)
        }
        notifyItemRangeInserted(lastPosition, data.size)
    }

    fun clear() {
        items.apply {
            clear()
        }
        notifyDataSetChanged()
    }

    fun showPagingLoading(position: Int) {
        if (!mPagingLoadingState) {
            mPagingLoadingState = true
            mPagingLoadingPosition = position
            items.add(position, PAGING_LOAD)
            notifyItemInserted(position)
        }
    }

    fun hidePagingLoading(failFetchPaging: Boolean = false) {
        if (mPagingLoadingPosition > 0) {
            if (!failFetchPaging) {
                items.removeAt(mPagingLoadingPosition)
                notifyItemRemoved(mPagingLoadingPosition)
                mPagingLoadingState = false
            }
        }
    }

    fun failFetchPaging() {
        items[mPagingLoadingPosition] = PAGING_FAIL
        notifyItemChanged(mPagingLoadingPosition, PAGING_FAIL)
    }

    fun onTryAgainPaging() {
        items[mPagingLoadingPosition] = PAGING_LOAD
        notifyItemChanged(mPagingLoadingPosition, PAGING_LOAD)
    }

    fun resetPaging() {
        mPagingLoadingPosition = 0
        mPagingLoadingState = false
    }

    override fun getItemCount() = items.size

}