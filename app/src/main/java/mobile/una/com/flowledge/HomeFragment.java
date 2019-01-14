package mobile.una.com.flowledge;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import mobile.una.com.flowledge.model.AreaData;
import mobile.una.com.flowledge.model.Sesion;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    View v;
    RecyclerView mRecyclerView;
    List<AreaData> mFlowerList;
    AreaData mFlowerData;
    Sesion s = new Sesion();

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_home, container, false);
        Bundle bundle = getArguments();
        if(bundle != null){
            s = (Sesion) bundle.getSerializable("sesion");
        }
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.v = view;
        init();
    }

    private void init(){
        mRecyclerView = v.findViewById(R.id.recyclerview);
        GridLayoutManager mGridLayoutManager = new GridLayoutManager(getContext(), 2);
        mRecyclerView.setLayoutManager(mGridLayoutManager);
        llenarAreas();
        MyAdapter myAdapter = new MyAdapter(getContext(), mFlowerList);
        mRecyclerView.setAdapter(myAdapter);
    }

    public void llenarAreas() {
        mFlowerList = new ArrayList<>();
        mFlowerData = new AreaData("Bases de datos", R.drawable.bases);
        mFlowerList.add(mFlowerData);
        mFlowerData = new AreaData("Estructura de datos", R.drawable.estructuras);
        mFlowerList.add(mFlowerData);
        mFlowerData = new AreaData("POO", R.drawable.poo);
        mFlowerList.add(mFlowerData);
        mFlowerData = new AreaData("Redes", R.drawable.redes);
        mFlowerList.add(mFlowerData);

    }
}
