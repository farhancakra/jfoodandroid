package muhammad.farhan.jfood_android;
import android.app.AlertDialog;
import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SelesaiPesananActivity  extends AppCompatActivity {
    private int currentUserId = 0;
    private int currentInvoiceId = 0;

    private String nama_makanan = "0";
    TextView staticIdInvoice;
    TextView staticNamaCustomer;
    TextView staticNamaMakanan;
    TextView staticTanggalPesan;
    TextView staticTotalBiaya;
    TextView staticStatusInvoice;
    TextView staticTipePayment;
    TextView staticPromoCode;
    TextView staticDeliveryFee;

    TextView idInvoice;
    TextView namaCustomer;
    TextView namaMakanan;
    TextView tanggalPesan;
    TextView totalBiaya;
    TextView statusInvoice;
    TextView tipePayment;
    TextView promoCode;
    TextView deliveryFee;

    Button batal;
    Button selesai;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selesai_pesanan);

        staticIdInvoice = (TextView) findViewById(R.id.staticIdInvoice);
        staticNamaCustomer = (TextView) findViewById(R.id.staticNamaCustomer);
        staticNamaMakanan = (TextView) findViewById(R.id.staticNamaMakanan);
        staticTanggalPesan = (TextView) findViewById(R.id.staticTanggalPesan);
        staticTotalBiaya = (TextView) findViewById(R.id.staticTotalBiaya);
        staticStatusInvoice = (TextView) findViewById(R.id.staticStatusInvoice);
        staticTipePayment = (TextView) findViewById(R.id.staticTipePayment);
        staticPromoCode = (TextView) findViewById(R.id.staticPromoCode);
        staticDeliveryFee = (TextView) findViewById(R.id.staticDeliveryFee);


        staticIdInvoice.setVisibility(View.GONE);
        staticNamaCustomer.setVisibility(View.GONE);
        staticNamaMakanan.setVisibility(View.GONE);
        staticTanggalPesan.setVisibility(View.GONE);
        staticTotalBiaya.setVisibility(View.GONE);
        staticStatusInvoice.setVisibility(View.GONE);
        staticTipePayment.setVisibility(View.GONE);
        staticPromoCode.setVisibility(View.GONE);
        staticDeliveryFee.setVisibility(View.GONE);

        idInvoice = (TextView) findViewById(R.id.idInvoice);
        namaCustomer = (TextView) findViewById(R.id.namaCustomer);
        namaMakanan = (TextView) findViewById(R.id.namaMakanan);
        tanggalPesan = (TextView) findViewById(R.id.tanggalPesan);
        totalBiaya = (TextView) findViewById(R.id.totalBiaya);
        statusInvoice = (TextView) findViewById(R.id.statusInvoice);
        tipePayment = (TextView) findViewById(R.id.tipePayment);
        promoCode = (TextView) findViewById(R.id.promoCode);
        deliveryFee=(TextView) findViewById(R.id.deliveryfee);

        idInvoice.setVisibility(View.GONE);
        namaCustomer.setVisibility(View.GONE);
        namaMakanan.setVisibility(View.GONE);
        tanggalPesan.setVisibility(View.GONE);
        totalBiaya.setVisibility(View.GONE);
        statusInvoice.setVisibility(View.GONE);
        tipePayment.setVisibility(View.GONE);
        promoCode.setVisibility(View.GONE);
        deliveryFee.setVisibility(View.GONE);


        batal = (Button) findViewById(R.id.batal);
        selesai = (Button) findViewById(R.id.selesai);

        if(getIntent().getExtras()!=null)
        {
            Intent intent = getIntent();
            currentUserId = intent.getIntExtra("currentUserId", 0);
            currentInvoiceId = intent.getIntExtra("currentInvoiceId", 0);
        }

        fetchPesanan();

        //    AlertDialog.Builder builder1 = new AlertDialog.Builder(SelesaiPesananActivity.this);
        //  builder1.setMessage(Integer.toString(currentUserId)).create().show();

        batal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            if (jsonResponse != null) {
                                Toast.makeText(SelesaiPesananActivity.this, "Pesanan Batal", Toast.LENGTH_LONG).show();
                                Intent mainIntent = new Intent(SelesaiPesananActivity.this, MainActivity.class);
                                mainIntent.putExtra("currentUserId", currentUserId);
                                startActivity(mainIntent);
                            }
                        } catch (JSONException e) {
                            Toast.makeText(SelesaiPesananActivity.this, "JSON FAILED", Toast.LENGTH_LONG).show();
                        }
                    }
                };

                PesananBatalRequest pesananBatalRequest = new PesananBatalRequest(currentInvoiceId, "cancelled", responseListener);
                RequestQueue queue = Volley.newRequestQueue(SelesaiPesananActivity.this);
                queue.add(pesananBatalRequest);
            }
        });

        selesai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            if (jsonResponse != null) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(SelesaiPesananActivity.this);
                                builder.setMessage("Penyelesaian Pesanan Success").create().show();

                                Intent mainIntent = new Intent(SelesaiPesananActivity.this, MainActivity.class);
                                mainIntent.putExtra("currentUserId", currentUserId);
                                startActivity(mainIntent);
                            }
                        }
                        catch (JSONException e) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(SelesaiPesananActivity.this);
                            builder.setMessage("Penyelesaian Pesanan Failed").create().show();
                        }
                    }
                };

                PesananSelesaiRequest pesananSelesaiRequest = new PesananSelesaiRequest(currentInvoiceId,"SELESAI", responseListener);
                RequestQueue queue = Volley.newRequestQueue(SelesaiPesananActivity.this);
                queue.add(pesananSelesaiRequest);
            }
        });
    }



    public void fetchPesanan() {
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONArray jsonResponse = new JSONArray(response);
                    if (jsonResponse != null) {
                        Toast.makeText(SelesaiPesananActivity.this, "JSON success", Toast.LENGTH_LONG).show();
                        for (int i = 0; i < jsonResponse.length(); i++)
                        {
                            JSONObject invoice = jsonResponse.getJSONObject(i);
                            JSONObject customer = invoice.getJSONObject("customer");
                            JSONArray foods = invoice.getJSONArray("foods");
                            String status_invoice = invoice.getString("invoiceStatus");
                            if(status_invoice.equals("ONGOING")) {
                                staticIdInvoice.setVisibility(View.VISIBLE);
                                staticNamaCustomer.setVisibility(View.VISIBLE);
                                staticNamaMakanan.setVisibility(View.VISIBLE);
                                staticTotalBiaya.setVisibility(View.VISIBLE);
                                staticStatusInvoice.setVisibility(View.VISIBLE);
                                staticTipePayment.setVisibility(View.VISIBLE);
                                staticTanggalPesan.setVisibility(View.VISIBLE);

                                idInvoice.setVisibility(View.VISIBLE);
                                namaCustomer.setVisibility(View.VISIBLE);
                                namaMakanan.setVisibility(View.VISIBLE);
                                totalBiaya.setVisibility(View.VISIBLE);
                                statusInvoice.setVisibility(View.VISIBLE);
                                tipePayment.setVisibility(View.VISIBLE);
                                tanggalPesan.setVisibility(View.VISIBLE);

                                batal.setVisibility(View.VISIBLE);
                                selesai.setVisibility(View.VISIBLE);
//
                                for (int j = 0; j < foods.length(); j++)
                                {
                                    JSONObject food = foods.getJSONObject(j);
                                    String nama_makanan = food.getString("name");
                                    namaMakanan.setText(nama_makanan);
                                }
//
                                int id_invoice = invoice.getInt("id");
                                String nama_customer = customer.getString("name");
                                int total_biaya = invoice.getInt("totalPrice");
                                String tipe_payment = invoice.getString("paymentType");
                                String tanggal_pesan = invoice.getString("date");
                                if (tipe_payment.equals("CASH")){
                                    staticDeliveryFee.setVisibility(View.VISIBLE);
                                    deliveryFee.setVisibility(View.VISIBLE);
                                    int delivery_fee = invoice.getInt("deliveryFee");
                                    deliveryFee.setText(Integer.toString(delivery_fee));
                                }else if(tipe_payment.equals("CASHLESS")){
                                    if(!invoice.isNull("promo")){
                                        staticPromoCode.setVisibility(View.VISIBLE);
                                        promoCode.setVisibility(View.VISIBLE);
                                        JSONObject promo = invoice.getJSONObject("promo");
                                        String promo_code = promo.getString("code");
                                        promoCode.setText(promo_code);
                                    }
                                }
                                idInvoice.setText(Integer.toString(id_invoice));
                                namaCustomer.setText(nama_customer);
                                totalBiaya.setText(Integer.toString(total_biaya));
                                statusInvoice.setText(status_invoice);
                                tipePayment.setText(tipe_payment);
                                tanggalPesan.setText(tanggal_pesan);
                            }
                        }

                    }
                }catch(JSONException e){
                    Toast.makeText(SelesaiPesananActivity.this, "JSON Failed", Toast.LENGTH_LONG).show();
                    Intent mainIntent = new Intent(SelesaiPesananActivity.this, MainActivity.class);
                    mainIntent.putExtra("currentUserId", currentUserId);
                    startActivity(mainIntent);
                }
            }
        };

        PesananFetchRequest pesananFetchRequest = new PesananFetchRequest(currentUserId, responseListener);
        RequestQueue queue = Volley.newRequestQueue(SelesaiPesananActivity.this);
        queue.add(pesananFetchRequest);

    }





}
