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

import java.util.Map;
import java.util.Map.Entry;

import org.elixirian.kommonlee.type.Pair;
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
 * @param <K>
 *          Key
 * @param <E>
 *          Element
 * @param <T>
 *          Input Map type
 * @param <NK>
 *          New Key after function applied.
 * @param <NE>
 *          New Value type after the function applied.
 * @param <F>
 *          Function to map Key and Value.
 * @param <R>
 *          Return Map type
 */
public class MapToMapWithNewKeyNewValueMapper<K, E, T extends Map<? extends K, ? extends E>, NK, NE, F extends Function2<? super K, ? super E, Pair<NK, NE>>, R extends Map<NK, NE>>
    implements Function2<T, F, R>
{
  private final MapCreator<NK, NE, ? extends R> mapCreator;

  public <MC extends MapCreator<NK, NE, ? extends R>> MapToMapWithNewKeyNewValueMapper(final MC mapCreator)
  {
    this.mapCreator = mapCreator;
  }

  @Override
  public R apply(final T inputMap, final F function)
  {
    final R result = mapCreator.createMap();
    for (final Entry<? extends K, ? extends E> entry : inputMap.entrySet())
    {
      final Pair<NK, NE> mappedKeyValuepPair = function.apply(entry.getKey(), entry.getValue());
      result.put(mappedKeyValuepPair.getValue1(), mappedKeyValuepPair.getValue2());
    }
    return result;
  }

  /* @formatter:off */
  public static <K, E, T extends Map<? extends K, ? extends E>,
                 NK,
                 NE,
                 F extends Function2<? super K, ? super E, Pair<NK, NE>>,
                 R extends Map<NK, NE>,
                 MC extends MapCreator<NK, NE, ? extends R>>
    MapToMapWithNewKeyNewValueMapper<K, E, T, NK, NE, F, R> newInstance(final MC mapCreator)
  {
    return new MapToMapWithNewKeyNewValueMapper<K, E, T, NK, NE, F, R>(mapCreator);
  }
  /* @formatter:on */
}