package com.suplidora.sistemas.sisago.Principal;

/**
 * Created by Sistemas on 21/6/2018.
 */
import android.app.Activity;
import android.location.Location;
import android.os.Bundle;
import android.location.LocationListener;

public class MyLocationListener implements LocationListener {
    MenuActivity mainActivity;

    public MenuActivity getMainActivity() {
        return mainActivity;
    }

    public void setMainActivity(MenuActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @Override
    public void onLocationChanged(Location loc) {
        // Este mŽtodo se ejecuta cada vez que el GPS recibe nuevas coordenadas
        // debido a la detecci—n de un cambio de ubicacion
        loc.getLatitude();
        loc.getLongitude();
       // this.mainActivity.setLocation(loc);
    }

    @Override
    public void onProviderDisabled(String provider) {
        // Este mŽtodo se ejecuta cuando el GPS es desactivado
       // messageTextView.setText("GPS Desactivado");
    }

    @Override
    public void onProviderEnabled(String provider) {
        // Este mŽtodo se ejecuta cuando el GPS es activado
       // messageTextView.setText("GPS Activado");
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // Este mŽtodo se ejecuta cada vez que se detecta un cambio en el
        // status del proveedor de localizaci—n (GPS)
        // Los diferentes Status son:
        // OUT_OF_SERVICE -> Si el proveedor esta fuera de servicio
        // TEMPORARILY_UNAVAILABLE -> Temp˜ralmente no disponible pero se
        // espera que este disponible en breve
        // AVAILABLE -> Disponible
    }
}
