package pembatamang.mvvm


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.recycler_item.view.*




class mAdapter() : ListAdapter<Note, mAdapter.MVH>(DiffCallback()) {

    interface RecyclerClick {
        fun click(itemPos : Int)
    }
    private var itemClick : RecyclerClick ? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MVH {
        return MVH(
            LayoutInflater.from(parent.context).inflate(
                R.layout.recycler_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MVH, position: Int) {
        val model = getItem(position)
        holder.title.text = model.title
        holder.desc.text = model.description
        holder.priority.text = model.priority.toString()
    }


    fun getItemAt(position: Int): Note? {
        return getItem(position)


    }

    inner class MVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title = itemView.title!!
        val desc = itemView.desc!!
        val priority = itemView.priority!!

        init {
            itemView.setOnClickListener {
                if(itemClick!=null && adapterPosition!=RecyclerView.NO_POSITION){
                    itemClick!!.click(adapterPosition)
                }
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Note>() {
        override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem.title == newItem.title &&
                    oldItem.description == newItem.description &&
                    oldItem.priority == newItem.priority
        }

    }

    fun setOnItemClickListener(itemClick: RecyclerClick) {
        this.itemClick = itemClick
    }
}


