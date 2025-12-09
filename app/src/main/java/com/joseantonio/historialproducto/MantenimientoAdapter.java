package com.joseantonio.historialproducto;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class MantenimientoAdapter extends RecyclerView.Adapter<MantenimientoAdapter.ViewHolder> {

    private final JSONArray mantenimientos;

    public MantenimientoAdapter(JSONArray mantenimientos) {
        this.mantenimientos = mantenimientos;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent,int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_mantenimiento, parent, false);
            return new ViewHolder(view);
    }
    /*
        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return 0;
        }
    */

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        try{
                JSONObject mantenimiento = mantenimientos.getJSONObject(position);
                String id = mantenimiento.getString("id");
                String fechaOriginal = mantenimiento.getString("date");
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                SimpleDateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                String fecha = outputFormat.format(sdf.parse(fechaOriginal));
                String horas = mantenimiento.getString("hours");
                String ubicacion = mantenimiento.getString("location");
                String servicioOficial = mantenimiento.getString("official_service").equals("1") ? "SI" : "NO";
                String tipoAceite = mantenimiento.getString("oil_type");
                String procedimientos = mantenimiento.getString("procedures");
                //convierte 1 o 0 a texto legible
                String garantia = mantenimiento.getString("with_guarantee").equals("1") ? "SI" : "NO";
                //Relenar las vistas (La caja)

                holder.txtId.setText("Mantenimiento Nº " + (position + 1));
                holder.txtFecha.setText("Mantenimiento en: " + fecha);
                holder.txtHoras.setText("Horas de máquina: " + horas);
                holder.txtUbicacion.setText("Ubicacion: " + ubicacion);
                holder.txtTipoAceite.setText("Tipo de aceite: " + tipoAceite);
                holder.txtServicioOficial.setText("En servicio oficial: " + servicioOficial);
                holder.txtProcedimientos.setText("Procedimientos: " + procedimientos);
                holder.txtGarantia.setText("En garantía: " + garantia);
            }catch (JSONException e) {
                e.printStackTrace();
            } catch (ParseException e) {
            throw new RuntimeException(e);
        }

    }
        @Override
                public int getItemCount(){
            return mantenimientos.length();

    }
       //clase interna para mantener las referencias a las vistas de esta fila
    public static class ViewHolder extends RecyclerView.ViewHolder{
            final TextView txtId;
            final TextView txtFecha;
            final TextView txtUbicacion;
            final TextView txtTipoAceite;
            final TextView txtServicioOficial;
            final TextView txtHoras;
            final TextView txtProcedimientos;
            final TextView txtGarantia;

            public ViewHolder(View view){
                super(view);
                txtId = view.findViewById(R.id.txtID);
                txtFecha = view.findViewById(R.id.txtFecha);
                txtTipoAceite = view.findViewById(R.id.txtTipoAceite);
                txtUbicacion = view.findViewById(R.id.txtUbicacion);
                txtHoras = view.findViewById(R.id.txtHorasMantenimiento);
                txtServicioOficial = view.findViewById(R.id.txtServicioOficial);
                txtProcedimientos = view.findViewById(R.id.txtProcedimientoMantenimiento);
                txtGarantia = view.findViewById(R.id.txtGarantiaMantenimiento);

            }

    }
}



