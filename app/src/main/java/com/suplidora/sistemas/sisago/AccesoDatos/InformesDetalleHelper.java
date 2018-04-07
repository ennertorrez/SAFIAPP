package com.suplidora.sistemas.sisago.AccesoDatos;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.suplidora.sistemas.sisago.Auxiliar.variables_publicas;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InformesDetalleHelper {


    private SQLiteDatabase database;

    public InformesDetalleHelper(SQLiteDatabase db) {
        database = db;
    }

    public void GuardarDetalleInforme(String CodInforme,
                                      String Recibo,
                                      String IdVendedor,
                                      String IdCliente,
                                      String Factura,
                                      String Saldo,
                                      String Monto,
                                      String Abono,
                                      String NoCheque,
                                      String BancoE,
                                      String BancoR,
                                      String FechaCK,
                                      String FechaDep,
                                      String Efectivo,
                                      String Moneda,
                                      String Aprobado,
                                      String Posfechado,
                                      String Procesado,
                                      String Usuario
    ) {
        long rows = 0;
        ContentValues contentValues = new ContentValues();
        contentValues.put(variables_publicas.DETALLEINFORMES_COLUMN_CodInforme, CodInforme);
        contentValues.put(variables_publicas.DETALLEINFORMES_COLUMN_Recibo, Recibo);
        contentValues.put(variables_publicas.DETALLEINFORMES_COLUMN_Idvendedor, IdVendedor);
        contentValues.put(variables_publicas.DETALLEINFORMES_COLUMN_IdCliente, IdCliente);
        contentValues.put(variables_publicas.DETALLEINFORMES_COLUMN_Factura, Factura);
        contentValues.put(variables_publicas.DETALLEINFORMES_COLUMN_Saldo, Saldo);
        contentValues.put(variables_publicas.DETALLEINFORMES_COLUMN_Monto, Monto);
        contentValues.put(variables_publicas.DETALLEINFORMES_COLUMN_Abono, Abono);
        contentValues.put(variables_publicas.DETALLEINFORMES_COLUMN_NoCheque, NoCheque);
        contentValues.put(variables_publicas.DETALLEINFORMES_COLUMN_BancoE, BancoE);
        contentValues.put(variables_publicas.DETALLEINFORMES_COLUMN_BancoR, BancoR);
        contentValues.put(variables_publicas.DETALLEINFORMES_COLUMN_FechaCK, FechaCK);
        contentValues.put(variables_publicas.DETALLEINFORMES_COLUMN_FechaDep, FechaDep);
        contentValues.put(variables_publicas.DETALLEINFORMES_COLUMN_Efectivo, Efectivo);
        contentValues.put(variables_publicas.DETALLEINFORMES_COLUMN_Moneda, Moneda);
        contentValues.put(variables_publicas.DETALLEINFORMES_COLUMN_Aprobado, Aprobado);
        contentValues.put(variables_publicas.DETALLEINFORMES_COLUMN_Posfechado, Posfechado);
        contentValues.put(variables_publicas.DETALLEINFORMES_COLUMN_Procesado, Procesado);
        contentValues.put(variables_publicas.DETALLEINFORMES_COLUMN_Usuario, Usuario);
        database.insert(variables_publicas.TABLE_DETALLE_INFORMES, null, contentValues);
    }


    public boolean GuardarDetalleInforme(HashMap<String,String> lstRecibos) {
        long rows = 0;
        ContentValues contentValues = new ContentValues();
        contentValues.put(variables_publicas.DETALLEINFORMES_COLUMN_CodInforme, lstRecibos.get(variables_publicas.DETALLEINFORMES_COLUMN_CodInforme));
        contentValues.put(variables_publicas.DETALLEINFORMES_COLUMN_Recibo, lstRecibos.get(variables_publicas.DETALLEINFORMES_COLUMN_Recibo));
        contentValues.put(variables_publicas.DETALLEINFORMES_COLUMN_Idvendedor, lstRecibos.get(variables_publicas.DETALLEINFORMES_COLUMN_Idvendedor));
        contentValues.put(variables_publicas.DETALLEINFORMES_COLUMN_IdCliente, lstRecibos.get(variables_publicas.DETALLEINFORMES_COLUMN_IdCliente));
        contentValues.put(variables_publicas.DETALLEINFORMES_COLUMN_Factura, lstRecibos.get(variables_publicas.DETALLEINFORMES_COLUMN_Factura));
        contentValues.put(variables_publicas.DETALLEINFORMES_COLUMN_Saldo, lstRecibos.get(variables_publicas.DETALLEINFORMES_COLUMN_Saldo));
        contentValues.put(variables_publicas.DETALLEINFORMES_COLUMN_Monto, lstRecibos.get(variables_publicas.DETALLEINFORMES_COLUMN_Monto));
        contentValues.put(variables_publicas.DETALLEINFORMES_COLUMN_Abono, lstRecibos.get(variables_publicas.DETALLEINFORMES_COLUMN_Abono));
        contentValues.put(variables_publicas.DETALLEINFORMES_COLUMN_NoCheque, lstRecibos.get(variables_publicas.DETALLEINFORMES_COLUMN_NoCheque));
        contentValues.put(variables_publicas.DETALLEINFORMES_COLUMN_BancoE, lstRecibos.get(variables_publicas.DETALLEINFORMES_COLUMN_BancoE));
        contentValues.put(variables_publicas.DETALLEINFORMES_COLUMN_BancoR, lstRecibos.get(variables_publicas.DETALLEINFORMES_COLUMN_BancoR));
        contentValues.put(variables_publicas.DETALLEINFORMES_COLUMN_FechaCK, lstRecibos.get(variables_publicas.DETALLEINFORMES_COLUMN_FechaCK));
        contentValues.put(variables_publicas.DETALLEINFORMES_COLUMN_FechaDep, lstRecibos.get(variables_publicas.DETALLEINFORMES_COLUMN_FechaDep));
        contentValues.put(variables_publicas.DETALLEINFORMES_COLUMN_Efectivo, lstRecibos.get(variables_publicas.DETALLEINFORMES_COLUMN_Efectivo));
        contentValues.put(variables_publicas.DETALLEINFORMES_COLUMN_Moneda, lstRecibos.get(variables_publicas.DETALLEINFORMES_COLUMN_Moneda));
        contentValues.put(variables_publicas.DETALLEINFORMES_COLUMN_Aprobado, lstRecibos.get(variables_publicas.DETALLEINFORMES_COLUMN_Aprobado));
        contentValues.put(variables_publicas.DETALLEINFORMES_COLUMN_Posfechado, lstRecibos.get(variables_publicas.DETALLEINFORMES_COLUMN_Posfechado));
        contentValues.put(variables_publicas.DETALLEINFORMES_COLUMN_Procesado, lstRecibos.get(variables_publicas.DETALLEINFORMES_COLUMN_Procesado));
        contentValues.put(variables_publicas.DETALLEINFORMES_COLUMN_Usuario, lstRecibos.get(variables_publicas.DETALLEINFORMES_COLUMN_Usuario));
        long rowInserted=database.insert(variables_publicas.TABLE_DETALLE_INFORMES, null, contentValues);
        if(rowInserted != -1)
           return true;
        else return false;
    }

    public List<HashMap<String, String>> ObtenerInformeDetalle(String idInforme) {
        List<HashMap<String,String>> lst= new ArrayList<>();
        Cursor c = database.rawQuery("SELECT  * FROM " + variables_publicas.TABLE_DETALLE_INFORMES + " WHERE " + variables_publicas.DETALLEINFORMES_COLUMN_CodInforme + " = ? ", new String[]{idInforme});
        if (c.moveToFirst()) {
            do {
                HashMap<String, String> detalle = new HashMap<>();
                detalle.put(variables_publicas.DETALLEINFORMES_COLUMN_CodInforme, c.getString(c.getColumnIndex(variables_publicas.DETALLEINFORMES_COLUMN_CodInforme)));
                detalle.put(variables_publicas.DETALLEINFORMES_COLUMN_Recibo, c.getString(c.getColumnIndex(variables_publicas.DETALLEINFORMES_COLUMN_Recibo)));
                detalle.put(variables_publicas.DETALLEINFORMES_COLUMN_Idvendedor, c.getString(c.getColumnIndex(variables_publicas.DETALLEINFORMES_COLUMN_Idvendedor)));
                detalle.put(variables_publicas.DETALLEINFORMES_COLUMN_IdCliente, c.getString(c.getColumnIndex(variables_publicas.DETALLEINFORMES_COLUMN_IdCliente)));
                detalle.put(variables_publicas.DETALLEINFORMES_COLUMN_Factura, c.getString(c.getColumnIndex(variables_publicas.DETALLEINFORMES_COLUMN_Factura)));
                detalle.put(variables_publicas.DETALLEINFORMES_COLUMN_Saldo, c.getString(c.getColumnIndex(variables_publicas.DETALLEINFORMES_COLUMN_Saldo)));
                detalle.put(variables_publicas.DETALLEINFORMES_COLUMN_Monto, c.getString(c.getColumnIndex(variables_publicas.DETALLEINFORMES_COLUMN_Monto)));
                detalle.put(variables_publicas.DETALLEINFORMES_COLUMN_Abono, c.getString(c.getColumnIndex(variables_publicas.DETALLEINFORMES_COLUMN_Abono)));
                detalle.put(variables_publicas.DETALLEINFORMES_COLUMN_NoCheque, c.getString(c.getColumnIndex(variables_publicas.DETALLEINFORMES_COLUMN_NoCheque)));
                detalle.put(variables_publicas.DETALLEINFORMES_COLUMN_BancoE, c.getString(c.getColumnIndex(variables_publicas.DETALLEINFORMES_COLUMN_BancoE)));
                detalle.put(variables_publicas.DETALLEINFORMES_COLUMN_BancoR, c.getString(c.getColumnIndex(variables_publicas.DETALLEINFORMES_COLUMN_BancoR)));
                detalle.put(variables_publicas.DETALLEINFORMES_COLUMN_FechaCK, c.getString(c.getColumnIndex(variables_publicas.DETALLEINFORMES_COLUMN_FechaCK)));
                detalle.put(variables_publicas.DETALLEINFORMES_COLUMN_FechaDep, c.getString(c.getColumnIndex(variables_publicas.DETALLEINFORMES_COLUMN_FechaDep)));
                detalle.put(variables_publicas.DETALLEINFORMES_COLUMN_Efectivo, c.getString(c.getColumnIndex(variables_publicas.DETALLEINFORMES_COLUMN_Efectivo)));
                detalle.put(variables_publicas.DETALLEINFORMES_COLUMN_Moneda, c.getString(c.getColumnIndex(variables_publicas.DETALLEINFORMES_COLUMN_Moneda)));
                detalle.put(variables_publicas.DETALLEINFORMES_COLUMN_Aprobado, c.getString(c.getColumnIndex(variables_publicas.DETALLEINFORMES_COLUMN_Aprobado)));
                detalle.put(variables_publicas.DETALLEINFORMES_COLUMN_Posfechado, c.getString(c.getColumnIndex(variables_publicas.DETALLEINFORMES_COLUMN_Posfechado)));
                detalle.put(variables_publicas.DETALLEINFORMES_COLUMN_Procesado, c.getString(c.getColumnIndex(variables_publicas.DETALLEINFORMES_COLUMN_Procesado)));
                detalle.put(variables_publicas.DETALLEINFORMES_COLUMN_Usuario, c.getString(c.getColumnIndex(variables_publicas.DETALLEINFORMES_COLUMN_Usuario)));
                lst.add(detalle);
            } while (c.moveToNext());
        }
        c.close();
        return lst;
    }

    public ArrayList<HashMap<String, String>> ObtenerInformeDetalleArrayList(String idInforme) {
        ArrayList<HashMap<String,String>> lst= new ArrayList<>();
        Cursor c = database.rawQuery("SELECT  * FROM " + variables_publicas.TABLE_DETALLE_INFORMES + " WHERE " + variables_publicas.DETALLEINFORMES_COLUMN_CodInforme + " = ? ", new String[]{idInforme});
        if (c.moveToFirst()) {
            do {
                HashMap<String, String> detalle = new HashMap<>();
                detalle.put(variables_publicas.DETALLEINFORMES_COLUMN_CodInforme, c.getString(c.getColumnIndex(variables_publicas.DETALLEINFORMES_COLUMN_CodInforme)));
                detalle.put(variables_publicas.DETALLEINFORMES_COLUMN_Recibo, c.getString(c.getColumnIndex(variables_publicas.DETALLEINFORMES_COLUMN_Recibo)));
                detalle.put(variables_publicas.DETALLEINFORMES_COLUMN_Idvendedor, c.getString(c.getColumnIndex(variables_publicas.DETALLEINFORMES_COLUMN_Idvendedor)));
                detalle.put(variables_publicas.DETALLEINFORMES_COLUMN_IdCliente, c.getString(c.getColumnIndex(variables_publicas.DETALLEINFORMES_COLUMN_IdCliente)));
                detalle.put(variables_publicas.DETALLEINFORMES_COLUMN_Factura, c.getString(c.getColumnIndex(variables_publicas.DETALLEINFORMES_COLUMN_Factura)));
                detalle.put(variables_publicas.DETALLEINFORMES_COLUMN_Saldo, c.getString(c.getColumnIndex(variables_publicas.DETALLEINFORMES_COLUMN_Saldo)));
                detalle.put(variables_publicas.DETALLEINFORMES_COLUMN_Monto, c.getString(c.getColumnIndex(variables_publicas.DETALLEINFORMES_COLUMN_Monto)));
                detalle.put(variables_publicas.DETALLEINFORMES_COLUMN_Abono, c.getString(c.getColumnIndex(variables_publicas.DETALLEINFORMES_COLUMN_Abono)));
                detalle.put(variables_publicas.DETALLEINFORMES_COLUMN_NoCheque, c.getString(c.getColumnIndex(variables_publicas.DETALLEINFORMES_COLUMN_NoCheque)));
                detalle.put(variables_publicas.DETALLEINFORMES_COLUMN_BancoE, c.getString(c.getColumnIndex(variables_publicas.DETALLEINFORMES_COLUMN_BancoE)));
                detalle.put(variables_publicas.DETALLEINFORMES_COLUMN_BancoR, c.getString(c.getColumnIndex(variables_publicas.DETALLEINFORMES_COLUMN_BancoR)));
                detalle.put(variables_publicas.DETALLEINFORMES_COLUMN_FechaCK, c.getString(c.getColumnIndex(variables_publicas.DETALLEINFORMES_COLUMN_FechaCK)));
                detalle.put(variables_publicas.DETALLEINFORMES_COLUMN_FechaDep, c.getString(c.getColumnIndex(variables_publicas.DETALLEINFORMES_COLUMN_FechaDep)));
                detalle.put(variables_publicas.DETALLEINFORMES_COLUMN_Efectivo, c.getString(c.getColumnIndex(variables_publicas.DETALLEINFORMES_COLUMN_Efectivo)));
                detalle.put(variables_publicas.DETALLEINFORMES_COLUMN_Moneda, c.getString(c.getColumnIndex(variables_publicas.DETALLEINFORMES_COLUMN_Moneda)));
                detalle.put(variables_publicas.DETALLEINFORMES_COLUMN_Aprobado, c.getString(c.getColumnIndex(variables_publicas.DETALLEINFORMES_COLUMN_Aprobado)));
                detalle.put(variables_publicas.DETALLEINFORMES_COLUMN_Posfechado, c.getString(c.getColumnIndex(variables_publicas.DETALLEINFORMES_COLUMN_Posfechado)));
                detalle.put(variables_publicas.DETALLEINFORMES_COLUMN_Procesado, c.getString(c.getColumnIndex(variables_publicas.DETALLEINFORMES_COLUMN_Procesado)));
                detalle.put(variables_publicas.DETALLEINFORMES_COLUMN_Usuario, c.getString(c.getColumnIndex(variables_publicas.DETALLEINFORMES_COLUMN_Usuario)));
                lst.add(detalle);
            } while (c.moveToNext());
        }
        c.close();
        return lst;
    }

    public void LimpiarInformeDetalle() {
        database.execSQL("DELETE FROM " + variables_publicas.TABLE_DETALLE_INFORMES + ";");
        Log.d("pedidos detalle_elimina", "Datos eliminados");
    }
    public boolean EliminarDetalleInforme(String idInforme) {
        int rowsAffected =database.delete( variables_publicas.TABLE_DETALLE_INFORMES, variables_publicas.DETALLEINFORMES_COLUMN_CodInforme+ "='" +idInforme+"'",null) ;
        if(rowsAffected != -1)
            return true;
        else return false;
    }

    public boolean ActualizarCodigoInforme(String idInforme, String NoInforme){
        ContentValues con = new ContentValues();
        con.put("CodInforme", NoInforme);
        long rowInserted= database.update(variables_publicas.TABLE_DETALLE_INFORMES, con, variables_publicas.DETALLEINFORMES_COLUMN_CodInforme +"='"+idInforme+"'", null );
        if(rowInserted != -1)
            return true;
        else return false;
    }
}