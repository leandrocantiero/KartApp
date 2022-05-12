package campagnolo.cantiero.kartapp.view.viewholder

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.view.View
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import campagnolo.cantiero.kartapp.R
import campagnolo.cantiero.kartapp.services.model.ResultModel
import java.time.Duration
import java.time.format.DateTimeFormatter
import kotlin.math.abs

@RequiresApi(Build.VERSION_CODES.S)
class ResultViewHolder(itemView: View, val context: Context) :
    RecyclerView.ViewHolder(itemView) {

    private val mFormaterMilli: DateTimeFormatter = DateTimeFormatter.ofPattern("mm:ss.SSS")

    private var mTextPilot: TextView = itemView.findViewById(R.id.text_pilot)
    private var mTextTimeRace: TextView = itemView.findViewById(R.id.text_time_race)
    private var mTextTimePlus: TextView = itemView.findViewById(R.id.text_time_plus)
    private var mTextPosition: TextView = itemView.findViewById(R.id.text_position)
    private var mTextLapsAndBestLap: TextView = itemView.findViewById(R.id.text_laps_and_best_lap)
    private var mTextAvarageSpeed: TextView = itemView.findViewById(R.id.text_avarage_speed)

    @SuppressLint("SetTextI18n")
    fun bindData(result: ResultModel) {
        mTextPilot.text = "${result.codigo} - ${result.nome}"
        mTextTimeRace.text = result.tempoProva.format(mFormaterMilli)
        mTextTimePlus.text =
            if (result.tempoAposPrimeiroColocado != null)
                "+" + formatDuration(result.tempoAposPrimeiroColocado).substring(0, 8)
            else ""

        mTextPosition.text = result.posicao.toString() + "º"
        when (result.posicao) {
            1 -> {
                mTextPosition.setTextColor(ContextCompat.getColor(context, R.color.gold))
            }

            2 -> {
                mTextPosition.setTextColor(ContextCompat.getColor(context, R.color.silver))
            }

            3 -> {
                mTextPosition.setTextColor(ContextCompat.getColor(context, R.color.bronze))
            }
        }

        mTextLapsAndBestLap.text = "${result.voltasCompletas} - ${result.melhorVolta}"
        mTextAvarageSpeed.text = String.format("%.3f", result.mediaVelocidade)
    }


    private fun formatDuration(duration: Duration?): String {
        if (duration == null) return ""

        return String.format(
            "%d:%02d:%d",
            duration.toMinutes(),
            duration.seconds,
            duration.nano
        )
    }
}