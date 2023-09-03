package nl.johnny.movemate.ui

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import nl.johnny.movemate.MoveMateApp
import nl.johnny.movemate.R
import nl.johnny.movemate.ui.components.MenuItem

open class MoveMateActivity : ComponentActivity() {

    protected lateinit var app: MoveMateApp
        private set

    val menuItems = listOf(
        MenuItem("Home", R.drawable.person_running_solid) {
            startActivity<MainActivity>()
        },
        MenuItem("Search", R.drawable.search){
            startActivity<SearchActivity>()
        },
        MenuItem("Logout", R.drawable.user_solid) {
            app.currentUser = null
            checkLogin()
        },
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        app = application as MoveMateApp
        checkLogin()

        onBackPressedDispatcher.addCallback(OnBackPress())
    }

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        app = application as MoveMateApp
        checkLogin()
    }

    private fun checkLogin() {
        if(app.currentUser == null && !isActivity<LoginActivity>()) {
            startActivity<LoginActivity>()
        } else if(app.currentUser != null && isActivity<LoginActivity>()) {
            startActivity<MainActivity>()
        }
    }

    protected inline fun <reified T: MoveMateActivity> isActivity() : Boolean {
        return this.javaClass.isAssignableFrom(T::class.java)
    }

    protected inline fun <reified T: MoveMateActivity> startActivity() {
        if(isActivity<T>()) return
        Intent(this, T::class.java).also {
            it.flags = Intent.FLAG_ACTIVITY_NO_ANIMATION or Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
            startActivity(it)
        }
    }

    inner class OnBackPress : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            startActivity<MainActivity>()
        }
    }
}