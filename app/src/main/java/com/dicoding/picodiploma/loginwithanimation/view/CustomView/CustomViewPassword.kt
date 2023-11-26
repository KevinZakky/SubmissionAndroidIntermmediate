package com.dicoding.picodiploma.loginwithanimation.view.CustomView

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import com.dicoding.picodiploma.loginwithanimation.R

class CustomViewPassword: AppCompatEditText {
    constructor(context: Context) : super(context){
        init()
    }
    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet){
        init()
    }

    constructor(context: Context, attributeSet: AttributeSet, defStyle: Int) : super(context, attributeSet, defStyle){
        init()
    }

    private fun init(){
        addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                if(s.toString().length < 8){
                    setError("Password tidak boleh kurang dari 8 karakter", null)
                }else{
                    error = null
                }
            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })
    }
}