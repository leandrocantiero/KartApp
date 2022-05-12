package campagnolo.cantiero.kartapp.viewmodel

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import campagnolo.cantiero.kartapp.services.listener.FileValidator
import campagnolo.cantiero.kartapp.services.model.RegistryModel
import campagnolo.cantiero.kartapp.services.repository.RegistryRepository
import java.io.File
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatterBuilder
import java.time.temporal.ChronoField
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val mRegistryRepository = RegistryRepository(application)

    private val mFormater: DateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss.SSS")
    private val mFormaterMilli: DateTimeFormatter = DateTimeFormatterBuilder()
        .parseDefaulting(ChronoField.HOUR_OF_DAY, 0)
        .appendPattern("m:ss.SSS")
        .toFormatter(Locale.ENGLISH);

    private val mOnChangeList = MutableLiveData<FileValidator>()
    val onChangeList: LiveData<FileValidator> = mOnChangeList

    fun readFile(file: File) {
        if (file.exists()) {
            // limpa registros
            mRegistryRepository.clear()

            try {
                var ignoreFirstLine = true
                file.forEachLine { line ->
                    if (ignoreFirstLine) {
                        ignoreFirstLine = false
                        return@forEachLine
                    }

                    if (!isLineValid(line)) {
                        return@forEachLine
                    }

                    val splitedLine = line.split(";")
                    val pilot = splitedLine[1].split(" – ")
                    val registry = RegistryModel(
                        codigo = pilot[0].toInt(),
                        nome = pilot[1],
                        hora = LocalTime.parse(splitedLine[0], mFormater),
                        volta = splitedLine[2].toInt(),
                        tempoVolta = LocalTime.parse(splitedLine[3], mFormaterMilli),
                        mediaVelocidade = splitedLine[4].replace(",", ".").toDouble()
                    )

                    mRegistryRepository.add(registry)
                }

                mOnChangeList.value = FileValidator()
            } catch (e: Exception) {
                mOnChangeList.value = FileValidator(e.message!!)
            }
        }
    }

    private fun isLineValid(line: String): Boolean {
        if (line.length != 5) return false

        return true
    }

    fun isListEmpty(): Boolean = mRegistryRepository.list().isEmpty()

    fun clear() {
        mRegistryRepository.clear()
        mOnChangeList.value = FileValidator()
    }
}