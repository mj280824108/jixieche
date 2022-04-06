package com.weiwei.jixieche.verify;

import com.weiwei.jixieche.response.ResponseException;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;

/**
 * @ClassName AssertUtil
 * @Description TODO
 * @Author houji
 * @Date 2019/5/14 9:57
 * @Version 1.0.1
 **/
public class AssertUtil {
    private AssertUtil() {
    }

    /**
     * 断定目标值为false.为true则抛出业务异常
     *
     * @param expression
     * @param message
     * @throws ResponseException
     */
    public static void isFlase(boolean expression, String message) throws ResponseException {
        if (expression) {
            throw new ResponseException(message);
        }
    }

    /**
     * 断定目标值为false.为true则抛出业务异常
     *
     * @param expression
     * @param message
     * @throws ResponseException
     */
    public static void isFlase(boolean expression, String message, int code) throws ResponseException {
        if (expression) {
            throw new ResponseException(message, code);
        }
    }

    /**
     * 断定目标值为true.为false则抛出业务异常
     *
     * @param expression
     * @param message
     * @throws ResponseException
     */
    public static void isTrue(boolean expression, String message) throws ResponseException {
        if (!expression) {
            throw new ResponseException(message);
        }
    }

    /**
     * 断定目标值为true.为false则抛出业务异常
     *
     * @param expression
     * @param message
     * @throws ResponseException
     */
    public static void isTrue(boolean expression, String message, int code) throws ResponseException {
        if (!expression) {
            throw new ResponseException(message, code);
        }
    }

    /**
     * 断定目标值为null.不为null则抛出业务异常
     *
     * @param obj
     * @param message
     * @throws ResponseException
     */
    public static void isNull(Object obj, String message) throws ResponseException {
        if (obj != null) {
            throw new ResponseException(message);
        }
    }

    /**
     * 断定目标值为null.不为null则抛出业务异常
     *
     * @param obj
     * @param message
     * @throws ResponseException
     */
    public static void isNull(Object obj, String message, int code) throws ResponseException {
        if (obj != null) {
            throw new ResponseException(message, code);
        }
    }

    /**
     * 断定目标值不为null.为null则抛出业务异常
     *
     * @param obj
     * @param message
     * @throws ResponseException
     */
    public static void notNull(Object obj, String message) throws ResponseException {
        if (obj == null) {
            throw new ResponseException(message);
        }
    }

    /**
     * 断定目标值不为null.为null则抛出业务异常
     *
     * @param obj
     * @param message
     * @throws ResponseException
     */
    public static void notNull(Object obj, String message, int code) throws ResponseException {
        if (obj == null) {
            throw new ResponseException(message, code);
        }
    }

    /**
     * 断定目标list不为空.为空则抛出业务异常
     *
     * @param collection
     * @param message
     * @throws ResponseException
     */
    public static void notEmpty(Collection<?> collection, String message) throws ResponseException {
        if (CollectionUtils.isEmpty(collection)) {
            throw new ResponseException(message);
        }
    }

    /**
     * 断定目标list不为空.为空则抛出业务异常
     *
     * @param collection
     * @param message
     * @throws ResponseException
     */
    public static void notEmpty(Collection<?> collection, String message, int code) throws ResponseException {
        if (CollectionUtils.isEmpty(collection)) {
            throw new ResponseException(message, code);
        }
    }

    /**
     * 断定目标list为空.不为空则抛出业务异常
     *
     * @param collection
     * @param message
     * @throws ResponseException
     */
    public static void isEmpty(Collection<?> collection, String message) throws ResponseException {
        if (CollectionUtils.isNotEmpty(collection)) {
            throw new ResponseException(message);
        }
    }

    /**
     * 断定目标list为空.不为空则抛出业务异常
     *
     * @param collection
     * @param message
     * @throws ResponseException
     */
    public static void isEmpty(Collection<?> collection, String message, int code) throws ResponseException {
        if (CollectionUtils.isNotEmpty(collection)) {
            throw new ResponseException(message, code);
        }
    }

    /**
     * 断定目标字符串不为空.为空则抛出业务异常
     *
     * @param string
     * @param message
     * @throws ResponseException
     */
    public static void notEmpty(String string, String message) throws ResponseException {

        if (StringUtils.isBlank(string)) {
            throw new ResponseException(message);
        }
    }

    /**
     * 断定目标字符串不为空.为空则抛出业务异常
     *
     * @param string
     * @param message
     * @throws ResponseException
     */
    public static void notEmpty(String string, String message, int code) throws ResponseException {

        if (StringUtils.isBlank(string)) {
            throw new ResponseException(message, code);
        }
    }

    /**
     * 断定目标字符串为空.不为空则抛出业务异常
     *
     * @param string
     * @param message
     * @throws ResponseException
     */
    public static void empty(String string, String message) throws ResponseException {

        if (StringUtils.isNotBlank(string)) {
            throw new ResponseException(message);
        }
    }

    /**
     * 断定目标字符串为空.不为空则抛出业务异常
     *
     * @param string
     * @param message
     * @throws ResponseException
     */
    public static void empty(String string, String message, int code) throws ResponseException {

        if (StringUtils.isNotBlank(string)) {
            throw new ResponseException(message, code);
        }
    }

    /**
     * 断定两个字符串不相等,相等则抛出指定业务异常
     *
     * @param source
     * @param target
     * @param message
     * @throws ResponseException
     */
    public static void notEqualString(String source, String target, String message) throws ResponseException {
        if (source.equals(target)) {
            throw new ResponseException(message);
        }
    }

    /**
     * 断定两个字符串不相等,相等则抛出指定业务异常
     *
     * @param source
     * @param target
     * @param message
     * @throws ResponseException
     */
    public static void notEqualString(String source, String target, String message, int code) throws ResponseException {
        if (source.equals(target)) {
            throw new ResponseException(message, code);
        }
    }

    /**
     * 断定数值是否大于0,不大于则抛出指定业务异常
     *
     * @param number
     * @param message
     * @throws ResponseException
     * @author taok
     * @date 2016-04-11
     */
    public static void numberGtZero(Integer number, String message,int code) throws ResponseException {
        if (number == null || number <= 0) {
            throw new ResponseException(code,message);
        }
    }

    /**
     * 断定数值是否大于0,不大于则抛出指定业务异常
     *
     * @param number
     * @param message
     * @throws ResponseException
     * @author taok
     * @date 2016-04-20
     */
    public static void LongGtZero(Long number, String message,int code) throws ResponseException {
        if (number == null || number <= 0) {
            throw new ResponseException(message,code);
        }
    }

    /**
     * 断定数值大于指定数值则抛出指定业务异常
     *
     * @param number
     * @param message
     * @throws ResponseException
     * @author taok
     * @date 2016-04-20
     */
    public static void LongGtNumber(Long number, Long targetNumber, String message) throws ResponseException {
        if (number != null && number > targetNumber) {
            throw new ResponseException(message);
        }
    }
}
