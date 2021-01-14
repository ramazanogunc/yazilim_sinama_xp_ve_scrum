package com.ramo.xpandscrum.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ramo.xpandscrum.databinding.ItemBoardBinding
import com.ramo.xpandscrum.model.Card

/*
bir tane iş kartı için kullnaılan adapter
öğe tıklaması
silme tıklaması
taşıma tıklaması içerir
 */
class CardListAdapter(
    private val onItemClick: (card: Card) -> Unit,
    private val onEditClick: (card: Card) -> Unit,
    private val onMoveClick: (card: Card, v: View) -> Unit
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
/*
view i inflate eder.
gerekli verileri ekranda gösterir.
click işlerini yapar
 */
class CardViewHolder(
    private val itemBinding: ItemBoardBinding,
    private val onItemClick: (card: Card) -> Unit,
    private val onDeleteClick: (card: Card) -> Unit,
    private val onMoveClick: (card: Card, v: View) -> Unit
) :
    RecyclerView.ViewHolder(itemBinding.root) {

    fun bind(card: Card) {
        with(itemBinding) {
            cardName.text = card.name
            btnDele.setOnClickListener { onDeleteClick(card) }
            btnMove.setOnClickListener { onMoveClick(card, it) }
            itemView.setOnClickListener { onItemClick(card) }
        }
    }

}
/*
karşılaştırma işlemi yapar
 */
class CardComparator : DiffUtil.ItemCallback<Card>() {
    override fun areItemsTheSame(oldItem: Card, newItem: Card): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(oldItem: Card, newItem: Card): Boolean {
        return oldItem == newItem
    }
}