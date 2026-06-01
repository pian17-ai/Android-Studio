package com.example.produkapp;

public class Produk {
    String id, nama_produk, harga, stok;

    public Produk(String id, String nama_produk, String harga, String stok){
        this.id=id;
        this.nama_produk=nama_produk;
        this.harga=harga;
        this.stok=stok;
    }
    public String getId(){
        return id;
    }
    public String getNama_produk(){
        return nama_produk;
    }
    public String getHarga(){
        return harga;
    }
    public String getStok(){
        return stok;
    }
}
