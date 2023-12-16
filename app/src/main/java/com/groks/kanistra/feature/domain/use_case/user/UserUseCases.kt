package com.groks.kanistra.feature.domain.use_case.user

data class UserUseCases(
    val deleteUser: DeleteUser,
    val editUserInfo: EditUserInfo,
    val getUserInfo: GetUserInfo
)