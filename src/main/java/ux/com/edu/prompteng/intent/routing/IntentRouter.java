package ux.com.edu.prompteng.intent.routing;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    private static final String ROL_POR_DEFECTO = "Asistente Virtual General";
    private static final Pattern PATRON_ROL_EXPLICITO = Pattern.compile(
            "(?i)(?:^|\\n)\\s*(?:eres\\s+una?|actua\\s+como|actúa\\s+como|asume\\s+el\\s+rol\\s+de)\\s+([^\\n.]+)"
    );
    private static final Pattern PATRON_CONSULTA = Pattern.compile("(?is)consulta\\s+del\\s+usuario\\s*:\\s*(.+)$");
    private static final Pattern PATRON_EJEMPLOS = Pattern.compile(
            "(?is)<ejemplo>\\s*entrada:\\s*(.+?)\\s*salida:\\s*(.+?)\\s*</ejemplo>"
    );
    // Patrones para extraer campos de prompts con delimitadores XML
    private static final Pattern PATRON_TAG_ROLE      = Pattern.compile("(?is)<role>(.*?)</role>");
    private static final Pattern PATRON_TAG_TASK      = Pattern.compile("(?is)<task_description>(.*?)</task_description>");
    private static final Pattern PATRON_TAG_OUTPUT    = Pattern.compile("(?is)<output_contract>(.*?)</output_contract>");
    private static final Pattern PATRON_TAG_USER      = Pattern.compile("(?is)<user_query>(.*?)</user_query>");

    /**
     * Analiza las instrucciones del usuario y determina el rol más adecuado
     * para responder su solicitud.
     *
     * @param instruccionesUsuario las instrucciones en texto libre introducidas por el usuario
     * @return el nombre del rol asignado según las palabras clave detectadas
     */
    public String determinarRol(String instruccionesUsuario) {
        if (instruccionesUsuario == null || instruccionesUsuario.isBlank()) {
            return ROL_POR_DEFECTO;
        }

        String rolExplicito = extraerRolExplicito(instruccionesUsuario);
        if (!rolExplicito.isBlank()) {
            return rolExplicito;
        }

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

        return ROL_POR_DEFECTO;
    }

    /**
     * Optimiza las instrucciones del usuario añadiendo delimitadores o
     * indicaciones de formato específicas según el dominio detectado.
     *
     * @param instrucciones las instrucciones originales del usuario
     * @return las instrucciones enriquecidas con directrices adicionales de formato
     */
    public String optimizarInstrucciones(String instrucciones) {
        if (instrucciones == null || instrucciones.isBlank()) {
            return "";
        }

        String lower = instrucciones.toLowerCase();

        if (lower.contains("formato exacto") || lower.contains("responde siempre con el siguiente formato")) {
            return instrucciones + " (Respeta el formato de salida exactamente como se indica).";
        }

        // Si es clima, le agregamos que use un formato específico
        if (lower.contains("clima")) {
            return instrucciones + " (Responde solo con la temperatura y condición)";
        }
        // si es sobre Inteligencia artificial
        if (lower.contains("inteligencia artificial") || lower.contains("ia")) {
            return instrucciones + " (Explica con ejemplos y analogías)";
        }

        // si es sobre videojuegos
        if (lower.contains("videojuegos") || lower.contains("gaming")) {
            return instrucciones + " (Incluye referencias a juegos populares)";
        }

        if(lower.contains("razona internamente") || lower.contains("internamente")) {
            return instrucciones + " (Realiza un razonamiento interno paso a paso antes de responder)";
        }


        return instrucciones;
    }

    /**
     * Determina el tipo de prompt más adecuado a partir del rol asignado
     * y las instrucciones ya optimizadas.
     *
     * <p>Prioridad de clasificación:</p>
     * <ol>
     *   <li><b>delimiters</b>: etiquetas XML estructuradas.</li>
     *   <li><b>meta-prompting</b>: generar o mejorar un prompt.</li>
     *   <li><b>chain-of-thought</b>: razonamiento paso a paso.</li>
     *   <li><b>few-shot</b>: ejemplos o formato objetivo.</li>
     *   <li><b>role-based</b>: rol explícito.</li>
     *   <li><b>zero-shot</b>: caso por defecto.</li>
     * </ol>
     *
     * @param rol                      el rol determinado previamente por {@link #determinarRol(String)}
     * @param instruccionesOptimizadas las instrucciones procesadas por {@link #optimizarInstrucciones(String)}
     * @return una cadena con el tipo de prompt seleccionado
     */
    public String determinarTipoPrompt(String rol, String instruccionesOptimizadas) {

        if (instruccionesOptimizadas == null || instruccionesOptimizadas.isBlank()) {
            return "zero-shot";
        }

        // 1ª prioridad: delimitadores XML estructurados
        if (esDelimitadores(instruccionesOptimizadas)) {
            return "delimiters";
        }

        String instruccionesLower = instruccionesOptimizadas.toLowerCase();

        // 2ª prioridad: meta-prompting
        if (esMetaPrompting(instruccionesLower)) {
            return "meta-prompting";
        }

        // 3ª prioridad: chain-of-thought
        if (esChainOfThought(instruccionesLower)) {
            return "chain-of-thought";
        }

        // 4ª prioridad: few-shot — prompts estructurados con formato objetivo
        if (instruccionesLower.contains("ejemplo") ||
                (instruccionesLower.contains("formato") && instruccionesLower.contains("consulta del usuario")) ||
                instruccionesLower.contains("responde siempre con el siguiente formato") ||
                instruccionesLower.contains("muestra cómo") ||
                instruccionesLower.contains("casos de uso") ||
                instruccionesLower.contains("siguiendo este formato")) {
            return "few-shot";
        }

        // 5ª prioridad: role-based
        String rolNormalizado = rol == null ? "" : rol.trim().toLowerCase();
        if ((!rolNormalizado.isEmpty() && !rolNormalizado.equals("asistente virtual general"))
                || instruccionesLower.contains("actúa como")
                || instruccionesLower.contains("asume el rol de")) {
            return "role-based";
        }

        return "zero-shot";
    }

    public boolean esDelimitadores(String promptUsuario) {
        if (promptUsuario == null || promptUsuario.isBlank()) return false;
        String lower = promptUsuario.toLowerCase();
        return lower.contains("<system_context>")
                && lower.contains("<role>")
                && lower.contains("<task_description>")
                && lower.contains("<user_query>");
    }

    public boolean esPromptEstructurado(String promptUsuario) {
        if (promptUsuario == null || promptUsuario.isBlank()) {
            return false;
        }

        String lower = promptUsuario.toLowerCase();
        return esDelimitadores(promptUsuario)
                || esMetaPrompting(lower)
                || lower.contains("consulta del usuario")
                || lower.contains("responde siempre con el siguiente formato")
                || lower.contains("eres un")
                || lower.contains("actúa como")
                || lower.contains("actua como")
                || lower.contains("asume el rol de");
    }

    public String extraerRolDeDelimitadores(String prompt) {
        return extraerEntreTags(PATRON_TAG_ROLE, prompt);
    }

    public String extraerInstruccionesDeDelimitadores(String prompt) {
        return extraerEntreTags(PATRON_TAG_TASK, prompt);
    }

    public String extraerContratoSalidaDeDelimitadores(String prompt) {
        return extraerEntreTags(PATRON_TAG_OUTPUT, prompt);
    }

    public String extraerConsultaDeDelimitadores(String prompt) {
        return extraerEntreTags(PATRON_TAG_USER, prompt);
    }

    public String extraerConsultaFinal(String promptUsuario) {
        if (promptUsuario == null || promptUsuario.isBlank()) {
            return "";
        }

        // Si es delimitado, usar tag <user_query>
        if (esDelimitadores(promptUsuario)) {
            return extraerConsultaDeDelimitadores(promptUsuario);
        }

        Matcher matcher = PATRON_CONSULTA.matcher(promptUsuario);
        if (!matcher.find()) {
            return promptUsuario.trim();
        }

        String bloqueConsulta = matcher.group(1).trim();
        String[] lineas = bloqueConsulta.split("\\R");
        for (String linea : lineas) {
            String candidata = linea.trim();
            if (!candidata.isEmpty()) {
                return candidata;
            }
        }

        return promptUsuario.trim();
    }

    public List<String[]> extraerEjemplosFewShot(String promptUsuario) {
        List<String[]> ejemplos = new ArrayList<>();
        if (promptUsuario == null || promptUsuario.isBlank()) {
            return ejemplos;
        }

        Matcher matcher = PATRON_EJEMPLOS.matcher(promptUsuario);
        while (matcher.find()) {
            String entrada = matcher.group(1) == null ? "" : matcher.group(1).trim();
            String salida  = matcher.group(2) == null ? "" : matcher.group(2).trim();
            if (!entrada.isEmpty() && !salida.isEmpty()) {
                ejemplos.add(new String[]{entrada, salida});
            }
        }

        return ejemplos;
    }

    private String extraerEntreTags(Pattern patron, String texto) {
        if (texto == null || texto.isBlank()) return "";
        Matcher matcher = patron.matcher(texto);
        return matcher.find() ? matcher.group(1).trim() : "";
    }

    private String extraerRolExplicito(String instruccionesUsuario) {
        Matcher matcher = PATRON_ROL_EXPLICITO.matcher(instruccionesUsuario);
        if (matcher.find()) {
            return matcher.group(1).trim();
        }
        return "";
    }

    private boolean esChainOfThought(String textoLower) {
        return textoLower.contains("paso a paso")
                || textoLower.contains("razona internamente")
                || textoLower.contains("razona")
                || textoLower.contains("piensa paso a paso")
                || textoLower.contains("pensemos")
                || textoLower.contains("desglosa")
                || textoLower.contains("no muestres tu razonamiento")
                || textoLower.contains("siguiendo este procedimiento");
    }

    private boolean esMetaPrompting(String texto) {
        return texto.contains("genera un prompt")
                || texto.contains("generar un prompt")
                || texto.contains("crear un prompt")
                || texto.contains("crea un prompt")
                || texto.contains("diseñar un prompt")
                || texto.contains("disenar un prompt")
                || texto.contains("diseña una instrucción")
                || texto.contains("disena una instruccion")
                || texto.contains("optimiza este prompt")
                || texto.contains("genera únicamente el prompt")
                || texto.contains("genera unicamente el prompt")
                || texto.contains("prompt final")
                || (texto.contains("prompt") && texto.contains("otro modelo"));
    }
}

