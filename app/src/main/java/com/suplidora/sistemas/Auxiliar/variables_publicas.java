package com.suplidora.sistemas.Auxiliar;


import com.suplidora.sistemas.Entidades.Configuraciones;
import com.suplidora.sistemas.Entidades.Pedido;
import com.suplidora.sistemas.Entidades.Usuario;

public class variables_publicas {

    public static Usuario usuario=null;
    public static Configuraciones Configuracion=null;
    public static Pedido Pedidos=null;
    public static String ValorConfigServ="";
    public static String CodigoVendedor = "";
    public static String UsuarioLogin = "";
    public static String NombreVendedor = "";
    public static String TipoUsuario = "";
    public static boolean LoginOk = false;
    public static String MensajeLogin = "";
    public static String IdCliente = "";
    public static String RutaCliente = "";
    public static String Canal = "";
    public static String direccionIp = "http://186.1.18.75:8080";
    public static String FechaActual="";

    //Variables BD
    public static final int DATABASE_VERSION = 26;
    public static final String DATABASE_NAME = "SysContabv3.db";
    //Variables TB
    public static final String TABLE_ARTICULOS = "Articulos";
    public static final String TABLE_CLIENTES = "Cliente";
    public static final String TABLE_PEDIDOS = "Pedidos";
    public static final String TABLE_PEDIDOS_DETALLE = "PedidosDetalle";
    public static final String TABLE_USUARIOS = "Usuarios";
    public static final String TABLE_VENDEDORES = "Vendedor";
    public static final String TABLE_CLIENTES_SUCURSALES = "ClientesSucursales";
    public static final String TABLE_FORMA_PAGO = "FormaPago";
    public static final String TABLE_CARTILLAS_BC = "CartillasBC";
    public static final String TABLE_DETALLE_CARTILLAS_BC = "DetalleCartillasBC";
    public static final String TABLE_PRECIO_ESPECIAL = "ListaPrecioEspeciales";
    public static final String TABLE_CONFIGURACION_SISTEMA = "Configuraciones";

    //Variables CamposTbArticulos
    //public static final String ARTICULO_COLUMN_Id= "Id";
    public static final String ARTICULO_COLUMN_Codigo = "Codigo";
    public static final String ARTICULO_COLUMN_Nombre = "Nombre";
    public static final String ARTICULO_COLUMN_Costo = "Costo";
    public static final String ARTICULO_COLUMN_Unidad = "Unidad";
    public static final String ARTICULO_COLUMN_UnidadCaja = "UnidadCaja";
    public static final String ARTICULO_COLUMN_Isc = "Isc";
    public static final String ARTICULO_COLUMN_PorIva = "PorIva";
    public static final String ARTICULO_COLUMN_PrecioSuper = "PrecioSuper";
    public static final String ARTICULO_COLUMN_PrecioDetalle = "PrecioDetalle";
    public static final String ARTICULO_COLUMN_PrecioForaneo = "PrecioForaneo";
    public static final String ARTICULO_COLUMN_PrecioMayorista = "PrecioMayorista";
    public static final String ARTICULO_COLUMN_Bonificable = "Bonificable";
    public static final String ARTICULO_COLUMN_AplicaPrecioDetalle = "AplicaPrecioDetalle";
    public static final String ARTICULO_COLUMN_DescuentoMaximo= "DescuentoMaximo";
    public static final String ARTICULO_COLUMN_Detallista = "Detallista";

