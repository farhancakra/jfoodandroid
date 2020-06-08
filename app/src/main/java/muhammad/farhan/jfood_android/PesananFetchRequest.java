package muhammad.farhan.jfood_android;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class PesananFetchRequest extends StringRequest {
    private static final String URL = "http://192.168.43.104:8080/invoice/customer/";
    private Map<String, String> params;

    public PesananFetchRequest(int id_customer, Response.Listener<String> listener) {
        super(Method.GET, URL+id_customer, listener, null);
        params = new HashMap<>();
    }
    @Override
    protected Map<String, String> getParams() throws AuthFailureError
    {
        return params;
    }
}

