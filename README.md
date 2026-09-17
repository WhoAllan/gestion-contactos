# Semana 01 — Gestión de Contactos y Seguimiento de Atención
**Neuracode Academy — Cohorte Hola Mujer**  
**Practicante:** Allan Zerpa  
**Tecnologías:** Java 17 LTS, Maven, Eclipse IDE  

---

## 1. Descripción del Proyecto
Módulo de consola desarrollado en Java 17 enfocado en el dominio HealthTech de Hola Mujer. Permite administrar contactos, registrar su historial de interacciones, agendar compromisos/seguimientos y gestionar el ciclo de vida del contacto mediante validaciones estrictas y reglas de negocio.

---

## 2. Modelo de Clases y Relaciones
- **`Contacto`**: Entidad principal que encapsula datos del usuario, su estado (`EstadoContacto`) y sus colecciones de interacciones y seguimientos.
- **`Interaccion`**: Modela un evento de comunicación (medio, detalle y fecha/hora automática).
- **`Seguimiento`**: Representa tareas pendientes con fecha objetivo, descripción y estado de cumplimiento.
- **`EstadoContacto` (Enum)**: Controla los estados permitidos (`NUEVO`, `EN_ATENCION`, `PENDIENTE`, `CERRADO`).

---

## 3. Casos de Prueba Manuales

1. **Caso Válido (Flujo Feliz):**
   - **Entrada:** `Contacto` con ID "C001", Nombre "María López" y WhatsApp.
   - **Resultado:** Registro exitoso, cambio de estado a `EN_ATENCION` tras interacción y a `PENDIENTE` tras agendar seguimiento.

2. **Caso Inválido (Validación de Reglas):**
   - **Entrada:** Intento de creación de `Contacto` con nombre vacío `""` o espacios `"   "`.
   - **Resultado:** Captura exitosa de `IllegalArgumentException` con el mensaje *"El nombre del contacto no puede estar vacío."*

3. **Caso Borde (Filtrado de Pendientes):**
   - **Entrada:** Lista mixta de contactos (con y sin seguimientos activos).
   - **Resultado:** Muestra únicamente a aquellos cuya lista contiene seguimientos con `completado = false`.

---

## 4. Uso de Inteligencia Artificial
- **Herramienta:** ChatGPT / Gemini.
- **Propósito:** Asistencia en la maquetación inicial de POO, aclaración de imports/sintaxis en Java 17 y estructuración de casos de prueba.
- **Verificación:** Código adaptado, comentado en su totalidad y verificado manualmente mediante compilación y ejecución en Eclipse IDE.