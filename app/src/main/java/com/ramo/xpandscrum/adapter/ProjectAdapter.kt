package com.ramo.xpandscrum.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ramo.xpandscrum.databinding.ItemProjectBinding
import com.ramo.xpandscrum.model.Project

/*
 projeleri listelemek için kullanılır
 tıklama
 silme tıklama
 düzenleme tıklama
 işlemlerini içerir
 */
class ProjectListAdapter(
    private val onItemClick: (project: Project) -> Unit,
    private val onEditClick: (project: Project) -> Unit,
    private val onDeleteClick: (project: Project) -> Unit
) :
    ListAdapter<Project, ProjectViewHolder>(ProjectComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProjectViewHolder {
        val itemBinding =
            ItemProjectBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProjectViewHolder(itemBinding, onItemClick, onEditClick, onDeleteClick)
    }

    override fun onBindViewHolder(holder: ProjectViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current)
    }

}

/*
Bir tane itemi inflate eder.
ilgili öğeyi ekrana bind eder
 */
class ProjectViewHolder(
    private val itemBinding: ItemProjectBinding,
    private val onItemClick: (project: Project) -> Unit,
    private val onEditClick: (project: Project) -> Unit,
    private val onDeleteClick: (project: Project) -> Unit
) :
    RecyclerView.ViewHolder(itemBinding.root) {

    fun bind(project: Project) {
        with(itemBinding) {
            itemView.setOnClickListener { onItemClick(project) }
            itemBinding.btnEdit.setOnClickListener { onEditClick(project) }
            itemBinding.btnDelete.setOnClickListener { onDeleteClick(project) }
            projectName.text = project.name
        }
    }

}

/*
karşılaştırma işlemi yapar
 */
class ProjectComparator : DiffUtil.ItemCallback<Project>() {
    override fun areItemsTheSame(oldItem: Project, newItem: Project): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(oldItem: Project, newItem: Project): Boolean {
        return oldItem == newItem
    }
}