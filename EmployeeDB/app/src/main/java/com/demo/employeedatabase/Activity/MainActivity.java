package com.demo.employeedatabase.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.demo.employeedatabase.Adapter.PreviewAdapter;
import com.demo.employeedatabase.DB.DBHelper;
import com.demo.employeedatabase.Model.PreviewModel;
import com.demo.employeedatabase.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    TextView tv;
    RequestQueue queue;
    DBHelper db;
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    List<PreviewModel> PreviewList = new ArrayList<>();
    PreviewAdapter adapter;
    ProgressDialog progressDialog;
    ImageView img;
    public static Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv=findViewById(R.id.tv1);
        img=findViewById(R.id.img1);
        recyclerView=findViewById(R.id.RecyclerView);
        db = new DBHelper(this);
        adapter = new PreviewAdapter(PreviewList,this);
        queue = Volley.newRequestQueue(this);
        progressDialog = new ProgressDialog(this);
        if (db.isEmpty()){
            if (DataIsActive()) {
                parseIt();
            }
            else {
                tv.setVisibility(View.VISIBLE);
                img.setVisibility(View.VISIBLE);
            }
        }
        else{getDb();}

        img.setOnClickListener(Views->{
            if (DataIsActive()) {
                parseIt();
                tv.setVisibility(View.GONE);
                img.setVisibility(View.GONE);
            }else{
                Toast.makeText(this, "No connection !", Toast.LENGTH_SHORT).show();
            }
        });

    }



    private void parseIt() {
        progressDialog.setMessage("Fetching Employee Data");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
        String url = "https://www.mocky.io/v2/5d565297300000680030a986";
        JsonArrayRequest jr = new JsonArrayRequest(Request.Method.GET, url, null, response -> {
            try {
                new Thread(() -> {
                    Looper.prepare();
                    try {
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject employee = response.getJSONObject(i);
                            String name = employee.getString("name");
                            String userName = employee.getString("username");
                            String email = employee.getString("email");
                            String profileUrl = employee.getString("profile_image");
                            JSONObject Address = employee.getJSONObject("address");
                            String street = "\n\t\tStreet :- " + Address.getString("street") +
                                    "\n\t\tSuite :- " + Address.getString("suite") +
                                    "\n\t\tCity :- " + Address.getString("city") +
                                    "\n\t\tZipcode :- " + Address.getString("zipcode");
                            String CompanyName;
                            String Phone = employee.getString("phone");
                            String web = employee.getString("website");
                            String CompanyDetails;
                            Bitmap prfImg;
                            if (!profileUrl.equals("null")){
                                prfImg = Glide.with(getApplicationContext()).asBitmap().load(profileUrl).submit().get();
                            }else{
                                prfImg = Glide.with(getApplicationContext()).asBitmap().load(R.drawable.ic_person).submit().get();
                            }
                            ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
                            prfImg.compress(Bitmap.CompressFormat.JPEG, 100, byteArray);
                            byte[] img = byteArray.toByteArray();
                            if (employee.getString("company").equals("null")) {
                                CompanyName = "null";
                                CompanyDetails = "null";
                                Log.e("jsonNull", "YES");
                            } else {
                                JSONObject okk = employee.getJSONObject("company");
                                CompanyName = okk.getString("name");
                                CompanyDetails = "\n\t\tName :- " + okk.getString("name") +
                                        "\n\t\tCatchPhrase :-" + okk.getString("catchPhrase") +
                                        "\n\t\tBs :-" + okk.getString("bs");
                                Log.e("jsonNull", CompanyName);
                            }
                            db.insertData(name, Phone, CompanyName, img, email,
                                    street, web, CompanyDetails, userName);
                            Log.e("USerName", userName);
                        }
                        runOnUiThread(MainActivity.this::getDb);

                    }catch (JSONException | InterruptedException | ExecutionException e){
                        e.printStackTrace();
                    }
                }).start();


            } catch (Exception e) {
                e.printStackTrace();
            }
        }, Throwable::printStackTrace);

        queue.add(jr);
    }

    private boolean DataIsActive() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo()!=null && cm.getActiveNetworkInfo().isConnected();
    }

    private void getDb() {
        try {
            Cursor res = db.getData();
            if (res.getCount() != 0) {
                while (res.moveToNext()) {
                    byte[] bitmap = res.getBlob(3);
                    Bitmap img = BitmapFactory.decodeByteArray(bitmap,0, bitmap.length);
                    PreviewList.add(new PreviewModel(
                            res.getString(0),
                            res.getString(1),
                            res.getString(2),
                            img,
                            res.getString(4),
                            res.getString(5),
                            res.getString(6),
                            res.getString(7),
                            res.getString(8)));
                }
                initRecyclerView();
                Toast.makeText(this, "Database imported successfully", Toast.LENGTH_SHORT).show();

            }
        }catch (Exception e){
            e.printStackTrace();
        }
        finally {
            db.close();
        }
    }

    private void initRecyclerView() {

        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new PreviewAdapter(PreviewList,this);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        progressDialog.dismiss();
    }

    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu,menu);
        MenuItem search = menu.findItem(R.id.SearchEmployee);
        SearchView searchView = (SearchView) search.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Search(newText);
                return false;
            }
        });
        return true;

    }

    private void Search(String newText) {
        ArrayList<PreviewModel> filteredList = new ArrayList<>();
        for (PreviewModel item:PreviewList){
            if (item.getNAME().toLowerCase().contains(newText.toLowerCase())){
                filteredList.add(item);
            }
        }
        adapter.filterList(filteredList);

    }


}