package ux.com.edu.prompteng.strategies;

import ux.com.edu.prompteng.builders.PromptConfig;

/**
 * Definimos el contrato: recibir un prompt y devolver una respuesta.
 *
 * <p>Esta interfaz establece el contrato que deben cumplir todas las
 * implementaciones de estrategias de inteligencia artificial, garantizando
 * que cualquier modelo pueda recibir una configuración de prompt y
 * retornar una respuesta generada.</p>
 *
 * @author ux
 * @version 1.0
 * @since 1.0
 */
public interface InteligenciaArtificialStrategy {

    /**
     * Genera una respuesta a partir de la configuración del prompt proporcionada.
     *
     * @param config la configuración del prompt que contiene los parámetros
     *               necesarios para construir y enviar la solicitud al modelo
     * @return una cadena de texto con la respuesta generada por el modelo
     */
    String generarRespuesta(PromptConfig config);

    /**
     * Obtiene el nombre del modelo de inteligencia artificial asociado
     * a esta estrategia.
     *
     * @return el nombre identificador del modelo
     */
    String getNombreModelo();
}

