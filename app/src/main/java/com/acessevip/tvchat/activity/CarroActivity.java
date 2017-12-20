package com.acessevip.tvchat.activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import com.acessevip.tvchat.domain.Carro;
import com.acessevip.tvchat.fragments.CarroFragment;
import com.acessevip.tvchat.R;
import com.acessevip.tvchat.fragments.CarrosTabFragment;
import com.acessevip.tvchat.util.AvaliarAPP;
import com.acessevip.tvchat.util.PrefUser;
import com.acessevip.tvchat.util.PrefsAtualizar;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class CarroActivity extends BaseActivity {

    //InterstitialAd mInterstitialAd_2 ;
    private int prop_interstitial = 0;



    @Override
    protected void onDestroy() {
        super.onDestroy();

    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carro);


        //adMob - Banner
        AdView mAdView = (AdView) findViewById(R.id.adView_2);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

       /* //adMob - interstitial
        mInterstitialAd_2 = new InterstitialAd(this);
        mInterstitialAd_2.setAdUnitId("ca-app-pub-1025155768178267/2880030631");

        AdRequest adRequest_2 = new AdRequest.Builder()
         .addTestDevice("AC8B43C090956A123E8B6F0220FAE8F0")
                .build();

        mInterstitialAd_2.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                prop_interstitial = 1;
                super.onAdClosed();
            }

        });

        mInterstitialAd_2.loadAd(adRequest_2);*/
        //configura o toolbar como action bar
        setUpToolbar();
        //atualiza o carro no fragment
        CarroFragment cf = (CarroFragment) getSupportFragmentManager().findFragmentById(R.id.CarroFragment);
        Carro c = (Carro) getIntent().getSerializableExtra("carro");
       // String img = "http://i.imgur.com/CDdkufG.jpg?1";
        cf.setCarro(c);


               //colocando Titulo no toobar e botao set navigation

        getSupportActionBar().setTitle(c.nome);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        alerta_referesh();
    }

    public void alerta_referesh() {

        if (PrefUser.getUserTcheia(this,"id") != 1) {
            AlertDialog.Builder adb = new AlertDialog.Builder(this);// cria o AD
            adb.setTitle("Tela Cheia...");
            adb.setIcon(R.drawable.telacheia);
            adb.setPositiveButton("OK",null);
            adb.setNegativeButton("Já entendi", new AlertDialog.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    PrefUser.setUserTcheia(getApplicationContext(),"id",1);
                    Toast.makeText(getApplicationContext(),"Ajuda desativada!",Toast.LENGTH_SHORT).show();
                }
            });
            adb.setMessage("Assista este canal em tela cheia clicando no ícone na barra superior.");

            adb.show();
        }

    }


}
