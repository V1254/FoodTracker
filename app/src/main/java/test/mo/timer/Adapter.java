package test.mo.timer;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder>{



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView food_Name;
        TextView food_Date;
        TextView food_expiration;
        CardView cardView;


        public MyViewHolder(View itemView) {
            super(itemView);

            //TODO: find each of the private fields above.
        }
    }




}
