package com.example.vagalumeapi

interface MainContract {

    interface View {
        fun exibirCarregando()
        fun esconderCarregando()
        fun exibirLetraMusica(letra: String)
        fun exibirMensagemErro(mensagem: String)
        fun limparCamposInput()
    }

    interface Presenter {
        fun buscarLetraMusica(nomeCantor: String, nomeMusica: String)
    }

    interface Model {
        fun buscarLetraMusicaAPI(nomeCantor: String, nomeMusica: String, onSuccess: (ResultadoPesquisa) -> Unit, onError: () -> Unit)
    }

}