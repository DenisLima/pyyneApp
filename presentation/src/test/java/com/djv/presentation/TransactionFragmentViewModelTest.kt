package com.djv.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.djv.domain.bank.BankUseCases
import com.djv.domain.model.UserInfo
import com.djv.domain.bank.MockAccountId
import com.djv.presentation.transactions.TransactionFragmentViewModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.spyk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class TransactionFragmentViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val dispatcher = TestCoroutineDispatcher()

    @MockK
    private lateinit var bankUseCases: BankUseCases

    private val viewModel by lazy {
        spyk(
            TransactionFragmentViewModel(
                bankUseCases = bankUseCases
            )
        )
    }

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxed = true)
        Dispatchers.setMain(dispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `GIVEN user need show the account information WHEN tapped in button bank THEN show information with success`() {
        dispatcher.runBlockingTest {
            coEvery {
                bankUseCases.getUserInfo(
                    MockAccountId.ACCOUNT_ID_BANK_1,
                    any(),
                    any(),
                    FAKE_BANK
                )
            } returns flowOf(FAKE_USER_INFO)

            //When
            viewModel.initEvent(
                TransactionFragmentViewModel.TransactionViewEvent.FetchTransactions(
                    bankId = FAKE_BANK
                )
            )

            //Then
            assertEquals(
                TransactionFragmentViewModel.TransactionViewState.UserInfo(FAKE_USER_INFO),
                viewModel.getViewState().value
            )
        }
    }

    companion object {
        private const val FAKE_BANK = 1
        private val FAKE_USER_INFO = UserInfo(
            balance = 200.9,
            currency = "USD",
            transactions = listOf()
        )
    }
}