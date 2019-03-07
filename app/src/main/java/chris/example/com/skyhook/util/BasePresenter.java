package chris.example.com.skyhook.util;

/**
 * Created by chris on 2/28/2018.
 */

public interface BasePresenter <V extends BaseView>
{
    void attachView(V view);
    void detachView();
}
