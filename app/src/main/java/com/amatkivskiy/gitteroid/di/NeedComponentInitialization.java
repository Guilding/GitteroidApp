package com.amatkivskiy.gitteroid.di;

public interface NeedComponentInitialization<T> {
  void initComponentAndInjectMembers(T rootComponent);
}
