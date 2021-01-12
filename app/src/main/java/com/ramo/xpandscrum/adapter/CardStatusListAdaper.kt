package com.ramo.xpandscrum.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ramo.xpandscrum.databinding.ItemCardStatusBinding
import com.ramo.xpandscrum.getCurrentDate
import com.ramo.xpandscrum.model.CardStatusAndUser


class CardStatusListAdaper(
    private val onItemClick: (cardStatusAndUser: CardStatusAndUser) -> Unit,
    private val onDeleteClick: (cardStatusAndUser: CardStatusAndUser) -> Unit
) :
    ListAdapter<CardStatusAndUser, CardStatusViewHolder>(CardStatusComparator()) {

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
    private val onItemClick: (cardStatusAndUser: CardStatusAndUser) -> Unit,
    private val onDeleteClick: (cardStatusAndUser: CardStatusAndUser) -> Unit
) :
    RecyclerView.ViewHolder(itemBinding.root) {

    fun bind(cardStatusAndUser: CardStatusAndUser) {
        with(itemBinding) {
            itemView.setOnClickListener { onItemClick(cardStatusAndUser) }
            itemBinding.btnDelete.setOnClickListener { onDeleteClick(cardStatusAndUser) }
            this.cardstatusDate.text = cardStatusAndUser.cardStatus?.date?.getCurrentDate()
            this.cardstatusType.text = cardStatusAndUser.cardStatus?.cardType?.name
            this.cardstatusDesc.text = cardStatusAndUser.cardStatus?.description.toString()
            this.cardstatusUser.text = cardStatusAndUser.user?.name
        }
    }
}

class CardStatusComparator : DiffUtil.ItemCallback<CardStatusAndUser>() {
    override fun areItemsTheSame(oldItem: CardStatusAndUser, newItem: CardStatusAndUser): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(oldItem: CardStatusAndUser, newItem: CardStatusAndUser): Boolean {
        return oldItem == newItem
    }
}