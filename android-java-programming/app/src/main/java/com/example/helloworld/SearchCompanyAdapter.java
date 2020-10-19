package com.example.helloworld;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SearchCompanyAdapter extends RecyclerView.Adapter<SearchCompanyAdapter.ViewHolder> {
    private Context context;
    private List<Company> companyList;

    public SearchCompanyAdapter(Context context, List<Company> companyList) {
        this.context = context;
        this.companyList = companyList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.certain_company_info, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Company company = companyList.get(position);
        holder.companyName.setText(company.getCompanyName());
    }

    @Override
    public int getItemCount() {
        return companyList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView companyName;

        public ViewHolder(View itemView) {
            super(itemView);
            companyName = itemView.findViewById(R.id.companyName);
        }
    }
}