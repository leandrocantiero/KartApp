package campagnolo.cantiero.kartapp.services.converter

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.TypeConverter
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
class LocalTimeConverter {
    private val mFormater: DateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss.SSS")
    private val mFormaterMilli: DateTimeFormatter = DateTimeFormatter.ofPattern("mm:ss.SSS")

    @TypeConverter
    fun fromSqlFormat(value: String?): LocalTime? {
        return if (value?.length!! > 8) {
            value.let { LocalTime.parse(it, mFormater) }
        } else {
            value.let { LocalTime.parse(it, mFormaterMilli) }
        }
    }

    @TypeConverter
    fun toSqlFormat(value: LocalTime?): String {
        return value.toString()
    }
}