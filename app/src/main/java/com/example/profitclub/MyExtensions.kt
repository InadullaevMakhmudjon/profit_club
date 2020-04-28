package com.example.profitclub

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.ConnectivityManager
import android.net.Uri
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.example.profitclub.utils.ImageFilePath
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.fragment_splash.*
import kotlinx.android.synthetic.main.main_custom_bar.*
import kotlin.system.exitProcess

fun log(message: String, TAG: String = "MyTagCheck") {
    Log.d(TAG, message)
}
fun log1(message: String, TAG: String = "NewTag") {
    Log.d(TAG, message)
}

fun Context.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Fragment.toast(message: String) {
    if(context != null)
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

fun Activity.preventShowingKeyboardOnStart(){
    this.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
}
/**
 * Try to hide the keyboard and returns whether it worked
 * https://stackoverflow.com/questions/1109022/close-hide-the-android-soft-keyboard
 */
fun View.hideKeyboard(): Boolean {
    try {
        val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        return inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
    } catch (ignored: RuntimeException) {
    }
    return false
}

/**
 * Extension method to provide hide keyboard for [Fragment].
 */
fun Fragment.hideSoftKeyboard() {
    activity?.hideSoftKeyboard()
}

/**
 * Extension method to provide hide keyboard for [Activity].
 */
fun Activity.hideSoftKeyboard() {
    if (currentFocus != null) {
        val inputMethodManager = getSystemService(
            Context
                .INPUT_METHOD_SERVICE
        ) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
    }
}

/**
 * Show the view  (visibility = View.VISIBLE)
 */
fun View.show(): View {
    if (visibility != View.VISIBLE) {
        visibility = View.VISIBLE
    }
    return this
}



/**
 *  Update Language
 */
val LANGUAGE_ENGLISH = "en"
val LANGUAGE_UZBEK = "uz"
val LANGUAGE_RUSSIAN = "ru"



fun Fragment.chooseLanguage() {
    val mBottomSheetDialog = BottomSheetDialog(activity!!)

    val sheetView = activity!!.layoutInflater.inflate(R.layout.bottom_sheet, null)
    mBottomSheetDialog.setContentView(sheetView)

    choose_language.setOnClickListener(View.OnClickListener { mBottomSheetDialog.show() })

    val english = sheetView.findViewById<RelativeLayout>(R.id.rl_english)
    val uzbek = sheetView.findViewById<RelativeLayout>(R.id.rl_uzbek)
    val russian = sheetView.findViewById<RelativeLayout>(R.id.rl_russian)

    english.setOnClickListener(View.OnClickListener {
        mBottomSheetDialog.hide()
        setNewLocale(LANGUAGE_ENGLISH, true, activity!!)
    })

    uzbek.setOnClickListener(View.OnClickListener {
        mBottomSheetDialog.hide()
        setNewLocale(LANGUAGE_UZBEK, true, activity!!)
    })

    russian.setOnClickListener(View.OnClickListener {
        mBottomSheetDialog.hide()
        setNewLocale(LANGUAGE_RUSSIAN, true, activity!!)
    })

}

fun Activity.chooseLanguage() {
    val mBottomSheetDialog = BottomSheetDialog(this)

    val sheetView = layoutInflater.inflate(R.layout.bottom_sheet, null)
    mBottomSheetDialog.setContentView(sheetView)

    choose_main_language.setOnClickListener(View.OnClickListener { mBottomSheetDialog.show() })

    val english = sheetView.findViewById<RelativeLayout>(R.id.rl_english)
    val uzbek = sheetView.findViewById<RelativeLayout>(R.id.rl_uzbek)
    val russian = sheetView.findViewById<RelativeLayout>(R.id.rl_russian)

    english.setOnClickListener(View.OnClickListener {
        mBottomSheetDialog.hide()
        setNewLocaleActivity(LANGUAGE_ENGLISH, true, this)
    })

    uzbek.setOnClickListener(View.OnClickListener {
        mBottomSheetDialog.hide()
        setNewLocaleActivity(LANGUAGE_UZBEK, true, this)
    })

    russian.setOnClickListener(View.OnClickListener {
        mBottomSheetDialog.hide()
        setNewLocaleActivity(LANGUAGE_RUSSIAN, true, this)
    })

}

fun Fragment.getLanguageDrawable(language: String): Drawable? {
    return when (language) {
        LANGUAGE_ENGLISH -> ContextCompat.getDrawable(activity!!,
            R.drawable.ic_united_kingdom
        )
        LANGUAGE_UZBEK -> ContextCompat.getDrawable(activity!!,
            R.drawable.ic_uzbekistan
        )
        LANGUAGE_RUSSIAN -> ContextCompat.getDrawable(activity!!,
            R.drawable.ic_russia
        )
        else -> {
            log("Unsupported language")
            null
        }
    }
}

fun Activity.getLanguageDrawable(language: String): Drawable? {
    return when (language) {
        LANGUAGE_ENGLISH -> ContextCompat.getDrawable(applicationContext!!,
            R.drawable.ic_united_kingdom
        )
        LANGUAGE_UZBEK -> ContextCompat.getDrawable(applicationContext!!,
            R.drawable.ic_uzbekistan
        )
        LANGUAGE_RUSSIAN -> ContextCompat.getDrawable(applicationContext!!,
            R.drawable.ic_russia
        )
        else -> {
            log("Unsupported language")
            null
        }
    }
}

fun Activity.getImagePath(uri: Uri): String? {
    return ImageFilePath.getPath(this, uri)
}

private fun setNewLocale(language: String, restartProcess: Boolean, activity: FragmentActivity): Boolean {
//    LocaleHelper.setLocale(activity.applicationContext, language)
    LocaleManager.setNewLocale(activity, language)
    activity.setLanguage(language)
    val i = Intent(activity, MainActivity::class.java)
    activity.startActivity(i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK))

    exitProcess(0)

    return true
}

private fun setNewLocaleActivity(language: String, restartProcess: Boolean, activity: Context): Boolean {
//    LocaleHelper.setLocale(activity.applicationContext, language)
    LocaleManager.setNewLocale(activity, language)
    activity.setLanguage(language)
    val i = Intent(activity, MainActivity::class.java)
    activity.startActivity(i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK))

    exitProcess(0)

    return true
}

fun Context.getLanguage(): String{
    val mPrefs = this.getSharedPreferences("profitclub", Context.MODE_PRIVATE)
    return mPrefs.getString("lang", LANGUAGE_RUSSIAN)!!
}

fun Context.setLanguage(language: String) {
    val mPrefs = this.getSharedPreferences("profitclub", Context.MODE_PRIVATE)
    mPrefs.edit().putString("lang", language).commit()
}


/**
 * Hide the view. (visibility = View.INVISIBLE)
 */
fun View.hide(): View {
    if (visibility != View.INVISIBLE) {
        visibility = View.INVISIBLE
    }
    return this
}

fun Context.isNetworkAvailable(): Boolean {
    val connectivityManager = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetworkInfo = connectivityManager.activeNetworkInfo
    return activeNetworkInfo != null && activeNetworkInfo.isConnected
}

fun Fragment.isNetworkAvailable(): Boolean {
    return activity!!.isNetworkAvailable()
}