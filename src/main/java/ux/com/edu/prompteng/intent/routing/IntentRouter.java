package ux.com.edu.prompteng.intent.routing;


public class IntentRouter {

    // Este método analiza las instrucciones que el usuario metió en el Main
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

    // Aquí podrías incluso determinar si necesita Delimitadores o no
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

    // Crear el metodo que determine el tipo de Prompt de acuerdo Al rol y al optimzadorInstrucciones

    /**
     * Este metodo tiene que definir que tipo de prompt es el que se selecciona a partir
     * de un rol y  una instruccion
     * @param rol
     * @param instruccionesOptimizadas
     * @return
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

        // 1. Detección de Meta-Prompting
        if (instruccionesLower.contains("genera un prompt") ||
                instruccionesLower.contains("diseña una instrucción") ||
                instruccionesLower.contains("optimiza este prompt")) {
            return "meta-prompting";
        }

        // Detección de Role-based Prompting
        // Se activa si el rol no es genérico o si las instrucciones piden explícitamente adoptar una identidad.
        String rolNormalizado = rol == null ? "" : rol.trim().toLowerCase();
        if ((!rolNormalizado.isEmpty() && !rolNormalizado.equals("asistente virtual general"))
                || instruccionesLower.contains("actúa como")
                || instruccionesLower.contains("asume el rol de")) {
            return "role-based";
        }



        return "zero-shot";
    }
}