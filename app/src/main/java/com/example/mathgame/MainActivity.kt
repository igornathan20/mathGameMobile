package com.example.mathgame

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    lateinit var input: EditText
    lateinit var num1: TextView
    lateinit var num2: TextView
    lateinit var sinalOperacao: TextView
    var correctAnswer: Double = 0.0
    var valor1: Double = 0.0
    var valor2: Double = 0.0
    var nota: Int = 0
    var rodadas: Int = 1
    lateinit var igual: TextView
    lateinit var botao: Button
    lateinit var resultado: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Inicializa as views
        input = findViewById(R.id.inputConta)
        num1 = findViewById(R.id.n1)
        num2 = findViewById(R.id.n2)
        sinalOperacao = findViewById(R.id.sinal)
        igual = findViewById(R.id.sinalIgual)
        botao = findViewById(R.id.calcularButton)
        resultado = findViewById(R.id.resultado)
        resultado.visibility = View.INVISIBLE

        // Gera os valores iniciais e configura o listener inicial
        gerarValor()
        botao.setOnClickListener { calcular(it) }
    }

    fun gerarValor() {
        val sinais = arrayOf("+", "-", "x", "/")
        val random1 = (0..99).random()
        val random2 = (0..99).random()
        val index = (0..3).random()
        sinalOperacao.text = sinais[index]
        num1.text = random1.toString()
        num2.text = random2.toString()
    }

    fun calcular(view: View) {
        valor1 = num1.text.toString().toDouble()
        valor2 = num2.text.toString().toDouble()
        val signal = sinalOperacao.text.toString()
        if(input.length() == 0 ) {    Toast.makeText(this, "Digite um número", Toast.LENGTH_SHORT).show() }
        else{
            correctAnswer = when (signal) {
                "+" -> valor1 + valor2
                "-" -> valor1 - valor2
                "x" -> valor1 * valor2
                "/" -> valor1 / valor2
                else -> 0.0
            }

            val inputKid = input.text.toString().toDoubleOrNull()
            if (inputKid == correctAnswer) {
                window.decorView.setBackgroundColor(Color.GREEN)
                resultado.visibility = View.INVISIBLE
                nota += 20
            } else {
                resultado.visibility = View.VISIBLE
                resultado.text = "O resultado é igual a " +correctAnswer.toString()
                window.decorView.setBackgroundColor(Color.RED)
            }

            botao.text = "Proximo"
            botao.setOnClickListener { proximo() }
        }

    }

    fun proximo() {
        window.decorView.setBackgroundColor(Color.WHITE)
        rodadas += 1
        if (rodadas > 5) {
            botao.text = "Finalizar"
            botao.setOnClickListener { finalizar() }

        } else {
            gerarValor()
            botao.text = "Calcular"
            botao.setOnClickListener { calcular(it) }
        }
        input.setText("")
    }

    fun finalizar(){
        window.decorView.setBackgroundColor(Color.WHITE)
        num1.visibility = TextView.GONE
        num2.text = "Nota"
        sinalOperacao.visibility = TextView.GONE
        botao.visibility = Button.GONE
        input.setText("$nota")
        resultado.visibility = View.GONE



    }
}
