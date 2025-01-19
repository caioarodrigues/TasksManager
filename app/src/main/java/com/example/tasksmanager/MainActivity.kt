package com.example.tasksmanager

import com.example.tasksmanager.classes.DB
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private val db: DB = DB.getInstance()
    private val tasks = mutableListOf<Task>()
    private lateinit var taskAdapter: TaskAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        db.populate()
        val mockedTasks = db.getTasks()

        for (mock in mockedTasks) {
            tasks.add(mock)
        }

        val recyclerView: RecyclerView = findViewById(R.id.recyclerViewTasks)
        val btnAddTask: Button = findViewById(R.id.btnAddTask)

        taskAdapter = TaskAdapter(tasks, ::editTask, ::deleteTask)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = taskAdapter

        btnAddTask.setOnClickListener {
            showTaskDialog()
        }
    }

    private fun showTaskDialog(task: Task? = null, position: Int? = null) {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_task, null)
        val etTitle = dialogView.findViewById<EditText>(R.id.etTaskTitle)
        val etDescription = dialogView.findViewById<EditText>(R.id.etTaskDescription)

        if (task != null) {
            etTitle.setText(task.title)
            etDescription.setText(task.description)
        }

        AlertDialog.Builder(this)
            .setTitle(if (task == null) "Adicionar Tarefa" else "Editar Tarefa")
            .setView(dialogView)
            .setPositiveButton("Salvar") { _, _ ->
                val title = etTitle.text.toString()
                val description = etDescription.text.toString()

                if (task == null) {
                    tasks.add(Task(title, description))
                } else {
                    task.title = title
                    task.description = description
                    position?.let { taskAdapter.notifyItemChanged(it) }
                }
                taskAdapter.notifyDataSetChanged()
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }

    private fun editTask(position: Int) {
        showTaskDialog(tasks[position], position)
    }

    private fun deleteTask(position: Int) {
        tasks.removeAt(position)
        taskAdapter.notifyItemRemoved(position)
    }
}
