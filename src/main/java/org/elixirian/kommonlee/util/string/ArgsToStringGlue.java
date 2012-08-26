/**
 * 
 */
package org.elixirian.kommonlee.util.string;

/**
 * <pre>
 *     ___  _____                                              _____
 *    /   \/    / ______ __________________  ______ __ ______ /    /   ______  ______  
 *   /        / _/ __  // /  /   / /  /   /_/ __  // //     //    /   /  ___ \/  ___ \ 
 *  /        \ /  /_/ _/  _  _  /  _  _  //  /_/ _/   __   //    /___/  _____/  _____/
 * /____/\____\/_____//__//_//_/__//_//_/ /_____//___/ /__//________/\_____/ \_____/
 * </pre>
 * 
 * <pre>
 *     ___  _____                                _____
 *    /   \/    /_________  ___ ____ __ ______  /    /   ______  ______
 *   /        / /  ___ \  \/  //___// //     / /    /   /  ___ \/  ___ \
 *  /        \ /  _____/\    //   //   __   / /    /___/  _____/  _____/
 * /____/\____\\_____/   \__//___//___/ /__/ /________/\_____/ \_____/
 * </pre>
 * 
 * @author Lee, SeongHyun (Kevin)
 * @version 0.0.1 (2010-11-14)
 */
public interface ArgsToStringGlue<E> extends ToStringGlue<E[]>
{
	@Override
	String glue(E[] values);

	<T extends E> String glue(T value1, T value2);

	<T extends E> String glue(T value1, T value2, T value3);

	<T extends E> String glue(T value1, T value2, T value3, T value4);

	<T extends E> String glue(T value1, T value2, T value3, T value4, T value5);

	<T extends E> String glue(T value1, T value2, T value3, T value4, T value5, T... rest);
}