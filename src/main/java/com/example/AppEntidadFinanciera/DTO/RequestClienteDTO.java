package com.example.AppEntidadFinanciera.DTO;

import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
public class RequestClienteDTO {

    private String tipoIdentidad;
    private String numIdentidad;
    private String nombres;
    private String apellidos;
    private int edad;
    private String correo;
    private LocalDate fechaNacimiento;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaModificacion;

}
