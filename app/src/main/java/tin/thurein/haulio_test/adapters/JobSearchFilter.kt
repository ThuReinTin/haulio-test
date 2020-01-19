package tin.thurein.haulio_test.adapters

import android.util.Log
import android.widget.Filter
import tin.thurein.domain.models.Job


class JobSearchFilter(var jobs: MutableList<Job>, var suggestions: MutableList<String>): Filter() {
    var jobsMap: MutableMap<String, MutableList<Job>> = HashMap()

    override fun performFiltering(constraint: CharSequence?): FilterResults {
        return if (constraint != null) {
            for (job in jobs) {
                if (job.company?.toLowerCase()!!.contains(constraint.toString().toLowerCase())) {
                    if (getJobById(job.id!!, jobsMap[job.company!!]) == null) {
                        jobsMap[job.company!!]?.add(job)
                        suggestions.add(job.company!!)
                    }
                }

                if (job.address?.toLowerCase()!!.contains(constraint.toString().toLowerCase())) {
                    if (getJobById(job.id!!, jobsMap[job.company!!]) == null) {
                        jobsMap[job.company!!]?.add(job)
                        suggestions.add(job.company!!)
                    }
                }
            }
            val filterResults = FilterResults()
            filterResults.values = suggestions
            filterResults.count = suggestions.size
            filterResults
        } else {
            FilterResults()
        }
    }

    private fun getJobById(id: Long, jobs: MutableList<Job>?): Job? {
        return jobs?.findLast { it.id == id }
    }
    override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
        if (results is MutableList<*>) {
            if (results != null && !results.isEmpty()) {
                Log.e("Notify Here", "OK")
            }
        }
    }
}