package tin.thurein.haulio_test.viewmodels

import android.view.View
import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR
import tin.thurein.domain.models.Job

class JobViewModel: BaseViewModel() {
    private var job: Job =Job(null, null, null, null, null, null)

    @Bindable
    fun getJobNo(): String {
        return "Job Number: ${job.jobId}"
    }

    fun setJobNo(jobId: Long?) {
        job.jobId = jobId
        notifyPropertyChanged(BR.jobNo)
    }

    @Bindable
    fun getCompany(): String {
        return "Company: ${job.company}"
    }

    fun setCompany(company: String?) {
        job.company = company
        notifyPropertyChanged(BR.company)

    }

    @Bindable
    fun getAddress(): String {
        return "Address: ${job.address}"
    }

    fun setAddress(address: String?) {
        job.address = address
        notifyPropertyChanged(BR.address)
    }

    @Bindable
    fun getAcceptVisibility(): Int {
        return if(job.isAccepted) View.GONE else View.VISIBLE
    }

    fun updateJob(job: Job) {
        this.job = job
        notifyPropertyChanged(BR.acceptVisibility)
    }
}