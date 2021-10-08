package sr.unasat.thewishapp;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import sr.unasat.thewishapp.database.WishAppDAO;
import sr.unasat.thewishapp.models.BankDTO;
import sr.unasat.thewishapp.models.QuotesDTO;

import static sr.unasat.thewishapp.database.WishAppDAO.USERS_ACCOUNT;
import static sr.unasat.thewishapp.database.WishAppDAO.USERS_ADDRESS;
import static sr.unasat.thewishapp.database.WishAppDAO.USERS_BIRTHDATE;
import static sr.unasat.thewishapp.database.WishAppDAO.USERS_CONTACT;
import static sr.unasat.thewishapp.database.WishAppDAO.USERS_FIRSTNAME;
import static sr.unasat.thewishapp.database.WishAppDAO.USERS_GENDER;
import static sr.unasat.thewishapp.database.WishAppDAO.USERS_ID;
import static sr.unasat.thewishapp.database.WishAppDAO.USERS_LASTNAME;
import static sr.unasat.thewishapp.database.WishAppDAO.USERS_PASSWORD;
import static sr.unasat.thewishapp.database.WishAppDAO.USERS_SALDO;
import static sr.unasat.thewishapp.database.WishAppDAO.USERS_TABLE;
import static sr.unasat.thewishapp.database.WishAppDAO.USERS_USERNAME;

public class RegistrarActivity extends AppCompatActivity {

    private WishAppDAO wishAppDAO;
    private EditText username, password, firstname, lastname, address, contact, account, birthdate;
    private CheckBox male, female;
    private int gender;
    long result;
    public String user, pass, first, last, adress, contactnumber, accountnumber, dob;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);

        wishAppDAO = new WishAppDAO(this);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        firstname = (EditText) findViewById(R.id.firstname);
        lastname = (EditText) findViewById(R.id.lastname);
        address = (EditText) findViewById(R.id.address);
        contact = (EditText) findViewById(R.id.contact);
        account = (EditText) findViewById(R.id.accountnumber);
        birthdate = (EditText) findViewById(R.id.birthdate);
        male = (CheckBox) findViewById(R.id.male);
        female = (CheckBox) findViewById(R.id.female);

    }

    //    private byte[] imageViewToByte(ImageView imageView){
//        Bitmap bitmap = ((BitmapDrawable)imageView.getDrawable()).getBitmap();
//        ByteArrayOutputStream stream = new ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
//        byte[] byteArray = stream.toByteArray();
//        return byteArray;
//    }


    private List<BankDTO> mapJsonToCountryObject(String jsonArray) {
        ObjectMapper mapper = new ObjectMapper();
        List<BankDTO> bankList = new ArrayList<>();
        List<Map<String, ?>> bankArray = null;
        BankDTO bank = null;

        try {
            bankArray = mapper.readValue(jsonArray, List.class);
            for (Map<String, ?> map : bankArray) {
                bank = new BankDTO((String) map.get("Saldo"));
                bankList.add(bank);
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Er is wat fout gegaan bij het parsen van de json data");
        }
        return bankList;
    }

    public void confirmAccount(View view) {
        System.out.println("Confirming");
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        final String BANK = "https://script.google.com/macros/s/AKfycbxydTDHryNb5ZmSj4eQmq4yw9ljRiMFxdHC-1-QC4iO_JRN14vF/exec?Accountnumber=" + account.getText().toString();
        ;

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, BANK,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        final List<BankDTO> bankDTOS = mapJsonToCountryObject(response);
                        //System.out.println(bankDTOS.get(0).getSaldo());
                        if(bankDTOS.get(0).getSaldo() != null){
                            Toast.makeText(RegistrarActivity.this, "Details found", Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(RegistrarActivity.this, "Details invalid", Toast.LENGTH_LONG).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(RegistrarActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
        // Add the request to the RequestQueue.
        queue.add(stringRequest);

    }

    public void backToLogin(View view) {
        Intent intent = new Intent(RegistrarActivity.this, loginActivity.class);
        startActivity(intent);
    }

    public int gender() {
        if (male.isChecked()) {
            gender = 1;
        }
        if (female.isChecked()) {
            gender = 2;
        }
        return gender;
    }

    public void registrarUser(View view) {
        user = username.getText().toString();
        pass = password.getText().toString();
        first = firstname.getText().toString();
        last = lastname.getText().toString();
        adress = address.getText().toString();
        contactnumber = contact.getText().toString();
        accountnumber = account.getText().toString();
        dob = birthdate.getText().toString();

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        final String BANK = "https://script.google.com/macros/s/AKfycbxydTDHryNb5ZmSj4eQmq4yw9ljRiMFxdHC-1-QC4iO_JRN14vF/exec?Accountnumber=" + account.getText().toString();
        ;

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, BANK,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        final List<BankDTO> bankDTOS = mapJsonToCountryObject(response);
                        //System.out.println(bankDTOS.get(0).getSaldo());
                        List<ContentValues> records = new ArrayList<>();
                        //insert record
                        ContentValues record1 = new ContentValues();
                        for (int i = 1; i > 1; i++) {
                            record1.put(USERS_ID, i);
                        }
                        record1.put(USERS_USERNAME, user);
                        record1.put(USERS_PASSWORD, pass);
                        record1.put(USERS_FIRSTNAME, first);
                        record1.put(USERS_LASTNAME, last);
                        record1.put(USERS_ADDRESS, adress);
                        record1.put(USERS_CONTACT, contactnumber);
                        record1.put(USERS_ACCOUNT, accountnumber);
                        record1.put(USERS_SALDO, bankDTOS.get(0).getSaldo());
                        record1.put(USERS_BIRTHDATE, dob);
                        record1.put(USERS_GENDER, gender());
                        records.add(record1);

                        if (user.equals("") || pass.equals("") || first.equals("") || last.equals("") || adress.equals("") || contactnumber.equals("") || accountnumber.equals("")) {
                            Toast.makeText(RegistrarActivity.this, "Please enter all the fields", Toast.LENGTH_SHORT).show();
                        } else {
                            Boolean checkusername = wishAppDAO.findRecords1(user);
                            if (!checkusername) {
                                long result = wishAppDAO.insertOneRecord(USERS_TABLE, record1);
                                if (result > 0) {
                                    Intent intent = new Intent(RegistrarActivity.this, loginActivity.class);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(RegistrarActivity.this, "Registration failed", Toast.LENGTH_SHORT).show();
                                }
                            }else {
                                Toast.makeText(RegistrarActivity.this, "User already exists!", Toast.LENGTH_SHORT).show();
                            }
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(RegistrarActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
        // Add the request to the RequestQueue.
        queue.add(stringRequest);

    }

}
