package com.djv.domain.di

import com.djv.domain.bank.BankUseCases
import com.djv.domain.bank.BankUseCasesImpl
import org.koin.dsl.module

val domainModule = module {

    factory<BankUseCases> {
        BankUseCasesImpl(get())
    }
}