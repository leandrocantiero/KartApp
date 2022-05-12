package campagnolo.cantiero.kartapp.view

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import campagnolo.cantiero.kartapp.R

class AboutDialog : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)

            builder
                .setTitle(R.string.about)
                .setMessage("Feito por Leandro Cantiero - leandrocantiero@hotmailcom")
                .setNeutralButton(R.string.ok, DialogInterface.OnClickListener { dialog, id ->
                    dialog.dismiss()
                })

            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

}