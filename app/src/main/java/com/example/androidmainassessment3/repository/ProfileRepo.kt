package com.example.androidmainassessment3.repository

import androidx.lifecycle.LiveData
import com.example.androidmainassessment3.room.dao.ProfileDao
import com.example.androidmainassessment3.room.model.Profile

class ProfileRepo(var profileDao: ProfileDao) {



    fun getAllProfile(name: String): LiveData<Profile> = profileDao.getProfile(name)


    suspend fun InsertProfile(profile: Profile) {
        return profileDao.addProfile(profile.name,profile.pass,profile.email,profile.isAdmin)
    }

    suspend fun checkProfile(name : String, pass:String): Boolean {
        return profileDao.login(name,pass)>0
    }

    suspend fun checkisAdmin(name : String): Boolean {
        return profileDao.CheckisAdmin(name)
    }

    suspend fun checkEmail(name : String): Boolean {
        return profileDao.CheckEmail(name)>0
    }

    suspend fun updateProfile(profile: Profile){
        return profileDao.updateProfile(profile)
    }
}