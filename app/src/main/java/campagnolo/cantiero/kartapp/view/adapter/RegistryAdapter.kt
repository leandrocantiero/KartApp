package campagnolo.cantiero.kartapp.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import campagnolo.cantiero.kartapp.R
import campagnolo.cantiero.kartapp.services.listener.RegistryListener
import campagnolo.cantiero.kartapp.services.model.RegistryModel
import campagnolo.cantiero.kartapp.view.viewholder.RegistryViewHolder

class RegistryAdapter : RecyclerView.Adapter<RegistryViewHolder>() {

    private var mList: List<RegistryModel> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RegistryViewHolder {
        val item =
            LayoutInflater.from(parent.context).inflate(R.layout.row_registry_list, parent, false)
        return RegistryViewHolder(item)
    }

    override fun getItemCount(): Int {
        return mList.count()
    }

    override fun onBindViewHolder(holder: RegistryViewHolder, position: Int) {
        holder.bindData(mList[position])
    }

    fun updateList(list: List<RegistryModel>) {
        mList = list
        notifyDataSetChanged()
    }
}