package com.example.helloworld;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class SearchCompanyAdapter extends RecyclerView.Adapter<SearchCompanyAdapter.ViewHolder> implements Filterable {
    private Context context;
    private List<Company> companyList;
    private List<Company> companyListFull;

    public SearchCompanyAdapter(Context context, List<Company> companyList) {
        this.context = context;
        this.companyList = companyList;
        companyListFull = new ArrayList<>(companyList);
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
        holder.companyBusinessID.setText(company.getBusinessID());
        holder.companyForm.setText(company.getCompanyForm());
        holder.companyRegistrationDate.setText(company.getRegistrationDate());
    }

    @Override
    public int getItemCount() {
        return companyList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView companyName, companyBusinessID, companyForm, companyRegistrationDate;

        public ViewHolder(View itemView) {
            super(itemView);
            companyName = itemView.findViewById(R.id.textViewCompanyName);
            companyBusinessID = itemView.findViewById(R.id.textViewCompanyBusinessID);
            companyForm = itemView.findViewById(R.id.textViewCompanyForm);
            companyRegistrationDate = itemView.findViewById(R.id.textViewCompanyRegistrationDate);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(), "Do Something With this Click", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public Filter getFilter() {
        return filterCompany;
    }

    private Filter filterCompany = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<Company> filteredList = new ArrayList<>();
            if (charSequence == null || charSequence.length() == 0) {
                filteredList.addAll(companyListFull);
            } else {
                String filterPattern = charSequence.toString().toLowerCase().trim();
                for (Company item : companyListFull) {
                    if (item.getCompanyName().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            companyList.clear();
            companyList.addAll((List) filterResults.values);
            notifyDataSetChanged();
        }
    };
}