package tin.thurein.haulio_test.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Filter
import android.widget.TextView
import tin.thurein.domain.models.Job
import tin.thurein.haulio_test.R
import tin.thurein.haulio_test.listeners.JobSearchAdapterListener

class JobSearchAdapter(context: Context, resource: Int,
                       var resultList: MutableList<String>, var jobs: List<Job>,var resultJobs: MutableList<Job>,
                       var listener: JobSearchAdapterListener
) : ArrayAdapter<String>(context, resource, resultList) {


    override fun getFilter(): Filter {
        return searchFilter
    }

    override fun getCount(): Int {
        return resultList.size
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view = convertView
        if (view == null) {
            val inflater: LayoutInflater =
                parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = inflater.inflate(R.layout.job_filter_layout, parent, false)
        }
        val tvSearchFilter = view?.findViewById(R.id.tvSearchResult) as TextView
        tvSearchFilter.text = resultList[position]
        return view
    }

    private val searchFilter: Filter = object : Filter(){
        override fun performFiltering(constraints: CharSequence?): FilterResults {
            val filterResults = FilterResults()

            resultList.clear()
            resultJobs.clear()

            if (constraints != null && constraints.isNotEmpty()) {
                for (job in jobs) {
                    if (job.company!!.toLowerCase().contains(constraints.toString().toLowerCase())) {
                        resultList.add(job.company!!)
                        resultJobs.add(job)
                    }
                }

                filterResults.values = resultList
                filterResults.count = resultList.size
            } else {
                for (job in jobs) {
                    resultList.add(job.company!!)
                    resultJobs.add(job)
                }
            }


            return filterResults
        }

        override fun publishResults(constraints: CharSequence?, results: FilterResults?) {
            notifyDataSetChanged()
            listener.onRefresh()
        }
    }
}