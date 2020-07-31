package com.programiqsolutions.news.views.fragments.feedback

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.programiqsolutions.news.NewsApp
import com.programiqsolutions.news.R
import com.programiqsolutions.news.VMFactory
import com.programiqsolutions.news.databinding.FeedbackBinding
import com.programiqsolutions.news.utils.Notify.snackBar
import com.programiqsolutions.news.utils.Notify.toast
import com.programiqsolutions.news.utils.PopulateSpinner.populateSpinner
import javax.inject.Inject

class Feedback: Fragment() {

    lateinit var binding: FeedbackBinding
    lateinit var spinner: Spinner
    lateinit var viewModel: FeedbackViewModel
    @Inject lateinit var factory: VMFactory

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        (requireContext().applicationContext as NewsApp).applicationComponent.inject(this)
        binding = FeedbackBinding.inflate(inflater)
        binding.lifecycleOwner = this
        viewModel = ViewModelProvider(this, factory).get(FeedbackViewModel::class.java)
        binding.viewModel = viewModel
        viewModel.message.observe(viewLifecycleOwner, Observer {
            snackBar(view = binding.root, message = it)
        })
        viewModel.response.observe(viewLifecycleOwner, Observer {
            viewModel.consumeResponse(it)
        })
        spinner = binding.spinnerFeedback
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                viewModel.spinnerSelectedItem.value = spinner.selectedItem.toString()
                viewModel.spinnerError.value = null
            }

        }
        populateSpinner(spinner = spinner, context = requireContext(), arrayResource = R.array.feedbacks, textViewResource = R.layout.spinner_text)
        return binding.root
    }
}