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
import com.example.vagalumeapi.databinding.ActivityMainBinding
import com.google.android.material.textfield.TextInputLayout

class MainActivity : AppCompatActivity() {

    private val mainViewModel by viewModels<MainViewModel>()

    //Nova variável para receber a instancia do binding :)
    private lateinit var binding : ActivityMainBinding

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Podemos remover o setContentView pois a responsabilidade agora é do View Binding
        //setContentView(R.layout.activity_main)

        //Inflando a view através do View Binding utilizando o inflater da tela, que já existe
        //sempre = layoutInflater
        binding = ActivityMainBinding.inflate(layoutInflater)

        //Adicionamos o setContent para adicionar a view criado pelo View Binding
        //Esssa view é a representação do layout inflado ou seja o activity_main.xml inflado
        setContentView(binding.root)

        //setTitle("Find your song") ou apenas
        title = "Find your song"

        setObservers()
        binding.buttonPesquisarMusica.setOnClickListener {
            mainViewModel.buscarLetraMusica(pegarEntradaUsuario().first, pegarEntradaUsuario().second)
            closeKeyboard()
            esconderBarraProgresso()
        }
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
        val nomeCantor = binding.inputNomeCantor.editText?.text.toString()
        val nomeMusica = binding.inputNomeMusica.editText?.text.toString()
        return nomeCantor to nomeMusica
    }

    private fun exibirLetraMusica(letra : String) {
        Log.d("apiVagalume2", letra)
        binding.menssagemErro.text = ""
        binding.letraMusica.text = letra
        limparCamposInput()
    }

    private fun exibirMensagemErro() {
        binding.letraMusica.text = ""
        binding.menssagemErro.text = getString(R.string.mensagem_erro_requisicao)
        Toast.makeText(this@MainActivity, "Dados incorretos", Toast.LENGTH_SHORT).show()
    }

    private fun limparCamposInput() {
        binding.inputNomeCantor.editText?.text?.clear()
        binding.inputNomeMusica.editText?.text?.clear()
        binding.inputNomeCantor.editText?.requestFocus()
    }

    private fun exibirBarraProgresso() {
        binding.barraProgresso.visibility = View.VISIBLE
    }

    private fun esconderBarraProgresso() {
        binding.barraProgresso.visibility = View.INVISIBLE
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
