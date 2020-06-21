package com.programiqsolutions.news.utils

import android.content.Context
import android.widget.ArrayAdapter
import android.widget.Spinner

object PopulateSpinner {

    fun populateSpinner(spinner: Spinner, context: Context, arrayResource: Int, textViewResource: Int = android.R.layout.simple_spinner_item, dropdownResource: Int = android.R.layout.simple_spinner_dropdown_item) {
        ArrayAdapter.createFromResource(context, arrayResource, textViewResource)
            .also { arrayAdapter ->
                arrayAdapter.setDropDownViewResource(dropdownResource)
                spinner.adapter = arrayAdapter
            }
    }

    fun populateSpinner(spinner: Spinner, context: Context, arrayList: List<String>, textViewResource: Int = android.R.layout.simple_spinner_item, dropdownResource: Int = android.R.layout.simple_spinner_dropdown_item) {
        ArrayAdapter(context, textViewResource, arrayList).also { arrayAdapter->
            arrayAdapter.setDropDownViewResource(dropdownResource)
            spinner.adapter = arrayAdapter
        }
    }

}