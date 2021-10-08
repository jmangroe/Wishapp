package sr.unasat.thewishapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import sr.unasat.thewishapp.adapter.CartAdapter;
import sr.unasat.thewishapp.adapter.ProductsAdapter;
import sr.unasat.thewishapp.database.WishAppDAO;
import sr.unasat.thewishapp.models.Products;

public class CartActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private WishAppDAO wishAppDAO;
    private TextView price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);


        price = (TextView) findViewById(R.id.textView2);
        wishAppDAO = new WishAppDAO(this);
        recyclerView = (RecyclerView) findViewById(R.id.cart_recycler);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setHasFixedSize(true);
        ArrayList<Products> displayProducts = wishAppDAO.productsArrayListCart();
        ArrayList<Double> amount = wishAppDAO.totalSpend();
        double total = amount.get(0);
        if (displayProducts.size() > 0) {
            recyclerView.setVisibility(View.VISIBLE);
            CartAdapter cartAdapter = new CartAdapter(this, displayProducts);
            recyclerView.setAdapter(cartAdapter);
            price.setText(String.valueOf(total));
        } else {
            System.out.println("Couldn't load recyclerview");
        }

    }

    public void placeOrder(View view) {
        ArrayList<Double> amount = wishAppDAO.totalSpend();
        List<Double> saldo = wishAppDAO.findRecordsSaldo("Rekha04");
        double getSaldo = saldo.get(0);
        double total = amount.get(0);
        if (total > getSaldo) {
            Toast.makeText(this, "Warning: you are going over budget", Toast.LENGTH_LONG).show();
        }

    }
}