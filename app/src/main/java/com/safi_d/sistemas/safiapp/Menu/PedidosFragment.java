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
import androidx.annotation.IdRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;

import android.provider.Settings;
import android.text.InputType;
import android.util.Log;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
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

import com.safi_d.sistemas.safiapp.AccesoDatos.ArticulosHelper;
import com.safi_d.sistemas.safiapp.AccesoDatos.ClientesHelper;
import com.safi_d.sistemas.safiapp.AccesoDatos.DataBaseOpenHelper;
import com.safi_d.sistemas.safiapp.Auxiliar.Funciones;
import com.safi_d.sistemas.safiapp.Auxiliar.variables_publicas;
import com.safi_d.sistemas.safiapp.Entidades.MotivosNoVenta;
import com.safi_d.sistemas.safiapp.HttpHandler;
import com.safi_d.sistemas.safiapp.Pedidos.PedidosActivity;
import com.safi_d.sistemas.safiapp.R;

import org.json.JSONObject;

import java.net.URI;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * Created by usuario on 20/3/2017.
 */

public class PedidosFragment extends Fragment {
    View myView;
    private DataBaseOpenHelper DbOpenHelper;
    private ClientesHelper ClientesH;
    private String TAG = PedidosFragment.class.getSimpleName();
    private String busqueda = "";
    private String tipoBusqueda = "2";
    private ProgressDialog pDialog;
    private ListView lv;
    private TextView lblFooter;
    private EditText txtBusqueda;
    private RadioGroup rgGrupo;
    private Button btnBuscar;
    private DecimalFormat df;
    private  String vLatitud;
    private  String vLongitud;
    private String Longitud="";
    private String Latitud="";
    private  String ClienteId;
    private String vMotivoNoVenta = "";
    private boolean guardadoOK=false;
    private String vObservacion="";
    private final int PETICION_ACTIVITY_SEGUNDA = 1;
    private TextView tvLocaliza;
    private TextView tvVisita;
    public static ArrayList<HashMap<String, String>> listaClientes;
    private ArticulosHelper databaseHelper;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState) {
        myView= inflater.inflate(R.layout.masterclientes_layout_pedidos,container,false);
        getActivity().setTitle("Nuevo Pedido");
        lv = (ListView) myView.findViewById(R.id.list);
        registerForContextMenu(lv);
        btnBuscar = (Button) myView.findViewById(R.id.btnBuscar);
        lblFooter = (TextView) myView.findViewById(R.id.lblFooter);
        rgGrupo = (RadioGroup) myView.findViewById(R.id.rgGrupo);

        rgGrupo.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if(group.getCheckedRadioButtonId()== R.id.rbCodigo){
                    txtBusqueda.setInputType(InputType.TYPE_CLASS_NUMBER);
                }
                else {
                    txtBusqueda.setInputType(InputType.TYPE_CLASS_TEXT);
                }
            }
        });
        txtBusqueda = (EditText)myView.findViewById(R.id.txtBusqueda);
        LayoutInflater inflate = getActivity().getLayoutInflater();
        View dialogView = inflate.inflate(R.layout.list_cliente_pedidos, null);
        tvLocaliza = (TextView) dialogView.findViewById(R.id.tvLocalizador);
        txtBusqueda.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                    btnBuscar.performClick();
                }
                return false;
            }
        });
        df = new DecimalFormat("#0.00");
        DecimalFormatSymbols fmts = new DecimalFormatSymbols();
        fmts.setGroupingSeparator(',');
        df.setGroupingSize(3);
        df.setGroupingUsed(true);
        df.setDecimalFormatSymbols(fmts);

        listaClientes = new ArrayList<>();
        if (Build.VERSION.SDK_INT >= 11) {
            //--post GB use serial executor by default --
            new GetClientesPedidos().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        } else {
            //--GB uses ThreadPoolExecutor by default--
            new GetClientesPedidos().execute();
        }

        lblFooter.setText("Clientes encontrados: " + String.valueOf(listaClientes.size()));

        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(txtBusqueda.getWindowToken(), 0);
                busqueda = txtBusqueda.getText().toString();
                tipoBusqueda = rgGrupo.getCheckedRadioButtonId() == R.id.rbCodigo ? "1" : "2";
                if (Build.VERSION.SDK_INT >= 11) {
                    //--post GB use serial executor by default --
                    new GetClientesPedidos().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                } else {
                    //--GB uses ThreadPoolExecutor by default--
                    new GetClientesPedidos().execute();
                }

                lblFooter.setText("Clientes encontrados: " + String.valueOf(listaClientes.size()));
            }
        });
        // Launching new screen on Selecting Single ListItem
