package com.example.AppEntidadFinanciera.service.Implement;

import com.example.AppEntidadFinanciera.DTO.RequestProductoDTO;
import com.example.AppEntidadFinanciera.mapper.RequestMapperDTO;
import com.example.AppEntidadFinanciera.models.ProductoFinanciero;
import com.example.AppEntidadFinanciera.repository.ClienteRepository;
import com.example.AppEntidadFinanciera.repository.ProducFinancieroRepository;
import com.example.AppEntidadFinanciera.service.IProductoFinancieroService;

import java.util.List;

public class ProductoFinancieroService implements IProductoFinancieroService {
    private final ProducFinancieroRepository producFinancieroRepository;

    public ProductoFinancieroService(ProducFinancieroRepository producFinancieroRepository) {
        this.producFinancieroRepository = producFinancieroRepository;
    }

    @Override
    public void createProductoFinanciero(RequestProductoDTO requestProductoDTO) {
        ProductoFinanciero productoFinanciero = RequestMapperDTO.productoToDto(requestProductoDTO);
        producFinancieroRepository.save(productoFinanciero);
    }

    @Override
    public ProductoFinanciero findProductoFinancieroById(Long Producto_Id) {
        return producFinancieroRepository.findById(Producto_Id).orElse(null);
    }

    @Override
    public List<ProductoFinanciero> findProducFinanByIdCliente(Long Cliente_Id) {
        return producFinancieroRepository.findProducFinanByIdCliente(Cliente_Id);
    }

    @Override
    public void updateProductoFinanciero(Long producto_Id, ProductoFinanciero productoFinanciero) {

    }

    @Override
    public void deleteProductoFinanciero(Long Producto_Id) {

    }
}
