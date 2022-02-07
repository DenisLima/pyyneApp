package com.djv.domain

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.djv.domain.bank.BankRepository
import com.djv.domain.bank.BankUseCasesImpl
import com.djv.domain.bank.MockAccountId
import com.djv.domain.model.UserInfo
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.spyk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
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
import java.util.*

@ExperimentalCoroutinesApi
class BankUseCasesImplTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val dispatcher = TestCoroutineDispatcher()

    @MockK
    private lateinit var repository: BankRepository

    private val useCases by lazy {
        spyk(
            BankUseCasesImpl(
                bankRepository = repository
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
    fun `GIVEN send account id WHEN get user information THEN get with success`()  {
        dispatcher.runBlockingTest {
            //Given
            coEvery {
                repository.getUserInfo(
                    MockAccountId.ACCOUNT_ID_BANK_1,
                    any(),
                    any(),
                    FAKE_BANK
                )
            } returns flowOf(FAKE_USER_INFO)
            var userInfo: UserInfo? = null

            //When
            useCases.getUserInfo(
                MockAccountId.ACCOUNT_ID_BANK_1,
                MockAccountId.DATE_FROM,
                MockAccountId.DATE_TO,
                FAKE_BANK
            ).collect {
                userInfo = it
            }

            //Then
            assertEquals(FAKE_USER_INFO, userInfo)
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