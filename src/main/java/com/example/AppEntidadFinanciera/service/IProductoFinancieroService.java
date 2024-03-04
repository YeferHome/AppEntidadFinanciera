package com.example.AppEntidadFinanciera.service;

import com.example.AppEntidadFinanciera.models.Cliente;
import com.example.AppEntidadFinanciera.models.ProductoFinanciero;

import java.util.List;

public interface IProductoFinancieroService {

    void createProductoFinanciero(Long Producto_Id, ProductoFinanciero productoFinanciero);

    ProductoFinanciero findProductoFinancieroById (Long Producto_Id);

    List<ProductoFinanciero> findAllProducFinanByIdCliente(Long Cliente_Id);

    void updateProductoFinanciero(Long producto_Id, ProductoFinanciero productoFinanciero);

    void deleteProductoFinanciero(Long Producto_Id);

}
