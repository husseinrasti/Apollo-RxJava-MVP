package ir.husseinrasti.app.apollorxjava.data;

import com.apollographql.apollo.ApolloClient;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.rx2.Rx2Apollo;

import io.reactivex.Completable;
import io.reactivex.Observable;
import ir.husseinrasti.app.apollorxjava.AllPostsQuery;
import ir.husseinrasti.app.apollorxjava.CreatePostMutation;
import ir.husseinrasti.app.apollorxjava.DeletePostMutation;
import ir.husseinrasti.app.apollorxjava.UpdatePostMutation;

public class RemotePostDataSource implements PostDataSource {

    private ApolloClient apolloClient;

    public RemotePostDataSource( ApolloClient apolloClient ) {
        this.apolloClient = apolloClient;
    }


    @Override
    public Observable<Response<AllPostsQuery.Data>> getAllPosts( AllPostsQuery query ) {
        return Rx2Apollo.from( apolloClient.query( query ) );
    }

    @Override
    public Completable createPost( CreatePostMutation mutation ) {
        return Rx2Apollo.from( apolloClient.prefetch( mutation ) );
    }

    @Override
    public Observable<Response<CreatePostMutation.Data>> createPostWithCallback( CreatePostMutation mutation ) {
        return Rx2Apollo.from( apolloClient.mutate( mutation ) );
    }

    @Override
    public Completable updatePost( UpdatePostMutation mutation ) {
        return Rx2Apollo.from( apolloClient.prefetch( mutation ) );
    }

    @Override
    public Completable deletePost( DeletePostMutation mutation ) {
        return Rx2Apollo.from( apolloClient.prefetch( mutation ) );
    }
}
