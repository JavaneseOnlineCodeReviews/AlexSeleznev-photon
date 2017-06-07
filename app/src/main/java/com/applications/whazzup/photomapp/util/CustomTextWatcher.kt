package com.applications.whazzup.photomapp.util

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.TextView
import com.applications.whazzup.photomapp.R
import com.applications.whazzup.photomapp.R.id.*
import java.util.regex.Pattern

open class CustomTextWatcher (view : View, hintView : View) : TextWatcher {

    var view = view as EditText
    var hintView = hintView as TextView

    override fun afterTextChanged(s: Editable?) {
        when(view.id){
            name_et->{
                if(s?.length!!<3){
                    view.setBackgroundResource(R.drawable.edit_text_error_style)
                    hintView.text = "Длина имени должна быть не менее 3-х символов"
                }else{
                    view.setBackgroundResource(R.drawable.edit_text_style)
                    hintView.text = ""
                }
            }
            login_et->{
                if(!isValidateLogin(s!!)){
                    view.setBackgroundResource(R.drawable.edit_text_error_style)
                    hintView.text = "Только латиница и нижние подчеркивания, запрещены спецсимволы ((!@#$%^&*()\\|=+-)"
                }else{
                    view.setBackgroundResource(R.drawable.edit_text_style)
                    hintView.text = ""
                }
            }
            email_et->{
                if(!isValidateEmail(s!!)){
                    view.setBackgroundResource(R.drawable.edit_text_error_style)
                    hintView.text = "any@any.ru"
                }else{
                    view.setBackgroundResource(R.drawable.edit_text_style)
                    hintView.text = ""
                }
            }
            password_et-> {
                if (!isValidatePassword(s!!)) {
                    view.setBackgroundResource(R.drawable.edit_text_error_style)
                    hintView.text = "Введите пароль, минимум 8 символов, используя только латиницу."
                } else {
                    view.setBackgroundResource(R.drawable.edit_text_style)
                    hintView.text = ""
                }
            }
        }
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

    fun isValidateEmail(s: Editable): Boolean {
        val pattern = Pattern.compile("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])")
        val matcher = pattern.matcher(s)
        return matcher.matches()
    }

    fun isValidateLogin(s : Editable) : Boolean{
        val pattern = Pattern.compile("([a-zA-z0-9\\d\\u005f]){3,}")
        val matcher = pattern.matcher(s)
        return matcher.matches()
    }
    fun isValidatePassword(s : Editable): Boolean {
        val pattern = Pattern.compile("([a-zA-z0-9\\d]){8,}")
        val matcher = pattern.matcher(s)
        return matcher.matches()
    }
}