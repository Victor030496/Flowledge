package mobile.una.com.flowledge;

/**
 * Created by Luis Bogantes on 08/01/2019.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import mobile.una.com.flowledge.model.Question;
import mobile.una.com.flowledge.model.Respuesta;

/**
 * Created by Luis Bogantes on 08/01/2019.
 */

public class Adapter2 extends BaseAdapter {

    Context contexto;
    List<Respuesta> respuestas;
    String nickUser;


    public Adapter2(Context contexto, List<Respuesta> preguntas,String nickUser) {
        this.contexto = contexto;
        this.respuestas = preguntas;
        this.nickUser = nickUser;
    }

    @Override
    public int getCount() {
        return respuestas.size();
    }

    @Override
    public Object getItem(int position) {
        return respuestas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vista = convertView;
        LayoutInflater inflat = LayoutInflater.from(contexto);
        vista = inflat.inflate(R.layout.activity_reply2, null);

        ImageView imagen = (ImageView) vista.findViewById(R.id.imgItem);
        TextView txt1 = (TextView) vista.findViewById(R.id.txtTitle);
        TextView txt2 = (TextView) vista.findViewById(R.id.txtDescription);
        TextView txt3 = (TextView) vista.findViewById(R.id.txtlikes);


        txt1.setText(respuestas.get(position).getNickRespuesta().toString());
        txt2.setText(respuestas.get(position).getRespuesta().toString());
        int aux = respuestas.get(position).getLikes();
        String  aux2 = String.valueOf(aux);
        txt3.setText(aux2);

        //imagen.setImageResource();

        if(respuestas.get(position).getNickRespuesta().equals(nickUser)){

            ImageView trash = (ImageView) vista.findViewById(R.id.imgTrash2);
            trash.setVisibility(View.VISIBLE);
        }

        return vista;
    }


}

