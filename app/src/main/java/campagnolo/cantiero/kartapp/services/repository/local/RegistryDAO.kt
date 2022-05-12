package campagnolo.cantiero.kartapp.services.repository.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import campagnolo.cantiero.kartapp.services.model.RegistryModel

@Dao
interface RegistryDAO {
    @Insert
    fun save(list: RegistryModel)

    @Query("DELETE FROM Registry")
    fun clear()

    @Query("SELECT * FROM Registry")
    fun list(): List<RegistryModel>
}