package com.joseantonio.historialproducto;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;/*base de actividad en Android*/
import android.view.View; /*vistas Android*/
import androidx.navigation.NavController; /*permite controlar navegación entre fragmentos*/
import androidx.navigation.Navigation;/*permite controlar navegación entre fragmentos*/
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.joseantonio.historialproducto.databinding.ActivityMainBinding;
/*ActivityMainBinding se usa con View Binding, para acceder a los elementos del layout.*/
import android.view.Menu;
import android.view.MenuItem;

import org.json.JSONObject;/*permite trabajar con datos en formato JSON*/

public class MainActivity extends AppCompatActivity {/*clase principal que hereda de AppCompatActivity*/
    /*define la actividad de la pantalla principal*/
    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;
    /*binding permite acceder a las vistas del archivo XML (activity main.xml*/
    private JSONObject machineData = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot()); /*asigna el layout a la pantalla*/

        setSupportActionBar(binding.toolbar); /*convierte Toolbar en ActionBar*/

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
    }


    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();




    }
}