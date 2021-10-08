package sr.unasat.thewishapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import sr.unasat.thewishapp.CartActivity;
import sr.unasat.thewishapp.R;
import sr.unasat.thewishapp.database.WishAppDAO;
import sr.unasat.thewishapp.models.Products;

public class CartAdapter extends RecyclerView.Adapter<CartViewHolder> {

    private Context context;
    private ArrayList<Products> productsArrayList;
    private WishAppDAO wishAppDAO;

    public CartAdapter(Context context, ArrayList<Products> productsArrayList){
        this.context = context;
        this.productsArrayList = productsArrayList;
        wishAppDAO = new WishAppDAO(context);
    }

    @Override
    public CartViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_cardview, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final CartViewHolder holder, int position) {
        final Products products = productsArrayList.get(position);
        holder.pic.setImageResource(products.getPictureResource());
        holder.name.setText(products.getName());
        holder.brand.setText(products.getBrand());
        holder.magnitude.setText(products.getMagnitude());
        holder.price.setText("SRD " + (products.getPrice()));
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wishAppDAO.deleteRecord(WishAppDAO.CART_TABLE, WishAppDAO.CART_PRODUCT_ID, String.valueOf(products.getId()));

            }
        });

    }

    @Override
    public int getItemCount() {
        return productsArrayList.size();
    }

}
