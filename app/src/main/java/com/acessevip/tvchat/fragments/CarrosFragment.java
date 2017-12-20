package com.acessevip.tvchat.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import com.acessevip.tvchat.R;
import com.acessevip.tvchat.activity.CarroActivity;
import com.acessevip.tvchat.adapter.CarroAdapter;
import com.acessevip.tvchat.domain.Carro;
import com.acessevip.tvchat.domain.CarroService;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import livroandroid.lib.utils.AndroidUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class CarrosFragment extends BaseFragment {
    protected RecyclerView recyclerView;
    private List<Carro> carros;
    private LinearLayoutManager mLayoutManager;
    private String tipo;
    private SwipeRefreshLayout swipeLayout;
    InterstitialAd mInterstitialAd_3 ;
    private int canal = -1;
    private int bloqueio  =0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        if(getArguments() != null) {

            if (getArguments().getString("busca") == null) {
                if (getArguments().getString("tipo") == "novo") {
                    this.tipo = "novo";//carrega o fragment sem tipo

                }else {
                    this.tipo = getArguments().getString("tipo");
                }
            }else {
                this.tipo = getArguments().getString("busca");
            }
        }else this.tipo = "";


    }

    @Override
    public void onResume() {
        //adMob - interstitial 3
        mInterstitialAd_3 = new InterstitialAd(getActivity());
        mInterstitialAd_3.setAdUnitId("ca-app-pub-1025155768178267/6421875039");

        AdRequest adRequest_3 = new AdRequest.Builder()
               //.addTestDevice("23DC7227932B734FD41A35786991BF66")
                .build();

        mInterstitialAd_3.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                Carro c = carros.get(canal);
                Intent intent = new Intent(getContext(), CarroActivity.class);
                intent.putExtra("carro",c);
                startActivity(intent);

                super.onAdClosed();
            }

        });

        mInterstitialAd_3.loadAd(adRequest_3);

        super.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        // Inflate the layout for this fragment
        View  view = inflater.inflate(R.layout.fragment_carros, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
        //criando o swipe refresh
        swipeLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeToRefresh);
        swipeLayout.setOnRefreshListener(OnRefreshListener());
        swipeLayout.setColorSchemeResources(
                R.color.refresh_progress1, R.color.refresh_progress2, R.color.refresh_progress3);
        return  view;
    }

    private SwipeRefreshLayout.OnRefreshListener OnRefreshListener() {
        return new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(AndroidUtils.isNetworkAvailable(getContext())){
                    taskCarros(true);
                }else {
                    swipeLayout.setRefreshing(false);
                    alert(R.string.error_conexao_indisponivel);
                }

            }
        };
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        taskCarros(false);
    }

    private void taskCarros(boolean pullToRefresh) {

        startTask("carros",new GetCarrosTask(pullToRefresh), pullToRefresh ? R.id.swipeToRefresh : R.id.progress);

    }

    private class GetCarrosTask implements TaskListener<List<Carro>> {

        private boolean refresh;
        public GetCarrosTask(boolean refresh) {
            this.refresh = refresh;
        }

        @Override
        public List<Carro> execute() throws Exception {

            return CarroService.getCarros(getContext(),tipo,refresh);

        }

        @Override
        public void updateView(List<Carro> carros) {

            if(carros != null) {
                CarrosFragment.this.carros = carros;
                recyclerView.setAdapter(new CarroAdapter(getContext(), carros, onClickCarro()));

            }

        }

        @Override
        public void onError(Exception e) {
            alert("Erro ao buscar os dados na internet");
        }

        @Override
        public void onCancelled(String cod) {

        }
    }




    private CarroAdapter.CarroOnClickListener onClickCarro(){

        return new CarroAdapter.CarroOnClickListener() {
            @Override
            public void onClickCarro(View view, int idx) {

                canal = idx;
                if  ( mInterstitialAd_3.isLoaded())  {
                    mInterstitialAd_3.show();
                }else {

                    if (bloqueio < 2) {
                        bloqueio++;
                        AndroidUtils.alertDialog(getActivity(),"Tentativa : "+ bloqueio+"/3","Aguarde uns 5 segundos e tente novamente..., pois a propaganda esta sendo Carregada");

                    }else {
                        bloqueio = 0;
                        Carro c = carros.get(canal);
                        Intent intent = new Intent(getContext(), CarroActivity.class);
                        intent.putExtra("carro",c);
                        startActivity(intent);

                    }
                }

                //Toast.makeText(getContext(), "foto: " + c.urlFoto, Toast.LENGTH_SHORT).show();
            }
        };
    }
}
