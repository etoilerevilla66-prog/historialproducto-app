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


public class MachineFragment extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    TabLayout tabs;
    ViewPager2 viewPager2;
    TabsAdapter adaptador;
    private FragmentMachineBinding binding;

    public MachineFragment() {

    }


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