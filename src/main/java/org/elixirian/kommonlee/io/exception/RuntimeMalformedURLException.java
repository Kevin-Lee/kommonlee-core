/**
 *
 */
package org.elixirian.kommonlee.io.exception;

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
 * @version 0.0.1 (2012-07-10)
 */
public class RuntimeMalformedURLException extends NetworkAddressException
{
	private static final long serialVersionUID = 1L;

	public RuntimeMalformedURLException()
	{
	}

	public RuntimeMalformedURLException(final String message, final Throwable cause)
	{
		super(message, cause);
	}

	public RuntimeMalformedURLException(final String message)
	{
		super(message);
	}

	public RuntimeMalformedURLException(final Throwable cause)
	{
		super(cause);
	}
}
