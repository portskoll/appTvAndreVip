package com.acessevip.tvchat.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.acessevip.tvchat.fragments.CarrosFragment;
import com.acessevip.tvchat.R;

/**
 * Created by Henrique on 17/11/2015.
 */
public class TabsAdapter extends FragmentPagerAdapter {

    private Context context;


    public TabsAdapter(Context context, FragmentManager fm) {
        super(fm);
        this.context = context;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if(position == 0){
            return context.getString(R.string.acao);
        }else if (position == 1) {
            return context.getString(R.string.animacao);
        }else if (position == 2) {
            return context.getString(R.string.aventura);
        }else if (position == 3) {
            return context.getString(R.string.comedia);
        }else if (position == 4) {
            return context.getString(R.string.comedia_r);
        }else if (position == 5) {
            return context.getString(R.string.crime);
        }else if (position == 6) {
            return context.getString(R.string.documentario);
        }else if (position == 7) {
            return context.getString(R.string.drama);
        }else if (position == 8) {
            return context.getString(R.string.faroeste);
        }else if (position == 9) {
            return context.getString(R.string.ficao);
        }else if (position == 10) {
            return context.getString(R.string.guerra);
        }else if (position == 11) {
            return context.getString(R.string.musical);
        }else if (position == 12) {
            return context.getString(R.string.policial);
        }else if (position == 13) {
            return context.getString(R.string.romance);
        }else if (position == 14) {
            return context.getString(R.string.suspense);
        }else if (position == 15) {
            return context.getString(R.string.terror);
        }
        return "novo";
    }

    @Override
    public Fragment getItem(int position) {

        Bundle args = new Bundle();


        if(position == 0){
            args.putString("tipo",context.getString(R.string.acao));
        }else if(position ==1){
            args.putString("tipo",context.getString(R.string.animacao));
        }else if(position ==2){
            args.putString("tipo",context.getString(R.string.aventura));
        }else if(position ==3){
            args.putString("tipo",context.getString(R.string.comedia));
        }else if(position ==4){
            args.putString("tipo",context.getString(R.string.comedia_r));
        }else if(position ==5){
            args.putString("tipo",context.getString(R.string.crime));
        }else if(position ==6){
            args.putString("tipo",context.getString(R.string.documentario));
        }else if(position ==7){
            args.putString("tipo",context.getString(R.string.drama));
        }else if(position ==8){
            args.putString("tipo",context.getString(R.string.faroeste));
        }else if(position ==9){
            args.putString("tipo",context.getString(R.string.ficao));
        }else if(position ==10){
            args.putString("tipo",context.getString(R.string.guerra));
        }else if(position ==11){
            args.putString("tipo",context.getString(R.string.musical));
        }else if(position ==12){
            args.putString("tipo",context.getString(R.string.policial));
        }else if(position ==13){
            args.putString("tipo",context.getString(R.string.romance));
        }else if(position ==14){
            args.putString("tipo",context.getString(R.string.suspense));
        }else if(position ==15) {
            args.putString("tipo", context.getString(R.string.terror));
        }else{
            args.putString("tipo","novo");
        }

        Fragment f = new CarrosFragment();
        f.setArguments(args);
        return f;
    }

    @Override
    public int getCount() {
        return 7;
    }
}
