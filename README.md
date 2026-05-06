# LL-oo-integration

Implementación de un modelo de diseño orientado a objetos para la integración de modelos LLM en aplicaciones conversacionales.

## Descripción del Proyecto

Este proyecto proporciona una arquitectura extensible y flexible para integrar modelos de lenguaje de gran escala (LLM) en aplicaciones conversacionales. El diseño permite cambiar dinámicamente entre diferentes modelos sin requerir cambios en la lógica empresarial del cliente.

## Características Principales

- **Abstracción de Modelos**: Interfaz genérica que permite trabajar con diferentes proveedores de LLM
- **Estrategias Intercambiables**: Implementación del patrón Strategy para cambiar entre modelos sin afectar la lógica de aplicación
- **IntentRouter**: Ruteador inteligente que determina el tipo de prompt a ejecutar basado en reglas de Ingeniería de Prompts
- **Gestor Conversacional**: Módulo AgenteConversacional que mantiene el contexto de la conversación

## Arquitectura

### Componentes Principales

```
ux.com.edu.prompteng
├── client/
│   └── OllamaClient          # Cliente para comunicación con modelos LLM
├── context/
│   ├── AgenteConversacional  # Gestor de conversaciones
│   └── impl/
│       └── Llama3Strategy    # Implementación específica del modelo Llama3
├── implementation/
│   ├── builders/
│   │   ├── PromptBuilder     # Construtor de prompts
│   │   └── PromptConfig      # Configuración de prompts
│   └── strategies/
│       └── InteligenciaArtificialStrategy  # Estrategia base de IA
└── core/                     # Clases base y núcleo
```

## Patrones de Diseño Utilizados

- **Strategy Pattern**: Para permitir el cambio dinámico de modelos LLM
- **Builder Pattern**: Para la construcción de prompts complejos
- **Adapter Pattern**: Para la integración con diferentes APIs de LLM

## Instalación y Configuración

### Requisitos Previos

- Java 8+
- Maven 3.6+
- Ollama (para ejecutar modelos locales)

### Construcción del Proyecto

```bash
mvn clean install
```

### Ejecución

```bash
mvn exec:java -Dexec.mainClass="ux.com.edu.prompteng.Main"
```

## Uso

```java
// Ejemplo básico de uso
AgenteConversacional agente = new AgenteConversacional(new Llama3Strategy());
String respuesta = agente.procesarPrompt("¿Cuál es tu nombre?");
```

## IntentRouter

El `IntentRouter` es responsable de:

- Analizar el intent del usuario
- Aplicar reglas de Ingeniería de Prompts
- Determinar el tipo de prompt más apropiado
- Enrutar la solicitud al modelo correspondiente

## Contribuciones

Para contribuir al proyecto, por favor:

1. Fork el repositorio
2. Crea una rama para tu característica (`git checkout -b feature/AmazingFeature`)
3. Commit tus cambios (`git commit -m 'Add some AmazingFeature'`)
4. Push a la rama (`git push origin feature/AmazingFeature`)
5. Abre un Pull Request

## Licencia

Este proyecto está bajo la licencia MIT.

## Autores

- Cesar Ruiz

## Contacto

Para más información o preguntas, por favor abre un issue en el repositorio.

