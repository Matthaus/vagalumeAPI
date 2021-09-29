package com.example.vagalumeapi

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    private val model = MainModel()

    val letraMusica : MutableLiveData<String> = MutableLiveData()
    val aconteceuErro : MutableLiveData<Boolean> = MutableLiveData()
    val exibirBarraProgresso : MutableLiveData<Boolean> = MutableLiveData()

    fun buscarLetraMusica(nomeCantor: String, nomeMusica: String) {
        exibirBarraProgresso.postValue(true)

        model.buscarLetraMusicaAPI(
            nomeCantor,
            nomeMusica,
            onSuccess = {
                val pegarMusica = it.mus
                val pegarLetraMusica = pegarMusica.firstOrNull()?.lyrics
                if (pegarLetraMusica != null) {
                    letraMusica.postValue(pegarLetraMusica)
                } else {
                    aconteceuErro.postValue(true)
                }
                exibirBarraProgresso.postValue(false)
            },
            onError = {
                exibirBarraProgresso.postValue(false)
                aconteceuErro.postValue(true)
            }
        )
    }

}