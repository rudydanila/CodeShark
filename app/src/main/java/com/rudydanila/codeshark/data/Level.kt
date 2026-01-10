package com.rudydanila.codeshark.data

data class Level(
    val id: Int,
    val chapter: Int,
    val taskNumber: Int,
    var isCompleted: Boolean = false,
)
