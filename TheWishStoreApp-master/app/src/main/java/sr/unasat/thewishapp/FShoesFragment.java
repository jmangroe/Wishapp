package sr.unasat.thewishapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import sr.unasat.thewishapp.adapter.ProductsAdapter;
import sr.unasat.thewishapp.database.WishAppDAO;
import sr.unasat.thewishapp.models.Products;

public class FShoesFragment extends Fragment {

    private WishAppDAO wishAppDAO;

    public FShoesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        wishAppDAO = new WishAppDAO(getActivity());
        // Inflate the layout for this fragment
        RecyclerView recyclerView = (RecyclerView) inflater.inflate(R.layout.fragment_recycler, container, false);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        ArrayList<Products> displayProducts = wishAppDAO.productsArrayList("8");
        if (displayProducts.size() > 0) {
            recyclerView.setVisibility(View.VISIBLE);
            ProductsAdapter productsAdapter = new ProductsAdapter(getActivity(), displayProducts);
            recyclerView.setAdapter(productsAdapter);
        }
        else {
            System.out.println("Couldn't load recyclerview");
        }

        return recyclerView;
    }
}