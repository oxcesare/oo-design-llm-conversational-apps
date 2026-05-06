package ux.com.edu.prompteng.context;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ux.com.edu.prompteng.implementation.builders.PromptConfig;
import ux.com.edu.prompteng.implementation.strategies.InteligenciaArtificialStrategy;

public class AgenteConversacional {

    private static final Logger log = LoggerFactory.getLogger(AgenteConversacional.class);

    private InteligenciaArtificialStrategy modelo;

    public void setModelo(InteligenciaArtificialStrategy nuevoModelo) {

        this.modelo = nuevoModelo;

        log.info("Cambiando cerebro a: {}", nuevoModelo.getNombreModelo());

    }

    public void interactuar(PromptConfig promptConfig) {

        if (modelo == null) {
            log.error("No hay un modelo de IA cargado.");
            return;
        }

        String respuesta = modelo.generarRespuesta(promptConfig);
        log.info("{}", respuesta);
    }
}
