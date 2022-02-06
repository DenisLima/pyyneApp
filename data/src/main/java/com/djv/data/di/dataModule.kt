package com.djv.data.di

import com.djv.data.bank1.Bank1AccountSource
import com.djv.data.BankRepositoryImpl
import com.djv.data.bank2.Bank2AccountSource
import com.djv.domain.bank1.BankRepository
import org.koin.dsl.module

val dataModule = module {

    factory {
        Bank1AccountSource()
    }

    factory {
        Bank2AccountSource()
    }

    single<BankRepository> {
        BankRepositoryImpl(get(), get())
    }
}