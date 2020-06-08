package muhammad.farhan.jfood_android;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Seller> listSeller = new ArrayList<>();
    private ArrayList<Food> foodIdList = new ArrayList<>();
    private HashMap<Seller, ArrayList<Food>> childMapping = new HashMap<>();
    ExpandableListView expandableListView;
    MainListAdapter listAdapter;
    private int currentUserId;
    Button pesanan;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pesanan = (Button) findViewById(R.id.btnpesanan);
        expandableListView = findViewById(R.id.lvExp);


        if(getIntent().getExtras()!=null)
        {
            Intent intent = getIntent();
            currentUserId = intent.getIntExtra("idCustomer", 0);
        }
        refreshList();

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener(){
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Food selected = childMapping.get(listSeller.get(groupPosition)).get(childPosition);
                Intent buatPesanan = new Intent(MainActivity.this, BuatPesananActivity.class);
                buatPesanan.putExtra("currentUserId", currentUserId);
                buatPesanan.putExtra("id_food", selected.getId());
                buatPesanan.putExtra("foodName", selected.getName());
                buatPesanan.putExtra("foodCategory", selected.getCategory());
                buatPesanan.putExtra("foodPrice", selected.getPrice());
                startActivity(buatPesanan);
                return false;
            }
        });

        pesanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent selesaiPesananIntent = new Intent(MainActivity.this, SelesaiPesananActivity.class);
                selesaiPesananIntent.putExtra("currentUserId", currentUserId);
                startActivity(selesaiPesananIntent);
            }
        });

    }

    protected void refreshList() {
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonResponse = new JSONArray(response);

                        for (int i = 0; i < jsonResponse.length(); i++) {
                            JSONObject food = jsonResponse.getJSONObject(i);
                            JSONObject seller = food.getJSONObject("seller");
                            JSONObject location = seller.getJSONObject("location");

                            String province = location.getString("province");
                            String description = location.getString("description");
                            String city = location.getString("city");
                            Location location1 = new Location(province, description, city);

                            int id = seller.getInt("id");
                            String name = seller.getString("name");
                            String email = seller.getString("email");
                            String phoneNumber = seller.getString("phoneNumber");
                            Seller seller1 = new Seller(id, name, email, phoneNumber, location1);
                            listSeller.add(seller1);

                            int idFood = food.getInt("id");
                            String nameFood = food.getString("name");
                            int price = food.getInt("price");
                            String category = food.getString("category");
                            Food food1 = new Food(idFood, nameFood, seller1, price, category);
                            foodIdList.add(food1);

                            if (listSeller.isEmpty()){
                                listSeller.add(seller1);
                            } else {
                                for (Seller temp: listSeller) {
                                    if (temp.getName().equals(seller1.getName())) {
                                        break;
                                    } else {
                                        listSeller.add(seller1);
                                    }
                                }
                            }
                            foodIdList.add(food1);

                        }
                        for(Seller sel : listSeller) {
                            ArrayList<Food> temp = new ArrayList<>();
                            for(Food food2 : foodIdList){
                                if(food2.getSeller().getName().equals(sel.getName()) || food2.getSeller().getEmail().equals(sel.getEmail()) || food2.getSeller().getPhoneNumber().equals(sel.getPhoneNumber())){
                                    temp.add(food2);
                                }

                            } childMapping.put(sel,temp);

                        }

                } catch (JSONException e) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setMessage("Main Failed").create().show();
                }

                listAdapter = new MainListAdapter(MainActivity.this, listSeller, childMapping);
                expandableListView.setAdapter(listAdapter);
        }};

        MenuRequest menuRequest = new MenuRequest(responseListener);
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        queue.add(menuRequest);
    }
}
