package com.suplidora.sistemas.Entidades;

/**
 * Created by Sistemas on 6/5/2017.
 */

public class Cliente {

    String IdCliente = "";
    String CodCv= "";
    String Nombre= "";
    String FechaCreacion = "";
    String Telefono = "";
    String Direccion = "";
    String IdDepartamento= "";
    String IdMunicipio = "";
    String Ciudad = "";
    String Ruc= "";
    String Cedula= "";
    String LimiteCredito= "";
    String IdFormaPago= "";
    String IdVendedor= "";
    String Excento= "";
    String CodigoLetra= "";
    String Ruta= "";
    String Frecuencia= "";
    String PrecioEspecial= "";
    String FechaUltimaCompra= "";

    public Cliente() {

    }

    public Cliente(String idCliente, String codCv, String nombre, String fechaCreacion, String telefono, String direccion, String idDepartamento, String idMunicipio, String ciudad, String ruc, String cedula, String limiteCredito, String idFormaPago, String idVendedor, String excento, String codigoLetra, String ruta, String frecuencia, String precioEspecial, String fechaUltimaCompra) {
        IdCliente = idCliente;
        CodCv = codCv;
        Nombre = nombre;
        FechaCreacion = fechaCreacion;
        Telefono = telefono;
        Direccion = direccion;
        IdDepartamento = idDepartamento;
        IdMunicipio = idMunicipio;
        Ciudad = ciudad;
        Ruc = ruc;
        Cedula = cedula;
        LimiteCredito = limiteCredito;
        IdFormaPago = idFormaPago;
        IdVendedor = idVendedor;
        Excento = excento;
        CodigoLetra = codigoLetra;
        Ruta = ruta;
        Frecuencia = frecuencia;
        PrecioEspecial = precioEspecial;
        FechaUltimaCompra = fechaUltimaCompra;
    }

    public String getIdCliente() {
        return IdCliente;
    }

    public void setIdCliente(String idCliente) {
        IdCliente = idCliente;
    }

    public String getCodCv() {
        return CodCv;
    }

    public void setCodCv(String codCv) {
        CodCv = codCv;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getFechaCreacion() {
        return FechaCreacion;
    }

    public void setFechaCreacion(String fechaCreacion) {
        FechaCreacion = fechaCreacion;
    }

    public String getTelefono() {
        return Telefono;
    }

    public void setTelefono(String telefono) {
        Telefono = telefono;
    }

    public String getDireccion() {
        return Direccion;
    }

    public void setDireccion(String direccion) {
        Direccion = direccion;
    }

    public String getIdDepartamento() {
        return IdDepartamento;
    }

    public void setIdDepartamento(String idDepartamento) {
        IdDepartamento = idDepartamento;
    }

    public String getIdMunicipio() {
        return IdMunicipio;
    }

    public void setIdMunicipio(String idMunicipio) {
        IdMunicipio = idMunicipio;
    }

    public String getCiudad() {
        return Ciudad;
    }

    public void setCiudad(String ciudad) {
        Ciudad = ciudad;
    }

    public String getRuc() {
        return Ruc;
    }

    public void setRuc(String ruc) {
        Ruc = ruc;
    }

    public String getCedula() {
        return Cedula;
    }

    public void setCedula(String cedula) {
        Cedula = cedula;
    }

    public String getLimiteCredito() {
        return LimiteCredito;
    }

    public void setLimiteCredito(String limiteCredito) {
        LimiteCredito = limiteCredito;
    }

    public String getIdFormaPago() {
        return IdFormaPago;
    }

    public void setIdFormaPago(String idFormaPago) {
        IdFormaPago = idFormaPago;
    }

    public int getIdVendedor() {
        return Integer.parseInt( IdVendedor);
    }

    public void setIdVendedor(String idVendedor) {
        IdVendedor = idVendedor;
    }

    public String getExcento() {
        return Excento;
    }

    public void setExcento(String excento) {
        Excento = excento;
    }

    public String getCodigoLetra() {
        return CodigoLetra;
    }

    public void setCodigoLetra(String codigoLetra) {
        CodigoLetra = codigoLetra;
    }

    public String getRuta() {
        return Ruta;
    }

    public void setRuta(String ruta) {
        Ruta = ruta;
    }

    public String getFrecuencia() {
        return Frecuencia;
    }

    public void setFrecuencia(String frecuencia) {
        Frecuencia = frecuencia;
    }

    public String getPrecioEspecial() {
        return PrecioEspecial;
    }

    public void setPrecioEspecial(String precioEspecial) {
        PrecioEspecial = precioEspecial;
    }

    public String getFechaUltimaCompra() {
        return FechaUltimaCompra;
    }

    public void setFechaUltimaCompra(String fechaUltimaCompra) {
        FechaUltimaCompra = fechaUltimaCompra;
    }

    public String toString(){

        return  this.getNombre();
    }
}