package com.suplidora.sistemas.Principal;

import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.suplidora.sistemas.AccesoDatos.ArticulosHelper;
import com.suplidora.sistemas.AccesoDatos.CartillasBcDetalleHelper;
import com.suplidora.sistemas.AccesoDatos.CartillasBcHelper;
import com.suplidora.sistemas.AccesoDatos.ClientesHelper;
import com.suplidora.sistemas.AccesoDatos.ClientesSucursalHelper;
import com.suplidora.sistemas.AccesoDatos.ConfiguracionSistemaHelper;
import com.suplidora.sistemas.AccesoDatos.DataBaseOpenHelper;
import com.suplidora.sistemas.AccesoDatos.FormaPagoHelper;
import com.suplidora.sistemas.AccesoDatos.PrecioEspecialHelper;
import com.suplidora.sistemas.AccesoDatos.UsuariosHelper;
import com.suplidora.sistemas.AccesoDatos.VendedoresHelper;
import com.suplidora.sistemas.Auxiliar.SincronizarDatos;
import com.suplidora.sistemas.Auxiliar.variables_publicas;
import com.suplidora.sistemas.HttpHandler;
import com.suplidora.sistemas.Menu.MapViewFragment;
import com.suplidora.sistemas.Menu.PedidosFragment;
import com.suplidora.sistemas.R;
import com.suplidora.sistemas.Menu.ClientesFragment;
import com.suplidora.sistemas.Menu.MaestroProductoFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
/*import com.suplidora.sistemas.app.ControladorArticulo;
import com.suplidora.sistemas.app.ControladorSincronizacion;*/


public class MenuActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private String TAG = Login.class.getSimpleName();

    private DataBaseOpenHelper DbOpenHelper;

    private UsuariosHelper UsuariosH;
    private ClientesHelper ClientesH;
    private VendedoresHelper VendedoresH;

    private CartillasBcHelper CartillasBcH;
    private CartillasBcDetalleHelper CartillasBcDetalleH;
    private FormaPagoHelper FormaPagoH;
    private PrecioEspecialHelper PrecioEspecialH;
    private ConfiguracionSistemaHelper ConfigH;
    private ClientesSucursalHelper ClientesSucH;
    private ArticulosHelper ArticulosH;
    private SincronizarDatos sd;

    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DbOpenHelper = new DataBaseOpenHelper(MenuActivity.this);
        ClientesH = new ClientesHelper(DbOpenHelper.database);
        UsuariosH = new UsuariosHelper(DbOpenHelper.database);
        VendedoresH = new VendedoresHelper(DbOpenHelper.database);
        ConfigH = new ConfiguracionSistemaHelper(DbOpenHelper.database);
        ClientesSucH = new ClientesSucursalHelper(DbOpenHelper.database);
        CartillasBcH = new CartillasBcHelper(DbOpenHelper.database);
        CartillasBcDetalleH = new CartillasBcDetalleHelper(DbOpenHelper.database);
        FormaPagoH = new FormaPagoHelper(DbOpenHelper.database);
        PrecioEspecialH = new PrecioEspecialHelper(DbOpenHelper.database);
        ArticulosH = new ArticulosHelper(DbOpenHelper.database);

        sd = new SincronizarDatos(DbOpenHelper, ClientesH, VendedoresH, CartillasBcH,
                CartillasBcDetalleH,
                FormaPagoH,
                PrecioEspecialH, ConfigH, ClientesSucH, ArticulosH);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


       TextView lblUsuarioHeader = (TextView) findViewById(R.id.UsuarioHeader);
        String Userheader = variables_publicas.UsuarioLogin;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private class SincronizaDatos extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(getApplicationContext());
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... arg0) {

            //SINCRONIZAR DATOS
            try {
                sd.SincronizarTodo();
            } catch (final JSONException e) {
                Log.e(TAG, "Json parsing error: " + e.getMessage());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Json parsing error: " + e.getMessage(),
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });
            }

            return null;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.SincronizarDatos) {
            new SincronizaDatos().execute();
        }
        //noinspection SimplifiableIfStatement
        if (id == R.id.Salir) {
            finish();//return true;
        }
        if (id == R.id.CerrarSesion) {
            Intent newAct = new Intent(getApplicationContext(), Login.class);
            newAct.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(newAct);//return true;
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        FragmentManager fragmentManager = getFragmentManager();

        switch (id) {

            case R.id.btnMaestroProductos:
                fragmentManager.beginTransaction()
                        .replace(R.id.content_frame, new MaestroProductoFragment())
                        .commit();
                break;
            case R.id.btnMapa:
                fragmentManager.beginTransaction()
                        .replace(R.id.content_frame, new MapViewFragment())
                        .commit();
                break;
            case R.id.btnClientes:
                fragmentManager.beginTransaction().replace(R.id.content_frame, new ClientesFragment()).commit();
                break;
            case R.id.btnDevoluciones:
                /*Intent newAct = new Intent(getApplicationContext(), ControladorSincronizacion.class);
                startActivity(newAct);*/
                //Intent newAct = new Intent(getApplicationContext(), AndroidJSONParsingActivit.class);
                //startActivity(newAct);
                break;
            case R.id.btnPedidos:
                /*Intent newAct = new Intent(getApplicationContext(), ControladorSincronizacion.class);
                startActivity(newAct);*/
                //Para pruebas
              /*  Intent newActi = new Intent(getApplicationContext(), onsultaArticulosActivity.class);
                startActivity(newActi);*/

                fragmentManager.beginTransaction().replace(R.id.content_frame, new PedidosFragment()).commit();
                break;
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
