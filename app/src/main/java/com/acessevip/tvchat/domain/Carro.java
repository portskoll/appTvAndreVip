package com.acessevip.tvchat.domain;

import java.io.Serializable;

/**
 * Created by Henrique on 17/11/2015.
 */
public class Carro implements Serializable{

    private static final long serialVersionUID = 6601006766832473959L;

    public long id;
    public String tipo_play; //tipo de filme 0-local 1-PlayerNativo 2-vk 3-yt 4-html ou php
    public String tipo;// categoria do filme (acão, terror etc)
    public String nome;
    public String nome_original;
    public String desc;
    public String urlFoto;
    public String urlVideo;
    public String duracao;
    public String diretor;
    public String elenco;
    public String ano_lancamento;



    @Override
    public String toString() {
        return "Carro{" + "nome='" + nome + '\'' + '}';
    }
}
