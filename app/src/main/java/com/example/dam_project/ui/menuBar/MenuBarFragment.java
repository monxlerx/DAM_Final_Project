package com.example.dam_project.ui.menuBar;

import androidx.cardview.widget.CardView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dam_project.R;
import com.example.dam_project.products.ProductsActivity;
import com.example.dam_project.ui.order.OrderViewModel;

public class MenuBarFragment extends Fragment implements View.OnClickListener {

    private MenuBarViewModel menuBarViewModel;
    CardView card1, card2, card3, card4, card5, card6;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        menuBarViewModel =
                ViewModelProviders.of(this).get(MenuBarViewModel.class);
        View root = inflater.inflate(R.layout.fragment_menu_bar, container, false);

        card1 = (CardView)root.findViewById(R.id.card1);
        card2 = (CardView)root.findViewById(R.id.card2);
        card3 = (CardView)root.findViewById(R.id.card3);
        card4 = (CardView)root.findViewById(R.id.card4);
        card5 = (CardView)root.findViewById(R.id.card5);
        card6 = (CardView)root.findViewById(R.id.card6);

        card1.setOnClickListener(this);
        card2.setOnClickListener(this);
        card3.setOnClickListener(this);
        card4.setOnClickListener(this);
        card5.setOnClickListener(this);
        card6.setOnClickListener(this);

        return root;
    }


    @Override
    public void onClick(View v) {

        Intent i;
        Bundle bundle = new Bundle();

        switch(v.getId()) {
            case R.id.card1:
                i = new Intent(getActivity(), ProductsActivity.class);
                bundle.putString("Category", "Ensaladas");
                i.putExtras(bundle);
                startActivity(i);
                break;
            case R.id.card2:
                i = new Intent(getActivity(), ProductsActivity.class);
                bundle.putString("Category", "Entrantes");
                i.putExtras(bundle);
                startActivity(i);
                break;
            case R.id.card3:
                i = new Intent(getActivity(), ProductsActivity.class);
                bundle.putString("Category", "Carnes");
                i.putExtras(bundle);
                startActivity(i);
                break;
            case R.id.card4:
                i = new Intent(getActivity(), ProductsActivity.class);
                bundle.putString("Category", "Pescado");
                i.putExtras(bundle);
                startActivity(i);
                break;
            case R.id.card5:
                i = new Intent(getActivity(), ProductsActivity.class);
                bundle.putString("Category", "Postres");
                i.putExtras(bundle);
                startActivity(i);
                break;
            case R.id.card6:
                i = new Intent(getActivity(), ProductsActivity.class);
                bundle.putString("Category", "Bebidas");
                i.putExtras(bundle);
                startActivity(i);
                break;
        }
    }


}
