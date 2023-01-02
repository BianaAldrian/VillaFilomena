package com.example.villafilomena.Login_Registration;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.preference.PreferenceManager;
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
import com.example.villafilomena.Guest.home_booking.MainFrame;
import com.example.villafilomena.IP_Address;
import com.example.villafilomena.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.HashMap;

public class Register_Guest extends AppCompatActivity {

    String IP;
    TextView line1_1, line2_1;

    GoogleSignInOptions gso;
    GoogleSignInClient gsc;

    TextInputEditText email, password, confirm_pass, fullname, contactNo, address;
    TextInputLayout pass_layout,Conpas_layout;
    Button Cont, back,register, cont_google;
    TextView login;
    String EMAIL;
    public static final String SHARED_PREFS = "";
    public static final String EMAIL_1 = "";

    boolean cont;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_guest);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);

        IP = preferences.getString("IP_Address", "").trim();

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc = GoogleSignIn.getClient(getApplication(),gso);

        email = findViewById(R.id.register_email);
        password = findViewById(R.id.register_password);
        confirm_pass = findViewById(R.id.confirm_password);
        pass_layout = findViewById(R.id.pass_layout);
        Conpas_layout = findViewById(R.id.Conpass_layout);
        contactNo = findViewById(R.id.contactNo);
        fullname = findViewById(R.id.fullname);
        address = findViewById(R.id.address);
        Cont = findViewById(R.id.register_continue);
        back = findViewById(R.id.back);
        register = findViewById(R.id.register_btnReg);
        cont_google = findViewById(R.id.register_contgoogle);
        login = findViewById(R.id.register_login);
        line1_1 = findViewById(R.id.line1_1);
        line2_1 = findViewById(R.id.line2_1);

        line1_1.setPaintFlags(line1_1.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);
        line2_1.setPaintFlags(line2_1.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);

        Cont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EMAIL = email.getText().toString();
                checkEmail();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cont.setVisibility(View.VISIBLE);
                back.setVisibility(View.GONE);
                register.setVisibility(View.GONE);
                fullname.setVisibility(View.GONE);
                address.setVisibility(View.GONE);
                contactNo.setVisibility(View.GONE);
                email.setVisibility(View.VISIBLE);
                password.setVisibility(View.VISIBLE);
                confirm_pass.setVisibility(View.VISIBLE);
                pass_layout.setVisibility(View.VISIBLE);
                Conpas_layout.setVisibility(View.VISIBLE);
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String Email = email.getText().toString();
                String pass = password.getText().toString();
                String fname = fullname.getText().toString();
                String contact = contactNo.getText().toString();
                String addr = address.getText().toString();

                if(Email.equals("")||pass.equals("")||fname.equals("")||contact.equals("")||addr.equals("")){
                    Toast.makeText(getApplicationContext(), "Please fill all information", Toast.LENGTH_SHORT).show();
                }
                else if (!password.getText().toString().equals(confirm_pass.getText().toString())){
                    Toast.makeText(getApplicationContext(), "Password not matched", Toast.LENGTH_SHORT).show();
                }
                else {
                    String url = "http://"+IP+"/VillaFilomena/register.php";
                    RequestQueue myrequest = Volley.newRequestQueue(getApplicationContext());
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString(EMAIL_1, Email);
                            editor.apply();
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
                            map.put("fullname",fullname.getText().toString());
                            map.put("contactNo",contactNo.getText().toString());
                            map.put("address",address.getText().toString());

                            startActivity(new Intent(getApplication(), MainFrame.class));
                            finish();

                            return map;
                        }
                    };
                    myrequest.add(stringRequest);
                }
            }
        });

        cont_google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cont = true;
                signUp();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplication(), Login_Guest.class));
                finish();
                gsc.signOut();
            }
        });
    }

    private void signUp(){
        Intent signInIntent = gsc.getSignInIntent();
        startActivityForResult(signInIntent,1000);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
        if(requestCode == 1000){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);

            try {
                task.getResult(ApiException.class);
                //Toast.makeText(getApplicationContext(), "Welcome", Toast.LENGTH_SHORT).show();
                if(acct != null){
                    EMAIL = acct.getEmail();
                    checkEmail();
                }
            }catch (ApiException e) {
                Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void checkEmail(){
        String url1 = "http://"+IP+"/VillaFilomena/check_email.php";
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
        RequestQueue myrequest = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url1, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(cont == true && !response.equals("true")){
                    if(acct != null){
                        email.setText(acct.getEmail());
                        fullname.setText(acct.getDisplayName());
                    }
                }
                else if(response.equals("true")){
                    Toast.makeText(getApplicationContext(), "Email already used", Toast.LENGTH_LONG).show();
                    gsc.signOut();
                }
                else {
                    Cont.setVisibility(View.GONE);
                    back.setVisibility(View.VISIBLE);
                    register.setVisibility(View.VISIBLE);
                    fullname.setVisibility(View.VISIBLE);
                    contactNo.setVisibility(View.VISIBLE);
                    address.setVisibility(View.VISIBLE);
                    email.setVisibility(View.GONE);
                    password.setVisibility(View.GONE);
                    confirm_pass.setVisibility(View.GONE);
                    pass_layout.setVisibility(View.GONE);
                    Conpas_layout.setVisibility(View.GONE);

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
                map.put("email",EMAIL);
                return map;
            }
        };
        myrequest.add(stringRequest);
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        startActivity(new Intent(getApplication(), Login_Guest.class));
        finish();
    }
}