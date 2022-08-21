package com.fdlr.timer.repository

import com.fdlr.timer.model.TimerSet
import com.orhanobut.hawk.Hawk

class Repository {
    fun saveTheme(theme: String) {
        Hawk.put(THEME_USER, theme)
    }

    fun getTheme(): String? = Hawk.get(THEME_USER)

    fun saveTimer(obj: TimerSet) {
        Hawk.put(TIMER_SET, obj)
    }

    fun getTimer(): TimerSet = Hawk.get(TIMER_SET)


    companion object {
        const val THEME_USER = "THEME_USER"
        const val TIMER_SET = "TIMER_SET"
    }


}