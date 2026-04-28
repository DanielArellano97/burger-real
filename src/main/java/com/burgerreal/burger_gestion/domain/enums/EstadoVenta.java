package com.burgerreal.burger_gestion.domain.enums;

public enum EstadoVenta {
    PENDIENTE,   // Pedido tomado, pago no confirmado o burger no entregada.
    COMPLETADA,  // Dinero en caja y burger entregada.
    ANULADA      // El flujo se cortó (aquí es donde entra nuestra nueva tabla).
}
