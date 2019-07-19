package ir.husseinrasti.app.apollorxjava.view;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import ir.husseinrasti.app.apollorxjava.AllPostsQuery;
import ir.husseinrasti.app.apollorxjava.CreatePostMutation;
import ir.husseinrasti.app.apollorxjava.data.PostEntity;

public class PresenterPost implements MVPBasePost.Presenter {

    private MVPBasePost.Model model;
    private MVPBasePost.View view;

    private CompositeDisposable disposable = new CompositeDisposable();

    public PresenterPost( MVPBasePost.Model model ) {
        this.model = model;
    }

    @Override
    public void fetchAllPosts() {
        if ( view != null ) {
            view.showProgress();
        }
        disposable.add(
                model.fetchAllPosts()
                        .subscribeOn( Schedulers.io() )
                        .observeOn( AndroidSchedulers.mainThread() )
                        .map( dataResponse -> {
                            assert dataResponse.data() != null;
                            List<AllPostsQuery.AllPost> allPosts = dataResponse.data().allPosts();
                            List<PostEntity> listPosts = new ArrayList<>();
                            for ( AllPostsQuery.AllPost post : allPosts ) {
                                PostEntity postEntity = new PostEntity();
                                postEntity.setType( post.__typename() );
                                postEntity.setId( post.id() );
                                postEntity.setTitle( post.title() );
                                postEntity.setDesc( post.description() );
                                postEntity.setImagUrl( post.imageUrl() );
                                listPosts.add( postEntity );
                            }
                            return listPosts;
                        } )
                        .subscribeWith( new DisposableObserver<List<PostEntity>>() {

                            @Override
                            public void onNext( List<PostEntity> allPosts ) {
                                if ( allPosts != null ) {
                                    if ( view != null ) {
                                        view.setAllPosts( allPosts );
                                    } else {
                                        onError( new Throwable( "View is null" ) );
                                    }
                                } else {
                                    onError( new Throwable( "Response is null" ) );
                                }
                            }

                            @Override
                            public void onError( Throwable e ) {
                                if ( view != null ) {
                                    view.showError( e.getMessage() );
                                }
                                e.printStackTrace();
                            }

                            @Override
                            public void onComplete() {
                                if ( view != null ) {
                                    view.hideProgress();
                                    view.showComplete( "All Post Receive" );
                                }
                            }
                        } )
        );
    }

    @Override
    public void createPost( PostEntity postEntity ) {
        if ( view != null ) {
            view.showProgress();
        }
        disposable.add(
                model.createPost( postEntity )
                        .subscribeOn( Schedulers.io() )
                        .observeOn( AndroidSchedulers.mainThread() )
                        .subscribeWith( new DisposableCompletableObserver() {

                            @Override
                            public void onComplete() {
                                if ( view != null ) {
                                    view.hideProgress();
                                    view.showComplete( "Post is created" );
                                    fetchAllPosts();
                                } else {
                                    onError( new Throwable( "View is null" ) );
                                }
                            }

                            @Override
                            public void onError( Throwable e ) {
                                if ( view != null ) {
                                    view.showError( e.getMessage() );
                                }
                                e.printStackTrace();
                            }
                        } )
        );
    }

    @Override
    public void createPostWithCallback( PostEntity postEntity ) {
        if ( view != null ) {
            view.showProgress();
        }
        disposable.add(
                model.createPostWithCallback( postEntity )
                        .subscribeOn( Schedulers.io() )
                        .observeOn( AndroidSchedulers.mainThread() )
                        .map( dataResponse -> {
                            assert dataResponse.data() != null;
                            CreatePostMutation.CreatePost post = dataResponse.data().createPost();
                            PostEntity postCreated = new PostEntity();
                            assert post != null;
                            postCreated.setId( post.id() );
                            postCreated.setType( post.__typename() );
                            postCreated.setTitle( post.title() );
                            postCreated.setDesc( post.description() );
                            postCreated.setImagUrl( post.imageUrl() );
                            return postCreated;
                        } )
                        .subscribeWith( new DisposableObserver<PostEntity>() {


                            @Override
                            public void onNext( PostEntity createPost ) {
                                if ( createPost != null ) {
                                    if ( view != null ) {
                                        view.createdPost( createPost );
                                    } else {
                                        onError( new Throwable( "View is null" ) );
                                    }
                                } else {
                                    onError( new Throwable( "Response is null" ) );
                                }
                            }

                            @Override
                            public void onError( Throwable e ) {
                                if ( view != null ) {
                                    view.showError( e.getMessage() );
                                }
                                e.printStackTrace();
                            }

                            @Override
                            public void onComplete() {
                                if ( view != null ) {
                                    view.hideProgress();
                                    view.showComplete( "All Post Receive" );
                                }
                            }
                        } )
        );
    }

    @Override
    public void updatePost( PostEntity postEntity , String id , int position ) {
        if ( view != null ) {
            view.showProgress();
        }
        disposable.add(
                model.updatePost( id , postEntity )
                        .subscribeOn( Schedulers.io() )
                        .observeOn( AndroidSchedulers.mainThread() )
                        .subscribeWith( new DisposableCompletableObserver() {

                            @Override
                            public void onComplete() {
                                if ( view != null ) {
                                    view.hideProgress();
                                    view.updatePost( postEntity , position );
                                }
                            }

                            @Override
                            public void onError( Throwable e ) {
                                if ( view != null ) {
                                    view.showError( e.getMessage() );
                                }
                                e.printStackTrace();
                            }
                        } )
        );
    }

    @Override
    public void deletePost( String id , int position ) {
        if ( view != null ) {
            view.showProgress();
        }
        disposable.add(
                model.deletePost( id )
                        .subscribeOn( Schedulers.io() )
                        .observeOn( AndroidSchedulers.mainThread() )
                        .subscribeWith( new DisposableCompletableObserver() {

                            @Override
                            public void onComplete() {
                                if ( view != null ) {
                                    view.hideProgress();
                                    view.deletePost( position );
                                }
                            }

                            @Override
                            public void onError( Throwable e ) {
                                if ( view != null ) {
                                    view.showError( e.getMessage() );
                                }
                                e.printStackTrace();
                            }
                        } )
        );
    }

    @Override
    public void takeView( MVPBasePost.View view ) {
        this.view = view;
    }

    @Override
    public void dropView() {
        this.view = null;
    }

    @Override
    public void rxUnSubscribe() {
        disposable.clear();
    }
}
