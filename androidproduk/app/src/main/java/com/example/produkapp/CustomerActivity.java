package com.example.produkapp;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;

public class CustomerActivity extends AppCompatActivity {
    Button btnCekOrder, btnLogout;
    ListView listProduk;
    ArrayList<Produk>list=new ArrayList<>();
    String URL = ApiConfig.BASE_URL + "tampil.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_customer);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        listProduk=findViewById(R.id.listProduk);

        btnCekOrder = findViewById(R.id.btnCekOrder);
        btnLogout = findViewById(R.id.btnLogout);

        btnCekOrder.setOnClickListener(view -> {
            Intent intent = new Intent(CustomerActivity.this, OrderActivity.class);
            startActivity(intent);
        });

        btnLogout.setOnClickListener(view -> {
            getSharedPreferences("login", MODE_PRIVATE)
                    .edit()
                    .clear()
                    .apply();

            Intent intent = new Intent(CustomerActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }

    private void loadData() {
        StringRequest request=new StringRequest(Request.Method.GET, URL,
                response->{
                    Toast.makeText(this, response, Toast.LENGTH_SHORT).show();
                    try {
                        list.clear();
                        JSONArray array = new JSONArray(response);
                        for(int i = 0; i < array.length(); i++) {
                            JSONObject obj = array.getJSONObject(i);
                            list.add(new Produk(
                                    obj.getString("id"),
                                    obj.getString("nama_produk"),
                                    obj.getString("harga"),
                                    obj.getString("stok")
                            ));
                        }
                        listProduk.setAdapter(new CustomerActivity.ProdukAdapter());
                    } catch (Exception e) {
                        Toast.makeText(this, "JSON Error:" + e.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                },
                error->Toast.makeText(this,error.toString(),Toast.LENGTH_SHORT).show()
                );
        Volley.newRequestQueue(this).add(request);
    }

    class ProdukAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return list.size();
        }
        @Override
        public Object getItem(int position) {
            return list.get(position);
        }
        @Override
        public long getItemId(int position) {
            return position;
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent){
            View view = LayoutInflater.from(CustomerActivity.this)
                    .inflate(R.layout.item_product_cust,parent,false);

            TextView txtProduk = view.findViewById(R.id.txtProduk);
            ImageButton btnOrder = view.findViewById(R.id.btnOrder);
            Produk p = list.get(position);
            txtProduk.setText(
                    p.getNama_produk() +
                            "\nRp" +p.getHarga() +
                            "\nStok:" +p.getStok()
            );
            btnOrder.setOnClickListener(view1->{
                Intent intent = new Intent(CustomerActivity.this, AddOrderActivity.class);

                intent.putExtra("produk_id", p.getId());

                startActivity(intent);
            });
            return view;
        }
    }
}