package com.ramo.xpandscrum.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ramo.xpandscrum.databinding.ItemCardStatusBinding
import com.ramo.xpandscrum.getCurrentDate
import com.ramo.xpandscrum.model.CardStatus


class CardStatusListAdaper(
    private val onItemClick: (cardStatus: CardStatus) -> Unit,
    private val onDeleteClick: (cardStatus: CardStatus) -> Unit
) :
    ListAdapter<CardStatus, CardStatusViewHolder>(CardStatusComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardStatusViewHolder {
        val itemBinding =
            ItemCardStatusBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CardStatusViewHolder(itemBinding, onItemClick, onDeleteClick)
    }

    override fun onBindViewHolder(holder: CardStatusViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current)
    }

}

class CardStatusViewHolder(
    private val itemBinding: ItemCardStatusBinding,
    private val onItemClick: (cardStatus: CardStatus) -> Unit,
    private val onDeleteClick: (cardStatus: CardStatus) -> Unit
) :
    RecyclerView.ViewHolder(itemBinding.root) {

    fun bind(cardStatus: CardStatus) {
        with(itemBinding) {
            itemView.setOnClickListener { onItemClick(cardStatus) }
            itemBinding.btnDelete.setOnClickListener { onDeleteClick(cardStatus) }
            this.cardstatusDate.text = cardStatus.date?.getCurrentDate()
            this.cardstatusType.text = cardStatus.cardType?.name
            this.cardstatusDesc.text = cardStatus.description.toString()
            this.cardstatusUser.text = cardStatus.userId.toString()
        }
    }
}

class CardStatusComparator : DiffUtil.ItemCallback<CardStatus>() {
    override fun areItemsTheSame(oldItem: CardStatus, newItem: CardStatus): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(oldItem: CardStatus, newItem: CardStatus): Boolean {
        return oldItem == newItem
    }
}