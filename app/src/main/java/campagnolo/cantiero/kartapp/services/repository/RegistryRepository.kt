package campagnolo.cantiero.kartapp.services.repository

import android.content.Context
import campagnolo.cantiero.kartapp.services.model.RegistryModel
import campagnolo.cantiero.kartapp.services.repository.local.KartDatabase

class RegistryRepository(context: Context) {
    private val mDatabase = KartDatabase.getDatabase(context).registryDAO()

    fun add(registry: RegistryModel) {
        mDatabase.save(registry)
    }

    fun list(): List<RegistryModel> = mDatabase.list()

    fun clear() {
        mDatabase.clear()
    }
}