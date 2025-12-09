package com.joseantonio.historialproducto;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.joseantonio.historialproducto.databinding.FragmentInicioBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class InicioFragment extends Fragment {

    private FragmentInicioBinding binding;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentInicioBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.btnNumeroSerie.setOnClickListener((ev) -> {
            var nSerie = binding.inNumeroSerie.getText();
            var _this = this;
            _this.binding.txtNumSerieStatus.setText("");

            Tools.httpRequest("https://customer.revimachinery.com/query.php?q=machine&serial=" + nSerie, new Tools.HttpCallback() {
                @Override
                public void onSuccess(String response) {
                    if(response.isBlank() || response.isEmpty()){
                        binding.txtNumSerieStatus.setText("Número de serie no encontrado");
                        return;
                    }
                    try{
                        JSONObject machineObj = new JSONObject(response); //EL OBJETO A PASAR
                        SharedViewModel viewModel =
                                new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
                        viewModel.setJson(machineObj);
                        // PASAR EL OBJETO MACHINEOBJ A LOS FRAGMENTOS TAB1FRAGMENT Y TAB2FRAGMENT

                        NavHostFragment.findNavController(InicioFragment.this)
                                .navigate(R.id.action_InicioFragment_to_MachineFragment);
                    }catch(JSONException ex){
                        binding.txtNumSerieStatus.setText("Número de serie no encontrado");
                    }
                }

                @Override
                public void onError(Exception e) {
                    binding.txtNumSerieStatus.setText("Número de serie no encontrado");
                }
            });
        });

        binding.buttonFirst.setOnClickListener(v ->
                NavHostFragment.findNavController(InicioFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment)
        );
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}