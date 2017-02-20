package com.shemanigans.mime.abstractclasses;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;

import com.shemanigans.mime.services.BluetoothLeService;

/**
 * A Fragment listening to broadcast from the {@link android.support.v4.content.LocalBroadcastManager}
 * <p>
 * Created by tj.dahunsi on 2/18/17.
 */

public abstract class BroadcastReceiverFragment extends BaseFragment {

    protected final IntentFilter intentFilter = new IntentFilter();
    protected final BroadcastReceiver gattUpdateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            BroadcastReceiverFragment.this.onReceive(context, intent);
        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        intentFilter.addAction(BluetoothLeService.GATT_CONNECTED);
        intentFilter.addAction(BluetoothLeService.GATT_CONNECTING);
        intentFilter.addAction(BluetoothLeService.GATT_DISCONNECTED);
        intentFilter.addAction(BluetoothLeService.GATT_SERVICES_DISCOVERED);
        intentFilter.addAction(BluetoothLeService.DATA_AVAILABLE_UNKNOWN);
        intentFilter.addAction(BluetoothLeService.DATA_AVAILABLE_SAMPLE_RATE);
        intentFilter.addAction(BluetoothLeService.DATA_AVAILABLE_BIOIMPEDANCE);
        intentFilter.addAction(BluetoothLeService.DATA_AVAILABLE_TAUBIN_SOLUTION);
        intentFilter.addAction(BluetoothLeService.DATA_AVAILABLE_FREQUENCY_PARAMS);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        LocalBroadcastManager.getInstance(getContext()).registerReceiver(gattUpdateReceiver, intentFilter);
    }

    @Override
    public void onDestroy() {
        LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(gattUpdateReceiver);
        super.onDestroy();
    }

    protected abstract void onReceive(Context context, Intent intent);
}
