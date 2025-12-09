package com.joseantonio.historialproducto;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.joseantonio.historialproducto.databinding.FragmentInicioBinding;
import com.joseantonio.historialproducto.databinding.FragmentMantenimientoBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jspecify.annotations.NonNull;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class MantenimientoFragment extends Fragment {

    private FragmentMantenimientoBinding binding;
    private RecyclerView reciclerView;

    private void mantenimientoChange(JSONArray mantenimientos) {
        if (mantenimientos == null) return;
        try {
            MantenimientoAdapter adapter = new MantenimientoAdapter(mantenimientos);
            // Asegurar que el LayoutManager está configurado
            if (reciclerView.getLayoutManager() == null) {
                reciclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            }
            this.reciclerView = binding.recyclerViewMantenimientos;

            reciclerView.setAdapter(adapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // Inflar el diseño para este fragmento (asociar a la vista)
        binding = FragmentMantenimientoBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
    @Override
    public void onViewCreated(@androidx.annotation.NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //1. Obtener el ViewModel compartido
        SharedViewModel viewModel =
                new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        //Inicializar ReciclerView desde binding
        reciclerView = binding.recyclerViewMantenimientos;
        reciclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        reciclerView.setHasFixedSize(true);
        var _this = this;
        //2. Observar los datos JSON de la máquina
        viewModel.getJson().observe(getViewLifecycleOwner(), (json) -> {
            try {
                var nSerie = json.getString("serialNumber");

                Tools.httpRequest("https://customer.revimachinery.com/query.php?q=maintenances&serial=" + nSerie, new Tools.HttpCallback() {
                    @Override
                    public void onSuccess(String response) {
                        if (response.isBlank() || response.isEmpty()) {
                            return;
                        }
                        try {
                            JSONArray maintenancesArr = new JSONArray(response);

                            _this.mantenimientoChange(maintenancesArr);
                        } catch (JSONException ex) {
                            ex.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Exception e) {

                    }
                });
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });


    }


    @Override
    public void onDestroyView(){
        super.onDestroyView();
        binding = null; // limpiar el binding
    }
}