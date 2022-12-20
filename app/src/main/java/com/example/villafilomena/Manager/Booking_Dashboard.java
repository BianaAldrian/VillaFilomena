package com.example.villafilomena.Manager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
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
import com.example.villafilomena.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.HashMap;

public class Booking_Dashboard extends AppCompatActivity {
    final Context context = this;
    private static final int PICK_ROOM_IMAGE_REQUEST = 1, PICK_COTTAGE_IMAGE_REQUEST = 2;
    StorageReference RoomImageReference, CottageImageReference;
    Dialog dialog;
    Uri imageUri;
    ImageView edit, save, FeeDetails, addRoom, addCottage, RoomImage, CottageImage;
    EditText RoomName, RoomCapacity, RoomRate, CottageCapacity, CottageRate;
    ProgressBar Roomprogress;

    //popup_feedetails
    TextView txtDaytour, txtDaytourKidAge, txtDaytourKidFee, txtDaytourAdultAge, txtDaytourAdultFee,
            txtNighttour, txtNighttourKidAge, txtNighttourKidFee, txtNighttourAdultAge, txtNighttourAdultFee;
    EditText DaytourTime, NighttourTime;
    LinearLayout linear1, linear2, linear3, linear4;
    Button Next, Back, Done;
    ImageView Daytour_KidAgeFee_edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.booking_dashboard);

        RoomImageReference = FirebaseStorage.getInstance().getReference("RoomImages");
        CottageImageReference = FirebaseStorage.getInstance().getReference("CottageImages");

        edit = findViewById(R.id.managerBooking_edit);
        save = findViewById(R.id.managerBooking_save);
        FeeDetails = findViewById(R.id.manager_FeeDetails);
        addRoom = findViewById(R.id.booking_addRoom);
        addCottage = findViewById(R.id.booking_addCottage);

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit.setVisibility(View.GONE);
                save.setVisibility(View.VISIBLE);
                addRoom.setVisibility(View.VISIBLE);
                addCottage.setVisibility(View.VISIBLE);
                FeeDetails.setVisibility(View.VISIBLE);
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit.setVisibility(View.VISIBLE);
                save.setVisibility(View.GONE);
                addRoom.setVisibility(View.GONE);
                addCottage.setVisibility(View.GONE);
                FeeDetails.setVisibility(View.GONE);
            }
        });

        FeeDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FeeDetail();
            }
        });

        addRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseRoomImage();
            }
        });

        addCottage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void FeeDetail(){
        ArrayList<String> kidAge = new ArrayList<>();

        dialog = new Dialog(context);
        dialog.setContentView(R.layout.popup_feedetails);

        txtDaytour = dialog.findViewById(R.id.popup_booking_txtDaytour);
        DaytourTime = dialog.findViewById(R.id.popup_booking_DayTourTime);
        linear1 = dialog.findViewById(R.id.popup_booking_lnrlyt1);
        txtDaytourKidAge = dialog.findViewById(R.id.popup_booking_txtDaytourKidAge);
        txtDaytourKidFee = dialog.findViewById(R.id.popup_booking_txtDaytourKidFee);
        Daytour_KidAgeFee_edit = dialog.findViewById(R.id.Daytour_kidAgeFee_Edit);
        linear2 = dialog.findViewById(R.id.popup_booking_lnrlyt2);
        txtDaytourAdultAge = dialog.findViewById(R.id.popup_booking_txtDaytourAdultAge);
        txtDaytourAdultFee = dialog.findViewById(R.id.popup_booking_txtDaytourAdultFee);
        txtNighttour = dialog.findViewById(R.id.popup_booking_txtNighttour);
        NighttourTime = dialog.findViewById(R.id.popup_booking_NightTourTime);
        linear3 = dialog.findViewById(R.id.popup_booking_lnrlyt3);
        txtNighttourKidAge = dialog.findViewById(R.id.popup_booking_txtNighttourKidAge);
        txtNighttourKidFee = dialog.findViewById(R.id.popup_booking_txtNighttourKidFee);
        linear4 = dialog.findViewById(R.id.popup_booking_lnrlyt4);
        txtNighttourAdultAge = dialog.findViewById(R.id.popup_booking_txtNighttourAdultAge);
        txtNighttourAdultFee = dialog.findViewById(R.id.popup_booking_txtNighttourAdultFee);
        Next = dialog.findViewById(R.id.popup_booking_btnNext);
        Back = dialog.findViewById(R.id.popup_booking_btnBack);
        Done = dialog.findViewById(R.id.popup_booking_btnDone);

        for (int i=1; i<18; i++){
            kidAge.add(String.valueOf(i));
        }

        Dialog dialog1 = new Dialog(dialog.getContext());
        dialog1.setContentView(R.layout.popup_age_fee);

        Spinner DayKidAge_Min = dialog1.findViewById(R.id.popup_kidAge_Min);
        Spinner DayKidAge_Max = dialog1.findViewById(R.id.popup_kidAge_Max);
        Button Done = dialog1.findViewById(R.id.popup_Agefee_Done);

        ArrayAdapter adapter = new ArrayAdapter(dialog.getContext(), R.layout.spinner_age, kidAge);
        DayKidAge_Min.setAdapter(adapter);
        DayKidAge_Max.setAdapter(adapter);

        Done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        Daytour_KidAgeFee_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.show();
            }
        });

        Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtDaytour.setVisibility(View.GONE);
                DaytourTime.setVisibility(View.GONE);
                linear1.setVisibility(View.GONE);
                linear2.setVisibility(View.GONE);
                Next.setVisibility(View.GONE);
                txtNighttour.setVisibility(View.VISIBLE);
                NighttourTime.setVisibility(View.VISIBLE);
                linear3.setVisibility(View.VISIBLE);
                linear4.setVisibility(View.VISIBLE);
                Back.setVisibility(View.VISIBLE);
                Done.setVisibility(View.VISIBLE);
            }
        });
        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtDaytour.setVisibility(View.VISIBLE);
                DaytourTime.setVisibility(View.VISIBLE);
                linear1.setVisibility(View.VISIBLE);
                linear2.setVisibility(View.VISIBLE);
                Next.setVisibility(View.VISIBLE);
                txtNighttour.setVisibility(View.GONE);
                NighttourTime.setVisibility(View.GONE);
                linear3.setVisibility(View.GONE);
                linear4.setVisibility(View.GONE);
                Back.setVisibility(View.GONE);
                Done.setVisibility(View.GONE);
            }
        });
        Done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.hide();
            }
        });

        dialog.show();
    }

    private void chooseRoomImage(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_ROOM_IMAGE_REQUEST);
    }

    private void chooseCottageImage(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_COTTAGE_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_ROOM_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();

            dialog = new Dialog(context);
            dialog.setContentView(R.layout.popup_roomdetails);

            RoomName = dialog.findViewById(R.id.popup_RoomName);
            RoomImage = dialog.findViewById(R.id.popup_RoomImage);
            RoomCapacity = dialog.findViewById(R.id.popup_RoomCapacity);
            RoomRate = dialog.findViewById(R.id.popup_RoomRate);
            Button Done = dialog.findViewById(R.id.popup_RoomDetail_Done);
            Roomprogress = dialog.findViewById(R.id.popup_Room_progressbar);

            RoomImage.setImageURI(imageUri);

            Done.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    UploadRoomImage();
                }
            });
            dialog.show();
        }
        else if (requestCode == PICK_COTTAGE_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null){
            imageUri = data.getData();

            dialog = new Dialog(context);
            dialog.setContentView(R.layout.popup_cottagedetails);

            CottageImage = dialog.findViewById(R.id.popup_CottageImage);
            CottageCapacity = dialog.findViewById(R.id.popup_CottageCapacity);
            CottageRate = dialog.findViewById(R.id.popup_CottageRate);
            Button Done = dialog.findViewById(R.id.popup_CottageDetail_Done);
            //imageprogressBar = dialog.findViewById(R.id.popup_manager_imageProgressBar);

            CottageImage.setImageURI(imageUri);

            Done.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    UploadRoomImage();
                }
            });
            dialog.show();
        }
    }

    private String getfileExt(Uri MyUri){
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(MyUri));
    }

    private void UploadRoomImage(){
        Roomprogress.setVisibility(View.VISIBLE);
        if (imageUri != null){
            StorageReference reference = RoomImageReference.child(RoomName.getText().toString()+"."+getfileExt(imageUri));

            reference.putFile(imageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Roomprogress.setVisibility(View.INVISIBLE);
                            dialog.hide();

                            reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    String imageUrl = uri.toString();

                                    String url = "http://"+ IP_Address.IP_Address+"/VillaFilomena/room_details.php";
                                    RequestQueue myrequest = Volley.newRequestQueue(getApplicationContext());
                                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            if (response.equals("Success")){
                                                Toast.makeText(context, "Upload Successful", Toast.LENGTH_SHORT).show();
                                            }
                                            else if(response.equals("Failed")){
                                                Toast.makeText(context, "Upload Failed", Toast.LENGTH_SHORT).show();
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
                                            map.put("imageUrl",imageUrl);
                                            map.put("name",RoomName.getText().toString());
                                            map.put("room_capacity",RoomCapacity.getText().toString());
                                            map.put("room_rate", RoomRate.getText().toString());
                                            return map;
                                        }
                                    };
                                    myrequest.add(stringRequest);
                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }else{
            Toast.makeText(context, "No file selected", Toast.LENGTH_SHORT).show();
        }
    }

    private void UploadCottageImage(){
        //imageprogressBar.setVisibility(View.VISIBLE);
        if (imageUri != null){
            StorageReference reference = CottageImageReference.child(System.currentTimeMillis()+"."+getfileExt(imageUri));

            reference.putFile(imageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            //imageprogressBar.setVisibility(View.INVISIBLE);
                            dialog.hide();

                            reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    String imageUrl = uri.toString();

                                    /*String url = "http://"+ IP_Address.IP_Address+"/VillaFilomena/image_upload.php";
                                    RequestQueue myrequest = Volley.newRequestQueue(getApplicationContext());
                                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            if (response.equals("Upload Success")){
                                                Toast.makeText(context, "Upload Successful", Toast.LENGTH_SHORT).show();
                                            }
                                            else if(response.equals("Upload Failed")){
                                                Toast.makeText(context, "Upload Failed", Toast.LENGTH_SHORT).show();
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
                                            map.put("name",System.currentTimeMillis()+"."+getfileExt(imageUri));
                                            map.put("url",imageUrl);
                                            return map;
                                        }
                                    };
                                    myrequest.add(stringRequest);*/
                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }else{
            Toast.makeText(context, "No file selected", Toast.LENGTH_SHORT).show();
        }
    }

}