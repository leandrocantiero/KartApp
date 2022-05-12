package campagnolo.cantiero.kartapp.services.model

import java.time.Duration
import java.time.LocalTime

data class ResultModel(
    var codigo: Int,

    var nome: String,

    var posicao: Int,

    var voltasCompletas: Int,

    var tempoVolta: LocalTime,

    var tempoProva: LocalTime,

    var melhorVolta: Int,

    var mediaVelocidade: Double,

    var tempoAposPrimeiroColocado: Duration?
)