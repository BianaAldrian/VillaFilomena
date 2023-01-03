package com.example.villafilomena.Manager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.VideoView;

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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;

public class GuestHomePage_Dashboard extends AppCompatActivity {
    String IP;

    final Context context = this;

    private static final int PICK_IMAGE_REQUEST = 1, PICK_VIDEO_REQUEST = 2;

    StorageReference videostorageReference, imagestorageReference;
    DatabaseReference databaseReference;
    Dialog dialog;
    ImageView addBanner,addVideo;
    Uri videoUri, imageUri;
    ProgressBar progressBar,imageprogressBar;
    VideoView videoView;
    EditText videoName;
    Button upload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.guest_homepage_dashboard);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        IP = preferences.getString("IP_Address", "").trim();

        addBanner = findViewById(R.id.add_homeBanner);
        addVideo = findViewById(R.id.add_homeVideo);

        imagestorageReference = FirebaseStorage.getInstance().getReference("images");
        videostorageReference = FirebaseStorage.getInstance().getReference("videos");
        databaseReference = FirebaseDatabase.getInstance().getReference("videos");

        addBanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });
        addVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseVideo();
            }
        });
    }

    private void chooseImage(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    private void chooseVideo() {
        Intent intent = new Intent();
        intent.setType("video/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_VIDEO_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null){
            imageUri = data.getData();

            dialog = new Dialog(context);
            dialog.setContentView(R.layout.popup_upload_image);

            ImageView imageView = dialog.findViewById(R.id.popup_manager_ImageView);
            Button upload = dialog.findViewById(R.id.popup_manager_imageUpload);
            imageprogressBar = dialog.findViewById(R.id.popup_manager_imageProgressBar);

            imageView.setImageURI(imageUri);

            upload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    UploadImage();
                }
            });
            dialog.show();
        }
        else if (requestCode == PICK_VIDEO_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null){
            videoUri = data.getData();

            dialog = new Dialog(context);
            dialog.setContentView(R.layout.popup_upload_video);

            final MediaController mediaController = new MediaController(dialog.getContext());

            videoView = dialog.findViewById(R.id.popup_manager_VideoView);
            videoName = dialog.findViewById(R.id.popup_manager_VideoName);
            upload = dialog.findViewById(R.id.popup_manager_Upload);
            progressBar = dialog.findViewById(R.id.popup_manager_ProgressBar);

            videoView.setMediaController(mediaController);
            mediaController.setAnchorView(videoView);
            videoView.start();

            videoView.setVideoURI(videoUri);

            upload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    UploadVideo();
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

    private void UploadImage(){
        imageprogressBar.setVisibility(View.VISIBLE);
        if (imageUri != null){
            StorageReference reference = imagestorageReference.child(System.currentTimeMillis()+"."+getfileExt(imageUri));

            reference.putFile(imageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            imageprogressBar.setVisibility(View.INVISIBLE);
                            dialog.hide();

                            reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    String imageUrl = uri.toString();
                                    if(!IP.equalsIgnoreCase("")){
                                        String url = "http://"+ IP_Address.IP_Address+"/VillaFilomena/image_upload.php";
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
                                        myrequest.add(stringRequest);
                                    }
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

    private void UploadVideo() {
        progressBar.setVisibility(View.VISIBLE);
        if (videoUri != null){
            StorageReference reference = videostorageReference.child(System.currentTimeMillis()+"."+getfileExt(videoUri));

            reference.putFile(videoUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressBar.setVisibility(View.INVISIBLE);
                            dialog.hide();

                            reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    String video_url = uri.toString();

                                    if(!IP.equalsIgnoreCase("")){
                                        String url = "http://"+ IP_Address.IP_Address+"/VillaFilomena/video_upload.php";
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
                                                map.put("name",System.currentTimeMillis()+"."+getfileExt(videoUri));
                                                map.put("url",video_url);
                                                return map;
                                            }
                                        };
                                        myrequest.add(stringRequest);
                                    }
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