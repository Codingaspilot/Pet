package com.example.projectg103;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Register extends AppCompatActivity {
    private Button registerCreatebtn;
    private EditText registerEmail,registerPassword,registerPasswordConfirm;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        registerCreatebtn = (Button) findViewById(R.id.registerCreatebtn);
        registerEmail = (EditText) findViewById(R.id.registerEmail);
        registerPassword = (EditText) findViewById(R.id.registerPassword);
        registerPasswordConfirm = (EditText) findViewById(R.id.registerPasswordConfirm);
        auth = FirebaseAuth.getInstance();

        registerCreatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //logica de registro
                String email = registerEmail.getText().toString().trim();
                String pass = registerPassword.getText().toString().trim();
                String passconfirm = registerPasswordConfirm.getText().toString().trim();

                if(pass.compareTo(passconfirm) == 0){

                    //Registro
                    auth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(getApplicationContext(), "Usuario creado exitosamente", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(),Login.class);
                                startActivity(intent);
                            }

                        }

                    })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getApplicationContext(), "Usuario No Creado", Toast.LENGTH_SHORT).show();
                                }
                            });

                  }else{
                    Toast.makeText(getApplicationContext(),"Contraseña no coincide",Toast.LENGTH_SHORT).show();
                }


            }
        });
    }
}