package com.example.news.views.fragments.everything

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.news.NewsApp
import com.example.news.R
import com.example.news.databinding.EverythingBinding
import com.example.news.utils.*
import com.example.news.utils.PopulateSpinner.populateSpinner
import com.example.news.views.fragments.everything.viewModels.EverythingViewModel
import com.example.news.views.fragments.everything.viewModels.EverythingViewModelFactory
import kotlinx.android.synthetic.main.options.view.*
import javax.inject.Inject


/**
Created by ian
 */

class Everything : Fragment() {/*

    private lateinit var binding: EverythingBinding
    private lateinit var fragContext: Context
    private lateinit var categorySpinner: Spinner
    private lateinit var countrySpinner: Spinner
    private lateinit var sourceSpinner: Spinner
    private lateinit var categoryCheckBox: CheckBox
    private lateinit var countryCheckBox: CheckBox
    private lateinit var sourceCheckBox: CheckBox
    private lateinit var keyWordCheckBox: CheckBox
    private lateinit var keyWordEditText: EditText
    private lateinit var checkBoxes: ArrayList<CheckBox>
    private lateinit var ftbAction: Button
    private lateinit var ftbCancel: Button
    private lateinit var horizontalOptions: LinearLayout
    private lateinit var verticalOptions: LinearLayout
    private lateinit var iconCancel: Drawable
    private lateinit var iconMenu: Drawable
    private lateinit var iconSearch: Drawable
    private var combinedQuery: String = ""
    private var categoryQuery: String? = null
    private var countryQuery: String? = null
    private var sourceQuery: String? = null
    private lateinit var everythingViewModel: EverythingViewModel
    @Inject
    lateinit var everythingViewModelFactory: EverythingViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (context!!.applicationContext as NewsApp).applicationComponent.inject(this)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_everything, container, false)
        fragContext = binding.root.context
        everythingViewModel = ViewModelProviders.of(this, everythingViewModelFactory).get(EverythingViewModel::class.java)
        everythingViewModel.getGeneralResponse().observe(viewLifecycleOwner, Observer {

        })

        categorySpinner = binding.includedOptions.spinner_category
        countrySpinner = binding.includedOptions.spinner_country
        sourceSpinner = binding.includedOptions.spinner_source

        populateSpinner(categorySpinner, fragContext, R.array.categories, textViewResource = R.layout.spinner_text)
        populateSpinner(countrySpinner, fragContext, R.array.countries, textViewResource = R.layout.spinner_text)
        checkBoxes = ArrayList()
        countryCheckBox = binding.includedOptions.checkBox_country
        categoryCheckBox = binding.includedOptions.checkBox_category
        sourceCheckBox = binding.includedOptions.checkBox_source
        keyWordCheckBox = binding.includedOptions.checkBox_keyword

        keyWordEditText = binding.includedOptions.editText_keyword

        ftbAction = binding.includedOptions.action_button
        ftbCancel = binding.includedOptions.cancel_button

        horizontalOptions = binding.includedOptions.options_horizontal
        verticalOptions = binding.includedOptions.options_vertical

        checkBoxes.add(countryCheckBox)
        checkBoxes.add(categoryCheckBox)
        checkBoxes.add(sourceCheckBox)
        checkBoxes.add(keyWordCheckBox)

        iconCancel = fragContext.resources.getDrawable(R.drawable.ic_cancel_white_24dp)
        iconCancel.setBounds(0, 0, 60, 60)
        iconMenu = fragContext.resources.getDrawable(R.drawable.ic_menu_white)
        iconMenu.setBounds(0, 0, 60, 60)
        iconSearch = fragContext.resources.getDrawable(R.drawable.ic_search_white_24dp)
        iconSearch.setBounds(0, 0, 60, 60)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initializeView()
    }

    private fun toggleOptions() {
        when (horizontalOptions.visibility) {
            View.VISIBLE -> {
//                snackBar(view!!, "Snack", "Undo", SnackBarAction)
                var selectedCountry: String? = null
                var selectedCategory: String? = null
                var selectedSource: String? = null
                combinedQuery = ""

                if (countryCheckBox.isChecked && countrySpinner.selectedItem != null && countrySpinner.selectedItem.toString() != getString(
                        R.string.spinner_prompt
                    )
                ) {
                    selectedCountry = countrySpinner.selectedItem.toString()
                    countryQuery = "&country=$selectedCountry"
                    combinedQuery += countryQuery
                } else countryQuery = null

                if (categoryCheckBox.isChecked && categorySpinner.selectedItem != null && categorySpinner.selectedItem.toString() != getString(
                        R.string.spinner_prompt
                    )
                ) {
                    selectedCategory = categorySpinner.selectedItem.toString()
                    categoryQuery = "&category=$selectedCategory"
                    combinedQuery += categoryQuery
                } else categoryQuery = null

                if (sourceCheckBox.isChecked && sourceSpinner.selectedItem != null && sourceSpinner.selectedItem.toString() != getString(
                        R.string.spinner_prompt
                    )
                ) {
                    selectedSource = sourceSpinner.selectedItem.toString()
                    sourceQuery = "&source=$selectedSource"
                    combinedQuery += sourceQuery
                } else sourceQuery = null

                if (combinedQuery != "") Notify.toast(fragContext, "Fetching news")

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

        ftbCancel.setOnClickListener {
            reset()
        }

        ftbAction.setOnClickListener {
            toggleOptions()
        }

        fun hideVerticalOptions () {
            if (!countryCheckBox.isChecked && !categoryCheckBox.isChecked && !sourceCheckBox.isChecked) {
                Hide.hide(verticalOptions)
            }
        }

        countryCheckBox.setOnClickListener { checkbox ->
            hideVerticalOptions()
            Checkbox.connectCheckboxToView(checkbox, countrySpinner, verticalOptions)
        }
        categoryCheckBox.setOnClickListener { checkbox ->
            hideVerticalOptions()
            Checkbox.connectCheckboxToView(checkbox, categorySpinner, verticalOptions)
        }
        sourceCheckBox.setOnClickListener { checkbox ->
            hideVerticalOptions()
            Checkbox.connectCheckboxToView(checkbox, sourceSpinner, verticalOptions)
        }
        keyWordCheckBox.setOnClickListener { checkBox ->
            checkBox as CheckBox
            if (checkBox.isChecked) {
                Show.show(keyWordEditText)
            } else {
                Hide.hide(keyWordEditText)
            }
        }

        Hide.hide(countrySpinner)
        Hide.hide(categorySpinner)
        Hide.hide(sourceSpinner)
        Hide.hide(keyWordEditText)
        Hide.hide(ftbCancel)

    }

    private fun reset() {

        Hide.hide(horizontalOptions)
        Hide.hide(keyWordEditText)
        Hide.hide(sourceSpinner)
        Hide.hide(categorySpinner)
        Hide.hide(countrySpinner)
        Hide.hide(ftbCancel)

        Checkbox.uncheck(checkBoxes)

        ftbAction.text = getString(R.string.options)
        ftbAction.setCompoundDrawables(iconMenu, null, null, null)

    }*/
}