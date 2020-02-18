package org.stream;

import java.util.ArrayList;
import java.util.Optional;
import java.util.function.Predicate;

/**
 * 流接口
 *
 * @author chriszhang
 * @version 1.0
 * @date 2018/9/9
 */
public abstract class ReferenceStage<T> implements Stage<T> {

    protected T sourceData;

    public ReferenceStage(T sourceData) {
        this.sourceData = sourceData;
    }



}
