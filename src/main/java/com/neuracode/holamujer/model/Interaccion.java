package com.neuracode.holamujer.model;

import java.time.LocalDateTime;//Importación para trabajar con el  tiempo 

public class Interaccion {
    private final LocalDateTime fechaHora;//guarda la fecha y hora del mensaje 
    private final String medio;//Guarda el medio de contacto 
    private final String detalle;//Guarda el resumen de la conversacion

    public Interaccion(String medio, String detalle) {
        if (medio == null || medio.trim().isEmpty()) {//Valida , recorta y verifica que medio (o detalle) no este vacio
            throw new IllegalArgumentException("El medio de interacción no puede estar vacío.");//Detiene la ejecucion si uno de los datos ingresados es invalido 
        }
        if (detalle == null || detalle.trim().isEmpty()) {
            throw new IllegalArgumentException("El detalle de la interacción no puede estar vacío.");
        }
        //Asignacion de valores 
        this.fechaHora = LocalDateTime.now();//Toma la fecha y  hora del mensaje
        this.medio = medio.trim();
        this.detalle = detalle.trim();
    }
    
   //Aplicacion GETTERS
    //Nos permite leer atributos privados desde otras clases 
 
    public LocalDateTime getFechaHora() { return fechaHora; }
    public String getMedio() { return medio; }
    public String getDetalle() { return detalle; }

    
 // Sirve para que, cuando hagamos un 'System.out.println(Interaccion)', Java imprima un texto legible
    @Override
    public String toString() {
        return "[" + fechaHora + "] (" + medio + "): " + detalle;
    }
}