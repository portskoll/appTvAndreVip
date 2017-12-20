package com.acessevip.tvchat.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.ShareActionProvider;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.Toast;


import java.util.List;

import com.acessevip.tvchat.adapter.NavDrawerMenuItem;
import com.acessevip.tvchat.fragments.CarrosFragment;
import com.acessevip.tvchat.fragments.CarrosTabFragment;
import com.acessevip.tvchat.util.PrefsAtualizar;
import com.acessevip.tvchat.R;
import com.acessevip.tvchat.adapter.NavDrawerMenuAdapter;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.tjeannin.apprate.AppRate;

import livroandroid.lib.fragment.NavigationDrawerFragment;

public class MainActivity extends BaseActivity implements  NavigationDrawerFragment.NavigationDrawerCallbacks{

    private NavigationDrawerFragment mNavDrawerFragment;
    private NavDrawerMenuAdapter listAdapter;
    //private AdcashBannerView mBanner;
    //private AdcashInterstitial mInterstitial;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //AdMob - Banner  voltar banner aqui -05-02
        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                //.addTestDevice("23DC7227932B734FD41A35786991BF66")
                .build();
                mAdView.loadAd(adRequest);
        //--------------
        PrefsAtualizar.setAtualiza(getApplicationContext(),"at",-1);
        setUpToolbar();
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        //nav Drawer
        mNavDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.nav_drawer_fragment);
        //configura o drawer layout
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mNavDrawerFragment.setUp(drawerLayout);
        drawerLayout.setStatusBarBackground(R.color.colorPrimaryDark);
    }

    @Override
    public NavigationDrawerFragment.NavDrawerListView getNavDrawerView(NavigationDrawerFragment navDrawerFrag, LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.nav_drawer_listview, container, false);
        navDrawerFrag.setHeaderValues(view, R.id.listViewContainer, R.drawable.nav_drawer_header,
                R.drawable.splash1, R.string.nav_drawer_user_name, R.string.nav_drawer_email);

        return new NavigationDrawerFragment.NavDrawerListView(view, R.id.listView);
    }

    @Override
    public ListAdapter getNavDrawerListAdapter(NavigationDrawerFragment navDrawerFrag) {
        List<NavDrawerMenuItem> list = NavDrawerMenuItem.getList();
        //seleciona o premeiro item
        list.get(0).selected = true;
        this.listAdapter = new NavDrawerMenuAdapter(this,list);
        return listAdapter;
    }

    @Override
    public void onNavDrawerItemSelected(NavigationDrawerFragment navDrawerFrag, int position) {
        List<NavDrawerMenuItem> list = NavDrawerMenuItem.getList();
        NavDrawerMenuItem selectedItem = list.get(position);
        //seleciona a linha
        this.listAdapter.setSelect(position, true);

        if (position == 0) {

            replaceFragment(new CarrosTabFragment());
            PrefsAtualizar.setAtualiza(getApplicationContext(), "at", -1);
        }else if (position == 1){
            //mInterstitial.loadAd();
            //PrefsAtualizar.setAtualiza(getApplicationContext(), "at", 1);
            Bundle args = new Bundle();
            args.putString("tipo", "novo");
            CarrosFragment cf= new CarrosFragment();
            cf.setArguments(args);
            replaceFragment(cf);

        }else if (position ==4) {
           // mInterstitial.loadAd();
            replaceFragment(new CarrosTabFragment());
            PrefsAtualizar.setAtualiza(getApplicationContext(), "at", 1);


        }else if (position ==5) {
            PrefsAtualizar.setAtualiza(getApplicationContext(), "at", -1);
            Uri uri = Uri.parse("http://livestreamtv.biz/apptvcanais/canaltv/redirect/php_google_play_xat_vip.php");
            Intent it = new Intent(Intent.ACTION_VIEW,uri);
            startActivity(it);


        }else if (position ==3) {

            PrefsAtualizar.setAtualiza(getApplicationContext(), "at", -1);
            Uri uri = Uri.parse("http://celulartv.net/app_ver_futebol.php");
            Intent it = new Intent(Intent.ACTION_VIEW,uri);
            startActivity(it);

        }else if(position ==2) {

            PrefsAtualizar.setAtualiza(getApplicationContext(), "at", -1);
            Uri uri = Uri.parse("http://livestreamtv.biz/apptvcanais/canaltv/redirect/php_google_play_tv.php");
            Intent it = new Intent(Intent.ACTION_VIEW,uri);
            startActivity(it);

        }



    }

    //cria um toast com uma imagem
    public void alerta_referesh() {

        AlertDialog.Builder adb = new AlertDialog.Builder(this);// cria o AD
        adb.setTitle("Atualizar canais...");

        adb.setIcon(R.drawable.ic_refresh_black);
        adb.setMessage("Não se esqueça para atualizar os canais você pode clicar no icone na barra superior.");
        adb.setNegativeButton("Sair",null);
        adb.setPositiveButton("Atualizar Agora", new AlertDialog.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //comando aqui
                PrefsAtualizar.setAtualiza(getApplicationContext(),"at",1);
                replaceFragment(new CarrosTabFragment());
                toast("Aguarde...\nAtualizando Canais...");

            }
        });
        adb.show();// mostra o AD
