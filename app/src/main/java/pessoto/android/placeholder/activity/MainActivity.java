package pessoto.android.placeholder.activity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import pessoto.android.placeholder.R;
import pessoto.android.placeholder.adapter.Adapter;
import pessoto.android.placeholder.fragments.QuantidadeFragment;
import pessoto.android.placeholder.model.PlaceHolder;
import pessoto.android.placeholder.service.PlaceService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<PlaceHolder> listPlaceHolder = new ArrayList<>();
    private Retrofit retrofit;
    private ProgressBar progressBar;
    private FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inicializarComponentes();
        verificaConexao();
        definirQuantidadeDeItens();
    }

    private void inicializarComponentes() {
        recyclerView = findViewById(R.id.recyclerView);
        progressBar = findViewById(R.id.progressBar);
        floatingActionButton = findViewById(R.id.floatingAction);
    }

    private void delayParaCarregarRecycler() {
        new Handler().postDelayed(() -> {

            Intent intent = getIntent();
            int quantidade = intent.getIntExtra("quantidade", 10);

            Adapter adapter = new Adapter(listPlaceHolder, quantidade);
            configuraRecyclerView(adapter);
            progressBar.setVisibility(View.INVISIBLE);
        }, 2000);
    }

    private void configuraRecyclerView(Adapter adapter) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
    }

    private void retrofit() {
        retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    private void recuperarListaRetrofit() {
        PlaceService service = retrofit.create(PlaceService.class);
        Call<List<PlaceHolder>> requestPlace = service.listPlace();

        requestPlace.enqueue(new Callback<List<PlaceHolder>>() {
            @Override
            public void onResponse(Call<List<PlaceHolder>> call, Response<List<PlaceHolder>> response) {
                if (response.isSuccessful()) {
                    floatingActionButton.setVisibility(View.VISIBLE);
                    listPlaceHolder = response.body();
                }
            }

            @Override
            public void onFailure(Call<List<PlaceHolder>> call, Throwable t) {
                progressBar.setVisibility(View.VISIBLE);
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void verificaConexao() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo net = cm.getActiveNetworkInfo();

        if (net != null && net.isConnectedOrConnecting()) {

            retrofit();

            recuperarListaRetrofit();

            delayParaCarregarRecycler();

        } else {
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle("Sem conexão com a internet");
            alert.setIcon(R.mipmap.ic_launcher);
            alert.setMessage("Precisamos de conexão com a internet. Por favor, ative e tente novamente");
            alert.setCancelable(false);
            alert.setPositiveButton("TENTAR NOVAMENTE", (dialogInterface, i) -> verificaConexao());

            alert.setNegativeButton("FECHAR", (dialogInterface, i) -> {
                Toast.makeText(getApplicationContext(),
                        "Tente mais tarde",
                        Toast.LENGTH_LONG).show();
                finishAffinity();
            });

            alert.create();
            alert.show();
        }
    }

    public void definirQuantidadeDeItens() {
        floatingActionButton.setOnClickListener(v -> {
            QuantidadeFragment quantidadeFragment = new QuantidadeFragment();

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.frame, quantidadeFragment);
            transaction.addToBackStack(null).commit();

        });
    }

    @Override
    public void onBackPressed() {

    }
}