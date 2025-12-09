package com.joseantonio.historialproducto;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.json.JSONObject;

public class SharedViewModel extends ViewModel{

    private final MutableLiveData<JSONObject> jsonData = new MutableLiveData<>();

    public void setJson(JSONObject json) {
        jsonData.setValue(json);
    }
    public LiveData<JSONObject> getJson(){
        return jsonData;
    }
}
