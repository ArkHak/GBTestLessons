package com.geekbrains.tests.presenter.search

import com.geekbrains.tests.presenter.PresenterContract
import com.geekbrains.tests.view.ViewContract

interface PresenterSearchContract : PresenterContract {
    fun searchGitHub(searchQuery: String)
    override fun onAttach(view: ViewContract)
    override fun onDetach()
}
