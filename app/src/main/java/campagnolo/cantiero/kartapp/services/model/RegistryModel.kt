package campagnolo.cantiero.kartapp.services.model

import androidx.room.*
import java.time.LocalTime

@Entity(tableName = "Registry")
data class RegistryModel(
    @ColumnInfo(name = "codigo")
    var codigo: Int,

    @ColumnInfo(name = "nome")
    var nome: String,

    @PrimaryKey
    @ColumnInfo(name = "hora")
    var hora: LocalTime,

    @ColumnInfo(name = "volta")
    var volta: Int,

    @ColumnInfo(name = "tempoVolta")
    var tempoVolta: LocalTime,

    @ColumnInfo(name = "mediaVelocidade")
    var mediaVelocidade: Double
)