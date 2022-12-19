package com.example.projectg103;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.projectg103.DB.DBmanage;
import com.example.projectg103.Entidades.Producto;
import com.example.projectg103.Servicios.ProductoService;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

public class FormAddProduct extends AppCompatActivity {
    private Button addformbtn,deleteAddform,searchbtnform,updatebtnform;
    private EditText editPriceadd,editDescriptionadd,editNameadd,idSearch;
    private ImageView imageAddForm;
    private DBmanage dBmanage;
    private ActivityResultLauncher<String> content;
    private ProductoService productoService;
    //private DBfirebase dBfirebase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_add_product);

        addformbtn= (Button) findViewById(R.id.addFormbtn);
        deleteAddform= (Button) findViewById(R.id.deleteAddform);
        searchbtnform= (Button) findViewById(R.id.searchbtnform);
        updatebtnform= (Button) findViewById(R.id.updatebtnform);
        editPriceadd = (EditText) findViewById(R.id.editPriceadd);
        editDescriptionadd = (EditText) findViewById(R.id.editDescriptionadd);
        editNameadd = (EditText) findViewById(R.id.editNameadd);
        idSearch = (EditText) findViewById(R.id.idSearch);
        imageAddForm = (ImageView) findViewById(R.id.imageAddForm);

        try{
            dBmanage= new DBmanage(this);
            //dBfirebase =new DBfirebase();
            productoService = new ProductoService();
            content= registerForActivityResult(
                    new ActivityResultContracts.GetContent(),
                    new ActivityResultCallback<Uri>() {
                        @Override
                        public void onActivityResult(Uri result) {
                            try {
                                InputStream inputStream = getContentResolver().openInputStream(result);
                                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                                imageAddForm.setImageBitmap(bitmap);
                            }catch(FileNotFoundException e){
                                Log.e("FileError",e.toString());
                            }
                        }
                    });

        }catch (Exception e){
            Log.e("DB", e.toString());
        }

        imageAddForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                content.launch("image/*");

            }
        });

        addformbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //dBfirebase.insertProduct(
                dBmanage.insertProduct(
                        editNameadd.getText().toString(),
                        editDescriptionadd.getText().toString(),
                        editPriceadd.getText().toString(),
                        productoService.imageViewToByte(imageAddForm));

                Intent intent = new Intent(getApplicationContext(),ProductList.class);
                startActivity(intent);
            }
        });

        deleteAddform.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = idSearch.getText().toString().trim();
                dBmanage.deleteProductById(id);
                clean();


            }
        });



        searchbtnform.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = idSearch.getText().toString().trim();
                if(id.compareTo("") == 0) {
                    Toast.makeText(getApplicationContext(), "Ingrese ID", Toast.LENGTH_SHORT).show();
                }else {
                    ArrayList<Producto> productDB = productoService.cursorToArray(dBmanage.getProductById(id));
                    if (productDB.size() != 0) {
                        Producto producto = productDB.get(0);

                        editNameadd.setText(producto.getName());
                        editDescriptionadd.setText(producto.getDescription());
                        editPriceadd.setText(String.valueOf(producto.getPrice()));
                        Bitmap bitmap = BitmapFactory.decodeByteArray(producto.getImage(), 0, producto.getImage().length);
                        imageAddForm.setImageBitmap(bitmap);
                    } else {
                        Toast.makeText(getApplicationContext(), "No existe", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

        updatebtnform.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = idSearch.getText().toString().trim();
                if(id.compareTo("")!=0){
                    dBmanage.updateProduct(
                            id,
                            editNameadd.getText().toString(),
                            editDescriptionadd.getText().toString(),
                            editPriceadd.getText().toString(),
                            productoService.imageViewToByte(imageAddForm)
                    );
                    clean();
                }

            }
        });

        }
        public void clean(){
        editNameadd.setText("");
        editDescriptionadd.setText("");
        editPriceadd.setText("");
        imageAddForm.setImageResource(R.drawable.ic_launcher_background);
        }
}