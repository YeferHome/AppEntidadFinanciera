package com.example.AppEntidadFinanciera.service;

import com.example.AppEntidadFinanciera.DTO.RequestProductoDTO;
import com.example.AppEntidadFinanciera.models.Cliente;
import com.example.AppEntidadFinanciera.models.ProductoFinanciero;

import java.util.List;

public interface IProductoFinancieroService {

    void createProductoFinanciero(RequestProductoDTO requestProductoDTO);

    ProductoFinanciero findProductoFinancieroById (Long Producto_Id);

    List<ProductoFinanciero> findProducFinanByIdCliente(Long Cliente_Id);

    void updateProductoFinanciero(Long producto_Id, ProductoFinanciero productoFinanciero);

    void deleteProductoFinanciero(Long Producto_Id);

}
