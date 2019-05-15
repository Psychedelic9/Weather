package cn.wj.android.common.adapter.recyclerview

import android.graphics.Canvas
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

class BaseRvItemTouchCallback(var adapter: ItemTouchHelperAdapter, private var canMove: Boolean = false) :
    ItemTouchHelper.Callback() {

    override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
        return if (canMove) makeMovementFlags(ItemTouchHelper.UP or ItemTouchHelper.DOWN, ItemTouchHelper.LEFT)
        else makeMovementFlags(0, ItemTouchHelper.LEFT)
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        adapter.onItemMove(viewHolder.adapterPosition, target.adapterPosition)
        return true
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        adapter.onItemSwipe(viewHolder.adapterPosition)
    }

    override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
        super.clearView(recyclerView, viewHolder)
        viewHolder.itemView.scrollX = 0
        val rl = (viewHolder.itemView as ViewGroup).getChildAt(1) as ViewGroup
        rl.getChildAt(0).visibility = View.VISIBLE
        rl.getChildAt(1).visibility = View.GONE
    }

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
            val viewGroup = viewHolder.itemView as ViewGroup
            val rl = viewGroup.getChildAt(1) as ViewGroup
            val params = rl.layoutParams
            params.width = recyclerView.width / 3
            rl.layoutParams = params
            when {
                Math.abs(dX) <= recyclerView.width / 3 -> {
                    viewHolder.itemView.scrollTo((-dX).toInt(), 0)
                    rl.getChildAt(0).visibility = View.VISIBLE
                    rl.getChildAt(1).visibility = View.GONE
                }
                Math.abs(dX) > recyclerView.width / 2 -> {
                    rl.getChildAt(0).visibility = View.GONE
                    rl.getChildAt(1).visibility = View.VISIBLE
                }
                else -> {
                    rl.getChildAt(0).visibility = View.VISIBLE
                    rl.getChildAt(1).visibility = View.GONE
                }
            }
        } else {
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
        }
    }

    interface ItemTouchHelperAdapter {
        fun onItemMove(fromPosition: Int, toPosition: Int)
        fun onItemSwipe(position: Int)
    }
}