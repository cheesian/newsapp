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
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.news.NewsApp
import com.example.news.R
import com.example.news.VMFactory
import com.example.news.adapters.ArticlesAdapter
import com.example.news.data.response.everything.ArticleResponseEntity
import com.example.news.databinding.TopHeadlinesBinding
import com.example.news.utils.*
import com.example.news.utils.Hide.hide
import com.example.news.utils.Notify.snackBar
import com.example.news.utils.PopulateSpinner.populateSpinner
import com.example.news.views.fragments.topHeadlines.viewModels.TopHeadlinesViewModel
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
    lateinit var topHeadlinesViewModelFactory: VMFactory
    private lateinit var refreshLayout: SwipeRefreshLayout
    private var currentPage: Int = 1
    var map: HashMap<String, String> = HashMap()
    lateinit var prevRequest: HashMap<String, String>

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
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, true)
        recyclerView.layoutManager = layoutManager
        val adapter = ArticlesAdapter(context!!, binding.root)
        recyclerView.adapter = adapter
        recyclerView.addOnScrollListener(
            object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    if (progressBar.visibility != View.VISIBLE) {
                        with (layoutManager.findLastCompletelyVisibleItemPosition()) {
                            when (this) {
                                3 -> {
                                    fetchNextPage()
                                }
                            }
                        }
                    }

                }
            }
        )

        ItemTouchHelper(SwipeToDismiss(articlesAdapter = adapter)).attachToRecyclerView(recyclerView)

        topHeadlinesViewModel = ViewModelProvider(this, topHeadlinesViewModelFactory).get(TopHeadlinesViewModel::class.java)
        topHeadlinesViewModel!!.getGeneralResponse().observe(viewLifecycleOwner, Observer {
            topHeadlinesViewModel!!.consume(it)
        })
        topHeadlinesViewModel!!.articleList.observe(viewLifecycleOwner, Observer {
            adapter.setTopHeadlinesItems(it)
            layoutManager.scrollToPosition(it.size - 1)
//            The current page should be reset to 1 when the user swipes to refresh or makes a custom request
            currentPage = 1
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
                function = { reload() }
            )

        })

        topHeadlinesViewModel!!.lastRequest.observe(viewLifecycleOwner, Observer {
            prevRequest = it
        })

        topHeadlinesViewModel!!.nextPageList.observe(viewLifecycleOwner, Observer {nextPageList->
            currentPage++
            nextPageList.forEach {
                adapter.undoDelete(
                    position = 0,
                    articleResponseEntity = ArticleResponseEntity(
                        author = it.author,
                        title = it.title,
                        description = it.description,
                        url = it.url,
                        urlToImage = it.urlToImage,
                        publishedAt = it.publishedAt,
                        content = it.content,
                        sourceResponseEntity = it.sourceResponseEntity
                    )
                )
            }
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
            refreshLayout.isRefreshing = false
        }
        return binding.root
    }

    private fun fetchNextPage() {
//        This function will make a request for the next page using the same parameters which the user had specified in the previous query
        val nextPageRequest = prevRequest
        val nextPage = currentPage + 1
        nextPageRequest["page"] = nextPage.toString()
        Notify.log(
            tag = "fetchNextPage",
            message = nextPageRequest.toString()
        )
        topHeadlinesViewModel!!.getNextPage(nextPageRequest)
    }

    private fun reload() {
//        The reloading should use the previous query
        topHeadlinesViewModel!!.getNextPage(prevRequest)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initializeView()
    }

    private fun toggleOptions() {
        when (horizontalOptions.visibility) {
            View.VISIBLE -> {
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
//    when you start the app for the first time it does not get the articles on its own, you have to swipe down
//    this function checks solves that bug
    private fun populateViewIfEmpty () {
        val list = topHeadlinesViewModel?.articleList?.value
        if (list.isNullOrEmpty()) topHeadlinesViewModel?.getTopHeadlines()
    }

    private fun initializeView() {

        populateViewIfEmpty()

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

    }

    private fun reset() {

        hide(horizontalOptions)
        hide(keyWordEditText)
        hide(sourceSpinner)
        hide(categorySpinner)
        hide(countrySpinner)
        hide(ftbCancel)
        hide(languageSpinner)

        Checkbox.uncheck(checkBoxes)

        ftbAction.text = getString(R.string.options)
        ftbAction.setCompoundDrawables(iconMenu, null, null, null)

    }
}