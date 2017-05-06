package com.suplidora.sistemas;

import android.os.Bundle;

import android.view.Menu;
import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.suplidora.sistemas.AccesoDatos.ArticulosHelper;
import com.suplidora.sistemas.AccesoDatos.ClientesHelper;
import com.suplidora.sistemas.AccesoDatos.DataBaseOpenHelper;
import com.suplidora.sistemas.AccesoDatos.UsuariosHelper;

public class ConsultaArticuloActivity extends Activity {

    private EditText txtCodigo;
    private EditText txtNombre;
    private TextView txtResultado;

    private Button btnInsertar;
    private Button btnActualizar;
    private Button btnEliminar;
    private Button btnConsultar;

    private DataBaseOpenHelper DbOpenHelper ;
    private UsuariosHelper UsuarioH;
    private ClientesHelper ClientesH;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.consulta_articulo_activity);

        //Obtenemos las referencias a los controles
        txtCodigo = (EditText)findViewById(R.id.txtReg);
        txtNombre = (EditText)findViewById(R.id.txtVal);
        txtResultado = (TextView)findViewById(R.id.txtResultado);

        btnInsertar = (Button)findViewById(R.id.btnInsertar);
        btnActualizar = (Button)findViewById(R.id.btnActualizar);
        btnEliminar = (Button)findViewById(R.id.btnEliminar);
        btnConsultar = (Button)findViewById(R.id.btnConsultar);

        DbOpenHelper = new DataBaseOpenHelper(ConsultaArticuloActivity.this);
        UsuarioH = new UsuariosHelper(DbOpenHelper.database);
        ClientesH = new ClientesHelper(DbOpenHelper.database);

        btnConsultar.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
//                String Codigo = "001",Usuario = "rm",Contrasenia ="aa";
//                Cursor c = UsuarioH.BuscarUsuariosCount();
//                //Recorremos los resultados para mostrarlos en pantalla
//                txtResultado.setText("");
//                if (c.moveToFirst()) {
////                    //Recorremos el cursor hasta que no haya más registros
//                    do {
//                        String cod = c.getString(0);
//                       //String nom = c.getString(1);
//                        txtResultado.append(" " + cod + "\n");
//                    } while(c.moveToNext());
//                }
                Cursor c = ClientesH.BuscarClientesCount();
                //Recorremos los resultados para mostrarlos en pantalla
                txtResultado.setText("");
                if (c.moveToFirst()) {
//                    //Recorremos el cursor hasta que no haya más registros
                    do {
                        String cod = c.getString(0);
                        //String nom = c.getString(1);
                        txtResultado.append(" " + cod + "\n");
                    } while(c.moveToNext());
                }
            }
        });
//        btnEliminar.setOnClickListener(new OnClickListener() {
//            public void onClick(View v) {
//                    usdbh.EliminaArticulos();
//            }
//        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

}