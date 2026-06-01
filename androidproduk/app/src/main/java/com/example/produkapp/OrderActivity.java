package com.example.produkapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
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
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class OrderActivity extends AppCompatActivity {
    ListView listOrder;
    ArrayList<Order>list= new ArrayList<>();
    String URL = ApiConfig.BASE_URL + "tampilorder.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_order);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        listOrder=findViewById(R.id.listOrder);
        loadData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }

    private void loadData() {

        String userId = getSharedPreferences("login", MODE_PRIVATE)
                .getString("id", "");

        StringRequest request = new StringRequest(
                Request.Method.POST,
                URL,
                response -> {
                    Toast.makeText(this, response, Toast.LENGTH_SHORT).show();

                    try {
                        list.clear();

                        JSONArray array = new JSONArray(response);

                        for(int i = 0; i < array.length(); i++) {
                            JSONObject obj = array.getJSONObject(i);

                            list.add(new Order(
                                    obj.getString("id"),
                                    obj.getString("nama_produk"),
                                    obj.getString("quantity"),
                                    obj.getString("total"),
                                    obj.getString("harga")
                            ));
                        }

                        listOrder.setAdapter(new OrderAdapter());

                    } catch (Exception e) {
                        Toast.makeText(this,
                                "JSON error: " + e.getMessage(),
                                Toast.LENGTH_SHORT).show();
                    }
                },
                error -> Toast.makeText(this,
                        error.toString(),
                        Toast.LENGTH_SHORT).show()
        ) {
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<>();

                params.put("user_id", userId);

                return params;
            }
        };

        Volley.newRequestQueue(this).add(request);
    }

    class OrderAdapter extends BaseAdapter {
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

        public View getView(int position, View convertView, ViewGroup parent) {
            View view = LayoutInflater.from(OrderActivity.this)
                    .inflate(R.layout.order_list, parent, false);

            TextView txtProduk = view.findViewById(R.id.txtNamaProduk);
            TextView txtHarga = view.findViewById(R.id.txtHarga);
            TextView txtQty = view.findViewById(R.id.txtQuantity);
            TextView txtTotal = view.findViewById(R.id.txtTotal);

            Order o = list.get(position);
            txtProduk.setText(o.getQuantity());
            txtHarga.setText("Harga: Rp " + o.getHarga());
            txtQty.setText("Quantity: " + o.getTotal());
            txtTotal.setText("Total: Rp " + o.getNama_produk());

            return view;
        }
    }
}