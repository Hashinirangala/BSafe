package com.bsafe;
 
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import java.util.ArrayList;
import java.util.List;

import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import java.util.Set;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.uimanager.IllegalViewOperationException;
 

public class BluetoothModule  extends ReactContextBaseJavaModule  {
    
    private ListView lstvw;
    private ArrayAdapter aAdapter;
    private BluetoothAdapter bAdapter = BluetoothAdapter.getDefaultAdapter();

    public BluetoothModule(ReactApplicationContext reactContext) {
        super(reactContext); //required by React Native
    }
 
    @Override
    //getName is required to define the name of the module represented in JavaScript
    public String getName() { 
        return "Bluetooth";
    }
    
    //use this method to get the scanned bluetooth devices count
    @ReactMethod
    public void scanBluetoothDevices(Callback errorCallback, Callback successCallback) {
        try {
            if(bAdapter==null){
                System.out.println("Bluetooth Not Supported");
            }
            else{
                Set<BluetoothDevice> pairedDevices = bAdapter.getBondedDevices();
                ArrayList list = new ArrayList();
                String res="";
                if(pairedDevices.size()>0){
                    for(BluetoothDevice device: pairedDevices){
                        String devicename = device.getName();
                        String macAddress = device.getAddress();
                        list.add("Name: "+devicename+"MAC Address: "+macAddress);
                        res=res+"Name: "+devicename+"MAC Address: "+macAddress+"\n";
                    }                    
                }
                successCallback.invoke("Send number of scanned devices "+list.size()+"\n"+res);
            }
        } catch (IllegalViewOperationException e) {
            errorCallback.invoke(e.getMessage());
        }
    }
}