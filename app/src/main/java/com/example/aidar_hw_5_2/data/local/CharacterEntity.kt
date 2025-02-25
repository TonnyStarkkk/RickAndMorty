package com.example.aidar_hw_5_2.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "characters")
data class CharacterEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("id")
    val id: Int = 0,
    @ColumnInfo("name")
    val name: String,
    @ColumnInfo("gender")
    val gender: String,
    @ColumnInfo("image")
    val image: String,
    @ColumnInfo("status")
    val status: String
)