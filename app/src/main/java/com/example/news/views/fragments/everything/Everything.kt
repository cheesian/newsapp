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
import com.example.news.utils.Notify.toast
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
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList
import kotlin.collections.HashMap


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
    private var everythingViewModel: EverythingViewModel? = null
    @Inject
    lateinit var everythingViewModelFactory: EverythingViewModelFactory
    private lateinit var refreshLayout: SwipeRefreshLayout
    private lateinit var calendarView: CalendarView
    private lateinit var calendar: Calendar
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

        keyWordEditText = binding.includedOptions.editText_keyword

        ftbAction = binding.includedOptions.action_button
        ftbCancel = binding.includedOptions.cancel_button

        horizontalOptions = binding.includedOptions.options_horizontal

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
            everythingViewModel!!.getEverythingWithoutDates()
            refreshLayout.isRefreshing = false
        }
        calendar = Calendar.getInstance()
        calendar.set(Calendar.MONTH, Calendar.JANUARY);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.YEAR, 2020);
        calendarView = binding.includedOptions.calendarView
        calendarView.setDate(calendar.timeInMillis, true, true);
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
//                snackBar(view!!, "Snack", "Undo", SnackBarAction)
                val selectedSource: String
                val selectedKeyword: String
                map.clear()

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

        languageCheckBox.setOnClickListener { checkbox ->
            Checkbox.connectCheckboxToView(checkbox, languageSpinner)
        }

        sourceCheckBox.setOnClickListener { checkbox ->
            Checkbox.connectCheckboxToView(checkbox, sourceSpinner)
        }
        keyWordCheckBox.setOnClickListener { checkBox ->
            checkBox as CheckBox
            if (checkBox.isChecked) {
                Show.show(keyWordEditText)
            } else {
                Hide.hide(keyWordEditText)
            }
        }
        calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->
            val message = "Selected date Day: " + dayOfMonth + " Month : " + (month + 1) + " Year " + year
            toast(context = context!!, message = message)
        }

        Hide.hide(sourceSpinner)
        Hide.hide(languageSpinner)
        Hide.hide(keyWordEditText)
        Hide.hide(ftbCancel)
//        hide(ftbReload)

    }

    private fun reset() {

        Hide.hide(horizontalOptions)
        Hide.hide(keyWordEditText)
        Hide.hide(sourceSpinner)
        Hide.hide(ftbCancel)
        Hide.hide(languageSpinner)
//        hide(ftbReload)sourceSpinner

        Checkbox.uncheck(checkBoxes)

        ftbAction.text = getString(R.string.options)
        ftbAction.setCompoundDrawables(iconMenu, null, null, null)

    }
}