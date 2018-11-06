package jdk18.stream;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collector;

/**
 * @author chriszhang
 * @version 1.0
 * @date 2018/9/9
 */
public class ArrayListReferenceStage<E> extends ReferenceStage<ArrayList> implements Terminal{


    public ArrayListReferenceStage(ArrayList<E> sourceData) {
        super(sourceData);
    }

    @Override
    public <E> ReferenceStage<ArrayList> filter(Predicate<E> predicate) {
        ArrayList newList = new ArrayList(sourceData.size());
        sourceData.forEach(obj -> {
            if(predicate.test((E)obj)){
                newList.add(obj);
            }
        });
        return new ArrayListReferenceStage<>(newList);
    }

    @Override
    public <T extends Comparable> Optional<T> max() {
        if(Objects.isNull(sourceData)){
            return Optional.empty();
        }
        T result = (T)sourceData.get(0);
        for(Object num :sourceData){
            if(((T)num).compareTo(result) > 0){
                result = (T) num;
            }
        }
        return Optional.of(result);
    }

    final static class Head<E> extends ReferenceStage<ArrayList<E>> implements Terminal{

        public Head(ArrayList sourceData) {
            super(sourceData);
        }

        @Override
        public ReferenceStage<ArrayList<E>> filter(Predicate predicate) {
            ArrayList newList = new ArrayList(sourceData.size());
            sourceData.forEach(obj -> {
                if(predicate.test(obj)){
                    newList.add(obj);
                }
            });
            return new ArrayListReferenceStage<>(newList);
        }


        @Override
        public <T extends Comparable> Optional<T> max() {
            throw new UnsupportedOperationException("not support!!");
        }
    }
}
