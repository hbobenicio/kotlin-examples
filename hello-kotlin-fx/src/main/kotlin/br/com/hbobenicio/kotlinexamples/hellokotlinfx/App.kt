package br.com.hbobenicio.kotlinexamples.hellokotlinfx

import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.stage.Stage
import java.lang.RuntimeException
import java.net.URL

class App : Application() {

    companion object {
        private var scene: Scene? = null
    }

    override fun start(stage: Stage) {
        val ui: URL = javaClass.classLoader.getResource("ui/main.fxml") ?:
            throw RuntimeException("Não foi possível encontrar interface FXML (ui/main.fxml)")

        val root: Parent = FXMLLoader.load(ui)

        stage.apply {
            title = "Hello, World!"
            scene = Scene(root)

            show()
        }
    }
}
