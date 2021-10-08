package sr.unasat.thewishapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

import sr.unasat.thewishapp.adapter.CartAdapter;
import sr.unasat.thewishapp.adapter.GiftAdapter;
import sr.unasat.thewishapp.database.WishAppDAO;
import sr.unasat.thewishapp.models.Products;

public class GiftActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private WishAppDAO wishAppDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gift);

        wishAppDAO = new WishAppDAO(this);
        recyclerView = (RecyclerView) findViewById(R.id.cart_recycler);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setHasFixedSize(true);
        ArrayList<Products> displayProducts = wishAppDAO.productsArrayListGift();
        if (displayProducts.size() > 0) {
            recyclerView.setVisibility(View.VISIBLE);
            GiftAdapter giftAdapter = new GiftAdapter(this, displayProducts);
            recyclerView.setAdapter(giftAdapter);
        }
        else {
            System.out.println("Couldn't load recyclerview");
        }

    }
}