package test.mo.FoodTracker;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import test.mo.FoodTracker.Model.Food;
import test.mo.FoodTracker.View.AddActivity;

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {

    private List<Food> foods;
    private DateConverter dateConverter;
    private Context context;
    private int themeID;

    public Adapter(List<Food> foods, DateConverter dateConverter, Context context) {
        this.foods = foods;
        this.dateConverter = dateConverter;
        this.context = context;
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

//        Long dateAdded = foods.get(holder.getAdapterPosition()).getStartDate();
//        holder.start_Date.setText(dateConverter.getAddedString(dateAdded));

        Long expiryDate = foods.get(holder.getAdapterPosition()).getExpiryDate();
        String toDisplay = dateConverter.getExpiryString(expiryDate);
        holder.expiration_Date.setText(toDisplay);

        String category = foods.get(holder.getAdapterPosition()).getCategory();
        holder.categoryImage.setImageResource(DrawableManager.getDrawable(category));

        Map<String, Integer> colors = generateColors();

        switch (toDisplay) {
            case "Tomorrow":
                setColor(holder, colors.get(toDisplay));
                break;
            case "Expired!!":
                setColor(holder, colors.get(toDisplay));
                break;
            case "Today":
                setColor(holder, colors.get(toDisplay));
                break;
            default:
                setColor(holder, colors.get("Default"));
        }
    }

    public void setThemeID(int themeID){
        this.themeID = themeID;
    }

    // colors based on current theme.
    private Map<String,Integer> generateColors(){
        Map<String,Integer> colorMap = new HashMap<>();
        if(themeID == 0){
            // light theme so bright colors.
            colorMap.put("Expired!!",R.color.light_mode_red);
            colorMap.put("Today",R.color.light_mode_red);
            colorMap.put("Tomorrow",R.color.light_mode_amber);
            colorMap.put("Default",R.color.light_mode_green);
            return colorMap;
        }
        colorMap.put("Expired!!",R.color.dark_mode_Red);
        colorMap.put("Today",R.color.dark_mode_Red);
        colorMap.put("Tomorrow",R.color.dark_mode_Amber);
        colorMap.put("Default",R.color.dark_mode_Green);
        return colorMap;
    }

    private void setColor(MyViewHolder holder, int color){
        holder.expiration_Date.setTextColor(ContextCompat.getColor(context,color));
        holder.expiryColor.setBackgroundColor(ContextCompat.getColor(context,color));
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
//            start_Date = itemView.findViewById(R.id.date_added);
            expiration_Date = itemView.findViewById(R.id.expiration_date);
            cardView = itemView.findViewById(R.id.card_view);
            expiryColor = itemView.findViewById(R.id.expiry_color);
            categoryImage = itemView.findViewById(R.id.categoryImage);
        }


        @Override
        public void onClick(View v) {
            Food food = getFoodAtPosition(getAdapterPosition());
            Intent intent = new Intent(v.getContext(), AddActivity.class);
            intent.putExtra("id", food.getId());
            intent.putExtra("name", food.getFoodName());
            intent.putExtra("added", food.getStartDate());
            intent.putExtra("expiry", food.getExpiryDate());
            intent.putExtra("category",food.getCategory());
            v.getContext().startActivity(intent);
        }
    }


}
