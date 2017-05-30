package com.applications.whazzup.photomapp.ui.activities

import android.content.Context
import android.os.Bundle
import android.support.annotation.StringDef
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.widget.FrameLayout
import android.widget.TextView

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
import com.applications.whazzup.photomapp.ui.screens.photo_card_list.PhotoCardListScreen
import com.squareup.picasso.Picasso

import javax.inject.Inject

import butterknife.BindView
import butterknife.ButterKnife
import flow.Flow
import mortar.MortarScope
import mortar.bundler.BundleServiceRunner

class RootActivity : AppCompatActivity(), IRootView {

    @BindView(R.id.root_frame) internal var mRootFrame: FrameLayout? = null
    @BindView(R.id.navigation) internal var mNavigation: BottomNavigationView? = null

    @Inject
    internal var mRootPresenter: RootPresenter? = null


    override fun attachBaseContext(newBase: Context) {
        var newBase = newBase
        newBase = Flow.configure(newBase, this)
                .defaultKey(PhotoCardListScreen())
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
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_root)
        ButterKnife.bind(this)
        mNavigation!!.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        BundleServiceRunner.getBundleServiceRunner(this).onCreate(savedInstanceState)
        (DaggerService.getDaggerComponent<Any>(this) as RootComponent).inject(this)
        mRootPresenter!!.takeView(this)
    }


    override fun onDestroy() {
        super.onDestroy()
        mRootPresenter!!.dropView(this)
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

    }

    override fun hideLoad() {

    }

    override val currentScreen: IView?
        get() = mRootFrame!!.getChildAt(0) as IView
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
