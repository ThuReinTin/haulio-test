package tin.thurein.haulio_test.common

import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import de.hdodenhof.circleimageview.CircleImageView
import tin.thurein.haulio_test.R

object BindingUtils {
    @JvmStatic
    @BindingAdapter(value = ["app:imageUri"], requireAll = false)
    fun setImageUrl(iv: CircleImageView, uri: String) {
        val options = RequestOptions()
            .placeholder(R.drawable.ic_person_outline_black_24dp)
            .error(R.drawable.ic_person_outline_black_24dp)

        Glide.with(iv.context).load(uri).apply(options).into(iv)
    }

    @JvmStatic
    @BindingAdapter(value = ["app:isRefresh"], requireAll = false)
    fun setRefresh(srl: SwipeRefreshLayout, isRefresh: Boolean) {
        srl.isRefreshing = isRefresh
    }
}