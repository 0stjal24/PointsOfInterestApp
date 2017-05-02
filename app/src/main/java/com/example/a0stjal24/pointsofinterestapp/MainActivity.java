package com.example.a0stjal24.pointsofinterestapp;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

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
        mv.getOverlays().add(items);
    }

        public boolean onCreateOptionsMenu(Menu menu)
        {
            MenuInflater inflater=getMenuInflater();
            inflater.inflate(R.menu.menu, menu);
            return true;
        }
        public boolean onOptionsItemSelected(MenuItem item)
        {
            if(item.getItemId() == R.id.addpoi)
            {
                Intent intent = new Intent(this,PoiAdd.class);
                startActivityForResult(intent,0);
                return true;
            }
            return false;
        }

        protected void onActivityResult(int requestCode,int resultCode,Intent intent)
        {
            if(requestCode==0)
            {
                if (requestCode==RESULT_OK)
                {
                    Bundle extras=intent.getExtras();
                    boolean addpoi = extras.getBoolean("com.example.a0stjal24.pointofinterestapp");
                    if(addpoi==true)
                    {
                        mv.setTileSource(TileSourceFactory.ADDPOI);
                    }
                    else
                    {
                        mv.setTileSource(TileSourceFactory.MAPNIK);
                    }
                }
        }


}