    //Variables CamposTbClientes
    public static final String CLIENTES_COLUMN_IdCliente = "IdCliente";
    public static final String CLIENTES_COLUMN_CodCv = "CodCv";
    public static final String CLIENTES_COLUMN_Nombre = "Nombre";
    public static final String CLIENTES_COLUMN_FechaCreacion = "FechaCreacion";
    public static final String CLIENTES_COLUMN_Telefono = "Telefono";
    public static final String CLIENTES_COLUMN_Direccion = "Direccion";
    public static final String CLIENTES_COLUMN_IdDepartamento = "IdDepartamento";
    public static final String CLIENTES_COLUMN_IdMunicipio = "IdMunicipio";
    public static final String CLIENTES_COLUMN_Ciudad = "Ciudad";
    public static final String CLIENTES_COLUMN_Ruc = "Ruc";
    public static final String CLIENTES_COLUMN_Cedula = "Cedula";
    public static final String CLIENTES_COLUMN_LimiteCredito = "LimiteCredito";
    public static final String CLIENTES_COLUMN_IdFormaPago = "IdFormaPago";
    public static final String CLIENTES_COLUMN_IdVendedor = "IdVendedor";
    public static final String CLIENTES_COLUMN_Excento = "Excento";
    public static final String CLIENTES_COLUMN_CodigoLetra = "CodigoLetra";
    public static final String CLIENTES_COLUMN_Ruta = "Ruta";
    public static final String CLIENTES_COLUMN_Frecuencia = "Frecuencia";
    public static final String CLIENTES_COLUMN_PrecioEspecial = "PrecioEspecial";
    public static final String CLIENTES_COLUMN_FechaUltimaCompra = "FechaUltimaCompra";
    public static final String CLIENTES_COLUMN_Tipo = "Tipo";
    public static final String CLIENTES_COLUMN_CodigoGalatea="CodigoGalatea";
    public static final String CLIENTES_COLUMN_Descuento="Descuento";
    public static final String CLIENTES_COLUMN_Empleado="Empleado";
    public static final String CLIENTES_COLUMN_Detallista="Detallista";

    //Variables CamposTbPedidos
    public static final String PEDIDOS_COLUMN_CodigoPedido = "CodigoPedido";
    public static final String PEDIDOS_COLUMN_IdVendedor = "IdVendedor";
    public static final String PEDIDOS_COLUMN_IdCliente = "IdCliente";
    public static final String PEDIDOS_COLUMN_Cod_cv = "Cod_cv";
    public static final String PEDIDOS_COLUMN_Observacion = "Observacion";
    public static final String PEDIDOS_COLUMN_IdFormaPago = "IdFormaPago";
    public static final String PEDIDOS_COLUMN_IdSucursal = "IdSucursal";
    public static final String PEDIDOS_COLUMN_Fecha = "Fecha";
    public static final String PEDIDOS_COLUMN_Usuario = "Usuario";
    public static final String PEDIDOS_COLUMN_IMEI = "IMEI";


    public static final String PEDIDOS_DETALLE_COLUMN_CodigoPedido = "CodigoPedido";
    public static final String PEDIDOS_DETALLE_COLUMN_CodigoArticulo = "CodigoArticulo";
    public static final String PEDIDOS_DETALLE_COLUMN_Descripcion = "Descripcion";
    public static final String PEDIDOS_DETALLE_COLUMN_Cantidad = "Cantidad";
    public static final String PEDIDOS_DETALLE_COLUMN_BonificaA = "BonificaA";
    public static final String PEDIDOS_DETALLE_COLUMN_TipoArt = "TipoArt";
    public static final String PEDIDOS_DETALLE_COLUMN_Descuento = "Descuento";
    public static final String PEDIDOS_DETALLE_COLUMN_PorDescuento = "PorDescuento";
    public static final String PEDIDOS_DETALLE_COLUMN_Isc = "Isc";
    public static final String PEDIDOS_DETALLE_COLUMN_Costo = "Costo";
    public static final String PEDIDOS_DETALLE_COLUMN_Precio = "Precio";
    public static final String PEDIDOS_DETALLE_COLUMN_PorcentajeIva = "PorcentajeIva";
    public static final String PEDIDOS_DETALLE_COLUMN_Iva = "Iva";
    public static final String PEDIDOS_DETALLE_COLUMN_Um = "Um";
    public static final String PEDIDOS_DETALLE_COLUMN_Subtotal = "SubTotal";
    public static final String PEDIDOS_DETALLE_COLUMN_Total = "Total";

    //Variables CamposUsuario
    public static final String USUARIOS_COLUMN_Codigo = "Codigo";
    public static final String USUARIOS_COLUMN_Nombre = "Nombre";
    public static final String USUARIOS_COLUMN_Usuario = "Usuario";
    public static final String USUARIOS_COLUMN_Contrasenia = "Contrasenia";
    public static final String USUARIOS_COLUMN_Tipo = "Tipo";
    public static final String USUARIOS_COLUMN_Ruta = "Ruta";
    public static final String USUARIOS_COLUMN_Canal = "Canal";
    public static final String USUARIOS_COLUMN_TasaCambio = "TasaCambio";
    public static final String USUARIOS_COLUMN_RutaForanea = "RutaForanea";
    public static final String USUARIOS_COLUMN_FechaActualiza = "FechaActualiza";

