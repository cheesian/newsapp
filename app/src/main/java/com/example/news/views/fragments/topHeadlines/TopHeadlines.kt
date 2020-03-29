package com.example.news.views.fragments.topHeadlines

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat.getDrawable
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.news.NewsApp
import com.example.news.R
import com.example.news.adapters.ArticlesAdapter
import com.example.news.databinding.TopHeadlinesBinding
import com.example.news.utils.*
import com.example.news.utils.Hide.hide
import com.example.news.utils.Notify.snackBar
import com.example.news.utils.PopulateSpinner.populateSpinner
import com.example.news.views.fragments.topHeadlines.viewModels.TopHeadlinesViewModel
import com.example.news.views.fragments.topHeadlines.viewModels.TopHeadlinesViewModelFactory
import kotlinx.android.synthetic.main.options.view.*
import javax.inject.Inject

/**
Created by ian
 */

class TopHeadlines : Fragment() {
    private lateinit var binding: TopHeadlinesBinding
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
    private var sourceList = mutableListOf<String>()
    private var topHeadlinesViewModel: TopHeadlinesViewModel? = null
    @Inject
    lateinit var topHeadlinesViewModelFactory: TopHeadlinesViewModelFactory
    private lateinit var refreshLayout: SwipeRefreshLayout
    var map: HashMap<String, String> = HashMap()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (context!!.applicationContext as NewsApp).applicationComponent.inject(this)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_top_headlines, container, false)
        binding.lifecycleOwner = this
        fragContext = binding.root.context
        progressBar = binding.progress
        refreshLayout = binding.refreshLayout
        recyclerView = binding.topHeadlinesRecyclerView
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = layoutManager
        val adapter = ArticlesAdapter(context!!)
        recyclerView.adapter = adapter
        topHeadlinesViewModel = ViewModelProvider(this, topHeadlinesViewModelFactory).get(TopHeadlinesViewModel::class.java)
        topHeadlinesViewModel!!.getGeneralResponse().observe(viewLifecycleOwner, Observer {
            topHeadlinesViewModel!!.consume(it)
        })

        topHeadlinesViewModel!!.articleList.observe(viewLifecycleOwner, Observer {
            adapter.setTopHeadlinesItems(it)
            layoutManager.scrollToPosition(0)
        })

        topHeadlinesViewModel!!.sourceList.observe(viewLifecycleOwner, Observer {
            it?.let {
                sourceList.clear()
                for (source in it) {
                    val sourceId = source.id
                    sourceList.add(sourceId)
                }
                sourceList.sort()
                populateSpinner(spinner = sourceSpinner, context = context!!, arrayList = sourceList, textViewResource = R.layout.spinner_text)
            }
        })

        topHeadlinesViewModel!!.visibility.observe(viewLifecycleOwner, Observer {
            progressBar.visibility = it
            if (it == View.GONE) refreshLayout.isRefreshing = false
        })

        topHeadlinesViewModel!!.message.observe(viewLifecycleOwner, Observer {
            val action = when (it) {
                "Fetching data ..." -> ""
                else -> "Reload"
            }
            snackBar(
                view = binding.root,
                message = it,
                actionMessage = action,
                function = View.OnClickListener { topHeadlinesViewModel!!.getTopHeadlines() }
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
//        ftbReload = binding.includedOptions.reload_button

        horizontalOptions = binding.includedOptions.options_horizontal

        checkBoxes.add(countryCheckBox)
        checkBoxes.add(categoryCheckBox)
        checkBoxes.add(sourceCheckBox)
        checkBoxes.add(keyWordCheckBox)
        checkBoxes.add(languageCheckBox)

        iconCancel = getDrawable(fragContext, R.drawable.ic_cancel_white_24dp)!!
        iconCancel.setBounds(0, 0, 60, 60)
        iconMenu = getDrawable(fragContext, R.drawable.ic_menu_white)!!
        iconMenu.setBounds(0, 0, 60, 60)
        iconSearch = getDrawable(fragContext, R.drawable.ic_search_white_24dp)!!
        iconSearch.setBounds(0, 0, 60, 60)
        refreshLayout.setOnRefreshListener {
            topHeadlinesViewModel!!.getTopHeadlines()
        }
        topHeadlinesViewModel!!.initialize()
        return binding.root
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser) topHeadlinesViewModel?.getTopHeadlines()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initializeView()
    }

    private fun toggleOptions() {
        when (horizontalOptions.visibility) {
            View.VISIBLE -> {
//                snackBar(view!!, "Snack", "Undo", SnackBarAction)
                val selectedCategory: String?
                val selectedSource: String
                val selectedKeyword: String
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
                    selectedCategory = categorySpinner.selectedItem.toString()
                    map["category"] = selectedCategory
                }

                if (sourceCheckBox.isChecked && sourceSpinner.selectedItem != null && sourceSpinner.selectedItem.toString() != getString(
                        R.string.spinner_prompt
                    )
                ) {
                    selectedSource = sourceSpinner.selectedItem.toString()
                    map["sources"] = selectedSource
                }

                if (keyWordCheckBox.isChecked && !keyWordEditText.getText().toString().isBlank()) {
                    selectedKeyword = keyWordEditText.getText().toString()
                    map["q"] = selectedKeyword
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
                    snackBar(view = binding.root, message = "Use the checkboxes to filter your search")
                } else {
                    reset()
                    topHeadlinesViewModel!!.getCustomTopHeadlines(map)
                }

            }
            View.GONE -> {
                Show.show(horizontalOptions)
                Show.show(ftbCancel)
//                Show.show(ftbReload)
                ftbAction.text = getString(R.string.search)
                ftbAction.setCompoundDrawables(iconSearch, null, null, null)
            }
        }
    }

    private fun initializeView() {

        ftbCancel.setOnClickListener {
            reset()
        }

        /*ftbReload.setOnClickListener {
            reset()
            topHeadlinesViewModel.getTopHeadlines()
        }*/

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
        sourceCheckBox.setOnClickListener { checkbox ->
            Checkbox.connectCheckboxToView(checkbox, sourceSpinner)
        }
        keyWordCheckBox.setOnClickListener { checkBox ->
            checkBox as CheckBox
            if (checkBox.isChecked) {
                Show.show(keyWordEditText)
            } else {
                hide(keyWordEditText)
            }
        }

        hide(countrySpinner)
        hide(categorySpinner)
        hide(sourceSpinner)
        hide(languageSpinner)
        hide(keyWordEditText)
        hide(ftbCancel)
//        hide(ftbReload)

    }

    private fun reset() {

        hide(horizontalOptions)
        hide(keyWordEditText)
        hide(sourceSpinner)
        hide(categorySpinner)
        hide(countrySpinner)
        hide(ftbCancel)
        hide(languageSpinner)
//        hide(ftbReload)

        Checkbox.uncheck(checkBoxes)

        ftbAction.text = getString(R.string.options)
        ftbAction.setCompoundDrawables(iconMenu, null, null, null)

    }
}