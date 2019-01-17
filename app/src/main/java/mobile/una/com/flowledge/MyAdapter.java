package mobile.una.com.flowledge;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.Serializable;
import java.util.List;

import mobile.una.com.flowledge.model.AreaData;
import mobile.una.com.flowledge.model.Question;

/**
 * Created by abdalla on 1/12/18.
 */

public class MyAdapter extends RecyclerView.Adapter<AreaViewHolder>{
    private Context mContext;
    private List<AreaData> mDataList;
    Question q = new Question("Barco03","Bases de Datos");
  //  String area="";

    MyAdapter(Context mContext, List<AreaData> mAreaList) {
        this.mContext = mContext;
        this.mDataList = mAreaList;
    }

    @Override
    public AreaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_row_item, parent, false);
        return new AreaViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(final AreaViewHolder holder, int position) {
        holder.mImage.setImageResource(mDataList.get(position).getAreaImage());
        holder.mTitle.setText(mDataList.get(position).getAreaName());
      //  area=mDataList.get(position).getAreaName();
        holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                Fragment myFragment = new ReplyFragment();
                Bundle bundle = new Bundle();
                q.setCategory(mDataList.get(holder.getAdapterPosition()).getAreaName());
                bundle.putSerializable("question", (Serializable) q);
                myFragment.setArguments(bundle);
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, myFragment).addToBackStack(null).commit();

                // Intent mIntent = new Intent(mContext, ReplyFragment.class);
              //  mIntent.putExtra("question", holder.mTitle.toString());
              //  mIntent.putExtra("Title", mDataList.get(holder.getAdapterPosition()).getAreaName());
               // mIntent.putExtra("Image", mDataList.get(holder.getAdapterPosition()).getAreaImage());
             ///  mContext.startActivity(mIntent);

                // Probando putadas

             //   Fragment selectFragment = null;
               // Bundle bundle = new Bundle();
               // bundle.putSerializable("question", (Serializable) question);
              //  selectFragment = new ReplyFragment();
               // selectFragment.setArguments(bundle);


             //   getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,selectFragment).commit();



            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }
}

class AreaViewHolder extends RecyclerView.ViewHolder {

    ImageView mImage;
    TextView mTitle;
    CardView mCardView;

    AreaViewHolder(View itemView) {
        super(itemView);

        mImage = itemView.findViewById(R.id.ivImage);
        mTitle = itemView.findViewById(R.id.tvTitle);
        mCardView = itemView.findViewById(R.id.cardview);
    }
}
