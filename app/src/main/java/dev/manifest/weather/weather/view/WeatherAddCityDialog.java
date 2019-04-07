package dev.manifest.weather.weather.view;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import dev.manifest.weather.R;

public class WeatherAddCityDialog extends DialogFragment {

    public static WeatherAddCityDialog newInstance() {
        return new WeatherAddCityDialog();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.add_city_dialog, null);
        EditText editText = view.findViewById(R.id.city_name_edit_text);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.add_city);
        builder.setNegativeButton(android.R.string.cancel, null);
        builder.setPositiveButton(android.R.string.ok, (dialogInterface, i) ->
                ((WeatherActivity) getActivity()).addCityByName(editText.getText().toString()));
        builder.setView(view);
        Dialog dialog = builder.create();

        return dialog;
    }
}
