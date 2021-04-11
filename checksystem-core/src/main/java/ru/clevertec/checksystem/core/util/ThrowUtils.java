package ru.clevertec.checksystem.core.util;

import java.math.BigDecimal;

public final class ThrowUtils {

    public static final class Argument {

        private static final String NULL_MESSAGE_FORMAT = "Argument parameter '%s' cannot be null";
        private static final String NULL_OR_EMPTY_MESSAGE_FORMAT = "Argument's '%s' cannot be null or empty";
        private static final String GREATER_THAN_MESSAGE_FORMAT = "The '%s' argument's value '%b' greater be less than %b";
        private static final String LESS_THAN_MESSAGE_FORMAT = "The '%s' argument's value '%b' cannot be less than %b";
        private static final String ARGUMENT_OUT_OF_RANGE_MESSAGE_FORMAT = "Argument parameter '%s' has a value '%b' that is out of range %b - %b";

        public static <T> void nullValue(String parameterName, T argument) {
            if (argument == null)
                throw new NullPointerException(String.format(NULL_MESSAGE_FORMAT, parameterName));
        }

        public static void outOfRange(String parameterName, int parameterValue, int minInclusive, int maxInclusive) {
            if (parameterValue < minInclusive || parameterValue > maxInclusive)
                throw new IllegalArgumentException(String.format(ARGUMENT_OUT_OF_RANGE_MESSAGE_FORMAT, parameterName, parameterValue, minInclusive, maxInclusive));
        }

        public static void outOfRange(String parameterName, short parameterValue, short minInclusive, short maxInclusive) {
            if (parameterValue < minInclusive || parameterValue > maxInclusive)
                throw new IllegalArgumentException(String.format(ARGUMENT_OUT_OF_RANGE_MESSAGE_FORMAT, parameterName, parameterValue, minInclusive, maxInclusive));
        }

        public static void outOfRange(String parameterName, long parameterValue, long minInclusive, long maxInclusive) {
            if (parameterValue < minInclusive || parameterValue > maxInclusive)
                throw new IllegalArgumentException(String.format(ARGUMENT_OUT_OF_RANGE_MESSAGE_FORMAT, parameterName, parameterValue, minInclusive, maxInclusive));
        }

        public static void outOfRange(String parameterName, float parameterValue, float minInclusive, float maxInclusive) {
            if (parameterValue < minInclusive || parameterValue > maxInclusive)
                throw new IllegalArgumentException(String.format(ARGUMENT_OUT_OF_RANGE_MESSAGE_FORMAT, parameterName, parameterValue, minInclusive, maxInclusive));
        }

        public static void outOfRange(String parameterName, double parameterValue, double minInclusive, double maxInclusive) {
            if (parameterValue < minInclusive || parameterValue > maxInclusive)
                throw new IllegalArgumentException(String.format(ARGUMENT_OUT_OF_RANGE_MESSAGE_FORMAT, parameterName, parameterValue, minInclusive, maxInclusive));
        }

        public static void outOfRange(
                String parameterName, BigDecimal parameterValue, BigDecimal minInclusive, BigDecimal maxInclusive) {
            if (parameterValue.compareTo(minInclusive) < 0 || parameterValue.compareTo(maxInclusive) > 0)
                throw new IllegalArgumentException(String.format(ARGUMENT_OUT_OF_RANGE_MESSAGE_FORMAT, parameterName, parameterValue, minInclusive, maxInclusive));
        }

        public static <T> void nullOrEmpty(String parameterName, Iterable<T> collection) {
            if (collection == null)
                throw new NullPointerException(String.format(NULL_MESSAGE_FORMAT, parameterName));
            if (!collection.iterator().hasNext())
                throw new IllegalArgumentException(String.format(NULL_OR_EMPTY_MESSAGE_FORMAT, parameterName));
        }

