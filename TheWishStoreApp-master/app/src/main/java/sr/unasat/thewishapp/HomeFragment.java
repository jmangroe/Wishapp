package sr.unasat.thewishapp;

import android.content.Intent;
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
import sr.unasat.thewishapp.service.SaldoCheckService;

public class HomeFragment extends Fragment {

    private WishAppDAO wishAppDAO;

    public HomeFragment() {
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
        ArrayList<Products> displayProducts = wishAppDAO.bestsellers("500");
        if (displayProducts.size() > 0) {
            recyclerView.setVisibility(View.VISIBLE);
            ProductsAdapter productsAdapter = new ProductsAdapter(getActivity(), displayProducts);
            recyclerView.setAdapter(productsAdapter);
        }
        else {
            System.out.println("Couldn't load recyclerview");
        }

        requireActivity().startService(new Intent(getActivity(), SaldoCheckService.class));

        return recyclerView;
    }
    }



