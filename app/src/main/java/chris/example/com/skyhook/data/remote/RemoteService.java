package chris.example.com.skyhook.data.remote;

import chris.example.com.skyhook.model.request.ReverseGeoRQ;
import chris.example.com.skyhook.model.response.RGEOResponse;
import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by chris on 2/28/2018.
 */

public interface RemoteService
{
    @Headers({"Content-Type: text/xml",
            "Accept-Charset: utf-8"
    })
    @POST("wps2/reverse-geo")
    Observable<RGEOResponse> getAddress(@Body ReverseGeoRQ body);
}
