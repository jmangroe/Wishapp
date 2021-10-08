package sr.unasat.thewishapp.adapter;

import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import sr.unasat.thewishapp.R;

public class CartViewHolder extends RecyclerView.ViewHolder {

    ImageView pic;
    TextView name, brand, magnitude, price;
    ImageButton delete;

    CartViewHolder(View view){
        super(view);
        pic = (ImageView) view.findViewById(R.id.imageView2);
        name = (TextView) view.findViewById(R.id.cart_name);
        brand = (TextView) view.findViewById(R.id.cart_brand);
        magnitude = (TextView) view.findViewById(R.id.cart_magnitude);
        price = (TextView) view.findViewById(R.id.cart_price);
        delete = (ImageButton) view.findViewById(R.id.imageButton2);
    }

}
