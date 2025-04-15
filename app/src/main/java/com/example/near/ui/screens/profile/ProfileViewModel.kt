package com.example.near.ui.screens.profile

import androidx.lifecycle.ViewModel
import com.example.near.domain.models.User

class ProfileViewModel : ViewModel() {
    val avatarUrl: String = "https://avatars.mds.yandex.net/get-kinopoisk-image/1599028/4adf61aa-3cb7-4381-9245-523971e5b4c8/300x450"
    val user: User = User(
        "f8f812d3-eb1a-4e34-8fd7-c640de4fbb41",
        "Максим",
        "Сидоренко",
        "2003-04-26",
        21,
        "Россия",
        "Краснодар",
        "Северный",
        "2025-04-15",
        listOf(),
        listOf(),
        listOf(),
        listOf("Telegram", )
    )
}