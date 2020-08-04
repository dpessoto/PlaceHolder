package pessoto.android.placeholder.service;

import pessoto.android.placeholder.model.PlaceHolder;
import retrofit2.Call;
import retrofit2.http.GET;

public interface PlaceService {

    @GET("photos")
    Call<PlaceHolder> listPlace();
}
