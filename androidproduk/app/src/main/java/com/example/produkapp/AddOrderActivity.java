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


public class AddOrderActivity extends AppCompatActivity {
    Button btnOrder;

    EditText edtQuantity;
    String produkId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_order);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnOrder = findViewById(R.id.btnOrder);

        produkId = getIntent().getStringExtra("produk_id");
        edtQuantity = findViewById(R.id.edtQuantity);
        btnOrder.setOnClickListener(v -> {

            String quantity = edtQuantity.getText().toString();

            if(quantity.isEmpty()){
                edtQuantity.setError("Quantity wajib diisi");
                return;
            }

            String userId = getSharedPreferences("login", MODE_PRIVATE)
                    .getString("id", "");

            StringRequest request = new StringRequest(
                    Request.Method.POST, ApiConfig.BASE_URL + "order.php",
                    response -> {
                        Toast.makeText(
                                AddOrderActivity.this,
                                response,
                                Toast.LENGTH_SHORT
                        ).show();

                        finish();
                    },
                    error -> Toast.makeText(
                            AddOrderActivity.this,
                            error.toString(),
                            Toast.LENGTH_SHORT
                    ).show()
            ){
                @Override
                protected Map<String, String> getParams() {

                    Map<String, String> params = new HashMap<>();

                    params.put("user_id", userId);
                    params.put("produk_id", produkId);
                    params.put("quantity", quantity);

                    return params;
                }
            };

            Volley.newRequestQueue(this).add(request);
        });
    }
}