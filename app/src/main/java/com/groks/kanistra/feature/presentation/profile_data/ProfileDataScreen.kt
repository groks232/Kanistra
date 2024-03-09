package com.groks.kanistra.feature.presentation.profile_data

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.groks.kanistra.common.ViewState
import com.groks.kanistra.feature.presentation.profile.components.ProfileItem
import com.groks.kanistra.feature.presentation.profile.components.ProfileRow

@Composable
fun ProfileDataScreen(
    viewModel: ProfileDataViewModel = hiltViewModel(),
    onNavigateToLoginScreen: () -> Unit = {}
) {
    val viewState by viewModel.viewState.collectAsState()

    when(viewState) {
        is ViewState.Loading -> {
            Box(modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator(Modifier.align(Alignment.Center))
            }
        }

        is ViewState.NotLoggedIn -> {
            LaunchedEffect(true) {
                onNavigateToLoginScreen()
            }
        }

        is ViewState.LoggedIn -> {
            val state = viewModel.state.value

            Scaffold { paddingValues ->
                when(state.isLoading) {
                    true -> {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(paddingValues)
                        ) {
                            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                        }
                    }
                    false -> {
                        if (state.user != null) {
                            Column(
                                modifier = Modifier
                                    .padding(paddingValues)
                                    .padding(horizontal = 10.dp)
                                    .verticalScroll(rememberScrollState())
                            ) {
                                ProfileRow(state.user.fullName)
                                HorizontalDivider(
                                    modifier = Modifier
                                        .padding(horizontal = 10.dp),
                                    thickness = 1.dp
                                )
                                ProfileItem(mainContent = "Ваш пол", secondaryContent = "Указать")
                                HorizontalDivider(
                                    modifier = Modifier
                                        .padding(horizontal = 10.dp),
                                    thickness = 1.dp
                                )
                                ProfileItem(mainContent = "Ваш email", secondaryContent = "Указать")
                                HorizontalDivider(
                                    modifier = Modifier
                                        .padding(horizontal = 10.dp),
                                    thickness = 1.dp
                                )
                                ProfileItem(mainContent = "Настройка уведомлений")
                                HorizontalDivider(
                                    modifier = Modifier
                                        .padding(horizontal = 10.dp),
                                    thickness = 1.dp
                                )
                                ProfileItem(
                                    mainContent = "Выйти из аккаунта",
                                    textColor = Color.Red,
                                    onRowClick = {
                                        viewModel.onEvent(ProfileDataEvent.LogOut)
                                    }
                                )
                            }
                        }
                        if (state.error.isNotBlank()) {
                            Text(text = state.error)
                        }
                    }
                }
            }
        }
    }
}