package chris.example.com.skyhook.data.remote;

import android.os.Environment;

import java.io.File;
import java.util.List;

import chris.example.com.skyhook.model.request.ReverseGeoRQ;
import chris.example.com.skyhook.model.response.RGEOResponse;
import io.reactivex.Observable;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

/**
 * Created by chris on 2/28/2018.
 */

public class RemoteDataSource
{
    private static String baseURL;
    
    public RemoteDataSource(String baseURL)
    {
        this.baseURL = baseURL;
    }
    
    private static OkHttpClient httpClientConfig(HttpLoggingInterceptor interceptor)
    {
        return new OkHttpClient.Builder().addInterceptor(interceptor).build();
        
    }
    
    private static HttpLoggingInterceptor loggingInterceptor()
    {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return  httpLoggingInterceptor;
    }
    
    public static Retrofit create()
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseURL)
                .client(httpClientConfig(loggingInterceptor()))
                //add converter to parse the response
                .addConverterFactory(SimpleXmlConverterFactory.create())
                //add call adapter to convert the response to RxJava observable
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        
        return retrofit;
    }
    
    public static Observable<RGEOResponse> getAddress(ReverseGeoRQ reverseGeoRQ)
    {
        Retrofit retrofit = create();
        RemoteService remoteService = retrofit.create(RemoteService.class);
        return remoteService.getAddress(reverseGeoRQ);
    }
}