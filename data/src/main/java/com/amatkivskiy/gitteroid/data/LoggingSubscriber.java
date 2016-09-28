package com.amatkivskiy.gitteroid.data;

import com.amatkivskiy.gitteroid.domain.interactor.DefaultSubscriber;

public class LoggingSubscriber<Result> extends DefaultSubscriber<Result>{

  @Override
  public void onCompleted() {
//    Log.d("Gitteroid", getClass().getName() + ".onCompleted()");
  }

  @Override
  public void onError(Throwable e) {
//    Log.e("Gitteroid", getClass().getName() + ".onError():" + Log.getStackTraceString(e));
  }

  @Override
  public void onNext(Result result) {
//    Log.d("Gitteroid", getClass().getName() + ".onNext(" + result + ")");
  }
}
