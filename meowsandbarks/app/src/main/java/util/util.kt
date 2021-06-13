package util

import android.util.Log
import android.view.View
import android.widget.EditText
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.MutableLiveData

fun <T> MutableLiveData<T>.notifyObserver() {
    this.value = this.value
}

fun EditText.setupValueSetterInForm(errorMessage: String, setFieldFn: (String) -> Boolean) {
    this.addTextChangedListener {
        val text = it?.trim()
        if (text === null || !setFieldFn(text.toString())) {
            this.error = errorMessage
        }
        else {
            this.error = null
        }
    }
}