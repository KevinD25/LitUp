package com.example.shado.litup

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.net.wifi.WifiManager
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_device_setup.*
import kotlinx.android.synthetic.main.activity_setup_device.*
import java.util.ArrayList

@Suppress("DEPRECATION")
class SetupDeviceActivity : AppCompatActivity() {

    lateinit var wifiManager: WifiManager
    var ssidList : ArrayList<String> = arrayListOf()
    var adapter : ArrayAdapter<String>? = null

    companion object {
        private const val PERMISSIONS_REQUEST_CODE_ACCESS_COARSE_LOCATION = 120
    }

    val wifiScanReceiver = object : BroadcastReceiver() {

        override fun onReceive(context: Context, intent: Intent) {
            val success = intent.getBooleanExtra(WifiManager.EXTRA_RESULTS_UPDATED, false)
            if (success) {
                scanSuccess()
            } else {
                scanFailure()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setup_device)

        btn_back.setOnClickListener {
            StartNextActivity()
        }

        wifiManager = this.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager

        // Enable Wi-Fi
        if (wifiManager.isWifiEnabled == false) {
            Toast.makeText(this, "Enabling Wifi...", Toast.LENGTH_SHORT).show()
            wifiManager.isWifiEnabled = true
        }

        val intentFilter = IntentFilter()
        intentFilter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION)
        this.registerReceiver(wifiScanReceiver, intentFilter)

        Scan()
    }

    private fun StartNextActivity(){
        val intent = Intent(this, SetupDeviceWifiActivity::class.java)
        startActivity(intent)
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }

    private fun Scan(){
        if(checkPermissions()){
            val success = wifiManager.startScan()
            if (!success) {
                // scan failure handling
                scanFailure()
            }
        }
    }

    private fun scanSuccess() {
        getScanResults()
    }

    private fun scanFailure() {
        getScanResults()
    }

    private fun getScanResults(){
        val results = wifiManager.scanResults
        //... use new scan results ...

        var add = true

        ssidList.clear()
        for(scanresult in results){
            add = true
            if(scanresult.SSID.trim() != ""){
                for(item in ssidList){
                    if (scanresult.SSID.trim().equals(item)){
                        //TODO FILTER
                        add = false
                    }
                }
                if(add) {
                    ssidList.add(scanresult.SSID.trim())
                    adapter?.notifyDataSetChanged()
                }
            }
        }

        fillList()
    }



    private fun fillList(){
        if(ssidList.size > 0 ){
            // Create an ArrayAdapter
            adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, ssidList)

            // Apply the adapter to the spinner
            listLitup.adapter = adapter

            listLitup.setOnItemClickListener{
                adapterView : AdapterView<*>?, view : View?, position : Int, id : Long ->
                //TODO WIFI CLICKED
            }
        }
    }

    private fun checkPermissions(): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) !== PackageManager.PERMISSION_GRANTED) {
            requestPermissions(
                    arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),
                    SetupDeviceActivity.PERMISSIONS_REQUEST_CODE_ACCESS_COARSE_LOCATION)
            return false
        } else {
            return true
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            SetupDeviceActivity.PERMISSIONS_REQUEST_CODE_ACCESS_COARSE_LOCATION -> {
                Scan()
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}
