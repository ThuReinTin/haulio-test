package tin.thurein.haulio_test.viewmodels

import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR

class DrawerViewModel: BaseViewModel() {
    var drawerLabel = "Label"

    @Bindable
    fun getDrawerName(): String {
        return drawerLabel
    }

    fun setDrawerName(name: String) {
        this.drawerLabel = name
        notifyPropertyChanged(BR.drawerName)
    }
}