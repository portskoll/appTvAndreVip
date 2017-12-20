package com.acessevip.tvchat.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import com.acessevip.tvchat.R;

/**
 * Created by Henrique on 15/11/2015.
 */
public class NavDrawerMenuAdapter extends BaseAdapter {

    protected  static final String TAG = "livroandroid";
    private  final List<NavDrawerMenuItem> list;
    private final Context context;
    private LayoutInflater inflater;

    public NavDrawerMenuAdapter(Context context,List<NavDrawerMenuItem> list ) {
        this.list = list;
        this.context = context;
        this.inflater = (LayoutInflater) LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list != null ? list.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return list != null ? list.get(position) : null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder holder = null;
        if(view == null) {
            //cria o view holder
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.adapter_nav_drawer,parent,false);
            view.setTag(holder);
            holder.text = (TextView) view.findViewById(R.id.text);
            holder.img = (ImageView)view.findViewById(R.id.img);

        }else {
            //reaproveita o viewHolder
            holder = (ViewHolder) view.getTag();
        }

        //Atualiza a view
        NavDrawerMenuItem item = list.get(position);
        holder.text.setText(item.title);
        holder.img.setImageResource(item.img);

        if (item.selected) {
            //configura o fundo cinza
            view.setBackgroundResource(R.drawable.selector_nav_drawer_selected);
            holder.text.setTextColor(context.getResources().getColor(R.color.colorPrimary));



        }else {
            view.setBackgroundResource(R.drawable.selector_nav_drawer);
            holder.text.setTextColor(context.getResources().getColor(R.color.black));
        }
        return view;
    }

    public  void setSelect(int position,boolean selected) {
        clearSelected();
        list.get(position).selected = selected;
        notifyDataSetChanged();
    }

    public void clearSelected(){
        if (list != null){
            for(NavDrawerMenuItem item : list) {
                item.selected = false;

            }
            notifyDataSetChanged();
        }
    }

    //Desing Patter "ViewHolder"
    static  class ViewHolder {
        TextView text;
        ImageView img;
    }
}
