package edu.ucne.ap2_p1_almarosa.data.tareas


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.ucne.ap2_p1_almarosa.data.local.entities.TareaEntity
import edu.ucne.ap2_p1_almarosa.data.local.repository.TareaRepository
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
    val uiState: StateFlow<TareaUiState> = _uiState.asStateFlow()

    init {
        loadTarea()
    }

    private fun loadTarea() {
        viewModelScope.launch {
            repository.getAll().collect { tarea ->
                _uiState.update { it.copy(tarea = tarea) }
            }
        }
    }

    fun onDescripcionChange(newValue: String) {
        _uiState.update { it.copy(descripcion = newValue) }
    }

    fun onTiempoChange(newValue: Int) {
        _uiState.update { it.copy(tiempo = newValue) }
    }

    fun editarTarea(tarea: TareaEntity) {
        _uiState.update {
            it.copy(
                descripcion = tarea.descripcion,
                tiempo = tarea.tiempo,
                tareaEditandoId = tarea.tareaid,
                errorMessage = null,
                successMessage = null
            )
        }
    }

    fun guardarTarea() {
        val state = _uiState.value

        if (state.descripcion.isBlank()) {
            _uiState.update { it.copy(errorMessage = "La Descripción es obligatoria.") }
            return
        }

        if (state.tiempo <= 0) {
            _uiState.update { it.copy(errorMessage = "El Tiempo es obligatorio y debe ser mayor que 0.") }
            return
        }

        viewModelScope.launch {
            val tarea = TareaEntity(
                tareaid = state.tareaEditandoId ?: 0,
                descripcion = state.descripcion,
                tiempo = state.tiempo
            )

            if (state.tareaEditandoId != null) {
                repository.saveTarea(tarea)
                _uiState.update {
                    it.copy(
                        descripcion = "",
                        tiempo = 0,
                        tareaEditandoId = null,
                        errorMessage = null,
                        successMessage = "Tarea actualizada con éxito"
                    )
                }
            } else {
                if (isDescripcionDuplicado(state.descripcion)) {
                    _uiState.update { it.copy(errorMessage = "La descripción ya existe.") }
                    return@launch
                }
                repository.saveTarea(tarea.copy(tareaid = null))
                _uiState.update {
                    it.copy(
                        descripcion = "",
                        tiempo = 0,
                        errorMessage = null,
                        successMessage = "Tarea guardada con éxito"
                    )
                }
            }
        }
    }


    fun cancelarEdicion() {
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

    fun delete(tarea: TareaEntity) {
        viewModelScope.launch {
            repository.delete(tarea)
        }
    }

    fun isDescripcionDuplicado(descripcion: String): Boolean {
        val state = _uiState.value
        return if (state.tareaEditandoId != null) {
            state.tarea.any { it.descripcion == descripcion && it.tareaid != state.tareaEditandoId }
        } else {
            state.tarea.any { it.descripcion == descripcion }
        }
    }

    fun getTareaById(id: Int?): TareaEntity? {
        return _uiState.value.tarea.find { it.tareaid == id }
    }

    fun clearMessages() {
        _uiState.update { it.copy(successMessage = null, errorMessage = null) }
    }



}
