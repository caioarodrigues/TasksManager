package com.seuprojeto.taskmanager

data class Task(
    var title: String,
    var description: String,
    var isCompleted: Boolean = false
)
