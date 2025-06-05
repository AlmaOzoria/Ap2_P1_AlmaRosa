package edu.ucne.ap2_p1_almarosa.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import edu.ucne.ap2_p1_almarosa.data.local.entities.TareaEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TareaDao {
    @Upsert
    suspend fun save(entidad: TareaEntity)

    @Query(
        """
            SELECT * FROM Tarea
            WHERE tareaid = :id
            LIMIT 1
        """)
    suspend fun find(id: Int): TareaEntity?
    @Update
    suspend fun update(tarea: TareaEntity)
    @Delete
    suspend fun delete(tarea: TareaEntity)

    @Query("SELECT * FROM Tarea")
    fun getAll(): Flow<List<TareaEntity>>

}