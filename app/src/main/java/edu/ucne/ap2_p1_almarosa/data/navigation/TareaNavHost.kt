package edu.ucne.ap2_p1_almarosa.data.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import edu.ucne.ap2_p1_almarosa.data.tareas.TareaEvent
import edu.ucne.ap2_p1_almarosa.data.tareas.TareaListScreen
import edu.ucne.ap2_p1_almarosa.data.tareas.TareaScreen
import edu.ucne.ap2_p1_almarosa.data.tareas.TareaViewModel

@Composable
fun TareaNavHost(
    navHostController: NavHostController,
    viewModel: TareaViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    NavHost(
        navController = navHostController,
        startDestination = "TareaList"
    ) {
        composable("TareaList") {
            TareaListScreen(
                onEdit = { tarea ->
                    viewModel.onEvent(TareaEvent.OnEditar(tarea))
                    navHostController.navigate("TareaForm")
                },
                onCreate = {
                    viewModel.onEvent(TareaEvent.OnCancelar)
                    navHostController.navigate("TareaForm")
                },
                onDelete = { tarea ->
                    viewModel.onEvent(TareaEvent.OnEliminar(tarea))
                }
            )
        }

        composable("TareaForm") {
            TareaScreen(viewModel = viewModel)

            LaunchedEffect(uiState.successMessage) {
                if (!uiState.successMessage.isNullOrEmpty()) {
                    navHostController.popBackStack()
                    viewModel.onEvent(TareaEvent.ClearMessages)
                }
            }
        }
    }
}

