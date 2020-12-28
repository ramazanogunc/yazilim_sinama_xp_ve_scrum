package com.ramo.xpandscrum.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ramo.xpandscrum.R
import com.ramo.xpandscrum.model.Project


class ProjectListAdapter(private val onClick: (project: Project) -> Unit) :
    ListAdapter<Project, ProjectViewHolder>(ProjectComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProjectViewHolder {
        return ProjectViewHolder.create(parent, onClick)
    }

    override fun onBindViewHolder(holder: ProjectViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current)
    }


}

class ProjectViewHolder(itemView: View, private val onClick: (project: Project) -> Unit) :
    RecyclerView.ViewHolder(itemView) {
    private val projectName: TextView = itemView.findViewById(R.id.projectName)

    fun bind(project: Project) {
        itemView.setOnClickListener { onClick(project) }
        projectName.text = project.name
    }

    companion object {
        fun create(parent: ViewGroup, onClick: (project: Project) -> Unit): ProjectViewHolder {
            val view: View = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_project, parent, false)
            return ProjectViewHolder(view, onClick)
        }
    }
}

class ProjectComparator : DiffUtil.ItemCallback<Project>() {
    override fun areItemsTheSame(oldItem: Project, newItem: Project): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(oldItem: Project, newItem: Project): Boolean {
        return oldItem == newItem
    }
}