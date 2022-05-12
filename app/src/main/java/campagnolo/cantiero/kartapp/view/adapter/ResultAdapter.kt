package campagnolo.cantiero.kartapp.view.adapter

import android.annotation.SuppressLint
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import campagnolo.cantiero.kartapp.R
import campagnolo.cantiero.kartapp.services.model.ResultModel
import campagnolo.cantiero.kartapp.view.viewholder.ResultViewHolder

@RequiresApi(Build.VERSION_CODES.O)
class ResultAdapter : RecyclerView.Adapter<ResultViewHolder>() {

    private var mList: List<ResultModel> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultViewHolder {
        val item =
            LayoutInflater.from(parent.context).inflate(R.layout.row_result_list, parent, false)
        return ResultViewHolder(item, parent.context)
    }

    override fun getItemCount(): Int {
        return mList.count()
    }

    override fun onBindViewHolder(holder: ResultViewHolder, position: Int) {
        holder.bindData(mList[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(list: List<ResultModel>) {
        mList = list
        notifyDataSetChanged()
    }
}