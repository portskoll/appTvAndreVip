package com.acessevip.tvchat.adapter;

import java.util.ArrayList;
import java.util.List;

import com.acessevip.tvchat.R;

/**
 * Created by Henrique on 14/11/2015.
 */
public class NavDrawerMenuItem {
    //Titulo : R.string. ....
    public int title;

    //Figura : R.drawable. ....
    public int img;

    // colocar fundo cinza quamdo a linha esta seçecionada
    public boolean selected;

    public NavDrawerMenuItem(int img, int title) {
        this.img = img;
        this.title = title;
    }

    //Criando alista com itens de menu
    public static List<NavDrawerMenuItem> getList() {

        List<NavDrawerMenuItem> list = new ArrayList<NavDrawerMenuItem>();
        list.add(new NavDrawerMenuItem(R.drawable.ic_tab,R.string.carros));
        list.add(new NavDrawerMenuItem(R.drawable.ic_novo,R.string.site_livro));
        list.add(new NavDrawerMenuItem(R.drawable.ic_live_tv,R.string.app_tv));
        list.add(new NavDrawerMenuItem(R.drawable.ic_live_tv,R.string.futebol));
        list.add(new NavDrawerMenuItem(R.drawable.ic_configuracoes,R.string.configuracoes));
        list.add(new NavDrawerMenuItem(R.drawable.ic_chat_black_24dp,R.string.chat));

        return list;
    }
}
