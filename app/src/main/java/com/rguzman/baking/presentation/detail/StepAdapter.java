package com.rguzman.baking.presentation.detail;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rguzman.baking.R;
import com.rguzman.baking.domain.model.Step;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepAdapter extends RecyclerView.Adapter<StepAdapter.StepViewHolder> {

    public interface ListItemClickListener {
        void onListItemClick(Step step);
    }

    private ListItemClickListener listener;
    private List<Step> list = new ArrayList<>();

    public StepAdapter(ListItemClickListener listener) {
        this.listener = listener;
    }


    @NonNull
    @Override
    public StepViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.list_item_step;
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(layoutIdForListItem, parent, false);
        return new StepViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StepViewHolder stepViewHolder, int position) {
        stepViewHolder.bind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public void setList(List<Step> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public void clearList() {
        this.list.clear();
        notifyDataSetChanged();
    }

    class StepViewHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.txt_step_name)
        TextView txtStepName;

        public StepViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(final Step step) {

            txtStepName.setText(itemView.getResources().getString(R.string.text_step_name_label, String.valueOf(getAdapterPosition() + 1), step.getShortDescription()));

            itemView.setOnClickListener(v -> listener.onListItemClick(step));
        }
    }
}
