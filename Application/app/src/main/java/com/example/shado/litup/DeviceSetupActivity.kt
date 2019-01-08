package com.example.shado.litup

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.net.wifi.ScanResult
import android.net.wifi.WifiManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.*
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.afollestad.materialdialogs.customview.getCustomView
import kotlinx.android.synthetic.main.activity_device_setup.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.customwifidialog.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.find
import org.jetbrains.anko.uiThread
import java.net.URL
import java.util.*
import kotlin.concurrent.schedule


@Suppress("DEPRECATION")
class DeviceSetupActivity : AppCompatActivity() {

    lateinit var wifiManager: WifiManager
    lateinit var customView: View
    var ssidList : ArrayList<String> = arrayListOf()
    var adapter : ArrayAdapter<String>? = null
    var gekozenWifi : String = ""
    var progress  : ProgressBar? = null


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
        setContentView(R.layout.activity_device_setup)

        wifiManager = this.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager

        // Enable Wi-Fi
        if (wifiManager.isWifiEnabled == false) {
            Toast.makeText(this, "Enabling Wifi...", Toast.LENGTH_SHORT).show()
            wifiManager.isWifiEnabled = true
        }


        btn_send.setOnClickListener {
            send()
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
        val results = wifiManager.scanResults
        //... use new scan results ...
        if(progress != null){
            progress?.visibility = View.INVISIBLE
        }

        var add = true

        ssidList.clear()
        for(scanresult in results){
            add = true
            if(scanresult.SSID.trim() != ""){
                for(item in ssidList){
                    if (scanresult.SSID.trim().equals(item)) add = false
                }
                if(add) {
                    ssidList.add(scanresult.SSID.trim())
                    adapter?.notifyDataSetChanged()
                }
            }
        }
    }

    private fun scanFailure() {
        // handle failure: new scan did NOT succeed
        // consider using old scan results: these are the OLD results!
        val results = wifiManager.scanResults
        //... potentially use older scan results ...
        if(progress != null){
            progress?.visibility = View.INVISIBLE
        }

        var add = true

        ssidList.clear()
        for(scanresult in results){
            add = true
            if(scanresult.SSID.trim() != ""){
                for(item in ssidList){
                    if (scanresult.SSID.trim().equals(item)) add = false
                }
                if(add) {
                    ssidList.add(scanresult.SSID.trim())
                    adapter?.notifyDataSetChanged()
                }
            }
        }
    }

    private fun send() {
        var ssid = txt_ssid.text
        var passwd = txt_pass.text
        var city = txt_city.text
        var temp = ""
        if (radiogroup.checkedRadioButtonId == 2131230855) {
            temp = "C"
        } else if (radiogroup.checkedRadioButtonId == -1) {
            temp = ""
        } else {
            temp = "F"
        }
        var timezone = spinner_timezone.selectedItem.toString()

        var param = "ssid=" + ssid + "&passwd=" + passwd + "&city=" + city + "&temp=" + temp + "&timezone=" + timezone
        if (emptycheck(ssid.toString(), passwd.toString(), city.toString(), temp.toString())) {
            doAsync {
                val result = URL("http://192.168.0.247?" + param).readText()
                uiThread {
                    Log.d("Request", result)
                    lbl_response.text = result
                }

            }
        } else {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Error")
            builder.setMessage("Please fill in all the fields!")
            builder.setPositiveButton("Ok") { dialog, which -> }
            val dialog: AlertDialog = builder.create()
            dialog.show()
        }
    }

    private fun emptycheck(ssid: String, passwd: String, city: String, temp: String): Boolean {
        return (ssid != "" && passwd != "" && city != "" && temp != "")
    }

    private fun openDialog(view: View) {
        val dialog = MaterialDialog(this).show {
            customView(R.layout.customwifidialog)
        }

        // Setup custom view content
        customView = dialog.getCustomView() ?: return
        val wifilist: ListView = customView.findViewById(R.id.listWifi)
        progress = customView.findViewById(R.id.progressBar)

        if(ssidList.size > 0){
            progress?.visibility = View.INVISIBLE
        }else{
            progress?.visibility = View.VISIBLE
        }

        // Create an ArrayAdapter
        adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, ssidList)

        // Specify the layout to use when the list of choices appears

        // Apply the adapter to the spinner
        wifilist.adapter = adapter

        wifilist.setOnItemClickListener{
            adapterView : AdapterView<*>?, view : View?, position : Int, id : Long ->

                gekozenWifi = ssidList[position]
                dialog.dismiss()

                txt_ssid.setText(gekozenWifi)

        }
    }

    private fun checkPermissions(): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) !== PackageManager.PERMISSION_GRANTED) {
            requestPermissions(
                    arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),
                    PERMISSIONS_REQUEST_CODE_ACCESS_COARSE_LOCATION)
            return false
        } else {
            return true
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PERMISSIONS_REQUEST_CODE_ACCESS_COARSE_LOCATION -> {
                Scan()
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }


}
