package com.joseantonio.historialproducto;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.OptIn;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ExperimentalGetImage;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageProxy;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.mlkit.vision.barcode.BarcodeScanner;
import com.google.mlkit.vision.barcode.BarcodeScanning;
import com.google.mlkit.vision.barcode.common.Barcode;
import com.google.mlkit.vision.common.InputImage;
import com.joseantonio.historialproducto.databinding.FragmentLeerQrBinding;

import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

public class LeerQrFragment extends Fragment {

    private FragmentLeerQrBinding binding;
    private static final int PERMISSION_REQUEST_CAMERA = 1;
    private Context context;
    private BarcodeScanner barcodeScanner;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        this.context = requireContext();
        binding = FragmentLeerQrBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    @OptIn(markerClass = ExperimentalGetImage.class)
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        binding.buttonSecond.setOnClickListener(v ->
//                NavHostFragment.findNavController(SecondFragment.this)
//                        .navigate(R.id.action_SecondFragment_to_FirstFragment)
//        );

        // Pedir permiso de cámara
        if (ContextCompat.checkSelfPermission(this.context, android.Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED) {
            this.iniciarCamara();
        } else {
            ActivityCompat.requestPermissions(requireActivity(),
                    new String[]{Manifest.permission.CAMERA},
                    PERMISSION_REQUEST_CAMERA);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @androidx.camera.core.ExperimentalGetImage
    private void iniciarCamara() {
        ListenableFuture<ProcessCameraProvider> cameraProviderFuture =
                ProcessCameraProvider.getInstance(this.context);

        cameraProviderFuture.addListener(() -> {
            try {
                ProcessCameraProvider cameraProvider = cameraProviderFuture.get();

                Preview preview = new Preview.Builder().build();
                preview.setSurfaceProvider(binding.previewView.getSurfaceProvider());

                ImageAnalysis analysis = new ImageAnalysis.Builder()
                        .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                        .build();

                BarcodeScanner scanner = BarcodeScanning.getClient();
                this.barcodeScanner = scanner;

                analysis.setAnalyzer(ContextCompat.getMainExecutor(this.context), imageProxy -> {
                    ImageProxy.PlaneProxy[] planes = imageProxy.getPlanes();

                    if (imageProxy.getImage() != null) {
                        InputImage image = InputImage.fromMediaImage(
                                imageProxy.getImage(),
                                imageProxy.getImageInfo().getRotationDegrees());

                        scanner.process(image)
                                .addOnSuccessListener(barcodes -> procesarCodigos(barcodes))
                                .addOnCompleteListener(task -> imageProxy.close());
                    }
                });

                CameraSelector cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA;
                cameraProvider.unbindAll();
                cameraProvider.bindToLifecycle(this, cameraSelector, preview, analysis);

            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
        }, ContextCompat.getMainExecutor(this.context));
    }

    private void obtenerDatosMaquina(String nSerie) {
        var _this = this;
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL("https://customer.revimachinery.com/query.php?q=machine&serial=" + nSerie);

                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
                    conn.connect();

                    int responsecode = conn.getResponseCode();

                    if (responsecode != 200) {
                        throw new RuntimeException("HttpResponseCode: " + responsecode);
                    } else {

                        String inline = "";
                        Scanner scanner = new Scanner(url.openStream());

                        while (scanner.hasNext()) {
                            inline += scanner.nextLine();
                        }

                        scanner.close();

                        if (inline != null) {
                            JSONObject machineObj = new JSONObject(inline); //EL OBJETO A PASAR

                            new Handler(Looper.getMainLooper()).post(new Runnable() {
                                @Override
                                public void run() {
                                    _this.binding.textQrDisplay.setText("ACCEDIENDO: " + nSerie);
                                    SharedViewModel viewModel =
                                            new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
                                    viewModel.setJson(machineObj);
                                    // PASAR EL OBJETO MACHINEOBJ A LOS FRAGMENTOS TAB1FRAGMENT Y TAB2FRAGMENT

                                    NavHostFragment.findNavController(LeerQrFragment.this)
                                            .navigate(R.id.action_SecondFragment_to_MachineFragment);
                                }
                            });

                        } else {
                            new Handler(Looper.getMainLooper()).post(new Runnable() {
                                @Override
                                public void run() {
                                    _this.binding.textQrDisplay.setText("Número de serie no encontrado");
                                }
                            });
                        }
                    }
                } catch (Exception e) {
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            _this.binding.textQrDisplay.setText("Número de serie no encontrado");
                        }
                    });
                }
            }
        }).start();
    }

    private void procesarCodigos(List<Barcode> barcodes) {
        for (Barcode barcode : barcodes) {
            String valor = barcode.getDisplayValue();


            this.obtenerDatosMaquina(valor);

            barcodeScanner.close();
            return;
        }
    }

    // Resultado del permiso
    @Override
    @androidx.camera.core.ExperimentalGetImage
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CAMERA
                && grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            iniciarCamara();
        } else {
            this.binding.textQrDisplay.setText("No hay permisos requeridos.");
        }
    }

}