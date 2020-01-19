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
import kotlin.collections.ArrayList

class PeopleAdapter(
    context: Context,
    resource: Int,
    textViewResourceId: Int,
    var items: List<Job>
) : ArrayAdapter<Job>(context, resource, textViewResourceId, items) {
    var tempItems: MutableList<Job> = ArrayList(items)
    var suggestions: MutableList<Job> = ArrayList()
    override fun getView(
        position: Int,
        convertView: View,
        parent: ViewGroup
    ): View {
        var view = convertView
        if (convertView == null) {
            val inflater =
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = inflater.inflate(R.layout.job_filter_layout, parent, false)
        }
        val people = items[position]
        if (people != null) {
            val lblName =
                view.findViewById(R.id.tvSearchResult) as TextView
            if (lblName != null) lblName.text = people.company
        }
        return view
    }

    override fun getFilter(): Filter {
        return nameFilter
    }

    /**
     * Custom Filter implementation for custom suggestions we provide.
     */
    var nameFilter: Filter = object : Filter() {
        override fun convertResultToString(resultValue: Any): CharSequence {
            val str = (resultValue as Job).company
            return str!!
        }

        override fun performFiltering(constraint: CharSequence?): FilterResults {
            return if (constraint != null) {
                suggestions.clear()
                for (people in tempItems) {
                    if (people.company!!.toLowerCase().contains(constraint.toString().toLowerCase())) {
                        suggestions.add(people)
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

        override fun publishResults(
            constraint: CharSequence?,
            results: FilterResults?
        ) {
            val filterList: List<Job>? =
                results?.values as List<Job>?
            if (results != null && results.count > 0) {
                clear()
                for (people in filterList!!) {
                    add(people)
                    notifyDataSetChanged()
                }
            }
        }
    }

    init {
        // this makes the difference.
    }
}