package com.example.testrecyclerview

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import java.util.*

/**
 * ...
 * @author 1799796122 (Ran Sixiang)
 * @email 1799796122@qq.com
 * @date 2022/3/14
 */
class MyRecyclerViewAdapter(private val context: Context, private val mData:MutableList<String>):RecyclerView.Adapter<MyRecyclerViewAdapter.MyViewHolder>(),ItemTouchHelperAdapter {
        val TAG = "RQ"
    inner class MyViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        var tv:TextView? = null
        var dtv:TextView? = null
            init {
                tv = itemView.findViewById(R.id.textView)
                tv?.setOnClickListener { Toast.makeText(context,"点击了${this.adapterPosition}位置",Toast.LENGTH_SHORT).show() }
                dtv = itemView.findViewById(R.id.delete)
                dtv?.setOnClickListener {
                    onItemDismiss(this.adapterPosition)
                    Log.d(TAG, ": yes")
                }

            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view =LayoutInflater.from(context).inflate(R.layout.item_rv,parent,false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.tv?.text = mData[position]
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    override fun onItemMoved(fromPosition: Int, toPosition: Int) {
        Collections.swap(mData,fromPosition,toPosition)
        notifyItemMoved(fromPosition,toPosition)
    }

    override fun onItemDismiss(position: Int) {
        mData.removeAt(position)
        notifyItemRemoved(position)
    }
}