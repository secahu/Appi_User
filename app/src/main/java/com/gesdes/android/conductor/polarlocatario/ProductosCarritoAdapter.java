package com.gesdes.android.conductor.polarlocatario;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

public class ProductosCarritoAdapter extends BaseAdapter {

    Context mContext;
    List<ProductosModel>listaProductos;

    public ProductosCarritoAdapter(Context context1, List<ProductosModel>lista){
        this.mContext=context1;
        this.listaProductos=lista;
    }

    public void setListaProductos(List<ProductosModel>lista){
        this.listaProductos=lista;
    }


    @Override
    public int getCount() {
        return listaProductos.size();
    }

    @Override
    public Object getItem(int i) {
        return listaProductos.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final ProductosModel producto=listaProductos.get(i);

        if (view == null) {
            final LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            view = layoutInflater.inflate(R.layout.item_productos_carrito, null);
        }

        ImageButton btnElimina=view.findViewById(R.id.ivBoteProductoItem);
        TextView tvNombre=view.findViewById(R.id.tvNombreProductoItemCarrito);
        TextView tvCantidad=view.findViewById(R.id.tvCantidadItemCarrito);
        TextView tvPrecio=view.findViewById(R.id.tvPrecioItemCarrito);
        ImageView ivProducto=view.findViewById(R.id.ivProductoImagenItem);

        tvNombre.setText(producto.getPRODUCTO());
        tvCantidad.setText(""+producto.getCANTIDAD());
        DecimalFormat df = new DecimalFormat("#0.00");
        tvPrecio.setText("$ "+df.format(producto.getPRECIO()));

        return view;
    }
}
