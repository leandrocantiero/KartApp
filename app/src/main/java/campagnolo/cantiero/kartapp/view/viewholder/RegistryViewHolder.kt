package campagnolo.cantiero.kartapp.view.viewholder

import android.annotation.SuppressLint
import android.os.Build
import android.view.View
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import campagnolo.cantiero.kartapp.R
import campagnolo.cantiero.kartapp.services.listener.RegistryListener
import campagnolo.cantiero.kartapp.services.model.RegistryModel
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
class RegistryViewHolder(itemView: View) :
    RecyclerView.ViewHolder(itemView) {

    private val mFormaterMilli: DateTimeFormatter = DateTimeFormatter.ofPattern("m:ss.SSS")

    private var mTextPilot: TextView = itemView.findViewById(R.id.text_pilot)
    private var mTextTimeLap: TextView = itemView.findViewById(R.id.text_time_lap)
    private var mTextLapAndAvarageSpeed: TextView = itemView.findViewById(R.id.text_lap_and_avarage_speed)

    @SuppressLint("SetTextI18n")
    fun bindData(registry: RegistryModel) {
        this.mTextPilot.text = "${registry.codigo} - ${registry.nome}"
        this.mTextTimeLap.text = registry.tempoVolta.format(mFormaterMilli)
        this.mTextLapAndAvarageSpeed.text = "${registry.volta} - ${registry.mediaVelocidade}"
    }

}