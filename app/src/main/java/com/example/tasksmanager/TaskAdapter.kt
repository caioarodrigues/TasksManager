package com.seuprojeto.taskmanager

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.tasksmanager.R

class TaskAdapter(
    private val tasks: MutableList<Task>,
    private val onEdit: (Int) -> Unit,
    private val onDelete: (Int) -> Unit
) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    class TaskViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvTitle: TextView = view.findViewById(R.id.tvTaskTitle)
        val tvDescription: TextView = view.findViewById(R.id.tvTaskDescription)
        val cbCompleted: CheckBox = view.findViewById(R.id.cbTaskCompleted)
        val btnEdit: Button = view.findViewById(R.id.btnEditTask)
        val btnDelete: Button = view.findViewById(R.id.btnDeleteTask)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_task, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = tasks[position]
        holder.tvTitle.text = task.title
        holder.tvDescription.text = task.description
        holder.cbCompleted.isChecked = task.isCompleted

        holder.cbCompleted.setOnCheckedChangeListener { _, isChecked ->
            task.isCompleted = isChecked
        }

        holder.btnEdit.setOnClickListener {
            onEdit(position)
        }

        holder.btnDelete.setOnClickListener {
            onDelete(position)
        }
    }

    override fun getItemCount(): Int = tasks.size
}
