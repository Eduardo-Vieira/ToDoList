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

    private lateinit var onRemoverClickListener:OnClickListener
    private lateinit var onEditClickListener:OnClickListener

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

    fun getItemList(position: Int):ToDo {
        return listItens[position]
    }

    fun setOnRemoveClickListener(onClickListener:OnClickListener){
        this.onRemoverClickListener = onClickListener
    }

    fun setOnEditClickListener(onClickListener:OnClickListener){
        this.onEditClickListener = onClickListener
    }

   inner class ItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val txtTask: TextView = itemView.txtTask
        private val bntRemover:Button = itemView.bntRemover
        private val btnEditar:Button = itemView.btnEditar
        private var item:ToDo? = null

       init {
            bntRemover.setOnClickListener {
                onRemoverClickListener.onItemClick(this.item, adapterPosition)
            }

            btnEditar.setOnClickListener {
                onEditClickListener.onItemClick(this.item, adapterPosition)
            }
        }

        fun setItens(item:ToDo){
            this.item = item
            txtTask.text = item.task
        }
    }
}