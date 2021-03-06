package com.applications.whazzup.photomapp.ui.activities



import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.support.design.widget.AppBarLayout
import android.support.design.widget.BottomNavigationView
import android.support.design.widget.CoordinatorLayout
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.TypedValue
import android.view.*
import android.widget.*
import butterknife.BindView
import butterknife.ButterKnife
import com.applications.whazzup.photomapp.R
import com.applications.whazzup.photomapp.data.network.req.UserChangeInfoReq
import com.applications.whazzup.photomapp.data.network.req.UserLogInReq
import com.applications.whazzup.photomapp.data.network.req.UserSigInReq
import com.applications.whazzup.photomapp.di.DaggerScope
import com.applications.whazzup.photomapp.di.DaggerService
import com.applications.whazzup.photomapp.di.components.AppComponent
import com.applications.whazzup.photomapp.di.modules.PicassoCacheModule
import com.applications.whazzup.photomapp.di.modules.RootModule
import com.applications.whazzup.photomapp.flow.TreeKeyDispatcher
import com.applications.whazzup.photomapp.mvp.presenters.MenuItemHolder
import com.applications.whazzup.photomapp.mvp.presenters.RootPresenter
import com.applications.whazzup.photomapp.mvp.views.IActionBarView
import com.applications.whazzup.photomapp.mvp.views.IRootView
import com.applications.whazzup.photomapp.mvp.views.IView
import com.applications.whazzup.photomapp.ui.screens.photo_card_list.PhotoCardListScreen
import com.applications.whazzup.photomapp.ui.screens.splash.SplashScreen
import com.applications.whazzup.photomapp.ui.screens.upload_photo.UploadPhotoScreen
import com.applications.whazzup.photomapp.ui.screens.user_profile_auth.UserProfileAuthScreen
import com.applications.whazzup.photomapp.ui.screens.user_profile_idle.UserProfileIdleScreen
import com.applications.whazzup.photomapp.util.CustomTextWatcher
import com.squareup.picasso.Picasso
import flow.Direction
import flow.Flow
import flow.History
import mortar.MortarScope
import mortar.bundler.BundleServiceRunner
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import javax.inject.Inject

class RootActivity : AppCompatActivity(), IRootView, IActionBarView {



    @BindView(R.id.root_frame) lateinit var mRootFrame: FrameLayout
    @BindView(R.id.navigation) lateinit var mNavigation: BottomNavigationView
    @BindView(R.id.toolbar) lateinit var mToolbar: Toolbar
    @BindView(R.id.appbar_layout) lateinit var mAppBarLayout: AppBarLayout

    @Inject
    lateinit var mRootPresenter: RootPresenter

    var mProgressDialog: ProgressDialog? = null

