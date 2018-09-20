package test.mo.timer;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder>{

    List<Food> foods;

    public Adapter(List<Food> foods) {
        this.foods = foods;
    }


    // following used to generate cardview for each data.
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview,parent,false);
        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.food_Name.setText(foods.get(holder.getAdapterPosition()).getFoodName());
        holder.start_Date.setText(foods.get(holder.getAdapterPosition()).getStartDate());
        holder.expiration_Date.setText(foods.get(holder.getAdapterPosition()).getExpiryDate());

    }

    @Override
    public int getItemCount() {
        return foods.size();
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


            //TODO: find each of the private fields above.
        }
    }




}
