package sr.unasat.thewishapp;

import android.content.ContentValues;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import sr.unasat.thewishapp.database.WishAppDAO;

public class MyProfileFragment extends Fragment {

    EditText username, newPassword, confirmPassword;
    Button changePassword;
    View view;
    public WishAppDAO wishAppDAO;

    public MyProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_my_profile_fragment, container, false);
        username = (EditText) view.findViewById(R.id.usernameChangePass);
        newPassword = (EditText) view.findViewById(R.id.newPassword);
        confirmPassword = (EditText) view.findViewById(R.id.retypePassword);
        changePassword = (Button) view.findViewById(R.id.changePassword);
        wishAppDAO = new WishAppDAO(getActivity());


        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username1 = username.getText().toString();
                String newPassword1 = newPassword.getText().toString();
                String confirmPassword1 = confirmPassword.getText().toString();

                if(newPassword1.equals(confirmPassword1)) {
                    ContentValues contentValues = new ContentValues();
                    contentValues.put(WishAppDAO.USERS_PASSWORD, newPassword1);
                    int update = wishAppDAO.updateRecord(contentValues, username1);
                    if(update > 0){
                        Toast.makeText(getActivity(), "Password updated", Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(getActivity(), "Error while updating", Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(getActivity(), "Password incorrect", Toast.LENGTH_LONG).show();
                }
            }
        });

        return view;

    }

}