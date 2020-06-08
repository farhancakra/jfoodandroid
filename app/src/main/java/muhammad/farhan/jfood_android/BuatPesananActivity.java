package muhammad.farhan.jfood_android;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class BuatPesananActivity extends AppCompatActivity {
    private int currentUserId;
    private int id_food;
    private String foodName = "0";
    private String foodCategory = "0";
    private double foodPrice = 0;
    private String promoCode = "0";
    private String selectedPayment = "CASH";
    private int deliveryFee = 0;
    private int promoDiscount;
    private int promoMinPrice;
    private boolean promoActive;
    private int totalPrice = (int) (deliveryFee + foodPrice);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buat_pesanan);
        final TextView textCode = (TextView) findViewById(R.id.textCode);
        final TextView staticDeliveryFee = (TextView) findViewById(R.id.staticDeliveryFee);
        final TextView food_name = (TextView) findViewById(R.id.food_name);
        final TextView food_category = (TextView) findViewById(R.id.food_category);
        final TextView food_price = (TextView) findViewById(R.id.food_price);
        final TextView delivery_fee = (TextView) findViewById(R.id.delivery_fee);
        final EditText promo_code = (EditText) findViewById(R.id.promo_code);
        final TextView total_price = (TextView) findViewById(R.id.total_price);
        final RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        final Button hitung = (Button) findViewById(R.id.hitung);
        final Button pesan = (Button) findViewById(R.id.pesan);

        if(getIntent().getExtras()!=null)
        {
            Intent intent = getIntent();
            currentUserId = intent.getIntExtra("currentUserId", 0);
            id_food = intent.getIntExtra("id_food", 0);
            foodName = intent.getStringExtra("foodName");
            foodCategory = intent.getStringExtra("foodCategory");
            foodPrice = intent.getIntExtra("foodPrice", 0);
            AlertDialog.Builder builder = new AlertDialog.Builder(BuatPesananActivity.this);
            builder.setMessage("Current Id = " + currentUserId).create().show();;
        }

        final LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params1.setMargins(0,210,0,0);
        delivery_fee.setLayoutParams(params1);

        final LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(0,32,0,0);
        total_price.setLayoutParams(params);

        pesan.setVisibility(View.GONE);
        textCode.setVisibility(View.GONE);
        promo_code.setVisibility(View.GONE);

        food_name.setText(foodName);
        food_category.setText(foodCategory);
        food_price.setText(Double.toString(foodPrice));
        total_price.setText("0");

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.cashless)
                {
                    textCode.setVisibility(View.VISIBLE);
                    promo_code.setVisibility(View.VISIBLE);
                    staticDeliveryFee.setVisibility(View.GONE);
                    delivery_fee.setVisibility(View.GONE);
                    params.setMargins(0,56,0,0);
                    total_price.setLayoutParams(params);
                    selectedPayment = "cashless";
                }
                else if(checkedId == R.id.cash)
                {
                    textCode.setVisibility(View.GONE);
                    promo_code.setVisibility(View.GONE);
                    staticDeliveryFee.setVisibility(View.VISIBLE);
                    delivery_fee.setVisibility(View.VISIBLE);
                    params1.setMargins(0,210,0,0);
                    delivery_fee.setLayoutParams(params1);
                    params.setMargins(0,32,0,0);
                    total_price.setLayoutParams(params);
                    selectedPayment = "cash";
                }
                hitung.setVisibility(View.VISIBLE);
                pesan.setVisibility(View.GONE);
            }
        });

        hitung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(selectedPayment.equals("cash"))
                {
                    deliveryFee = Integer.valueOf(delivery_fee.getText().toString());
                    total_price.setText(Double.toString(foodPrice + deliveryFee));
                }
                else if(selectedPayment.equals("cashless"))
                {

                    if(!promo_code.getText().toString().isEmpty()){
                        promoCode = promo_code.getText().toString();
                        Response.Listener<String> responseListener = new Response.Listener<String>(){
                            @Override
                            public void onResponse(String response) {
                                try{
                                    JSONObject jsonObject = new JSONObject(response);
                                    if(jsonObject != null) {
                                        promoDiscount = jsonObject.getInt("discount");
                                        promoMinPrice = jsonObject.getInt("minPrice");
                                        promoActive = jsonObject.getBoolean("active");
                                        if (promoActive && foodPrice > promoMinPrice){
                                            total_price.setText(String.valueOf(Integer.parseInt(food_price.toString())-promoDiscount));
                                        }
                                    }
                                } catch (JSONException e){
                                    Toast.makeText(BuatPesananActivity.this, "Promo Code Not Found", Toast.LENGTH_LONG).show();
                                }
                            }
                        };
                        CheckPromoRequest promoRequest = new CheckPromoRequest(promoCode, responseListener);
                        RequestQueue queue = Volley.newRequestQueue(BuatPesananActivity.this);
                        queue.add(promoRequest);
                    }else{
                        Toast.makeText(BuatPesananActivity.this,"Please, Fill Promo Code Field", Toast.LENGTH_LONG).show();
                    }
                }



                hitung.setVisibility(View.GONE);
                pesan.setVisibility(View.VISIBLE);

            }
        });

        pesan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                BuatPesananRequest buatPesananRequest = null;

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    Intent intent = getIntent();
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            if (jsonResponse != null){
                                AlertDialog.Builder builder = new AlertDialog.Builder(BuatPesananActivity.this);
                                builder.setMessage("Buat Pesanan Berhasil").create().show();
                                Intent mainIntent = new Intent(BuatPesananActivity.this, MainActivity.class);
                                mainIntent.putExtra("currentUserId", currentUserId);
                                startActivity(mainIntent);
                            }
                        }catch (JSONException e){
                            AlertDialog.Builder builder = new AlertDialog.Builder(BuatPesananActivity.this);
                            builder.setMessage("Buat Pesanan gagal").create().show();
                        }
                    }
                };

                if(selectedPayment.equals("cash"))
                {
                    deliveryFee = Integer.valueOf(delivery_fee.getText().toString());
                    buatPesananRequest = new BuatPesananRequest(id_food+"", deliveryFee, currentUserId+"", responseListener);
                }

                else if(selectedPayment.equals("cashless"))
                {
                    promoCode = promo_code.getText().toString();
                    buatPesananRequest = new BuatPesananRequest(id_food+"", promoCode, currentUserId+"", responseListener);
                }

                RequestQueue queue = Volley.newRequestQueue(BuatPesananActivity.this);
                queue.add(buatPesananRequest);
            }
        });
    }
}








