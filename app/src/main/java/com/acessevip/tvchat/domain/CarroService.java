package com.acessevip.tvchat.domain;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.acessevip.tvchat.util.CatVerifica;
import com.acessevip.tvchat.util.PrefsAtualizar;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import livroandroid.lib.utils.FileUtils;
import livroandroid.lib.utils.HttpHelper;
import livroandroid.lib.utils.IOUtils;
import livroandroid.lib.utils.SDCardUtils;

/**
 * Created by Henrique on 17/11/2015.
 */
public class CarroService {

    //private static final String URL = "http://www.livroandroid.com.br/livro/carros/carros_{tipo}.json";
    private static final String URL = "http://livestreamtv.biz/apptvcanais/canaltv/bd/json_seguro.php";
    private static final String SENHA = "g0s8qb";

    private static final boolean LOG_ON = false;
    private static final String TAG = "CarroService";

    public static List<Carro> getCarros(Context context,String tipo,boolean refresh) throws  IOException{

            Log.e(TAG,"o tipo =" + tipo);
            List<Carro> carros = null;
            boolean buscaNoBancoDeDados = !refresh;
             if(buscaNoBancoDeDados && PrefsAtualizar.getAtualiza(context,"at") < 0) {
                //busca no banco de dados
                carros = getCarrosFromBanco(context,tipo);
                if(carros != null && carros.size() > 0  ) {
                    return carros;

                }

             }
            //se não encontrar busca no web service
            carros = getCarrosFromWebServece(context, tipo);

            return carros;



            //String url = URL.replace("{tipo}",tipo);
            //String json = HttpHelper.doGet(url);
            //salvaArquivoNaMemoriaInterna(context,url,json);
            //salvaArquivoNaMemoriaExterna(context,url,json);
            //List<Carro> carros = parserJSON(context, json);
            //
    }

    //salva os arquivos baixados do servidor na memoria interna do celular
    private static void salvaArquivoNaMemoriaInterna(Context context,String url,String json){

        String fileName = url.substring(url.lastIndexOf("/") + 1);
        File  file = FileUtils.getFile(context, fileName);
        IOUtils.writeString(file,json);
        Log.d(TAG,"arquivo salvo: " + file);
    }

    //salva arquivos json na memoria externa
    private static void salvaArquivoNaMemoriaExterna(Context context,String url,String json){
        String fileName = url.substring(url.lastIndexOf("/") + 1);
        //cria arquivo privado
        File f = SDCardUtils.getPrivateFile(context, fileName, Environment.DIRECTORY_DOWNLOADS);
       IOUtils.writeString(f,json);
        Log.d(TAG,"1) Arquivo Privado salva na pasta downloads: " + f);
        //cria arquivo publico
       f = SDCardUtils.getPublicFile(fileName,Environment.DIRECTORY_DOWNLOADS);
       IOUtils.writeString(f,json);
       Log.d(TAG, "2) Arquivo publico salva na pasta downloads: " + f);
    }

    //abre o arquivo salvo, se existir, e cria a lista de carros
    public static List<Carro> getCarrosFromArquivo(Context context, String tipo) throws IOException {

        String fileName = String.format("carros_%s.json", tipo);
        Log.d(TAG,"Abrindo arquivo: " + fileName);
        //Le o arquivo da memoria
        String json = FileUtils.readFile(context,fileName,"UTF-8");
        if(json == null) {
            Log.d(TAG,"Arquivo "+fileName+" não encontado");
            return null;
        }
        List<Carro> carros = parserJSON(context,json);
        Log.d(TAG,"Carros lidos do arquivo  "+fileName+".");
        return carros;
    }



    //busca o carros do banco or tipo
    public static  List<Carro> getCarrosFromBanco(Context context,String tipo) throws IOException {

        CarroDB db = new CarroDB(context);
        try {

            if (!CatVerifica.VERIFICA(tipo)) {
                List<Carro> carros = db.findAllByNome(tipo);
                Log.d(TAG,"Retornando por nome: " + carros.size() + " carros [" + tipo +"] do banco");
                return carros;
            }else {
                List<Carro> carros = db.findAllByTipo(tipo);
                Log.d(TAG,"Retornando por tipo: " + carros.size() + " carros [" + tipo +"] do banco");
                return carros;
            }




        }finally {
            db.close();
        }

    }

    private static void salvarCarros(Context context,String tipo,List<Carro> carros){
        CarroDB db = new CarroDB(context);
        try{
            //deleta os carros por tipo para limpar o banco
            db.deleteCarrosByTipo(tipo);
            //salva todos os carros
            for (Carro c : carros) {
                c.tipo = tipo;
                Log.d(TAG,"Salvando o carro" + c.nome);
                db.save(c);
            }

        }finally {
            db.close();
        }
    }

    //Faz a requisicao HTTP, cria a lista de carros e salva o Json em arquivo
    public static List<Carro> getCarrosFromWebServece(Context context, String tipo) throws  IOException {

        //String url = URL.replace("{tipo}", tipo);
        String url = URL;
        Log.d(TAG, "URL: " + url);

        Map<String,String> post = new HashMap<String,String>();
        post.put("cat",tipo);
        post.put("senha",SENHA);


        //String json = HttpHelper.doGet(url);
        String json = HttpHelper.doPost(url, post, "UTF-8");
        Log.e("RETORNOJSON", "Retorno JSON" + json);
        //salvaArquivoNaMemoriaInterna(context,url,json);
        List<Carro> carros = parserJSON(context, json);
        if(carros.size() > 1) {
            salvarCarros(context, tipo, carros);
        }

        return carros;

    }

    private void StatusAtualizacao(Context context, int at) {
        PrefsAtualizar.setAtualiza(context,"at",-1);
    }

    //fazendo o parse do json
    private  static List<Carro> parserJSON(Context context,String json)  {


        List<Carro> carros = new ArrayList<Carro>();

        try {
            JSONObject root = new JSONObject(json);
            JSONObject obj = root.getJSONObject("carros");
            JSONArray jsonCarros = obj.getJSONArray("carro");

            //insere ca da c arro na lista
            for (int i =0;i < jsonCarros.length();i++) {
                JSONObject jsonCarro = jsonCarros.getJSONObject(i);
                Carro c = new Carro();
                //Le as informações de cada carro
                c.nome = jsonCarro.optString("nome","UTF-8");
                c.tipo_play = jsonCarro.optString("t_play");
                c.nome_original = jsonCarro.optString("n_original");
                c.desc = jsonCarro.optString("desc");
                c.urlFoto = jsonCarro.optString("url_foto");
                c.urlVideo = jsonCarro.optString("url_video");
                c.duracao = jsonCarro.optString("duracao");
                c.diretor = jsonCarro.optString("diretor");
                c.elenco = jsonCarro.optString("elenco");
                c.ano_lancamento = jsonCarro.optString("ano");

       if(LOG_ON){
                    Log.d(TAG,"Carro"+ c.nome + " > " + c.urlFoto);
                }

                carros.add(c);

            }
            Log.d(TAG, carros.size() + "encontrados");

        }catch (JSONException e) {
            //throw new IOException(e.getMessage(),e);
        }

        return carros;

    }
}
