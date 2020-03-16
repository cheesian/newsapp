package com.example.news.views.fragments.sources

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.news.NewsApp
import com.example.news.R
import com.example.news.adapters.SourcesAdapter
import com.example.news.databinding.SourcesBinding
import com.example.news.utils.Checkbox
import com.example.news.utils.Checkbox.uncheck
import com.example.news.utils.Hide.hide
import com.example.news.utils.Notify
import com.example.news.utils.Notify.toast
import com.example.news.utils.PopulateSpinner.populateSpinner
import com.example.news.utils.Show
import com.example.news.utils.Show.show
import com.example.news.views.fragments.sources.viewModels.SourcesViewModel
import com.example.news.views.fragments.sources.viewModels.SourcesViewModelFactory
import kotlinx.android.synthetic.main.options.view.*
import javax.inject.Inject


/**
Created by ian
 */

class Sources : Fragment() {

    private lateinit var binding: SourcesBinding
    private lateinit var fragContext: Context
    private lateinit var categorySpinner: Spinner
    private lateinit var countrySpinner: Spinner
    private lateinit var sourceSpinner: Spinner
    private lateinit var languageSpinner: Spinner
    private lateinit var categoryCheckBox: CheckBox
    private lateinit var countryCheckBox: CheckBox
    private lateinit var sourceCheckBox: CheckBox
    private lateinit var keyWordCheckBox: CheckBox
    private lateinit var languageCheckBox: CheckBox
    private lateinit var keyWordEditText: EditText
    private lateinit var checkBoxes: ArrayList<CheckBox>
    private lateinit var ftbAction: Button
    private lateinit var ftbCancel: Button
    private lateinit var horizontalOptions: ConstraintLayout
    private lateinit var iconCancel: Drawable
    private lateinit var iconMenu: Drawable
    private lateinit var iconSearch: Drawable
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var sourcesViewModel: SourcesViewModel
    @Inject
    lateinit var sourcesViewModelFactory: SourcesViewModelFactory
    private lateinit var refreshLayout: SwipeRefreshLayout
    var map: HashMap<String, String> = HashMap()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (context!!.applicationContext as NewsApp).applicationComponent.inject(this)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sources, container, false)
        fragContext = binding.root.context
        progressBar = binding.progress
        refreshLayout = binding.refreshLayout
        recyclerView = binding.sourcesRecyclerView
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, true)
        recyclerView.layoutManager = layoutManager
        val adapter = SourcesAdapter(context!!)
        recyclerView.adapter = adapter
        sourcesViewModel = ViewModelProviders.of(this, sourcesViewModelFactory).get(SourcesViewModel::class.java)
        sourcesViewModel.getGeneralResponse().observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            sourcesViewModel.consume(it)
        })

        sourcesViewModel.sourceList.observe(viewLifecycleOwner, Observer {
            adapter.setItems(it)
            layoutManager.scrollToPosition(it.size - 1)
        })

        sourcesViewModel.visibility.observe(viewLifecycleOwner, Observer {
            progressBar.visibility = it
            if (it == View.GONE) refreshLayout.isRefreshing = false
        })

        sourcesViewModel.message.observe(viewLifecycleOwner, Observer {
            val action = when (it) {
                "Fetching data ..." -> ""
                else -> "Reload"
            }
            Notify.snackBar(
                view = binding.root,
                message = it,
                actionMessage = action,
                function = View.OnClickListener { sourcesViewModel.getSources() }
            )

        })
        categorySpinner = binding.includedOptions.spinner_category
        countrySpinner = binding.includedOptions.spinner_country
        sourceSpinner = binding.includedOptions.spinner_source
        languageSpinner = binding.includedOptions.spinner_language
        populateSpinner(categorySpinner, fragContext, R.array.categories, textViewResource = R.layout.spinner_text)
        populateSpinner(countrySpinner, fragContext, R.array.countries, textViewResource = R.layout.spinner_text)
        populateSpinner(languageSpinner, fragContext, R.array.languages, textViewResource = R.layout.spinner_text)

        checkBoxes = ArrayList()
        countryCheckBox = binding.includedOptions.checkBox_country
        categoryCheckBox = binding.includedOptions.checkBox_category
        sourceCheckBox = binding.includedOptions.checkBox_source
        keyWordCheckBox = binding.includedOptions.checkBox_keyword
        languageCheckBox = binding.includedOptions.checkBox_language

        keyWordEditText = binding.includedOptions.editText_keyword

        ftbAction = binding.includedOptions.action_button
        ftbCancel = binding.includedOptions.cancel_button

        horizontalOptions = binding.includedOptions.options_horizontal

        checkBoxes.add(countryCheckBox)
        checkBoxes.add(categoryCheckBox)
        checkBoxes.add(keyWordCheckBox)

        iconCancel = fragContext.resources.getDrawable(R.drawable.ic_cancel_white_24dp)
        iconCancel.setBounds(0, 0, 60, 60)
        iconMenu = fragContext.resources.getDrawable(R.drawable.ic_menu_white)
        iconMenu.setBounds(0, 0, 60, 60)
        iconSearch = fragContext.resources.getDrawable(R.drawable.ic_search_white_24dp)
        iconSearch.setBounds(0, 0, 60, 60)
        refreshLayout.setOnRefreshListener {
            sourcesViewModel.getSources()
        }
        return binding.root
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser) sourcesViewModel.getSources()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initializeView()
    }

    private fun toggleOptions() {
        when (horizontalOptions.visibility) {
            View.VISIBLE -> {
                map.clear()

                if (countryCheckBox.isChecked && countrySpinner.selectedItem != null && countrySpinner.selectedItem.toString() != getString(
                        R.string.spinner_prompt
                    )
                ) {
                    countrySpinner.selectedItem.toString().also {
                        map["country"] = it.takeLast(2)
                    }
                }

                if (categoryCheckBox.isChecked && categorySpinner.selectedItem != null && categorySpinner.selectedItem.toString() != getString(
                        R.string.spinner_prompt
                    )
                ) {
                    categorySpinner.selectedItem.toString().also {
                        map["category"] = it
                    }
                }

                if (languageCheckBox.isChecked && languageSpinner.selectedItem != null && languageSpinner.selectedItem.toString() != getString(
                        R.string.spinner_prompt
                    )
                ) {
                    languageSpinner.selectedItem.toString().also {
                        map["language"] = it.takeLast(2)
                    }
                }

                if (map.isEmpty()) {
                    Notify.snackBar(
                        view = binding.root,
                        message = "Use the checkboxes to filter your search"
                    )
                } else {
                    reset()
                    sourcesViewModel.getCustomSources(map)
                }

            }
            View.GONE -> {
                show(horizontalOptions)
                show(ftbCancel)
                ftbAction.text = getString(R.string.search)
                ftbAction.setCompoundDrawables(iconSearch, null, null, null)
            }
        }
    }

    private fun initializeView() {

        ftbCancel.setOnClickListener {
            reset()
        }

        ftbAction.setOnClickListener {
            toggleOptions()
        }

        countryCheckBox.setOnClickListener { checkbox ->
            Checkbox.connectCheckboxToView(checkbox, countrySpinner)
        }
        languageCheckBox.setOnClickListener { checkbox ->
            Checkbox.connectCheckboxToView(checkbox, languageSpinner)
        }
        categoryCheckBox.setOnClickListener { checkbox ->
            Checkbox.connectCheckboxToView(checkbox, categorySpinner)
        }
        /*sourceCheckBox.setOnClickListener { checkbox ->
            Checkbox.connectCheckboxToView(checkbox, sourceSpinner)
        }
        keyWordCheckBox.setOnClickListener { checkBox ->
            checkBox as CheckBox
            if (checkBox.isChecked) {
                show(keyWordEditText)
            } else {
                hide(keyWordEditText)
            }
        }*/

        hide(countrySpinner)
        hide(categorySpinner)
        hide(sourceSpinner)
        hide(languageSpinner)
        hide(keyWordEditText)
        hide(ftbCancel)
        hide(sourceCheckBox)
        hide(keyWordCheckBox)
    }

    private fun reset() {

        hide(horizontalOptions)
        hide(keyWordEditText)
        hide(sourceSpinner)
        hide(categorySpinner)
        hide(countrySpinner)
        hide(ftbCancel)

        uncheck(checkBoxes)

        ftbAction.text = getString(R.string.options)
        ftbAction.setCompoundDrawables(iconMenu, null, null, null)

    }
}