        public static <T> void nullOrEmpty(String parameterName, T[] argument) {
            if (argument == null)
                throw new NullPointerException(String.format(NULL_MESSAGE_FORMAT, parameterName));
            if (argument.length == 0)
                throw new IllegalArgumentException(String.format(NULL_OR_EMPTY_MESSAGE_FORMAT, parameterName));
        }

        public static void nullOrEmpty(String parameterName, String str) {
            if (str == null)
                throw new NullPointerException(String.format(NULL_MESSAGE_FORMAT, parameterName));
            if (str.isEmpty())
                throw new IllegalArgumentException(String.format(NULL_OR_EMPTY_MESSAGE_FORMAT, parameterName));
        }

        public static void nullOrBlank(String parameterName, String str) {
            if (str == null)
                throw new NullPointerException(String.format(NULL_MESSAGE_FORMAT, parameterName));
            if (str.isBlank())
                throw new IllegalArgumentException(String.format(NULL_OR_EMPTY_MESSAGE_FORMAT, parameterName));
        }

        public static void greaterThan(String parameterName, int value, int maxInclusive) {
            if (value < maxInclusive)
                throw new IllegalArgumentException(String.format(parameterName, GREATER_THAN_MESSAGE_FORMAT, value, maxInclusive));
        }

        public static void greaterThan(String parameterName, short value, short maxInclusive) {
            if (value < maxInclusive)
                throw new IllegalArgumentException(String.format(parameterName, GREATER_THAN_MESSAGE_FORMAT, value, maxInclusive));
        }

        public static void greaterThan(String parameterName, long value, long maxInclusive) {
            if (value < maxInclusive)
                throw new IllegalArgumentException(String.format(parameterName, GREATER_THAN_MESSAGE_FORMAT, value, maxInclusive));
        }

        public static void greaterThan(String parameterName, float value, float maxInclusive) {
            if (value < maxInclusive)
                throw new IllegalArgumentException(String.format(parameterName, GREATER_THAN_MESSAGE_FORMAT, value, maxInclusive));
        }

        public static void greaterThan(String parameterName, Double value, Double maxInclusive) {
            if (value < maxInclusive)
                throw new IllegalArgumentException(String.format(parameterName, GREATER_THAN_MESSAGE_FORMAT, value, maxInclusive));
        }

        public static void greaterThan(String parameterName, BigDecimal value, BigDecimal maxInclusive) {
            if (value.compareTo(maxInclusive) < 0)
                throw new IllegalArgumentException(String.format(parameterName, GREATER_THAN_MESSAGE_FORMAT, value, maxInclusive));
        }

        public static void lessThan(String parameterName, Long value, int minInclusive) {
            if (value < minInclusive)
                throw new IllegalArgumentException(String.format(LESS_THAN_MESSAGE_FORMAT, parameterName, value, minInclusive));
        }

        public static void lessThan(String parameterName, short value, short minInclusive) {
            if (value < minInclusive)
                throw new IllegalArgumentException(String.format(LESS_THAN_MESSAGE_FORMAT, parameterName, value, minInclusive));
        }

        public static void lessThan(String parameterName, long value, long minInclusive) {
            if (value < minInclusive)
                throw new IllegalArgumentException(String.format(LESS_THAN_MESSAGE_FORMAT, parameterName, value, minInclusive));
        }

        public static void lessThan(String parameterName, float value, float minInclusive) {
            if (value < minInclusive)
                throw new IllegalArgumentException(String.format(LESS_THAN_MESSAGE_FORMAT, parameterName, value, minInclusive));
        }

        public static void lessThan(String parameterName, Double value, Double minInclusive) {
            if (value < minInclusive)
                throw new IllegalArgumentException(String.format(LESS_THAN_MESSAGE_FORMAT, parameterName, value, minInclusive));
        }

        public static void lessThan(String parameterName, BigDecimal value, BigDecimal minInclusive) {
            if (value.compareTo(minInclusive) < 0)
                throw new IllegalArgumentException(String.format(LESS_THAN_MESSAGE_FORMAT, parameterName, value, minInclusive));
        }
    }
}
