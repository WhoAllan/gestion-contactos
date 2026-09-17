package com.neuracode.holamujer.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Contacto {

    //Encapsulación
    private final String id;            // Código único del contacto (ej. "C001").
    private String nombre;             // Nombre del paciente/usuario.
    private String medioContacto;      // Teléfono, correo o WhatsApp de contacto.
    private EstadoContacto estado;     // Enum con el estado actual (NUEVO, EN_ATENCION, PENDIENTE, CERRADO).

    // RELACIONES / COMPOSICIÓN:
    // Un contacto almacena su historial mediante listas dinámicas .
    private final List<Interaccion> interacciones;
    private final List<Seguimiento> seguimientos;

    // CONSTRUCTOR
    public Contacto(String id, String nombre, String medioContacto) {
        
        //Validaciones de reglas del negocio:
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("El ID del contacto no puede estar vacío.");//Valida , recorta y verifica que (ID,Nombre,medioContacto) no este vacio
        }
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del contacto no puede estar vacío.");
        }
        if (medioContacto == null || medioContacto.trim().isEmpty()) {
            throw new IllegalArgumentException("El medio de contacto principal es obligatorio.");
        }

        this.id = id.trim();
        this.nombre = nombre.trim();
        this.medioContacto = medioContacto.trim();
        
        // Estado inicial por defecto cuando el contacto recién llega al sistema.
        this.estado = EstadoContacto.NUEVO;
        
        // Inicializamos las listas vacías para evitar errores de tipo 'NullPointerException'(Error de ejecucion )
        this.interacciones = new ArrayList<>();
        this.seguimientos = new ArrayList<>();
    }

    //Metodosde negocio y comportamiento

    // Registra una conversación y actualiza el estado de NUEVO a EN_ATENCION.
    public void registrarInteraccion(String medio, String detalle) {
        Interaccion interaccion = new Interaccion(medio, detalle);
        this.interacciones.add(interaccion);
        
        if (this.estado == EstadoContacto.NUEVO) {
            this.estado = EstadoContacto.EN_ATENCION;
        }
    }

    // Programa un seguimiento y actualiza el estado a PENDIENTE si no está cerrado.
    public void agregarSeguimiento(LocalDate fecha, String descripcion) {
        Seguimiento seguimiento = new Seguimiento(fecha, descripcion);
        this.seguimientos.add(seguimiento);
        
        if (this.estado != EstadoContacto.CERRADO) {
            this.estado = EstadoContacto.PENDIENTE;
        }
    }

    // Comprueba si hay alguna tarea en la lista que no esté marcada como completada.
    public boolean tieneSeguimientosPendientes() {
        return seguimientos.stream().anyMatch(s -> !s.isCompletado());
    }

    // Permite cambiar el estado del contacto de forma explícita con validación.
    public void cambiarEstado(EstadoContacto nuevoEstado) {
        if (nuevoEstado == null) {
            throw new IllegalArgumentException("El estado no puede ser nulo.");
        }
        this.estado = nuevoEstado;
    }

    // Aplicacion de Getters
    public String getId() { return id; }
    public String getNombre() { return nombre; }
    public String getMedioContacto() { return medioContacto; }
    public EstadoContacto getEstado() { return estado; }

    // BUENA PRÁCTICA DE ENCAPSULACIÓN:
    // Retornamos listas no modificables para evitar que desde fuera agreguen o borren
    // elementos directamente sin pasar por los métodos controladores de la clase.
    public List<Interaccion> getInteracciones() { 
        return Collections.unmodifiableList(interacciones); 
    }

    public List<Seguimiento> getSeguimientos() { 
        return Collections.unmodifiableList(seguimientos); 
    }

    //Representacion en texto 
    @Override
    public String toString() {
        return "Contacto{" +
                "id='" + id + '\'' +
                ", nombre='" + nombre + '\'' +
                ", medio='" + medioContacto + '\'' +
                ", estado=" + estado +
                ", seguimientos=" + seguimientos.size() +
                '}';
    }
}