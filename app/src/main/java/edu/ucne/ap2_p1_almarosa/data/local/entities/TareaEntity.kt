package edu.ucne.ap2_p1_almarosa.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Tarea")
data class TareaEntity (
    @PrimaryKey
    val tareaid: Int? = null,
    val descripcion: String = "",
    val tiempo: Int = 0
)