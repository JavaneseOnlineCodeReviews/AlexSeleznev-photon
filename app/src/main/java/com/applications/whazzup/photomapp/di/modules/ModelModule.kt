package com.applications.whazzup.photomapp.di.modules

import com.applications.whazzup.photomapp.App
import com.applications.whazzup.photomapp.data.managers.DataManager
import com.applications.whazzup.photomapp.data.managers.RealmManager
import com.birbit.android.jobqueue.JobManager

import javax.inject.Singleton

import dagger.Module
import dagger.Provides
import com.applications.whazzup.photomapp.util.ConstantManager
import com.birbit.android.jobqueue.config.Configuration
import com.birbit.android.jobqueue.config.Configuration.MAX_CONSUMER_COUNT
import com.birbit.android.jobqueue.config.Configuration.MIN_CONSUMER_COUNT



@Module
class ModelModule {

    @Provides
    @Singleton
    fun provideDataManager(): DataManager {
        return DataManager()
    }

    @Provides
    @Singleton
    fun provideRealmManager(): RealmManager {
        return RealmManager()
    }

    @Provides
    @Singleton
    internal fun provideJobManager(): JobManager {
        val configuration = Configuration.Builder(App.appComponent!!.context)
                .minConsumerCount(ConstantManager.MIN_CONSUMER_COUNT) //минимальное кол-во потоков для решения задачи
                .maxConsumerCount(ConstantManager.MAX_CONSUMER_COUNT) //максимальное кол-во потоков для решения задачи
                .loadFactor(ConstantManager.LOAD_FACTOR) // кол-во задач на один поток
                .consumerKeepAlive(ConstantManager.KEEP_ALIVE) // ожидание 2 минуты на поток
                .build()

        return JobManager(configuration)
    }
}
