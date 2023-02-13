package algonquin.cst2335.anda0017;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        //returns an intent object
        Intent fromPrevious = getIntent();
        String emailAddress = fromPrevious.getStringExtra("EmailAddress");
        TextView email = findViewById(R.id.textView3);
        email.setText("Welcome Back " + emailAddress);


        Button phoneCall = (Button) findViewById(R.id.call);
        EditText phoneNumber = (EditText) findViewById(R.id.editTextNumber);

        SharedPreferences sharedPreferences = getSharedPreferences("MyPhoneData", Context.MODE_PRIVATE);
        String phone = sharedPreferences.getString("Phone number", "");
        phoneNumber.setText(phone);

        phoneCall.setOnClickListener(clk -> {
            Intent call = new Intent(Intent.ACTION_DIAL);
            call.setData(Uri.parse("tel: " + phoneNumber.getText()));
            startActivity(call);
        });

        ImageView profileImage = (ImageView) findViewById(R.id.imageButton);
        Button changePicture = (Button) findViewById(R.id.button3);

        ActivityResultLauncher<Intent> cameraResult = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {

                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        Bitmap thumbnail = data.getParcelableExtra("data");
                        profileImage.setImageBitmap(thumbnail);


                        FileOutputStream fOut = null;

                        File file = new File( getFilesDir(), "Picture.png");

                        if(file.exists())

                        {
                            Bitmap theImage=
                            BitmapFactory.decodeFile("/data/data/algonquin.cst2335.anda0017/files/Picture.png");
                            profileImage.setImageBitmap( theImage);
                        }
                        try {
                            fOut = openFileOutput("Picture.png", Context.MODE_PRIVATE);
                            thumbnail.compress(Bitmap.CompressFormat.PNG, 100, fOut);
                            fOut.flush();
                            fOut.close();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        }
                    }
                });
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        changePicture.setOnClickListener(clk -> {
            cameraResult.launch(cameraIntent);
        });
    }

    protected void onPause() {
        super.onPause();

        EditText phoneNumber = (EditText) findViewById(R.id.editTextNumber);

        SharedPreferences sharedPreferences = getSharedPreferences
                ("MyPhoneData", Context.MODE_PRIVATE);
        SharedPreferences.Editor ed = sharedPreferences.edit();
        ed.putString("Phone number" , phoneNumber.getText().toString());
        ed.apply();
    }
}








