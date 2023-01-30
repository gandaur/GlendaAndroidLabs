package algonquin.cst2335.anda0017;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import algonquin.cst2335.anda0017.data.MainActivityViewModel;
import algonquin.cst2335.anda0017.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

        MainActivityViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);

        binding = ActivityMainBinding.inflate(getLayoutInflater() );
        setContentView(binding.getRoot() );


        viewModel.editTextContents.observe(this, s -> {
            binding.editText.setText("Your edit text has" + s);


        });
        binding.button.setOnClickListener(v -> {
            viewModel.editTextContents.postValue(binding.editText.getText().toString());
        });

        binding.mySwitch.setChecked(true);
        binding.myButton.setChecked(true);
        binding.myBox.setChecked(true);


        binding.myimage.setOnClickListener(v -> {



    });


        //binding.editText.setText(viewModel.editTextContents);
        //binding.textHelloWorld.setText(viewModel.editTextContents);
        //binding.button.setText(viewModel.editTextContents);




        //Button b = findViewById(R.id.button);

        //TextView tv = findViewById(R.id.textHelloWorld);

       // EditText et = findViewById(R.id.editText);




            //class MyListener implements View.OnClickListener {

              // @Override
            //public void onClick(View view) {


            }
    }
