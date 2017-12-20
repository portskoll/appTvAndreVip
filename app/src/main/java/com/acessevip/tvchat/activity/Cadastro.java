package com.acessevip.tvchat.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.acessevip.tvchat.R;
import com.acessevip.tvchat.util.PrefUser;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import livroandroid.lib.utils.AndroidUtils;
import livroandroid.lib.utils.HttpHelper;

public class Cadastro extends AppCompatActivity {

    private ProgressBar pb;
    private EditText edt_nome;
    private EditText edt_senha;
    private TextView txt_status;
    String URL ="http://livestreamtv.biz/apptvcanais/canaltv/bd/cadPost.php";
    String post_nome = "";
    String post_senha = "";
    private int id_retorno = -1;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
        pb = (ProgressBar) findViewById(R.id.progBar_cad);
        edt_nome = (EditText)findViewById(R.id.edt_cad_nome);
        edt_senha = (EditText)findViewById(R.id.edt_cad_senha);
        txt_status = (TextView) findViewById(R.id.cadStatus);


    }

    public void enviar_cad (View v) {
        String email = edt_nome.getText().toString();
        if(email.contains("@") && email.contains(".") && email.length() > 5 ) {
            post_cat();
        }else {
            Toast.makeText(getApplicationContext(),R.string.erro_email,Toast.LENGTH_LONG).show();
        }




    }
//coorige o retorno pois vem caracters a mais
    public static int strToInt(String valor, int padrao)
    {
        try
        {
            return Integer.parseInt(valor.replaceAll("[\\D]", ""));
        }
        catch (NumberFormatException e){
            return padrao;
        }
    }

    public void post_cat() {
        post_nome = edt_nome.getText().toString();
        post_senha = edt_senha.getText().toString();
        if(post_nome.equals("") || post_senha.equals("")) {
            Toast.makeText(getApplicationContext(),"Todos os campos devem ser preenchidos!",Toast.LENGTH_LONG).show();
        }else {
            CadastroUser cadastroUser = new CadastroUser();
            cadastroUser.execute();
        }

    }

    private class CadastroUser extends AsyncTask<Void,Void,String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pb.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(Void... params) {
            String n = post_nome;
            String s = post_senha;
            String retorno=null;

            Map<String,String> post = new HashMap<String,String>();
            post.put("nome",n);
            post.put("senha", s);
            try {
                if(AndroidUtils.isNetworkAvailable(getApplicationContext())){
                    retorno = HttpHelper.doPost(URL,post,"UTF-8");
                    Log.e("cadastro Retorno",retorno);
                }


            }catch (IOException e) {
                Log.e("cadastro",e.getMessage(),e);
                return null;
            }


            return retorno;

        }

        @Override
          protected void onPostExecute(String s) {

            if(s != null) {
                Log.d("retorno", s);
                if(s.contains("-1")){
                    txt_status.setText(R.string.erro_usuario_existente);
                    pb.setVisibility(View.INVISIBLE);
                    Toast.makeText(getApplicationContext(),R.string.erro_cadastro,Toast.LENGTH_LONG).show();
                }else {
                    int idx = strToInt(s,-2);
                    Log.d("Inteiro",String.valueOf(idx));
                    PrefUser.setUserID(getApplicationContext(),"id",idx);
                    pb.setVisibility(View.INVISIBLE);
                    if(PrefUser.getUserID(getApplicationContext(),"id")> 0) {
                        Intent it = new Intent(getApplicationContext(),FilmeTela2.class);
                        startActivity(it);
                        finish();
                    }else {
                        Toast.makeText(getApplicationContext(),R.string.erro_cadastro,Toast.LENGTH_LONG).show();

                    }
                }

            }else {
                Toast.makeText(getApplicationContext(),R.string.error_conexao_indisponivel,Toast.LENGTH_LONG).show();
                txt_status.setText(R.string.error_conexao_indisponivel);
                pb.setVisibility(View.INVISIBLE);
            }


        }
    }
}
