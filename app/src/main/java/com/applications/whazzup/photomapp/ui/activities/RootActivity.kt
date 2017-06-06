package com.applications.whazzup.photomapp.ui.activities


import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.design.widget.TextInputLayout

import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.*
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
import com.applications.whazzup.photomapp.ui.screens.splash.SplashScreen
import com.applications.whazzup.photomapp.util.CustomTextWatcher
import com.squareup.picasso.Picasso
import flow.Flow
import kotlinx.android.synthetic.main.sign_in.*
import mortar.MortarScope
import mortar.bundler.BundleServiceRunner
import javax.inject.Inject

class RootActivity : AppCompatActivity(), IRootView {

    @BindView(R.id.root_frame) lateinit var mRootFrame: FrameLayout
    @BindView(R.id.navigation) lateinit var mNavigation: BottomNavigationView



    @Inject
    lateinit var mRootPresenter: RootPresenter

    var mProgressDialog: ProgressDialog? = null

    var builder: AlertDialog?= null

    override fun attachBaseContext(newBase: Context) {
        var newBase = Flow.configure(newBase, this)
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
        createSignInAlertDialog()
    }

   override fun createSignInAlertDialog(){
        builder = AlertDialog.Builder(this).create()

        val v: View=LayoutInflater.from(this).inflate(R.layout.sign_in, null)
        val btn : Button =v.findViewById(R.id.sign_btn) as Button
        val cancelBtn = v.findViewById(R.id.cancel_btn) as Button
        val nameEt : EditText = v.findViewById(R.id.name_et) as EditText
        val loginEt =v.findViewById(R.id.login_et) as EditText
        val emailEt = v.findViewById(R.id.email_et) as EditText
        val passwordEt = v.findViewById(R.id.password_et) as EditText
        val loginErrorHint = v.findViewById(R.id.login_error_hint) as TextView
        val emailErrorHint = v.findViewById(R.id.email_error_hint) as TextView
        val nameErrorHint = v.findViewById(R.id.name_error_hint) as TextView
        val passwordErrorHint = v.findViewById(R.id.password_error_hint) as TextView

        nameEt.addTextChangedListener(CustomTextWatcher(nameEt, nameErrorHint))
        loginEt.addTextChangedListener(CustomTextWatcher(loginEt, loginErrorHint))
        emailEt.addTextChangedListener(CustomTextWatcher(emailEt, emailErrorHint))
        passwordEt.addTextChangedListener(CustomTextWatcher(passwordEt, passwordErrorHint))

        btn.setOnClickListener {
            if((loginErrorHint.text == "" && loginErrorHint.text == "" && nameErrorHint.text == "" && passwordErrorHint.text == "")&&
                    (!loginEt.text.isEmpty()&&!nameEt.text.isEmpty()&&!emailEt.text.isEmpty()&&!passwordEt.text.isEmpty())) {
                Toast.makeText(this, "SignIn", Toast.LENGTH_LONG).show()
                builder?.cancel()
            }
        }
        cancelBtn.setOnClickListener {
            builder?.cancel()
        }
        builder?.setView(v)
        builder?.show()
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
