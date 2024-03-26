package com.example.calculatorfinal

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    private var tvInput : TextView? = null
    private var lastNumeric : Boolean = false
    private var lastDot : Boolean = false
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        tvInput = findViewById(R.id.tv_display)
    }
    fun onDigit(view : View){
       // Toast.makeText(this , "You clicked" , Toast.LENGTH_LONG).show()
        tvInput?.append((view as Button).text) // as we know that button has a text property view does not have so will do view as button typecasting
        // now when we click on something then the text of the button will append behind your textView
        lastNumeric = true
        lastDot = false
    }
    fun onClear (view: View){
        tvInput?.text = ""
    }
    fun onDecimalPoint(view : View) {
      if (lastNumeric && !lastDot){
          lastDot = true
          lastNumeric = false
          tvInput?.append(".")
      }
    }
    fun onEqual(view : View){
        if (lastNumeric){
            var tvVal = tvInput?.text.toString()
            var prefix = ""
            try {
                if ( tvVal.startsWith("-")){    // This if statement is only for dealing with the negative values otherwise we will only check that if
                                       // any operator is present if present then we will split and calculate the result
                    prefix = "-"
                    tvVal = tvVal.substring(1)
                }
                if (tvVal.contains("-")){
                    val splitValue = tvVal.split("-")
                    var one = splitValue[0]
                    var two = splitValue[1]
                    if (prefix.isNotEmpty()){
                        one = prefix + one
                    }
                    tvInput?.text = removeZeroAfterDot((one.toDouble() - two.toDouble()).toString())
                }
                else if (tvVal.contains("+")){
                    val splitValue = tvVal.split("+")
                    var one = splitValue[0]
                    var two = splitValue[1]
                    if (prefix.isNotEmpty()){
                        one = prefix + one
                    }
                    tvInput?.text = removeZeroAfterDot((one.toDouble() + two.toDouble()).toString())
                }
                else if (tvVal.contains("*")){
                    val splitValue = tvVal.split("*")
                    var one = splitValue[0]
                    var two = splitValue[1]
                    if (prefix.isNotEmpty()){
                        one = prefix + one
                    }
                    tvInput?.text = removeZeroAfterDot((one.toDouble()  * two.toDouble()).toString())
                }
                else if (tvVal.contains("/")){
                    val splitValue = tvVal.split("/")
                    var one = splitValue[0]
                    var two = splitValue[1]
                    if (prefix.isNotEmpty()){
                        one = prefix + one
                    }
                    tvInput?.text = removeZeroAfterDot((one.toDouble() / two.toDouble()).toString())
                }
            }catch ( e : Exception){
                e.printStackTrace()
            }
        }
    }
    fun onOperator(view : View){
        tvInput?.text.let {// it can also be tvInput?.text? as this is also called nullable stream
            if (lastNumeric && !isOperatorAdded(it.toString())){ // we are checking that if the operator is already present or not if it is already then we wil not append the operator
                tvInput?.append((view as Button).text)
                lastNumeric = false
                lastDot = false
            }
        }
    }
    private fun isOperatorAdded(value : String) : Boolean{
        return if (value.startsWith("-")){ // as we need to deal with the negative values so if at the start -ve is added then we will return false so that we can add one more -ve or positive sign
            false
        }
            else{
                value.contains("/")||
                        value.contains("+")||
                        value.contains("X")||
                        value.contains("-")
        }
    }
    private fun removeZeroAfterDot(result : String ) : String {
        var value = result
        if (result.contains(".0"))value =result.substring(0 , result.length - 2 )
        return value
    }
}