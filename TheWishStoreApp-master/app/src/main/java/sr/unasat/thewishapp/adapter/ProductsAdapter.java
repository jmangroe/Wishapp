package sr.unasat.thewishapp.adapter;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import sr.unasat.thewishapp.R;
import sr.unasat.thewishapp.database.WishAppDAO;
import sr.unasat.thewishapp.models.Products;


import static android.content.Context.MODE_PRIVATE;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsViewHolder> {

    private Context context;
    private ArrayList<Products> productsArrayList;
    private WishAppDAO wishAppDAO;

    public ProductsAdapter(Context context, ArrayList<Products> productsArrayList){
        this.context = context;
        this.productsArrayList = productsArrayList;
        wishAppDAO = new WishAppDAO(context);
    }

    @Override
    public ProductsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview, parent, false);
        return new ProductsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ProductsViewHolder holder, int position) {
        final Products products = productsArrayList.get(position);
        final int product_id = products.getId();
        holder.pic.setImageResource(products.getPictureResource());
        holder.name.setText(products.getName());
        holder.brand.setText(products.getBrand());
        holder.magnitude.setText(products.getMagnitude());
        holder.price.setText("SRD " + (products.getPrice()));
        holder.addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues contentValues = new ContentValues();
                contentValues.put(WishAppDAO.CART_PRODUCT_ID, product_id);
                contentValues.put(WishAppDAO.CART_IMAGE, products.getPictureResource());
                contentValues.put(WishAppDAO.CART_NAME, products.getName());
                contentValues.put(WishAppDAO.CART_MAGNITUDE, products.getMagnitude());
                contentValues.put(WishAppDAO.CART_BRAND, products.getBrand());
                contentValues.put(WishAppDAO.CART_PRICE, products.getPrice());
                long result = wishAppDAO.insertOneRecord(WishAppDAO.CART_TABLE, contentValues);
                if(result > 0){
                    System.out.println("Product added to cart");
                }else{
                    System.out.println("Couldn't add product");
                }
            }
        });
        holder.gift.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues contentValues = new ContentValues();
                contentValues.put(WishAppDAO.GIFT_PRODUCT_ID, product_id);
                contentValues.put(WishAppDAO.GIFT_IMAGE, products.getPictureResource());
                contentValues.put(WishAppDAO.GIFT_NAME, products.getName());
                contentValues.put(WishAppDAO.GIFT_MAGNITUDE, products.getMagnitude());
                contentValues.put(WishAppDAO.GIFT_BRAND, products.getBrand());
                contentValues.put(WishAppDAO.GIFT_PRICE, products.getPrice());
                long result = wishAppDAO.insertOneRecord(WishAppDAO.GIFT_TABLE, contentValues);
                if(result > 0){
                    System.out.println("Product added to gift");
                }else{
                    System.out.println("Couldn't add product");
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return productsArrayList.size();
    }
}
