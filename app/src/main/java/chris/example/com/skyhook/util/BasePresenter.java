package chris.example.com.skyhook.util;

public interface BasePresenter <V extends BaseView>
{
    void attachView(V view);
    void detachView();
}
