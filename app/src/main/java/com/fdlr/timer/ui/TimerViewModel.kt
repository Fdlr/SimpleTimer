package com.fdlr.timer.ui

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fdlr.timer.ui.ThemeBottomSheet.Companion.COLOR_BLUE
import com.fdlr.timer.ui.ThemeBottomSheet.Companion.COLOR_DEFAULT
import com.fdlr.timer.ui.ThemeBottomSheet.Companion.COLOR_GRAY
import com.fdlr.timer.ui.ThemeBottomSheet.Companion.COLOR_GREEN
import com.fdlr.timer.ui.ThemeBottomSheet.Companion.COLOR_RED
import com.fdlr.timer.model.TimerSet
import com.fdlr.timer.repository.Repository

class TimerViewModel(
    private val repository: Repository
) : ViewModel() {
    val stop = MutableLiveData<Boolean>()
    val onDismiss = MutableLiveData<Boolean>()
    val checkTimer = MediatorLiveData<Boolean>()
    val theme = MutableLiveData<String>()
    private val isEditing = MutableLiveData<Boolean>()

    init {
        checkTheme()
    }

    fun checkTheme() {
        val savedTheme = repository.getTheme()
        if (savedTheme != null) {
            theme.postValue(savedTheme)
        } else {
            theme.postValue(COLOR_DEFAULT)
        }
    }

    fun getSavedTheme(): String? {
        return repository.getTheme()
    }

    fun stopTimer(stop: Boolean) {
        this.stop.postValue(stop)
    }

    fun onDismissClick(dismiss: Boolean) {
        onDismiss.postValue(dismiss)
    }

    fun checkTime(minValue: Int, secondValue: Int) {
        if (minValue == 0 && secondValue == 0) {
            checkTimer.postValue(false)
        } else {
            checkTimer.postValue(true)
        }
    }

    fun saveTheme(theme: String) = repository.saveTheme(theme)

    fun setTheme(theme: String) {
        when (theme) {
            COLOR_DEFAULT -> this.theme.postValue(COLOR_DEFAULT)

            COLOR_GREEN -> this.theme.postValue(COLOR_GREEN)

            COLOR_BLUE -> this.theme.postValue(COLOR_BLUE)

            COLOR_RED -> this.theme.postValue(COLOR_RED)

            COLOR_GRAY -> this.theme.postValue(COLOR_GRAY)

        }
    }

    fun isEditing(isEditing: Boolean) {
        this.isEditing.postValue(isEditing)
    }

    fun isOnEditing() = this.isEditing.value

    fun savePreviewTimer(obj: TimerSet) {
        repository.saveTimer(obj)
    }

    fun getPreviewTimer() = repository.getTimer()

}