package chris.example.com.skyhook.main;

import chris.example.com.skyhook.model.response.RGEOResponse;
import chris.example.com.skyhook.util.BasePresenter;
import chris.example.com.skyhook.util.BaseView;


public interface MainContract
{
    interface  View extends BaseView
    {
        void setAddress(RGEOResponse rgeoResponse);
        void showProgress(String message);
        void showError(String error);
    }
    
    interface Presenter extends BasePresenter<View>
    {
        void getAddress(String lat, String lon);
    }
}
