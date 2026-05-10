package ux.com.edu.prompteng.intent.routing;

/**
 * Enrutador de intenciones del usuario.
 *
 * <p>Esta clase analiza las instrucciones del usuario para determinar
 * el rol más adecuado, optimizar las instrucciones y seleccionar el
 * tipo de prompt que mejor se adapta al contexto de la solicitud.</p>
 *
 * @author cesarruiz
 * @version 1.0
 * @since 1.0
 */
public class IntentRouter {

    /**
     * Analiza las instrucciones del usuario y determina el rol más adecuado
     * para responder su solicitud.
     *
     * @param instruccionesUsuario las instrucciones en texto libre introducidas por el usuario
     * @return el nombre del rol asignado según las palabras clave detectadas
     */
    public String determinarRol(String instruccionesUsuario) {
        String input = instruccionesUsuario.toLowerCase();

        if (input.contains("clima") || input.contains("tiempo")) {
            return "Meteorólogo Profesional Certificado";
        }
        if (input.contains("patrón") || input.contains("código") || input.contains("java")) {
            return "Arquitecto de Software Senior y experto en Clean Code";
        }
        if (input.contains("tarea") || input.contains("explica")) {
            return "Profesor de Inteligencia Artificial";
        }

        return "Asistente Virtual General";
    }

    /**
     * Optimiza las instrucciones del usuario añadiendo delimitadores o
     * indicaciones de formato específicas según el dominio detectado.
     *
     * @param instrucciones las instrucciones originales del usuario
     * @return las instrucciones enriquecidas con directrices adicionales de formato
     */
    public String optimizarInstrucciones(String instrucciones) {
        // Si es clima, le agregamos que use un formato específico
        if (instrucciones.toLowerCase().contains("clima")) {
            return instrucciones + " (Responde solo con la temperatura y condición)";
        }
        // si es sobre Inteligencia artificial
        if (instrucciones.toLowerCase().contains("inteligencia artificial") || instrucciones.toLowerCase().contains("ia")) {
            return instrucciones + " (Explica con ejemplos y analogías)";
        }

        // si es sobre videojuegos
        if (instrucciones.toLowerCase().contains("videojuegos") || instrucciones.toLowerCase().contains("gaming")) {
            return instrucciones + " (Incluye referencias a juegos populares)";
        }


        return instrucciones;
    }

    /**
     * Determina el tipo de prompt más adecuado a partir del rol asignado
     * y las instrucciones ya optimizadas.
     *
     * <p>Los tipos de prompt soportados son:</p>
     * <ul>
     *   <li><b>few-shot</b>: cuando se detectan ejemplos o casos de uso.</li>
     *   <li><b>chain-of-thought</b>: cuando se requiere razonamiento paso a paso.</li>
     *   <li><b>meta-prompting</b>: cuando la solicitud es generar o mejorar un prompt.</li>
     *   <li><b>role-based</b>: cuando se ha asignado un rol específico al modelo.</li>
     *   <li><b>zero-shot</b>: caso por defecto sin ejemplos ni rol especializado.</li>
     * </ul>
     *
     * @param rol                      el rol determinado previamente por {@link #determinarRol(String)}
     * @param instruccionesOptimizadas las instrucciones procesadas por {@link #optimizarInstrucciones(String)}
     * @return una cadena con el tipo de prompt seleccionado
     */
    public String determinarTipoPrompt(String rol, String instruccionesOptimizadas) {

        String instruccionesLower = instruccionesOptimizadas.toLowerCase();

        if (instruccionesLower.contains("ejemplo") ||
                instruccionesLower.contains("muestra cómo") ||
                instruccionesLower.contains("casos de uso") ||
                instruccionesLower.contains("siguiendo este formato")) {
            return "few-shot";
        }

        if (instruccionesLower.contains("paso a paso") ||
                instruccionesLower.contains("razona") ||
                instruccionesLower.contains("pensemos") ||
                instruccionesLower.contains("desglosa")) {
            return "chain-of-thought";
        }

        if (instruccionesLower.contains("genera un prompt") ||
                instruccionesLower.contains("diseña una instrucción") ||
                instruccionesLower.contains("optimiza este prompt")) {
            return "meta-prompting";
        }

        String rolNormalizado = rol == null ? "" : rol.trim().toLowerCase();
        if ((!rolNormalizado.isEmpty() && !rolNormalizado.equals("asistente virtual general"))
                || instruccionesLower.contains("actúa como")
                || instruccionesLower.contains("asume el rol de")) {
            return "role-based";
        }


        return "zero-shot";
    }
}