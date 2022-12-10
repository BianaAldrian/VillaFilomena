package com.example.villafilomena.Login_Registration;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.villafilomena.Guest.home_reservation.MainFrame;
import com.example.villafilomena.IP_Address;
import com.example.villafilomena.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;

import java.util.HashMap;

public class Login_Guest extends AppCompatActivity {

    TextView line1, line2;

    GoogleSignInOptions gso;
    GoogleSignInClient gsc;



    Button login, cont_google;
    TextView register;
    TextInputEditText password,email;

    public static final String SHARED_PREFS = "";
    public static final String EMAIL = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_guest);

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc = GoogleSignIn.getClient(getApplication(),gso);

        email = findViewById(R.id.loginGeust_email);
        password = findViewById(R.id.loginGuest_password);
        login = findViewById(R.id.loginGeust_btnLog);
        cont_google = findViewById(R.id.loginGuest_contgoogle);
        register = findViewById(R.id.login_register);
        line1 = findViewById(R.id.line1);
        line2 = findViewById(R.id.line2);

        line1.setPaintFlags(line1.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);
        line2.setPaintFlags(line2.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "http://"+IP_Address.IP_Address+":8080/VillaFilomena/login.php";
                    //Toast.makeText(getApplicationContext(), "Account already exists", Toast.LENGTH_SHORT).show();
                RequestQueue myrequest = Volley.newRequestQueue(getApplicationContext());
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.equals("not_exist")){
                            Toast.makeText(getApplicationContext(), "Account doesn't exist", Toast.LENGTH_SHORT).show();
                        }
                        else if(response.equals("true")){

                            SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString(EMAIL, email.getText().toString());
                            editor.apply();

                            startActivity(new Intent(getApplicationContext(), MainFrame.class));
                            finish();
                            //Toast.makeText(getApplicationContext(), "True", Toast.LENGTH_SHORT).show();
                        }
                        else if(response.equals("false")){
                            Toast.makeText(getApplicationContext(),"Incorrect Password", Toast.LENGTH_LONG).show();
                        }
                    }
                },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(getApplicationContext(),error.getMessage().toString(), Toast.LENGTH_LONG).show();
                            }
                        })
                {
                    @Override
                    protected HashMap<String,String> getParams() throws AuthFailureError {
                        HashMap<String,String> map = new HashMap<String,String>();
                        map.put("email",email.getText().toString());
                        map.put("password",password.getText().toString());
                        return map;
                    }
                };
                myrequest.add(stringRequest);
            }
        });

        cont_google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplication(),Register_Guest.class));
                finish();
            }
        });
    }

    private void signIn(){
        Intent signInIntent = gsc.getSignInIntent();
        startActivityForResult(signInIntent,1000);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String url = "http://"+IP_Address.IP_Address+":8080/VillaFilomena/login_google.php";

        if(requestCode == 1000){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);

            try {
                task.getResult(ApiException.class);
                //Toast.makeText(getApplicationContext(), "Welcome", Toast.LENGTH_SHORT).show();

                GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
                if(acct != null){
                    RequestQueue myrequest = Volley.newRequestQueue(getApplicationContext());
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if(response.equals("true")){
                                Intent intent = new Intent(getApplication(),MainFrame.class);
                                startActivity(intent);
                                finish();
                                Toast.makeText(Login_Guest.this, "True", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Toast.makeText(getApplicationContext(),response, Toast.LENGTH_LONG).show();
                                gsc.signOut();
                            }
                        }
                    },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(getApplicationContext(),error.getMessage().toString(), Toast.LENGTH_LONG).show();
                                }
                            })
                    {
                        @Override
                        protected HashMap<String,String> getParams() throws AuthFailureError {
                            HashMap<String,String> map = new HashMap<String,String>();
                            map.put("email",acct.getEmail());
                            map.put("password",password.getText().toString());

                            return map;
                        }
                    };
                    myrequest.add(stringRequest);
                }
            }catch (ApiException e) {
                Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        }
    }
}