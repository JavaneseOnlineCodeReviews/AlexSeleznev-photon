package com.applications.whazzup.photomapp.mvp.views

import android.content.Intent
import android.view.View

interface IRootView : IView {
    fun showMessage(message: String)
    fun showError(e: Throwable)

    fun showLoad()
    fun hideLoad()
    fun hideBottomNavigation(isVisible: Boolean)
    fun createSignInAlertDialog()
    fun createLoginDialog()
    fun showtoolBar()
    fun hideToolBar()
    fun hideAlertDialog()
    val currentScreen: IView?
}
