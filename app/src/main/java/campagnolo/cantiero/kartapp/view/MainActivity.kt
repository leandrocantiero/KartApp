package campagnolo.cantiero.kartapp.view

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import campagnolo.cantiero.kartapp.R
import campagnolo.cantiero.kartapp.services.constant.KartContants
import campagnolo.cantiero.kartapp.services.listener.RegistryListener
import campagnolo.cantiero.kartapp.viewmodel.MainViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.io.File

@RequiresApi(Build.VERSION_CODES.S)
class MainActivity : AppCompatActivity() {
    private lateinit var mViewModel: MainViewModel
    private lateinit var mListener: RegistryListener

    private val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mViewModel = ViewModelProvider(this)[MainViewModel::class.java]

        verifyPermissions()
        setToolbar()
        setFabButton()
        setObserver()
    }

    private fun verifyPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
            requestPermissions(
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                KartContants.SHARED.PERMISSION_REQUEST_STORAGE
            )
    }

    private fun setToolbar() {
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        toolbar.inflateMenu(R.menu.main)
        setSupportActionBar(toolbar)
    }

    private fun setFabButton() {
        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener {
            openFileSelection()
        }
    }

    private fun setObserver() {
        mViewModel.onChangeList.observe(this, Observer {
            if (it.success()) {
                mListener.onChangeList()
                Toast.makeText(this, "Dados atualizados com sucesso!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, it.failure(), Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun setListener(listener: RegistryListener) {
        mListener = listener;
    }

    private fun openFileSelection() {
        val intent = Intent()
            .setType("text/*")
            .setAction(Intent.ACTION_OPEN_DOCUMENT)
            .addCategory(Intent.CATEGORY_OPENABLE)

        startActivityForResult(
            Intent.createChooser(intent, "Selecione o arquivo texto"),
            KartContants.SHARED.PERMISSION_REQUEST_STORAGE
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == KartContants.SHARED.PERMISSION_REQUEST_STORAGE && resultCode == RESULT_OK) {
            if (data != null) {
                val uri = data.data!!
                var path = uri.path!!
                path = path.substring(path.indexOf(":") + 1)

                try {
                    mViewModel.readFile(File(path))
                } catch (e: Exception) {
                    e.printStackTrace()
                    Toast.makeText(
                        this,
                        "Erro ao abrir o arquivo, tente novamente",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_clear -> {
                clear()
            }

            R.id.action_results -> {
                viewResults()
            }

            R.id.action_about -> {
                showDialogAbout()
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun clear() {
        mViewModel.clear()
    }

    private fun viewResults() {
        if (!mViewModel.isListEmpty()) {
            startActivity(Intent(this, ResultActivity::class.java))
        } else {
            Toast.makeText(this, "Carregue um arquivo primeiro", Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun showDialogAbout() {
        val dialog = AboutDialog()
        dialog.show(supportFragmentManager, TAG)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == KartContants.SHARED.PERMISSION_REQUEST_STORAGE) {
            if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                Toast.makeText(
                    this,
                    "É necessário conceder as permissões para continuar",
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            }
        }
    }
}