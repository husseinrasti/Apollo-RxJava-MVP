package ir.husseinrasti.app.apollorxjava.root;

import ir.husseinrasti.app.apollorxjava.data.ApiClient;
import ir.husseinrasti.app.apollorxjava.data.PostDataSource;
import ir.husseinrasti.app.apollorxjava.data.RemotePostDataSource;
import ir.husseinrasti.app.apollorxjava.view.MVPBasePost;
import ir.husseinrasti.app.apollorxjava.view.ModelPost;
import ir.husseinrasti.app.apollorxjava.view.PresenterPost;

public class Injection {

    public static MVPBasePost.Presenter providePresenter() {
        return new PresenterPost( provideModel() );
    }

    private static MVPBasePost.Model provideModel() {
        return new ModelPost( provideDataSource() );
    }

    private static PostDataSource provideDataSource() {
        return new RemotePostDataSource( ApiClient.getClient() );
    }
}
