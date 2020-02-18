package org.stream;

import java.util.Optional;

/**
 * @author chriszhang
 * @version 1.0
 * @date 2018/9/9
 */
public interface Terminal {
    <T extends Comparable> Optional<T> max();
}
