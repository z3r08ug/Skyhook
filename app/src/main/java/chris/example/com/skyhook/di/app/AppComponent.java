package chris.example.com.skyhook.di.app;

import chris.example.com.skyhook.di.main.MainComponent;
import chris.example.com.skyhook.di.main.MainModule;
import dagger.Component;

@Component(modules = AppModule.class)
public interface AppComponent
{
    MainComponent add(MainModule mainModule);
}
