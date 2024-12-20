package com.example.aguazero.Localidades;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.aguazero.R;

public class ListaUbicacion extends AppCompatActivity {

    private Button button2, button3, button4;
    private ImageButton imageButton1, imageButton2, imageButton3;
    private SharedPreferences sharedPreferences;

    // Definir el array de localidades
    private String[] localidades = {
            "Antonio Nariño", "Barrios Unidos", "Bosa", "La Candelaría", "Chapinero",
            "Ciudad Bolívar", "Engativá", "Fontibón", "Kennedy", "Los Martires",
            "Puente Aranda", "Rafael Uribe", "San Cristobal", "Santa Fe", "Suba",
            "Sumapaz", "Teusaquillo", "Tunjuelito", "Usaquén", "Usme"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_lista_ubicacion);

        sharedPreferences = getSharedPreferences("LocalidadPrefs", MODE_PRIVATE);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        button4 = findViewById(R.id.button4);

        loadButtonData();

        imageButton1 = findViewById(R.id.imageButton1);
        imageButton2 = findViewById(R.id.imageButton2);
        imageButton3 = findViewById(R.id.imageButton3);

        imageButton1.setOnClickListener(v -> {
            button2.setText("--------");
            saveButtonData("button2Text", "--------");
        });
        imageButton2.setOnClickListener(v -> {
            button3.setText("--------");
            saveButtonData("button3Text", "--------");
        });
        imageButton3.setOnClickListener(v -> {
            button4.setText("--------");
            saveButtonData("button4Text", "--------");
        });

        Intent intent = getIntent();
        String localidadSeleccionada = intent.getStringExtra("localidadSeleccionada");
        int contador = intent.getIntExtra("contador", -1);

        if (localidadSeleccionada != null) {
            if (!localidadYaSeleccionada(localidadSeleccionada)) {
                sobrescribirBoton(localidadSeleccionada, contador);
            } else {
                Toast.makeText(ListaUbicacion.this, "La localidad ya está seleccionada.", Toast.LENGTH_SHORT).show();
            }
        }

        button2.setOnClickListener(v -> seleccionarLocalidad(button2.getText().toString()));
        button3.setOnClickListener(v -> seleccionarLocalidad(button3.getText().toString()));
        button4.setOnClickListener(v -> seleccionarLocalidad(button4.getText().toString()));

        ImageButton añadir = findViewById(R.id.imageButtonañadir);

        añadir.setOnClickListener(v -> {
            if (todosBotonesOcupados()) {
                Toast.makeText(ListaUbicacion.this, "No puedes añadir más localidades.", Toast.LENGTH_SHORT).show();
            } else {
                Intent intent1 = new Intent(ListaUbicacion.this, ListaLocalidades.class);
                startActivity(intent1);
            }
        });
    }

    private void sobrescribirBoton(String localidad, int contador) {
        if (button2.getText().toString().equals("--------")) {
            button2.setText(localidad);
            saveButtonData("button2Text", localidad);
        } else if (button3.getText().toString().equals("--------")) {
            button3.setText(localidad);
            saveButtonData("button3Text", localidad);
        } else if (button4.getText().toString().equals("--------")) {
            button4.setText(localidad);
            saveButtonData("button4Text", localidad);
        } else {
            if (contador == 0) {
                button2.setText(localidad);
                saveButtonData("button2Text", localidad);
            } else if (contador == 1) {
                button3.setText(localidad);
                saveButtonData("button3Text", localidad);
            } else if (contador == 2) {
                button4.setText(localidad);
                saveButtonData("button4Text", localidad);
            }
        }
    }

    private boolean todosBotonesOcupados() {
        return !button2.getText().toString().equals("--------") &&
                !button3.getText().toString().equals("--------") &&
                !button4.getText().toString().equals("--------");
    }

    private boolean localidadYaSeleccionada(String localidad) {
        return button2.getText().toString().equals(localidad) ||
                button3.getText().toString().equals(localidad) ||
                button4.getText().toString().equals(localidad);
    }

    private void seleccionarLocalidad(String buttonText) {
        for (String localidad : localidades) {
            if (localidad.equals(buttonText)) {
                Intent intent = new Intent(ListaUbicacion.this, Infoporlocalidades.class);
                intent.putExtra("localidadSeleccionada", localidad);
                startActivity(intent);
                break;
            }
        }
    }

    private void loadButtonData() {
        String button2Text = sharedPreferences.getString("button2Text", "---");
        String button3Text = sharedPreferences.getString("button3Text", "---");
        String button4Text = sharedPreferences.getString("button4Text", "---");

        button2.setText(button2Text);
        button3.setText(button3Text);
        button4.setText(button4Text);
    }

    private void saveButtonData(String key, String value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }
}


