package com.example.tasksmanager

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tasksmanager.classes.DB

class MainActivity : AppCompatActivity() {

    private val db: DB = DB.getInstance()
    private val tasks = mutableListOf<Task>()
    private lateinit var taskAdapter: TaskAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        db.populate()
        tasks.addAll(db.getTasks())

        val recyclerView: RecyclerView = findViewById(R.id.recyclerViewTasks)
        recyclerView.layoutManager = GridLayoutManager(this, 2)
        taskAdapter = TaskAdapter(tasks, ::editTask, ::deleteTask)
        recyclerView.adapter = taskAdapter

        val btnAddTask: Button = findViewById(R.id.btnAddTask)
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
                val title = etTitle.text.toString().trim()
                val description = etDescription.text.toString().trim()

                if (title.isNotEmpty() && description.isNotEmpty()) {
                    if (task == null) {
                        val newTask = Task(title, description)
                        tasks.add(newTask)
                        taskAdapter.notifyItemInserted(tasks.size - 1)
                    } else {
                        task.title = title
                        task.description = description
                        position?.let { taskAdapter.notifyItemChanged(it) }
                    }
                }
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }

    private fun createTask () {

    }
    private fun editTask(position: Int) {
        showTaskDialog(tasks[position], position)
    }

    private fun deleteTask(position: Int) {
        tasks.removeAt(position)
        taskAdapter.notifyItemRemoved(position)
    }
}
