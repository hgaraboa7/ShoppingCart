package com.hectorgc.carrodecompra;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;


import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private Toolbar menu;
    private Button comprar;
    private EditText textoCantidad;
    private TextView textoProducto;
    private ListView todosProductos;
    private ArrayList<Producto> listaProductos;
    private Adaptador adaptador;
    private RadioButton radioEnglish;
    private RadioButton radioSpanish;
    private ImageView imageLanguage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        todosProductos=findViewById(R.id.todos_productos);
        textoProducto=findViewById(R.id.texto_producto);
        textoCantidad =findViewById(R.id.texto_cantidad);
        comprar=findViewById(R.id.boton_comprar);
        menu=findViewById(R.id.toolbar2);
        setSupportActionBar(menu);

        radioSpanish = findViewById(R.id.radio_spanish);
        radioEnglish = findViewById(R.id.radio_english);
        imageLanguage = findViewById(R.id.image_language);


        String language = Locale.getDefault().getLanguage();
        setLanguage(language);



        if (language.equals("en")) {
            radioEnglish.setChecked(true);
            imageLanguage.setImageResource(R.drawable.ic_english);
        } else if (language.equals("es")) {
            radioSpanish.setChecked(true);
            imageLanguage.setImageResource(R.drawable.ic_spanish);
        }


        radioEnglish.setOnClickListener(v -> {
            setLanguage("en");
            recreate();
        });

        radioSpanish.setOnClickListener(v -> {
            setLanguage("es");
            recreate();
        });


        registerForContextMenu(todosProductos);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        listaProductos = new ArrayList<>();


        adaptador = new Adaptador(this, android.R.layout.simple_list_item_1, listaProductos);
        todosProductos.setAdapter(adaptador);



        comprar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if((!textoProducto.getText().toString().isEmpty())&&(!textoCantidad.getText().toString().isEmpty())){

                String producto=textoProducto.getText().toString();
                int cantidad= Integer.parseInt(textoCantidad.getText().toString());
                Producto nuevoProducto = new Producto(cantidad, producto);



                listaProductos.add(nuevoProducto);


                adaptador.notifyDataSetChanged();


                textoProducto.setText("");
                textoCantidad.setText("");

                }


            }
        });






    }

private void setLanguage(String lang) {
    Locale locale = new Locale(lang);
    Locale.setDefault(locale);
    Configuration config = new Configuration();
    config.setLocale(locale);
    getResources().updateConfiguration(config, getResources().getDisplayMetrics());
}

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.menu_contextual, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();


        if (item.getItemId() == R.id.menu_contextual_delete) {

            listaProductos.remove(info.position);

            adaptador.notifyDataSetChanged();
            return true;
        }

        return super.onContextItemSelected(item);
    }


    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;

    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.menu_main_eliminar) {

            listaProductos.clear();
            adaptador.notifyDataSetChanged();
            return true;
        } else if (item.getItemId() == R.id.menu_main_enviar) {

            new AlertDialog.Builder(this)
                    .setTitle(R.string.payment_confirmation_title)
                    .setMessage(R.string.payment_confirmation_message)
                    .setPositiveButton(R.string.payment_confirmation_button, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            Toast.makeText(MainActivity.this,R.string.toast_products_sent, Toast.LENGTH_SHORT).show();
                            listaProductos.clear();
                            adaptador.notifyDataSetChanged();
                        }
                    })
                    .setNegativeButton(R.string.payment_deny_button, null)
                    .show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
