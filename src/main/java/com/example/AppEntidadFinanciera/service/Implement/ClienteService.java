package com.example.AppEntidadFinanciera.service.Implement;

import com.example.AppEntidadFinanciera.DTO.RequestClienteDTO;
import com.example.AppEntidadFinanciera.mapper.RequestMapperDTO;
import com.example.AppEntidadFinanciera.models.Cliente;
import com.example.AppEntidadFinanciera.repository.ClienteRepository;
import com.example.AppEntidadFinanciera.service.IClienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClienteService implements IClienteService {

    private final ClienteRepository clienteRepository;
    private final RequestMapperDTO requestMapperDTO;

    @Override
    public void createCliente(RequestClienteDTO requestClienteDTO) {
        Cliente saveInformation = RequestMapperDTO.dtoToRequestDto(requestClienteDTO);
        clienteRepository.save(saveInformation);
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
        Cliente clienteActualizado = requestMapperDTO.dtoToRequestDto(updateClienteDTO);
        cliente.setTipoIdentidad(clienteActualizado.getTipoIdentidad());
        cliente.setNumIdentidad(clienteActualizado.getNumIdentidad());
        cliente.setNombres(clienteActualizado.getNombres());
        cliente.setApellidos(clienteActualizado.getApellidos());
        cliente.setEdad(clienteActualizado.getEdad());
        cliente.setCorreo(clienteActualizado.getCorreo());
        cliente.setFechaNacimiento(clienteActualizado.getFechaNacimiento());
        cliente.setFechaCreacion(clienteActualizado.getFechaCreacion());
        cliente.setFechaModificacion(clienteActualizado.getFechaModificacion());
    }

    @Override
    public void deleteCliente(Long cliente_Id) {

    }
}
