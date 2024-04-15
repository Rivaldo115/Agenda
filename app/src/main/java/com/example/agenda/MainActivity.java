package com.example.agenda;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Map;

public class MainActivity extends AppCompatActivity {

    EditText txtnombres, txttelefonos, txtcorreo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        txtnombres = findViewById(R.id.txtnombre);
        txttelefonos = findViewById(R.id.txttelefono);
        txtcorreo = findViewById(R.id.txtcorreo);
    }

    public void cmdGuardar_onClick(View v){
        String nombres = txtnombres.getText().toString();
        String telefonos = txttelefonos.getText().toString();
        String correo = txtcorreo.getText().toString();

        SharedPreferences preferences = getSharedPreferences("datos", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("nombres", nombres);
        editor.putString("telefonos", telefonos);
        editor.putString("correo", correo);
        editor.commit();
        Toast.makeText(this, "Datos Guardados Correctamente!!", Toast.LENGTH_SHORT).show();
    }

    public void cmdBuscar_onClick(View v)
    {
        String inputNombre = txtnombres.getText().toString();
        String inputTelefono = txttelefonos.getText().toString();
        String inputCorreo = txtcorreo.getText().toString();

        SharedPreferences preferences = getSharedPreferences("datos", MODE_PRIVATE);
        String sNombre = preferences.getString("nombres", null);
        String sTelefono = preferences.getString("telefonos", null);
        String sCorreo = preferences.getString("correo", null);

        if (inputNombre.equals(sNombre) || inputCorreo.equals(sCorreo) || inputTelefono.equals(sTelefono)) {

            txtnombres.setText(sNombre);
            txttelefonos.setText(sTelefono);
            txtcorreo.setText(sCorreo);

            Toast.makeText(this, "Datos coincidentes leídos correctamente", Toast.LENGTH_LONG).show();
        } else {
            // Si no coincide, mostramos un mensaje
            Toast.makeText(this, "No hay coincidencia con los datos guardados", Toast.LENGTH_LONG).show();
        }
    }

    public void cmdBuscar_onClick1(View v) {
        String inputNombre = txtnombres.getText().toString().toLowerCase();
        String inputCorreo = txtcorreo.getText().toString();

        SharedPreferences preferences = getSharedPreferences("datos", MODE_PRIVATE);
        Map<String, ?> allEntries = preferences.getAll();

        boolean encontrado = false;
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue().toString().toLowerCase();
            if (value.contains(inputNombre) || value.contains(inputCorreo)) {

                txtnombres.setText(preferences.getString("nombres", null));
                txttelefonos.setText(preferences.getString("telefonos", null));
                txtcorreo.setText(preferences.getString("correo", null));

                Toast.makeText(this, "Datos coincidentes leídos correctamente", Toast.LENGTH_LONG).show();
                encontrado = true;
                break;
            }
        }

        if (!encontrado) {

            Toast.makeText(this, "No hay coincidencia con los datos guardados", Toast.LENGTH_LONG).show();
        }
    }

}