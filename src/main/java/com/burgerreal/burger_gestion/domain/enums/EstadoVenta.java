package com.burgerreal.burger_gestion.domain.enums;

public enum EstadoVenta {
    PENDIENTE,          // Venta creada pero aun no comienza preparacion.
    EN_COCINA,          // Venta creada que ya fue tomada por cocina
    LISTO_PARA_ENTREGA, // Venta creeda que ya fue realiaz, embolsada y que esta lista para ser entregada.
    COMPLETADA,         // Dinero en caja y burger entregada.
    ANULADA             // Venta anulada, puede ser anulada una vez creada (claramente) y en los estados PENDIENTE, EN_COCINA y LISTA_PARA_ENTREGA
}
