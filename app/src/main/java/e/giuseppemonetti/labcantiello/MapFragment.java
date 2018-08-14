package e.giuseppemonetti.labcantiello;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import e.giuseppemonetti.labcantiello.Datasource.DatiAttrazione;

import static android.content.Context.LOCATION_SERVICE;

public class MapFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;
    private SupportMapFragment suppMap;
    private Marker mark;
    private LatLng latLng;
    private Location loc;

    public MapFragment() {
        //EMPTY SORT CONSTRUCTOR
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.map_fragment, container, false);

        suppMap = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        suppMap.getMapAsync(this);

        return view;
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        //LatLng sydney = new LatLng(-34, 151);
        //mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));


        if (ActivityCompat.checkSelfPermission(this.getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this.getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }


        mMap.setMyLocationEnabled(true);

        final DatiAttrazione da = DatiAttrazione.getIstance();

        LocationManager lm = (LocationManager) this.getContext().getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this.getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this.getContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        loc = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        final LatLng centercamera = new LatLng(loc.getLatitude(),loc.getLongitude());
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(centercamera,(float)13.3));
        Circle c = mMap.addCircle(new CircleOptions()
                .center(centercamera)
                .radius(2000)
                .strokeColor(Color.RED)
                .fillColor(0x220000FF)
                .strokeWidth(1)
        );

        da.iniziaOsservazioneMap(loc, new DatiAttrazione.UpdateListener2() {
            @Override
            public void listAttrazioniAggiornate() {
                List<Attrazione> list = da.getAttrazioniPerMap();
                for (int i = 0; i < list.size(); i++) {
                    switch (list.get(i).getCategoria()) {
                        case "Bar & Pub":
                            mMap.addMarker(new MarkerOptions().position(new LatLng(list.get(i).getLat(), list.get(i).getLng())).title(list.get(i).getNome())
                                    .icon(getMarkerIcon("#325438")));
                            break;
                        case "Ristoranti & Pizzerie":
                            mMap.addMarker(new MarkerOptions().position(new LatLng(list.get(i).getLat(), list.get(i).getLng())).title(list.get(i).getNome())
                                    .icon(getMarkerIcon("#ff7100")));
                            break;
                        case "Cultura & Spettacolo":
                            mMap.addMarker(new MarkerOptions().position(new LatLng(list.get(i).getLat(), list.get(i).getLng())).title(list.get(i).getNome())
                                    .icon(getMarkerIcon("#2de5ff")));
                            break;
                        case "Cinema & Intrattenimento":
                            mMap.addMarker(new MarkerOptions().position(new LatLng(list.get(i).getLat(), list.get(i).getLng())).title(list.get(i).getNome())
                                    .icon(getMarkerIcon("#ffe200")));
                            break;
                    }
                }
            }
        });

        
        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                marker.setTitle("sort");
            }
        });


        mMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
            @Override
            public boolean onMyLocationButtonClick() {
                LatLng centercamera = new LatLng(loc.getLatitude(),loc.getLongitude());


                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(centercamera, 13.3f));

                return false;
            }
        });


        //SETTIAMO L'AZIONE AL CLICK DEL MARKER todo!!
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                return false;
            }
        });




        mMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {

            @Override
            public void onMyLocationChange(Location location) {

                if (mark != null) {mark.remove();}
                double lat = location.getLatitude();
                double lng = location.getLongitude();
                loc = location;
                latLng = new LatLng(lat, lng);



                mark = mMap.addMarker(new MarkerOptions().position(latLng).title(getResources().getString(R.string.msg_position)));
                //  myself.setDraggable(true);
               // mMap.animateCamera(CameraUpdateFactory.newLatLng(new LatLng(location.getLatitude(), location.getLongitude())));
            }
        });



    }

    public BitmapDescriptor getMarkerIcon(String color) {
        float[] hsv = new float[3];
        Color.colorToHSV(Color.parseColor(color), hsv);
        return BitmapDescriptorFactory.defaultMarker(hsv[0]);
    }


}