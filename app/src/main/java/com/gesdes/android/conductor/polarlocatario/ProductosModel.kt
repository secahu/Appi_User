package com.gesdes.android.conductor.polarlocatario

import java.io.Serializable

class ProductosModel : Serializable {
    var PK: String? = null
    var PK_CATEGORIA: String? = null
    var CATEGORIA: String? = null
    var PK_TIENDA: String? = null
    var TIENDA: String? = null
    var PRODUCTO: String? = null
    var DESCRIPCION: String? = null
    var DETALLE: String? = null
    var STOCK = 0
    var PK_MEDIDA: String? = null
    var MEDIDA: String? = null
    var MEDIDA_DESCRIPCION: String? = null
    var CANTIDAD = 0.0
    var IMAGEN: String? = null
    var IMAGEN_TIENDA: String? = null
    var PRECIO = 0.0
    var BORRADO: String? = null
    var FECHA_C: String? = null
    var FECHA_M: String? = null
    var FECHA_D: String? = null
    var USUARIO_C: String? = null
    var USUARIO_M: String? = null
    var USUARIO_D: String? = null
}