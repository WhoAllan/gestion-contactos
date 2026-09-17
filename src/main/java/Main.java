

import com.neuracode.holamujer.model.Contacto;
import com.neuracode.holamujer.model.EstadoContacto;
import com.neuracode.holamujer.model.Seguimiento;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
    	
        // Simulación de la base de datos o almacenamiento en memoria del sistema.
        List<Contacto> sistemaContactos = new ArrayList<>();

        System.out.println("=== HOLA MUJER ===");
        System.out.println("---  Gestión de Contactos ---\n");

        //Registro de contacto (Estado inicial: NUEVO)
        Contacto c1 = new Contacto("C001", "María López", "+51 987654321 (WhatsApp)");
        sistemaContactos.add(c1);
        System.out.println("Contacto registrado: " + c1);

        //Registrar interacción (El estado pasa dinámicamente a EN_ATENCION)
        c1.registrarInteraccion("WhatsApp", "Consulta sobre precios de exámenes.");
        System.out.println("Después de interacción -> Estado: " + c1.getEstado());

        //Crear seguimiento con fecha (El estado pasa a PENDIENTE)
        c1.agregarSeguimiento(LocalDate.now().plusDays(2), "Enviar cotización detallada por correo.");
        System.out.println("Después de agregar seguimiento -> Estado: " + c1.getEstado());

        //Registrar segundo contacto sin seguimientos
        Contacto c2 = new Contacto("C002", "Ana Torres", "ana.torres@email.com");
        c2.cambiarEstado(EstadoContacto.CERRADO);
        sistemaContactos.add(c2);

        //Filtrar y listar únicamente contactos con seguimientos pendientes
        System.out.println("\n--- Contactos con Seguimientos Pendientes ---");
        for (Contacto c : sistemaContactos) {
            if (c.tieneSeguimientosPendientes()) {
                System.out.println("- " + c.getNombre() + " [" + c.getEstado() + "]");
                for (Seguimiento s : c.getSeguimientos()) {
                    if (!s.isCompletado()) {
                        System.out.println("   * " + s);
                    }
                }
            }
        }

        //Prueba de Excepción: Intento de registro con datos obligatorios vacíos
        System.out.println("\n--- Prueba de Validación (Datos Vacíos) ---");
        try {
            Contacto cInvalido = new Contacto("C003", "", "   ");
        } catch (IllegalArgumentException e) {
            System.out.println("Excepción capturada correctamente: " + e.getMessage());
        }
    }
}