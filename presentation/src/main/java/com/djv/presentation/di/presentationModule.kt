package com.djv.presentation.di

import com.djv.presentation.transactions.TransactionFragmentViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {

    viewModel {
        TransactionFragmentViewModel(get())
    }
}