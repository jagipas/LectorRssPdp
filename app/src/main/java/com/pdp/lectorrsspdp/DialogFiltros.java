package com.pdp.lectorrsspdp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;

import java.util.ArrayList;

public class DialogFiltros extends DialogFragment{

    ArrayList<String> list = new ArrayList<String>();
    DialogFiltrosListener filtrosListener;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final String[] items = getResources().getStringArray(R.array.filter_selection);

        // Checkeamos lo que toque;
        list = this.getArguments().getStringArrayList("categorias");
        final boolean[] checkedCategorias = new boolean[3];
        checkedCategorias[0] = list.contains("Noticias") ? true:false;
        checkedCategorias[1] = list.contains("Deportes") ? true:false;
        checkedCategorias[2] = list.contains("Tecnología") ? true:false;

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Filtros").setMultiChoiceItems(R.array.filter_selection, checkedCategorias, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {

                if(isChecked){
                    list.add(items[which]);
                }else if(list.contains(items[which])){
                    list.remove(items[which]);
                }

            }
        }).setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                filtrosListener.onSelectedOptions(list);
            }
        });
        return builder.create();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        filtrosListener = (DialogFiltrosListener) activity;
    }

    public void setList(ArrayList<String> list) {
        this.list = list;
    }
}
