package com.example.planejamentodeviagem;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView; // Importado para o texto da ansiedade
import android.widget.TimePicker; // Importado para o horário
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText editDestino;
    private DatePicker dpPartida, dpRetorno;
    private SeekBar seekBarAnsiedade;
    private TextView txtAnsiedade; // Novo: para mostrar a %
    private TimePicker tpVoo; // Novo: para o horário
    private NumberPicker numberPickerPessoas;
    private CheckBox checkPassagens;
    private Switch switchNotificacoes;
    private RadioGroup radioGroupTipo;
    private Button btnFinalizar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editDestino = findViewById(R.id.editDestino);
        dpPartida = findViewById(R.id.dpPartida);
        dpRetorno = findViewById(R.id.dpRetorno);
        seekBarAnsiedade = findViewById(R.id.seekBarAnsiedade);
        txtAnsiedade = findViewById(R.id.txtAnsiedade);
        tpVoo = findViewById(R.id.tpVoo);
        numberPickerPessoas = findViewById(R.id.numberPickerPessoas);
        checkPassagens = findViewById(R.id.checkPassagens);
        switchNotificacoes = findViewById(R.id.switchNotificacoes);
        radioGroupTipo = findViewById(R.id.radioGroupTipo);
        btnFinalizar = findViewById(R.id.btnFinalizar);

        numberPickerPessoas.setMinValue(1);
        numberPickerPessoas.setMaxValue(10);

        tpVoo.setIs24HourView(true);

        seekBarAnsiedade.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                txtAnsiedade.setText("Ansiedade: " + progress);
            }

            @Override public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        btnFinalizar.setOnClickListener(v -> {
            salvarDados();
        });
    }

    private void salvarDados() {
        String destino = editDestino.getText().toString();
        int ansiedade = seekBarAnsiedade.getProgress();
        int pessoas = numberPickerPessoas.getValue();
        boolean passagens = checkPassagens.isChecked();
        boolean notificacoes = switchNotificacoes.isChecked();

        int hora = tpVoo.getHour();
        int minuto = tpVoo.getMinute();
        String horarioVoo = hora + ":" + String.format("%02d", minuto);

        int idSelecionado = radioGroupTipo.getCheckedRadioButtonId();
        String tipoViagem = "Não definido";
        if (idSelecionado != -1) {
            RadioButton rb = findViewById(idSelecionado);
            tipoViagem = rb.getText().toString();
        }

        SharedPreferences pref = getSharedPreferences("MinhaViagem", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

        editor.putString("destino", destino);
        editor.putInt("ansiedade", ansiedade);
        editor.putInt("pessoas", pessoas);
        editor.putBoolean("passagens", passagens);
        editor.putBoolean("notificacoes", notificacoes);
        editor.putString("tipo", tipoViagem);
        editor.putString("horario", horarioVoo);

        editor.apply();

        Toast.makeText(this, "Planejamento para " + destino + " salvo!", Toast.LENGTH_SHORT).show();
    }
}