package campagnolo.cantiero.kartapp.view

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import campagnolo.cantiero.kartapp.databinding.FragmentHomeBinding
import campagnolo.cantiero.kartapp.services.listener.RegistryListener
import campagnolo.cantiero.kartapp.view.adapter.RegistryAdapter
import campagnolo.cantiero.kartapp.viewmodel.HomeViewModel

@RequiresApi(Build.VERSION_CODES.O)
class HomeFragment: Fragment() {
    private lateinit var mViewModel: HomeViewModel
    private lateinit var mBinding: FragmentHomeBinding
    private lateinit var mListener: RegistryListener
    private val mAdapter = RegistryAdapter()

    private val TAG = "HomeFragment"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mViewModel = ViewModelProvider(this)[HomeViewModel::class.java]

        mBinding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = mBinding.root

        val recycler = mBinding.recyclerRegistry
        recycler.layoutManager = LinearLayoutManager(context)
        recycler.adapter = mAdapter

        setListener()
        setObserver()

        return root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mListener = object : RegistryListener {
            override fun onChangeList() {
                mViewModel.list()
            }
        }
        (activity as MainActivity?)?.setListener(mListener)
    }

    private fun setListener() { }

    @SuppressLint("SetTextI18n")
    private fun setObserver() {
        mViewModel.registryList.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                mAdapter.updateList(it)
                updateUi(it.size)

                Log.i(TAG, "list upadated")
            } else {
                updateUi(0)
                Log.i(TAG, "empty list")
            }
        })
    }

    private fun updateUi(size: Int) {
        if (size > 0) {
            mBinding.textRegisterCount.text = "${size} registros lidos"
        } else {
            mBinding.textRegisterCount.text = "Selecione um arquivo para começar"
        }
    }

    override fun onResume() {
        super.onResume()
        mViewModel.list()
    }
}