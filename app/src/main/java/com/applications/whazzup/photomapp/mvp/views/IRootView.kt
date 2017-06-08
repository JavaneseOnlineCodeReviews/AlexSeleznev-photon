package com.applications.whazzup.photomapp.mvp.views

interface IRootView : IView {
    fun showMessage(message: String)
    fun showError(e: Throwable)

    fun showLoad()
    fun hideLoad()
    fun hideBottomNavigation(isVisible: Boolean)
    fun createSignInAlertDialog()
    fun showtoolBar()
    fun hideToolBar()
    fun hideAlertDialog()
    val currentScreen: IView?
}
