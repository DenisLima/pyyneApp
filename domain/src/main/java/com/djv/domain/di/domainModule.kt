package com.djv.domain.di

import com.djv.domain.bank1.BankUseCases
import com.djv.domain.bank1.BankUseCasesImpl
import org.koin.dsl.module

val domainModule = module {

    factory<BankUseCases> {
        BankUseCasesImpl(get())
    }
}