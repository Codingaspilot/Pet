package com.example.projectg103;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class selectioScreen extends AppCompatActivity {
    private ImageView selectioProduct,selectioLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selectio_screen);

        selectioProduct = (ImageView) findViewById(R.id.selectioProduct);
        selectioLocation = (ImageView) findViewById(R.id.selectioLocation);

        selectioProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ProductList.class);
                startActivity(intent);
            }
        });

        selectioLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                startActivity(intent);
            }
        });
    }
}