package com.example.registro;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {
    EditText nom,tel,correo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        nom=findViewById(R.id.name);
        tel=findViewById(R.id.cell);
        correo=findViewById(R.id.corr);


    }

    public void boton(View view){
        anaDatos();
    }
    public void anaDatos(){
        String nombre=nom.getText().toString();
        String telefono=tel.getText().toString();
        String correoo=correo.getText().toString();

        UUID uuid=UUID.randomUUID();
        String num=uuid.toString();
        if(TextUtils.isEmpty(nombre)){
            nom.setError("Verifique Informacion");
        }else if(telefono.length()<10||telefono.length()>10){
            tel.setError("Verifique telefono");
        }else if(!correoo.isEmpty()&& Patterns.EMAIL_ADDRESS.matcher(correoo).matches()){
            StringRequest stringRequest=new StringRequest(Request.Method.POST, "https://script.google.com/macros/s/AKfycbwnFpqrMSJYBslLLMZ_5MsZetCyvH4SRXR9oPoizW9p_enMDuWjQaZEyGiLnOADxVm2/exec", new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Toast.makeText(MainActivity.this, "CORRECTO", Toast.LENGTH_SHORT).show();
                }
            }, new Response.ErrorListener(){
                @Override
                public void onErrorResponse(VolleyError error){

                }
            }){
                @Nullable
                @Override
                protected Map<String, String> getParams(){
                    Map<String, String> params=new HashMap<>();
                    params.put("action","add");
                    params.put("id",num);
                    params.put("no",nombre);

                    params.put("te","(+57)"+telefono);
                    params.put("em",correoo);
                    return params;
                }
            };
            int tiempo=50000;
            RetryPolicy retryPolicy=new DefaultRetryPolicy(tiempo,0,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
            stringRequest.setRetryPolicy(retryPolicy);

            RequestQueue requestQueue= Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);
        }else{
            correo.setError("Verfifique Correo");

        }


    }


}