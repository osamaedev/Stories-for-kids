package com.yoham.storieskids.ui.reading

import com.yoham.storieskids.R
import com.yoham.storieskids.data.DataManager
import com.yoham.storieskids.data.IDataManager
import com.yoham.storieskids.ui.base.BasePresenter
import com.yoham.storieskids.utils.rx.ISchedulerProvider
import com.yoham.storieskids.utils.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.reactive.asFlow
import kotlinx.coroutines.reactive.openSubscription

class NewReadingPresenter<V : IReadingView>(val dataManager: DataManager,
                                            val schedulerProvider: SchedulerProvider,
                                            val disposables: CompositeDisposable) : BasePresenter<V>(dataManager, schedulerProvider, disposables) {


    fun loadStoryById(storyId: Int) {
        rootView.showLoading()
        CoroutineScope(Dispatchers.IO).launch {

            // we use this openSubscription() method and consume
            // element like they were emitted by a channel
            val storyItem = dataManager.getStoryById(storyId).openSubscription().receive()

            // the above method is deprecated
            // new method ...
            try {
                dataManager.getStoryById(storyId).asFlow().collect {
                    CoroutineScope(Dispatchers.Main).launch {
                        if (it != null) {
                            rootView.setStoryTitle(it.title)
                            rootView.setStoryToolBarTitle(it.title)
                            rootView.setStoryBody(it.body)
                            rootView.setStoryImage(it.image)
                            rootView.hideLoading()
                        }
                    }
                }
            } catch (e: Exception) {
                rootView.hideLoading()
                rootView.showMessage(R.string.general_error)
            }
        }
    }

}