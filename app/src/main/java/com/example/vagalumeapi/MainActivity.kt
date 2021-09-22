package com.example.vagalumeapi

//oq melhorar no app:
//tratar erros da entrada do usuario
//dar focus no singer name ou tirar focus
//qdo apertar o botao fechar o teclado
//mudar a cor do cursor
//no final da pagina criar um link para direcionar ao vagalume

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity(), MainContract.View {

    private val presenter = MainPresenter(this)

    private var btnPesquisarMusica : Button? = null
    private var letraMusica : TextView? = null
    private var barraProgresso : ProgressBar? = null
    private var inputNomeCantor : TextInputLayout? = null
    private var inputNomeMusica : TextInputLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnPesquisarMusica = findViewById(R.id.button_pesquisar_musica)
        letraMusica = findViewById(R.id.letra_musica)
        barraProgresso = findViewById(R.id.barra_progresso)
        inputNomeCantor = findViewById(R.id.input_nome_cantor)
        inputNomeMusica = findViewById(R.id.input_nome_musica)

        btnPesquisarMusica?.setOnClickListener {
            val nomeCantor = inputNomeCantor?.editText?.text.toString()
            val nomeMusica = inputNomeMusica?.editText?.text.toString()

            presenter.buscarLetraMusica(nomeCantor, nomeMusica)
        }
    }

    override fun limparCamposInput() {
        inputNomeCantor?.editText?.text?.clear()
        inputNomeMusica?.editText?.text?.clear()
    }

    override fun exibirCarregando() {
        barraProgresso?.visibility = View.VISIBLE
    }

    override fun esconderCarregando() {
        barraProgresso?.visibility = View.INVISIBLE
    }

    override fun exibirLetraMusica(letra: String) {
        Log.d("apiVagalume", letra)
        letraMusica?.text = letra
    }

    override fun exibirMensagemErro(mensagem: String) {
        //Para ser implementado futuramente
    }
}