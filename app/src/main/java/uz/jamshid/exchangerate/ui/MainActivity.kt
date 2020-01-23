package uz.jamshid.exchangerate.ui

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import uz.jamshid.exchangerate.R
import uz.jamshid.exchangerate.core.base.BaseActivity
import uz.jamshid.exchangerate.core.extension.failure
import uz.jamshid.exchangerate.core.extension.observe
import uz.jamshid.exchangerate.core.extension.viewModel
import uz.jamshid.exchangerate.data.local.SharedPrefManager
import uz.jamshid.exchangerate.data.model.RatesResponse

class MainActivity : BaseActivity() {

    lateinit var viewModel: MainViewModel
    private val rates = listOf("CAD", "GBP", "RUB", "JPY", "EUR", "CNY", "USD", "AUD")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        SharedPrefManager.initContext(this)

        viewModel = viewModel(MainViewModel()) {
            observe(ratesLiveData, ::renderResult)
            failure(errorLiveData, ::renderError)
        }

        setUpUI()
        viewModel.loadFromCacheOrNet(
            inputSpinner.selectedItem.toString(),
            outputSpinner.selectedItem.toString()
        )

    }

    private fun renderResult(result: RatesResponse) {
        btnConvert.visibility = View.VISIBLE
        progressBar.visibility = View.GONE
        if (etInputRate.text.isNotEmpty())
            tvOutputRate.text =
                (etInputRate.text.toString().toFloat() * result.rates.rate).toString()
        else tvOutputRate.text = result.rates.rate.toString()
    }

    private fun renderError(error: String) {
        btnConvert.visibility = View.VISIBLE
        progressBar.visibility = View.GONE
        Toast.makeText(this, error, Toast.LENGTH_LONG).show()
    }

    fun convert(view: View) {
        if (etInputRate.text.isNotEmpty()) {
            view.visibility = View.GONE
            progressBar.visibility = View.VISIBLE
            viewModel.loadRates(
                inputSpinner.selectedItem.toString(),
                outputSpinner.selectedItem.toString()
            )
        } else
            Toast.makeText(this, "Please enter value to the input field", Toast.LENGTH_LONG).show()
    }

    private fun setUpUI() {
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, rates)
        inputSpinner.adapter = adapter
        outputSpinner.adapter = adapter
        var index = rates.indexOf(SharedPrefManager.readString(SharedPrefManager.INPUT_RATE))
        if (index != -1) {
            inputSpinner.setSelection(index)
        }
        index = rates.indexOf(SharedPrefManager.readString(SharedPrefManager.OUTPUT_RATE))
        if (index != -1)
            outputSpinner.setSelection(index)

    }

    override fun onPause() {
        SharedPrefManager.writePrefs(SharedPrefManager.INPUT_RATE, inputSpinner.selectedItem.toString())
        SharedPrefManager.writePrefs(SharedPrefManager.OUTPUT_RATE, outputSpinner.selectedItem.toString())
        super.onPause()
    }
    override fun layout(): Int {
        return R.layout.activity_main
    }


}
