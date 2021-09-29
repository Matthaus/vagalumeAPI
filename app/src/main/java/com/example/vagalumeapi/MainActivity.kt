package com.example.vagalumeapi

//oq melhorar no app:
//mudar a cor do cursor
//aumentar fonte do editText
//no final da pagina criar um link para direcionar ao vagalume
//arrumar resourses das cores

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputLayout

class MainActivity : AppCompatActivity() {

    private val mainViewModel by viewModels<MainViewModel>()

    private var inputNomeCantor : TextInputLayout? = null
    private var inputNomeMusica : TextInputLayout? = null
    var barraProgresso : ProgressBar? = null
    var letraMusica : TextView? = null
    var menssagemErro : TextView? = null
    var btnPesquisarMusica : Button? = null

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //setTitle("Find your song") ou apenas
        title = "Find your song"

        bindViews()
        setObservers()
        btnPesquisarMusica?.setOnClickListener {
            mainViewModel.buscarLetraMusica(pegarEntradaUsuario().first, pegarEntradaUsuario().second)
            closeKeyboard()
            esconderBarraProgresso()
        }
    }

    fun bindViews() {
        letraMusica = findViewById(R.id.letra_musica)
        menssagemErro = findViewById(R.id.menssagem_erro)
        barraProgresso = findViewById(R.id.barra_progresso)
        inputNomeCantor = findViewById(R.id.input_nome_cantor)
        inputNomeMusica = findViewById(R.id.input_nome_musica)
        btnPesquisarMusica = findViewById(R.id.button_pesquisar_musica)
    }

    private fun setObservers() {
        mainViewModel.letraMusica.observe(this, {
            exibirLetraMusica(it)
        })

        mainViewModel.aconteceuErro.observe(this, {
            if (it) {
                exibirMensagemErro()
            }
        })

        mainViewModel.exibirBarraProgresso.observe(this, {
            if (it) {
                exibirBarraProgresso()
            } else {
                esconderBarraProgresso()
            }
        })
    }

    private fun pegarEntradaUsuario(): Pair<String, String> {
        val nomeCantor = inputNomeCantor?.editText?.text.toString()
        val nomeMusica = inputNomeMusica?.editText?.text.toString()
        return nomeCantor to nomeMusica
    }

    private fun exibirLetraMusica(letra : String) {
        Log.d("apiVagalume2", letra)
        menssagemErro?.text = ""
        letraMusica?.text = letra
        limparCamposInput()
    }

    private fun exibirMensagemErro() {
        letraMusica?.text = ""
        menssagemErro?.text = getString(R.string.mensagem_erro_requisicao)
        Toast.makeText(this@MainActivity, "Dados incorretos", Toast.LENGTH_SHORT).show()
    }

    private fun limparCamposInput() {
        inputNomeCantor?.editText?.text?.clear()
        inputNomeMusica?.editText?.text?.clear()
        inputNomeCantor?.editText?.requestFocus()
    }

    private fun exibirBarraProgresso() {
        barraProgresso?.visibility = View.VISIBLE
    }

    private fun esconderBarraProgresso() {
        barraProgresso?.visibility = View.INVISIBLE
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun closeKeyboard() {
        // this will give us the view which is currently focus in this layout
        val view = this.currentFocus

        // if nothing is currently focus then this will protect the app from crash
        if (view != null) {
            // now assign the system service to InputMethodManager
            val manager: InputMethodManager = getSystemService(
                Context.INPUT_METHOD_SERVICE
            ) as InputMethodManager
            manager
                .hideSoftInputFromWindow(
                    view.windowToken, 0
                )
        }
    }
}
