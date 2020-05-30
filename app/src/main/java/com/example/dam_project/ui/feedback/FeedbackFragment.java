package com.example.dam_project.ui.feedback;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dam_project.MainActivity;
import com.example.dam_project.R;
import com.example.dam_project.email.JavaMailAPI;
import com.example.dam_project.email.Utils;
import com.example.dam_project.sessionmanagment.UserSessionManager;
import com.example.dam_project.ui.help.HelpViewModel;

import java.util.HashMap;

import static java.nio.file.Paths.get;

public class FeedbackFragment extends Fragment {

    private FeedbackViewModel feedbackViewModel;
    UserSessionManager session;
    private RadioGroup radioGroup;
    private RadioButton rbAwful, rbBad, rbNormal, rbGood, rbSuperb;
    private EditText etComment;
    private Button btnFeedback;
    private String email;
    public String rating;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        feedbackViewModel =
                ViewModelProviders.of(this).get(FeedbackViewModel.class);
        View root = inflater.inflate(R.layout.fragment_feedback, container, false);

        session = new UserSessionManager(getContext());
        HashMap<String, String> user = session.getUserDetails();
        final String name = user.get(UserSessionManager.KEY_NAME);

        radioGroup = root.findViewById(R.id.radioGroup);
        rbAwful = root.findViewById(R.id.rbAwful);
        rbBad = root.findViewById(R.id.rbUnderNormal);
        rbNormal = root.findViewById(R.id.rbNormal);
        rbGood = root.findViewById(R.id.rbGood);
        rbSuperb = root.findViewById(R.id.rbSuperb);

        etComment = root.findViewById(R.id.etComment);
        btnFeedback = root.findViewById(R.id.btnFeedback);

        btnFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(radioGroup.getCheckedRadioButtonId() == -1){
                    Toast.makeText(getActivity(), "Selecciona una calificación", Toast.LENGTH_SHORT).show();
                } else if (etComment.getText().toString().trim().length() == 0) {
                    Toast.makeText(getActivity(), "Introduce un comentario", Toast.LENGTH_SHORT).show();
                } else if (name == null) {
                    Toast.makeText(getActivity(), "Debes iniciar sesión", Toast.LENGTH_SHORT).show();
                } else {
                    sendMail();
                    etComment.setText("");
                    radioGroup.clearCheck();
                }
            }
        });


        return root;
    }

    public void sendMail() {

        HashMap<String, String> user = session.getUserDetails();
        String mail = user.get(UserSessionManager.KEY_EMAIL);
        String subject = "Valoración Cliente de Casa Juan";

        //Check the radio button that has been selected
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rbAwful:
                        rating = "Valoración: Horrible. ";
                        break;
                    case R.id.rbUnderNormal:
                        rating = "Valoración: Mala. ";
                        break;
                    case R.id.rbNormal:
                        rating = "Valoración: Normal. ";
                        break;
                    case R.id.rbGood:
                        rating = "Valoración: Buena. ";
                        break;
                    case R.id.rbSuperb:
                        rating = "Valoración: Excelente. ";
                        break;
                }
            }
        });

        //Add rating to message.
        String message = rating + " " + etComment.getText().toString();

        JavaMailAPI email = new JavaMailAPI(getContext(), mail, subject, message);
        email.execute();

    }
}
