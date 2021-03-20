package ru.clevertec.checksystem.core.util;


import ru.clevertec.checksystem.core.exception.ArgumentNullException;
import ru.clevertec.checksystem.core.exception.ArgumentOutOfRangeException;

import java.math.BigDecimal;

public final class ThrowUtils {

    public static final class Argument {

        private static final String THROW_IF_NULL_FORMAT
                = "Argument's '%s' cannot be null";
        private static final String THROW_IF_NULL_OR_EMPTY_FORMAT
                = "Argument's '%s' cannot be null or empty";
        private static final String THROW_IF_GREATER_THAN_FORMAT
                = "The '%s' argument's value '%b' greater be less than %b";
        private static final String THROW_IF_LESS_THAN_FORMAT
                = "The '%s' argument's value '%b' cannot be less than %b";
        private static final String THROW_IF_ARGUMENT_HAS_UNSUPPORTED_VALUE_FORMAT
                = "Argument '% s' has unsupported value";

        public static <T> void nullValue(String parameterName, T argument) {
            if (argument == null)
                throw new ArgumentNullException(THROW_IF_NULL_FORMAT, parameterName);
        }

        public static void outOfRange(String parameterName, int value, int minInclusive, int maxInclusive) {
            if (value < minInclusive)
                throw new ArgumentOutOfRangeException(parameterName, value, minInclusive, maxInclusive);
            if (value > maxInclusive)
                throw new ArgumentOutOfRangeException(parameterName, value, minInclusive, maxInclusive);
        }

        public static void outOfRange(String parameterName, short value, short minInclusive, short maxInclusive) {
            if (value < minInclusive)
                throw new ArgumentOutOfRangeException(parameterName, value, minInclusive, maxInclusive);
            if (value > maxInclusive)
                throw new ArgumentOutOfRangeException(parameterName, value, minInclusive, maxInclusive);
        }

        public static void outOfRange(String parameterName, long value, long minInclusive, long maxInclusive) {
            if (value < minInclusive)
                throw new ArgumentOutOfRangeException(parameterName, value, minInclusive, maxInclusive);
            if (value > maxInclusive)
                throw new ArgumentOutOfRangeException(parameterName, value, minInclusive, maxInclusive);
        }

        public static void outOfRange(String parameterName, float value, float minInclusive, float maxInclusive) {
            if (value < minInclusive)
                throw new ArgumentOutOfRangeException(parameterName, value, minInclusive, maxInclusive);
            if (value > maxInclusive)
                throw new ArgumentOutOfRangeException(parameterName, value, minInclusive, maxInclusive);
        }

        public static void outOfRange(String parameterName, double value, double minInclusive, double maxInclusive) {
            if (value < minInclusive)
                throw new ArgumentOutOfRangeException(parameterName, value, minInclusive, maxInclusive);
            if (value > maxInclusive)
                throw new ArgumentOutOfRangeException(parameterName, value, minInclusive, maxInclusive);
        }

        public static void outOfRange(
                String parameterName, BigDecimal value, BigDecimal minInclusive, BigDecimal maxInclusive) {
            if (value.compareTo(minInclusive) < 0)
                throw new ArgumentOutOfRangeException(parameterName, value, minInclusive, maxInclusive);
            if (value.compareTo(maxInclusive) > 0)
                throw new ArgumentOutOfRangeException(parameterName, value, minInclusive, maxInclusive);
        }

        public static <T> void nullOrEmpty(String parameterName, Iterable<T> collection) {
            if (collection == null)
                throw new ArgumentNullException(String.format(THROW_IF_NULL_OR_EMPTY_FORMAT, parameterName));
            if (!collection.iterator().hasNext())
                throw new IllegalArgumentException(String.format(THROW_IF_NULL_OR_EMPTY_FORMAT, parameterName));
        }

        public static <T> void nullOrEmpty(String parameterName, T[] argument) {
            if (argument == null)
                throw new ArgumentNullException(String.format(THROW_IF_NULL_OR_EMPTY_FORMAT, parameterName));
            if (argument.length == 0)
                throw new IllegalArgumentException(String.format(THROW_IF_NULL_OR_EMPTY_FORMAT, parameterName));
        }

