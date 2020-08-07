package pessoto.android.placeholder.service;

import java.util.List;

import pessoto.android.placeholder.model.PlaceHolder;
import retrofit2.Call;
import retrofit2.http.GET;

public interface PlaceService {

    @GET("/photos")
    Call<List<PlaceHolder>> listPlace();
}
