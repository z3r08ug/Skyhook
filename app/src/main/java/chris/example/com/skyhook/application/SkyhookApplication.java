package chris.example.com.skyhook.application;

import android.app.Application;
import android.content.Context;

import chris.example.com.skyhook.di.app.AppComponent;
import chris.example.com.skyhook.di.app.AppModule;
import chris.example.com.skyhook.di.app.DaggerAppComponent;
import chris.example.com.skyhook.di.main.MainComponent;
import chris.example.com.skyhook.di.main.MainModule;
import chris.example.com.skyhook.util.Constants;


public class SkyhookApplication extends Application
{
    private AppComponent appComponent;
    private MainComponent mainComponent;
    
    @Override
    public void onCreate()
    {
        super.onCreate();
    
        AppModule appModule = new AppModule(Constants.BASE_URL);
        
        appComponent = DaggerAppComponent.builder()
                .appModule(appModule)
                .build();
    
    }
    
    public static SkyhookApplication get(Context context)
    {
        return (SkyhookApplication) context.getApplicationContext();
    }

    public MainComponent getMainComponent()
    {
        mainComponent = appComponent.add(new MainModule());
        return mainComponent;
    }

    public void clearMainComponent()
    {
        mainComponent = null;
    }

}
