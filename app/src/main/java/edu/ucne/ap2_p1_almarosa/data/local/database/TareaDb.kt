package edu.ucne.ap2_p1_almarosa.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import edu.ucne.ap2_p1_almarosa.data.local.dao.TareaDao
import edu.ucne.ap2_p1_almarosa.data.local.entities.TareaEntity

@Database(
    entities = [
        TareaEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class TareaDb : RoomDatabase() {
    abstract fun tareaDao(): TareaDao
}