package com.example.dam_project.ui.feedback;

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
import com.example.dam_project.ui.help.HelpViewModel;

public class FeedbackFragment extends Fragment {

    private FeedbackViewModel feedbackViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        feedbackViewModel =
                ViewModelProviders.of(this).get(FeedbackViewModel.class);
        View root = inflater.inflate(R.layout.fragment_feedback, container, false);
        final TextView textView = root.findViewById(R.id.text_feedback);
        feedbackViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;

    }

}
