package com.example.todolist.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.R
import com.example.todolist.db.model.ToDo
import kotlinx.android.synthetic.main.view_item_todo.view.*

class AdapterListToDo(var listItens:List<ToDo> = arrayListOf()): RecyclerView.Adapter<AdapterListToDo.ItemViewHolder>() {

    private lateinit var onRemoveClickListener:OnRemoveClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context)
           .inflate(R.layout.view_item_todo, parent,false)
        return ItemViewHolder(view)
    }
    override fun getItemCount(): Int = listItens.size

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.setItens(listItens[position])
    }

    fun update(listItens:List<ToDo>){
        this.listItens = listItens
        notifyDataSetChanged()
    }
    fun removeItem(position: Int){
        notifyItemRemoved(position)
    }

    fun getItemList(position: Int):ToDo {
        return listItens[position]
    }

    fun setOnRemoveClickListener(onRemoveClickListener:OnRemoveClickListener){
        this.onRemoveClickListener = onRemoveClickListener
    }

   inner class ItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val txtTask: TextView = itemView.txtTask
        private val bntRemover:Button = itemView.bntRemover
        private var item:ToDo? = null

       init {
            bntRemover.setOnClickListener {
                onRemoveClickListener.onItemClick(this.item, adapterPosition)
            }
        }

        fun setItens(item:ToDo){
            this.item = item
            txtTask.text = item.task
        }
    }
}