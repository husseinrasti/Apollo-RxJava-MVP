package ir.husseinrasti.app.apollorxjava.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import ir.husseinrasti.app.apollorxjava.R;
import ir.husseinrasti.app.apollorxjava.data.PostEntity;
import ir.husseinrasti.app.apollorxjava.root.Injection;
import ir.husseinrasti.app.apollorxjava.view.helper.MyDividerItemDecoration;
import ir.husseinrasti.app.apollorxjava.view.helper.RecyclerTouchListener;

public class MainActivity extends AppCompatActivity implements MVPBasePost.View {

    private static final String TAG = MainActivity.class.getSimpleName();

    private List<PostEntity> list = new ArrayList<>();
    private AdapterPosts adapterPosts;
    private Unbinder unbinder;

    @BindView(R.id.list)
    RecyclerView recyclerView;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.fabAdd)
    FloatingActionButton fabAdd;

    private MVPBasePost.Presenter presenter;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        unbinder = ButterKnife.bind( this );

        presenter = Injection.providePresenter();

        adapterPosts = new AdapterPosts( this , list );
        recyclerView.setLayoutManager( new LinearLayoutManager( this ) );
        recyclerView.setItemAnimator( new DefaultItemAnimator() );
        recyclerView.addItemDecoration( new MyDividerItemDecoration( this , LinearLayoutManager.VERTICAL , 16 ) );
        recyclerView.setAdapter( adapterPosts );
        recyclerView.addOnItemTouchListener( new RecyclerTouchListener( this , recyclerView ,
                new RecyclerTouchListener.ClickListener() {

                    @Override
                    public void onClick( View view , int position ) {

                    }

                    @Override
                    public void onLongClick( View view , int position ) {
                        showActionsDialog( position );
                    }
                } ) );

        fabAdd.setOnClickListener( v -> showDialogPost( false , null , -1 ) );

        presenter.fetchAllPosts();
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.takeView( this );
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        presenter.rxUnSubscribe();
        presenter.dropView();
    }

    private void showDialogPost( final boolean shouldUpdate , final PostEntity post , final int position ) {
        AlertDialog.Builder builder = new AlertDialog.Builder( this );
        View view = LayoutInflater.from( this ).inflate( R.layout.dialog_create_post , null );
        TextView title = view.findViewById( R.id.txtTitle );
        EditText edtTitle = view.findViewById( R.id.edtTitle );
        EditText edtDesc = view.findViewById( R.id.edtDesc );
        EditText edtImageUrl = view.findViewById( R.id.edtImage );
        title.setText( shouldUpdate ? "Edit Post" : "Create Post" );
        if ( shouldUpdate ) {
            edtTitle.setText( post.getTitle() );
            edtDesc.setText( post.getDesc() );
            edtImageUrl.setText( post.getImagUrl() );
        }
        builder.setView( view )
                .setPositiveButton( shouldUpdate ? "Update" : "Create" , ( dialog , which ) -> {
                    PostEntity postEntity = new PostEntity();
                    postEntity.setTitle( edtTitle.getText().toString() );
                    postEntity.setDesc( edtDesc.getText().toString() );
                    postEntity.setImagUrl( edtImageUrl.getText().toString() );
                    if ( shouldUpdate ) {
                        presenter.updatePost( postEntity , post.getId() , position );
                    } else {
                        presenter.createPostWithCallback( postEntity );
                    }
                } )
                .setNegativeButton( "Cancel" , ( dialog , which ) -> dialog.dismiss() ).show();
    }

    /**
     * Opens dialog with Edit - Delete options
     * Edit - 0
     * Delete - 1
     */
    private void showActionsDialog( int position ) {
        CharSequence[] options = new CharSequence[] { "Edit" , "Delete" };

        AlertDialog.Builder builder = new AlertDialog.Builder( this );
        builder.setTitle( "Choose option" );
        builder.setItems( options , ( dialog , which ) -> {
            if ( which == 0 ) {
                showDialogPost( true , list.get( position ) , position );
            } else {
                presenter.deletePost( list.get( position ).getId() , position );
            }
        } );
        builder.show();
    }


    @Override
    public void setAllPosts( List<PostEntity> allPost ) {
        list.clear();
        list.addAll( allPost );
        adapterPosts.notifyDataSetChanged();
    }

    @Override
    public void createdPost( PostEntity postEntity ) {
        list.add( 0 , postEntity );
        adapterPosts.notifyItemInserted( 0 );
    }

    @Override
    public void updatePost( PostEntity postEntity , int position ) {
        list.set( position , postEntity );
        adapterPosts.notifyItemChanged( position );
    }

    @Override
    public void deletePost( int position ) {
        list.remove( position );
        adapterPosts.notifyItemRemoved( position );
    }

    @Override
    public void showError( String message ) {
        Toast.makeText( this , message , Toast.LENGTH_LONG ).show();
    }

    @Override
    public void showComplete( String message ) {
        Toast.makeText( this , message , Toast.LENGTH_LONG ).show();
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility( View.VISIBLE );
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility( View.GONE );
    }
}
