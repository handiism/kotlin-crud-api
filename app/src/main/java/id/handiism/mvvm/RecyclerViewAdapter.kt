package id.handiism.mvvm

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewAdapter(private val itemClickListener: OnItemClickListener) :
    RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {
    var categories = mutableListOf<Category>()

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val tvId: TextView = view.findViewById(R.id.tv_category_id)
        private val tvName: TextView = view.findViewById(R.id.tv_category_name)

        fun bind(data: Category) {
            tvId.text = data.id.toString()
            tvName.text = data.name
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerViewAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context).inflate(
            R.layout.item_row_category,
            parent,
            false
        )
        return ViewHolder(inflater)
    }

    override fun onBindViewHolder(holder: RecyclerViewAdapter.ViewHolder, position: Int) {
        holder.bind(categories[position])
        holder.itemView.setOnClickListener {
            itemClickListener.onItemClickListener(categories[position])
        }
    }

    override fun getItemCount(): Int {
        return categories.size
    }

    interface OnItemClickListener {
        fun onItemClickListener(category: Category)
    }
}