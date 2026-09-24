package com.neuracode.holamujer.main;

import com.neuracode.holamujer.model.Contacto;
import com.neuracode.holamujer.model.Seguimiento;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static final List<Contacto> contactos = new ArrayList<>();

    public static void main(String[] args) {

        int opcion;

        System.out.println("=================================");
        System.out.println("         HOLA MUJER");
        System.out.println("   GESTIÓN DE CONTACTOS");
        System.out.println("=================================");

        do {
            mostrarMenu();
            opcion = leerEntero("Seleccione una opción: ");

            try {
                switch (opcion) {

                    case 1:
                        registrarContacto();
                        break;

                    case 2:
                        registrarInteraccion();
                        break;

                    case 3:
                        programarSeguimiento();
                        break;

                    case 4:
                        completarSeguimiento();
                        break;

                    case 5:
                        mostrarContactos();
                        break;

                    case 6:
                        mostrarSeguimientosPendientes();
                        break;

                    case 0:
                        System.out.println("\nSistema finalizado.");
                        break;

                    default:
                        System.out.println("\nOpción no válida.");

                }

            } catch (IllegalArgumentException e) {
                System.out.println("\nError: " + e.getMessage());
            }

        } while (opcion != 0);

        scanner.close();
    }

    // =========================
    // MENÚ PRINCIPAL
    // =========================

    private static void mostrarMenu() {

        System.out.println("\n---------------------------------");
        System.out.println("             MENÚ");
        System.out.println("---------------------------------");
        System.out.println("1. Registrar contacto");
        System.out.println("2. Registrar interacción");
        System.out.println("3. Programar seguimiento");
        System.out.println("4. Completar seguimiento");
        System.out.println("5. Consultar contactos");
        System.out.println("6. Ver seguimientos pendientes");
        System.out.println("0. Salir");
        System.out.println("---------------------------------");
    }

    // =========================
    // REGISTRAR CONTACTO
    // =========================

    private static void registrarContacto() {

        System.out.println("\n--- REGISTRAR CONTACTO ---");

        String id = leerTexto("ID del contacto: ");

        if (buscarContacto(id) != null) {
            System.out.println("Ya existe un contacto con ese ID.");
            return;
        }

        String nombre = leerTexto("Nombre: ");
        String medio = leerTexto("Medio de contacto: ");

        Contacto contacto = new Contacto(id, nombre, medio);

        contactos.add(contacto);

        System.out.println("\nContacto registrado correctamente.");
        System.out.println("Estado actual: " + contacto.getEstado());
    }

    // =========================
    //  REGISTRAR INTERACCIÓN
    // =========================

    private static void registrarInteraccion() {

        System.out.println("\n--- REGISTRAR INTERACCIÓN ---");

        String id = leerTexto("ID del contacto: ");

        Contacto contacto = buscarContacto(id);

        if (contacto == null) {
            System.out.println("No se encontró un contacto con ese ID.");
            return;
        }

        System.out.println("Contacto: " + contacto.getNombre());
        System.out.println("Estado anterior: " + contacto.getEstado());

        String medio = leerTexto("Medio de interacción: ");
        String detalle = leerTexto("Detalle de la interacción: ");

        contacto.registrarInteraccion(medio, detalle);

        System.out.println("\nInteracción registrada correctamente.");
        System.out.println("Estado actual: " + contacto.getEstado());
    }

    // =========================
    //  PROGRAMAR SEGUIMIENTO
    // =========================

    private static void programarSeguimiento() {

        System.out.println("\n--- PROGRAMAR SEGUIMIENTO ---");

        String id = leerTexto("ID del contacto: ");

        Contacto contacto = buscarContacto(id);

        if (contacto == null) {
            System.out.println("No se encontró un contacto con ese ID.");
            return;
        }

        if (contacto.getInteracciones().isEmpty()) {
            System.out.println(
                    "Primero debe registrarse una interacción con el contacto."
            );
            return;
        }

        System.out.println("Contacto: " + contacto.getNombre());
        System.out.println("Estado actual: " + contacto.getEstado());

        LocalDate fecha = leerFecha("Fecha del seguimiento (AAAA-MM-DD): ");

        String descripcion = leerTexto("Descripción de la tarea: ");

        contacto.agregarSeguimiento(fecha, descripcion);

        System.out.println("\nSeguimiento programado correctamente.");
        System.out.println("Estado actual: " + contacto.getEstado());
    }

    // =========================
    //  COMPLETAR SEGUIMIENTO
    // =========================

    private static void completarSeguimiento() {

        System.out.println("\n--- COMPLETAR SEGUIMIENTO ---");

        String id = leerTexto("ID del contacto: ");

        Contacto contacto = buscarContacto(id);

        if (contacto == null) {
            System.out.println("No se encontró un contacto con ese ID.");
            return;
        }

        List<Seguimiento> seguimientos = contacto.getSeguimientos();

        if (seguimientos.isEmpty()) {
            System.out.println("El contacto no tiene seguimientos.");
            return;
        }

        boolean encontrado = false;

        for (Seguimiento seguimiento : seguimientos) {

            if (!seguimiento.isCompletado()) {

                System.out.println("\nSeguimiento pendiente:");
                System.out.println(seguimiento);

                String confirmar = leerTexto(
                        "¿Desea marcarlo como completado? (S/N): "
                );

                if (confirmar.equalsIgnoreCase("S")) {

                    seguimiento.marcarComoCompletado();

                    if (!contacto.tieneSeguimientosPendientes()) {
                        contacto.cambiarEstado(
                                com.neuracode.holamujer.model.EstadoContacto.CERRADO
                        );
                    }

                    System.out.println(
                            "\nSeguimiento completado correctamente."
                    );

                    System.out.println(
                            "Estado actual del contacto: "
                                    + contacto.getEstado()
                    );
                }

                encontrado = true;
                break;
            }
        }

        if (!encontrado) {
            System.out.println("No existen seguimientos pendientes.");
        }
    }

    // =========================
    //  MOSTRAR CONTACTOS
    // =========================

    private static void mostrarContactos() {

        System.out.println("\n--- CONTACTOS REGISTRADOS ---");

        if (contactos.isEmpty()) {
            System.out.println("No hay contactos registrados.");
            return;
        }

        for (Contacto contacto : contactos) {

            System.out.println("\nID: " + contacto.getId());
            System.out.println("Nombre: " + contacto.getNombre());
            System.out.println("Medio: " + contacto.getMedioContacto());
            System.out.println("Estado: " + contacto.getEstado());
            System.out.println(
                    "Interacciones: "
                            + contacto.getInteracciones().size()
            );
            System.out.println(
                    "Seguimientos: "
                            + contacto.getSeguimientos().size()
            );
        }
    }

    // =========================
    //  SEGUIMIENTOS PENDIENTES
    // =========================

    private static void mostrarSeguimientosPendientes() {

        System.out.println("\n--- SEGUIMIENTOS PENDIENTES ---");

        boolean hayPendientes = false;

        for (Contacto contacto : contactos) {

            if (contacto.tieneSeguimientosPendientes()) {

                hayPendientes = true;

                System.out.println(
                        "\nContacto: "
                                + contacto.getNombre()
                                + " [" + contacto.getEstado() + "]"
                );

                for (Seguimiento seguimiento : contacto.getSeguimientos()) {

                    if (!seguimiento.isCompletado()) {
                        System.out.println(" - " + seguimiento);
                    }
                }
            }
        }

        if (!hayPendientes) {
            System.out.println("No hay seguimientos pendientes.");
        }
    }

    // =========================
    // BUSCAR CONTACTO
    // =========================

    private static Contacto buscarContacto(String id) {

        for (Contacto contacto : contactos) {

            if (contacto.getId().equalsIgnoreCase(id)) {
                return contacto;
            }
        }

        return null;
    }

    // =========================
    // LEER TEXTO
    // =========================

    private static String leerTexto(String mensaje) {

        System.out.print(mensaje);

        return scanner.nextLine().trim();
    }

    // =========================
    // LEER ENTERO
    // =========================

    private static int leerEntero(String mensaje) {

        while (true) {

            try {

                System.out.print(mensaje);

                return Integer.parseInt(scanner.nextLine());

            } catch (NumberFormatException e) {

                System.out.println(
                        "Ingrese solamente un número."
                );
            }
        }
    }

    // =========================
    // LEER FECHA
    // =========================

    private static LocalDate leerFecha(String mensaje) {

        while (true) {

            try {

                System.out.print(mensaje);

                return LocalDate.parse(scanner.nextLine());

            } catch (Exception e) {

                System.out.println(
                        "Formato incorrecto. Ejemplo: 2026-09-30"
                );
            }
        }
    }
}