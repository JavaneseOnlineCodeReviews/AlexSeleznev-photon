package com.applications.whazzup.photomapp.mvp.views

interface IRootView : IView {
    fun showMessage(message: String)
    fun showError(e: Throwable)

    fun showLoad()
    fun hideLoad()
    fun hideBottomNavigation(isVisible: Boolean)

    val currentScreen: IView?
}
