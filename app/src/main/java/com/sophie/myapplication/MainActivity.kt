package com.sophie.myapplication

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()
        hello_world.setOnClickListener {
                val url = "https://says-test.tesco-europe.com/api/gate/open"
            hello_world.text = ""

                // Post parameters
                // Form fields and values
                val params = HashMap<String,Any>()
                params["branchId"] = 11004
                params["gateId"] = "9999"
            params["receipt"] = "9999915100000236"
                val jsonObject = JSONObject(params)

                // Volley post request with parameters
                val request = JsonObjectRequest(
                    Request.Method.POST,url,jsonObject,
                    Response.Listener { response ->
                        // Process the json
                        try {
                            hello_world.text = "Response: $response"
                        }catch (e:Exception){
                            hello_world.text = "Exception: $e"
                        }

                    }, Response.ErrorListener{
                        // Error in request
                        hello_world.text = "Volley error: $it"
                    })


                // Volley request policy, only one time request to avoid duplicate transaction
                request.retryPolicy = DefaultRetryPolicy(
                    DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
                    // 0 means no retry
                    0, // DefaultRetryPolicy.DEFAULT_MAX_RETRIES = 2
                    1f // DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
                )

                // Add the volley post request to the request queue
                VolleySingleton.getInstance(this).addToRequestQueue(request)
            }
    }
}

