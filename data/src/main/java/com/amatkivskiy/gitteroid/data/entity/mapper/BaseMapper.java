package com.amatkivskiy.gitteroid.data.entity.mapper;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseMapper<InputType, Result> {

  public abstract Result transform(InputType input);

  public List<Result> transform(List<InputType> inputs) {
    List<Result> newList = new ArrayList<>(inputs.size());

    for (InputType input : inputs) {
      newList.add(transform(input));
    }

    return newList;
  }
}
