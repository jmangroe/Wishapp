package sr.unasat.thewishapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;

import java.util.List;

import sr.unasat.thewishapp.database.WishAppDAO;
import sr.unasat.thewishapp.models.Users;

public class myProfileActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ActionBarDrawerToggle toggle;
    private TextView usernameT, passwordT, firstnameT, lastnameT, addressT, contactnumberT, accountnumberT, saldoT, birthdateT, genderT;
    private WishAppDAO wishAppDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        wishAppDAO = new WishAppDAO(this);
        usernameT= (TextView) findViewById(R.id.username);
        passwordT= (TextView) findViewById(R.id.username2);
        firstnameT= (TextView) findViewById(R.id.username3);
        lastnameT= (TextView) findViewById(R.id.username4);
        addressT= (TextView) findViewById(R.id.username5);
        contactnumberT= (TextView) findViewById(R.id.username6);
        accountnumberT= (TextView) findViewById(R.id.username7);
        saldoT= (TextView) findViewById(R.id.username8);
        birthdateT= (TextView) findViewById(R.id.username9);
        genderT= (TextView) findViewById(R.id.username10);

        SharedPreferences getUsername = getSharedPreferences("RememberedUsername", MODE_PRIVATE);
        String currentUserUsername = getUsername.getString("Username", "");

        wishAppDAO.findRecords2(currentUserUsername, usernameT, 3);
        wishAppDAO.findRecords2(currentUserUsername, passwordT, 4);
        wishAppDAO.findRecords2(currentUserUsername, firstnameT, 1);
        wishAppDAO.findRecords2(currentUserUsername, lastnameT, 2);
        wishAppDAO.findRecords2(currentUserUsername, addressT, 5);
        wishAppDAO.findRecords2(currentUserUsername, contactnumberT, 6);
        wishAppDAO.findRecords2(currentUserUsername, accountnumberT, 7);
        wishAppDAO.findRecords2(currentUserUsername, saldoT, 8);
        wishAppDAO.findRecords2(currentUserUsername, birthdateT, 9);
        wishAppDAO.findRecordsGender(currentUserUsername, genderT);

//        loadFragment(new MyProfileFragment());

        toolbar = findViewById(R.id.toolbar1);
        navigationView = findViewById(R.id.navigation_drawer1);
        drawerLayout = findViewById(R.id.myProfileLayout);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("WishApp");

        toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                drawerLayout.closeDrawer(GravityCompat.START);
                switch (item.getItemId()){
                    case R.id.home:
                        loadFragment(new HomeFragment());
                        break;
                    case R.id.f_skirts:
                        loadFragment(new FSkirtFragment());
                        wishAppDAO.updateRecord("5");
                        break;
                    case R.id.blouse:
                        loadFragment(new FBlouseFragment());
                        wishAppDAO.updateRecord("6");
                        break;
                    case R.id.f_pantsAndShorts:
                        loadFragment(new FPantsShortsFragment());
                        wishAppDAO.updateRecord("7");
                        break;
                    case R.id.f_shoes:
                        loadFragment(new FShoesFragment());
                        wishAppDAO.updateRecord("8");
                        break;
                    case R.id.f_bags:
                        loadFragment(new FBagsFragment());
                        wishAppDAO.updateRecord("9");
                        break;
                    case R.id.f_under:
                        loadFragment(new FUndergarmentsFragment());
                        wishAppDAO.updateRecord("19");
                        break;
                    case R.id.f_perfume:
                        loadFragment(new FPerfumeFragment());
                        wishAppDAO.updateRecord("11");
                        break;
                    case R.id.makeup:
                        loadFragment(new MakeupFragment());
                        wishAppDAO.updateRecord("18");
                        break;
                    case R.id.m_pants:
                        loadFragment(new MPantsShortsFragment());
                        wishAppDAO.updateRecord("1");
                        break;
                    case R.id.shirts:
                        loadFragment(new ShirtFragment());
                        wishAppDAO.updateRecord("2");
                        break;
                    case R.id.m_shoes:
                        loadFragment(new MShoesFragment());
                        wishAppDAO.updateRecord("3");
                        break;
                    case R.id.m_bags:
                        loadFragment(new MBagsFragment());
                        wishAppDAO.updateRecord("4");
                        break;
                    case R.id.m_under:
                        loadFragment(new MUnderwearFragment());
                        wishAppDAO.updateRecord("10");
                        break;
                    case R.id.m_perfume:
                        loadFragment(new MPerfumeFragment());
                        wishAppDAO.updateRecord("12");
                        break;
                    case R.id.jewelry:
                        loadFragment(new JewelryFragment());
                        wishAppDAO.updateRecord("13");
                        break;
                    case R.id.hats:
                        loadFragment(new HatFragment());
                        wishAppDAO.updateRecord("14");
                        break;
                }
                return true;
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case(R.id.myprofile):
                Intent intent1 =new Intent(myProfileActivity.this,myProfileActivity.class);
                startActivity(intent1);
                return true;
            case(R.id.signOut):
                Intent intent2= new Intent(myProfileActivity.this,loginActivity.class);
                startActivity(intent2);

                SharedPreferences sharedPreferences = getSharedPreferences("Checkbox", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("remember", "false");
                editor.apply();
                finish();

                SharedPreferences rememberUsername = getSharedPreferences("RememberedUsername", MODE_PRIVATE);
                SharedPreferences.Editor usernameEditor = rememberUsername.edit();
                usernameEditor.putString("Username", "");
                usernameEditor.apply();
                return true;
            case(R.id.Cart):
                Intent intent3=new Intent(myProfileActivity.this,CartActivity.class);
                startActivity(intent3);
                return true;
            case(R.id.GiftSection):
                Intent intent4 = new Intent(myProfileActivity.this, GiftActivity.class);
                startActivity(intent4);
                return true;
            default:return super.onOptionsItemSelected(item);
        }
    }

    private void loadFragment(Fragment fragment) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.fragementLayout, fragment);
        transaction.commit();
    }

    private void loadFragment1(Fragment fragment) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.fragementLayout1, fragment);
        transaction.commit();
    }

    public void loadChangePassword(View view){
        loadFragment1(new MyProfileFragment());
    }

    public void setDetails(){

    }

}