package campagnolo.cantiero.kartapp.view

import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import campagnolo.cantiero.kartapp.R
import campagnolo.cantiero.kartapp.view.adapter.ResultAdapter
import campagnolo.cantiero.kartapp.viewmodel.ResultViewModel


@RequiresApi(Build.VERSION_CODES.S)
class ResultActivity : AppCompatActivity() {
    private lateinit var mViewModel: ResultViewModel
    private val mAdapter = ResultAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        mViewModel = ViewModelProvider(this)[ResultViewModel::class.java]

        val recycler = findViewById<RecyclerView>(R.id.recycler_result)
        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = mAdapter

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        setObserver()

        mViewModel.loadResults()
    }

    private fun setObserver() {
        mViewModel.onResult.observe(this, Observer {
            if (it != null) {
                mAdapter.updateList(it)

                val mTextBestPilotData: TextView = findViewById(R.id.text_best_pilot_data)
                mTextBestPilotData.text = mViewModel.getBestPilotData()
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }

        return super.onOptionsItemSelected(item)
    }
}