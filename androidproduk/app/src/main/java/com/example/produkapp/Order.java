package com.example.produkapp;

public class Order {
    String id, quantity, total, nama_produk, harga;

    public Order(String id, String quantity, String total, String nama_produk, String harga){
        this.id=id;
        this.quantity=quantity;
        this.total=total;
        this.nama_produk=nama_produk;
        this.harga=harga;
    }
    public String getId(){
        return id;
    }
    public String getNama_produk(){
        return nama_produk;
    }
    public String getHarga() {
        return harga;
    }
    public String getQuantity() {
        return quantity;
    }
    public String getTotal() {
        return total;
    }
}
