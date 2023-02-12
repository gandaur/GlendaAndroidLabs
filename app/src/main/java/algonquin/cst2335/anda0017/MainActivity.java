package algonquin.cst2335.anda0017;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import algonquin.cst2335.anda0017.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity {

    private static String TAG = "MainActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.w("MainActivity", "In onCreate() - Loading Widgets");
        getSupportActionBar().hide();

        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        SharedPreferences preferences= getSharedPreferences("MyData", Context.MODE_PRIVATE);
        Button loginButton = findViewById(R.id.login);
        EditText text = findViewById(R.id.editTextPassword);
        EditText emailtext= findViewById(R.id.emailText);

        binding.login.setOnClickListener(btn -> {

            Intent nextPage = new Intent(MainActivity.this, SecondActivity.class);
            Intent EmailAddress  = nextPage.putExtra("EmailAddress", emailtext.getText().toString());
                nextPage.putExtra("Age", 34);
                nextPage.putExtra("Name","Glenda");
                nextPage.putExtra("PostalCode", "J8X 3R9");

                //makes the transition, send the data to SecondActivity
                startActivity(nextPage);
            });


    }
    @Override // Activity is now visible
    protected void onStart() {

        super.onStart();
        Log.w(TAG, "The application is now visible");
    }

    @Override //listens for touch
    protected void onResume() {

        super.onResume();
        Log.w(TAG, "The application now listens for touch");
    }

    @Override //no longer listening to touches
    protected void onPause() {

        super.onPause();
        Log.w(TAG, "The application no longer listens to touches");
    }


    @Override // no longer visible
    protected void onStop() {

        super.onStop();
        Log.w(TAG, "The application is no longer visible");
    }

    @Override // opposite of onCreate, memory is garbage collected
    protected void onDestroy() {

        super.onDestroy();
        Log.w(TAG, "Memory used by the application is garbaged");
    }


    }

