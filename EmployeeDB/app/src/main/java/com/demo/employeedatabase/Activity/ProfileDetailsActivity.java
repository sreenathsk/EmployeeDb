package com.demo.employeedatabase.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.demo.employeedatabase.R;

public class ProfileDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_details);

        TextView Name,UserName,Email,Address,Phone,Website,CompanyDetails;
        ImageView prfImg = findViewById(R.id.ProfileImg);
        Intent i = getIntent();
        Name = findViewById(R.id.textView);
        UserName = findViewById(R.id.UserName);
        Email = findViewById(R.id.emailId);
        Address = findViewById(R.id.Address);
        Phone = findViewById(R.id.Phone);
        Website = findViewById(R.id.Web);
        CompanyDetails = findViewById(R.id.CompanyDetails);
        try {
            Name.setText(i.getStringExtra("name"));
            UserName.setText(i.getStringExtra("user"));
            Email.setText(i.getStringExtra("email"));
            Address.setText(i.getStringExtra("address"));
            Phone.setText(i.getStringExtra("phone"));
            Website.setText(i.getStringExtra("web"));
            CompanyDetails.setText(i.getStringExtra("company"));
            Glide.with(this).load(MainActivity.bitmap).into(prfImg);
        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(this, "An error occurred", Toast.LENGTH_SHORT).show();
        }


    }
}