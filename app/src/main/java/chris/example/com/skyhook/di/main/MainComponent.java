package chris.example.com.skyhook.di.main;


import javax.inject.Singleton;

import chris.example.com.skyhook.main.MainActivity;
import dagger.Subcomponent;

/**
 * Created by chris on 2/28/2018.
 */
@Subcomponent(modules = MainModule.class)
@Singleton
public interface MainComponent
{
    void inject(MainActivity mainActivity);
}
