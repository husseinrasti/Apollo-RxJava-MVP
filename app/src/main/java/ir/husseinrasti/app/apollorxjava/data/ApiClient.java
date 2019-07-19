package ir.husseinrasti.app.apollorxjava.data;

import com.apollographql.apollo.ApolloClient;
import com.apollographql.apollo.api.Operation;
import com.apollographql.apollo.api.ResponseField;
import com.apollographql.apollo.cache.normalized.CacheKey;
import com.apollographql.apollo.cache.normalized.CacheKeyResolver;
import com.apollographql.apollo.cache.normalized.NormalizedCacheFactory;
import com.apollographql.apollo.cache.normalized.lru.EvictionPolicy;
import com.apollographql.apollo.cache.normalized.lru.LruNormalizedCacheFactory;

import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import ir.husseinrasti.app.apollorxjava.utils.Config;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

public class ApiClient {

    private static final String KEY_FOR_CACHE = "id";

    private static ApolloClient apolloClient;
    private static OkHttpClient okHttpClient;

    public static ApolloClient getClient() {
        if ( okHttpClient == null ) {
            initOkHttp();
        }

        if ( apolloClient == null ) {
            apolloClient = ApolloClient.builder()
                    .serverUrl( Config.BASE_URL )
//                    .normalizedCache( initCacheFactory() , initCacheKeyResolver() )
                    .okHttpClient( okHttpClient )
                    .build();
        }

        return apolloClient;
    }

    private static NormalizedCacheFactory initCacheFactory() {
        return new LruNormalizedCacheFactory(
                EvictionPolicy.builder()
                        .expireAfterAccess( Config.EXPIRE_AFTER_ACCESS_TIMEOUT , TimeUnit.MINUTES )
                        .maxSizeBytes( 10 * 1024 )
                        .build() );
    }

    private static CacheKeyResolver initCacheKeyResolver() {
        return new CacheKeyResolver() {

            @NotNull
            @Override
            public CacheKey fromFieldRecordSet( @NotNull ResponseField field , @NotNull Map<String, Object> recordSet ) {
                if ( recordSet.containsKey( KEY_FOR_CACHE ) ) {
                    String id = ( String ) recordSet.get( KEY_FOR_CACHE );
                    return CacheKey.from( id );
                }
                return CacheKey.NO_KEY;
            }

            @NotNull
            @Override
            public CacheKey fromFieldArguments( @NotNull ResponseField field , @NotNull Operation.Variables variables ) {
                return CacheKey.NO_KEY;
            }
        };
    }

    private static void initOkHttp() {
        OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder()
                .readTimeout( Config.REQUEST_TIMEOUT , TimeUnit.SECONDS )
                .writeTimeout( Config.REQUEST_TIMEOUT , TimeUnit.SECONDS )
                .connectTimeout( Config.REQUEST_TIMEOUT , TimeUnit.SECONDS );

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel( HttpLoggingInterceptor.Level.BODY );

        okHttpBuilder.addInterceptor( interceptor );

        okHttpClient = okHttpBuilder.build();
    }

    private static void resetApiClient() {
        apolloClient = null;
        okHttpClient = null;
    }

}
