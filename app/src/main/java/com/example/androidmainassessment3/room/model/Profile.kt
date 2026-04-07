package com.example.androidmainassessment3.room.model


import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Profile(
    @PrimaryKey(autoGenerate = true)
    var id: Int =0,
    var name: String,
    var pass: String,
    var email: String="",
    var isAdmin: Boolean=false
)
