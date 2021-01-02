package com.ramo.xpandscrum.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ramo.xpandscrum.databinding.ItemProjectBinding
import com.ramo.xpandscrum.model.Project


class ProjectListAdapter(private val onClick: (project: Project) -> Unit) :
    ListAdapter<Project, ProjectViewHolder>(ProjectComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProjectViewHolder {
        val itemBinding =
            ItemProjectBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProjectViewHolder(itemBinding, onClick)
    }

    override fun onBindViewHolder(holder: ProjectViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current)
    }

}

class ProjectViewHolder(
    private val itemBinding: ItemProjectBinding,
    private val onClick: (project: Project) -> Unit
) :
    RecyclerView.ViewHolder(itemBinding.root) {

    fun bind(project: Project) {
        with(itemBinding) {
            itemView.setOnClickListener { onClick(project) }
            projectName.text = project.name
        }
    }

//    companion object {
//        fun create(parent: ViewGroup, onClick: (project: Project) -> Unit): ProjectViewHolder {
//            val view: View = LayoutInflater.from(parent.context)
//                .inflate(R.layout.item_project, parent, false)
//            return ProjectViewHolder(view, onClick)
//        }
//    }
}

class ProjectComparator : DiffUtil.ItemCallback<Project>() {
    override fun areItemsTheSame(oldItem: Project, newItem: Project): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(oldItem: Project, newItem: Project): Boolean {
        return oldItem == newItem
    }
}