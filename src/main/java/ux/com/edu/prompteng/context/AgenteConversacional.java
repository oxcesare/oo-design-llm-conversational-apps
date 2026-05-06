package ux.com.edu.prompteng.context;

import ux.com.edu.prompteng.implementation.builders.PromptConfig;
import ux.com.edu.prompteng.implementation.strategies.InteligenciaArtificialStrategy;

public class AgenteConversacional {

    private InteligenciaArtificialStrategy modelo;

    // El corazón del patrón: inyección de la estrategia

    public void setModelo(InteligenciaArtificialStrategy nuevoModelo) {

        this.modelo = nuevoModelo;

        System.out.println("Cambiando cerebro a: " + nuevoModelo.getNombreModelo());

    }

    public void interactuar(PromptConfig promptConfig) {

        if (modelo == null) {

            System.err.println("Error: No hay un modelo de IA cargado.");

            return;

        }

        System.out.println(modelo.generarRespuesta(promptConfig));

    }
}
