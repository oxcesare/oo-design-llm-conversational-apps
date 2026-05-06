package ux.com.edu.prompteng.implementation.strategies;

import ux.com.edu.prompteng.implementation.builders.PromptConfig;

public interface InteligenciaArtificialStrategy {

    // Definimos el contrato: recibir un prompt y devolver una respuesta

    String generarRespuesta(PromptConfig config);

    String getNombreModelo();
}

