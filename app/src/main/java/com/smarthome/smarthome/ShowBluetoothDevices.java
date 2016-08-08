package com.smarthome.smarthome;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;

import java.util.ArrayList;
import java.util.Set;

public class ShowBluetoothDevices extends AppCompatActivity {
    ListView pairedDevicesList;
    ListView availableDevices;
    private BluetoothAdapter myBluetoothAdapter;
    private Set<BluetoothDevice> pairedDevicesSet;
    private ArrayAdapter<String> adapter;
    // EXTRA string to send on to mainactivity
    public static String EXTRA_DEVICE_ADDRESS = "device_address";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toast.makeText(getApplicationContext(),"ONCREATE",Toast.LENGTH_SHORT).show();
        setContentView(R.layout.activity_show_bluetooth_devices);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        pairedDevicesList = (ListView) findViewById(R.id.listViewDevicesPairedBluetooth);
        myBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        availableDevices = (ListView)findViewById(R.id.listViewDevicesDiscovered);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.refreshDevices);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Refreshing", Toast.LENGTH_LONG).show();
                pairedDevicesListM();
                discoveredDevicesListM();
            }
        });
        pairedDevicesListM();
        discoveredDevicesListM();

    }

    private void pairedDevicesListM() {
        pairedDevicesSet = myBluetoothAdapter.getBondedDevices();
        ArrayList<String> list = new ArrayList<>();
        if (pairedDevicesSet.size() > 0) {
            for (BluetoothDevice device : pairedDevicesSet) {
                list.add(device.getName() + "\n" + device.getAddress());
            }
        } else {
            String noDevices = "The are no paired devices.";
            list.add(noDevices);
        }

        adapter = new ArrayAdapter<String>(
             getApplicationContext(),R.layout.content_show_bluetooth_devices,R.id.textViewBluetoothDevicesPaired,list);
        pairedDevicesList.setAdapter(adapter);
        pairedDevicesList.setOnItemClickListener(myListClickListener);//Method called when the device from the list is clicked
    }
    private AdapterView.OnItemClickListener myListClickListener = new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView av, View v, int arg2, long arg3) {
            Toast.makeText(getApplicationContext(),"Connecting",Toast.LENGTH_SHORT);
            // Get the device MAC address, which is the last 17 chars in the View
            String info = adapter.getItem(arg2);
            String address = info.substring(info.length() - 17);
            Log.v("ADDRESS",address);
            // Make an intent to start next activity while taking an extra which is the MAC address.
            Intent i = new Intent(ShowBluetoothDevices.this, OnConnectionEstablished.class);
            i.putExtra(EXTRA_DEVICE_ADDRESS, address);
                startActivity(i);
        }
    };
    private void discoveredDevicesListM(){
        pairedDevicesSet = myBluetoothAdapter.getBondedDevices();
        ArrayList<String> list = new ArrayList<>();
        if (pairedDevicesSet.size() > 0) {
            for (BluetoothDevice device : pairedDevicesSet) {
                list.add(device.getName() + "\n" + device.getAddress());
            }
        } else {
            String noDevices = "The are no paired devices.";
            list.add(noDevices);
        }

        adapter = new ArrayAdapter<String>(
                getApplicationContext(),R.layout.content_show_bluetooth_devices,R.id.textViewBluetoothDevicesPaired,list);
        availableDevices.setAdapter(adapter);
        availableDevices.setOnItemClickListener(myListClickListener);//Method called when the device from the list is clicked
    }
}
