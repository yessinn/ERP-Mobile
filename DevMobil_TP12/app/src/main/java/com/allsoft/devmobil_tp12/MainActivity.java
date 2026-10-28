package com.allsoft.devmobil_tp12;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SeekBar sbAge = findViewById(R.id.sbAge);
        TextView tvAge = findViewById(R.id.tvAge);

        if (sbAge.getProgress() < 1) {
            sbAge.setProgress(1);
        }

        afficherAge(tvAge, sbAge.getProgress());

        sbAge.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {

                    @Override
                    public void onProgressChanged(
                            SeekBar seekBar,
                            int progress,
                            boolean fromUser) {

                        if (progress < 1) {
                            seekBar.setProgress(1);
                            return;
                        }

                        afficherAge(tvAge, progress);
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                    }
                }
        );
    }

    private void afficherAge(TextView tvAge, int age) {
        tvAge.setText("Votre âge : " + age + (age == 1 ? " an" : " ans"));
    }


    public void calculer(View view) {

        SeekBar sbAge = findViewById(R.id.sbAge);
        RadioGroup rgJeune = findViewById(R.id.rgJeune);
        EditText etGlycemie = findViewById(R.id.etGlycemie);
        TextView msg = findViewById(R.id.msg);

        int age = sbAge.getProgress();

        String saisie = etGlycemie.getText()
                .toString().trim().replace(',', '.');

        if (age < 1) {
            msg.setText("Sélectionnez un âge entre 1 et 120 ans");
            msg.setVisibility(View.VISIBLE);
            return;
        }

        if (saisie.isEmpty()) {
            etGlycemie.setError("Saisissez la glycémie");
            return;
        }

        double glycemie;

        try {
            glycemie = Double.parseDouble(saisie);
        } catch (NumberFormatException e) {
            etGlycemie.setError("Valeur invalide");
            return;
        }

        if (Double.isNaN(glycemie)
                || Double.isInfinite(glycemie)
                || glycemie <= 0) {
            etGlycemie.setError("Valeur invalide");
            return;
        }

        int selection = rgJeune.getCheckedRadioButtonId();

        if (selection == -1) {
            msg.setText("Choisissez Oui ou Non");
            msg.setVisibility(View.VISIBLE);
            return;
        }

        boolean jeune = selection == R.id.rbOui;
        String resultat;

        if (jeune) {

            double minimum;
            double maximum;

            if (age < 6) {
                minimum = 5.5;
                maximum = 10.0;
            } else if (age <= 12) {
                minimum = 5.0;
                maximum = 10.0;
            } else if (age == 13) {
                msg.setText(
                        "Seuil non précisé pour 13 ans dans le TP"
                );
                msg.setVisibility(View.VISIBLE);
                return;
            } else {
                minimum = 5.0;
                maximum = 7.2;
            }

            if (glycemie < minimum) {
                resultat = "Niveau de glycémie est trop bas";
            } else if (glycemie > maximum) {
                resultat = "Niveau de glycémie est trop élevé";
            } else {
                resultat = "Niveau de glycémie est normal";
            }

        } else {

            if (glycemie >= 10.5) {
                resultat = "Niveau de glycémie est trop élevé";
            } else {
                resultat = "Niveau de glycémie est normal selon le seuil du TP";
            }
        }

        msg.setText(resultat);
        msg.setVisibility(View.VISIBLE);
    }
}