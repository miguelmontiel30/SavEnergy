package com.example.miguelangel.savenergy;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class Menu_configuracion extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    Button btn_configurar_perfil,btn_contrasenia,btn_tarifa;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Menu_configuracion() {
        // Required empty public constructor
    }

    public static Menu_configuracion newInstance(String param1, String param2) {
        Menu_configuracion fragment = new Menu_configuracion();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        btn_configurar_perfil =  (Button) btn_configurar_perfil.findViewById(R.id.btn_configurar_perfil);
        btn_contrasenia = (Button) btn_contrasenia.findViewById(R.id.btn_contrasenia);
        btn_tarifa = (Button) btn_tarifa.findViewById(R.id.btn_tarifa);

        btn_configurar_perfil.setOnClickListener(this);
        btn_contrasenia.setOnClickListener(this);
        btn_tarifa.setOnClickListener(this);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_menu_configuracion, container, false);

    }


    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    @Override
    public void onClick(View view) {
        /*if(view == btn_configurar_perfil){
            cv_configurar_perfil();
        }else if (view == btn_contrasenia){
            cv_contrasenia();
        }else if (view == btn_tarifa){
            cv_tarifa();
        }*/
    }

    private void cv_tarifa() {
    }

    private void cv_contrasenia() {
    }

    //Fragment fragment = null;

    public void cv_configurar_perfil() {

       /* fragment = new Configuracion_Perfil();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.Principal, fragment);
        transaction.addToBackStack(null);

        // Commit a la transacción
        transaction.commit();

*/
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
