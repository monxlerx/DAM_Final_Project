package com.example.dam_project.ui.menuBar;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dam_project.R;
import com.example.dam_project.ui.order.OrderViewModel;

public class MenuBarFragment extends Fragment {

    private MenuBarViewModel menuBarViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        menuBarViewModel =
                ViewModelProviders.of(this).get(MenuBarViewModel.class);
        View root = inflater.inflate(R.layout.fragment_menu_bar, container, false);

        return root;
    }

}
