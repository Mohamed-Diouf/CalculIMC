package com.example.calculimc;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button envoyer = null;
    Button reset = null;
    EditText taille = null;
    EditText poids = null;
    CheckBox commentaire = null;
    RadioGroup group = null;
    TextView result = null;

    private final String texteInit = "Cliquez sur le bouton « Calculer l'IMC » pour obtenir un résultat.";





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        envoyer = (Button)findViewById(R.id.calcul);
        envoyer.setOnClickListener(envoyerListener);
        reset = (Button)findViewById(R.id.reset);
        reset.setOnClickListener(resetListener);
        taille = (EditText)findViewById(R.id.taille);
        taille.addTextChangedListener(textWatcher);
        poids = (EditText)findViewById(R.id.poids);
        poids.addTextChangedListener(textWatcher);
        commentaire = (CheckBox)findViewById(R.id.commentaire);
        commentaire.setOnClickListener(checkedListener);
        group = (RadioGroup)findViewById(R.id.group);
        result = (TextView)findViewById(R.id.result);



    }
    private View.OnClickListener envoyerListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            //  on récupère la taille
            String t = taille.getText().toString();
           if(t.contains(".")== true){
               group. check(R.id.radio_metre);
            }
           else{
               group. check(R.id.radio_centimetre);
           }
            // On récupère le poids
            String p = poids.getText().toString();
            float tValue = Float.valueOf(t);

            // Puis on vérifie que la taille est cohérente
            if(tValue <= 0)
                Toast.makeText(MainActivity.this, "La taille doit être positive", Toast.LENGTH_SHORT).show();
            else {
                float pValue = Float.valueOf(p);
                if(pValue <= 0)
                    Toast.makeText(MainActivity.this, "Le poids doit etre positif", Toast.LENGTH_SHORT).show();
                else {
                    // Si l'utilisateur a indiqué que la taille était en centimètres
                    // On vérifie que la Checkbox sélectionnée est la deuxième à l'aide de son identifiant
                    if (group.getCheckedRadioButtonId() == R.id.radio_centimetre) tValue = tValue / 100;
                    float imc = pValue / (tValue * tValue);
                    String resultat="Votre IMC est " + imc+" . ";

                    if(commentaire.isChecked()) resultat += interpreteIMC(imc);



                    result.setText(resultat);
                }
            }
        }
    };

    public String interpreteIMC(float imc){
        String appreciation = "";
        if (imc <= 16.5 ){
        appreciation = "famine";
        }
        else if ( imc > 16.5 && imc <= 18.5){
            appreciation = "maigreur";
        }
        else if (imc > 18.5 && imc <= 25){
            appreciation = "corpulence normale";
        }
        else if ( imc > 25 && imc <= 30){
            appreciation = "surpoids";
        }
        else if (imc > 30 && imc <= 35){
            appreciation = "obésité modéré";
        }
        else if (imc > 35  && imc <= 40){
            appreciation = "obésité sevére";
        }
        else if (imc > 40){
            appreciation = "obésité morbide ou massive";
        }

        return appreciation;
    };

    private View.OnClickListener resetListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            poids.getText().clear();
            taille.getText().clear();
            result.setText(texteInit);
        }
    };



    private View.OnClickListener checkedListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            if(((CheckBox)v).isChecked()) {
                result.setText(texteInit);
            }
        }
    };



    private TextWatcher textWatcher = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            result.setText(texteInit);
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void afterTextChanged(Editable s) {}
    };



}