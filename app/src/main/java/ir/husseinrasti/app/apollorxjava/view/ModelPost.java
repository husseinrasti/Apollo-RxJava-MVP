package ir.husseinrasti.app.apollorxjava.view;

import com.apollographql.apollo.api.Response;

import io.reactivex.Completable;
import io.reactivex.Observable;
import ir.husseinrasti.app.apollorxjava.AllPostsQuery;
import ir.husseinrasti.app.apollorxjava.CreatePostMutation;
import ir.husseinrasti.app.apollorxjava.DeletePostMutation;
import ir.husseinrasti.app.apollorxjava.UpdatePostMutation;
import ir.husseinrasti.app.apollorxjava.data.PostDataSource;
import ir.husseinrasti.app.apollorxjava.data.PostEntity;

public class ModelPost implements MVPBasePost.Model {

    private PostDataSource dataSource;

    public ModelPost( PostDataSource dataSource ) {
        this.dataSource = dataSource;
    }

    @Override
    public Observable<Response<AllPostsQuery.Data>> fetchAllPosts() {
        return dataSource.getAllPosts( AllPostsQuery.builder().build() );
    }

    @Override
    public Completable createPost( PostEntity postEntity ) {
        return dataSource.createPost( CreatePostMutation.builder()
                .title( postEntity.getTitle() )
                .description( postEntity.getDesc() )
                .imageUrl( postEntity.getImagUrl() )
                .build()
        );
    }

    @Override
    public Observable<Response<CreatePostMutation.Data>> createPostWithCallback( PostEntity postEntity ) {
        return dataSource.createPostWithCallback( CreatePostMutation.builder()
                .title( postEntity.getTitle() )
                .description( postEntity.getDesc() )
                .imageUrl( postEntity.getImagUrl() )
                .build()
        );
    }

    @Override
    public Completable updatePost( String id , PostEntity postEntity ) {
        return dataSource.updatePost( UpdatePostMutation.builder()
                .id( id )
                .title( postEntity.getTitle() )
                .description( postEntity.getDesc() )
                .imageUrl( postEntity.getImagUrl() )
                .build()
        );
    }

    @Override
    public Completable deletePost( String id ) {
        return dataSource.deletePost( DeletePostMutation.builder().id( id ).build() );
    }
}
