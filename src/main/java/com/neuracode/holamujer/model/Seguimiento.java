package com.neuracode.holamujer.model;

import java.time.LocalDate;

public class Seguimiento {
	
	// Encapsulación
	
    private final LocalDate fechaProgramada;  // Usamos 'final'(no se podra modificar una vez iniciada la tarea ) en fecha y descripción
    private final String descripcion;
    private boolean completado;

    public Seguimiento(LocalDate fechaProgramada, String descripcion) {
        if (fechaProgramada == null) {
            throw new IllegalArgumentException("La fecha programada no puede ser nula.");
        }
        if (descripcion == null || descripcion.trim().isEmpty()) {//Valida , recorta y verifica que descripcion o fecha programada  no este vacio
            throw new IllegalArgumentException("La descripción del seguimiento no puede estar vacía.");//Detiene la ejecucion si uno de los datos ingresados es invalido
        }
        //Asignacion de valores 
        this.fechaProgramada = fechaProgramada;
        this.descripcion = descripcion.trim();
        this.completado = false;//Todo seguimiento nuevo nace como ("PENDIENTE" = falso) por defecto.
    }
//Aplicacion de Getters
    public LocalDate getFechaProgramada() { return fechaProgramada; }
    public String getDescripcion() { return descripcion; }
    // Los 'Getters' de variables booleanas no empiezan con "get", sino con "is" (es/está).
    public boolean isCompletado() { return completado; }
    
 //Comportamiento
 // En lugar de hacer un "setCompletado(true)", creamos un método con un nombre
 // más descriptivo según la lógica del negocio.
    public void marcarComoCompletado() { this.completado = true; }

    @Override
    public String toString() {
        // Si 'completado' es true, imprime "COMPLETADO", si es false, imprime "PENDIENTE".
        return "Fecha: " + fechaProgramada + " | Desc: " + descripcion + " | Estado: " + (completado ? "COMPLETADO" : "PENDIENTE");
    }
}