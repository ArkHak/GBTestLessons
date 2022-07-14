package com.geekbrains.tests.presenter.details

import com.geekbrains.tests.presenter.PresenterContract
import com.geekbrains.tests.view.ViewContract

internal interface PresenterDetailsContract : PresenterContract {
    fun setCounter(count: Int)
    fun onIncrement()
    fun onDecrement()
    override fun onAttach(view: ViewContract)
    override fun onDetach()
}
