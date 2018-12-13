package test.mo.FoodTracker;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import test.mo.FoodTracker.Model.Food;
import test.mo.FoodTracker.View.UpdateActivity;

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {

    private List<Food> foods;
    private DateConverter dateConverter;

    public Adapter(List<Food> foods, DateConverter dateConverter) {
        this.foods = foods;
        this.dateConverter = dateConverter;
    }


    //  layout used for each food in the list
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview, parent, false);
        return new MyViewHolder(view);

    }

    // binds data to the cards in the recyclerview.
    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        holder.food_Name.setText(foods.get(holder.getAdapterPosition()).getFoodName());

        Long dateAdded = foods.get(holder.getAdapterPosition()).getStartDate();
        holder.start_Date.setText(dateConverter.getAddedString(dateAdded));

        Long expiryDate = foods.get(holder.getAdapterPosition()).getExpiryDate();
        String toDisplay = dateConverter.getExpiryString(expiryDate);
        holder.expiration_Date.setText(toDisplay);

        String category = foods.get(holder.getAdapterPosition()).getCategory();
        holder.categoryImage.setImageResource(getDrawable(category));

        if (toDisplay.equals("Expired!!") || toDisplay.equals("Today") || toDisplay.equals("Tomorrow")) {
            holder.expiration_Date.setTextColor(Color.RED);
            holder.expiryColor.setBackgroundColor(Color.RED);
        } else {
            holder.expiration_Date.setTextColor(Color.GREEN);
            holder.expiryColor.setBackgroundColor(Color.GREEN);
        }

    }

    @Override
    public int getItemCount() {
        return foods.size();
    }

    public Food getFoodAtPosition(int position) {
        return foods.get(position);
    }

    public void addItems(List<Food> foodList) {
        this.foods = foodList;
        notifyDataSetChanged();
    }

    private int getDrawable(String category){
        switch (category){
            case "Fruit & Veg": return R.drawable.veg;
            case "Meats": return R.drawable.meat;
            case "Dairy": return R.drawable.dairy;
            case "Grains": return R.drawable.grain;
            case "Oils": return R.drawable.oil;
            default:return R.drawable.other;
        }
    }

    // non static to allow access to enclosing fields/methods
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView food_Name;
        TextView start_Date;
        TextView expiration_Date;
        CardView cardView;
        ImageView expiryColor;
        ImageView categoryImage;

        public MyViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            food_Name = itemView.findViewById(R.id.food_name);
            start_Date = itemView.findViewById(R.id.date_added);
            expiration_Date = itemView.findViewById(R.id.expiration_date);
            cardView = itemView.findViewById(R.id.card_view);
            expiryColor = itemView.findViewById(R.id.expiry_color);
            categoryImage = itemView.findViewById(R.id.categoryImage);
        }


        @Override
        public void onClick(View v) {
            Food food = getFoodAtPosition(getAdapterPosition());
            Intent intent = new Intent(v.getContext(), UpdateActivity.class);
            intent.putExtra("id", food.getId());
            intent.putExtra("name", food.getFoodName());
            intent.putExtra("added", food.getStartDate());
            intent.putExtra("expiry", food.getExpiryDate());
            intent.putExtra("category",food.getCategory());
            v.getContext().startActivity(intent);
        }
    }


}
