package muhammad.farhan.jfood_android;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class CheckPromoRequest extends StringRequest {

    private static final String Promo_URL = "http://192.168.43.104:8080/promo/code/";
    private Map<String, String> params;

    public CheckPromoRequest(String promoCode, Response.Listener<String> listener) {
        super(Method.GET, Promo_URL+promoCode, listener, null);
        params = new HashMap<>();
    }
    @Override
    protected Map<String, String> getParams() throws AuthFailureError
    {
        return params;
    }
}