package com.example.dam_project.ui.help;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.dam_project.MainActivity;
import com.example.dam_project.R;

public class HelpFragment extends Fragment {

    private HelpViewModel helpViewModel;
    ListView lv;
    ArrayAdapter<String> adapter;
    String[] names = {"Preguntas frecuentes", "Compartir", "Términos y condiciones", "Configuración"};

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        helpViewModel =
                ViewModelProviders.of(this).get(HelpViewModel.class);
        View root = inflater.inflate(R.layout.fragment_help, container, false);

        lv = (ListView) root.findViewById(R.id.help_list);
        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1);
        lv.setAdapter(adapter);

        //Select different activities in the listview
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View v, int pos, long id) {
                Intent i;
                switch (pos) {
                    case 0:
                        i = new Intent(getContext(), MainActivity.class);
                        startActivity(i);
                        break;

                    case 1:
                        i = new Intent(getActivity(), ShareApplicationActivity.class);
                        startActivity(i);
                        break;

                    case 2:
                        i = new Intent(getActivity(), TermsConditionsActivity.class);
                        startActivity(i);
                        break;

                    case 3:
                        i = new Intent(getActivity(), SettingsActivity.class);
                        startActivity(i);
                        break;
                }
            }
        });

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, names);
        lv.setAdapter(adapter);
    }

}
