package edu.ucne.ap2_p1_almarosa.data.local.repository

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import edu.ucne.ap2_p1_almarosa.data.local.entities.TareaEntity
import edu.ucne.ap2_p1_almarosa.data.local.database.TareaDb

class TareaRepository @Inject constructor(
    private val tareaDb: TareaDb
) {
    suspend fun saveTarea(tarea: TareaEntity){
        tareaDb.tareaDao().save(tarea)
    }
    suspend fun delete(tarea: TareaEntity){
        return tareaDb.tareaDao().delete(tarea)
    }
    suspend fun updateTarea(tarea: TareaEntity){
        tareaDb.tareaDao().update(tarea)
    }
    suspend fun find(id: Int): TareaEntity? {
        return tareaDb.tareaDao().find(id)
    }
    fun getAll(): Flow<List<TareaEntity>>{
        return tareaDb.tareaDao().getAll()
    }

}