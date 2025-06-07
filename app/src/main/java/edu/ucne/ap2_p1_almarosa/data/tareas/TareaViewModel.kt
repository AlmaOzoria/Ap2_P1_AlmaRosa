package edu.ucne.ap2_p1_almarosa.data.tareas


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.ucne.ap2_p1_almarosa.data.local.entities.TareaEntity
import edu.ucne.ap2_p1_almarosa.data.local.repository.TareaRepository
import edu.ucne.ap2_p1_almarosa.data.tareas.TareaEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TareaViewModel @Inject constructor(
    private val repository: TareaRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(TareaUiState())
    val uiState: StateFlow<TareaUiState> = _uiState

    init {
        viewModelScope.launch {
            repository.getAll().collect { tareas ->
                _uiState.update { it.copy(tarea = tareas) }
            }
        }
    }

    fun onEvent(event: TareaEvent) {
        when (event) {
            is TareaEvent.OnDescripcionChange -> {
                _uiState.update { it.copy(descripcion = event.descripcion) }
            }

            is TareaEvent.OnTiempoChange -> {
                _uiState.update { it.copy(tiempo = event.tiempo) }
            }

            is TareaEvent.OnEditar -> {
                _uiState.update {
                    it.copy(
                        tareaEditandoId = event.tarea.tareaid,
                        descripcion = event.tarea.descripcion,
                        tiempo = event.tarea.tiempo
                    )
                }
            }

            is TareaEvent.OnEliminar -> {
                viewModelScope.launch {
                    repository.delete(event.tarea)
                }
            }

            is TareaEvent.OnGuardar -> {
                val descripcion = uiState.value.descripcion
                val tiempo = uiState.value.tiempo

                if (descripcion.isBlank()) {
                    _uiState.update { it.copy(errorMessage = "La descripción es obligatoria") }
                    return
                }

                if (tiempo <= 0) {
                    _uiState.update { it.copy(errorMessage = "El tiempo es obligatorio y debe ser mayor que 0") }
                    return
                }

                viewModelScope.launch {
                    val tarea = TareaEntity(
                        tareaid = uiState.value.tareaEditandoId ?: null,
                        descripcion = descripcion,
                        tiempo = tiempo
                    )

                    if (uiState.value.tareaEditandoId == null) {
                        repository.saveTarea(tarea)
                    } else {
                        repository.updateTarea(tarea)
                    }

                    _uiState.update {
                        it.copy(
                            descripcion = "",
                            tiempo = 0,
                            tareaEditandoId = null,
                            errorMessage = null,
                            successMessage = "Tarea guardada correctamente"
                        )
                    }
                }
            }

            is TareaEvent.OnCancelar -> {
                _uiState.update {
                    it.copy(
                        descripcion = "",
                        tiempo = 0,
                        tareaEditandoId = null,
                        errorMessage = null,
                        successMessage = null
                    )
                }
            }

            is TareaEvent.ClearMessages -> {
                _uiState.update {
                    it.copy(errorMessage = null, successMessage = null)
                }
            }
        }
    }
}

