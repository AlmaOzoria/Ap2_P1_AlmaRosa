package edu.ucne.ap2_p1_almarosa.data.tareas

import edu.ucne.ap2_p1_almarosa.data.local.entities.TareaEntity

sealed class TareaEvent {
    data class OnDescripcionChange(val descripcion: String) : TareaEvent()
    data class OnTiempoChange(val tiempo: Int) : TareaEvent()
    data class OnEditar(val tarea: TareaEntity) : TareaEvent()
    data class OnEliminar(val tarea: TareaEntity) : TareaEvent()
    object OnGuardar : TareaEvent()
    object OnCancelar : TareaEvent()
    object ClearMessages : TareaEvent()
}
