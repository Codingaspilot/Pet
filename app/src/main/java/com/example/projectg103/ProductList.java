package com.example.projectg103;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.ViewDebug;
import android.widget.ListView;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.projectg103.Adaptadores.ProductoAdapter;
import com.example.projectg103.DB.DBmanage;
import com.example.projectg103.Entidades.Producto;
import com.example.projectg103.Servicios.ProductoService;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class ProductList extends AppCompatActivity {
    private DBmanage dBmanage;
    private ProductoService productoService;
    private ListView listViewProducts;
    private ProductoAdapter productoAdapter;
    private ArrayList<Producto> arrayProductos;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_list);
        arrayProductos = new ArrayList<>();

        try{
          dBmanage = new DBmanage(this);
          productoService = new ProductoService();
          arrayProductos= productoService.cursorToArray(dBmanage.getProducts());


        }catch (Exception e){
            Log.e("DB",e.toString());
        }
        listViewProducts = (ListView) findViewById(R.id.listViewProducts);



        productoAdapter = new ProductoAdapter(this, arrayProductos);

        listViewProducts.setAdapter(productoAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.actionAdd:
                Intent intent = new Intent(getApplicationContext(),FormAddProduct.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}