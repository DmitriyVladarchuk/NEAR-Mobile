package com.example.near.domain.models

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
        bgColor = "#2D384A"
    ),
    EmergencyType(
        id = 2,
        title = "Flood",
        color = "#223366",
        bgColor = "#223366"
    ),
    EmergencyType(
        id = 3,
        title = "Tsunami",
        color = "#214678",
        bgColor = "#214678"
    ),
    EmergencyType(
        id = 4,
        title = "Hurricane",
        color = "#595B82",
        bgColor = "#595B82"
    ),
    EmergencyType(
        id = 5,
        title = "Forest fire",
        color = "#FF4C2B",
        bgColor = "#FF4C2B"
    ),
    EmergencyType(
        id = 6,
        title = "Terrorist attack",
        color = "#965443",
        bgColor = "#965443"
    )
)
