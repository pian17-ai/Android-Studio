package com.example.produkapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    EditText regUsername, regPassword;
    Button btnDaftar;

    String URL= ApiConfig.BASE_URL + "register.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        regUsername=findViewById(R.id.regUsername);
        regPassword=findViewById(R.id.regPassword);
        btnDaftar=findViewById(R.id.btnDaftar);

        btnDaftar.setOnClickListener(view -> {
            String username = regUsername.getText().toString().trim();
            String password = regPassword.getText().toString().trim();

            if (username.isEmpty()||password.isEmpty()){
                Toast.makeText(this, "Tidak boleh kosong",Toast.LENGTH_SHORT).show();
                return;
            }
            StringRequest request = new StringRequest(Request.Method.POST,URL,
                    response->{
                try {
                    JSONObject obj = new JSONObject(response);
                    Toast.makeText(this,obj.getString("message"),Toast.LENGTH_SHORT).show();
                    if (obj.getString("status").equals("success")){
                        finish();
                    }
                } catch (Exception e){
                    Toast.makeText(this,e.getMessage(),Toast.LENGTH_SHORT).show();
                }
                    },
                    error->Toast.makeText(this,error.toString(),Toast.LENGTH_SHORT).show()
                    ){
                protected Map<String, String> getParams(){
                    Map<String, String>params=new HashMap<>();
                    params.put("username", username);
                    params.put("password", password);
                    return params;
                }
            };
            Volley.newRequestQueue(this).add(request);
        });
    }
}