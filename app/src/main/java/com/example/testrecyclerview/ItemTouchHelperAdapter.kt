package com.example.testrecyclerview

/**
 * ...
 * @author 1799796122 (Ran Sixiang)
 * @email 1799796122@qq.com
 * @date 2022/3/14
 */
interface ItemTouchHelperAdapter {
    fun onItemMoved(fromPosition:Int,toPosition:Int)
    fun onItemDismiss(position:Int)
}