package com.demo.employeedatabase.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.demo.employeedatabase.Activity.MainActivity;
import com.demo.employeedatabase.Model.PreviewModel;
import com.demo.employeedatabase.Activity.ProfileDetailsActivity;
import com.demo.employeedatabase.R;

import java.util.ArrayList;
import java.util.List;

public class PreviewAdapter extends RecyclerView.Adapter<PreviewAdapter.ViewHolder> {
    private List<PreviewModel> EmployeeList;
    private final Context context;


    public PreviewAdapter(List<PreviewModel> EmployeeList, Context context){
        this.EmployeeList = EmployeeList;
        this.context=context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.preview_item_design,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setData(EmployeeList.get(position).getNAME(), EmployeeList.get(position).getCOMPANY_NAME(), EmployeeList.get(position).getPROFILE_URL());
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ProfileDetailsActivity.class);
            intent.putExtra("name", "Name : "+EmployeeList.get(position).getNAME());
            intent.putExtra("user", "User Name : "+EmployeeList.get(position).getUSER_NAME());
            intent.putExtra("email", "Email : "+EmployeeList.get(position).getEMAIL());
            intent.putExtra("address", "Address : "+EmployeeList.get(position).getADDRESS());
            intent.putExtra("phone", "Phone : "+EmployeeList.get(position).getPHONE());
            intent.putExtra("web", "Website : "+EmployeeList.get(position).getWEBSITE());
            intent.putExtra("company", "Company : "+EmployeeList.get(position).getCOMPANY_DETAILS());
            MainActivity.bitmap=EmployeeList.get(position).getPROFILE_URL();
            context.startActivity(intent);
        });

    }


    @Override
    public int getItemCount() {
        return EmployeeList.size();
    }

    public void filterList(ArrayList<PreviewModel> filteredList) {
        EmployeeList=filteredList;
        notifyDataSetChanged();
        if (filteredList.isEmpty())
            Toast.makeText(context, "No result found", Toast.LENGTH_SHORT).show();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private final TextView Name;
        private final TextView CompanyName;
        private final ImageView profileImg;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Name = itemView.findViewById(R.id.PreviewName);
            CompanyName = itemView.findViewById(R.id.PreviewCompanyName);
            profileImg = itemView.findViewById(R.id.previewProfileImg);


        }

        public void setData(String name, String companyName, Bitmap img) {
            Name.setText(name);
            CompanyName.setText(companyName);
            Glide.with(context)
                    .load(img)
                    .into(profileImg);
        }
    }

}
