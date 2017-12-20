package com.acessevip.tvchat.activity;

import com.acessevip.tvchat.R;
import android.support.v7.widget.Toolbar;


/**
 * Created by Henrique on 14/11/2015.
 */
public class BaseActivity extends livroandroid.lib.activity.BaseActivity {

    protected void setUpToolbar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if(toolbar != null) {
            setSupportActionBar(toolbar);
        }
    }

}
