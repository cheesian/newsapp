package com.example.news.views.fragments.everything

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Build
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
import com.example.news.databinding.EverythingBinding
import com.example.news.utils.*
import com.example.news.utils.DateTimeUtil.getYMD
import com.example.news.utils.Notify.toast
import com.example.news.utils.PopulateSpinner.populateSpinner
import com.example.news.views.fragments.everything.viewModels.EverythingViewModel
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
    private lateinit var fromDateCheckBox: CheckBox
    private lateinit var toDateCheckBox: CheckBox
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
    lateinit var everythingViewModelFactory: VMFactory
    private lateinit var refreshLayout: SwipeRefreshLayout
    private lateinit var datePicker: DatePicker
    private lateinit var calendar: Calendar
    private lateinit var fromDateTextView: TextView
    private lateinit var toDateTextView: TextView
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
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, true)
        recyclerView.layoutManager = layoutManager
        val adapter = ArticlesAdapter(context!!, binding.root)
        recyclerView.adapter = adapter
        ItemTouchHelper(SwipeToDismiss(adapter)).attachToRecyclerView(recyclerView)
        everythingViewModel = ViewModelProvider(this, everythingViewModelFactory).get(EverythingViewModel::class.java)
        everythingViewModel!!.getGeneralResponse().observe(viewLifecycleOwner, Observer {
            everythingViewModel!!.consume(it)
        })

        everythingViewModel!!.articleList.observe(viewLifecycleOwner, Observer {
            adapter.setItems(it)
            layoutManager.scrollToPosition(it.size - 1)
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
        fromDateCheckBox = binding.includedOptions.checkBox_from
        toDateCheckBox = binding.includedOptions.checkBox_to

        keyWordEditText = binding.includedOptions.editText_keyword

        fromDateTextView = binding.includedOptions.tv_from
        toDateTextView = binding.includedOptions.tv_to

        ftbAction = binding.includedOptions.action_button
        ftbCancel = binding.includedOptions.cancel_button

        horizontalOptions = binding.includedOptions.options_horizontal

        checkBoxes.addAll(listOf(sourceCheckBox, keyWordCheckBox, languageCheckBox, fromDateCheckBox, toDateCheckBox))

        iconCancel = getDrawable(fragContext, R.drawable.ic_cancel_white_24dp)!!
        iconCancel.setBounds(0, 0, 60, 60)
        iconMenu = getDrawable(fragContext, R.drawable.ic_menu_white)!!
        iconMenu.setBounds(0, 0, 60, 60)
        iconSearch = getDrawable(fragContext, R.drawable.ic_search_white_24dp)!!
        iconSearch.setBounds(0, 0, 60, 60)

        datePicker = binding.includedOptions.date_picker
        calendar = Calendar.getInstance()
        val minAllowedDate = with (calendar.clone() as Calendar) {
            add(Calendar.DATE, -30)
            this
        }
        val maxAllowedDate = with (calendar.clone() as Calendar) {
            add(Calendar.DATE, 1)
            this
        }
        datePicker.minDate = minAllowedDate.timeInMillis
        datePicker.maxDate = maxAllowedDate.timeInMillis

        return binding.root
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

                if (fromDateCheckBox.isChecked && !fromDateTextView.text.isNullOrBlank()) {
//                    map["to"] and map["from"] must both be set or unset so we cannot set map["from"] right here
                    when (toDateCheckBox.isChecked){
                        true -> {
                            map["to"] = toDateTextView.text?.toString() ?: getYMD()
                            map["from"] = fromDateTextView.text.toString()
                        }
                        false -> {
                            map["from"] = fromDateTextView.text.toString()
                            map["to"] = getYMD()
                        }
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

//    when you start the app for the first time it does not get the articles on its own, you have to swipe down
//    this function checks solves that bug
    private fun populateViewIfEmpty () {
        val list = everythingViewModel?.articleList?.value
        if (list.isNullOrEmpty()) everythingViewModel?.getEverythingWithoutDates()
    }

    private fun initializeView() {

        populateViewIfEmpty()

        Hide.hide(datePicker)

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

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            datePicker.setOnDateChangedListener { calendarView1, year, month, dayOfMonth ->
                val mon = month + 1
                val date = "$year-$mon-$dayOfMonth"
                setDate(date)
                Hide.hide(calendarView1)
            }
        }

        fromDateCheckBox.setOnClickListener {checkBox->
            dateSetter = DateSetter.CHECKBOX_FROM
            Checkbox.connectCheckboxToView(checkBox, datePicker)
            Checkbox.connectCheckboxToView(checkBox, fromDateTextView)
        }

        toDateCheckBox.setOnClickListener { checkBox->
            dateSetter = DateSetter.CHECKBOX_TO
            Checkbox.connectCheckboxToView(checkBox, datePicker)
            Checkbox.connectCheckboxToView(checkBox, toDateTextView)
        }

        fromDateTextView.setOnClickListener {
            dateSetter = DateSetter.TEXT_VIEW_FROM
            Show.show(datePicker)
        }

        toDateTextView.setOnClickListener {
            dateSetter = DateSetter.TEXT_VIEW_TO
            Show.show(datePicker)
        }

        Hide.hide(sourceSpinner)
        Hide.hide(languageSpinner)
        Hide.hide(keyWordEditText)
        Hide.hide(ftbCancel)
        Hide.hide(datePicker)

    }

    private fun reset() {

        Hide.hide(horizontalOptions)
        Hide.hide(keyWordEditText)
        Hide.hide(sourceSpinner)
        Hide.hide(ftbCancel)
        Hide.hide(fromDateTextView)
        Hide.hide(toDateTextView)
        Hide.hide(languageSpinner)
        Hide.hide(datePicker)

        Checkbox.uncheck(checkBoxes)

        ftbAction.text = getString(R.string.options)
        ftbAction.setCompoundDrawables(iconMenu, null, null, null)

    }

    companion object {
        var dateSetter: DateSetter? = null
    }

    private fun setDate(date: String) {

        when (dateSetter) {
            DateSetter.CHECKBOX_FROM, DateSetter.TEXT_VIEW_FROM -> {
                fromDateTextView.text = date
                toast(context = context!!, message = "From $date")
            }
            DateSetter.CHECKBOX_TO, DateSetter.TEXT_VIEW_TO -> {
                toDateTextView.text = date
                toast(context = context!!, message = "To $date")
            }
            else -> {}
        }

    }

    enum class DateSetter {
        CHECKBOX_FROM,
        CHECKBOX_TO,
        TEXT_VIEW_FROM,
        TEXT_VIEW_TO
    }
}