package pessoto.android.placeholder.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import pessoto.android.placeholder.R;
import pessoto.android.placeholder.activity.MainActivity;

public class QuantidadeFragment extends Fragment {

    private EditText quantidade;
    private ImageButton enviar;
    private ImageButton fechar;
    private int quantidadeInt;

    public QuantidadeFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_quantidade, container, false);

        inicializarComponentes(view);

        definirQuantidadeItens();

        fecharFragment();

        return view;
    }

    private void inicializarComponentes(View view) {
        quantidade = view.findViewById(R.id.editTextQuantidade);
        enviar = view.findViewById(R.id.btnEnviar);
        fechar = view.findViewById(R.id.btnClose);
    }

    private void fecharFragment() {
        fechar.setOnClickListener(v -> {
            getActivity().getSupportFragmentManager().popBackStack();
        });
    }

    private void definirQuantidadeItens() {
        enviar.setOnClickListener(v -> {

            if (quantidade.getText().length() == 0) {
                getActivity().getSupportFragmentManager().popBackStack();
            } else {
                quantidadeInt = Integer.parseInt(quantidade.getText().toString());

                if (quantidadeInt > 5000 || quantidadeInt < 1)
                    Toast.makeText(getActivity(), "A quantidade de itens tem que ser entre 1 e 5000!!!", Toast.LENGTH_LONG).show();

                else {

                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    intent.putExtra("quantidade", quantidadeInt);

                    startActivity(intent);
                }
            }

        });
    }

}