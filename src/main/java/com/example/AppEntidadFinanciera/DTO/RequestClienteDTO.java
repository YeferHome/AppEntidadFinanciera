package com.example.AppEntidadFinanciera.DTO;

import jakarta.validation.constraints.Pattern;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class RequestClienteDTO {

    private String tipoIdentidad;
    private int numIdentidad;
    private String nombres;
    private String apellidos;
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$",
            message = "El correo electrónico debe tener un formato válido")
    private String correo;
    private LocalDate fechaNacimiento;

}
