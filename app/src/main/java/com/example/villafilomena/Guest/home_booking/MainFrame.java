package com.example.villafilomena.Guest.home_booking;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.villafilomena.Guest.Profile.Account;
import com.example.villafilomena.R;
import com.google.android.material.appbar.AppBarLayout;

public class MainFrame extends AppCompatActivity {

    ImageView account, bannerView;
    TextView home, book;
    AppBarLayout appbar;
    NestedScrollView nested;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_frame);
        account = findViewById(R.id.account);
        bannerView = findViewById(R.id.bannerView);
        home = findViewById(R.id.mainHome);
        book = findViewById(R.id.mainBook);
        appbar = findViewById(R.id.appbar);
        nested = findViewById(R.id.nested);

        account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Account.class));
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
                }
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

    @Override
    protected void onStart(){
        super.onStart();
        //home.performClick();
        home.setPaintFlags(home.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        home.setPaintFlags(home.getPaintFlags() | Paint.FAKE_BOLD_TEXT_FLAG);
        home.setPadding(0,0,0,5);
        replace_home(new Home());
    }
}