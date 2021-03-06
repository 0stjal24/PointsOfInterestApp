package com.example.a0stjal24.pointsofinterestapp;

import android.app.Activity;
import android.content.Intent;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;

import android.os.Environment;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import org.osmdroid.config.Configuration;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.OverlayItem;


import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
{
    MapView mv;
    ItemizedIconOverlay<OverlayItem> items;
    ItemizedIconOverlay.OnItemGestureListener<OverlayItem> markerGestureListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addpoi);


        Configuration.getInstance().load(this, PreferenceManager.getDefaultSharedPreferences(this));
        setContentView(R.layout.activity_main);

        mv = (MapView) findViewById(R.id.map1);

        mv.setBuiltInZoomControls(true);
        mv.getController().setZoom(11);
        mv.getController().setCenter(new GeoPoint(50.9068, -1.3943));

        markerGestureListener = new ItemizedIconOverlay.OnItemGestureListener<OverlayItem>() {
            public boolean onItemLongPress(int i, OverlayItem item) {
                Toast.makeText(MainActivity.this, item.getSnippet(), Toast.LENGTH_SHORT).show();
                return true;
            }

            public boolean onItemSingleTapUp(int i, OverlayItem item) {
                Toast.makeText(MainActivity.this, item.getSnippet(), Toast.LENGTH_SHORT).show();
                return true;
            }
        };

        items = new ItemizedIconOverlay<OverlayItem>(this, new ArrayList<OverlayItem>(), null);

    }

        public boolean onCreateOptionsMenu(Menu menu)
        {
            MenuInflater inflater=getMenuInflater();
            inflater.inflate(R.menu.menu, menu);
            return true;
        }

        public boolean onOptionsItemSelected(MenuItem item)
        {
            {
                String dir_path = Environment.getExternalStorageDirectory().getAbsolutePath();
                EditText et = (EditText) findViewById(R.id.nameEditText);
                EditText et1 = (EditText) findViewById(R.id.typeEditText);
                EditText et2 = (EditText) findViewById(R.id.descriptionEditText);

                if (item.getItemId() == R.id.save) {

                    try {
                        FileWriter fw = new FileWriter(dir_path + "/notes.txt");
                        PrintWriter pw = new PrintWriter(fw);
                        pw.println(et.getText());
                        pw.flush();
                        pw.close();
                    }
                    catch(IOException e){
                        System.out.println("Error! " + e.getMessage());
                    }

                    return true;
                }
            }
            if(item.getItemId() == R.id.addpoi)
            {
                Intent intent = new Intent(this,PoiAdd.class);
                startActivityForResult(intent,0);
                return true;
            }
            return false;


        }

        protected void onActivityResult(int requestCode,int resultCode, Intent intent)
        {
            if(requestCode==0)
            {


                    Bundle bundle = intent.getExtras();

                    String poiname = bundle.getString("com.example.a0stjal24.pointofinterestapp.name");
                    String poitype = bundle.getString("com.example.a0stjal24.pointofinterestapp.type");
                    String poidescription = bundle.getString("com.example.a0stjal24.pointofinterestapp.description");

                    double latitude = mv.getMapCenter().getLatitude();
                    double longitude = mv.getMapCenter().getLongitude();

                    OverlayItem addpoi = new OverlayItem(poiname, poitype + poidescription, new GeoPoint(latitude, longitude));

                   // this.listPOIs.add(new POIs(poiname, poitype, poidescription, latitude, longitude));

                    items.addItem(addpoi);

                    mv.getOverlays().add(items);

                    mv.refreshDrawableState();

                    Toast.makeText(MainActivity.this, "Marker added!", Toast.LENGTH_SHORT).show();

                    }
                    else if(requestCode == 1)
                    {

                    }
                }


}



