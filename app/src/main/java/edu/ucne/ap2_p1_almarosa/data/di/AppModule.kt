package edu.ucne.ap2_p1_almarosa.data.di


import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import edu.ucne.ap2_p1_almarosa.data.local.database.TareaDb
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideTareaDb(
        @ApplicationContext appContext: Context
    ): TareaDb {
        return Room.databaseBuilder(
            appContext,
            TareaDb::class.java,
            "TareaDb"
        ).build()
    }

    @Provides
    fun provideTareaDao(db: TareaDb) = db.tareaDao()
}