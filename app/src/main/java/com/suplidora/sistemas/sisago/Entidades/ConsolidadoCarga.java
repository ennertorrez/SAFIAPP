package com.suplidora.sistemas.sisago.Entidades;

/**
 * Created by Sistemas on 6/5/2017.
 */

public class ConsolidadoCarga {

    String IdConsolidado = "";
    String Factura = "";
    String Cliente = "";
    String Vendedor = "";
    String Direccion = "";

    public ConsolidadoCarga() {
    }

    public ConsolidadoCarga(String idConsolidado){
        this.IdConsolidado = idConsolidado;
    }
    public ConsolidadoCarga(String idConsolidado, String factura, String cliente, String vendedor, String direccion) {
        this.IdConsolidado = idConsolidado;
       this.Factura = factura;
        this.Cliente = cliente;
        this.Vendedor = vendedor;
        this.Direccion = direccion;
    }

    public String getIdConsolidado() {
        return IdConsolidado;
    }

    public void setIdConsolidado(String idConsolidado) {
        IdConsolidado = idConsolidado;
    }

    public String getFactura() {
        return Factura;
    }

    public void setFactura(String factura) {
        Factura = factura;
    }

    public String getCliente() {
        return Cliente;
    }

    public void setCliente(String cliente) {
        Cliente = cliente;
    }

    public String getVendedor() {
        return Vendedor;
    }

    public void setVendedor(String vendedor) {
        Vendedor = vendedor;
    }

    public String getDireccion() {
        return Direccion;
    }

    public void setDireccion(String direccion) {
        Direccion = direccion;
    }

    public String toString(){
        return this.getIdConsolidado();
    }
}
