package com.applications.whazzup.photomapp.jobs

import android.support.annotation.NonNull
import android.util.Log
import android.widget.Toast
import com.applications.whazzup.photomapp.App
import com.applications.whazzup.photomapp.data.managers.DataManager
import com.applications.whazzup.photomapp.util.ConstantManager
import com.birbit.android.jobqueue.Job
import com.birbit.android.jobqueue.Params
import com.birbit.android.jobqueue.RetryConstraint
import id.zelory.compressor.Compressor
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import javax.annotation.Nullable


class UploadPhotoJob(private val mImageUri: String) : Job(Params(JobPriority.HIGH)
        .requireNetwork().groupBy("uploadPhoto")) {


    override fun onAdded() {
        Log.e(TAG, "PRODUCT-AVATAR onAdded: "+mImageUri)

    }

    @Throws(Throwable::class)
    override fun onRun() {

        Log.e(TAG, "AVATAR onRun: ")
        var file: File? = null
        var photoUrl : String = ""

        try {
//            file = File(getPath(App.appComponent!!.context, Uri.parse(mImageUri)))
            file=  File(mImageUri)
            file = Compressor.getDefault(App.appComponent!!.context).compressToFile(file)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        if (file != null) {
            val sendFile = RequestBody.create(MediaType.parse("multipart/form-data"), file)

            val mBody = MultipartBody.Part.createFormData("image", file.name, sendFile)

            DataManager.INSTANCE
                    .uploadUserPhoto(mBody)
                    .subscribeBy(onNext = {
                        Log.e("this", "fdsfds"+it.image);
                        DataManager.INSTANCE.mPreferencesManager.savePhotoUrl(it.image)
                    }, onComplete = {
                        Log.e("Job", "OnCompleteObservable")


                    }, onError = {
                        Log.e("this", "fdsfds"+it.message);
                    })
        } else {
            Observable.just("Повторите загрузку аватара, произошла ошибка")
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ message -> Toast.makeText(applicationContext, message, Toast.LENGTH_LONG).show() })
        }
    }

    override fun onCancel(i: Int, @Nullable throwable: Throwable?) {
        Log.e(TAG, "AVATAR onCancel: ")
    }

    override fun shouldReRunOnThrowable(@NonNull throwable: Throwable, runCount: Int, maxRunCount: Int): RetryConstraint {
        return RetryConstraint.createExponentialBackoff(runCount, ConstantManager
                .INITIAL_BACK_OFF_IN_MS)
    }

    companion object {
        private val TAG = "UploadAvatarJob"
    }
}
