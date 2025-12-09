package com.joseantonio.historialproducto;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.joseantonio.historialproducto.databinding.FragmentDatosMaquinaBinding;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.Objects;


public class DatosMaquinaFragment extends Fragment {
    private FragmentDatosMaquinaBinding binding;
    private JSONObject machineObj = null;

    private TextView modelo, peso, motor, fecha, garantia, horas, numSerie, extras;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        this.binding = FragmentDatosMaquinaBinding.inflate(inflater, container, false);


        this.modelo = this.binding.txtModelo;
        this.peso = this.binding.txtPeso;
        this.motor = this.binding.txtMotor;
        this.fecha = this.binding.txtFecha;
        this.garantia = this.binding.txtGarantia;
        this.horas = this.binding.txtHoras;
        this.numSerie = this.binding.txtNumSerie;
        this.extras = Objects.requireNonNull(this.binding.txtExtras);


        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle saveInstanceState) {
        super.onViewCreated(view, saveInstanceState);
        SharedViewModel viewModel =
                new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        viewModel.getJson().observe(getViewLifecycleOwner(), json -> {
            if (json != null) {
                try {
                    JSONObject obj = json;
                    modelo.setText(obj.getString("model"));
                    peso.setText(obj.getString("weight"));
                    motor.setText(obj.getString("engine"));
                    fecha.setText(obj.getString("dateOfPurchase"));
                    garantia.setText(obj.getString("guarantee"));
                    horas.setText(obj.getString("hours"));
                    numSerie.setText(obj.getString("serialNumber"));
                    obj.getJSONArray("extras").join(",");


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}