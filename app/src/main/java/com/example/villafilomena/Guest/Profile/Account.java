package com.example.villafilomena.Guest.Profile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
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
import com.example.villafilomena.IP_Address;
import com.example.villafilomena.Login_Registration.Login_Guest;
import com.example.villafilomena.Login_Registration.Register_Guest;
import com.example.villafilomena.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

public class Account extends AppCompatActivity {
    GoogleSignInOptions gso;
    GoogleSignInClient gsc;
    GoogleSignInAccount acct;
    SharedPreferences sharedPreferences, sharedPreferences1;
    String url = "http://"+IP_Address.IP_Address+":8080/VillaFilomena/get_accountInfo.php";

    Button signout,login,editProf;
    TextView name, contact, address, profEmail;
    String email, log_email, reg_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account);

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc = GoogleSignIn.getClient(getApplicationContext(),gso);

        editProf = findViewById(R.id.editProf);
        signout = findViewById(R.id.signout);
        login = findViewById(R.id.prof_login);
        name = findViewById(R.id.name);
        contact = findViewById(R.id.contact);
        address = findViewById(R.id.profAddress);
        profEmail = findViewById(R.id.profEmail);

        sharedPreferences = getSharedPreferences(Login_Guest.SHARED_PREFS, MODE_PRIVATE);
        log_email = sharedPreferences.getString(Login_Guest.EMAIL, "");

        sharedPreferences1 = getSharedPreferences(Register_Guest.SHARED_PREFS, MODE_PRIVATE);
        reg_email = sharedPreferences1.getString(Register_Guest.EMAIL_1,"");

        acct = GoogleSignIn.getLastSignedInAccount(getApplicationContext());

        if(!log_email.equals("")){
            email = log_email;
            getData();
        }
        else if(!reg_email.equals("")){
            email = reg_email;
            getData();
        }
        else if(log_email.equals("") && reg_email.equals("")){
            if(acct != null){
                email = acct.getEmail();
                getData();
            }
            else {
                signout.setVisibility(View.GONE);
                login.setVisibility(View.VISIBLE);
            }
        }

        editProf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Edit_PofileAcc.class));
            }
        });

        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gsc.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        SharedPreferences.Editor editor1 = sharedPreferences1.edit();
                        editor.clear();
                        editor.commit();
                        editor1.clear();
                        editor1.commit();

                        finish();
                        startActivity(new Intent(getApplicationContext(), Login_Guest.class));
                    }
                });
            }
        });
    }

    public void getData(){
        signout.setVisibility(View.VISIBLE);
        login.setVisibility(View.GONE);
        RequestQueue myrequest = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");
                    JSONArray jsonArray = jsonObject.getJSONArray("data");

                    if(success.equals("1")){
                        for (int i=0; i<jsonArray.length(); i++){
                            JSONObject object = jsonArray.getJSONObject(i);

                            String email = object.getString("email");
                            String fullname = object.getString("fullname");
                            String cont = object.getString("contact");
                            String addre = object.getString("address");

                            name.setText(fullname);
                            contact.setText(cont);
                            address.setText(addre);
                            profEmail.setText(email);
                        }
                    }else{
                        Toast.makeText(getApplicationContext(), "Failed to get", Toast.LENGTH_SHORT).show();
                    }

                }catch (Exception e){
                    Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
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
                map.put("email", email);

                return map;
            }
        };
        myrequest.add(stringRequest);
    }
}