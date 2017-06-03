package com.applications.whazzup.photomapp.ui.activities

import android.app.ProgressDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.WindowManager
import android.widget.FrameLayout
import butterknife.BindView
import butterknife.ButterKnife
import com.applications.whazzup.photomapp.R
import com.applications.whazzup.photomapp.di.DaggerService
import com.applications.whazzup.photomapp.di.components.AppComponent
import com.applications.whazzup.photomapp.di.modules.PicassoCacheModule
import com.applications.whazzup.photomapp.di.modules.RootModule
import com.applications.whazzup.photomapp.di.scopes.RootScope
import com.applications.whazzup.photomapp.flow.TreeKeyDispatcher
import com.applications.whazzup.photomapp.mvp.presenters.RootPresenter
import com.applications.whazzup.photomapp.mvp.views.IRootView
import com.applications.whazzup.photomapp.mvp.views.IView
import com.applications.whazzup.photomapp.ui.screens.photo_card_list.PhotoCardListAdapter
import com.applications.whazzup.photomapp.ui.screens.splash.SplashScreen
import com.squareup.picasso.Picasso
import flow.Flow
import mortar.MortarScope
import mortar.bundler.BundleServiceRunner
import javax.inject.Inject

class RootActivity : AppCompatActivity(), IRootView {

    @BindView(R.id.root_frame) lateinit var mRootFrame: FrameLayout
    @BindView(R.id.navigation) lateinit var mNavigation: BottomNavigationView

    @Inject
    lateinit var mRootPresenter: RootPresenter

    var mProgressDialog: ProgressDialog? = null


    override fun attachBaseContext(newBase: Context) {
        var newBase = newBase
        newBase = Flow.configure(newBase, this)
                .defaultKey(SplashScreen())
                .dispatcher(TreeKeyDispatcher(this))
                .install()
        super.attachBaseContext(newBase)
    }

    override fun getSystemService(name: String): Any {
        val rootActivityScope = MortarScope.findChild(applicationContext, RootActivity::class.java.name)
        return if (rootActivityScope.hasService(name)) rootActivityScope.getService<Any>(name) else super.getSystemService(name)
    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        BundleServiceRunner.getBundleServiceRunner(this).onSaveInstanceState(outState)
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> return@OnNavigationItemSelectedListener true
            R.id.navigation_dashboard -> return@OnNavigationItemSelectedListener true
            R.id.navigation_notifications -> return@OnNavigationItemSelectedListener true
            else -> return@OnNavigationItemSelectedListener false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_root)
        ButterKnife.bind(this)
        mNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        BundleServiceRunner.getBundleServiceRunner(this).onCreate(savedInstanceState)
        (DaggerService.getDaggerComponent<Any>(this) as RootComponent).inject(this)
        mRootPresenter.takeView(this)
    }


    override fun onDestroy() {
        super.onDestroy()
        mRootPresenter.dropView(this)
    }

    // region================IRootView==============
    override fun viewOnBackPressed(): Boolean {
        return false
    }

    override fun showMessage(message: String) {

    }

    override fun showError(e: Throwable) {

    }

    override fun showLoad() {
        mProgressDialog = ProgressDialog(this)
        mProgressDialog!!.setCancelable(false)
        mProgressDialog!!.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        mProgressDialog!!.show()
        mProgressDialog!!.window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
        mProgressDialog!!.setContentView(R.layout.progress_dialog)
    }

    override fun hideLoad() {
        if (mProgressDialog!!.isShowing) {
            mProgressDialog!!.hide()
        }
    }

    override fun hideBottomNavigation(isVisible: Boolean) {
        if(isVisible) mNavigation.visibility = View.VISIBLE
        else mNavigation.visibility = View.GONE
    }

    override val currentScreen: IView?
        get() = mRootFrame.getChildAt(0) as IView
    // endregion

    // region================DI==============

    @dagger.Component(dependencies = arrayOf(AppComponent::class), modules = arrayOf(RootModule::class, PicassoCacheModule::class))
    @RootScope
    interface RootComponent {
        fun inject(rootActivity: RootActivity)
        fun inject(rootPresenter: RootPresenter)
        val rootPresenter: RootPresenter
        val picasso: Picasso
    }

    // endregion

}
