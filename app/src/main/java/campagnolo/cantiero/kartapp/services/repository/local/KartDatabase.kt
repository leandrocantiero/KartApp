package campagnolo.cantiero.kartapp.services.repository.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import campagnolo.cantiero.kartapp.services.converter.LocalTimeConverter
import campagnolo.cantiero.kartapp.services.model.RegistryModel

@Database(entities = [RegistryModel::class], version = 1)

@TypeConverters(LocalTimeConverter::class)

abstract class KartDatabase : RoomDatabase() {

    abstract fun registryDAO(): RegistryDAO

    companion object {
        private lateinit var INSTANCE: KartDatabase

        fun getDatabase(context: Context): KartDatabase {
            if (!Companion::INSTANCE.isInitialized) {
                synchronized(KartDatabase::class) {
                    INSTANCE = Room.databaseBuilder(context, KartDatabase::class.java, "kartDB")
                        .allowMainThreadQueries()
                        .build()
                }
            }
            return INSTANCE
        }
    }

}