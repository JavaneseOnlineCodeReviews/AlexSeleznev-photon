package com.applications.whazzup.photomapp.mvp.views

import android.content.Intent
import android.net.Uri
import android.view.View
import android.widget.ImageView

interface IRootView : IView {
    fun showMessage(message: String)
    fun showError(e: Throwable)

    fun showLoad()
    fun hideLoad()
    fun hideBottomNavigation(isVisible: Boolean)
    fun createSignInAlertDialog()
    fun createLoginDialog()
    fun createChangeUserInfoDialog()
    fun showtoolBar()
    fun hideToolBar()
    fun hideAlertDialog()
    val currentScreen: IView?
    fun  startAct(imageIntent: Intent)
}
