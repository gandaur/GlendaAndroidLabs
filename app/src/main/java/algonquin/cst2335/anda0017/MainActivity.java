package algonquin.cst2335.anda0017;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import algonquin.cst2335.anda0017.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    protected String cityName;
    protected RequestQueue queue = null;
    Bitmap image;
    String url = null;
    String imgUrl = "https://openweathermap.org/img/w/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        queue = Volley.newRequestQueue(this);

        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.getForecastButton.setOnClickListener(click -> {
            cityName = binding.cityEditView.getText().toString();

            try {
                url = "https://api.openweathermap.org/data/2.5/weather?q=" + URLEncoder.encode(cityName, "UTF-8") + "&appid=7e943c97096a9784391a981c4d878b22&units=metric";
                JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, (response) -> {
                    try {
                        JSONObject coord = response.getJSONObject("coord");
                        JSONArray weatherArray = response.getJSONArray("weather");
                        JSONObject position0 = weatherArray.getJSONObject(0);
                        String description = position0.getString("description");
                        String iconName = position0.getString("icon");
                        JSONObject mainObject = response.getJSONObject("main");
                        double current = mainObject.getDouble("temp");
                        double min = mainObject.getDouble("temp_min");
                        double max = mainObject.getDouble("temp_max");
                        int humidity = mainObject.getInt("humidity");

                        String pathname = getFilesDir() + "/" + iconName + ".png";
                        File file = new File(pathname);
                        if (file.exists()) {
                            image = BitmapFactory.decodeFile(pathname);
                        } else {
                            ImageRequest imgReq = new ImageRequest(imgUrl + iconName + ".png", new Response.Listener<Bitmap>() {
                                @Override
                                public void onResponse(Bitmap bitmap) {
                                    image = bitmap;
                                    FileOutputStream fOut = null;
                                    try {
                                        fOut = openFileOutput(iconName + ".png", Context.MODE_PRIVATE);
                                        image.compress(Bitmap.CompressFormat.PNG, 100, fOut);
                                        fOut.flush();
                                        fOut.close();
                                    } catch (FileNotFoundException e) {
                                        e.printStackTrace();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }

                                }
                            }, 1024, 1024, ImageView.ScaleType.CENTER, null, (error) -> {

                            });

                            queue.add(imgReq);
                        }

                        runOnUiThread(() -> {

                            binding.tempText.setText("The current temperature is " + current);
                            binding.tempText.setVisibility(View.VISIBLE);

                            binding.minText.setText("The min temperature is " + min);
                            binding.minText.setVisibility(View.VISIBLE);

                            binding.maxText.setText("The max temperature is " + max);
                            binding.maxText.setVisibility(View.VISIBLE);

                            binding.humidityText.setText("The humidity is " + humidity + "%");
                            binding.humidityText.setVisibility(View.VISIBLE);

                            binding.icon.setImageBitmap(image);
                            binding.icon.setVisibility(View.VISIBLE);

                            binding.descriptionText.setText(description);
                            binding.descriptionText.setVisibility(View.VISIBLE);

                        });


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, (error) -> { });

                queue.add(request);


            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        });

    }




}