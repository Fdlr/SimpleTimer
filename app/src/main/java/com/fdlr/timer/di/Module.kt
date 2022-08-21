package com.fdlr.timer.di

import com.fdlr.timer.ui.TimerViewModel
import com.fdlr.timer.repository.Repository
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

var viewModelModule = module {
    viewModel { TimerViewModel(get()) }
}

val repositoryModule = module {
    factory { Repository() }
}





