package com.burgerreal.burger_gestion.domain.model;

public record ProductoInsumo(
        Long id,
        Insumo insumo,
        Double cantidad,
        boolean permiteQuitar,
        boolean permiteAgregar
) {

    public static ProductoInsumo crearProductoInsumo(Insumo insumo, Double cantidad, boolean permiteQuitar, boolean permiteAgregar){
        return new ProductoInsumo(null, insumo, cantidad, permiteQuitar, permiteAgregar);
    }

    // 🌟 NUEVO: Para cuando reconstruyes el objeto desde la BDD (Mappers de persistencia)
    public static ProductoInsumo reconstruirProductoInsumo(Long id, Insumo insumo, Double cantidad, boolean permiteQuitar, boolean permiteAgregar){
        return new ProductoInsumo(id, insumo, cantidad, permiteQuitar, permiteAgregar);
    }
}
