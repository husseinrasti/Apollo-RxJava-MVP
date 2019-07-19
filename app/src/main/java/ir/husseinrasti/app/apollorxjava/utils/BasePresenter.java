package ir.husseinrasti.app.apollorxjava.utils;

public interface BasePresenter<T> {

    public void takeView( T view );


    public void dropView();


    void rxUnSubscribe();
}
