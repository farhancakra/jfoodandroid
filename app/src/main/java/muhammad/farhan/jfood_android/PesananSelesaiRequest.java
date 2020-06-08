package muhammad.farhan.jfood_android;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class PesananSelesaiRequest extends StringRequest {

    private static final String URL = "http://192.168.43.104:8080/invoice/invoiceStatus/";
    private Map<String, String> params;


    public PesananSelesaiRequest(int id_invoice, String status, Response.Listener<String> listener) {
        super(Method.PUT, URL+id_invoice, listener, null);
        params = new HashMap<>();
        params.put("status", status);
    }
    @Override
    protected Map<String, String> getParams() throws AuthFailureError
    {
        return params;
    }

}