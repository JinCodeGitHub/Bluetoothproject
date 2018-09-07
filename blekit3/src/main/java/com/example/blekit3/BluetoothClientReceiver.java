package com.example.blekit3;

import com.example.blekit3.connect.listener.BleConnectStatusListener;
import com.example.blekit3.connect.listener.BluetoothStateListener;
import com.example.blekit3.connect.response.BleNotifyResponse;
import com.example.blekit3.receiver.listener.BluetoothBondListener;

import java.util.HashMap;
import java.util.List;

/**
 * Created by liwentian on 2017/1/13.
 */

public class BluetoothClientReceiver {

    private HashMap<String, HashMap<String, List<BleNotifyResponse>>> mNotifyResponses;
    private HashMap<String, List<BleConnectStatusListener>> mConnectStatusListeners;
    private List<BluetoothStateListener> mBluetoothStateListeners;
    private List<BluetoothBondListener> mBluetoothBondListeners;
}
