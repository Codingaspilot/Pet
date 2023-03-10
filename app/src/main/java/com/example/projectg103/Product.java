package com.example.projectg103;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.projectg103.DB.DBmanage;
import com.example.projectg103.Entidades.Producto;
import com.example.projectg103.Servicios.ProductoService;

import java.util.ArrayList;

public class Product extends AppCompatActivity {
    private DBmanage dBmanage;
    private ProductoService productoService;
    private Button btnReturn;
    private ImageView imgDetail;
    private TextView tvNameDetail, tvDescriptionDetail, tvPriceDetail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_view);

        imgDetail = (ImageView) findViewById(R.id.imgDetail);
        tvNameDetail = (TextView) findViewById(R.id.tvNameDetail);
        tvDescriptionDetail = (TextView) findViewById(R.id.tvDescriptionDetail);
        tvPriceDetail = (TextView) findViewById(R.id.tvPriceDetail);
        dBmanage = new DBmanage(this);
        productoService = new ProductoService();

        Intent intent = getIntent();
        String id = String.valueOf((intent.getIntExtra("id",0)));
        ArrayList<Producto> productDB = productoService.cursorToArray(dBmanage.getProductById(id));
        Producto producto = productDB.get(0);
        Bitmap bitmap = BitmapFactory.decodeByteArray(producto.getImage(), 0, producto.getImage().length);
        imgDetail.setImageBitmap(bitmap);
        tvNameDetail.setText(intent.getStringExtra("name"));
        tvDescriptionDetail.setText(intent.getStringExtra("description"));
        tvPriceDetail.setText(String.valueOf(intent.getIntExtra("price", 0)));

        }
    }
