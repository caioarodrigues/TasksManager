package com.example.tasksmanager.classes

import com.example.tasksmanager.Task

class DB private constructor() {
    private val _mockTasks: MutableList<Task> = mutableListOf<Task>()
    init {}

    companion object {
        @Volatile
        private var instance: DB? = null

        fun getInstance(): DB {
            return instance ?: synchronized(this) {
                instance ?: DB().also { instance = it }
            }
        }
    }

    fun populate () {
        _mockTasks.add(Task("Comprar mantimentos", "Comprar leite, pão e ovos"))
        _mockTasks.add(Task("Estudar Kotlin", "Completar o curso básico de Kotlin"))
        _mockTasks.add(Task("Fazer exercícios", "Correr 5km no parque", isCompleted = true))
        _mockTasks.add(Task("Limpar a casa", "Organizar o quarto e limpar a cozinha"))
        _mockTasks.add(Task("Revisar projeto", "Revisar os requisitos do projeto de tarefas"))
        _mockTasks.add(Task("Marcar consulta", "Marcar consulta com o dentista"))
        _mockTasks.add(Task("Planejar viagem", "Pesquisar destinos e comprar passagens"))

        println("Database just got populated")
    }

    fun getTasks (): MutableList<Task> {
        return _mockTasks
    }
}
