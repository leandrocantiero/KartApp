package campagnolo.cantiero.kartapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import campagnolo.cantiero.kartapp.services.model.RegistryModel
import campagnolo.cantiero.kartapp.services.repository.RegistryRepository

class HomeViewModel(application: Application) : AndroidViewModel(application) {
    private val mRegistryRepository = RegistryRepository(application)

    private val mRegistryList = MutableLiveData<List<RegistryModel>?>()
    val registryList: LiveData<List<RegistryModel>?> = mRegistryList

    fun list() {
        mRegistryList.value = mRegistryRepository.list()
    }
}