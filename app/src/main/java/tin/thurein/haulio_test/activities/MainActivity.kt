package tin.thurein.haulio_test.activities

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main_layout.*
import tin.thurein.domain.models.Progress
import tin.thurein.haulio_test.App
import tin.thurein.haulio_test.fragments.HomeFragment
import tin.thurein.haulio_test.R
import tin.thurein.haulio_test.adapters.DrawerAdapter
import tin.thurein.haulio_test.databinding.ActivityMainBinding
import tin.thurein.haulio_test.injection.ViewModelFactory
import tin.thurein.haulio_test.listeners.BaseListener
import tin.thurein.haulio_test.listeners.HomeFragmentListener
import tin.thurein.haulio_test.viewmodels.MainViewModel
import javax.inject.Inject

class MainActivity : BaseActivity(), HomeFragmentListener {

    @JvmField
    @Inject
    var viewModelFactory: ViewModelFactory? = null

    private lateinit var binding: ActivityMainBinding

    private lateinit var mainViewModel: MainViewModel

    private lateinit var drawerToggle: ActionBarDrawerToggle

    private lateinit var drawerItems: List<String>

    private lateinit var drawerAdapter: DrawerAdapter

    private var fragmentTransaction: FragmentTransaction? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        AndroidInjection.inject(this)

        initUI()
        initData()
    }

    private fun initUI() {
        setSupportActionBar(toolbar)

        supportActionBar!!.setDisplayShowHomeEnabled(false)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        supportActionBar!!.setHomeButtonEnabled(true)
        drawerToggle = ActionBarDrawerToggle(this, drawer_layout, toolbar, R.string.app_name, R.string.app_name)
        drawerToggle.isDrawerIndicatorEnabled = false

        drawerToggle.toolbarNavigationClickListener = View.OnClickListener {
            if (drawer_layout.showContextMenuForChild(rv_drawer)) {
                drawer_layout.closeDrawer(GravityCompat.START)
            } else {
                drawer_layout.openDrawer(GravityCompat.START)
            }
        }

        drawerItems = resources.getStringArray(R.array.nav_drawer_list).toList()
        drawerAdapter = DrawerAdapter(drawerItems, object : BaseListener {
            override fun onItemClick(position: Int) {
                when(position) {
                    0 -> {
                        gotoActivity(ProfileActivity::class.java)
                        finish()
                    }
                    1 -> {
                        gotoActivity(MapsActivity::class.java)
                        finish()
                    }
                }
                drawer_layout.closeDrawer(GravityCompat.START)
            }
        })

        rv_drawer.adapter = drawerAdapter
        addFragment(HomeFragment.newInstance())
    }

    private fun initData() {
        mainViewModel = ViewModelProvider(this, viewModelFactory as ViewModelProvider.Factory).get(MainViewModel::class.java)
        binding.mainViewModel = mainViewModel

        mainViewModel.mutableSignOut.observe(this, Observer { modelWrapper ->
            when(modelWrapper.progress) {
                Progress.SUCCESS -> {
                    gotoActivity(LoginActivity::class.java)
                    finish()
                }
                else -> { Toast.makeText(this, modelWrapper.message, Toast.LENGTH_SHORT).show() }
            }
        })

        if (!App.isLogin) {
            mainViewModel.getRemoteJobs(this)
        }

        mainViewModel.mutableRemoteJobs.observe(this, Observer { modelWrapper ->
            Toast.makeText(this, modelWrapper.message, Toast.LENGTH_SHORT).show()
         })

        mainViewModel.mutableSaveJobs.observe(this, Observer { modelWrapper ->
            Toast.makeText(this, modelWrapper.message, Toast.LENGTH_SHORT).show()
        })
    }

    private fun addFragment(fragment: Fragment) {
        fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction!!.replace(fl_main.id, fragment, null)
        fragmentTransaction!!.commit()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (drawerToggle.onOptionsItemSelected(item)) {
            true
        } else super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.unbind()
    }

    override fun onRefresh() {
        mainViewModel.getRemoteJobs(this)
    }
}