    lateinit var mActionBar: android.support.v7.app.ActionBar
    lateinit var mActionBarMenuItem: MutableList<MenuItemHolder>
    lateinit var builder: AlertDialog
    lateinit var tabView : TabLayout

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
            R.id.navigation_home -> {
                Flow.get(this).setHistory(History.single(PhotoCardListScreen()), Direction.FORWARD)
                true
            }
            R.id.navigation_dashboard -> {
                if (mRootPresenter.isUserAuth()) {
                    Flow.get(this).setHistory(History.single(UserProfileAuthScreen()), Direction.FORWARD)
                } else {
                    Flow.get(this).setHistory(History.single(UserProfileIdleScreen()), Direction.FORWARD)
                }
                true
            }
            R.id.navigation_notifications -> {
                Flow.get(this).setHistory(History.single(UploadPhotoScreen()), Direction.FORWARD)
                true
            }
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
        setSupportActionBar(mToolbar)
        mActionBar = supportActionBar!!
    }

    override fun startActivityForResult(intent: Intent?, requestCode: Int) {

        super.startActivityForResult(intent, requestCode)
    }

    override fun createSignInAlertDialog() {
        builder = AlertDialog.Builder(this).create()

        val v: View = LayoutInflater.from(this).inflate(R.layout.sign_in, null)
        val btn: Button = v.findViewById(R.id.sign_btn) as Button
        val cancelBtn = v.findViewById(R.id.cancel_btn) as Button
        val nameEt: EditText = v.findViewById(R.id.name_et) as EditText
        val loginEt = v.findViewById(R.id.login_et) as EditText
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
            if ((loginErrorHint.text == "" && loginErrorHint.text == "" && nameErrorHint.text == "" && passwordErrorHint.text == "") &&
                    (!loginEt.text.isEmpty() && !nameEt.text.isEmpty() && !emailEt.text.isEmpty() && !passwordEt.text.isEmpty())) {
                var user = UserSigInReq(nameEt.text.toString(), loginEt.text.toString(), emailEt.text.toString(), passwordEt.text.toString())
                mRootPresenter.signUpUser(user)
            }
        }
        cancelBtn.setOnClickListener {
            builder?.cancel()
        }
        builder?.setView(v)
        builder?.show()
    }

    override fun createLoginDialog() {
        builder = AlertDialog.Builder(this).create()

        val v: View = LayoutInflater.from(this).inflate(R.layout.log_in, null)
        val btn: Button = v.findViewById(R.id.sign_btn) as Button
        val cancelBtn = v.findViewById(R.id.cancel_btn) as Button
        val emailEt = v.findViewById(R.id.email_et) as EditText
        val passwordEt = v.findViewById(R.id.password_et) as EditText
        val emailErrorHint = v.findViewById(R.id.email_error_hint) as TextView
        val passwordErrorHint = v.findViewById(R.id.password_error_hint) as TextView

        emailEt.addTextChangedListener(CustomTextWatcher(emailEt, emailErrorHint))
        passwordEt.addTextChangedListener(CustomTextWatcher(passwordEt, passwordErrorHint))

        btn.setOnClickListener {
            if ((emailErrorHint.text == "" && passwordErrorHint.text == "") &&
                    (!emailEt.text.isEmpty() && !passwordEt.text.isEmpty())) {
                mRootPresenter.logInUser(UserLogInReq(emailEt.text.toString(), passwordEt.text.toString()))
            }
        }
        cancelBtn.setOnClickListener {
            builder?.cancel()
        }
        builder?.setView(v)
        builder?.show()

    }

    override fun createChangeUserInfoDialog() {
        builder = AlertDialog.Builder(this).create()
        val v: View = LayoutInflater.from(this).inflate(R.layout.change_user_dialog, null)
        val btn: Button = v.findViewById(R.id.apply_btn) as Button
        val cancelBtn = v.findViewById(R.id.cancel_btn) as Button
        val nameEt: EditText = v.findViewById(R.id.name_et) as EditText
        val loginEt = v.findViewById(R.id.login_et) as EditText
        val loginErrorHint = v.findViewById(R.id.login_error_hint) as TextView
        val nameErrorHint = v.findViewById(R.id.name_error_hint) as TextView

        nameEt.addTextChangedListener(CustomTextWatcher(nameEt, nameErrorHint))
        loginEt.addTextChangedListener(CustomTextWatcher(loginEt, loginErrorHint))

        btn.setOnClickListener {
            if ((loginErrorHint.text == "" && nameErrorHint.text == "") &&
                    (!nameEt.text.isEmpty() && !loginEt.text.isEmpty())) {
                mRootPresenter.changeUserInfo(UserChangeInfoReq(nameEt.text.toString(), loginEt.text.toString(), mRootPresenter.mRootModel.getUserAvatar()))

            }
        }
        cancelBtn.setOnClickListener {
            builder?.cancel()
        }
        builder?.setView(v)
        builder?.show()

    }


    override fun hideAlertDialog() {
        builder.cancel()
    }

    override fun onDestroy() {
        super.onDestroy()
        mRootPresenter.dropView(this)
    }

    override fun onBackPressed() {
        if (!currentScreen?.viewOnBackPressed()!! && !Flow.get(this).goBack()) super.onBackPressed()
    }


    // region================IRootView==============
    override fun viewOnBackPressed(): Boolean {
        return false
    }

    override fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    override fun showError(e: Throwable) {

    }

    override fun startAct(imageIntent: Intent) {
        startActivity(imageIntent)
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        mRootPresenter.onActivityResult(requestCode, resultCode, data)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        mRootPresenter.onRequestPermissionResult(requestCode, permissions as Array<String>, grantResults)
    }

    override fun hideBottomNavigation(isVisible: Boolean) {
        if (isVisible) mNavigation.visibility = View.VISIBLE
        else mNavigation.visibility = View.GONE
    }

    override val currentScreen: IView?
        get() = mRootFrame.getChildAt(0) as IView

    override fun hideToolBar() {
        if (supportActionBar != null) {
            supportActionBar?.hide()
        }
        var layoutParams = mRootFrame.layoutParams as CoordinatorLayout.LayoutParams
        layoutParams.setMargins(0, 0, 0, 0)
        mRootFrame.layoutParams = layoutParams
        mRootFrame.fitsSystemWindows = true
    }

    override fun showtoolBar() {
        if (supportActionBar != null) {
            supportActionBar?.show()
        }
        var actionBarHeight = 0
        var tv = TypedValue()
        if (theme.resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
            actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, resources.displayMetrics)
        }
        var layoutParams = mRootFrame.layoutParams as CoordinatorLayout.LayoutParams
        layoutParams.setMargins(0, actionBarHeight, 0, 0)
        mRootFrame.layoutParams = layoutParams
    }

    // endregion

    // region================DI==============

    @dagger.Component(dependencies = arrayOf(AppComponent::class), modules = arrayOf(RootModule::class, PicassoCacheModule::class))
    @DaggerScope(RootActivity::class)
    interface RootComponent {
        fun inject(rootActivity: RootActivity)
        fun inject(rootPresenter: RootPresenter)
        val rootPresenter: RootPresenter
        val picasso: Picasso
    }

    // endregion

    // region===============ActionBarBuilder==================

    override fun setActionBarTitle(title: CharSequence?) {
        mActionBar.title = title
    }

    override fun setActionBarVisible(visible: Boolean) {
        if (visible) {
            supportActionBar?.show()
        } else {
            supportActionBar?.hide()
        }
    }

    override fun setBackArrow(enable: Boolean) {
        if (enable) {
            mActionBar.setDisplayHomeAsUpEnabled(true)
            mActionBar.setHomeAsUpIndicator(R.drawable.ic_custom_back_black_24dp)
        } else {
            mActionBar.setDisplayHomeAsUpEnabled(false)
        }
    }

    override fun setTabLayout(viewPager: ViewPager?) {
        if(mAppBarLayout.getChildAt(1)==null) {
        tabView = TabLayout(this) //создаём tab layout
        tabView.setupWithViewPager(viewPager)      //связываем его с ViewPager
        tabView.setBackgroundResource(R.color.white)
            mAppBarLayout.addView(tabView) //добавляем табы в appbar
            viewPager?.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabView))
        }
        viewPager?.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabView))
        //регистрируем обработчик переключения по табам для viewPager
    }

    override fun removeTabLayout() {
        val tabView = mAppBarLayout.getChildAt(1)
        if (tabView != null && tabView is TabLayout) { //проверяем есть ли у appBar дочерние View, являющиеся TabLayout
            mAppBarLayout.removeView(tabView)
        }
    }

    override fun setMenuItem(items: List<MenuItemHolder>) {
        mActionBarMenuItem = items as MutableList<MenuItemHolder>
        supportInvalidateOptionsMenu()
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        if (mActionBarMenuItem != null && !mActionBarMenuItem.isEmpty()) {
            for (menuItem in mActionBarMenuItem) {
                val item = menu?.add(menuItem.itemTitle)
                item?.setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_ALWAYS)
                        ?.setActionView(menuItem.iconRes)
                        ?.actionView
                        ?.setOnClickListener(menuItem.listener)
                //.setOnMenuItemClickListener(menuItem.getListener());
            }
        } else {
            menu?.clear()
        }

        return super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                Flow.get(this).goBack()
                return false
            }
            else -> return super.onOptionsItemSelected(item)
        }

        // endregion

    }
}
