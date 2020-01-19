package tin.thurein.haulio_test.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import tin.thurein.domain.models.Job
import tin.thurein.haulio_test.R
import tin.thurein.haulio_test.databinding.JobDetailLayoutBinding
import tin.thurein.haulio_test.listeners.JobAdapterListener
import tin.thurein.haulio_test.viewmodels.JobViewModel

class JobAdapter(var jobs: MutableList<Job>, var listener: JobAdapterListener?, var isProfile: Boolean):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var inflater: LayoutInflater = parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val binding: JobDetailLayoutBinding = DataBindingUtil.inflate(inflater, R.layout.job_detail_layout, parent, false)
        var jobViewModel = JobViewModel()
        binding.jobViewModel = jobViewModel
        val viewHolder = JobViewHolder(binding, jobViewModel)
        binding.btnAccept.setOnClickListener{
            listener?.onItemClick(viewHolder.adapterPosition)
        }

        return viewHolder
    }

    override fun getItemCount(): Int {
        return jobs.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is JobViewHolder) {
            holder.bind(jobs[position])

            if (position == (jobs.size - 1)) {
                holder.binding.vLine.visibility = View.GONE
            }

            if (isProfile) {
                holder.binding.tvCompany.visibility = View.GONE
                holder.binding.tvAddress.visibility = View.GONE
            } else {
                holder.binding.tvCompany.visibility = View.VISIBLE
                holder.binding.tvAddress.visibility = View.VISIBLE
            }
        }
    }

    class JobViewHolder(val binding: JobDetailLayoutBinding, var jobViewModel: JobViewModel): RecyclerView.ViewHolder(binding.root) {
        fun bind(job: Job) {
            jobViewModel.updateJob(job)
            binding.invalidateAll()
        }
    }
}