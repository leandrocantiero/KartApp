package campagnolo.cantiero.kartapp.viewmodel

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import campagnolo.cantiero.kartapp.services.model.RegistryModel
import campagnolo.cantiero.kartapp.services.model.ResultModel
import campagnolo.cantiero.kartapp.services.repository.RegistryRepository
import java.time.Duration

@RequiresApi(Build.VERSION_CODES.S)
class ResultViewModel(application: Application) : AndroidViewModel(application) {
    private val mRegistryRepository = RegistryRepository(application)

    private val mOnResult = MutableLiveData<List<ResultModel>>()
    val onResult: LiveData<List<ResultModel>> = mOnResult

    private var posicaoPiloto: ResultModel? = null
    private val resultList = arrayListOf<ResultModel>()

    fun loadResults() {
        val registryList = mRegistryRepository.list()
        resultList.clear()

        registryList.forEach { reg ->
            if (reg.volta > 1) {
                val res = resultList.first { it.codigo == reg.codigo }

                res.tempoProva = res.tempoProva
                    .plusMinutes(reg.tempoVolta.minute.toLong())
                    .plusSeconds(reg.tempoVolta.second.toLong())
                    .plusNanos(reg.tempoVolta.nano.toLong())
                res.mediaVelocidade += reg.mediaVelocidade
                res.voltasCompletas = reg.volta
                if (reg.tempoVolta < res.tempoVolta) {
                    res.melhorVolta = reg.volta
                }

                if (res.voltasCompletas == 4 && posicaoPiloto == null) {
                    res.posicao = 1
                    res.mediaVelocidade = res.mediaVelocidade / res.voltasCompletas
                    posicaoPiloto = res
                } else if (posicaoPiloto != null && posicaoPiloto!!.codigo != res.codigo) {
                    res.posicao = posicaoPiloto!!.posicao + 1
                    res.mediaVelocidade = res.mediaVelocidade / res.voltasCompletas
                    res.tempoAposPrimeiroColocado =
                        getTempoAposPrimeiroColocado(res.codigo, resultList)
                    posicaoPiloto = res
                }
            } else {
                // inicializa o piloto na corrida
                resultList.add(
                    ResultModel(
                        codigo = reg.codigo,
                        nome = reg.nome,
                        posicao = resultList.size + 1,
                        voltasCompletas = reg.volta,
                        tempoVolta = reg.tempoVolta,
                        tempoProva = reg.tempoVolta,
                        melhorVolta = reg.volta,
                        mediaVelocidade = reg.mediaVelocidade,
                        tempoAposPrimeiroColocado = null
                    )
                )
            }
        }

        mOnResult.value = resultList
    }

    private fun getTempoAposPrimeiroColocado(
        codigo: Int,
        resultList: ArrayList<ResultModel>
    ): Duration? {
        val first = resultList.first { it.posicao == 1 }
        val pilot = resultList.first { it.codigo == codigo }

        return Duration.between(first.tempoProva, pilot.tempoProva)
    }

    fun getBestPilotData(): String {
        var reg: RegistryModel? = null
        mRegistryRepository.list().forEach {
            if (reg == null || reg!!.tempoVolta > it.tempoVolta) {
                reg = it
            }
        }

        return "${reg!!.nome}, com o tempo de ${reg!!.tempoVolta} na volta ${reg!!.volta}"
    }
}