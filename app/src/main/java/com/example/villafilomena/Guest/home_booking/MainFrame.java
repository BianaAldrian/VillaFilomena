package com.example.villafilomena.Guest.home_booking;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.GravityCompat;
import androidx.core.widget.NestedScrollView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.villafilomena.Guest.Profile.Account;
import com.example.villafilomena.R;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.navigation.NavigationView;

public class MainFrame extends AppCompatActivity {
    final Context context = this;
    ImageView account, bannerView, mainmenu;
    TextView home, book;
    AppBarLayout appbar;
    NestedScrollView nested;
    public static Button Continue, Booknow, Done;

    DrawerLayout drawerLayout;
    ImageView navigationBar,iv_logout;
    NavigationView navigationView;
    CardView profile, bookingHsty, ratings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_frame);

        profile = findViewById(R.id.MainFrame_guestProfile);
        bookingHsty = findViewById(R.id.MainFrame_guestBookings);
        ratings = findViewById(R.id.MainFrame_guestRatings);
        mainmenu = findViewById(R.id.MainMenu);
        bannerView = findViewById(R.id.bannerView);
        home = findViewById(R.id.mainHome);
        book = findViewById(R.id.mainBook);
        appbar = findViewById(R.id.appbar);
        nested = findViewById(R.id.nested);
        Continue = findViewById(R.id.guestBooking_Continue);
        Booknow = findViewById(R.id.guestBooking2_booknow);
        Done = findViewById(R.id.guestBooking3_done);

        home.setPaintFlags(home.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        home.setPaintFlags(home.getPaintFlags() | Paint.FAKE_BOLD_TEXT_FLAG);
        home.setPadding(0,0,0,5);
        replace_home(new Home());


       /* account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Account.class));
            }
        });*/
        mainmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nested.fullScroll(View.FOCUS_UP);
                toggle(true);
                nested.setNestedScrollingEnabled(true);

                home.setPaintFlags(home.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                home.setPaintFlags(home.getPaintFlags() | Paint.FAKE_BOLD_TEXT_FLAG);
                home.setPadding(0,0,0,5);

                if((book.getPaintFlags() & Paint.UNDERLINE_TEXT_FLAG) == Paint.UNDERLINE_TEXT_FLAG &&
                        (book.getPaintFlags() & Paint.FAKE_BOLD_TEXT_FLAG) == Paint.FAKE_BOLD_TEXT_FLAG){
                    book.setPaintFlags(book.getPaintFlags() ^ Paint.UNDERLINE_TEXT_FLAG);
                    book.setPaintFlags(book.getPaintFlags() ^ Paint.FAKE_BOLD_TEXT_FLAG);
                    book.setPadding(0,5,0,0);

                    replace_home(new Home());
                    Continue.setVisibility(View.GONE);
                }
            }
        });
        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nested.fullScroll(View.FOCUS_UP);
                toggle(false);
                nested.setNestedScrollingEnabled(false);

                book.setPaintFlags(book.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                book.setPaintFlags(book.getPaintFlags() | Paint.FAKE_BOLD_TEXT_FLAG);
                book.setPadding(0,0,0,5);

                if((home.getPaintFlags() & Paint.UNDERLINE_TEXT_FLAG) == Paint.UNDERLINE_TEXT_FLAG &&
                        (home.getPaintFlags() & Paint.FAKE_BOLD_TEXT_FLAG) == Paint.FAKE_BOLD_TEXT_FLAG){
                    home.setPaintFlags(home.getPaintFlags() ^ Paint.UNDERLINE_TEXT_FLAG);
                    home.setPaintFlags(home.getPaintFlags() ^ Paint.FAKE_BOLD_TEXT_FLAG);
                    home.setPadding(0,5,0,0);

                    replace_book(new Guest_Booking());
                    Continue.setVisibility(View.VISIBLE);
                }
            }
        });

        onSetNavigationDrawerEvents();
    }

    private void onSetNavigationDrawerEvents() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        navigationView = (NavigationView) findViewById(R.id.navigationView);

        mainmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(navigationView, true);
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainFrame.this, Account.class));
            }
        });
        bookingHsty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainFrame.this, Guest_Booking_Hstry.class));
            }
        });
        ratings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.rating_dialog);

                WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
                layoutParams.copyFrom(dialog.getWindow().getAttributes());
                layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
                layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
                dialog.getWindow().setAttributes(layoutParams);

                dialog.show();
            }
        });
    }

    private void replace_book(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
        transaction.replace(R.id.frame,fragment).commit();
    }

    private void replace_home(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right);
        transaction.replace(R.id.frame,fragment).commit();
    }

    private void toggle(boolean show) {
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) appbar.getLayoutParams();
        params.height = bannerView.getHeight();
        appbar.setLayoutParams(params);
        appbar.setExpanded(show);

    }

   /* @Override
    protected void onStart(){
        super.onStart();
        //home.performClick();
        home.setPaintFlags(home.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        home.setPaintFlags(home.getPaintFlags() | Paint.FAKE_BOLD_TEXT_FLAG);
        home.setPadding(0,0,0,5);
        replace_home(new Home());
    }*/
}