package ux.com.edu.prompteng;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ux.com.edu.prompteng.builders.PromptConfig;
import ux.com.edu.prompteng.builders.TipoPrompt;
import ux.com.edu.prompteng.context.AgenteConversacional;
import ux.com.edu.prompteng.context.impl.ModeloStrategy;
import ux.com.edu.prompteng.intent.routing.IntentRouter;
import ux.com.edu.prompteng.strategies.InteligenciaArtificialStrategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {


    private static final Logger log = LoggerFactory.getLogger(Main.class);

    private static final String ROL_POR_DEFECTO = "Asistente Virtual General";

    public static void main(String[] args) {

        AgenteConversacional miAgente = new AgenteConversacional();
        IntentRouter router = new IntentRouter();

        try (Scanner sc = new Scanner(System.in)) {
            InteligenciaArtificialStrategy modeloSeleccionado = seleccionarModelo(sc);
            miAgente.setModelo(modeloSeleccionado);

            String promptUsuario = leerPrompt(sc);
            if (promptUsuario.isBlank()) {
                System.out.println("No se recibió ninguna entrada.");
                return;
            }

            String rolDetectado;
            String instruccionesMejoradas;
            TipoPrompt tipoPrompt;
            String loQuePidioElUsuario;
            List<String[]> listaEjemplos = new ArrayList<>();

            if (router.esDelimitadores(promptUsuario)) {
                rolDetectado          = router.extraerRolDeDelimitadores(promptUsuario);
                instruccionesMejoradas = router.extraerInstruccionesDeDelimitadores(promptUsuario);
                loQuePidioElUsuario   = router.extraerConsultaDeDelimitadores(promptUsuario);
                tipoPrompt            = TipoPrompt.DELIMITERS;
            } else if (router.esPromptEstructurado(promptUsuario)) {
                rolDetectado          = router.determinarRol(promptUsuario);
                instruccionesMejoradas = router.optimizarInstrucciones(promptUsuario);
                tipoPrompt            = router.determinarTipoPrompt(rolDetectado, instruccionesMejoradas);
                loQuePidioElUsuario   = router.extraerConsultaFinal(promptUsuario);
                listaEjemplos         = router.extraerEjemplosFewShot(promptUsuario);
            } else {
                rolDetectado          = ROL_POR_DEFECTO;
                instruccionesMejoradas = promptUsuario;
                tipoPrompt            = TipoPrompt.ZERO_SHOT;
                loQuePidioElUsuario   = promptUsuario;
            }

            PromptConfig miPrompt = new PromptConfig(
                    rolDetectado,
                    instruccionesMejoradas,
                    loQuePidioElUsuario,
                    tipoPrompt,
                    listaEjemplos
            );

            if (TipoPrompt.DELIMITERS == tipoPrompt) {
                miPrompt.setContratoSalida(router.extraerContratoSalidaDeDelimitadores(promptUsuario));
            }

            log.info("Prompt Construido: {}", tipoPrompt);
            //miAgente.interactuar(miPrompt);
        } catch (Exception e) {
            log.error("Ocurrió un error al procesar la entrada: " + e.getMessage());
        }
    }

    private static InteligenciaArtificialStrategy seleccionarModelo(Scanner sc) {
        while (true) {
            System.out.println("Selecciona el modelo LLM:");
            System.out.println("1) Llama3");
            System.out.println("2) Mistral");
            System.out.println("3) Gemma2");
            String opcion = sc.nextLine().trim().toLowerCase();

            switch (opcion) {
                case "1", "llama3" -> {
                    return new ModeloStrategy("llama3", "Llama3-Local-M4");
                }
                case "2", "mistral" -> {
                    return new ModeloStrategy("mistral", "mistral");
                }
                case "3", "gemma2" -> {
                    return new ModeloStrategy("gemma2:9b", "gemma2");
                }
                default -> System.out.println("Opción inválida. Escribe 1, 2 o 3.");
            }
        }
    }

    private static String leerPrompt(Scanner sc) {
        System.out.println("Ingresa tu prompt (si es multilinea, termina con una línea que diga FIN):");
        StringBuilder sb = new StringBuilder();

        while (sc.hasNextLine()) {
            String linea = sc.nextLine();
            if ("FIN".equalsIgnoreCase(linea.trim())) {
                break;
            }

            if (!sb.isEmpty()) {
                sb.append(System.lineSeparator());
            }
            sb.append(linea);
        }

        return sb.toString().trim();
    }
}
