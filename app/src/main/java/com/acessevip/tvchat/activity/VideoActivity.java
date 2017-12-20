package com.acessevip.tvchat.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.support.v4.app.NavUtils;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.acessevip.tvchat.R;
import com.acessevip.tvchat.domain.Carro;
import com.acessevip.tvchat.fragments.VideoFragment;

public class VideoActivity extends BaseActivity {
    private Carro carro;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
       //configura o toolbar para a action bar
        setUpToolbar();
        carro = (Carro) getIntent().getSerializableExtra("carro");
        getSupportActionBar().setTitle(carro.nome);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().hide();
        if(savedInstanceState == null) {
            //adiciona o fragmente no layout da actvity
            VideoFragment videoFragment = new VideoFragment();
            videoFragment.setArguments(getIntent().getExtras());
            getSupportFragmentManager().beginTransaction().replace(R.id.fragLayout,videoFragment).commit();
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            alerta();

        }





    }

   public void videofullteste(View v) {

       if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
           v.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
       }
       alerta();

    }

    //cria um toast com uma imagem
    public void alerta() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            ImageView alertIMG = new ImageView(this);
            alertIMG.setImageResource(R.drawable.ic_fingerprint);
            Toast aviso = new Toast(this);
            aviso.setView(alertIMG);
            aviso.setGravity(Gravity.CENTER_VERTICAL, -200, 0);
            aviso.setDuration(Toast.LENGTH_SHORT);
            aviso.show();
        }
    }


        @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = NavUtils.getParentActivityIntent(getActivity());
                intent.putExtra("carro", carro);
                NavUtils.navigateUpTo(getActivity(),intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
