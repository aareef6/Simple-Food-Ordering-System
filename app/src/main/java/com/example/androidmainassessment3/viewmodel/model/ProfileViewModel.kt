package com.example.androidmainassessment3.viewmodel.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidmainassessment3.repository.ProfileRepo
import com.example.androidmainassessment3.room.model.Profile
import kotlinx.coroutines.launch

class ProfileViewModel(var profileRepo: ProfileRepo): ViewModel() {

    fun getProfileVM(name: String): LiveData<Profile> = profileRepo.getAllProfile(name)


    fun insertProfile(profile: Profile) =
        viewModelScope.launch {
            profileRepo.InsertProfile(profile)
        }

    fun checkProfile(name : String, email:String,onResult:(Boolean)-> Unit) =
        viewModelScope.launch {
            var isValid= profileRepo.checkProfile(name,email)
            onResult(isValid)
        }

    fun checkisAdmin( email:String,onResult:(Boolean)-> Unit) =
        viewModelScope.launch {
            var isValid= profileRepo.checkisAdmin(email)
            onResult(isValid)
        }

    fun checkEmail(email:String,onResult:(Boolean)-> Unit) =
        viewModelScope.launch {
            var isValid= profileRepo.checkEmail(email)
            onResult(isValid)
        }

    fun updateProfile(profile: Profile) =
        viewModelScope.launch {
            profileRepo.updateProfile(profile)
        }

}