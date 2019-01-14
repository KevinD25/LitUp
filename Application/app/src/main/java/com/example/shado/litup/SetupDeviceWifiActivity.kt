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
import android.widget.Button
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_setup_device_wifi.*
import java.util.ArrayList

@Suppress("DEPRECATION")
class SetupDeviceWifiActivity : AppCompatActivity() {

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
        setContentView(R.layout.activity_setup_device_wifi)

        val btnBack : Button = findViewById(R.id.btn_back)
        btnBack.setOnClickListener {
           onBackPressed()
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
            listWifi.adapter = adapter

            listWifi.setOnItemClickListener{
                adapterView : AdapterView<*>?, view : View?, position : Int, id : Long ->
                goToPassword(position)
            }
        }
    }

    private fun goToPassword(position: Int){
        val intent = Intent(this, SetupDeviceWifiPass::class.java)
        val wifi = ssidList[position]
        intent.putExtra("wifi", wifi)
        startActivity(intent)
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }

    private fun checkPermissions(): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) !== PackageManager.PERMISSION_GRANTED) {
            requestPermissions(
                    arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),
                    SetupDeviceWifiActivity.PERMISSIONS_REQUEST_CODE_ACCESS_COARSE_LOCATION)
            return false
        } else {
            return true
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            SetupDeviceWifiActivity.PERMISSIONS_REQUEST_CODE_ACCESS_COARSE_LOCATION -> {
                Scan()
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}
