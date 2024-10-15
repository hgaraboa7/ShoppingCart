package com.hectorgc.carrodecompra;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

public class Adaptador extends ArrayAdapter<Producto> {

    private List<Producto> productos;
    public Adaptador(@NonNull Context context, int resource, @NonNull List<Producto> objects) {
        super(context, resource, objects);
        this.productos = objects; }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {

        Producto productoActual = productos.get(position);


        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        }

        TextView textView = convertView.findViewById(android.R.id.text1);
        String texto = getContext().getString(R.string.menu_main_product) + " " +
                productoActual.getNombre() + " " +
                getContext().getString(R.string.menu_main_amount) + " " +
                productoActual.getValor();

        textView.setText(texto);
        return convertView;
    }
}

