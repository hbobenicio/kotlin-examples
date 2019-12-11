package br.com.hbobenicio.kotlinexamples.hellokotlinfx

import javafx.fxml.FXML
import java.util.logging.Logger

class AppController {

    companion object {
        private val LOG = Logger.getLogger(AppController::class.java.name)
    }

    @FXML
    private fun initialize() {
        LOG.info("Inicializando...")
    }

    @FXML
    private fun sair() {
        LOG.info("Clique detectado!")
    }
}
