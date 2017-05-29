package com.applications.whazzup.photomapp.di.modules;


import com.applications.whazzup.photomapp.data.network.RestService;
import com.applications.whazzup.photomapp.util.AppConfig;
import com.applications.whazzup.photomapp.util.ConstantManager;
import com.facebook.stetho.okhttp3.StethoInterceptor;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.moshi.MoshiConverterFactory;

@Module
public class NetworkModule {
    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient() {
        return createClient();
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit(OkHttpClient okHttpClient) {
        return createRetrofit(okHttpClient);
    }

    @Provides
    @Singleton
    RestService provideRestService(Retrofit retrofit) {
        return retrofit.create(RestService.class);
    }

    private OkHttpClient createClient() {
        return new OkHttpClient.Builder()
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .addNetworkInterceptor(new StethoInterceptor())
                .connectTimeout(ConstantManager.MAX_CONNECTION_TIMEOUT, TimeUnit.MILLISECONDS)
                .readTimeout(ConstantManager.MAX_READ_TIMEOUT, TimeUnit.MILLISECONDS)
                .writeTimeout(ConstantManager.MAX_WRITE_TIMEOUT, TimeUnit.MILLISECONDS)
                .build();
    }

    private Retrofit createRetrofit(OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl(AppConfig.BASE_URL)
                .addConverterFactory(createConvertFactory())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build();
    }

    private Converter.Factory createConvertFactory() {
        return MoshiConverterFactory.create();
    }
}
