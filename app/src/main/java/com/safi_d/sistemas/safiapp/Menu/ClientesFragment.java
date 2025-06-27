package com.safi_d.sistemas.safiapp.Menu;

import android.Manifest;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;

import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
//import android.widget.Toolbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.safi_d.sistemas.safiapp.AccesoDatos.ClientesHelper;
import com.safi_d.sistemas.safiapp.AccesoDatos.DataBaseOpenHelper;
import com.safi_d.sistemas.safiapp.Auxiliar.Funciones;
import com.safi_d.sistemas.safiapp.Auxiliar.variables_publicas;
import com.safi_d.sistemas.safiapp.Clientes.ClientesNew;
import com.safi_d.sistemas.safiapp.Entidades.MotivosNoVenta;
import com.safi_d.sistemas.safiapp.HttpHandler;
import com.safi_d.sistemas.safiapp.Pedidos.PedidosActivity;
import com.safi_d.sistemas.safiapp.R;

/**
 * Created by usuario on 20/3/2017.
 */

public class ClientesFragment extends Fragment {
    View myView;
    private String TAG = ClientesFragment.class.getSimpleName();
    private String busqueda = "1";
    private String tipoBusqueda = "1";
    private ProgressDialog pDialog;
    private ListView lv;
    private TextView lblFooter;
    private EditText txtBusqueda;
    private RadioGroup rgGrupo;
    private Button btnBuscar;
    private ClientesHelper ClienteH;
    private DataBaseOpenHelper DbOpenHelper;
    private  String ClienteId;
    private  String vLatitud;
    private  String vLongitud;
    private String Longitud="";
    private String Latitud="";
    private boolean guardadoOK=false;
    private TextView tvLocaliza;
    private final int PETICION_ACTIVITY_SEGUNDA = 1;
    private String vMotivoNoVenta = "";
    private String vObservacion="";
    private TextView tvVisita;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.masterclientes_layout, container, false);
        getActivity().setTitle("Listado Clientes");
        DbOpenHelper = new DataBaseOpenHelper(getActivity().getApplicationContext());
        ClienteH = new ClientesHelper(DbOpenHelper.database);
        lv = (ListView) myView.findViewById(R.id.list);
        registerForContextMenu(lv);
        btnBuscar = (Button) myView.findViewById(R.id.btnBuscar);
        LayoutInflater inflate = getActivity().getLayoutInflater();
        View dialogView = inflate.inflate(R.layout.list_cliente, null);
        tvLocaliza = (TextView) dialogView.findViewById(R.id.tvLocalizadorMaster);
        lblFooter = (TextView) myView.findViewById(R.id.lblFooter);
        rgGrupo = (RadioGroup) myView.findViewById(R.id.rgGrupo);
        txtBusqueda = (EditText) myView.findViewById(R.id.txtBusqueda);

        listaClientes = new ArrayList<>();
        lv.setOnItemClickListener((parent, view, position, id) -> {
        });
 /*       lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // getting values from selected ListItem
                String IdCliente = ((TextView) view.findViewById(R.id.IdCliente)).getText().toString();
                String Nombre = ((TextView) view.findViewById(R.id.Nombre)).getText().toString();
                // Starting new intent
                Intent in = new Intent(getActivity().getApplicationContext(), PedidosActivity.class);

                in.putExtra(variables_publicas.CLIENTES_COLUMN_IdCliente, IdCliente);
                in.putExtra(variables_publicas.CLIENTES_COLUMN_Nombre, Nombre);

                *//*Guardamos el cliente seleccionado*//*
                for (HashMap<String, String> cliente : listaClientes) {
                    if (cliente.get(variables_publicas.CLIENTES_COLUMN_IdCliente).equals(IdCliente) ) {
                        ClienteH.EliminaCliente(IdCliente);
                        ClienteH.GuardarTotalClientes(cliente);

                    }
                }

                startActivity(in);
            }
        });*/
        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

                inputMethodManager.hideSoftInputFromWindow(txtBusqueda.getWindowToken(), 0);
                busqueda = txtBusqueda.getText().toString();
                tipoBusqueda = rgGrupo.getCheckedRadioButtonId() == R.id.rbCodigo ? "1" : "2";

                if (TextUtils.isEmpty(busqueda)) {
                    txtBusqueda.setError("Ingrese un valor");
                    return;
                }
                new GetClientes().execute();
                lblFooter.setText("Clientes encontrados: " + String.valueOf(listaClientes.size()));
            }
        });
        return myView;
    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        try {
            super.onCreateContextMenu(menu, v, menuInfo);
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;


            HashMap<String, String> obj = (HashMap<String, String>) lv.getItemAtPosition(info.position);

            String Codigo = obj.get("IdCliente");
            String nombre = obj.get("Nombre");
            String Visita = obj.get("Visita");

            String HeaderMenu = "Cliente: "+ Codigo + "\n" + nombre;

            menu.setHeaderTitle(HeaderMenu);
            MenuInflater inflater = getActivity().getMenuInflater();

            inflater.inflate(R.menu.clientes_list_menu_context, menu);
            MenuItem tv = menu.getItem(0); //Boton Editar
            MenuItem tv2 = menu.getItem(3); //Boton No Venta

            if ((Visita.equals("Compra") )  ){
                tv2.setEnabled(false);
            }else{
                tv2.setEnabled(true);
            }

            if (variables_publicas.usuario.getTipo().equalsIgnoreCase("Vendedor")) {
                tv.setEnabled(false);
            }
            else {
                tv.setEnabled(true);
            }


        } catch (Exception e) {
            //mensajeAviso(e.getMessage());
        }
    }
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        HashMap<String, String> clientes = null;
        try {
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

            switch (item.getItemId()) {
                case R.id.itemEditarCliente:{

                   // busqueda = txtBusqueda.getText().toString();
                     //Editar
                    HashMap<String, String> obj = listaClientes.get(info.position);
                    String Codigo = obj.get("IdCliente");
                    //String vValorFiltro = ClienteH.ObtenerDescripcion(variables_publicas.CLIENTES_COLUMN_NombreCliente,variables_publicas.TABLE_CLIENTES,variables_publicas.CLIENTES_COLUMN_IdCliente,Codigo);
                    clientes = ClienteH.ObtenerClienteGuardado(Codigo);
                    if (clientes == null) {
                        Funciones.MensajeAviso(getActivity(), "No se ha encontrado Información del Cliente");
                        return true;
                    }

                    String IdCliente = clientes.get("IdCliente");
                    String Nombre = clientes.get("Nombre");
                    String FechaCreacion = clientes.get("FechaCreacion");
                    String Telefono = clientes.get("Telefono");
                    String Direccion = clientes.get("Direccion");
                    String IdDepartamento = clientes.get("IdDepartamento");
                    String IdMunicipio = clientes.get("IdMunicipio");
                    String Ciudad = clientes.get("Ciudad");
                    String Ruc = clientes.get("Ruc");
                    String Cedula = clientes.get("Cedula");
                    String LimiteCredito = clientes.get("LimiteCredito");
                    String IdFormaPago = clientes.get("IdFormaPago");
                    String IdVendedor = clientes.get("IdVendedor");
                    String Excento = clientes.get("Excento");
                    String CodigoLetra = clientes.get("CodigoLetra");
                    String Ruta = clientes.get("Ruta");
                    String NombreRuta = clientes.get("NombreRuta");
                    String Frecuencia = clientes.get("Frecuencia");
                    String PrecioEspecial = clientes.get("PrecioEspecial");
                    String FechaUltimaCompra = clientes.get("FechaUltimaCompra");
                    String Tipo = clientes.get("Tipo");
                    String TipoPrecio = clientes.get("TipoPrecio");
                    String Descuento = clientes.get("Descuento");
                    String Empleado = clientes.get("Empleado");
                    String IdSupervisor = clientes.get("IdSupervisor");
                    String Empresa = clientes.get("Empresa");
                    String Cod_Zona = clientes.get("Cod_Zona");
                    String Cod_SubZona = clientes.get("Cod_SubZona");
                    String Pais_Id = clientes.get("Pais_Id");
                    String Pais_Nombre = clientes.get("Pais_Nombre");
                    String IdTipoNegocio = clientes.get("IdTipoNegocio");
                    String TipoNegocio = clientes.get("TipoNegocio");

                    Intent in = new Intent(getActivity().getApplicationContext(), ClientesNew.class);

                    in.putExtra(variables_publicas.CLIENTES_COLUMN_IdCliente, IdCliente);
                    in.putExtra(variables_publicas.CLIENTES_COLUMN_Nombre, Nombre);
                    in.putExtra(variables_publicas.CLIENTES_COLUMN_FechaCreacion, FechaCreacion);
                    in.putExtra(variables_publicas.CLIENTES_COLUMN_Telefono, Telefono);
                    in.putExtra(variables_publicas.CLIENTES_COLUMN_Direccion, Direccion);
                    in.putExtra(variables_publicas.CLIENTES_COLUMN_IdDepartamento, IdDepartamento);
                    in.putExtra(variables_publicas.CLIENTES_COLUMN_IdMunicipio, IdMunicipio);
                    in.putExtra(variables_publicas.CLIENTES_COLUMN_Ciudad, Ciudad);
                    in.putExtra(variables_publicas.CLIENTES_COLUMN_Ruc, Ruc);
                    in.putExtra(variables_publicas.CLIENTES_COLUMN_Cedula, Cedula);
                    in.putExtra(variables_publicas.CLIENTES_COLUMN_LimiteCredito, LimiteCredito);
                    in.putExtra(variables_publicas.CLIENTES_COLUMN_IdFormaPago, IdFormaPago);
                    in.putExtra(variables_publicas.CLIENTES_COLUMN_IdVendedor, IdVendedor);
                    in.putExtra(variables_publicas.CLIENTES_COLUMN_Excento, Excento);
                    in.putExtra(variables_publicas.CLIENTES_COLUMN_CodigoLetra, CodigoLetra);
                    in.putExtra(variables_publicas.CLIENTES_COLUMN_Ruta, Ruta);
                    in.putExtra(variables_publicas.CLIENTES_COLUMN_NombreRuta, NombreRuta);
                    in.putExtra(variables_publicas.CLIENTES_COLUMN_Frecuencia, Frecuencia);
                    in.putExtra(variables_publicas.CLIENTES_COLUMN_PrecioEspecial, PrecioEspecial);
                    in.putExtra(variables_publicas.CLIENTES_COLUMN_FechaUltimaCompra, FechaUltimaCompra);
                    in.putExtra(variables_publicas.CLIENTES_COLUMN_Tipo, Tipo);
                    in.putExtra(variables_publicas.CLIENTES_COLUMN_TipoNegocio, TipoPrecio);
                    in.putExtra(variables_publicas.CLIENTES_COLUMN_Descuento, Descuento);
                    in.putExtra(variables_publicas.CLIENTES_COLUMN_Empleado, Empleado);
                    in.putExtra(variables_publicas.CLIENTES_COLUMN_IdSupervisor, IdSupervisor);
                    in.putExtra(variables_publicas.CLIENTES_COLUMN_Empresa, Empresa);
                    in.putExtra(variables_publicas.CLIENTES_COLUMN_Cod_Zona, Cod_Zona);
                    in.putExtra(variables_publicas.CLIENTES_COLUMN_Cod_SubZona, Cod_SubZona);
                    in.putExtra(variables_publicas.CLIENTES_COLUMN_Pais_Id, Pais_Id);
                    in.putExtra(variables_publicas.CLIENTES_COLUMN_Pais_Nombre, Pais_Nombre);
                    in.putExtra(variables_publicas.CLIENTES_COLUMN_IdTipoNegocio, IdTipoNegocio);
                    in.putExtra(variables_publicas.CLIENTES_COLUMN_TipoNegocio, TipoNegocio);
                    // Starting new intent
                    variables_publicas.vEditando= true;
                    startActivity(in);
                    return true;
                }
                case R.id.itemGeoCliente:{
                    HashMap<String, String> obj = listaClientes.get(info.position);
                    String vCliente = obj.get("IdCliente");
                    String vNombre = obj.get("Nombre");
                    String CodigoCV= obj.get("CodCv");
                    String Codigo;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                        ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);

                        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                        }
                        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
                        Location location = null;
                        LocationListener mlocListener = new LocationListener() {
                            @Override
                            public void onLocationChanged(Location location) {
                            }
                            @Override
                            public void onStatusChanged(String provider, int status, Bundle extras) {
                            }
                            @Override
                            public void onProviderEnabled(String provider) {
                            }
                            @Override
                            public void onProviderDisabled(String provider) {
                            }
                        };

                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, mlocListener);
                        if (locationManager != null) {
                            //Existe GPS_PROVIDER obtiene ubicación
                            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                        }

                        if(location == null){ //Trata con NETWORK_PROVIDER
                            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, mlocListener);
                            if (locationManager != null) {
                                //Existe NETWORK_PROVIDER obtiene ubicación
                                location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                            }
                        }
                        if(location != null) {
                            Latitud = String.valueOf(location.getLatitude());
                            Longitud = String.valueOf(location.getLongitude());
                        }else {//Volvemos a preguntar por una segunda ocacion hasta encontrar la ultima ubicacion
                            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, mlocListener);
                            if (locationManager != null) {
                                //Existe GPS_PROVIDER obtiene ubicación
                                location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            }

                            if(location == null){ //Trata con NETWORK_PROVIDER
                                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, mlocListener);
                                if (locationManager != null) {
                                    //Existe NETWORK_PROVIDER obtiene ubicación
                                    location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                                }
                            }
                            if(location != null) {
                                Latitud = String.valueOf(location.getLatitude());
                                Longitud = String.valueOf(location.getLongitude());
                            }else {
                                Toast.makeText(getActivity(), "No se pudo obtener geolocalización", Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                    MostrarMensajeGuardar2(vCliente,CodigoCV,vNombre,Latitud,Longitud);
                    return true;
                }
                case R.id.itemVenta:{
                    // getting values from selected ListItem
                    HashMap<String, String> obj = listaClientes.get(info.position);
                    String IdCliente = obj.get("IdCliente");
                    String Nombre = obj.get("Nombre");
                    String CodigoCV= obj.get("CodCv");

                    if (CodigoCV==null ||CodigoCV.equals("") || CodigoCV.isEmpty()){
                        CodigoCV="0";
                    }

                    Intent in = new Intent(getContext(), PedidosActivity.class);
                    in.putExtra(variables_publicas.CLIENTES_COLUMN_IdCliente, IdCliente );
                    in.putExtra(variables_publicas.CLIENTES_COLUMN_Nombre, Nombre );
                    in.putExtra(variables_publicas.vVisualizar,"False");
/*
                    // Starting new intent
                    Intent in = new Intent(getActivity().getApplicationContext(), PedidosActivity.class);
                    in.putExtra(variables_publicas.CLIENTES_COLUMN_IdCliente, IdCliente );
                    in.putExtra(variables_publicas.CLIENTES_COLUMN_Nombre, Nombre );
                    in.putExtra(variables_publicas.vVisualizar,"False");
                    in.putExtra(variables_publicas.CLIENTES_COLUMN_CodCv, CodigoCV );
*/
                    ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                    if (ActivityCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
                    {

                    }else {
                        LocationManager locManager;
                        boolean isGPSEnabled = false;
                        locManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
                        isGPSEnabled = locManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
                        if (!isGPSEnabled){
                            showSettingsAlert();
                            return false;
                        }
                        String referenciado= ClienteH.EsReferenciado(IdCliente,CodigoCV);
                        if (referenciado.equalsIgnoreCase("0")){
                            MensajeAviso("El Cliente no está Geoposicionado. No se puede registrar un pedido.");
                            return false;
                        }
                    }
                    startActivityForResult(in, PETICION_ACTIVITY_SEGUNDA);
                    IdCliente="";
                    Nombre="";
                    CodigoCV="";
                    //startActivity(in);
                    return true;

                }
                case R.id.itemVisita:{
                    HashMap<String, String> obj = listaClientes.get(info.position);
                    String vCliente = obj.get("IdCliente");
                    String vNombre = obj.get("Nombre");

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                        ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);

                        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                        }
                        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
                        Location location = null;
                        LocationListener mlocListener = new LocationListener() {
                            @Override
                            public void onLocationChanged(Location location) {
                            }
                            @Override
                            public void onStatusChanged(String provider, int status, Bundle extras) {
                            }
                            @Override
                            public void onProviderEnabled(String provider) {
                            }
                            @Override
                            public void onProviderDisabled(String provider) {
                            }
                        };

                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, mlocListener);
                        if (locationManager != null) {
                            //Existe GPS_PROVIDER obtiene ubicación
                            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                        }

                        if(location == null){ //Trata con NETWORK_PROVIDER
                            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, mlocListener);
                            if (locationManager != null) {
                                //Existe NETWORK_PROVIDER obtiene ubicación
                                location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                            }
                        }
                        if(location != null) {
                            Latitud = String.valueOf(location.getLatitude());
                            Longitud = String.valueOf(location.getLongitude());
                        }else {//Volvemos a preguntar por una segunda ocacion hasta encontrar la ultima ubicacion
                            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, mlocListener);
                            if (locationManager != null) {
                                //Existe GPS_PROVIDER obtiene ubicación
                                location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            }

                            if(location == null){ //Trata con NETWORK_PROVIDER
                                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, mlocListener);
                                if (locationManager != null) {
                                    //Existe NETWORK_PROVIDER obtiene ubicación
                                    location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                                }
                            }
                            if(location != null) {
                                Latitud = String.valueOf(location.getLatitude());
                                Longitud = String.valueOf(location.getLongitude());
                            }else {
                                Toast.makeText(getActivity(), "No se pudo obtener geolocalización", Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                    MostrarMensajeGuardar3(vCliente,vNombre,Latitud,Longitud);
                    return  true;
                }
                default:
                    return super.onContextItemSelected(item);
            }
        } catch (Exception e) {
            //mensajeAviso(e.getMessage());
        }
        return false;
    }
    public void showSettingsAlert(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
  /*      Dialog dialog = new Dialog(getActivity());
        dialog.setTitle("Configuración GPS");
        dialog.setMessage("GPS no está habilitado. Favor activarlo");
        dialog.*/
        // Setting Dialog Title
        alertDialog.setTitle("Configuración GPS");
        // Setting Dialog Message
        alertDialog.setMessage("GPS no está habilitado. Favor activarlo");
        // On pressing Settings button
        alertDialog.setPositiveButton("Activar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
                dialog.cancel();
            }
        });
        alertDialog.setCancelable(false);
        alertDialog.show();
    }
    public void MostrarMensajeGuardar3( String codigocliente,final String nombrecliente,final String valorLat,final String valorLng) {
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogView = null;
        dialogBuilder.setCancelable(false);
        dialogView = inflater.inflate(R.layout.clientenoventa, null);
        Button btnOK = dialogView.findViewById(R.id.btnGuardar);
        Button btnNOK = dialogView.findViewById(R.id.btnCancelar);
        TextView txtNombre = dialogView.findViewById(R.id.txtNombreCliente);
        final Spinner cboMotivo= dialogView.findViewById(R.id.cboMotivoNoVenta);
        EditText txtObservaciones = dialogView.findViewById(R.id.txtObservacion);

        txtNombre.setText(nombrecliente);

        ClienteId=codigocliente;
        vLatitud=valorLat;
        vLongitud=valorLng;

        final List<MotivosNoVenta> CMotivos;
        CMotivos = ClienteH.ObtenerListaMotivosNoVenta();
        ArrayAdapter<MotivosNoVenta> adapterMotivos = new ArrayAdapter<MotivosNoVenta>(getActivity(), android.R.layout.simple_spinner_item, CMotivos);
        adapterMotivos.setDropDownViewResource(android.R.layout.simple_list_item_checked);
        cboMotivo.setAdapter(adapterMotivos);
        cboMotivo.setSelection(0);

        cboMotivo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapter, View v, int position, long id) {
                MotivosNoVenta mSelected = (MotivosNoVenta) adapter.getItemAtPosition(position);
                vMotivoNoVenta=mSelected.getCodigo();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
        dialogBuilder.setView(dialogView);

        final  AlertDialog alertDialog = dialogBuilder.create();

        btnOK.setOnClickListener(v -> {
            try{
                vObservacion=txtObservaciones.getText().toString().equals("") ? "-" : txtObservaciones.getText().toString();
                ActualizaMotivoNoVenta();

            }catch (Exception e){
                Log.e("Error",e.getMessage());
            }
            if (guardadoOK) {
                try {
                    alertDialog.dismiss();
                    btnBuscar.performClick();
                } catch (Exception e) {
                    Log.e("Error", e.getMessage());
                }
            }else{
                MensajeAviso("Hubo un error al actualizar el motivo No Venta. Intente más tarde.");
                alertDialog.dismiss();
            }
        });

        btnNOK.setOnClickListener(v -> alertDialog.dismiss());


        alertDialog.show();
    }

    private void ActualizaMotivoNoVenta() {

        HttpHandler sh = new HttpHandler();

        final String url = variables_publicas.direccionIp + "/ServicioClientes.svc/ActualizarMotivosNoVenta/" + ClienteId + "/"  + vMotivoNoVenta + "/" + variables_publicas.usuario.getCodigo() + "/" + variables_publicas.usuario.getRuta() + "/" + vObservacion + "/" + vLatitud + "/" + vLongitud;

        String urlString = url;
        String urlStr = urlString;
        String encodeUrl = "";
        try {
            URL Url = new URL(urlStr);
            URI uri = new URI(Url.getProtocol(), Url.getUserInfo(), Url.getHost(), Url.getPort(), Url.getPath(), Url.getQuery(), Url.getRef());
            encodeUrl = uri.toURL().toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String jsonStr = sh.makeServiceCall(encodeUrl);

        if (jsonStr != null) {
            try {
                JSONObject result = new JSONObject(jsonStr);
                String resultState = ((String) result.get("ActualizarMotivosNoVentaResult")).split(",")[0];
                final String mensaje = ((String) result.get("ActualizarMotivosNoVentaResult")).split(",")[1];
                if (resultState.equals("false")) {
                    if(getActivity()==null) return ;
                    getActivity().runOnUiThread(() -> Toast.makeText(getActivity().getApplicationContext(),
                            mensaje,
                            Toast.LENGTH_LONG).show());
                    guardadoOK = false;
                } else {
                    guardadoOK = true;
                    DbOpenHelper.database.beginTransaction();
                    ClienteH.ActualizarVisita(ClienteId,"NoCompra");
                    DbOpenHelper.database.setTransactionSuccessful();
                    DbOpenHelper.database.endTransaction();
                }


            } catch (final Exception ex) {
                guardadoOK = false;
                new Funciones().SendMail("Ha ocurrido un error al actualizar la Visita. Excepcion controlada", variables_publicas.info + ex.getMessage(), "dlunasistemas@gmail.com.ni", variables_publicas.correosErrores);
                if(getActivity()==null) return ;
                getActivity().runOnUiThread(() -> {

                    Toast.makeText(getActivity().getApplicationContext(),
                            "No es posible conectarse al servidor",
                            Toast.LENGTH_LONG).show();
                    //  }
                });
            }
        } else {
            new Funciones().SendMail("Ha ocurrido un error al actualizar la visita. Respuesta nula GET", variables_publicas.info + urlStr, "dlunasistemas@gmail.com.ni", variables_publicas.correosErrores);
            if(getActivity()==null) return ;
            getActivity().runOnUiThread(() -> Toast.makeText(getActivity().getApplicationContext(),
                    "No es posible conectarse al servidor.",
                    Toast.LENGTH_LONG).show());
        }

    }
    public void MostrarMensajeGuardar2( String valorcodigocliente,String valorcodigoclientevario,String valornombrecliente,final String valorLat,final String valorLng) {
        final AlertDialog.Builder dialogBuilder2 = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater2 = getActivity().getLayoutInflater();
        View dialogView2 = null;
        dialogBuilder2.setCancelable(false);
        dialogView2 = inflater2.inflate(R.layout.coordenadasactualizar_layout, null);
        Button btnOK = (Button) dialogView2.findViewById(R.id.btnGuardar);
        Button btnNOK = (Button) dialogView2.findViewById(R.id.btnCancelar);
        TextView txtCliente = (TextView) dialogView2.findViewById(R.id.txtCliente);
        TextView txtLatitud = (TextView) dialogView2.findViewById(R.id.txtLatitud);
        TextView txtLongitud = (TextView) dialogView2.findViewById(R.id.txtLongitud);
        txtCliente.setText(valornombrecliente);
        txtLatitud.setText(valorLat);
        txtLongitud.setText(valorLng);

        dialogBuilder2.setView(dialogView2);

        final  AlertDialog alertDialog2 = dialogBuilder2.create();
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txtLatitud.getText().toString().equals("") || txtLatitud.getText().toString().isEmpty() || txtLatitud.equals("0")) {
                    MensajeAviso("La Latitud no debe ser vacía o Nula.");
                    return;
                }
                if (txtLongitud.getText().toString().equals("") || txtLongitud.getText().toString().isEmpty() || txtLongitud.equals("0")) {
                    MensajeAviso("La Longitud no debe ser vacía o Nula.");
                    return;
                }
                ClienteId=valorcodigocliente;
                vLatitud=txtLatitud.getText().toString();
                vLongitud=txtLongitud.getText().toString();
                guardadoOK=false;
                try{

                    //new ActualizaCoordenada().execute();
                    EjecutarActualizacionCoordenada();

                }catch (Exception e){
                    Log.e("Error",e.getMessage());
                }
                if (guardadoOK) {
                    try {
                        MensajeAviso("Cliente GeoReferenciado exitosamente!.");
                        alertDialog2.dismiss();
                        btnBuscar.performClick();
                    } catch (Exception e) {
                        Log.e("Error", e.getMessage());
                    }
                }else{
                    MensajeAviso("Hubo un error al actualizar la localización. Intente más tarde.");
                    alertDialog2.dismiss();
                }
            }
        });

        btnNOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog2.dismiss();
            }
        });


        alertDialog2.show();
    }
    public void MensajeAviso(String texto) {
        AlertDialog.Builder dlgAlert = new AlertDialog.Builder(getActivity());
        dlgAlert.setMessage(texto);
        dlgAlert.setPositiveButton(R.string.aceptar, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
            }
        });
        dlgAlert.setCancelable(true);
        dlgAlert.create().show();
    }

    private void EjecutarActualizacionCoordenada(){
        HttpHandler sh = new HttpHandler();

        final String url = variables_publicas.direccionIp + "/ServicioClientes.svc/ActualizarClienteCoordenada/" + ClienteId + "/" + variables_publicas.usuario.getUsuario() + "/" + vLatitud + "/" + vLongitud;

        String urlString = url;
        String urlStr = urlString;
        String encodeUrl = "";
        try {
            URL Url = new URL(urlStr);
            URI uri = new URI(Url.getProtocol(), Url.getUserInfo(), Url.getHost(), Url.getPort(), Url.getPath(), Url.getQuery(), Url.getRef());
            encodeUrl = uri.toURL().toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String jsonStr = sh.makeServiceCall(encodeUrl);

        if (jsonStr != null) {
            try {
                JSONObject result = new JSONObject(jsonStr);
                String resultState = ((String) result.get("ActualizarClienteCoordenadaResult")).split(",")[0];
                final String mensaje = ((String) result.get("ActualizarClienteCoordenadaResult")).split(",")[1];
                if (resultState.equals("false")) {
                    if(getActivity()==null) return ;
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            Toast.makeText(getActivity().getApplicationContext(),
                                    mensaje,
                                    Toast.LENGTH_LONG).show();
                        }
                    });
                } else {
                    ClienteH.ActualizarLocalizacionLocal(ClienteId,vLatitud,vLongitud);
                    guardadoOK = true;
                }


            } catch (final Exception ex) {
                new Funciones().SendMail("Ha ocurrido un error al actualizar la Geolocalización. Excepcion controlada", variables_publicas.info + ex.getMessage(), "dlunasistemas@gmail.com.ni", variables_publicas.correosErrores);
                if(getActivity()==null) return ;
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        Toast.makeText(getActivity().getApplicationContext(),
                                "No es posible conectarse al servidor",
                                Toast.LENGTH_LONG).show();
                        //  }
                    }
                });
            }
        } else {
            new Funciones().SendMail("Ha ocurrido un error al actualizar la Geolocalización. Respuesta nula GET", variables_publicas.info + urlStr, "dlunasistemas@gmail.com", variables_publicas.correosErrores);
            if(getActivity()==null) return ;
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getActivity().getApplicationContext(),
                            "No es posible conectarse al servidor.",
                            Toast.LENGTH_LONG).show();
                }
            });
        }
    }
    // URL to get contacts JSON
    private static String url = variables_publicas.direccionIp + "/ServicioClientes.svc/BuscarClientes2/";
    public static ArrayList<HashMap<String, String>> listaClientes;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private class GetClientes extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            if(getActivity()==null) return null;
            HttpHandler sh = new HttpHandler();

            // Making a request to url and getting response
            String urlString = url + busqueda.replace(" ", "%20") + "/" + variables_publicas.usuario.getCodigo()  + "/" +  tipoBusqueda;
            String jsonStr = sh.makeServiceCall(urlString);

            Log.e(TAG, "Response from url: " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    listaClientes = new ArrayList<>();
                    // Getting JSON Array node
                    JSONArray clientes = jsonObj.getJSONArray("BuscarClientes2Result");

                    HashMap<String, String> client = null;
                    // looping through All Contacts
                    for (int i = 0; i < clientes.length(); i++) {
                        JSONObject c = clientes.getJSONObject(i);

                        HashMap<String, String> cliente = new HashMap<>();
                        cliente.put(variables_publicas.CLIENTES_COLUMN_IdCliente, c.getString("IdCliente"));
                        cliente.put(variables_publicas.CLIENTES_COLUMN_Nombre, c.getString("Nombre"));
                        cliente.put(variables_publicas.CLIENTES_COLUMN_FechaCreacion, c.getString("FechaCreacion"));
                        cliente.put(variables_publicas.CLIENTES_COLUMN_Telefono, c.getString("Telefono"));
                        cliente.put(variables_publicas.CLIENTES_COLUMN_Direccion, c.getString("Direccion"));
                        cliente.put(variables_publicas.CLIENTES_COLUMN_IdDepartamento, c.getString("IdDepartamento"));
                        cliente.put(variables_publicas.CLIENTES_COLUMN_IdMunicipio, c.getString("IdMunicipio"));
                        cliente.put(variables_publicas.CLIENTES_COLUMN_Ciudad, c.getString("Ciudad"));
                        cliente.put(variables_publicas.CLIENTES_COLUMN_Ruc, c.getString("Ruc"));
                        cliente.put(variables_publicas.CLIENTES_COLUMN_Cedula, c.getString("Cedula"));
                        cliente.put(variables_publicas.CLIENTES_COLUMN_LimiteCredito, c.getString("LimiteCredito"));
                        cliente.put(variables_publicas.CLIENTES_COLUMN_IdFormaPago, c.getString("IdFormaPago"));
                        cliente.put(variables_publicas.CLIENTES_COLUMN_IdVendedor, c.getString("IdVendedor"));
                        cliente.put(variables_publicas.CLIENTES_COLUMN_Excento, c.getString("Excento"));
                        cliente.put(variables_publicas.CLIENTES_COLUMN_CodigoLetra, c.getString("CodigoLetra"));
                        cliente.put(variables_publicas.CLIENTES_COLUMN_Ruta, c.getString("Ruta"));
                        cliente.put(variables_publicas.CLIENTES_COLUMN_NombreRuta, c.getString("NombreRuta"));
                        cliente.put(variables_publicas.CLIENTES_COLUMN_Frecuencia, c.getString("Frecuencia"));
                        cliente.put(variables_publicas.CLIENTES_COLUMN_PrecioEspecial, c.getString("PrecioEspecial"));
                        cliente.put(variables_publicas.CLIENTES_COLUMN_FechaUltimaCompra, c.getString("FechaUltimaCompra"));
                        cliente.put(variables_publicas.CLIENTES_COLUMN_Tipo, c.getString("Tipo"));
                        cliente.put(variables_publicas.CLIENTES_COLUMN_TipoPrecio, c.getString("TipoPrecio"));
                        cliente.put(variables_publicas.CLIENTES_COLUMN_Descuento, c.getString("Descuento"));
                        cliente.put(variables_publicas.CLIENTES_COLUMN_Empleado, c.getString("Empleado"));
                        cliente.put(variables_publicas.CLIENTES_COLUMN_IdSupervisor, c.getString("IdSupervisor"));
                        cliente.put(variables_publicas.CLIENTES_COLUMN_Empresa, c.getString("EMPRESA"));
                        cliente.put(variables_publicas.CLIENTES_COLUMN_Cod_Zona, c.getString("COD_ZONA"));
                        cliente.put(variables_publicas.CLIENTES_COLUMN_Cod_SubZona, c.getString("COD_SUBZONA"));
                        cliente.put(variables_publicas.CLIENTES_COLUMN_Pais_Id, c.getString("Pais_Id"));
                        cliente.put(variables_publicas.CLIENTES_COLUMN_Pais_Nombre, c.getString("Pais_Nombre"));
                        cliente.put(variables_publicas.CLIENTES_COLUMN_IdTipoNegocio, c.getString("IdTipoNegocio"));
                        cliente.put(variables_publicas.CLIENTES_COLUMN_TipoNegocio, c.getString("TipoNegocio"));
                        cliente.put(variables_publicas.CLIENTES_COLUMN_Latitud, c.getString("Latitud"));
                        cliente.put(variables_publicas.CLIENTES_COLUMN_Longitud, c.getString("Longitud"));
                        cliente.put(variables_publicas.CLIENTES_COLUMN_Referenciado, c.getString("Referenciado"));
                        cliente.put(variables_publicas.CLIENTES_COLUMN_Visita, c.getString("Visita"));
                        listaClientes.add(cliente);

                        if (variables_publicas.usuario.getTipo().equals("Supervisor")||variables_publicas.usuario.getTipo().equals("User")) {
                            String Codigo;
                            Codigo = c.getString("IdCliente");

                            client = ClienteH.ObtenerClienteGuardado(Codigo);
                            if (client == null) {
                                DbOpenHelper.database.beginTransaction();
                                ClienteH.GuardarClientes(c.getString("IdCliente"), c.getString("Nombre"), c.getString("FechaCreacion"),
                                        c.getString("Telefono"), c.getString("Direccion"), c.getString("IdDepartamento"), c.getString("IdMunicipio"), c.getString("Ciudad"), c.getString("Ruc"), c.getString("Cedula"), c.getString("LimiteCredito"),
                                        c.getString("IdFormaPago"), c.getString("IdVendedor"), c.getString("Excento"), c.getString("CodigoLetra"), c.getString("Ruta"),c.getString("NombreRuta"), c.getString("Frecuencia"), c.getString("PrecioEspecial"), c.getString("FechaUltimaCompra"),
                                        c.getString("Tipo"),c.getString("TipoPrecio"), c.getString("Descuento"), c.getString("Empleado"), c.getString("IdSupervisor"),c.getString("EMPRESA"),
                                        c.getString("COD_ZONA"), c.getString("COD_SUBZONA"),c.getString("Pais_Id"),c.getString("Pais_Nombre"), c.getString("IdTipoNegocio"),c.getString("TipoNegocio"),c.getString("Latitud"),c.getString("Longitud"),c.getString("Referenciado"),c.getString("Visita"));
                                DbOpenHelper.database.setTransactionSuccessful();
                                DbOpenHelper.database.endTransaction();
                            }
                        }
                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    if(getActivity()==null) return null;
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getActivity().getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });

                }
            } else {
                Log.e(TAG, "Couldn't get json from server.");
                if(getActivity()==null) return null;
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity().getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();
            /**
             * Updating parsed JSON data into ListView
             * */
            ListAdapter adapter = new SimpleAdapter(
                    getActivity(), listaClientes,
                    R.layout.list_cliente, new String[]{variables_publicas.CLIENTES_COLUMN_IdCliente, "CodigoLetra", "Nombre", variables_publicas.CLIENTES_COLUMN_Direccion}, new int[]{R.id.IdCliente, R.id.CodLetra, R.id.Nombre,
                    R.id.Direccion}){
                @Override
                public View getView(int position, View convertView, ViewGroup parent) {
                    View currView = super.getView(position, convertView, parent);
                    HashMap<String, String> currItem = (HashMap<String, String>) getItem(position);
                    tvLocaliza = (TextView) currView.findViewById(R.id.tvLocalizadorMaster);
                    tvVisita = currView.findViewById(R.id.tvVisitaMaster);
                    if (currItem.get(variables_publicas.CLIENTES_COLUMN_Referenciado)!=null ) {
                        if (currItem.get(variables_publicas.CLIENTES_COLUMN_Referenciado).equalsIgnoreCase("1")){
                            tvLocaliza.setVisibility(currView.VISIBLE);
                            tvLocaliza.setBackground(getResources().getDrawable(R.drawable.localizador_verde));
                            tvLocaliza.setTextColor(Color.GREEN);
                            //tvLocaliza.setTextColor(Color.BLUE);
                        }else{
                            tvLocaliza.setVisibility(currView.INVISIBLE);
                        }

                    } else {
                        tvLocaliza.setVisibility(currView.INVISIBLE);
                        //tvLocaliza.setBackground(getResources().getDrawable(R.drawable.ic_localizador_white));
                        /*tvLocaliza.setTextColor(Color.WHITE);*/
                    }
                    if (currItem.get("Visita").equalsIgnoreCase("Compra")) {
                        tvVisita.setBackground(getResources().getDrawable(R.drawable.rounded_corner_green));
                    } else if (currItem.get("Visita").equalsIgnoreCase("NoCompra")) {
                        tvVisita.setBackground(getResources().getDrawable(R.drawable.rounded_corner_red));
                    }else {
                        tvVisita.setBackground(getResources().getDrawable(R.drawable.rounded_corner_blue));
                    }
                    return currView;
                }
            };

            lv.setAdapter(adapter);
            lblFooter.setText("Clientes Encontrado: " + String.valueOf(listaClientes.size()));
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getActivity().getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            //finish();//return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void  onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==PETICION_ACTIVITY_SEGUNDA) {
            btnBuscar.performClick();
        }
    }
}
