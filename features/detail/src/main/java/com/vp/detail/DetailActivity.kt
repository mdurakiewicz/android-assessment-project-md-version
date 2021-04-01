package com.vp.detail

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.widget.CheckBox
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.vp.commonaddons.SharedPreferencesManager
import com.vp.detail.databinding.ActivityDetailBinding
import com.vp.detail.model.toListItem
import com.vp.detail.viewmodel.DetailsViewModel
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class DetailActivity : DaggerAppCompatActivity(), QueryProvider {

    @Inject
    lateinit var factory: ViewModelProvider.Factory


    @Inject
    lateinit var sharedPreferencesManager: SharedPreferencesManager

    private var starCheckBox: CheckBox? = null
    private lateinit var detailViewModel: DetailsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityDetailBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail)
        detailViewModel = ViewModelProviders.of(this, factory).get(DetailsViewModel::class.java)
        binding.viewModel = detailViewModel
        queryProvider = this
        binding.setLifecycleOwner(this)
        detailViewModel.fetchDetails()
        detailViewModel.title().observe(this, Observer {
            supportActionBar?.title = it
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detail_menu, menu)
        val menuItem = menu!!.findItem(R.id.star)
        starCheckBox = menuItem.actionView as CheckBox

        starCheckBox?.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked){
                sharedPreferencesManager.addFavorite(detailViewModel.detailsData().toListItem())
            } else {
                sharedPreferencesManager.removeFavorite(detailViewModel.detailsData().toListItem())
            }
        }

        return true
    }

    override fun getMovieId(): String {
        return intent?.data?.getQueryParameter("imdbID") ?: run {
            throw IllegalStateException("You must provide movie id to display details")
        }
    }

    companion object {
        lateinit var queryProvider: QueryProvider

        @JvmStatic
        fun start(activity: Activity, imdbID: String) {
            val intent = Intent(activity, DetailActivity::class.java)
            intent.data = Uri.Builder()
                    .appendQueryParameter("imdbID", imdbID)
                    .build()
            activity.startActivity(intent)
        }
    }
}
