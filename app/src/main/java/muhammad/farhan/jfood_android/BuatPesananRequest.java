package muhammad.farhan.jfood_android;

import com.android.volley.toolbox.StringRequest;

import com.android.volley.toolbox.StringRequest;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class BuatPesananRequest extends StringRequest {
    public static final String URL_CASH = "http://192.168.43.104:8080/invoice/createCashInvoice";
    public static final String URL_CASHLESS = "http://192.168.43.104:8080/invoice/createCashlessInvoice";
    private Map<String, String> params;

    public BuatPesananRequest(String id_food, int deliveryFee, String id_customer, Response.Listener<String> listener) {
        super(Method.POST, URL_CASH, listener, null);

        String deliveryFee1 = Integer.toString(deliveryFee);
        params = new HashMap<>();
        this.params.put("foodIdList", id_food);
        this.params.put("customerId", id_customer);
        this.params.put("deliveryFee", deliveryFee1);
    }

    public BuatPesananRequest(String id_food, String promo_code, String id_customer, Response.Listener<String> listener) {
        super(Method.POST, URL_CASHLESS, listener, null);


        params = new HashMap<>();
        params.put("foodIdList", id_food);
        params.put("promoCode", promo_code);
        params.put("customerId", id_customer);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
