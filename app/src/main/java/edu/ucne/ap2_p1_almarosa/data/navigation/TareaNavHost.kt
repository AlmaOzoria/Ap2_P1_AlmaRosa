package edu.ucne.ap2_p1_almarosa.data.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.compose.runtime.*
import androidx.navigation.*
import androidx.navigation.compose.*
import androidx.hilt.navigation.compose.hiltViewModel
import edu.ucne.ap2_p1_almarosa.data.tareas.TareaListScreen
import edu.ucne.ap2_p1_almarosa.data.tareas.TareaScreen
import edu.ucne.ap2_p1_almarosa.data.tareas.TareaViewModel

@Composable
fun TareaNavHost(
    navHostController: NavHostController,
    viewModel: TareaViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    NavHost(
        navController = navHostController,
        startDestination = "TareaList"
    ) {
        composable("TareaList") {
            TareaListScreen(
                tareaList = uiState.tarea,
                onEdit = { tarea ->
                    viewModel.editarTarea(tarea)
                    navHostController.navigate("TareaForm")
                },
                onCreate = {
                    viewModel.cancelarEdicion()
                    navHostController.navigate("TareaForm")
                },
                onDelete = { tarea ->
                    viewModel.delete(tarea)
                }
            )
        }

        composable("TareaForm") {
            TareaScreen(
                descripcion = uiState.descripcion,
                tiempo = uiState.tiempo,
                onDescripcionChange = { viewModel.onDescripcionChange(it) },
                onTiempoChange = { viewModel.onTiempoChange(it) },
                onGuardar = {
                    viewModel.guardarTarea()
                },
                onCancel = {
                    viewModel.cancelarEdicion()
                    navHostController.popBackStack()
                },
                editando = uiState.tareaEditandoId != null,
                errorMessage = uiState.errorMessage,
                successMessage = uiState.successMessage
            )

            LaunchedEffect(uiState.successMessage) {
                if (!uiState.successMessage.isNullOrEmpty()) {
                    navHostController.popBackStack()
                    viewModel.clearMessages()
                }
            }

        }

    }
}

