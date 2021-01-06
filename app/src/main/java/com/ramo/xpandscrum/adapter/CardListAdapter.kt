package com.ramo.xpandscrum.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ramo.xpandscrum.databinding.ItemBoardBinding
import com.ramo.xpandscrum.model.Card


class CardListAdapter(
    private val onItemClick: (card: Card) -> Unit,
    private val onEditClick: (card: Card) -> Unit,
    private val onMoveClick: (card: Card) -> Unit
) :
    ListAdapter<Card, CardViewHolder>(CardComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val itemBinding =
            ItemBoardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CardViewHolder(itemBinding, onItemClick, onEditClick, onMoveClick)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current)
    }

}

class CardViewHolder(
    private val itemBinding: ItemBoardBinding,
    private val onItemClick: (card: Card) -> Unit,
    private val onEditClick: (card: Card) -> Unit,
    private val onMoveClick: (card: Card) -> Unit
) :
    RecyclerView.ViewHolder(itemBinding.root) {

    fun bind(card: Card) {
        with(itemBinding) {
            cardName.text = card.name
            btnEdit.setOnClickListener { onEditClick(card) }
            btnMove.setOnClickListener { onMoveClick(card) }
            itemView.setOnClickListener { onItemClick(card) }
        }
    }

}

class CardComparator : DiffUtil.ItemCallback<Card>() {
    override fun areItemsTheSame(oldItem: Card, newItem: Card): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(oldItem: Card, newItem: Card): Boolean {
        return oldItem == newItem
    }
}