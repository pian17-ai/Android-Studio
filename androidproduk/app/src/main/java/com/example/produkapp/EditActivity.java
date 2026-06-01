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

import java.util.HashMap;
import java.util.Map;

public class EditActivity extends AppCompatActivity {
    EditText nama, harga, stok;
    Button btnUpdate;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        nama = findViewById(R.id.nama);
        harga = findViewById(R.id.harga);
        stok = findViewById(R.id.stok);
        btnUpdate = findViewById(R.id.btnUpdate);

        id = getIntent().getStringExtra("id");

        nama.setText(getIntent().getStringExtra("nama"));
        harga.setText(getIntent().getStringExtra("harga"));
        stok.setText(getIntent().getStringExtra("stok"));
        btnUpdate.setOnClickListener(view -> {
            StringRequest request = new StringRequest(
                    Request.Method.POST,  ApiConfig.BASE_URL + "edit.php",

                    response->{
                        Toast.makeText(this,response,Toast.LENGTH_SHORT).show();
                        finish();
                    },
                    error->Toast.makeText(this,error.toString(),Toast.LENGTH_SHORT).show()
            ) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("id", id);
                    params.put("nama_produk", nama.getText().toString());
                    params.put("harga", harga.getText().toString());
                    params.put("stok", stok.getText().toString());
                    return params;
                }
            };
            Volley.newRequestQueue(this).add(request);
        });
    }
}