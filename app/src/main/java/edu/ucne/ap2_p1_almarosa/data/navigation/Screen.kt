package edu.ucne.ap2_p1_almarosa.data.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Screen {
    @Serializable
    data object Sistema: Screen()
    @Serializable
    data object List : Screen()
}