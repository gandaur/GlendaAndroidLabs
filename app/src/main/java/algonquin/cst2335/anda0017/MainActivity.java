package algonquin.cst2335.anda0017;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.app.assist.AssistContent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import algonquin.cst2335.anda0017.data.MainActivityViewModel;
import algonquin.cst2335.anda0017.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    CompoundButton.OnCheckedChangeListener myListener = new CompoundButton.OnCheckedChangeListener() {

        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

        }
    };

    MainActivityViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);

        binding = ActivityMainBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        viewModel.isSelected.observe(this, newBooleanValue -> {
            binding.mySwitch.setChecked(newBooleanValue);
            binding.myButton.setChecked(newBooleanValue);
            binding.myBox.setChecked(newBooleanValue);

        });

        binding.image.setOnClickListener(click -> {

        });

            binding.mySwitch.setChecked(viewModel.isSelected.getValue());
            binding.mySwitch.setOnCheckedChangeListener((whichButton, isChecked) -> {
                viewModel.isSelected.postValue(isChecked);
            });
            binding.myButton.setChecked(viewModel.isSelected.getValue());
            binding.myButton.setOnCheckedChangeListener((whichButton, isChecked) -> {
                viewModel.isSelected.postValue(isChecked);
            });
                binding.myBox.setChecked(viewModel.isSelected.getValue());
                binding.myBox.setOnCheckedChangeListener((whichButton, isChecked) -> {


                viewModel.isSelected.postValue(isChecked);
                });


                //Toast.makeText(MainActivity.this, "the boolean is now:" + isChecked, Toast.LENGTH_SHORT).show();
                //binding.editText.setText(viewModel.editTextContents);
                //binding.textHelloWorld.setText(viewModel.editTextContents);
                //binding.button.setText(viewModel.editTextContents);


            binding.myButton.setOnClickListener(v -> {
                viewModel.editTextContents.postValue("You clicked the button");

                binding.editText.setText(viewModel.editTextContents.getValue());
                binding.textHelloWorld.setText(viewModel.editTextContents.getValue());
                binding.button.setText(viewModel.editTextContents.getValue());



        });
        }
    }









        // binding.myBox.setOnClickListener(v ->
        // viewModel.editTextContents.postValue(binding.editText.getText().toString());
        //});


        // binding.myButton.setChecked(true);
        //binding.myBox.setChecked(true);



        //Button b = findViewById(R.id.button);

        //TextView tv = findViewById(R.id.textHelloWorld);

        // EditText et = findViewById(R.id.editText);


        //class MyListener implements View.OnClickListener {

        // @Override
        //public void onClick(View view) {
