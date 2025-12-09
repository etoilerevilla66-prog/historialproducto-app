package com.joseantonio.historialproducto;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class TabsAdapter extends FragmentStateAdapter {

    public TabsAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
            }
    @NonNull
    @Override
   public Fragment createFragment(int position){
        switch (position){
            case 0:
                return new DatosMaquinaFragment();
            case 1:
                return new MantenimientoFragment();
        }
        return null;
    }
    @Override
    public int getItemCount() {
        return 2;

    }
}