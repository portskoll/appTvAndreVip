package com.acessevip.tvchat.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import com.acessevip.tvchat.R;
import com.acessevip.tvchat.domain.Carro;

/**
 * Created by Henrique on 17/11/2015.
 */
public class CarroAdapter extends RecyclerView.Adapter<CarroAdapter.CarrosViewHolder> {

    public static final String TAG = "livroandroid";
    private final List<Carro> carros;
    private final Context context;
    private CarroOnClickListener carroOnClickListener;

    public CarroAdapter(Context context, List<Carro> carros, CarroOnClickListener carroOnClickListener) {
        this.carros = carros;
        this.context = context;
        this.carroOnClickListener = carroOnClickListener;
    }

    @Override
    public CarroAdapter.CarrosViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_carro,parent,false);
        CarrosViewHolder holder = new CarrosViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final CarroAdapter.CarrosViewHolder holder, final int position) {
        Carro c = carros.get(position);
        holder.tNome.setText(c.nome);
        holder.tNomeO.setText(c.nome_original);
        holder.tAno.setText(c.ano_lancamento);
        holder.tDuracao.setText(c.duracao);
        holder.progress.setVisibility(View.VISIBLE);

        Picasso.with(context).load(c.urlFoto).fit().into(holder.img, new com.squareup.picasso.Callback() {
            @Override
            public void onSuccess() {
                holder.progress.setVisibility(View.GONE);
            }

            @Override
            public void onError() {
                holder.progress.setVisibility(View.GONE);
            }
        });

        if(carroOnClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    carroOnClickListener.onClickCarro(holder.itemView,position);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return this.carros != null ? this.carros.size() : 0;
    }

    public interface CarroOnClickListener {
        public void onClickCarro(View view,int idx);
    }

    public static class CarrosViewHolder  extends RecyclerView.ViewHolder{
        public TextView tNome;
        public TextView tNomeO;
        public TextView tAno;
        public TextView tDuracao;
        ImageView img;
        ProgressBar progress;
        CardView cardView;

        public CarrosViewHolder(View itemView) {
            super(itemView);
            tNome = (TextView) itemView.findViewById(R.id.f_nome);
            tNomeO = (TextView) itemView.findViewById(R.id.f_nome_original);
            tAno = (TextView) itemView.findViewById(R.id.f_ano);
            tDuracao = (TextView) itemView.findViewById(R.id.f_duracao);
            img = (ImageView) itemView.findViewById(R.id.imgcarro);
            progress = (ProgressBar) itemView.findViewById(R.id.progressImg);
            cardView = (CardView) itemView.findViewById(R.id.card_view);
        }
    }
}
