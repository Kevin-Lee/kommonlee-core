/**
 * This project is licensed under the Apache License, Version 2.0
 * if the following condition is met:
 * (otherwise it cannot be used by anyone but the author, Kevin, only)
 *
 * The original KommonLee project is owned by Lee, Seong Hyun (Kevin).
 *
 * -What does it mean to you?
 * Nothing, unless you want to take the ownership of
 * "the original project" (not yours or forked & modified one).
 * You are free to use it for both non-commercial and commercial projects
 * and free to modify it as the Apache License allows.
 *
 * -So why is this condition necessary?
 * It is only to protect the original project (See the case of Java).
 *
 *
 * Copyright 2009 Lee, Seong Hyun (Kevin)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.elixirian.kommonlee.functional.collect;

import java.util.Collection;

import org.elixirian.kommonlee.type.functional.Function1;
import org.elixirian.kommonlee.type.functional.Function2;

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
 * @version 0.0.1 (2011-07-23)
 * @param <E>
 *          source element type
 * @param <NE>
 *          The mapped type from the type E
 * @param <F>
 *          Function object to map the source type E to create the type NE
 * @param <R>
 *          Collection containing all the mapped objects.
 */
public class ArrayToCollectionMapper<E, NE, F extends Function1<? super E, NE>, R extends Collection<NE>> implements
    Function2<E[], F, R>
{
  private final CollectionCreator<NE, ? extends R> collectionCreator;

  protected <CC extends CollectionCreator<NE, ? extends R>> ArrayToCollectionMapper(final CC collectionCreator)
  {
    this.collectionCreator = collectionCreator;
  }

  @Override
  public R apply(final E[] source, final F function)
  {
    final R result = collectionCreator.createCollection();
    for (final E element : source)
    {
      result.add(function.apply(element));
    }
    return result;
  }

  /* @formatter:off */
  public static <E,
                 NE,
                 F extends Function1<? super E, NE>,
                 R extends Collection<NE>,
                 CC extends CollectionCreator<NE, ? extends R>>
    ArrayToCollectionMapper<E, NE, F, R>
    newInstance(final CC collectionCreator)
  {
    return new ArrayToCollectionMapper<E, NE, F, R>(collectionCreator);
  }
  /* @formatter:on */
}