/*        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // getting values from selected ListItem
                String IdCliente = ((TextView) view.findViewById(R.id.IdCliente)).getText().toString();
                String Nombre = ((TextView) view.findViewById(R.id.Nombre)).getText().toString();
                // Starting new intent
                Intent in = new Intent(getActivity().getApplicationContext(), PedidosActivity.class);

                in.putExtra(variables_publicas.CLIENTES_COLUMN_IdCliente, IdCliente );
                in.putExtra(variables_publicas.CLIENTES_COLUMN_Nombre, Nombre );
                in.putExtra(variables_publicas.vVisualizar,"False");
                startActivity(in);
            }
        });*/
        lv.setOnItemClickListener((parent, view, position, id) -> {
        });

        if(rgGrupo.getCheckedRadioButtonId()== R.id.rbCodigo){
            txtBusqueda.setInputType(InputType.TYPE_CLASS_NUMBER);
        }
        else {
            txtBusqueda.setInputType(InputType.TYPE_CLASS_TEXT);
        }


        return myView;
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
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        try {
            super.onCreateContextMenu(menu, v, menuInfo);
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;


            HashMap<String, String> obj = (HashMap<String, String>) lv.getItemAtPosition(info.position);

            String CodigoCliente = obj.get("IdCliente");
            String nombre = obj.get("Nombre");
            Integer Condicion= Integer.parseInt( obj.get("IdFormaPago"));
            String Visita = obj.get("Visita");
            String Codigo;
            Codigo=CodigoCliente;

            String HeaderMenu = "Cliente: "+ Codigo + "\n" + nombre;

            menu.setHeaderTitle(HeaderMenu);
            MenuInflater inflater = getActivity().getMenuInflater();

            inflater.inflate(R.menu.clientes_info_menu_new_context, menu);
            MenuItem tv2 = menu.getItem(2); //Boton No Venta

            if ((Visita.equals("Compra") )  ){
                tv2.setEnabled(false);
            }else{
                tv2.setEnabled(true);
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

                    if (CodigoCV==null || CodigoCV.equals("") || CodigoCV.isEmpty()){
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
                        String referenciado= ClientesH.EsReferenciado(IdCliente,CodigoCV);
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
            // e.getMessage();
        }
        return false;
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
        CMotivos = ClientesH.ObtenerListaMotivosNoVenta();
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
                    ClientesH.ActualizarVisita(ClienteId,"NoCompra");
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
                    ClientesH.ActualizarLocalizacionLocal(ClienteId,vLatitud,vLongitud);
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
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    //
    private class GetClientesPedidos extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Por favor espere...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... arg0) {

            try {
                if(getActivity().isFinishing()) return null;
                DbOpenHelper = new DataBaseOpenHelper(getActivity().getApplicationContext());
                ClientesH = new ClientesHelper(DbOpenHelper.database);
                switch (tipoBusqueda){
                    case "1":
                        listaClientes=ClientesH.BuscarClientesCodigo(busqueda);
                        break;
                    case  "2":
                        listaClientes=ClientesH.BuscarClientesNombre(busqueda);
                        break;
                }
            } catch (final Exception e) {
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
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            try{
                // Dismiss the progress dialog
                if (pDialog.isShowing())
                    pDialog.dismiss();
                /**
                 * Updating parsed JSON data into ListView
                 * */
                ListAdapter adapter = new SimpleAdapter(
                        getActivity(), listaClientes,
                        R.layout.list_cliente, new String[]{variables_publicas.CLIENTES_COLUMN_IdCliente,"CodigoLetra","Ciudad", "Nombre", variables_publicas.CLIENTES_COLUMN_Direccion}, new int[]{R.id.IdCliente,R.id.CodLetra,R.id.Ciudad, R.id.Nombre,
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
                if(adapter!=null){
                    lv.setAdapter(adapter);
                }
                lblFooter.setText("Clientes encontrados: " + String.valueOf(listaClientes.size()));
            }catch (final Exception ex){
                if(getActivity()==null) return ;
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        Toast.makeText(getActivity().getApplicationContext(),
                                "error: " + ex.getMessage(),
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });
            }
        }
    }
    @Override
    public void  onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==PETICION_ACTIVITY_SEGUNDA) {
            btnBuscar.performClick();
        }
    }
}