package io.luxus.animation.presentation.view.adapter

import androidx.recyclerview.widget.RecyclerView

abstract class RecyclerViewAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val listener: Listener = object: Listener {
        override fun onItemChangedSync(position: Int) {
            notifyItemChanged(position)
        }

        override fun onRangeInsertedSync(positionStart: Int, itemCount: Int) {
            notifyItemRangeInserted(positionStart, itemCount)
        }

        override fun onRemovedSync(position: Int) {
            notifyItemRemoved(position)
        }

        override fun onRangeRemovedSync(positionStart: Int, itemCount: Int) {
            notifyItemRangeRemoved(positionStart, itemCount)
        }
    }

    interface Listener {
        fun onItemChangedSync(position: Int)
        fun onRangeInsertedSync(positionStart: Int, itemCount: Int)
        fun onRemovedSync(position: Int)
        fun onRangeRemovedSync(positionStart: Int, itemCount: Int)
    }

}