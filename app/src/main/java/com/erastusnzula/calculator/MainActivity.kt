package com.erastusnzula.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.View
import android.widget.Button
import android.widget.TextView
import net.objecthunter.exp4j.ExpressionBuilder
import java.text.DecimalFormat

class MainActivity : AppCompatActivity() {
    private lateinit var inputView: TextView
    private lateinit var outputView: TextView
    private lateinit var equalsButton: Button
    private lateinit var clearButton:Button
    private lateinit var backSpaceButton:Button
    private var text = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        inputView = findViewById(R.id.inputView)
        outputView=findViewById(R.id.outputView)
        inputView.setHorizontallyScrolling(true)
        inputView.movementMethod=ScrollingMovementMethod()
        outputView.movementMethod=ScrollingMovementMethod()
        equalsButton=findViewById(R.id.equals)
        clearButton=findViewById(R.id.clear)
        backSpaceButton = findViewById(R.id.backSpace)
        equalsButton.setOnClickListener { equalsButtonClicked() }
        clearButton.setOnClickListener { clearButtonClicked() }
        backSpaceButton.setOnClickListener { backSpaceButtonClicked() }

    }
    fun numberClicked(view: View){
        if (view is Button){
            inputView.append(view.text)
            text=inputView.text.toString()
            showResults(text)
        }
    }



    fun operatorClicked(view: View){
        if (view is Button){
            inputView.append(view.text)
        }
    }

    private fun backSpaceButtonClicked() {
        val length = inputView.length()
        if (length> 0){
            inputView.text = inputView.text.subSequence(0, length-1)
            text = inputView.text.toString()
            showResults(text)
        }
    }

    private fun clearButtonClicked() {
        inputView.text = null
        outputView.text=null
    }

    private fun equalsButtonClicked() {
        text = inputView.text.toString()
        showResults(text)
        val getOutput = outputView.text.toString().replace(Regex(","),"")
        inputView.text = getOutput
        outputView.text = null
    }
    private fun showResults(text: String) {
        try{
            val insertPercent = text.replace(Regex("%"), "/100")
            val insertSquareRoot = insertPercent.replace(Regex("√"), "sqrt")
            val insertDivide = insertSquareRoot.replace(Regex("÷"), "/")
            val insertMultiply = insertDivide.replace(Regex("×"), "*")
            val expression = ExpressionBuilder(insertMultiply).build()
            val output= expression.evaluate()
            outputView.text = DecimalFormat("###,###.#########").format(output).toString()



        }catch (e:Exception){
            outputView.text=null
        }

    }
}