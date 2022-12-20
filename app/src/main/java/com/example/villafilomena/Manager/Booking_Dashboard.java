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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

import java.util.HashMap;

public class Booking_Dashboard extends AppCompatActivity {
    final Context context = this;
    private static final int PICK_ROOM_IMAGE_REQUEST = 1, PICK_COTTAGE_IMAGE_REQUEST = 2;
    StorageReference RoomImageReference, CottageImageReference;
    Dialog dialog;
    Uri imageUri;
    ImageView addRoom, addCottage, RoomImage, CottageImage;
    EditText RoomCapacity, RoomRate, CottageCapacity, CottageRate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.booking_dashboard);

        RoomImageReference = FirebaseStorage.getInstance().getReference("RoomImages");
        CottageImageReference = FirebaseStorage.getInstance().getReference("CottageImages");

        addRoom = findViewById(R.id.booking_addRoom);
        addCottage = findViewById(R.id.booking_addCottage);

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

            RoomImage = dialog.findViewById(R.id.popup_RoomImage);
            RoomCapacity = dialog.findViewById(R.id.popup_RoomCapacity);
            RoomRate = dialog.findViewById(R.id.popup_RoomRate);
            Button Done = dialog.findViewById(R.id.popup_RoomDetail_Done);
            //imageprogressBar = dialog.findViewById(R.id.popup_manager_imageProgressBar);

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
        //imageprogressBar.setVisibility(View.VISIBLE);
        if (imageUri != null){
            StorageReference reference = RoomImageReference.child(System.currentTimeMillis()+"."+getfileExt(imageUri));

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