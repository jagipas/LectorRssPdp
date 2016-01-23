package com.pdp.lectorrsspdp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

/**
 * Created by Marc on 23/01/2016.
 */
public class DialogAdd extends DialogFragment implements AdapterView.OnItemSelectedListener {

    private DialogAddListener addListener;
    private ArrayAdapter<CharSequence> adapter;
    private Spinner spCategoria;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.dialog_add, null);
        final EditText etUrl = (EditText) v.findViewById(R.id.etUrl);

        spCategoria = (Spinner) v.findViewById(R.id.spCat);
        spCategoria.setOnItemSelectedListener(this);
        adapter = ArrayAdapter.createFromResource(((Activity)addListener).getApplicationContext(),R.array.filter_selection,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter.notifyDataSetChanged();
        spCategoria.setAdapter(adapter);
        spCategoria.setSelection(0,false);
        builder.setView(v)
                // Add action buttons
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        addListener.addRss(etUrl.getText().toString(),spCategoria.getSelectedItem().toString());
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        DialogAdd.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        addListener = (DialogAddListener) activity;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        adapter.notifyDataSetChanged();
        spCategoria.setSelection(position, false);
        Log.i("SPINNER", "Posición clicada: " + parent.getItemAtPosition(position) + " Posición selecionada" + spCategoria.getSelectedItem().toString());

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
