package com.example.AppEntidadFinanciera.service.Implement;

import com.example.AppEntidadFinanciera.DTO.RequestClienteDTO;
import com.example.AppEntidadFinanciera.mapper.RequestMapperDTO;
import com.example.AppEntidadFinanciera.models.Cliente;
import com.example.AppEntidadFinanciera.repository.ClienteRepository;
import com.example.AppEntidadFinanciera.service.IClienteService;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ClienteService implements IClienteService {

    private final ClienteRepository clienteRepository;
    private final RequestMapperDTO requestMapperDTO;

    @Override
    public void createCliente(RequestClienteDTO requestClienteDTO) {
        LocalDate fechaNacimiento = requestClienteDTO.getFechaNacimiento();
        LocalDate fechaHoy = LocalDate.now();

        Period edad = Period.between(fechaNacimiento, fechaHoy);

        if (edad.getYears() < 18) {
            throw new ConstraintViolationException("El cliente debe tener al menos 18 años", null);
        }
        Cliente cliente = RequestMapperDTO.clienteToDto(requestClienteDTO);
        cliente.setFechaCreacion(LocalDateTime.now());
        clienteRepository.save(cliente);
    }

    @Override
    public Cliente findClienteById(Long cliente_Id) {
        return clienteRepository.findById(cliente_Id).orElse(null);
    }

    @Override
    public List<Cliente> findAllClientes() {
        return clienteRepository.findAll();
    }

    @Override
    public void updateCliente(Long cliente_Id, RequestClienteDTO updateClienteDTO) {
        Cliente cliente = clienteRepository.findById(cliente_Id).orElse(null);
        Cliente clienteActualizado = requestMapperDTO.clienteToDto(updateClienteDTO);
        cliente.setTipoIdentidad(clienteActualizado.getTipoIdentidad());
        cliente.setNumIdentidad(clienteActualizado.getNumIdentidad());
        cliente.setNombres(clienteActualizado.getNombres());
        cliente.setApellidos(clienteActualizado.getApellidos());
        cliente.setCorreo(clienteActualizado.getCorreo());
        cliente.setFechaNacimiento(clienteActualizado.getFechaNacimiento());
        cliente.setFechaModificacion(LocalDateTime.now());
        clienteRepository.save(cliente);
    }

    @Override
    public void deleteCliente(Long cliente_Id) {
        clienteRepository.deleteById(cliente_Id);
    }
}
