package uz.jamshid.exchangerate.core.extension

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import uz.jamshid.exchangerate.core.factory.ViewModelFactory


inline fun <reified T : ViewModel> AppCompatActivity.viewModel(creator: T, body: T.() -> Unit): T {
    return ViewModelProvider(this, ViewModelFactory(creator))[T::class.java].apply { body(this) }
}