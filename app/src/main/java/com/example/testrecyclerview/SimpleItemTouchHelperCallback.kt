package com.example.testrecyclerview

import android.graphics.Canvas
import android.util.Log
import android.widget.TextView
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.abs
import kotlin.math.log

/**
 * ...
 * @author 1799796122 (Ran Sixiang)
 * @email 1799796122@qq.com
 * @date 2022/3/14
 */
class SimpleItemTouchHelperCallback(val mAdapter: ItemTouchHelperAdapter) :
    ItemTouchHelper.Callback() {
    val TAG = "RQ"
    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {
        val dragFlags = ItemTouchHelper.UP.or(ItemTouchHelper.DOWN)
        val swipeFlags = ItemTouchHelper.LEFT.or(ItemTouchHelper.RIGHT)
        return makeMovementFlags(dragFlags, swipeFlags)
    }

    override fun isLongPressDragEnabled(): Boolean {
        return true
    }

    override fun isItemViewSwipeEnabled(): Boolean {
        return true
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        mAdapter.onItemMoved(viewHolder.adapterPosition, target.adapterPosition)
        return true
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

    }


    private var viewWidth: Int? = null
    var mDistance: Float? = null
    var lastDX: Float = 0f//因为每次dX是从0开始的，所以滑动防止叠加，要减去上一次的dX
    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        //判断滑动状态
        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
            //记录开始位置，
            mDistance = (viewHolder.itemView.scrollX).toFloat()
            //获取删除按钮的位置
            viewWidth = viewHolder.itemView.findViewById<TextView>(R.id.delete).width
            //手指滑动
            if (isCurrentlyActive ) {
                //计算滑动到达的位置
                mDistance = mDistance!! - dX + lastDX
                //超出处理
                 if (mDistance!!<0){
                    mDistance = 0f
                }else if (mDistance!!>viewWidth!!){
                    mDistance = viewWidth?.toFloat()
                }
                //滑动
                viewHolder.itemView.scrollTo(mDistance!!.toInt(), dY.toInt())
                lastDX = dX

            //惯性滑动，回弹
            } else {
                lastDX = if (viewHolder.itemView.scrollX >= viewWidth!! / 2) {
                    viewHolder.itemView.scrollTo(viewWidth!!, dY.toInt())
                    0f//重置dX
                } else {
                    viewHolder.itemView.scrollTo(0, dY.toInt())
                    0f
                }

            }

            //拖拽响应系统的事件
        } else if (actionState == ItemTouchHelper.ACTION_STATE_DRAG){
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
        }
    }

    override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
        super.clearView(recyclerView, viewHolder)
        viewHolder.itemView.scrollX = 0
    }


}