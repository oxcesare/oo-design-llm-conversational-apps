package ux.com.edu.prompteng.strategies.prompt;

import ux.com.edu.prompteng.builders.PromptConfig;

/**
 * Contrato para construir el texto final del prompt según su tipo.
 * Cada implementación encapsula la lógica de construcción de un TipoPrompt.
 *
 * <p>Para agregar un nuevo tipo de prompt basta con:
 * <ol>
 *   <li>Añadir el valor en {@link ux.com.edu.prompteng.builders.TipoPrompt}.</li>
 *   <li>Crear una clase que implemente esta interfaz.</li>
 *   <li>Registrarla en {@code PromptStrategyRegistry}.</li>
 * </ol>
 * </p>
 *
 * @author cesarruiz
 * @version 1.0
 */
public interface PromptGenerationStrategy {

    /**
     * Construye el texto del prompt listo para enviar al modelo LLM.
     *
     * @param config configuración del prompt con rol, instrucciones, entrada y ejemplos
     * @return el prompt en formato String
     */
    String buildPrompt(PromptConfig config);
}

