package pessoto.android.placeholder.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

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

    private RecyclerView recyclerView;
    private List<PlaceHolder> listPlaceHolder = new ArrayList<>();
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
            Adapter adapter = new Adapter(listPlaceHolder);
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
                    listPlaceHolder = response.body();

                    for (int i = 0; i < listPlaceHolder.size(); i++) {
                        PlaceHolder place = listPlaceHolder.get(i);
                        Log.i("API PLACE", "Title: " + place.getTitle());

                    }

                }
            }

            @Override
            public void onFailure(Call<List<PlaceHolder>> call, Throwable t) {

            }
        });
    }
}