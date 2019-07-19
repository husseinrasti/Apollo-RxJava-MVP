package ir.husseinrasti.app.apollorxjava.data;

import com.apollographql.apollo.api.Response;

import io.reactivex.Completable;
import io.reactivex.Observable;
import ir.husseinrasti.app.apollorxjava.AllPostsQuery;
import ir.husseinrasti.app.apollorxjava.CreatePostMutation;
import ir.husseinrasti.app.apollorxjava.DeletePostMutation;
import ir.husseinrasti.app.apollorxjava.UpdatePostMutation;

public interface PostDataSource {

    public Observable<Response<AllPostsQuery.Data>> getAllPosts( AllPostsQuery query );


    public Completable createPost( CreatePostMutation mutation );


    public Observable<Response<CreatePostMutation.Data>> createPostWithCallback( CreatePostMutation mutation );


    public Completable updatePost( UpdatePostMutation mutation );


    public Completable deletePost( DeletePostMutation mutation );
}
