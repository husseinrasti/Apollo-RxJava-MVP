package ir.husseinrasti.app.apollorxjava.view;

import com.apollographql.apollo.api.Response;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import ir.husseinrasti.app.apollorxjava.AllPostsQuery;
import ir.husseinrasti.app.apollorxjava.CreatePostMutation;
import ir.husseinrasti.app.apollorxjava.data.PostEntity;
import ir.husseinrasti.app.apollorxjava.utils.BasePresenter;
import ir.husseinrasti.app.apollorxjava.utils.BaseView;

public interface MVPBasePost {

    interface View extends BaseView<Presenter> {

        public void setAllPosts( List<PostEntity> allPost );


        public void createdPost( PostEntity postEntity );


        public void updatePost( PostEntity postEntity , int position );


        public void deletePost( int position );


        public void showError( String message );


        public void showComplete( String message );


        public void showProgress();


        public void hideProgress();
    }



    interface Presenter extends BasePresenter<View> {

        public void fetchAllPosts();


        public void createPost( PostEntity postEntity );


        public void createPostWithCallback( PostEntity postEntity );


        public void updatePost( PostEntity postEntity , String id , int position );


        public void deletePost( String id , int position );
    }



    interface Model {

        public Observable<Response<AllPostsQuery.Data>> fetchAllPosts();


        public Completable createPost( PostEntity postEntity );


        public Observable<Response<CreatePostMutation.Data>> createPostWithCallback( PostEntity postEntity );


        public Completable updatePost( String id , PostEntity postEntity );


        public Completable deletePost( String id );
    }

}
