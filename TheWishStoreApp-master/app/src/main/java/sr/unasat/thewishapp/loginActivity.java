package sr.unasat.thewishapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import sr.unasat.thewishapp.database.WishAppDAO;

public class loginActivity extends AppCompatActivity {

    private EditText username;
    private EditText password;
    private WishAppDAO wishAppDAO;
    CheckBox remember;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        wishAppDAO = new WishAppDAO(this);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        remember = (CheckBox) findViewById(R.id.remember);

        SharedPreferences sharedPreferences = getSharedPreferences("Checkbox", MODE_PRIVATE);
        String checkbox = sharedPreferences.getString("remember", "");
        if (checkbox.equals("true")){
            Intent intent = new Intent(loginActivity.this, HomeActivity.class);
            startActivity(intent);
        }else{
            Toast.makeText(loginActivity.this, "Please login", Toast.LENGTH_SHORT).show();
        }

        remember.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isChecked()){
                    SharedPreferences sharedPreferences = getSharedPreferences("Checkbox", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("remember", "true");
                    editor.apply();

                    SharedPreferences rememberUsername = getSharedPreferences("RememberedUsername", MODE_PRIVATE);
                    SharedPreferences.Editor usernameEditor = rememberUsername.edit();
                    usernameEditor.putString("Username", username.getText().toString());
                    usernameEditor.apply();
                }else if (!buttonView.isChecked()){
                    SharedPreferences sharedPreferences = getSharedPreferences("Checkbox", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("remember", "false");
                    editor.apply();
                }
            }
        });

    }

    public void signIn(View view){
       String user = username.getText().toString();
       String pass = password.getText().toString();

        if(user.equals("")||pass.equals(""))
            Toast.makeText(loginActivity.this, "Please enter all the fields", Toast.LENGTH_SHORT).show();
        else {
            Boolean checkuserpass = wishAppDAO.findRecords(user, pass);
            if (checkuserpass) {
                SharedPreferences rememberUsername = getSharedPreferences("RememberedUsername", MODE_PRIVATE);
                SharedPreferences.Editor usernameEditor = rememberUsername.edit();
                usernameEditor.putString("Username", user);
                usernameEditor.apply();
                Toast.makeText(loginActivity.this, "Sign in successfull", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(loginActivity.this, HomeActivity.class);
                startActivity(intent);
            }else{
                Toast.makeText(loginActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
            }
        }

    }

    public void registrar(View view){
        Intent intent = new Intent(loginActivity.this, RegistrarActivity.class);
        startActivity(intent);
    }

@Override
protected void onDestroy() {
        super.onDestroy();
        // Close The Database
        wishAppDAO.close();
        }
}