package com.example.hi.vol;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Hi on 26-05-2017.
 */

public class MeraAdapter extends RecyclerView.Adapter<MeraAdapter.MyHolder> {

    Context mcontext;
    ArrayList<String> Aname;
    ArrayList<String> Aheight;
    ArrayList<String> Amass;
    LayoutInflater inflater;
    MyHolder holder;
    View view;


    public MeraAdapter(MainActivity mainActivity, ArrayList<String> namearray, ArrayList<String> heightarray, ArrayList<String> massarray) {
        mcontext=mainActivity;
        Aname=namearray;
        Aheight=heightarray;
        Amass=massarray;
        inflater=inflater.from(mcontext);
    }


    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view=inflater.inflate(R.layout.mylist,parent,false);
        holder=new MyHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        holder.mname.setText(Aname.get(position));
        holder.mheight.setText(Aheight.get(position));
        holder.mmass.setText(Amass.get(position));
    }

    @Override
    public int getItemCount() {
        return Aname.size();
    }


    public class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView mname,mheight,mmass;

        public MyHolder(View view) {
            super(view);
            mname=(TextView) view.findViewById(R.id.ename);
            mheight=(TextView) view.findViewById(R.id.eheight);
            mmass=(TextView) view.findViewById(R.id.emass);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position=getAdapterPosition();
           Toast.makeText(mcontext,Aname.get(position), Toast.LENGTH_SHORT).show();

        }
    }
}
