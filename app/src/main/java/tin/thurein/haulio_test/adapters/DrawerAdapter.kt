package tin.thurein.haulio_test.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import tin.thurein.haulio_test.R
import tin.thurein.haulio_test.databinding.DrawerLayoutBinding
import tin.thurein.haulio_test.listeners.BaseListener
import tin.thurein.haulio_test.viewmodels.DrawerViewModel

class DrawerAdapter(private val drawers: List<String>, private val listener: BaseListener):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var inflater: LayoutInflater = parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val binding: DrawerLayoutBinding = DataBindingUtil.inflate(inflater, R.layout.drawer_layout, parent, false)
        val drawerViewModel = DrawerViewModel()
        binding.drawerViewModel = drawerViewModel
        val viewHolder = DrawerViewHolder(binding, drawerViewModel)
        binding.root.setOnClickListener{ listener.onItemClick(viewHolder.adapterPosition)}
        return viewHolder
    }

    override fun getItemCount(): Int {
        return drawers.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is DrawerViewHolder) {
            holder.bind(drawers[position])
        }
    }

    override fun onViewDetachedFromWindow(holder: RecyclerView.ViewHolder) {
        super.onViewDetachedFromWindow(holder)
        if (holder is DrawerViewHolder) {
            holder.binding.unbind()
        }
    }

    class DrawerViewHolder(var binding: DrawerLayoutBinding, var drawerViewModel: DrawerViewModel): RecyclerView.ViewHolder(binding.root) {
        fun bind(drawerName: String) {
            drawerViewModel.setDrawerName(drawerName)
            binding.invalidateAll()
        }
    }
}