//        PrefsAtualizar.setAtualiza(getApplicationContext(), "at", -1);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
//            ImageView alertIMG = new ImageView(this);
//            alertIMG.setImageResource(R.drawable.ic_cloud_download);
//            Toast aviso = new Toast(this);
//            aviso.setView(alertIMG);
//            aviso.setGravity(Gravity.CENTER_HORIZONTAL, 0,0);
//            aviso.setDuration(Toast.LENGTH_LONG);
//            aviso.show();
//        }
    }

    //adiciona o fragmente no centro da tela
    private void replaceFragment (Fragment frag) {

        getSupportFragmentManager().beginTransaction().replace(R.id.nav_drawer_container,frag,"TAG").commit();
    }

    //Criação do menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
       getMenuInflater().inflate(R.menu.menu_main,menu);
        //implementando a busca
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(onSearch());

        //implementando compartilhamento
        MenuItem shareItem = menu.findItem(R.id.action_share);
        ShareActionProvider share = (ShareActionProvider) MenuItemCompat.getActionProvider(shareItem);;
        share.setShareIntent(getDefaultIntent());

       return true;
    }

    private Intent getDefaultIntent() {

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/*");
        //intent.putExtra(Intent.EXTRA_TEXT,"Faça o download da versão de teste.\n"+"http://vertv.net/filmes/appfilmes/download/");
        intent.putExtra(Intent.EXTRA_TEXT, "http://livestreamtv.biz/apptvcanais/canaltv/redirect/php_gplay_b.php");
        return intent;
    }

    private SearchView.OnQueryTextListener onSearch(){
    return new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String query) {

            return false;
        }

        @Override
        public boolean onQueryTextChange(String newText) {
            PrefsAtualizar.setAtualiza(getApplicationContext(),"at",-1);
            Bundle args = new Bundle();
            args.putString("busca", newText);
            //args.putString("tipo", newText);
            CarrosFragment  cf= new CarrosFragment();
            cf.setArguments(args);
            replaceFragment(cf);
            return false;
        }
    };

};


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        //lendo a iddo item selecionado no menu
        int id = item.getItemId();
        if(id == R.id.action_refresh) {
            //mInterstitial.loadAd();
            PrefsAtualizar.setAtualiza(getApplicationContext(),"at",1);
            replaceFragment(new CarrosTabFragment());
            toast("Aguarde...\nAtualizando Canais...");
            return false;
        }

        if(id == R.id.action_backcat) {
            //mInterstitial.loadAd();
            PrefsAtualizar.setAtualiza(getApplicationContext(),"at",-1);
            replaceFragment(new CarrosTabFragment());
            toast("Mostrando Canais");
            return false;
        }
        
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onResume() {
        super.onResume();

        // Resume the Ad you have created:
        //mInterstitial.resume();
    }

    @Override
    protected void onPause() {
        // Pause the Ad you have created:
        //mInterstitial.pause();

        super.onPause();
    }

    @Override
    protected void onDestroy() {
        // Destroy the Ad you have created:
       // mInterstitial.destroy();

        super.onDestroy();
    }

    public void atualizaCanal(View view) {
        alerta_referesh();

    }
}
