package com.reihanalavi.jetpackdogs.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation

import com.reihanalavi.jetpackdogs.R
import com.reihanalavi.jetpackdogs.viewmodels.DetailViewModel
import kotlinx.android.synthetic.main.fragment_detail.*
import org.jetbrains.anko.sdk25.coroutines.onClick

/**
 * A simple [Fragment] subclass.
 */
class DetailFragment : Fragment() {

    private var dogUid = 0
    private lateinit var viewModel: DetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val views = inflater.inflate(R.layout.fragment_detail, container, false)
        return views
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(DetailViewModel::class.java)
        viewModel.fetch()

        arguments?.let {
            dogUid = DetailFragmentArgs.fromBundle(it).dogUid
        }

        observeViewModel()
    }

    fun observeViewModel() {
        viewModel.dogLiveData.observe(this, Observer { dog ->
            dog?.let {
                textView_detail_name.text = it.dogBreed
                textView_detail_purpoe.text = it.bredFor
                textView_detail_temperament.text = it.temperament
                textView_detail_lifespan.text = it.lifeSpan
            }
        })
    }
}
