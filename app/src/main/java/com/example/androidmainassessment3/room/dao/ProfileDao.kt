package com.example.androidmainassessment3.room.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Update
import com.example.androidmainassessment3.room.model.Profile

@Dao
interface ProfileDao {

    @Query("""
    INSERT INTO Profile (name, pass,email,isAdmin)
    SELECT :name, :pass, :email, :isAdmin
    WHERE NOT EXISTS (SELECT 1 FROM Profile WHERE email = :email)
""")
    suspend fun addProfile(name: String, pass: String, email: String,isAdmin: Boolean)

    @Query("Select * from Profile where name=:name LIMIT 1")
    fun getProfile(name: String) : LiveData<Profile>

    @Query("Select Count(*) from Profile where email=:name AND pass = :pass")
    suspend fun login(name: String,pass: String): Int

    @Query("Select isAdmin from Profile where email=:name ")
    suspend fun CheckisAdmin(name: String): Boolean

    @Query("Select Count(*) from Profile where email=:email")
    suspend fun CheckEmail(email: String): Int

    @Update
    suspend fun updateProfile(profile: Profile)
}