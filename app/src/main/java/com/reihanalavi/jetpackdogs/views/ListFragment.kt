package com.reihanalavi.jetpackdogs.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.LinearLayoutManager

import com.reihanalavi.jetpackdogs.R
import com.reihanalavi.jetpackdogs.viewmodels.DogViewModel
import kotlinx.android.synthetic.main.fragment_list.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.support.v4.onRefresh

/**
 * A simple [Fragment] subclass.
 */
class ListFragment : Fragment() {

    private lateinit var viewModel: DogViewModel
    private lateinit var adapters: DogAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val views = inflater.inflate(R.layout.fragment_list, container, false)

        return views
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(DogViewModel::class.java)
        adapters = DogAdapter(arrayListOf()) {
        }

        viewModel.refresh()

        recyclerView_list.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = adapters
        }

        swipeRefresh_list.onRefresh {
            recyclerView_list.visibility = View.INVISIBLE
            progressBar_list.visibility = View.INVISIBLE
            textView_list_error.visibility = View.VISIBLE
            viewModel.refresh()
            swipeRefresh_list.isRefreshing = false
        }

        observeViewModel()
    }

    fun observeViewModel() {
        viewModel.dogs.observe(this, Observer {  dogs ->
            dogs?.let {
                recyclerView_list.visibility = View.VISIBLE
                adapters.updateAllDogs(it)
            }
        })

        viewModel.error.observe(this, Observer { error ->
            error?.let {
                if(it) {
                    textView_list_error.visibility = View.VISIBLE
                } else {
                    textView_list_error.visibility = View.INVISIBLE
                }
            }
        })

        viewModel.loading.observe(this, Observer { loading ->
            loading?.let {
                if(it) {
                    progressBar_list.visibility = View.VISIBLE
                    textView_list_error.visibility = View.INVISIBLE
                    recyclerView_list.visibility = View.INVISIBLE
                } else {
                    progressBar_list.visibility = View.INVISIBLE
                    textView_list_error.visibility = View.VISIBLE
                    recyclerView_list.visibility = View.VISIBLE

                }
            }
        })
    }

}
