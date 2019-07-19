package ir.husseinrasti.app.apollorxjava.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import ir.husseinrasti.app.apollorxjava.R;
import ir.husseinrasti.app.apollorxjava.data.PostEntity;

public class AdapterPosts extends RecyclerView.Adapter<AdapterPosts.ViewHolder> {

    private Context context;
    private List<PostEntity> allPost;

    public AdapterPosts( Context context , List<PostEntity> allPost ) {
        this.context = context;
        this.allPost = allPost;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder( @NonNull ViewGroup parent , int viewType ) {
        View view = LayoutInflater.from( parent.getContext() ).inflate( R.layout.adapter_item_post , parent , false );
        return new ViewHolder( view , context );
    }

    @Override
    public void onBindViewHolder( @NonNull ViewHolder holder , int position ) {
        holder.bindingViews( allPost.get( position ) );
    }

    @Override
    public int getItemCount() {
        return allPost.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.desc)
        TextView desc;
        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.imageView)
        ImageView imageView;

        private Context context;

        public ViewHolder( @NonNull View itemView , Context context ) {
            super( itemView );
            this.context = context;
            ButterKnife.bind( this , itemView );
        }

        private void bindingViews( PostEntity post ) {
            title.setText( post.getTitle() );
            desc.setText( post.getDesc() );
            String imageUrl = post.getImagUrl();
            if ( !imageUrl.contains( "none" ) ) {
                Picasso.with( context ).load( imageUrl ).into( imageView );
            }
        }
    }
}