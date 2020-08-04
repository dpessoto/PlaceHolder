package pessoto.android.placeholder.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import pessoto.android.placeholder.R;
import pessoto.android.placeholder.adapter.Adapter;
import pessoto.android.placeholder.model.PlaceHolder;
import pessoto.android.placeholder.service.PlaceService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private  RecyclerView recyclerView;
    private List<PlaceHolder> listPlace = new ArrayList<>();
    private Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inicializarComponentes();
        retrofit();
        recuperarListaRetrofit();
        delayParaCarregarRecycler();

    }

    private void inicializarComponentes() {
        recyclerView = findViewById(R.id.recyclerView);
    }

    private void delayParaCarregarRecycler() {
        new Handler().postDelayed(() -> {
            Adapter adapter = new Adapter(listPlace);
            configuraRecyclerView(adapter);

        }, 2900);
    }

    private void configuraRecyclerView(Adapter adapter) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
    }

    private void retrofit() {
        retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    private void recuperarListaRetrofit() {

        PlaceService placeService  = retrofit.create(PlaceService.class);
        Call<PlaceHolder> requestPlace = placeService.listPlace();

        requestPlace.enqueue(new Callback<PlaceHolder>() {
            @Override
            public void onResponse(Call<PlaceHolder> call, Response<PlaceHolder> response) {
                if (response.isSuccessful()){
                    PlaceHolder placeLista = response.body();

                   // for (int i = 0; i < 10; i++){
                        String titlePlace = placeLista.getTitle();
                        String imagePlace = placeLista.getUrl();
                            Log.i("TESTE", titlePlace);

                        PlaceHolder placeHolder = new PlaceHolder(titlePlace, imagePlace);
                        //PlaceHolder placeHolder = new PlaceHolder(titlePlace);
                        listPlace.add(placeHolder);
                   // }

                }
            }

            @Override
            public void onFailure(Call<PlaceHolder> call, Throwable t) {

            }
        });

    }
}