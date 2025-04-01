package com.ap.pdfviewerappbookxpert.ui.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ap.pdfviewerappbookxpert.R
import com.ap.pdfviewerappbookxpert.model.Item

class ItemAdapter(private val onDelete: (Item) -> Unit) :
    ListAdapter<Item, ItemAdapter.ItemViewHolder>(DiffCallback()) {

    class ItemViewHolder(view: View, private val onDelete: (Item) -> Unit) :
        RecyclerView.ViewHolder(view) {
        private val itemName: TextView = view.findViewById(R.id.itemName)
        private val deleteButton: ImageButton = view.findViewById(R.id.deleteButton)

        fun bind(item: Item) {
            itemName.text = item.name
            deleteButton.setOnClickListener { onDelete(item) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
        return ItemViewHolder(view, onDelete)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class DiffCallback : DiffUtil.ItemCallback<Item>() {
        override fun areItemsTheSame(oldItem: Item, newItem: Item) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Item, newItem: Item) = oldItem == newItem
    }
}
