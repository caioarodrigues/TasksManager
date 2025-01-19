package com.example.tasksmanager

data class Task(
    var title: String,
    var description: String,
    var isCompleted: Boolean = false
)
