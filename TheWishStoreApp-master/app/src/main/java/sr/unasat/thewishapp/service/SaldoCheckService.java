package sr.unasat.thewishapp.service;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import sr.unasat.thewishapp.database.WishAppDAO;

public class SaldoCheckService extends Service {

    WishAppDAO wishAppDAO = new WishAppDAO(this);

    public SaldoCheckService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        Handler handler = new Handler();
        Runnable runnable = new Runnable(){
            @Override
            public void run() {
                ArrayList<Double> amount = wishAppDAO.totalSpend();
                List<Double> saldo = wishAppDAO.findRecordsSaldo("Rekha04");
                double getSaldo = saldo.get(0);
                double total = amount.get(0);
                if (total > getSaldo) {
                    Toast.makeText(SaldoCheckService.this, "Warning: you are going over budget", Toast.LENGTH_LONG).show();
                    System.out.println("Warning: you are going over budget");
                }else{
                    Toast.makeText(SaldoCheckService.this, "Product added", Toast.LENGTH_LONG).show();
                    System.out.println("Product added");
                }
            }
        };handler.postDelayed(runnable, 5000);
        return null;
    }
}
