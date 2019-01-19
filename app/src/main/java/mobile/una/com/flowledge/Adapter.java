package mobile.una.com.flowledge;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import mobile.una.com.flowledge.model.Question;

/**
 * Created by Luis Bogantes on 08/01/2019.
 */

public class Adapter extends BaseAdapter {

    Context contexto;
    List<Question> preguntas;
    String userSesion;



    public Adapter(Context contexto, List<Question> preguntas,String userSesion) {
        this.contexto = contexto;
        this.preguntas = preguntas;
        this.userSesion = userSesion;
    }

    @Override
    public int getCount() {
        return preguntas.size();
    }

    @Override
    public Object getItem(int position) {
        return preguntas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vista = convertView;
        LayoutInflater inflat = LayoutInflater.from(contexto);
        vista = inflat.inflate(R.layout.fragment_reply, null);

        ImageView imagen = (ImageView) vista.findViewById(R.id.imgItem);
        TextView txt1 = (TextView) vista.findViewById(R.id.txtTitle);
        TextView txt2 = (TextView) vista.findViewById(R.id.txtDescription);


        txt1.setText(preguntas.get(position).getUserNickname().toString());
        txt2.setText(preguntas.get(position).getDescription().toString());
        //imagen.setImageResource();

        if(preguntas.get(position).getUserNickname().equals(userSesion)){

            ImageView trash = (ImageView) vista.findViewById(R.id.imgTrash);
            trash.setVisibility(View.VISIBLE);
        }

        return vista;
    }


}