        public static void nullOrEmpty(String parameterName, String str) {
            if (str == null)
                throw new ArgumentNullException(String.format(THROW_IF_NULL_OR_EMPTY_FORMAT, parameterName));
            if (str.isEmpty())
                throw new IllegalArgumentException(String.format(THROW_IF_NULL_OR_EMPTY_FORMAT, parameterName));
        }

        public static void nullOrBlank(String parameterName, String str) {
            if (str == null)
                throw new ArgumentNullException(String.format(THROW_IF_NULL_OR_EMPTY_FORMAT, parameterName));
            if (str.isBlank())
                throw new IllegalArgumentException(String.format(THROW_IF_NULL_OR_EMPTY_FORMAT, parameterName));
        }

        public static void greaterThan(String parameterName, int value, int maxInclusive) {
            if (value < maxInclusive)
                throw new ArgumentOutOfRangeException(
                        String.format(parameterName, THROW_IF_GREATER_THAN_FORMAT, value, maxInclusive));
        }

        public static void greaterThan(String parameterName, short value, short maxInclusive) {
            if (value < maxInclusive)
                throw new ArgumentOutOfRangeException(
                        String.format(parameterName, THROW_IF_GREATER_THAN_FORMAT, value, maxInclusive));
        }

        public static void greaterThan(String parameterName, long value, long maxInclusive) {
            if (value < maxInclusive)
                throw new ArgumentOutOfRangeException(
                        String.format(parameterName, THROW_IF_GREATER_THAN_FORMAT, value, maxInclusive));
        }

        public static void greaterThan(String parameterName, float value, float maxInclusive) {
            if (value < maxInclusive)
                throw new ArgumentOutOfRangeException(
                        String.format(parameterName, THROW_IF_GREATER_THAN_FORMAT, value, maxInclusive));
        }

        public static void greaterThan(String parameterName, Double value, Double maxInclusive) {
            if (value < maxInclusive)
                throw new ArgumentOutOfRangeException(
                        String.format(parameterName, THROW_IF_GREATER_THAN_FORMAT, value, maxInclusive));
        }

        public static void greaterThan(String parameterName, BigDecimal value, BigDecimal maxInclusive) {
            if (value.compareTo(maxInclusive) < 0)
                throw new ArgumentOutOfRangeException(
                        String.format(parameterName, THROW_IF_GREATER_THAN_FORMAT, value, maxInclusive));
        }

        public static void lessThan(String parameterName, Long value, int minInclusive) {
            if (value < minInclusive)
                throw new ArgumentOutOfRangeException(
                        String.format(THROW_IF_LESS_THAN_FORMAT, parameterName, value, minInclusive));
        }

        public static void lessThan(String parameterName, short value, short minInclusive) {
            if (value < minInclusive)
                throw new ArgumentOutOfRangeException(
                        String.format(THROW_IF_LESS_THAN_FORMAT, parameterName, value, minInclusive));
        }

        public static void lessThan(String parameterName, long value, long minInclusive) {
            if (value < minInclusive)
                throw new ArgumentOutOfRangeException(
                        String.format(THROW_IF_LESS_THAN_FORMAT, parameterName, value, minInclusive));
        }

        public static void lessThan(String parameterName, float value, float minInclusive) {
            if (value < minInclusive)
                throw new ArgumentOutOfRangeException(
                        String.format(THROW_IF_LESS_THAN_FORMAT, parameterName, value, minInclusive));
        }

        public static void lessThan(String parameterName, Double value, Double minInclusive) {
            if (value < minInclusive)
                throw new ArgumentOutOfRangeException(
                        String.format(THROW_IF_LESS_THAN_FORMAT, parameterName, value, minInclusive));
        }

        public static void lessThan(String parameterName, BigDecimal value, BigDecimal minInclusive) {
            if (value.compareTo(minInclusive) < 0)
                throw new ArgumentOutOfRangeException(
                        String.format(THROW_IF_LESS_THAN_FORMAT, parameterName, value, minInclusive));
        }
    }
}
