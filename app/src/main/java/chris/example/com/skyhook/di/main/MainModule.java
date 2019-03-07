package chris.example.com.skyhook.di.main;

import javax.inject.Singleton;

import chris.example.com.skyhook.data.remote.RemoteDataSource;
import chris.example.com.skyhook.main.MainPresenter;
import dagger.Module;
import dagger.Provides;

/**
 * Created by chris on 2/28/2018.
 */
@Module
public class MainModule
{
    @Provides
    @Singleton
    MainPresenter providerMainPresenter(RemoteDataSource remoteDataSource)
    {
        return new MainPresenter(remoteDataSource);
    }
}
