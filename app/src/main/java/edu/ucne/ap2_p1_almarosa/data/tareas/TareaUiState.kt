package edu.ucne.ap2_p1_almarosa.data.tareas

import edu.ucne.ap2_p1_almarosa.data.local.entities.TareaEntity

data class TareaUiState (
    val tareaid: Int? = null,
    val descripcion: String = "",
    val tiempo: Int = 0,
    val errorMessage: String? = null,
    val tareaEditandoId: Int? = null,
    val successMessage: String? = null,
    val tarea: List<TareaEntity> = emptyList()
)