    public static final String VENDEDORES_COLUMN_CODIGO = "CODIGO";
    public static final String VENDEDORES_COLUMN_NOMBRE = "NOMBRE";
    public static final String VENDEDORES_COLUMN_COD_ZONA = "COD_ZONA";
    public static final String VENDEDORES_COLUMN_RUTA = "RUTA";
    public static final String VENDEDORES_COLUMN_codsuper = "codsuper";
    public static final String VENDEDORES_COLUMN_Status = "Status";
    public static final String VENDEDORES_COLUMN_detalle = "detalle";
    public static final String VENDEDORES_COLUMN_horeca = "horeca";
    public static final String VENDEDORES_COLUMN_mayorista = "mayorista";
    public static final String VENDEDORES_COLUMN_Super = "Super";

    public static final String CLIENTES_SUCURSALES_COLUMN_CodSuc = "CodSuc";
    public static final String CLIENTES_SUCURSALES_COLUMN_CodCliente = "CodCliente";
    public static final String CLIENTES_SUCURSALES_COLUMN_Sucursal = "Sucursal";
    public static final String CLIENTES_SUCURSALES_COLUMN_Ciudad = "Ciudad";
    public static final String CLIENTES_SUCURSALES_COLUMN_DeptoID = "DeptoID";
    public static final String CLIENTES_SUCURSALES_COLUMN_Direccion = "Direccion";
    public static final String CLIENTES_SUCURSALES_COLUMN_FormaPagoID = "FormaPagoID";

    public static final String FORMA_PAGO_COLUMN_CODIGO = "CODIGO";
    public static final String FORMA_PAGO_COLUMN_NOMBRE = "NOMBRE";
    public static final String FORMA_PAGO_COLUMN_DIAS = "DIAS";
    public static final String FORMA_PAGO_COLUMN_EMPRESA = "EMPRESA";

    public static final String CARTILLAS_BC_COLUMN_id = "id";
    public static final String CARTILLAS_BC_COLUMN_codigo = "codigo";
    public static final String CARTILLAS_BC_COLUMN_fechaini = "fechaini";
    public static final String CARTILLAS_BC_COLUMN_fechafinal = "fechafinal";
    public static final String CARTILLAS_BC_COLUMN_tipo = "tipo";
    public static final String CARTILLAS_BC_COLUMN_aprobado = "aprobado";

    public static final String CARTILLAS_BC_DETALLE_COLUMN_id = "id";
    public static final String CARTILLAS_BC_DETALLE_COLUMN_itemV = "itemV";
    public static final String CARTILLAS_BC_DETALLE_COLUMN_descripcionV = "descripcionV";
    public static final String CARTILLAS_BC_DETALLE_COLUMN_cantidad = "cantidad";
    public static final String CARTILLAS_BC_DETALLE_COLUMN_itemB = "itemB";
    public static final String CARTILLAS_BC_DETALLE_COLUMN_descripcionB = "descripcionB";
    public static final String CARTILLAS_BC_DETALLE_COLUMN_cantidadB = "cantidadB";
    public static final String CARTILLAS_BC_DETALLE_COLUMN_codigo = "codigo";
    public static final String CARTILLAS_BC_DETALLE_COLUMN_tipo = "tipo";
    public static final String CARTILLAS_BC_DETALLE_COLUMN_activo = "activo";

    public static final String PRECIO_ESPECIAL_COLUMN_Id = "Id";
    public static final String PRECIO_ESPECIAL_COLUMN_CodigoArticulo = "CodigoArticulo";
    public static final String PRECIO_ESPECIAL_COLUMN_IdCliente = "IdCliente";
    public static final String PRECIO_ESPECIAL_COLUMN_Descuento = "Descuento";
    public static final String PRECIO_ESPECIAL_COLUMN_Precio = "Precio";
    public static final String PRECIO_ESPECIAL_COLUMN_Facturar = "Facturar";

    public static final String CONFIGURACION_SISTEMA_COLUMN_Id = "Id";
    public static final String CONFIGURACION_SISTEMA_COLUMN_Sistema = "Sistema";
    public static final String CONFIGURACION_SISTEMA_COLUMN_Configuracion = "Configuracion";
    public static final String CONFIGURACION_SISTEMA_COLUMN_Valor = "Valor";
    public static final String CONFIGURACION_SISTEMA_COLUMN_Activo = "Activo";


}