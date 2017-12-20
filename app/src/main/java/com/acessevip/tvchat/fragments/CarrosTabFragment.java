package com.acessevip.tvchat.fragments;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.acessevip.tvchat.R;
import com.acessevip.tvchat.adapter.TabsAdapter;

import livroandroid.lib.utils.Prefs;

/**
 * Created by Henrique on 17/11/2015.
 */
public class CarrosTabFragment  extends BaseFragment implements TabLayout.OnTabSelectedListener{

    private ViewPager mViewPager;
    private TabLayout tabLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_carro_tab,container,false);
        //viewpager
        mViewPager = (ViewPager) view.findViewById(R.id.viewpager);
        mViewPager.setOffscreenPageLimit(7);
        mViewPager.setAdapter(new TabsAdapter(getContext(),getChildFragmentManager()));
        //tabs
        tabLayout = (TabLayout) view.findViewById(R.id.tabLayout);
        int cor1 = getContext().getResources().getColor(R.color.white);
        int cor2 = getContext().getResources().getColor(R.color.colorAccent);
        tabLayout.setTabTextColors(cor1,cor2);
        tabLayout.setTabMode(tabLayout.MODE_SCROLLABLE);
        //adicionandoas tabs
        tabLayout.addTab(tabLayout.newTab().setText(R.string.acao));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.animacao));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.aventura));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.comedia));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.comedia_r));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.crime));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.documentario));
        //tabLayout.addTab(tabLayout.newTab().setText(R.string.drama));
        //tabLayout.addTab(tabLayout.newTab().setText(R.string.faroeste));
       // tabLayout.addTab(tabLayout.newTab().setText(R.string.ficao));
        //tabLayout.addTab(tabLayout.newTab().setText(R.string.guerra));
       // tabLayout.addTab(tabLayout.newTab().setText(R.string.musical));
       // tabLayout.addTab(tabLayout.newTab().setText(R.string.policial));
       // tabLayout.addTab(tabLayout.newTab().setText(R.string.romance));
       // tabLayout.addTab(tabLayout.newTab().setText(R.string.suspense));
       // tabLayout.addTab(tabLayout.newTab().setText(R.string.terror));
        //listiner para taratar eventos de clique na tab
        tabLayout.setOnTabSelectedListener(this);
        //se mudar a view page atualiza a tab selecionada
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        //para mostrar sempre a ultima tab selecionada pelo usuario
        int tabIdx = Prefs.getInteger(getContext(),"tabIdx");
        mViewPager.setCurrentItem(tabIdx);

        return view;
    }



    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        mViewPager.setCurrentItem(tab.getPosition());
        //salava nas pref a ultima pagina selecionada
        Prefs.setInteger(getContext(),"tabIdx",mViewPager.getCurrentItem());

    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) { }

    @Override
    public void onTabReselected(TabLayout.Tab tab) { }
}
