package sr.unasat.thewishapp.adapter;

import android.media.Image;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import sr.unasat.thewishapp.R;

public class ProductsViewHolder extends RecyclerView.ViewHolder {

    ImageView pic;
    TextView name, brand, magnitude, price;
    ImageButton addToCart, gift;

    ProductsViewHolder(View view){
        super(view);
        pic = (ImageView) view.findViewById(R.id.imageView);
        name = (TextView) view.findViewById(R.id.productName);
        brand = (TextView) view.findViewById(R.id.productBrand);
        magnitude = (TextView) view.findViewById(R.id.productMagnitude);
        price = (TextView) view.findViewById(R.id.productPrice);
        addToCart = (ImageButton) view.findViewById(R.id.imageButton);
        gift = (ImageButton) view.findViewById(R.id.gift);
    }

}
