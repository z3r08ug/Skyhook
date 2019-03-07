package chris.example.com.skyhook.main;

import android.util.Log;

import javax.inject.Inject;

import chris.example.com.skyhook.data.remote.RemoteDataSource;
import chris.example.com.skyhook.model.request.Authentication;
import chris.example.com.skyhook.model.request.Key;
import chris.example.com.skyhook.model.request.Point;
import chris.example.com.skyhook.model.request.ReverseGeoRQ;
import chris.example.com.skyhook.model.response.RGEOResponse;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by chris on 2/28/2018.
 */

public class MainPresenter implements MainContract.Presenter
{
    RemoteDataSource remoteDataSource;
    MainContract.View view;
    public static final String TAG = MainPresenter.class.getSimpleName() + "_TAG";
    private RGEOResponse rgeoResponse;
    
    @Inject
    public MainPresenter(RemoteDataSource remoteDataSource)
    {
        this.remoteDataSource = remoteDataSource;
    }
    
    public MainPresenter()
    {
    
    }
    
    @Override
    public void attachView(MainContract.View view)
    {
        this.view = view;
    }
    
    @Override
    public void detachView()
    {
        this.view = null;
    }
    
    
    @Override
    public void getAddress(String lat, String lon)
    {
        ReverseGeoRQ reverseGeoRQ = new ReverseGeoRQ();
        Authentication authentication = new Authentication();
        Key key = new Key();
        Point point = new Point();
        
        authentication.setKey(key);
        point.setLat(lat);
        point.setLon(lon);
        
        reverseGeoRQ.setAuthentication(authentication);
        reverseGeoRQ.setPoint(point);
        
        RemoteDataSource.getAddress(reverseGeoRQ)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<RGEOResponse>()
                {
                    @Override
                    public void onSubscribe(Disposable d)
                    {
                        view.showProgress("Downloading address information...");
                    }
                    
                    @Override
                    public void onNext(RGEOResponse response)
                    {
                        rgeoResponse = response;
                    }
                    
                    @Override
                    public void onError(Throwable e)
                    {
                        view.showError(e.getMessage());
                    }
                    
                    @Override
                    public void onComplete()
                    {
                        view.setAddress(rgeoResponse);
                    }
                });
    }
}
