package com.codingwithmitch.hiltexperiment.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.codingwithmitch.hiltexperiment.R
import com.codingwithmitch.hiltexperiment.model.Blog
import com.codingwithmitch.hiltexperiment.util.DataState.Error
import com.codingwithmitch.hiltexperiment.util.DataState.Loading
import com.codingwithmitch.hiltexperiment.util.DataState.Success
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.coroutines.ExperimentalCoroutinesApi


@AndroidEntryPoint
class MainFragment constructor(private val someString: String)
    : Fragment(R.layout.fragment_main) {
    companion object {
        private const val TAG = "MainFragment"
    }
    private val viewModel: MainViewModel by viewModels()

    @ExperimentalCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "onViewCreated: $someString")
        subscribeObservers()
        viewModel.setStateEvent(MainStateEvent.GetBlogEvents)
    }

    private fun subscribeObservers() {
        viewModel.dataState.observe(viewLifecycleOwner, Observer { dataState ->
            when(dataState) {
                is Success<List<Blog>> -> {
                    displayProgressBar(false)
                    appendBlogTitles(dataState.data)
                }
                is Error -> {
                    displayProgressBar(false)
                    displayError(dataState.exception.message)
                }
                is Loading -> {
                    displayProgressBar(true)
                }
            }

        })
    }

    private fun displayError(message: String?) {
        if (message.isNullOrEmpty()) {
            text.text = message
        } else {
            text.text = "Unknown error"
        }
    }

    private fun displayProgressBar(isDisplayed: Boolean) {
        progress_bar.visibility = if (isDisplayed) View.VISIBLE else View.GONE
    }

    private fun appendBlogTitles(blogs: List<Blog>) {
        val sb = StringBuilder()
        blogs.forEach { sb.append("${it.title}\n") }
        text.text = sb.toString()
    }

}