package com.geekbrains.tests

import com.geekbrains.tests.presenter.details.DetailsPresenter
import com.geekbrains.tests.view.details.ViewDetailsContract
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

class DetailsPresenterTest {
    private lateinit var presenter: DetailsPresenter

    @Mock
    private lateinit var viewContract: ViewDetailsContract

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        presenter = DetailsPresenter(viewContract)
        presenter.onAttach(viewContract)
    }

    @Test
    fun onIncrement_Test() {
        val countIncrement = 1
        presenter.onIncrement()
        verify(viewContract, times(countIncrement)).setCount(countIncrement)
    }

    @Test
    fun onMultiIncrement_Test() {
        val countMultiIncrement = 5
        presenter.onIncrement()
        presenter.onIncrement()
        presenter.onIncrement()
        presenter.onIncrement()
        presenter.onIncrement()
        verify(viewContract, times(countMultiIncrement)).setCount(anyInt())
    }

    @Test
    fun onDecrement_Test() {
        val countDecrement = -1
        presenter.onDecrement()
        verify(viewContract, times(1)).setCount(countDecrement)
    }

    @Test
    fun onMultiDecrement_Test() {
        val countMultiDecrement = 5
        presenter.onDecrement()
        presenter.onDecrement()
        presenter.onDecrement()
        presenter.onDecrement()
        presenter.onDecrement()
        verify(viewContract, times(countMultiDecrement)).setCount(anyInt())
    }

    @Test
    fun setCounter_Test() {
        val counter = 5
        val targetPresenter = spy(presenter)
        `when`(targetPresenter.checkCount()).thenReturn(5)

        presenter.setCounter(counter)
        assertEquals(targetPresenter.checkCount(), counter)
    }

    @Test
    fun onAttach_Test() {
        presenter.onIncrement()
        presenter.onIncrement()
        presenter.onIncrement()
        presenter.onDecrement()
        presenter.onDecrement()
        verify(viewContract, times(5)).setCount(anyInt())
    }

    @Test
    fun onDetach_Test() {
        presenter.onDetach()
        presenter.onIncrement()
        presenter.onIncrement()
        presenter.onIncrement()
        presenter.onDecrement()
        presenter.onDecrement()
        verify(viewContract, times(0)).setCount(anyInt())
    }


}