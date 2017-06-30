package com.refactor.sweed.sweedversiontwo.data.retrofit;

import android.text.TextUtils;

import com.google.gson.Gson;

import com.refactor.sweed.sweedversiontwo.util.Constants;

import java.io.IOException;

import io.reactivex.schedulers.Schedulers;
import okhttp3.Credentials;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class SweedApi {
    private Retrofit mRetrofit;
    private OkHttpClient okHttpClient;
    private String access_token;

    private static OkHttpClient.Builder builder = new OkHttpClient.Builder();

    private HttpLoggingInterceptor getLogInterceptor() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        return loggingInterceptor;
    }

    private Retrofit getInstance() {
        if (mRetrofit == null) {
            mRetrofit = retrofitBuilder().build();
        }
        return mRetrofit;
    }

    private Retrofit.Builder retrofitBuilder() {

        return new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .client(okHttpClient());
    }

    private synchronized OkHttpClient okHttpClient() {
        if (okHttpClient == null) {

            setOkHttpClientDefaults(builder);
            okHttpClient = builder.build();
        }
        return okHttpClient;
    }


    private void setOkHttpClientDefaults(OkHttpClient.Builder builder) {
//        if (BuildConfig.DEBUG) {
//            builder.addInterceptor(getLogInterceptor());
//        }

        builder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Interceptor.Chain chain) throws IOException {
                Request original = chain.request();
                Request request = original.newBuilder()
//                        .header("Authorization",access_token)
                        .method(original.method(), original.body())
                        .build();
                return chain.proceed(request);
            }
        });
    }

    public APIRequests eventsService(String access_token) {
        this.access_token = access_token;
        return getInstance().create(APIRequests.class);
    }

    public APIRequests createService()
    {
        return getInstance().create(APIRequests.class);
    }

    public APIRequests createService(String accessToken)
    {
        this.access_token = accessToken;
        return getInstance().create(APIRequests.class);
    }

    public <S> S createAuthService(Class<S> serviceClass, String user, String password)
    {
        if(!TextUtils.isEmpty(user) && !TextUtils.isEmpty(password))
        {
            String auth  = Credentials.basic(user,password);
            AuthenticationInterceptor authInterceptor = new AuthenticationInterceptor(auth);
            if(!builder.interceptors().contains(authInterceptor))
            {
                builder.addInterceptor(authInterceptor);
                retrofitBuilder().client(okHttpClient);
                mRetrofit = retrofitBuilder().build();
            }
        }
        return mRetrofit.create(serviceClass);
    }

}
