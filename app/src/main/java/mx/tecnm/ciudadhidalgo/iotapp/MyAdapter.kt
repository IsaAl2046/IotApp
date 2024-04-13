package mx.tecnm.ciudadhidalgo.iotapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import mx.ItemListener

class MyAdapter (private val dataset: Array<Array<String?>>, private val listener: ItemListener):
    RecyclerView.Adapter<MyAdapter.ViewHolder>(){

        class ViewHolder(v: View, listener: ItemListener): RecyclerView.ViewHolder(v) {
            var tvItemId: TextView
            var tvItemType: TextView
            var tvItemValue: TextView
            var tvItemName: TextView
            var tvItemDate: TextView
            var btnEdit: Button
            var btnDelete: Button
            init {
                tvItemId = v.findViewById(R.id.tvItemId)
                tvItemType = v.findViewById(R.id.tvItemType)
                tvItemValue = v.findViewById(R.id.tvItemValue)
                tvItemName = v.findViewById(R.id.tvItemName)
                tvItemDate = v.findViewById(R.id.tvItemDate)
                btnEdit = v.findViewById(R.id.btnEdit)
                btnDelete = v.findViewById(R.id.btnDelete)
                btnEdit.setOnClickListener { view -> listener.onEdit(view, adapterPosition) }
                btnDelete.setOnClickListener { view -> listener.onDel(view, adapterPosition) }
                v.setOnClickListener { viiew -> listener.onClick(viiew, adapterPosition) }
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_sensor, parent, false)
        return ViewHolder(v, listener)
    }

    override fun getItemCount(): Int {
        return dataset!!.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvItemId.text = dataset!![position][0]
        holder.tvItemName.text = dataset!![position][1]
        holder.tvItemType.text = dataset!![position][2]
        holder.tvItemValue.text = dataset!![position][3]
        holder.tvItemDate.text = dataset!![position][4]
    }

}