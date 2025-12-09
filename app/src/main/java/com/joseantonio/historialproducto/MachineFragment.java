package com.joseantonio.historialproducto;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;

import com.google.android.material.tabs.TabLayout;
import com.joseantonio.historialproducto.databinding.FragmentMachineBinding;

/**
 * Una subclase simple {@link Fragment}
 * Utilizar el método de fábrica {@link MachineFragment#newInstance}
 * para crear una instancia de este fragmento.
 */
public class MachineFragment extends Fragment {

    // Cambiar el nombre de los argumentos de los parámetros, elegir nombres que coincidan
    // Los parámetros de inicialización del fragmento, por ejemplo ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // renombrar y cambiar tipos de parámetros
    private String mParam1;
    private String mParam2;

    TabLayout tabs;
    ViewPager2 viewPager2;
    TabsAdapter adaptador;
    private FragmentMachineBinding binding;

    public MachineFragment() {
        //Se requiere constructor público vacío
    }

    /**
     * Utilizar este método de fábrica para crear una nueva instancia de
     * este fragmento utilizando los parámetros proporcionados.
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MachineFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MachineFragment newInstance(String param1, String param2) {
        MachineFragment fragment = new MachineFragment();
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

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Inflar (asociar a la vista) el diseño para este fragmento

        View v = inflater.inflate(R.layout.fragment_machine, container, false);;

        tabs = v.findViewById(R.id.tabLayout);

        tabs.addTab(tabs.newTab().setText("Información").setId(0));
        tabs.addTab(tabs.newTab().setText("Mantenimiento").setId(1));

        adaptador = new TabsAdapter(((FragmentActivity)getContext()).getSupportFragmentManager(), getActivity().getLifecycle());

        viewPager2 = v.findViewById(R.id.pager);
        viewPager2.setAdapter(adaptador);
        tabs.setTabMode(TabLayout.MODE_FIXED);

        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        return v;
    }
}