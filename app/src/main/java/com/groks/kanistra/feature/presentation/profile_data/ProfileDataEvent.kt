package com.groks.kanistra.feature.presentation.profile_data

sealed class ProfileDataEvent {
    object LogOut: ProfileDataEvent()
    object GetUserData: ProfileDataEvent()
}