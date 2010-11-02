package com.elixirian.common.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * This class is a utility class to help use {@link Object} in the Java programming language. It was inspired by <a
 * href="http://download.java.net/jdk7/docs/api/java/util/Objects.html">http://download
 * .java.net/jdk7/docs/api/java/util/Objects.html</a> and <a
 * href="http://blogs.sun.com/darcy/entry/java_util_objects_and_friends"
 * >http://blogs.sun.com/darcy/entry/java_util_objects_and_friends</a> yet now has become a different class from the
 * java.util.Objects class.
 * <p>
 * This class is more convenient and useful than java.util.Objects because unlike the current java.util.Objects, it does
 * not have any methods with the names already used in {@link java.lang.Object} so does not need to worry about <a
 * href="http://java.sun.com/docs/books/jls/third_edition/html/names.html#34133">Shadowing Declarations</a> when using
 * the methods in this {@link Objects} class by static import.
 * </p>
 * <p>
 * It also provides convenient ways to compute a hashCode of primitive and object reference types. More over, it has
 * {@link ToStringBuilder} which can be used to create toString value for the {@link Object#toString()} method so that
 * do the users not only need to use {@link StringBuilder} for this purpose but also have easier way to do it
 * <p>
 * e.g.) instead of doing
 * 
 * <pre>
 * <code>
 * return new StringBuilder.append(getClass().getSimpleName())
 *             .append("{")
 *             .append("fieldName1=")
 *             .append(value1)
 *             .append(", ")
 *             .append("fieldName2=")
 *             .append(value2)
 *             .append("}")
 *             .toString();
 * </code>
 * </pre>
 * 
 * it can be
 * 
 * <pre>
 * <code>
 * return toStringBuilder(this)
 *         .add("fieldName1", value1)
 *         .add("fieldName2", value2)
 *         .toString();
 * </code>
 * </pre>
 * 
 * Both ways generate the same result, but using the {@link ToStringBuilder} is much simpler.
 * 
 * <pre>
 * SimpleClassName{fieldName1=actualValue1, fieldName2=actualValue2}
 * </pre>
 * 
 * </p>
 * </p>
 * 
 * @author Lee, SeongHyun (Kevin)
 * @version 0.0.1 (2010-04-06)
 * @version 0.0.2 (2010-07-05) {@link com.elixirian.common.util.Objects.ToStringBuilder},
 *          {@link #toStringBuilder(Object)} and {@link #toStringBuilder(Object, String, String)} are added.
 * @version 0.0.3 (2010-10-31)
 *          <ul>
 *          <li>hash methods for primitive types and reference types are created.</li>
 *          <li>hashCodeOf methods for primitive types and reference types are created.</li>
 *          <li>equals(Object, Object) method is changed to {@link #equal(Object, Object)}.</li>
 *          <li>deepEquals(Object, Object) method is changed to {@link #deepEqual(Object, Object)}.</li>
 *          <li>toString(Object) method is changed to {@link #toStringOf(Object)}</li>
 *          <li>toString(Object, String) method is changed to {@link #toStringOf(Object, String)}</li>
 *          </ul>
 */
public final class Objects
{
	/**
	 * 1
	 */
	public static final int HASH_SEED = 1;

	/**
	 * 31
	 */
	public static final int HASH_PRIME = 31;

	private Objects()
	{
		throw new IllegalStateException(getClass().getName() + CommonConstants.CANNOT_BE_INSTANTIATED);
	}

	/**
	 * Returns {@code true} if the arguments are equal to each other and {@code false} otherwise. Consequently, if both
	 * arguments are {@code null}, {@code true} is returned and if exactly one argument is {@code null}, {@code false}
	 * is returned. Otherwise, equality is determined by using the {@link Object#equals(Object) equals} method of the
	 * first argument.
	 * 
	 * @param left
	 *            an object
	 * @param right
	 *            an object to be compared with a for equality
	 * @return true if the arguments are equal to each other and false otherwise
	 * @see {@link Object#equals(Object)}
	 */
	public static boolean equal(final Object left, final Object right)
	{
		return (left == right) || (null != left && left.equals(right));
	}

	/**
	 * Returns {@code true} if the arguments are deeply equal to each other and {@code false} otherwise. Two
	 * {@code null} values are deeply equal. If both arguments are arrays, the algorithm in
	 * {@link Arrays#deepEquals(Object[], Object[]) Arrays.deepEquals} is used to determine equality. Otherwise,
	 * equality is determined by using the {@link Object#equals(Object) equals} method of the first argument.
	 * 
	 * @param left
	 *            an object
	 * @param right
	 *            an object to be compared with a for deep equality
	 * @return {@code true} if the arguments are deeply equal to each other and {@code false} otherwise
	 * @see {@link Arrays#deepEquals(Object[], Object[])}, {@link #equal(Object, Object)}
	 */
	public static boolean deepEqual(final Object left, final Object right)
	{
		return (left == right) || ((null != left && null != right) && ((left.getClass()
				.isArray() && right.getClass()
				.isArray()) ? Arrays.deepEquals((Object[]) left, (Object[]) right) : left.equals(right)));
	}

	/**
	 * Returns the hash code of the given input value.
	 * 
	 * @param value
	 *            the given input value.
	 * @return the hash code of the given input value.
	 * @see {@link Byte#hashCode()}
	 */
	public static int hashCodeOf(final byte value)
	{
		return value;
	}

	/**
	 * Returns the hash code of the given input value.
	 * 
	 * @param value
	 *            the given input value.
	 * @return the hash code of the given input value.
	 * @see {@link Character#hashCode()}
	 */
	public static int hashCodeOf(final char value)
	{
		return value;
	}

	/**
	 * Returns the hash code of the given input value.
	 * 
	 * @param value
	 *            the given input value.
	 * @return the hash code of the given input value.
	 * @see {@link Short#hashCode()}
	 */
	public static int hashCodeOf(final short value)
	{
		return value;
	}

	/**
	 * Returns the hash code of the given input value.
	 * 
	 * @param value
	 *            the given input value.
	 * @return the hash code of the given input value.
	 * @see {@link Integer#hashCode()}
	 */
	public static int hashCodeOf(final int value)
	{
		return value;
	}

	/**
	 * Returns the hash code of the given input value.
	 * 
	 * @param value
	 *            the given input value.
	 * @return the hash code of the given input value.
	 * @see {@link Long#hashCode()}
	 */
	public static int hashCodeOf(final long value)
	{
		return (int) (value ^ (value >>> 32));
	}

	/**
	 * Returns the hash code of the given input value.
	 * 
	 * @param value
	 *            the given input value.
	 * @return the hash code of the given input value.
	 * @see {@link Float#hashCode()}
	 */
	public static int hashCodeOf(final float value)
	{
		return Float.floatToIntBits(value);
	}

	/**
	 * Returns the hash code of the given input value.
	 * 
	 * @param value
	 *            the given input value.
	 * @return the hash code of the given input value.
	 * @see {@link Double#hashCode()}
	 */
	public static int hashCodeOf(final double value)
	{
		long bits = Double.doubleToLongBits(value);
		return (int) (bits ^ (bits >>> 32));
	}

	/**
	 * Returns the hash code of the given input value.
	 * 
	 * @param value
	 *            the given input value.
	 * @return the hash code of the given input value.
	 * @see {@link Boolean#hashCode()}
	 */
	public static int hashCodeOf(final boolean value)
	{
		return value ? 1231 : 1237;
	}

	/**
	 * Returns the hash code of a non-{@code null} argument and 0 for a {@code null} argument.
	 * 
	 * @param object
	 *            an object
	 * @return the hash code of a non-{@code null} argument and 0 for a {@code null} argument
	 * @see {@link Object#hashCode()}
	 */
	public static int hashCodeOf(final Object object)
	{
		return (null == object ? 0 : object.hashCode());
	}

	/**
	 * Generates a hash code for the given input value using {@link #HASH_PRIME} and the given seed. The result will be
	 * <p>
	 * <code>
	 * return HASH_PRIME * seed + value;
	 * </code>
	 * </p>
	 * 
	 * @param seed
	 *            the seed to be used.
	 * @param value
	 *            the value to be hashed.
	 * @return a hash value of the input value
	 */
	private static int hash0(final int seed, final byte value)
	{
		return HASH_PRIME * seed + value;
	}

	/**
	 * Generates a hash code for the given input value using the {@link #HASH_SEED}.
	 * 
	 * @param value
	 *            the value to be hashed.
	 * @return a hash value of the input value
	 */
	public static int hash(final byte value)
	{
		return hash0(HASH_SEED, value);
	}

	/**
	 * Generates a hash code for the given input value using {@link #HASH_PRIME} and the given seed. The result will be
	 * <p>
	 * <code>
	 * return HASH_PRIME * seed + value;
	 * </code>
	 * </p>
	 * 
	 * @param seed
	 *            the seed to be used.
	 * @param value
	 *            the value to be hashed.
	 * @return a hash value of the input value
	 */
	public static int hash(final int seed, final byte value)
	{
		return hash0(seed, value);
	}

	/**
	 * Generates a hash code for the values in an input array using the given seed.
	 * 
	 * @param seed
	 *            the seed to be used.
	 * @param values
	 *            the values to be hashed.
	 * @return a hash value of the input values.
	 */
	public static int hash(final int seed, final byte[] values)
	{
		if (null == values)
			return 0;

		int result = seed;
		for (final byte value : values)
			result = hash0(result, value);

		return result;
	}

	/**
	 * Generates a hash code for a sequence of input values. The hash code is generated as if all the input values were
	 * placed into an array.
	 * 
	 * @param seed
	 *            the seed to be used.
	 * @param value
	 *            the first value to be hashed.
	 * @param values
	 *            the rest values to be hashed.
	 * @return a hash value of the sequence of input values.
	 */
	public static int hashBytes(final int seed, final byte value, final byte... values)
	{
		return hash(hash0(seed, value), values);
	}

	/**
	 * Generates a hash code for the given input value using {@link #HASH_PRIME} and the given seed. The result will be
	 * <p>
	 * <code>
	 * return HASH_PRIME * seed + value;
	 * </code>
	 * </p>
	 * 
	 * @param seed
	 *            the seed to be used.
	 * @param value
	 *            the value to be hashed.
	 * @return a hash value of the input value
	 */
	private static int hash0(final int seed, char value)
	{
		return HASH_PRIME * seed + value;
	}

	/**
	 * Generates a hash code for the given input value using the {@link #HASH_SEED}.
	 * 
	 * @param value
	 *            the value to be hashed.
	 * @return a hash value of the input value
	 */
	public static int hash(final char value)
	{
		return hash0(HASH_SEED, value);
	}

	/**
	 * Generates a hash code for the given input value using {@link #HASH_PRIME} and the given seed. The result will be
	 * <p>
	 * <code>
	 * return HASH_PRIME * seed + value;
	 * </code>
	 * </p>
	 * 
	 * @param seed
	 *            the seed to be used.
	 * @param value
	 *            the value to be hashed.
	 * @return a hash value of the input value
	 */
	public static int hash(final int seed, final char value)
	{
		return hash0(seed, value);
	}

	/**
	 * Generates a hash code for the values in an input array using the given seed.
	 * 
	 * @param seed
	 *            the seed to be used.
	 * @param values
	 *            the values to be hashed.
	 * @return a hash value of the input values.
	 */
	public static int hash(final int seed, final char[] values)
	{
		if (null == values)
			return 0;

		int result = seed;
		for (final char value : values)
			result = hash0(result, value);

		return result;
	}

	/**
	 * Generates a hash code for a sequence of input values. The hash code is generated as if all the input values were
	 * placed into an array.
	 * 
	 * @param seed
	 *            the seed to be used.
	 * @param value
	 *            the first value to be hashed.
	 * @param values
	 *            the rest values to be hashed.
	 * @return a hash value of the sequence of input values.
	 */
	public static int hashChars(final int seed, final char value, final char... values)
	{
		return hash(hash0(seed, value), values);
	}

	/**
	 * Generates a hash code for the given input value using {@link #HASH_PRIME} and the given seed. The result will be
	 * <p>
	 * <code>
	 * return HASH_PRIME * seed + value;
	 * </code>
	 * </p>
	 * 
	 * @param seed
	 *            the seed to be used.
	 * @param value
	 *            the value to be hashed.
	 * @return a hash value of the input value
	 */
	private static int hash0(final int seed, final short value)
	{
		return HASH_PRIME * seed + value;
	}

	/**
	 * Generates a hash code for the given input value using the {@link #HASH_SEED}.
	 * 
	 * @param value
	 *            the value to be hashed.
	 * @return a hash value of the input value
	 */
	public static int hash(final short value)
	{
		return hash0(HASH_SEED, value);
	}

	/**
	 * Generates a hash code for the given input value using {@link #HASH_PRIME} and the given seed. The result will be
	 * <p>
	 * <code>
	 * return HASH_PRIME * seed + value;
	 * </code>
	 * </p>
	 * 
	 * @param seed
	 *            the seed to be used.
	 * @param value
	 *            the value to be hashed.
	 * @return a hash value of the input value
	 */
	public static int hash(final int seed, final short value)
	{
		return hash0(seed, value);
	}

	/**
	 * Generates a hash code for the values in an input array using the given seed.
	 * 
	 * @param seed
	 *            the seed to be used.
	 * @param values
	 *            the values to be hashed.
	 * @return a hash value of the input values.
	 */
	public static int hash(final int seed, final short[] values)
	{
		if (null == values)
			return 0;

		int result = seed;
		for (final short value : values)
			result = hash0(result, value);

		return result;
	}

	/**
	 * Generates a hash code for a sequence of input values. The hash code is generated as if all the input values were
	 * placed into an array.
	 * 
	 * @param seed
	 *            the seed to be used.
	 * @param value
	 *            the first value to be hashed.
	 * @param values
	 *            the rest values to be hashed.
	 * @return a hash value of the sequence of input values.
	 */
	public static int hashShorts(final int seed, final short value, final short... values)
	{
		return hash(hash0(seed, value), values);
	}

	/**
	 * Generates a hash code for the given input value using {@link #HASH_PRIME} and the given seed. The result will be
	 * <p>
	 * <code>
	 * return HASH_PRIME * seed + value;
	 * </code>
	 * </p>
	 * 
	 * @param seed
	 *            the seed to be used.
	 * @param value
	 *            the value to be hashed.
	 * @return a hash value of the input value
	 */
	private static int hash0(final int seed, final int value)
	{
		return HASH_PRIME * seed + value;
	}

	/**
	 * Generates a hash code for the given input value using the {@link #HASH_SEED}.
	 * 
	 * @param value
	 *            the value to be hashed.
	 * @return a hash value of the input value
	 */
	public static int hash(final int value)
	{
		return hash0(HASH_SEED, value);
	}

	/**
	 * Generates a hash code for the given input value using {@link #HASH_PRIME} and the given seed. The result will be
	 * <p>
	 * <code>
	 * return HASH_PRIME * seed + value;
	 * </code>
	 * </p>
	 * 
	 * @param seed
	 *            the seed to be used.
	 * @param value
	 *            the value to be hashed.
	 * @return a hash value of the input value
	 */
	public static int hash(final int seed, final int value)
	{
		return hash0(seed, value);
	}

	/**
	 * Generates a hash code for the values in an input array using the given seed.
	 * 
	 * @param seed
	 *            the seed to be used.
	 * @param values
	 *            the values to be hashed.
	 * @return a hash value of the input values.
	 */
	public static int hash(final int seed, final int[] values)
	{
		if (null == values)
			return 0;

		int result = seed;
		for (final int value : values)
			result = hash0(result, value);

		return result;
	}

	/**
	 * Generates a hash code for a sequence of input values. The hash code is generated as if all the input values were
	 * placed into an array.
	 * 
	 * @param seed
	 *            the seed to be used.
	 * @param value
	 *            the first value to be hashed.
	 * @param values
	 *            the rest values to be hashed.
	 * @return a hash value of the sequence of input values.
	 */
	public static int hashInts(final int seed, final int value, final int... values)
	{
		return hash(hash0(seed, value), values);
	}

	/**
	 * Generates a hash code for the given input value using {@link #HASH_PRIME} and the given seed. The result will be
	 * <p>
	 * <code>
	 * return HASH_PRIME * seed + value;
	 * </code>
	 * </p>
	 * 
	 * @param seed
	 *            the seed to be used.
	 * @param value
	 *            the value to be hashed.
	 * @return a hash value of the input value
	 */
	private static int hash0(final int seed, final long value)
	{
		return HASH_PRIME * seed + (int) (value ^ (value >>> 32));
	}

	/**
	 * Generates a hash code for the given input value using the {@link #HASH_SEED}.
	 * 
	 * @param value
	 *            the value to be hashed.
	 * @return a hash value of the input value
	 */
	public static int hash(final long value)
	{
		return hash0(HASH_SEED, value);
	}

	/**
	 * Generates a hash code for the given input value using {@link #HASH_PRIME} and the given seed. The result will be
	 * <p>
	 * <code>
	 * return HASH_PRIME * seed + value;
	 * </code>
	 * </p>
	 * 
	 * @param seed
	 *            the seed to be used.
	 * @param value
	 *            the value to be hashed.
	 * @return a hash value of the input value
	 */
	public static int hash(final int seed, final long value)
	{
		return hash0(seed, value);
	}

	/**
	 * Generates a hash code for the values in an input array using the given seed.
	 * 
	 * @param seed
	 *            the seed to be used.
	 * @param values
	 *            the values to be hashed.
	 * @return a hash value of the input values.
	 */
	public static int hash(final int seed, final long[] values)
	{
		if (null == values)
			return 0;

		int result = seed;
		for (final long value : values)
			result = hash0(result, value);

		return result;
	}

	/**
	 * Generates a hash code for a sequence of input values. The hash code is generated as if all the input values were
	 * placed into an array.
	 * 
	 * @param seed
	 *            the seed to be used.
	 * @param value
	 *            the first value to be hashed.
	 * @param values
	 *            the rest values to be hashed.
	 * @return a hash value of the sequence of input values.
	 */
	public static int hashLongs(final int seed, final long value, final long... values)
	{
		return hash(hash0(seed, value), values);

	}

	/**
	 * Generates a hash code for the given input value using {@link #HASH_PRIME} and the given seed. The result will be
	 * <p>
	 * <code>
	 * return HASH_PRIME * seed + value;
	 * </code>
	 * </p>
	 * 
	 * @param seed
	 *            the seed to be used.
	 * @param value
	 *            the value to be hashed.
	 * @return a hash value of the input value
	 */
	private static int hash0(final int seed, final float value)
	{
		return HASH_PRIME * seed + Float.floatToIntBits(value);
	}

	/**
	 * Generates a hash code for the given input value using the {@link #HASH_SEED}.
	 * 
	 * @param value
	 *            the value to be hashed.
	 * @return a hash value of the input value
	 */
	public static int hash(final float value)
	{
		return hash0(HASH_SEED, value);
	}

	/**
	 * Generates a hash code for the given input value using {@link #HASH_PRIME} and the given seed. The result will be
	 * <p>
	 * <code>
	 * return HASH_PRIME * seed + value;
	 * </code>
	 * </p>
	 * 
	 * @param seed
	 *            the seed to be used.
	 * @param value
	 *            the value to be hashed.
	 * @return a hash value of the input value
	 */
	public static int hash(final int seed, final float value)
	{
		return hash0(seed, value);
	}

	/**
	 * Generates a hash code for the values in an input array using the given seed.
	 * 
	 * @param seed
	 *            the seed to be used.
	 * @param values
	 *            the values to be hashed.
	 * @return a hash value of the input values.
	 */
	public static int hash(final int seed, final float[] values)
	{
		if (null == values)
			return 0;

		int result = seed;
		for (final float value : values)
			result = hash0(result, value);

		return result;
	}

	/**
	 * Generates a hash code for a sequence of input values. The hash code is generated as if all the input values were
	 * placed into an array.
	 * 
	 * @param seed
	 *            the seed to be used.
	 * @param value
	 *            the first value to be hashed.
	 * @param values
	 *            the rest values to be hashed.
	 * @return a hash value of the sequence of input values.
	 */
	public static int hashFloats(final int seed, final float value, final float... values)
	{
		return hash(hash0(seed, value), values);
	}

	/**
	 * Generates a hash code for the given input value using {@link #HASH_PRIME} and the given seed. The result will be
	 * <p>
	 * <code>
	 * return HASH_PRIME * seed + value;
	 * </code>
	 * </p>
	 * 
	 * @param seed
	 *            the seed to be used.
	 * @param value
	 *            the value to be hashed.
	 * @return a hash value of the input value
	 */
	private static int hash0(final int seed, final double value)
	{
		return hash0(seed, Double.doubleToLongBits(value));
	}

	/**
	 * Generates a hash code for the given input value using the {@link #HASH_SEED}.
	 * 
	 * @param value
	 *            the value to be hashed.
	 * @return a hash value of the input value
	 */
	public static int hash(final double value)
	{
		return hash0(HASH_SEED, value);
	}

	/**
	 * Generates a hash code for the given input value using {@link #HASH_PRIME} and the given seed. The result will be
	 * <p>
	 * <code>
	 * return HASH_PRIME * seed + value;
	 * </code>
	 * </p>
	 * 
	 * @param seed
	 *            the seed to be used.
	 * @param value
	 *            the value to be hashed.
	 * @return a hash value of the input value
	 */
	public static int hash(final int seed, final double value)
	{
		return hash0(seed, value);
	}

	/**
	 * Generates a hash code for the values in an input array using the given seed.
	 * 
	 * @param seed
	 *            the seed to be used.
	 * @param values
	 *            the values to be hashed.
	 * @return a hash value of the input values.
	 */
	public static int hash(final int seed, final double[] values)
	{
		if (null == values)
			return 0;

		int result = seed;
		for (final double value : values)
			result = hash0(result, value);

		return result;
	}

	/**
	 * Generates a hash code for a sequence of input values. The hash code is generated as if all the input values were
	 * placed into an array.
	 * 
	 * @param seed
	 *            the seed to be used.
	 * @param value
	 *            the first value to be hashed.
	 * @param values
	 *            the rest values to be hashed.
	 * @return a hash value of the sequence of input values.
	 */
	public static int hashDoubles(final int seed, final double value, final double... values)
	{
		return hash(hash0(seed, value), values);
	}

	/**
	 * Generates a hash code for the given input value using {@link #HASH_PRIME} and the given seed. The result will be
	 * <p>
	 * <code>
	 * return HASH_PRIME * seed + value;
	 * </code>
	 * </p>
	 * 
	 * @param seed
	 *            the seed to be used.
	 * @param value
	 *            the value to be hashed.
	 * @return a hash value of the input value
	 */
	private static int hash0(final int seed, final boolean value)
	{
		return HASH_PRIME * seed + (value ? 1231 : 1237);
	}

	/**
	 * Generates a hash code for the given input value using the {@link #HASH_SEED}.
	 * 
	 * @param value
	 *            the value to be hashed.
	 * @return a hash value of the input value
	 */
	public static int hash(final boolean value)
	{
		return hash0(HASH_SEED, value);
	}

	/**
	 * Generates a hash code for the given input value using {@link #HASH_PRIME} and the given seed. The result will be
	 * <p>
	 * <code>
	 * return HASH_PRIME * seed + value;
	 * </code>
	 * </p>
	 * 
	 * @param seed
	 *            the seed to be used.
	 * @param value
	 *            the value to be hashed.
	 * @return a hash value of the input value
	 */
	public static int hash(final int seed, final boolean value)
	{
		return hash0(seed, value);
	}

	/**
	 * Generates a hash code for the values in an input array using the given seed.
	 * 
	 * @param seed
	 *            the seed to be used.
	 * @param values
	 *            the values to be hashed.
	 * @return a hash value of the input values.
	 */
	public static int hash(final int seed, final boolean[] values)
	{
		if (null == values)
			return 0;

		int result = seed;
		for (final boolean value : values)
			result = hash0(result, value);

		return result;
	}

	/**
	 * Generates a hash code for a sequence of input values. The hash code is generated as if all the input values were
	 * placed into an array.
	 * 
	 * @param seed
	 *            the seed to be used.
	 * @param value
	 *            the first value to be hashed.
	 * @param values
	 *            the rest values to be hashed.
	 * @return a hash value of the sequence of input values.
	 */
	public static int hashBooleans(final int seed, final boolean value, final boolean... values)
	{
		return hash(hash0(seed, value), values);
	}

	/**
	 * Generates a hash code for the given input value using {@link #HASH_PRIME} and the given seed. The result will be
	 * <p>
	 * <code>
	 * return HASH_PRIME * seed + value;
	 * </code>
	 * </p>
	 * 
	 * @param seed
	 *            the seed to be used.
	 * @param value
	 *            the value to be hashed.
	 * @return a hash value of the input value
	 */
	private static int hash0(final int seed, final Object value)
	{
		return HASH_PRIME * seed + hashCodeOf(value);
	}

	/**
	 * Generates a hash code for the given input value using {@link #HASH_PRIME} and the given seed. The result will be
	 * <p>
	 * <code>
	 * return HASH_PRIME * seed + value;
	 * </code>
	 * </p>
	 * 
	 * @param seed
	 *            the seed to be used.
	 * @param value
	 *            the value to be hashed.
	 * @return a hash value of the input value
	 */
	public static int hash(final int seed, final Object value)
	{
		return hash0(seed, value);
	}

	/**
	 * Generates a hash code for the values in an input array using the given seed.
	 * 
	 * @param seed
	 *            the seed to be used.
	 * @param values
	 *            the values to be hashed.
	 * @return a hash value of the input values.
	 */
	public static int hash(final int seed, final Object[] values)
	{
		if (null == values)
			return 0;

		int result = seed;
		for (final Object value : values)
			result = hash0(result, value);

		return result;
	}

	/**
	 * Generates a hash code for a sequence of input values. The hash code is generated as if all the input values were
	 * placed into an array.
	 * 
	 * @param seed
	 *            the seed to be used.
	 * @param value
	 *            the first value to be hashed.
	 * @param values
	 *            the rest values to be hashed.
	 * @return a hash value of the sequence of input values.
	 */
	public static int hashObjects(final int seed, final Object value, final Object... values)
	{
		return hash(hash0(seed, value), values);
	}

	/**
	 * Generates a hash code for the given input value using the {@link #HASH_SEED}.
	 * 
	 * @param value
	 *            the value to be hashed.
	 * @return a hash value of the input value
	 */
	public static int hash(final Object value)
	{
		return hash0(HASH_SEED, value);
	}

	/**
	 * Generates a hash code for the values in an input array using the {@link #HASH_SEED}.
	 * 
	 * @param values
	 *            the values to be hashed.
	 * @return a hash value of the input values.
	 */
	public static int hash(final Object[] values)
	{
		return hash(HASH_SEED, values);
	}

	/**
	 * Generates a hash code for a sequence of input values using the {@link #HASH_SEED}. The hash code is generated as
	 * if all the input values were placed into an array.
	 * 
	 * @param value
	 *            the first value to be hashed.
	 * @param values
	 *            the rest values to be hashed.
	 * @return a hash value of the sequence of input values.
	 */
	public static int hash(final Object value, final Object... values)
	{
		return hash(hash(value), values);
	}

	/**
	 * Returns the result of calling {@code toString} for a non-{@code null} argument and "{@code null}" for a
	 * {@code null} argument.
	 * 
	 * @param object
	 *            an object
	 * @return the result of calling {@code toString} for a non-{@code null} argument and "{@code null}" for a
	 *         {@code null} argument
	 * @see {@link Object#toString()}, {@link String#valueOf(Object)}
	 */
	public static String toStringOf(final Object object)
	{
		return (null == object ? "null" : object.toString());
	}

	/**
	 * Returns the result of calling {@code toString} on the first argument if the first argument is not {@code null}
	 * and returns the second argument otherwise.
	 * 
	 * @param object
	 *            an object
	 * @param nullDefault
	 *            string to return if the first argument is {@code null}
	 * @return the result of calling {@code toString} on the first argument if it is not {@code null} and the second
	 *         argument otherwise.
	 * @see {@link #toStringOf(Object)}
	 */
	public static String toStringOf(final Object object, final String nullDefault)
	{
		return (null == object ? nullDefault : object.toString());
	}

	/**
	 * <p>
	 * Returns 0 if the arguments are identical and {@code c.compare(a, b)} otherwise. Consequently, if both arguments
	 * are {@code null} 0 is returned.
	 * </p>
	 * <p>
	 * Note that if one of the arguments is {@code null}, a {@code NullPointerException} may or may not be thrown
	 * depending on what ordering policy, if any, the {@link Comparator} chooses to have for {@code null} values.
	 * </p>
	 * 
	 * @param <T>
	 *            the type of the objects being compared
	 * @param left
	 *            an object
	 * @param right
	 *            an object to be compared with a
	 * @param c
	 *            the {@code Comparator} to compare the first two arguments
	 * @return 0 if the arguments are identical and {@code c.compare(a, b)} otherwise.
	 * @see {@link Comparable}, {@link Comparator}
	 */
	public static <T> int compare(final T left, final T right, final Comparator<? super T> c)
	{
		return (left == right ? 0 : c.compare(left, right));
	}

	/**
	 * Checks that the specified object reference is not {@code null}. This method is designed primarily for doing
	 * parameter validation in methods and constructors, as demonstrated below:
	 * 
	 * <pre>
	 * public Foo(Bar bar)
	 * {
	 * 	this.bar = Objects.nonNull(bar);
	 * }
	 * </pre>
	 * 
	 * @param <T>
	 *            the type of the reference
	 * @param object
	 *            the object reference to check for nullity
	 * @return object if not {@code null}
	 * @throws NullPointerException
	 *             if {@code obj} is {@code null}
	 */
	public static <T> T nonNull(final T object)
	{
		if (null == object)
		{
			throw new NullPointerException();
		}
		return object;
	}

	/**
	 * Checks that the specified object reference is not {@code null} and throws a customized
	 * {@link NullPointerException} if it is. This method is designed primarily for doing parameter validation in
	 * methods and constructors with multiple parameters, as demonstrated below:
	 * 
	 * <pre>
	 * public Foo(Bar bar, Baz baz)
	 * {
	 * 	this.bar = Objects.nonNull(bar, &quot;bar must not be null&quot;);
	 * 	this.baz = Objects.nonNull(baz, &quot;baz must not be null&quot;);
	 * }
	 * </pre>
	 * 
	 * @param <T>
	 *            the type of the reference
	 * @param object
	 *            the object reference to check for nullity
	 * @param message
	 *            detail message to be used in the event that a {@code NullPointerException} is thrown
	 * @return {@code object} if not {@code null}
	 * @throws NullPointerException
	 *             if object is null
	 */
	public static <T> T nonNull(final T object, final String message)
	{
		if (null == object)
		{
			throw new NullPointerException(message);
		}
		return object;
	}

	/**
	 * @param <T>
	 *            the type of the reference
	 * @param object
	 *            the object reference to check for nullity
	 * @param defaultValue
	 *            the default object to be used if the given object variable contains <code>null</code> reference.
	 * @return object if not {@code null}. Otherwise it's the given defaultValue.
	 */
	public static <T> T nullThenUse(final T object, T defaultValue)
	{
		return null == object ? defaultValue : object;
	}

	/**
	 * Builder to build the description of the given object. In other words, it is to build the result of the
	 * {@link java.lang.Object#toString()} method.
	 * 
	 * @author Lee, SeongHyun (Kevin)
	 * @version 0.0.1 (2010-07-05)
	 * @version 0.0.2 (2010-10-30) It does not used {@link IterableToAppendableGlue} any more
	 * @version 0.0.3 (2010-10-31) It uses {@link IterableToAppendableGlue} again.
	 */
	public static final class ToStringBuilder
	{
		/**
		 * ", "
		 */
		public static final String DEFAULT_FIELD_SEPARATOR = ", ";

		/**
		 * "="
		 */
		public static final String DEFAULT_NAME_VALUE_SEPARATOR = "=";

		private final Object object;

		private final String nameValueSeparator;

		private final String fieldSeparator;

		private final List<String> stringList;

		private final IterableToAppendableGlue iterableToAppendableGlue;

		private ToStringBuilder(Object object)
		{
			this(object, DEFAULT_FIELD_SEPARATOR, DEFAULT_NAME_VALUE_SEPARATOR);
		}

		private ToStringBuilder(Object object, String fieldSeparator, String nameValueSeparator)
		{
			/* @formatter:off */
			this.object 					= nonNull(object);
			this.fieldSeparator 			= nonNull(fieldSeparator);
			this.nameValueSeparator			= nonNull(nameValueSeparator);
			this.stringList 				= new ArrayList<String>();
			this.iterableToAppendableGlue	= IterableToAppendableGlue.withoutSeparator();
			/* @formatter:on */
		}

		private String checkName(String name)
		{
			if (Strings.isEmpty(name))
			{
				throw new IllegalArgumentException("The name cannot be null or empty.");
			}
			return name;
		}

		public String getNameValueSeparator()
		{
			return nameValueSeparator;
		}

		public String getFieldSeparator()
		{
			return fieldSeparator;
		}

		/**
		 * Add {@link #fieldSeparator}.
		 * 
		 * @return this {@link ToStringBuilder} object;
		 */
		private ToStringBuilder separator()
		{
			return value0(fieldSeparator);
		}

		public ToStringBuilder add(String name, boolean value) throws IllegalArgumentException
		{
			/* @formatter:off */
			return value0(checkName(name))
					.value0(nameValueSeparator)
					.value0(String.valueOf(value))
					.separator();
			/* @formatter:on */
		}

		public ToStringBuilder add(String name, int value) throws IllegalArgumentException
		{
			/* @formatter:off */
			return value0(checkName(name))
					.value0(nameValueSeparator)
					.value0(String.valueOf(value))
					.separator();
			/* @formatter:on */
		}

		public ToStringBuilder add(String name, byte value) throws IllegalArgumentException
		{
			/* @formatter:off */
			return value0(checkName(name))
					.value0(nameValueSeparator)
					.value0(String.valueOf(value))
					.separator();
			/* @formatter:on */
		}

		public ToStringBuilder add(String name, short value) throws IllegalArgumentException
		{
			/* @formatter:off */
			return value0(checkName(name))
					.value0(nameValueSeparator)
					.value0(String.valueOf(value))
					.separator();
			/* @formatter:on */
		}

		public ToStringBuilder add(String name, long value) throws IllegalArgumentException
		{
			/* @formatter:off */
			return value0(checkName(name))
					.value0(nameValueSeparator)
					.value0(String.valueOf(value))
					.separator();
			/* @formatter:on */
		}

		public ToStringBuilder add(String name, float value) throws IllegalArgumentException
		{
			/* @formatter:off */
			return value0(checkName(name))
					.value0(nameValueSeparator)
					.value0(String.valueOf(value))
					.separator();
			/* @formatter:on */
		}

		public ToStringBuilder add(String name, double value) throws IllegalArgumentException
		{
			/* @formatter:off */
			return value0(checkName(name))
					.value0(nameValueSeparator)
					.value0(String.valueOf(value))
					.separator();
			/* @formatter:on */
		}

		public ToStringBuilder add(String name, Object value) throws IllegalArgumentException
		{
			/* @formatter:off */
			return value0(checkName(name))
					.value0(nameValueSeparator)
					.value0(String.valueOf(value))
					.separator();
			/* @formatter:on */
		}

		private ToStringBuilder value0(String value)
		{
			stringList.add(value);
			return this;
		}

		/**
		 * Add the given value followed by the value of {@link #getFieldSeparator()} object to this
		 * {@link ToStringBuilder}.
		 * 
		 * @param value
		 *            the value to be added.
		 * @return this {@link ToStringBuilder} object.
		 */
		public ToStringBuilder value(String value)
		{
			return value0(value).separator();
		}

		/**
		 * Add the given value to this {@link ToStringBuilder} object without having the value of
		 * {@link #getFieldSeparator()} at the end of the value.
		 * 
		 * @param value
		 *            the value to be added.
		 * @return this {@link ToStringBuilder} object
		 */
		public ToStringBuilder valueWithNoSeparator(String value)
		{
			return value0(value);
		}

		/**
		 * Add a new line (\n) to this {@link ToStringBuilder} object.
		 * 
		 * @return this {@link ToStringBuilder} object.
		 */
		public ToStringBuilder newLine()
		{
			return value0("\n");
		}

		@Override
		public String toString()
		{
			if (!stringList.isEmpty() && fieldSeparator.equals(stringList.get(stringList.size() - 1)))
			{
				stringList.remove(stringList.size() - 1);
			}
			/* @formatter:off */
			return iterableToAppendableGlue
					.glue(new StringBuilder(object
											.getClass()
											.getSimpleName())
											.append("{"),
						  stringList)
						  .append("}")
						  .toString();
			/* @formatter:on */
		}
	}

	public static ToStringBuilder toStringBuilder(Object object)
	{
		return new ToStringBuilder(object);
	}

	public static ToStringBuilder toStringBuilder(Object object, String fieldSeparator, String keyValueSeparator)
	{
		return new ToStringBuilder(object, fieldSeparator, keyValueSeparator);
	}
}