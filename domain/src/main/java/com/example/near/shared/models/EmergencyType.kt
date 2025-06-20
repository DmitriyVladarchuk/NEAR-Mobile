package com.example.near.domain.shared.models

data class EmergencyType(
    val id: Int,
    val title: String,
    val color: String,
    val bgColor: String,
)

val emergencyTypes = listOf(
    EmergencyType(
        id = 1,
        title = "Earthquake",
        color = "#2D384A",
        bgColor = "rgb(18%, 22%, 29%, 0.3)"
    ),
    EmergencyType(
        id = 2,
        title = "Flood",
        color = "#223366",
        bgColor = "rgb(13%, 20%, 40%, 0.3)"
    ),
    EmergencyType(
        id = 3,
        title = "Tsunami",
        color = "#214678",
        bgColor = "rgb(13%, 27%, 47%, 0.3)"
    ),
    EmergencyType(
        id = 4,
        title = "Hurricane",
        color = "#595B82",
        bgColor = "rgb(35%, 36%, 51%, 0.3)"
    ),
    EmergencyType(
        id = 5,
        title = "Forest fire",
        color = "#FF4C2B",
        bgColor = "rgb(100%, 30%, 17%, 0.3)"
    ),
    EmergencyType(
        id = 6,
        title = "Terrorist attack",
        color = "#965443",
        bgColor = "rgb(59%, 33%, 26%, 0.3)"
    )
)
