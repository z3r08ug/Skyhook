package chris.example.com.skyhook.di.app;

import chris.example.com.skyhook.data.remote.RemoteDataSource;
import dagger.Module;
import dagger.Provides;

@Module
public class AppModule
{
    String baseURL;
    
    public AppModule(String baseURL)
    {
        this.baseURL = baseURL;
    }
    
    @Provides
    RemoteDataSource providesRemoteDataSource()
    {
        return new RemoteDataSource(baseURL);
    }
}
