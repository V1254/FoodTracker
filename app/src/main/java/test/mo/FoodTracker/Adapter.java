package test.mo.FoodTracker;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import test.mo.FoodTracker.Model.Food;

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder>{

    List<Food> foods;

    public Adapter(List<Food> foods) {
        this.foods = foods;
    }


    //  layout used for each food in the list
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview,parent,false);
        return new MyViewHolder(view);

    }

    // sets the data for each field in the cardview.
    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        holder.food_Name.setText(foods.get(holder.getAdapterPosition()).getFoodName());
        holder.start_Date.setText(foods.get(holder.getAdapterPosition()).getStartDate());
        holder.expiration_Date.setText(foods.get(holder.getAdapterPosition()).getExpiryDate());
    }

    @Override
    public int getItemCount() {
        return foods.size();
    }

    public Food getFoodAtPosition(int position){
        return foods.get(position);
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView food_Name;
        TextView start_Date;
        TextView expiration_Date;
        CardView cardView;


        public MyViewHolder(View itemView) {
            super(itemView);

            food_Name = itemView.findViewById(R.id.food_name);
            start_Date = itemView.findViewById(R.id.date_added);
            expiration_Date = itemView.findViewById(R.id.expiration_date);
            cardView = itemView.findViewById(R.id.card_view);
        }
    }




}
