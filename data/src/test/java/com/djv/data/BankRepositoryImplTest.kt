package com.djv.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.djv.data.bank1.Bank1AccountSource
import com.djv.data.bank2.Bank2AccountBalance
import com.djv.data.bank2.Bank2AccountSource
import com.djv.domain.bank.MockAccountId
import com.djv.domain.model.UserInfo
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.spyk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock

@ExperimentalCoroutinesApi
class BankRepositoryImplTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val dispatcher = TestCoroutineDispatcher()

    private val bank1AccountSource: Bank1AccountSource = mockk(relaxed = true)

    private val bank2AccountSource: Bank2AccountSource = mockk(relaxed = true)

    private val repository by lazy {
        spyk(
            BankRepositoryImpl(
                bank1AccountSource = bank1AccountSource,
                bank2AccountSource = bank2AccountSource
            )
        )
    }

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxed = true)
        Dispatchers.setMain(dispatcher)

        coEvery {
            bank1AccountSource.getAccountBalance(any())
        } returns 200.9

        coEvery {
            bank1AccountSource.getAccountCurrency(any())
        } returns "USD"

        coEvery {
            bank2AccountSource.getBalance(any())
        } returns Bank2AccountBalance(200.9, "USD")
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `GIVEN send account id WHEN get user bank 1 information THEN get with success`()  {
        dispatcher.runBlockingTest {
            //Given
            var userInfo: UserInfo? = null

            coEvery {
                bank1AccountSource.getTransactions(any(), any(), any())
            } returns listOf()

            //When
            repository.getUserInfo(
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

    @Test
    fun `GIVEN send account id WHEN get user bank 2 information THEN get with success`()  {
        dispatcher.runBlockingTest {
            //Given
            var userInfo: UserInfo? = null

            coEvery {
                bank1AccountSource.getTransactions(any(), any(), any())
            } returns listOf()

            //When
            repository.getUserInfo(
                MockAccountId.ACCOUNT_ID_BANK_1,
                MockAccountId.DATE_FROM,
                MockAccountId.DATE_TO,
                FAKE_BANK_2
            ).collect {
                userInfo = it
            }

            //Then
            assertEquals(FAKE_USER_INFO, userInfo)
        }
    }

    companion object {
        private const val FAKE_BANK = 1
        private const val FAKE_BANK_2 = 2
        private val FAKE_USER_INFO = UserInfo(
            balance = 200.9,
            currency = "USD",
            transactions = listOf()
        )
    }
}