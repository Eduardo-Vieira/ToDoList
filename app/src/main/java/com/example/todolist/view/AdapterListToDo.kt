package com.example.todolist.view

import android.content.Context
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.R
import com.example.todolist.db.model.ToDo
import kotlinx.android.synthetic.main.view_item_todo.view.*


class AdapterListToDo(
    private var itens:List<ToDo> = listOf()
): RecyclerView.Adapter<AdapterListToDo.ItemViewHolder>() {

    private lateinit var onRemoverClickListener:OnClickListener
    private lateinit var onEditClickListener:OnClickListener
    private lateinit var onCheckedClickListener:OnClickListener
    private lateinit var mCtx:Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        mCtx = parent.context
        val view = LayoutInflater.from(parent.context)
           .inflate(R.layout.view_item_todo, parent,false)
        return ItemViewHolder(view)
    }
    override fun getItemCount(): Int = itens.size

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.setItem(itens[position])
    }

    fun update(itens:List<ToDo>){
        this.itens = itens
        notifyDataSetChanged()
    }

    fun getItemList(position: Int):ToDo {
        return itens[position]
    }

    fun setOnRemoveClickListener(onClickListener:OnClickListener){
        this.onRemoverClickListener = onClickListener
    }

    fun setOnEditClickListener(onClickListener:OnClickListener){
        this.onEditClickListener = onClickListener
    }

    fun setOnCheckedClickListener(onClickListener:OnClickListener){
        this.onCheckedClickListener = onClickListener
    }

    inner class ItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val txtTask: CheckBox = itemView.txtTask
        private val bntOptions: ImageView = itemView.bntOptions
        private var item:ToDo? = null

        init {
            txtTask.setOnCheckedChangeListener { _, isChecked ->
                setTxtTaskPaintFlags(isChecked)
                this.item?.checked = isChecked
                onCheckedClickListener.onItemClick(this.item, adapterPosition)
            }

            bntOptions.setOnClickListener {
                val popup = PopupMenu(mCtx, bntOptions)
                popup.inflate(R.menu.options_menu_item)
                popup.setOnMenuItemClickListener { item ->
                    when (item.itemId) {
                        R.id.mnu_editar_item -> {
                            onEditClickListener.onItemClick(this.item, adapterPosition)
                        }
                        R.id.mnu_remover_item -> {
                            onRemoverClickListener.onItemClick(this.item, adapterPosition)
                        }
                    }
                    false
                }
                popup.show()
            }
        }

        private fun setTxtTaskPaintFlags(isChecked:Boolean){
            txtTask.paintFlags = if(isChecked) Paint.STRIKE_THRU_TEXT_FLAG else 0
        }

        fun setItem(item:ToDo){
           this.item = item
           txtTask.text = item.task
           item.checked?.let { isChecked ->
               txtTask.isChecked = isChecked
               setTxtTaskPaintFlags(isChecked)
           }
       }
    }
}