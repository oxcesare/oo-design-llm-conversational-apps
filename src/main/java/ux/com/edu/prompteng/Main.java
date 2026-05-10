package ux.com.edu.prompteng;


import ux.com.edu.prompteng.context.AgenteConversacional;
import ux.com.edu.prompteng.context.impl.Llama3Strategy;
import ux.com.edu.prompteng.builders.PromptConfig;
import ux.com.edu.prompteng.intent.routing.IntentRouter;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {


    public static void main(String[] args) {

        AgenteConversacional miAgente = new AgenteConversacional();
        IntentRouter router = new IntentRouter();

        Scanner sc = new Scanner(System.in);

        try {
            // Lo que el usuario realmente quiere
            System.out.println("Ingresa tu pregunta:");
            String loQuePidioElUsuario = sc.nextLine();

            // El Router hace su magia basada en el texto del usuario
            String rolDetectado = router.determinarRol(loQuePidioElUsuario);
            String instruccionesMejoradas = router.optimizarInstrucciones(loQuePidioElUsuario);
            String tipoPrompt = router.determinarTipoPrompt(rolDetectado, instruccionesMejoradas);
            List<String> listaEjemplos = new ArrayList<>();

            if (tipoPrompt.equals("few-shot")) {
                String agregarEjemplo;
                do {
                    System.out.println("¿Quieres agregar un ejemplo? (sí/no)");
                    agregarEjemplo = sc.nextLine().trim().toLowerCase();
                    if (agregarEjemplo.equals("si")) {
                        System.out.println("Escribe el ejemplo:");
                        String ejemplo = sc.nextLine();
                        listaEjemplos.add(ejemplo);
                    }
                } while (agregarEjemplo.equals("sí") || agregarEjemplo.equals("si"));
            }

            PromptConfig miPrompt = new PromptConfig(
                    rolDetectado,
                    instruccionesMejoradas,
                    "Explícamelo como experto en el área",
                    tipoPrompt,
                    listaEjemplos
            );

            Llama3Strategy miLlama = new Llama3Strategy();
            miAgente.setModelo(miLlama);

            miAgente.interactuar(miPrompt);

        } catch (Exception e) {
            System.out.println("Ocurrió un error al procesar la entrada: " + e.getMessage());
        } finally {
            sc.close();
        }
    }
}
