package com.flink.demo.view.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import com.flink.demo.R
import com.flink.demo.databinding.ActivityDetailBinding
import com.flink.demo.model.data.response.Movie
import com.flink.demo.viewmodel.DetailViewModel
import com.flink.demo.viewmodel.extentions.convertOffsetToPercent
import com.flink.demo.viewmodel.extentions.loadImage
import com.flink.demo.viewmodel.extentions.toDp
import com.flink.demo.viewmodel.preferences.AppPreferences
import com.google.android.material.appbar.AppBarLayout
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf


class DetailActivity : BaseActivity<ActivityDetailBinding>(), AppBarLayout.OnOffsetChangedListener {

    private var scrollRange = -1
    private var collapsedPercent : Float = -1f
    private val viewModel: DetailViewModel by viewModel { parametersOf() }

    override fun getLayout(): Int = R.layout.activity_detail

    override fun getToolbar(): Toolbar? {
        binding.toolbar.setNavigationIcon(R.drawable.ic_action_arrow_back_red)
        binding.toolbar.title = ""
        return binding.toolbar
    }

    override fun initViews(savedInstanceState: Bundle?) {
        binding.appBarLayout.addOnOffsetChangedListener(this)

        val movie = intent.getSerializableExtra("movie") as Movie
        binding.movie = movie
        binding.toolbarImage.loadImage(AppPreferences.IMAGE_PATH_BIG+movie.poster_path, R.drawable.img_thumb)
        binding.textTitleAutoscroll.text = movie.title
        binding.textTitleTop.text = movie.title
        binding.textDate.text = movie.release_date
        binding.textDescription.text = movie.overview
    }

    override fun initViewModel(savedInstanceState: Bundle?) {
        viewModel.movie.observe(this, movieObserver)

        viewModel.getMovie(binding.movie?.id.toString())
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    override fun onOffsetChanged(appBar: AppBarLayout?, verticalOffset: Int) {
        val actualPercent = convertOffsetToPercent(verticalOffset, 0)
        if(collapsedPercent != actualPercent){
            collapsedPercent = actualPercent
            binding.isCollapsed = changeAlphaValOfTitle(verticalOffset) > 0
            selectIfNecessaryAutoscrollTitle()
        }
    }

    private fun changeAlphaValOfTitle(verticalOffset: Int) : Float{
        val skipValue = (binding.collapsingToolbarLayout.height - binding.viewTitle.measuredHeight) - 30.toDp
        val alphaVal = convertOffsetToPercent(verticalOffset, skipValue.toInt())
        binding.textTitleAutoscroll.alpha = alphaVal
        return alphaVal
    }

    private fun selectIfNecessaryAutoscrollTitle(){
        if(binding.isCollapsed!! && !binding.textTitleAutoscroll.isSelected)
            binding.textTitleAutoscroll.isSelected = true
        else if(!binding.isCollapsed!! && binding.textTitleAutoscroll.isSelected)
            binding.textTitleAutoscroll.isSelected = false
    }

    private fun convertOffsetToPercent(verticalOffset: Int, skipValue: Int) : Float{
        if (scrollRange == -1)
            scrollRange = binding.appBarLayout.totalScrollRange
        return verticalOffset.convertOffsetToPercent(scrollRange, verticalOffset, skipValue)
    }

    private val movieObserver = Observer<Movie> { movie ->
        val companies = StringBuilder()
        movie.production_companies?.forEach { company ->
            companies.append("${company.name}\n")
        }
        binding.textCompaniesNames.text = companies.toString()
        val genres = StringBuilder()
        movie.genres?.forEach { genre ->
            genres.append("${genre.name}\n")
        }
        binding.textGenresNames.text = genres.toString()
        binding.textHomepageName.text = movie.homepage
        binding.textHomepageName.setOnClickListener {
            if(!movie.homepage.isNullOrEmpty()){
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(movie.homepage))
                startActivity(browserIntent)
            }
        }
    }
}