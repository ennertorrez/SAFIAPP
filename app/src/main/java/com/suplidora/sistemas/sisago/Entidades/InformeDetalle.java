package com.suplidora.sistemas.sisago.Entidades;

/**
 * Created by Sistemas on 16/3/2018
 */

public class InformeDetalle {

    String CodInforme ="";
    String Recibo ="";
    String Idvendedor ="";
    String IdCliente ="";
    String Factura ="";
    String Saldo="";
    String Monto="";
    String Abono ="";
    String NoCheque ="";
    String BancoE ="";
    String BancoR="";
    String FechaCK="";
    String FechaDep="";
    String Efectivo="";
    String Moneda="";
    String Aprobado ="";
    String Posfechado ="";
    String Procesado ="";
    String Usuario ="";

    public InformeDetalle() {
    }
    public InformeDetalle(String codigoInf, String recibo, String idvendedor, String idCliente, String factura, String saldo, String monto, String abono, String noCheque,
                          String bancoE, String bancoR, String fechaCK, String fechaDep, String efectivo, String moneda, String aprobado, String posfechado, String procesado, String usuario) {
        CodInforme = codigoInf;
        Recibo = recibo;
        Idvendedor = idvendedor;
        IdCliente = idCliente;
        Factura=factura;
        Saldo = saldo;
        Monto = monto;
        Abono = abono;
        NoCheque = noCheque;
        BancoE = bancoE;
        BancoR = bancoR;
        FechaCK = fechaCK;
        FechaDep = fechaDep;
        Efectivo = efectivo;
        Moneda=moneda;
        Aprobado=aprobado;
        Posfechado = posfechado;
        Procesado=procesado;
        Usuario=usuario;
    }

    public String getCodInforme() {
        return CodInforme;
    }

    public void setCodInforme(String codInforme) {
        CodInforme = codInforme;
    }

    public String getRecibo() {
        return Recibo;
    }

    public void setRecibo(String recibo) {
        Recibo = recibo;
    }

    public String getIdvendedor() {
        return Idvendedor;
    }

    public void setIdvendedor(String idvendedor) {
        Idvendedor = idvendedor;
    }

    public String getIdCliente() {
        return IdCliente;
    }

    public void setIdCliente(String idCliente) {
        IdCliente = idCliente;
    }

    public String getFactura() {
        return Factura;
    }

    public void setFactura(String factura) {
        Factura = factura;
    }

    public String getSaldo() {
        return Saldo;
    }

    public void setSaldo(String saldo) {
        Saldo = saldo;
    }
    public String getMonto() {
        return Monto;
    }

    public void setMonto(String monto) {
        Monto = monto;
    }

    public String getAbono() {
        return Abono;
    }

    public void setAbono(String abono) {
        Abono = abono;
    }

    public String getNoCheque() {
        return NoCheque;
    }

    public void setNoCheque(String noCheque) {
        NoCheque = noCheque;
    }

    public String getBancoE() {
        return BancoE;
    }

    public void setBancoE(String bancoE) {
        BancoE = bancoE;
    }

    public String getBancoR() {
        return BancoR;
    }

    public void setBancoR(String bancoR) {
        BancoR = bancoR;
    }

    public String getFechaCK() {
        return FechaCK;
    }

    public void setFechaCK(String fechaCK) {
        FechaCK = fechaCK;
    }

    public String getFechaDep() {
        return FechaDep;
    }

    public void setFechaDep(String fechaDep) {
        FechaDep = fechaDep;
    }

    public String getEfectivo() {
        return Efectivo;
    }

    public void setEfectivo(String efectivo) {
        Efectivo = efectivo;
    }

    public String getMoneda() {
        return Moneda;
    }

    public void setMoneda(String moneda) {
        Moneda = moneda;
    }

    public String getAprobado() {
        return Aprobado;
    }

    public void setAprobado(String aprobado) {
        Aprobado = aprobado;
    }

    public String getPosfechado() {
        return Posfechado;
    }

    public void setPosfechado(String posfechado) {
        Posfechado = posfechado;
    }

    public String getProcesado() {
        return Procesado;
    }

    public void setProcesado(String procesado) {
        Procesado = procesado;
    }

    public String getUsuario() {
        return Usuario;
    }

    public void setUsuario(String usuario) {
        Usuario = usuario;
    }
}
