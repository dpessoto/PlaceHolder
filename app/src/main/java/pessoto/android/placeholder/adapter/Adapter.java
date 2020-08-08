package pessoto.android.placeholder.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import pessoto.android.placeholder.R;
import pessoto.android.placeholder.model.PlaceHolder;

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {

    private List<PlaceHolder> listaPlace;
    private int quantidadeItens;

    public Adapter(List<PlaceHolder> lista, int quantidade) {
        listaPlace = lista;
        quantidadeItens = quantidade;
    }

    @NonNull
    @Override
    public Adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemLista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_lista, parent, false);

        return new MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        PlaceHolder placeHolder = listaPlace.get(position);

        holder.titlePlace.setText(placeHolder.getTitle());
        Picasso.get()
                .load(placeHolder.getUrl())
                .into(holder.imagePlace);
    }

    @Override
    public int getItemCount() {
        return quantidadeItens;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView titlePlace;
        ImageView imagePlace;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            titlePlace = itemView.findViewById(R.id.textTitle);
            imagePlace = itemView.findViewById(R.id.imagePlace);
        }
    }
}