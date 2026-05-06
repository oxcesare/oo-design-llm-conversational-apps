package ux.com.edu.prompteng.implementation.builders;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase que utiliza el patrón Builder para construir prompts dinámicos
 * siguiendo las mejores prácticas de Delimitadores y Estructura.
 */
public class PromptBuilder {

    private String rol;
    private String instrucciones;
    private List<String> ejemplos = new ArrayList<>();
    private String entradaUsuario;

    public PromptBuilder conRol(String rol) {
        this.rol = rol;
        return this;
    }

    public PromptBuilder conInstrucciones(String instrucciones) {
        this.instrucciones = instrucciones;
        return this;
    }

    public PromptBuilder agregarEjemplo(String entrada, String salida) {
        this.ejemplos.add(String.format("<ejemplo>\nEntrada: %s\nSalida: %s\n</ejemplo>", entrada, salida));
        return this;
    }

    public PromptBuilder conEntrada(String entradaUsuario) {
        this.entradaUsuario = entradaUsuario;
        return this;
    }

    public String build() {
        StringBuilder sb = new StringBuilder();
        sb.append("<system>\n");
        sb.append("Eres un: ").append(rol).append("\n");
        sb.append("Instrucciones: ").append(instrucciones).append("\n");
        sb.append("</system>\n");

        if (!ejemplos.isEmpty()) {
            sb.append("<ejemplos>\n");
            ejemplos.forEach(e -> sb.append(e).append("\n"));
            sb.append("</examples>\n");
        }

        sb.append("<user>\n").append(entradaUsuario).append("\n</user>");
        return sb.toString();
    }

    // Método para zero-shot (sin ejemplos)
    public static PromptBuilder zeroShot(String rol, String instrucciones, String entradaUsuario) {
        return new PromptBuilder()
                .conRol(rol)
                .conInstrucciones(instrucciones)
                .conEntrada(entradaUsuario);
    }

    // Método para few-shot (con ejemplos)
    public static PromptBuilder fewShot(String rol, String instrucciones, List<String[]> ejemplos, String entradaUsuario) {
        PromptBuilder builder = new PromptBuilder()
                .conRol(rol)
                .conInstrucciones(instrucciones);
        for (String[] ejemplo : ejemplos) {
            builder.agregarEjemplo(ejemplo[0], ejemplo[1]);
        }
        builder.conEntrada(entradaUsuario);
        return builder;
    }

    // Método para Chain-of-Thought (Pensamiento paso a paso)
    public static PromptBuilder chainOfThought(String rol, String instrucciones, String entradaUsuario) {
        String instruccionCoT = instrucciones + "\nAnaliza el problema paso a paso antes de dar la respuesta final.";
        return new PromptBuilder()
                .conRol(rol)
                .conInstrucciones(instruccionCoT)
                .conEntrada(entradaUsuario);
    }

    // Método para Meta-Prompting (Generador de Prompts)
    public static PromptBuilder metaPrompting(String instruccionesParaCrearPrompt) {
        return new PromptBuilder()
                .conRol("Experto en Ingeniería de Prompts")
                .conInstrucciones("Tu tarea es diseñar un prompt profesional y optimizado basado en los requisitos del usuario.")
                .conEntrada(instruccionesParaCrearPrompt);
    }

    // Método para Role-based Prompting (Persona)
    public static PromptBuilder roleBased(String rolEspecializado, String instrucciones, String entradaUsuario) {
        // Aquí el 'rol' deja de ser un campo opcional y se convierte en el núcleo del comportamiento
        return new PromptBuilder()
                .conRol(rolEspecializado)
                .conInstrucciones("Como " + rolEspecializado + ", tu tarea es: " + instrucciones)
                .conEntrada(entradaUsuario);
    }


}
