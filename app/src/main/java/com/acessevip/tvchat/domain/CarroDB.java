package com.acessevip.tvchat.domain;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Henrique on 03/12/2015.
 */
public class CarroDB extends SQLiteOpenHelper {
    private static final String TAG = "sql";
    public static final String NOME_BANCO = "cat_filmes.sqlite";
    private static final int VERSAO_BANCO = 1;

    public CarroDB(Context context) {

        super(context, NOME_BANCO, null, VERSAO_BANCO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "Criando a tabela de carros");
        db.execSQL("create table if not exists carro(_id integer primary key autoincrement,tipo_play text ,nome text,nome_original text, desc text, url_foto text, duracao text, url_video text,diretor text, elenco text, tipo text,ano_lancamento text);");
        Log.d(TAG, "Tabela criada com sucesso.");

    }

    /*public String tipo_play;
    public String tipo;
    public String nome;//
    public String nome_original;//
    public String desc;//
    public String urlFoto;//
    public String urlVideo;//
    public String duracao; //
    public String diretor; //
    public String elenco; //
    public String ano_lancamento; //*/


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    //insere um novo carro, ou atualiza o ja existente
    public long save(Carro carro){
        long id = carro.id;
        SQLiteDatabase db = getWritableDatabase();
        try {
            ContentValues values = new ContentValues();
            values.put("nome",carro.nome);
            values.put("desc",carro.desc);
            values.put("url_foto",carro.urlFoto);
            values.put("duracao",carro.duracao);
            values.put("url_video",carro.urlVideo);
            values.put("diretor",carro.diretor);
            values.put("nome_original",carro.nome_original);
            values.put("tipo", carro.tipo);
            values.put("tipo_play", carro.tipo_play);
            values.put("ano_lancamento", carro.ano_lancamento);
            values.put("elenco", carro.elenco);
            if(id!=0){
                String _id = String.valueOf(carro.id);
                String[] whereArgs = new String[]{_id};
                //faz o update > update carro set values = ... where _id=?
                int count = db.update("carro",values,"_id=?",whereArgs);
                return count;
            }else {
                //comando equivalente a sql insert
                db.insert("carro","",values);
                return id;
            }


        }finally {
            db.close();
        }
    }

    //Deleta Carro
    public int delete(Carro carro) {
        SQLiteDatabase db = getWritableDatabase();
        try{
            //delete from carro where _id=?
            int count = db.delete("carro","_id=?",new String[]{String.valueOf(carro.id)});
            Log.i(TAG, "Deletou [" + count + "] registro");
            return count;
        }finally {
            db.close();
        }
    }

    //Deleta os Carro por tipo fornecido
    public int deleteCarrosByTipo(String tipo) {
        SQLiteDatabase db = getWritableDatabase();
        try{
            //delete from carro where _id=?
            int count = db.delete("carro", "tipo=?", new String[]{tipo});
            Log.i(TAG,"Deletou [" + count + "] registros");
            return count;
        }finally {
            db.close();
        }
    }

    //Consulta a lista com todos os carros
    public List<Carro> findAll() {
        SQLiteDatabase db = getWritableDatabase();
        try{
            //select * from carro
            Cursor c = db.query("carro", null, null, null, null, null, null, null);
            return toList(c);

        }finally {
            db.close();
        }

    }

    //Consulta a lista de carros por tipo
    public List<Carro> findAllByTipo(String tipo) {
        SQLiteDatabase db = getWritableDatabase();
        try{
            //select * from carro where tipo=?
            Cursor c = db.query("carro", null, "tipo ='" + tipo + "'", null, null, null, null, null);
            return toList(c);

        }finally {
            db.close();
        }

    }

    //Consulta a lista de carros por tipo
    public List<Carro> findAllByNome(String nome) {
        SQLiteDatabase db = getWritableDatabase();
        try{
            //select * from carro where tipo=?
            Cursor  c = db.query("carro",null,"nome like" + "'%"+nome+"%'",null,null,null,null);
            //Cursor c = db.query("carro", null, "tipo ='" + tipo + "'", null, null, null, null, null);
            return toList(c);

        }finally {
            db.close();
        }

    }



    // Lê o cursor e cria a lsta de carros
    private List<Carro> toList(Cursor c) {
        List<Carro> carros = new ArrayList<Carro>();
        if(c.moveToFirst()){
            do {
                Carro carro = new Carro();
                carros.add(carro);
                //recupera os atributos de carro
                carro.id = c.getLong(c.getColumnIndex("_id"));
                carro.nome = c.getString(c.getColumnIndex("nome"));
                carro.desc = c.getString(c.getColumnIndex("desc"));
                carro.urlFoto = c.getString(c.getColumnIndex("url_foto"));
                carro.duracao = c.getString(c.getColumnIndex("duracao"));
                carro.urlVideo = c.getString(c.getColumnIndex("url_video"));
                carro.diretor = c.getString(c.getColumnIndex("diretor"));
                carro.nome_original = c.getString(c.getColumnIndex("nome_original"));
                carro.tipo_play = c.getString(c.getColumnIndex("tipo_play"));
                carro.tipo = c.getString(c.getColumnIndex("tipo"));
                carro.ano_lancamento = c.getString(c.getColumnIndex("ano_lancamento"));
                carro.elenco = c.getString(c.getColumnIndex("elenco"));

            }while (c.moveToNext());
        }
        return carros;
    }

    //Executa um SQL
    public void execSQL(String sql) {
        SQLiteDatabase db = getWritableDatabase();
        try{
            db.execSQL(sql);
        }finally {
            db.close();
        }
    }

    //Executa um SQL
    public void execSQL(String sql,Object[] args) {
        SQLiteDatabase db = getWritableDatabase();
        try{
            db.execSQL(sql,args);
        }finally {
            db.close();
        }
    }
}
