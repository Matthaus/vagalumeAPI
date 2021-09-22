package com.example.vagalumeapi

class MainPresenter(private val view: MainContract.View) : MainContract.Presenter {

    private val model = MainModel()

    override fun buscarLetraMusica(nomeCantor: String, nomeMusica: String) {
        //Passo 1: exibir o loader
        view.exibirCarregando()

        //Passo 2: realizar a busca de API
        model.buscarLetraMusicaAPI(
                nomeCantor,
                nomeMusica,
                onSuccess = {
                    //Passo 3: assim que retornar sucesso, processar
                    val musicaEncontrada = it.mus.firstOrNull()
                    if (musicaEncontrada != null) {
                        view.exibirLetraMusica(musicaEncontrada.text)
                    } else {
                        view.exibirMensagemErro("Música não encontrada")
                    }
                    //Passo 4: esconder o loader no final do processo
                    view.esconderCarregando()
                },
                onError = {
                    //Passo 5: caso dê erro de rede, exibimos na view
                    view.exibirMensagemErro("Deu erro de rede")
                })

    }
}