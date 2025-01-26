package com.example.tasksmanager

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView

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

        updateTaskStyle(holder, task.isCompleted)

        holder.cbCompleted.setOnCheckedChangeListener { _, isChecked ->
            task.isCompleted = isChecked
            updateTaskStyle(holder, isChecked)
        }

        holder.btnEdit.setOnClickListener {
            onEdit(position)
        }

        holder.btnDelete.setOnClickListener {
            onDelete(position)
        }
    }

    override fun getItemCount(): Int = tasks.size

    private fun updateTaskStyle(holder: TaskViewHolder, isCompleted: Boolean) {
        if (isCompleted) {
            holder.tvTitle.paintFlags = holder.tvTitle.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            holder.tvDescription.paintFlags =
                holder.tvDescription.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            holder.itemView.alpha = 0.6f
            holder.btnEdit.isVisible = false
            holder.btnDelete.isVisible = false
        } else {
            holder.tvTitle.paintFlags =
                holder.tvTitle.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
            holder.tvDescription.paintFlags =
                holder.tvDescription.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
            holder.itemView.alpha = 1.0f
            holder.btnEdit.isVisible = true
            holder.btnDelete.isVisible = true
        }
    }
}
