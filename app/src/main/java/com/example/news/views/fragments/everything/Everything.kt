package com.example.news.views.fragments.everything

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
import com.example.news.databinding.EverythingBinding
import com.example.news.utils.*
import com.example.news.utils.PopulateSpinner.populateSpinner
import com.example.news.views.fragments.everything.viewModels.EverythingViewModel
import com.example.news.views.fragments.everything.viewModels.EverythingViewModelFactory
import kotlinx.android.synthetic.main.options.view.*
import kotlinx.android.synthetic.main.options.view.action_button
import kotlinx.android.synthetic.main.options.view.cancel_button
import kotlinx.android.synthetic.main.options.view.checkBox_keyword
import kotlinx.android.synthetic.main.options.view.checkBox_language
import kotlinx.android.synthetic.main.options.view.checkBox_source
import kotlinx.android.synthetic.main.options.view.editText_keyword
import kotlinx.android.synthetic.main.options.view.options_horizontal
import kotlinx.android.synthetic.main.options.view.spinner_language
import kotlinx.android.synthetic.main.options.view.spinner_source
import kotlinx.android.synthetic.main.options_everything.view.*
import javax.inject.Inject


/**
Created by ian
 */

class Everything : Fragment() {

    private lateinit var binding: EverythingBinding
    private lateinit var fragContext: Context
    private lateinit var sourceSpinner: Spinner
    private lateinit var languageSpinner: Spinner
    private lateinit var sourceCheckBox: CheckBox
    private lateinit var keyWordCheckBox: CheckBox
    private lateinit var languageCheckBox: CheckBox
    private lateinit var fromCheckBox: CheckBox
    private lateinit var toCheckBox: CheckBox
    private lateinit var keyWordEditText: EditText
    private lateinit var fromEditText: EditText
    private lateinit var toEditText: EditText
    private lateinit var calendarView: CalendarView
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
    private var everythingViewModel: EverythingViewModel? = null
    @Inject
    lateinit var everythingViewModelFactory: EverythingViewModelFactory
    private lateinit var refreshLayout: SwipeRefreshLayout
    var map: HashMap<String, String> = HashMap()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (context!!.applicationContext as NewsApp).applicationComponent.inject(this)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_everything, container, false)
        fragContext = binding.root.context
        progressBar = binding.progress
        refreshLayout = binding.refreshLayout
        recyclerView = binding.everythingRecyclerView
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = layoutManager
        val adapter = ArticlesAdapter(context!!)
        recyclerView.adapter = adapter
        everythingViewModel = ViewModelProvider(this, everythingViewModelFactory).get(EverythingViewModel::class.java)
        everythingViewModel!!.getGeneralResponse().observe(viewLifecycleOwner, Observer {
            everythingViewModel!!.consume(it)
        })

        everythingViewModel!!.articleList.observe(viewLifecycleOwner, Observer {
            adapter.setItems(it)
            layoutManager.scrollToPosition(0)
        })

        everythingViewModel!!.sourceList.observe(viewLifecycleOwner, Observer {
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

        everythingViewModel!!.visibility.observe(viewLifecycleOwner, Observer {
            progressBar.visibility = it
        })

        everythingViewModel!!.message.observe(viewLifecycleOwner, Observer {
            val action = when (it) {
                "Fetching data ..." -> ""
                else -> "Reload"
            }
            Notify.snackBar(
                view = binding.root,
                message = it,
                actionMessage = action,
                function = { everythingViewModel!!.getEverythingWithoutDates() }
            )

        })

        sourceSpinner = binding.includedOptions.spinner_source
        languageSpinner = binding.includedOptions.spinner_language
        populateSpinner(languageSpinner, fragContext, R.array.languages, textViewResource = R.layout.spinner_text)

        checkBoxes = ArrayList()
        sourceCheckBox = binding.includedOptions.checkBox_source
        keyWordCheckBox = binding.includedOptions.checkBox_keyword
        languageCheckBox = binding.includedOptions.checkBox_language
        fromCheckBox = binding.includedOptions.checkBox_from
        toCheckBox = binding.includedOptions.checkBox_to

        keyWordEditText = binding.includedOptions.editText_keyword
        fromEditText = binding.includedOptions.editText_from
        toEditText = binding.includedOptions.editText_to

        ftbAction = binding.includedOptions.action_button
        ftbCancel = binding.includedOptions.cancel_button

        horizontalOptions = binding.includedOptions.options_horizontal

        calendarView =binding.calendar

        checkBoxes.addAll(listOf(sourceCheckBox, keyWordCheckBox, languageCheckBox, fromCheckBox, toCheckBox))

        iconCancel = getDrawable(fragContext, R.drawable.ic_cancel_white_24dp)!!
        iconCancel.setBounds(0, 0, 60, 60)
        iconMenu = getDrawable(fragContext, R.drawable.ic_menu_white)!!
        iconMenu.setBounds(0, 0, 60, 60)
        iconSearch = getDrawable(fragContext, R.drawable.ic_search_white_24dp)!!
        iconSearch.setBounds(0, 0, 60, 60)
        return binding.root
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser) everythingViewModel?.getEverythingWithoutDates()
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initializeView()
    }

    private fun toggleOptions() {
        when (horizontalOptions.visibility) {
            View.VISIBLE -> {

                map.clear()

                if (sourceCheckBox.isChecked && sourceSpinner.selectedItem != null && sourceSpinner.selectedItem.toString() != getString(
                        R.string.spinner_prompt
                    )
                ) {
                    sourceSpinner.selectedItem.toString().apply {
                        map["sources"] = this
                    }
                }

                if (keyWordCheckBox.isChecked && !keyWordEditText.getText().toString().isBlank()) {
                    keyWordEditText.getText().toString().apply {
                        map["q"] = this
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

                if (fromCheckBox.isChecked && !fromEditText.getText().toString().isBlank()) {
                    fromEditText.getText().toString().apply {
                        map["from"] = this
                    }
                }

                if (toCheckBox.isChecked && !toEditText.getText().toString().isBlank()) {
                    toEditText.getText().toString().apply {
                        map["to"] = this
                    }
                }

                if (map.isEmpty()) {
                    Notify.snackBar(
                        view = binding.root,
                        message = "Use the checkboxes to filter your search"
                    )
                } else {
                    reset()
                    everythingViewModel!!.getCustomEverything(map)
                }

            }
            View.GONE -> {
                Show.show(horizontalOptions)
                Show.show(ftbCancel)
                ftbAction.text = getString(R.string.search)
                ftbAction.setCompoundDrawables(iconSearch, null, null, null)
            }
        }
    }

    private fun initializeView() {

        refreshLayout.setOnRefreshListener {
            everythingViewModel!!.getEverythingWithoutDates()
            refreshLayout.isRefreshing = false
        }

        ftbCancel.setOnClickListener {
            reset()
        }

        ftbAction.setOnClickListener {
            toggleOptions()
        }

        languageCheckBox.setOnClickListener { checkbox ->
            Checkbox.connectCheckboxToView(checkbox, languageSpinner)
        }

        sourceCheckBox.setOnClickListener { checkbox ->
            Checkbox.connectCheckboxToView(checkbox, sourceSpinner)
        }

        keyWordCheckBox.setOnClickListener { checkBox ->
            Checkbox.connectCheckboxToView(checkBox, keyWordEditText)
        }

        fromCheckBox.setOnClickListener { checkBox->
            checkBox as CheckBox
            when (checkBox.isChecked) {
                true -> Show.show(fromEditText)
                false -> {
                    Hide.hide(fromEditText)
                    Hide.hide(calendarView)
                }
            }
        }

        toCheckBox.setOnClickListener {checkBox->
            checkBox as CheckBox
            when (checkBox.isChecked) {
                true -> Show.show(toEditText)
                false -> {
                    Hide.hide(toEditText)
                    Hide.hide(calendarView)
                }
            }
        }

        fromEditText.setOnClickListener {
            calendarView.apply {
                this.visibility = View.VISIBLE
            }
        }

        toEditText.setOnClickListener {
            with (calendarView) {
                visibility = View.VISIBLE
            }
        }

        Hide.hide(sourceSpinner)
        Hide.hide(languageSpinner)
        Hide.hide(keyWordEditText)
        Hide.hide(fromEditText)
        Hide.hide(toEditText)
        Hide.hide(ftbCancel)
        Hide.hide(calendarView)

    }

    private fun reset() {

        Hide.hide(horizontalOptions)
        Hide.hide(keyWordEditText)
        Hide.hide(fromEditText)
        Hide.hide(toEditText)
        Hide.hide(sourceSpinner)
        Hide.hide(ftbCancel)
        Hide.hide(languageSpinner)
        Hide.hide(calendarView)

        Checkbox.uncheck(checkBoxes)

        ftbAction.text = getString(R.string.options)
        ftbAction.setCompoundDrawables(iconMenu, null, null, null)

    }
}