/**
 *
 */
package org.elixirian.kommonlee.reflect;

import static org.elixirian.kommonlee.test.CommonTestHelper.*;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.elixirian.kommonlee.test.CommonTestHelper.Accessibility;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Lee, SeongHyun (Kevin)
 * @version 0.0.1 (2010-09-19)
 */
public class ClassesTest
{
  private static class FirstSuperClass
  {
  }

  private static class SecondSuperClass extends FirstSuperClass
  {
  }

  private static class ThirdSuperClass extends SecondSuperClass
  {
  }

  private static class SubClass extends ThirdSuperClass
  {
  }

  @Target({ ElementType.TYPE })
  @Retention(RetentionPolicy.RUNTIME)
  private static @interface FirstTestAnnotation
  {
  }

  @Target({ ElementType.TYPE })
  @Retention(RetentionPolicy.RUNTIME)
  private static @interface SecondTestAnnotation
  {
  }

  @SecondTestAnnotation
  private static class FirstClassWithSecondTestAnnotation extends SubClass
  {
  }

  @FirstTestAnnotation
  @SecondTestAnnotation
  private static class SecondClassWithFirstAndSecondTestAnnotations extends FirstClassWithSecondTestAnnotation
  {
  }

  private static class ThirdClassWithNoTestAnnotation extends SecondClassWithFirstAndSecondTestAnnotations
  {
  }

  @SecondTestAnnotation
  private static class FourthClassWithSecondTestAnnotation extends ThirdClassWithNoTestAnnotation
  {
  }

  @FirstTestAnnotation
  private static class SubClassWithFirstTestAnnotation extends FourthClassWithSecondTestAnnotation
  {
  }

  @FirstTestAnnotation
  @SecondTestAnnotation
  private static class SubClassWithFirstAndSecondTestAnnotations extends FourthClassWithSecondTestAnnotation
  {
  }

  private static final List<Class<? super SubClass>> CLASS_LIST_IN_SUB_TO_SUPER_ORDER;
  private static final List<Class<? super SubClass>> CLASS_LIST_IN_SUPER_TO_SUB_ORDER;

  /* @formatter:off */
  private static final List<Class<? super SubClassWithFirstTestAnnotation>>
    CLASS_LIST_OF_SUB_CLASS_WITH_FIRST_TEST_ANNOTATION_IN_SUB_TO_SUPER_ORDER_FOR_SUB_CLASS_WITH_FIRST_TEST_ANNOTATION;
  private static final List<Class<? super SubClassWithFirstTestAnnotation>>
    CLASS_LIST_OF_SUB_CLASS_WITH_FIRST_TEST_ANNOTATION_IN_SUPER_TO_SUB_ORDER_FOR_SUB_CLASS_WITH_FIRST_TEST_ANNOTATION;

  private static final List<Class<? super SubClassWithFirstTestAnnotation>>
    CLASS_LIST_OF_SUB_CLASS_WITH_SECOND_TEST_ANNOTATION_IN_SUB_TO_SUPER_ORDER_FOR_SUB_CLASS_WITH_FIRST_TEST_ANNOTATION;
  private static final List<Class<? super SubClassWithFirstTestAnnotation>>
    CLASS_LIST_OF_SUB_CLASS_WITH_SECOND_TEST_ANNOTATION_IN_SUPER_TO_SUB_ORDER_FOR_SUB_CLASS_WITH_FIRST_TEST_ANNOTATION;

  private static final List<Class<? super SubClassWithFirstAndSecondTestAnnotations>>
    CLASS_LIST_OF_SUB_CLASS_WITH_FIRST_TEST_ANNOTATION_IN_SUB_TO_SUPER_ORDER_FOR_SUB_CLASS_WITH_FIRST_AND_SECOND_TEST_ANNOTATIONS;
  private static final List<Class<? super SubClassWithFirstAndSecondTestAnnotations>>
    CLASS_LIST_OF_SUB_CLASS_WITH_FIRST_TEST_ANNOTATION_IN_SUPER_TO_SUB_ORDER_FOR_SUB_CLASS_WITH_FIRST_AND_SECOND_TEST_ANNOTATIONS;

  private static final List<Class<? super SubClassWithFirstAndSecondTestAnnotations>>
    CLASS_LIST_OF_SUB_CLASS_WITH_SECOND_TEST_ANNOTATION_IN_SUB_TO_SUPER_ORDER_FOR_SUB_CLASS_WITH_FIRST_AND_SECOND_TEST_ANNOTATIONS;
  private static final List<Class<? super SubClassWithFirstAndSecondTestAnnotations>>
    CLASS_LIST_OF_SUB_CLASS_WITH_SECOND_TEST_ANNOTATION_IN_SUPER_TO_SUB_ORDER_FOR_SUB_CLASS_WITH_FIRST_AND_SECOND_TEST_ANNOTATIONS;

  private static final List<Class<? super SubClassWithFirstTestAnnotation>>
    CLASS_LIST_OF_SUB_CLASS_WITH_FIRST_OR_SECOND_TEST_ANNOTATION_IN_SUB_TO_SUPER_ORDER_FOR_SUB_CLASS_WITH_FIRST_TEST_ANNOTATION;
  private static final List<Class<? super SubClassWithFirstTestAnnotation>>
    CLASS_LIST_OF_SUB_CLASS_WITH_FIRST_OR_SECOND_TEST_ANNOTATION_IN_SUPER_TO_SUB_ORDER_FOR_SUB_CLASS_WITH_FIRST_TEST_ANNOTATION;

  private static final List<Class<? super SubClassWithFirstAndSecondTestAnnotations>>
    CLASS_LIST_OF_SUB_CLASS_WITH_FIRST_OR_SECOND_TEST_ANNOTATION_IN_SUB_TO_SUPER_ORDER_FOR_SUB_CLASS_WITH_FIRST_AND_SECOND_TEST_ANNOTATIONS;
  private static final List<Class<? super SubClassWithFirstAndSecondTestAnnotations>>
    CLASS_LIST_OF_SUB_CLASS_WITH_FIRST_OR_SECOND_TEST_ANNOTATION_IN_SUPER_TO_SUB_ORDER_FOR_SUB_CLASS_WITH_FIRST_AND_SECOND_TEST_ANNOTATIONS;
  /* @formatter:on */

  static
  {
    CLASS_LIST_IN_SUB_TO_SUPER_ORDER = newArrayList();
    CLASS_LIST_IN_SUB_TO_SUPER_ORDER.add(SubClass.class);
    CLASS_LIST_IN_SUB_TO_SUPER_ORDER.add(ThirdSuperClass.class);
    CLASS_LIST_IN_SUB_TO_SUPER_ORDER.add(SecondSuperClass.class);
    CLASS_LIST_IN_SUB_TO_SUPER_ORDER.add(FirstSuperClass.class);

    CLASS_LIST_IN_SUPER_TO_SUB_ORDER = newArrayList();
    CLASS_LIST_IN_SUPER_TO_SUB_ORDER.add(FirstSuperClass.class);
    CLASS_LIST_IN_SUPER_TO_SUB_ORDER.add(SecondSuperClass.class);
    CLASS_LIST_IN_SUPER_TO_SUB_ORDER.add(ThirdSuperClass.class);
    CLASS_LIST_IN_SUPER_TO_SUB_ORDER.add(SubClass.class);

    /* @FirstTestAnnotation for SubClassWithFirstTestAnnotation */
    CLASS_LIST_OF_SUB_CLASS_WITH_FIRST_TEST_ANNOTATION_IN_SUB_TO_SUPER_ORDER_FOR_SUB_CLASS_WITH_FIRST_TEST_ANNOTATION =
      newArrayList();
    CLASS_LIST_OF_SUB_CLASS_WITH_FIRST_TEST_ANNOTATION_IN_SUB_TO_SUPER_ORDER_FOR_SUB_CLASS_WITH_FIRST_TEST_ANNOTATION.add(SubClassWithFirstTestAnnotation.class);
    CLASS_LIST_OF_SUB_CLASS_WITH_FIRST_TEST_ANNOTATION_IN_SUB_TO_SUPER_ORDER_FOR_SUB_CLASS_WITH_FIRST_TEST_ANNOTATION.add(SecondClassWithFirstAndSecondTestAnnotations.class);

    CLASS_LIST_OF_SUB_CLASS_WITH_FIRST_TEST_ANNOTATION_IN_SUPER_TO_SUB_ORDER_FOR_SUB_CLASS_WITH_FIRST_TEST_ANNOTATION =
      newArrayList();
    CLASS_LIST_OF_SUB_CLASS_WITH_FIRST_TEST_ANNOTATION_IN_SUPER_TO_SUB_ORDER_FOR_SUB_CLASS_WITH_FIRST_TEST_ANNOTATION.add(SecondClassWithFirstAndSecondTestAnnotations.class);
    CLASS_LIST_OF_SUB_CLASS_WITH_FIRST_TEST_ANNOTATION_IN_SUPER_TO_SUB_ORDER_FOR_SUB_CLASS_WITH_FIRST_TEST_ANNOTATION.add(SubClassWithFirstTestAnnotation.class);

    /* @SecondTestAnnotation for SubClassWithFirstTestAnnotation */
    CLASS_LIST_OF_SUB_CLASS_WITH_SECOND_TEST_ANNOTATION_IN_SUB_TO_SUPER_ORDER_FOR_SUB_CLASS_WITH_FIRST_TEST_ANNOTATION =
      newArrayList();
    CLASS_LIST_OF_SUB_CLASS_WITH_SECOND_TEST_ANNOTATION_IN_SUB_TO_SUPER_ORDER_FOR_SUB_CLASS_WITH_FIRST_TEST_ANNOTATION.add(FourthClassWithSecondTestAnnotation.class);
    CLASS_LIST_OF_SUB_CLASS_WITH_SECOND_TEST_ANNOTATION_IN_SUB_TO_SUPER_ORDER_FOR_SUB_CLASS_WITH_FIRST_TEST_ANNOTATION.add(SecondClassWithFirstAndSecondTestAnnotations.class);
    CLASS_LIST_OF_SUB_CLASS_WITH_SECOND_TEST_ANNOTATION_IN_SUB_TO_SUPER_ORDER_FOR_SUB_CLASS_WITH_FIRST_TEST_ANNOTATION.add(FirstClassWithSecondTestAnnotation.class);

    CLASS_LIST_OF_SUB_CLASS_WITH_SECOND_TEST_ANNOTATION_IN_SUPER_TO_SUB_ORDER_FOR_SUB_CLASS_WITH_FIRST_TEST_ANNOTATION =
      newArrayList();
    CLASS_LIST_OF_SUB_CLASS_WITH_SECOND_TEST_ANNOTATION_IN_SUPER_TO_SUB_ORDER_FOR_SUB_CLASS_WITH_FIRST_TEST_ANNOTATION.add(FirstClassWithSecondTestAnnotation.class);
    CLASS_LIST_OF_SUB_CLASS_WITH_SECOND_TEST_ANNOTATION_IN_SUPER_TO_SUB_ORDER_FOR_SUB_CLASS_WITH_FIRST_TEST_ANNOTATION.add(SecondClassWithFirstAndSecondTestAnnotations.class);
    CLASS_LIST_OF_SUB_CLASS_WITH_SECOND_TEST_ANNOTATION_IN_SUPER_TO_SUB_ORDER_FOR_SUB_CLASS_WITH_FIRST_TEST_ANNOTATION.add(FourthClassWithSecondTestAnnotation.class);

    /* @FirstTestAnnotation for SubClassWithFirstAndSecondTestAnnotations */
    CLASS_LIST_OF_SUB_CLASS_WITH_FIRST_TEST_ANNOTATION_IN_SUB_TO_SUPER_ORDER_FOR_SUB_CLASS_WITH_FIRST_AND_SECOND_TEST_ANNOTATIONS =
      newArrayList();
    CLASS_LIST_OF_SUB_CLASS_WITH_FIRST_TEST_ANNOTATION_IN_SUB_TO_SUPER_ORDER_FOR_SUB_CLASS_WITH_FIRST_AND_SECOND_TEST_ANNOTATIONS.add(SubClassWithFirstAndSecondTestAnnotations.class);
    CLASS_LIST_OF_SUB_CLASS_WITH_FIRST_TEST_ANNOTATION_IN_SUB_TO_SUPER_ORDER_FOR_SUB_CLASS_WITH_FIRST_AND_SECOND_TEST_ANNOTATIONS.add(SecondClassWithFirstAndSecondTestAnnotations.class);

    CLASS_LIST_OF_SUB_CLASS_WITH_FIRST_TEST_ANNOTATION_IN_SUPER_TO_SUB_ORDER_FOR_SUB_CLASS_WITH_FIRST_AND_SECOND_TEST_ANNOTATIONS =
      newArrayList();
    CLASS_LIST_OF_SUB_CLASS_WITH_FIRST_TEST_ANNOTATION_IN_SUPER_TO_SUB_ORDER_FOR_SUB_CLASS_WITH_FIRST_AND_SECOND_TEST_ANNOTATIONS.add(SecondClassWithFirstAndSecondTestAnnotations.class);
    CLASS_LIST_OF_SUB_CLASS_WITH_FIRST_TEST_ANNOTATION_IN_SUPER_TO_SUB_ORDER_FOR_SUB_CLASS_WITH_FIRST_AND_SECOND_TEST_ANNOTATIONS.add(SubClassWithFirstAndSecondTestAnnotations.class);

    /* @SecondTestAnnotation for SubClassWithFirstAndSecondTestAnnotations */
    CLASS_LIST_OF_SUB_CLASS_WITH_SECOND_TEST_ANNOTATION_IN_SUB_TO_SUPER_ORDER_FOR_SUB_CLASS_WITH_FIRST_AND_SECOND_TEST_ANNOTATIONS =
      newArrayList();
    CLASS_LIST_OF_SUB_CLASS_WITH_SECOND_TEST_ANNOTATION_IN_SUB_TO_SUPER_ORDER_FOR_SUB_CLASS_WITH_FIRST_AND_SECOND_TEST_ANNOTATIONS.add(SubClassWithFirstAndSecondTestAnnotations.class);
    CLASS_LIST_OF_SUB_CLASS_WITH_SECOND_TEST_ANNOTATION_IN_SUB_TO_SUPER_ORDER_FOR_SUB_CLASS_WITH_FIRST_AND_SECOND_TEST_ANNOTATIONS.add(FourthClassWithSecondTestAnnotation.class);
    CLASS_LIST_OF_SUB_CLASS_WITH_SECOND_TEST_ANNOTATION_IN_SUB_TO_SUPER_ORDER_FOR_SUB_CLASS_WITH_FIRST_AND_SECOND_TEST_ANNOTATIONS.add(SecondClassWithFirstAndSecondTestAnnotations.class);
    CLASS_LIST_OF_SUB_CLASS_WITH_SECOND_TEST_ANNOTATION_IN_SUB_TO_SUPER_ORDER_FOR_SUB_CLASS_WITH_FIRST_AND_SECOND_TEST_ANNOTATIONS.add(FirstClassWithSecondTestAnnotation.class);

    CLASS_LIST_OF_SUB_CLASS_WITH_SECOND_TEST_ANNOTATION_IN_SUPER_TO_SUB_ORDER_FOR_SUB_CLASS_WITH_FIRST_AND_SECOND_TEST_ANNOTATIONS =
      newArrayList();
    CLASS_LIST_OF_SUB_CLASS_WITH_SECOND_TEST_ANNOTATION_IN_SUPER_TO_SUB_ORDER_FOR_SUB_CLASS_WITH_FIRST_AND_SECOND_TEST_ANNOTATIONS.add(FirstClassWithSecondTestAnnotation.class);
    CLASS_LIST_OF_SUB_CLASS_WITH_SECOND_TEST_ANNOTATION_IN_SUPER_TO_SUB_ORDER_FOR_SUB_CLASS_WITH_FIRST_AND_SECOND_TEST_ANNOTATIONS.add(SecondClassWithFirstAndSecondTestAnnotations.class);
    CLASS_LIST_OF_SUB_CLASS_WITH_SECOND_TEST_ANNOTATION_IN_SUPER_TO_SUB_ORDER_FOR_SUB_CLASS_WITH_FIRST_AND_SECOND_TEST_ANNOTATIONS.add(FourthClassWithSecondTestAnnotation.class);
    CLASS_LIST_OF_SUB_CLASS_WITH_SECOND_TEST_ANNOTATION_IN_SUPER_TO_SUB_ORDER_FOR_SUB_CLASS_WITH_FIRST_AND_SECOND_TEST_ANNOTATIONS.add(SubClassWithFirstAndSecondTestAnnotations.class);

    /* @FirstTestAnnotation or @SecondTestAnnotation for SubClassWithFirstTestAnnotation */
    CLASS_LIST_OF_SUB_CLASS_WITH_FIRST_OR_SECOND_TEST_ANNOTATION_IN_SUB_TO_SUPER_ORDER_FOR_SUB_CLASS_WITH_FIRST_TEST_ANNOTATION =
      newArrayList();
    CLASS_LIST_OF_SUB_CLASS_WITH_FIRST_OR_SECOND_TEST_ANNOTATION_IN_SUB_TO_SUPER_ORDER_FOR_SUB_CLASS_WITH_FIRST_TEST_ANNOTATION.add(SubClassWithFirstTestAnnotation.class);
    CLASS_LIST_OF_SUB_CLASS_WITH_FIRST_OR_SECOND_TEST_ANNOTATION_IN_SUB_TO_SUPER_ORDER_FOR_SUB_CLASS_WITH_FIRST_TEST_ANNOTATION.add(FourthClassWithSecondTestAnnotation.class);
    CLASS_LIST_OF_SUB_CLASS_WITH_FIRST_OR_SECOND_TEST_ANNOTATION_IN_SUB_TO_SUPER_ORDER_FOR_SUB_CLASS_WITH_FIRST_TEST_ANNOTATION.add(SecondClassWithFirstAndSecondTestAnnotations.class);
    CLASS_LIST_OF_SUB_CLASS_WITH_FIRST_OR_SECOND_TEST_ANNOTATION_IN_SUB_TO_SUPER_ORDER_FOR_SUB_CLASS_WITH_FIRST_TEST_ANNOTATION.add(FirstClassWithSecondTestAnnotation.class);

    CLASS_LIST_OF_SUB_CLASS_WITH_FIRST_OR_SECOND_TEST_ANNOTATION_IN_SUPER_TO_SUB_ORDER_FOR_SUB_CLASS_WITH_FIRST_TEST_ANNOTATION =
      newArrayList();
    CLASS_LIST_OF_SUB_CLASS_WITH_FIRST_OR_SECOND_TEST_ANNOTATION_IN_SUPER_TO_SUB_ORDER_FOR_SUB_CLASS_WITH_FIRST_TEST_ANNOTATION.add(FirstClassWithSecondTestAnnotation.class);
    CLASS_LIST_OF_SUB_CLASS_WITH_FIRST_OR_SECOND_TEST_ANNOTATION_IN_SUPER_TO_SUB_ORDER_FOR_SUB_CLASS_WITH_FIRST_TEST_ANNOTATION.add(SecondClassWithFirstAndSecondTestAnnotations.class);
    CLASS_LIST_OF_SUB_CLASS_WITH_FIRST_OR_SECOND_TEST_ANNOTATION_IN_SUPER_TO_SUB_ORDER_FOR_SUB_CLASS_WITH_FIRST_TEST_ANNOTATION.add(FourthClassWithSecondTestAnnotation.class);
    CLASS_LIST_OF_SUB_CLASS_WITH_FIRST_OR_SECOND_TEST_ANNOTATION_IN_SUPER_TO_SUB_ORDER_FOR_SUB_CLASS_WITH_FIRST_TEST_ANNOTATION.add(SubClassWithFirstTestAnnotation.class);

    /* @FirstTestAnnotation or @SecondTestAnnotation for SubClassWithFirstAndSecondTestAnnotations */
    CLASS_LIST_OF_SUB_CLASS_WITH_FIRST_OR_SECOND_TEST_ANNOTATION_IN_SUB_TO_SUPER_ORDER_FOR_SUB_CLASS_WITH_FIRST_AND_SECOND_TEST_ANNOTATIONS =
      newArrayList();
    CLASS_LIST_OF_SUB_CLASS_WITH_FIRST_OR_SECOND_TEST_ANNOTATION_IN_SUB_TO_SUPER_ORDER_FOR_SUB_CLASS_WITH_FIRST_AND_SECOND_TEST_ANNOTATIONS.add(SubClassWithFirstAndSecondTestAnnotations.class);
    CLASS_LIST_OF_SUB_CLASS_WITH_FIRST_OR_SECOND_TEST_ANNOTATION_IN_SUB_TO_SUPER_ORDER_FOR_SUB_CLASS_WITH_FIRST_AND_SECOND_TEST_ANNOTATIONS.add(FourthClassWithSecondTestAnnotation.class);
    CLASS_LIST_OF_SUB_CLASS_WITH_FIRST_OR_SECOND_TEST_ANNOTATION_IN_SUB_TO_SUPER_ORDER_FOR_SUB_CLASS_WITH_FIRST_AND_SECOND_TEST_ANNOTATIONS.add(SecondClassWithFirstAndSecondTestAnnotations.class);
    CLASS_LIST_OF_SUB_CLASS_WITH_FIRST_OR_SECOND_TEST_ANNOTATION_IN_SUB_TO_SUPER_ORDER_FOR_SUB_CLASS_WITH_FIRST_AND_SECOND_TEST_ANNOTATIONS.add(FirstClassWithSecondTestAnnotation.class);

    CLASS_LIST_OF_SUB_CLASS_WITH_FIRST_OR_SECOND_TEST_ANNOTATION_IN_SUPER_TO_SUB_ORDER_FOR_SUB_CLASS_WITH_FIRST_AND_SECOND_TEST_ANNOTATIONS =
      newArrayList();
    CLASS_LIST_OF_SUB_CLASS_WITH_FIRST_OR_SECOND_TEST_ANNOTATION_IN_SUPER_TO_SUB_ORDER_FOR_SUB_CLASS_WITH_FIRST_AND_SECOND_TEST_ANNOTATIONS.add(FirstClassWithSecondTestAnnotation.class);
    CLASS_LIST_OF_SUB_CLASS_WITH_FIRST_OR_SECOND_TEST_ANNOTATION_IN_SUPER_TO_SUB_ORDER_FOR_SUB_CLASS_WITH_FIRST_AND_SECOND_TEST_ANNOTATIONS.add(SecondClassWithFirstAndSecondTestAnnotations.class);
    CLASS_LIST_OF_SUB_CLASS_WITH_FIRST_OR_SECOND_TEST_ANNOTATION_IN_SUPER_TO_SUB_ORDER_FOR_SUB_CLASS_WITH_FIRST_AND_SECOND_TEST_ANNOTATIONS.add(FourthClassWithSecondTestAnnotation.class);
    CLASS_LIST_OF_SUB_CLASS_WITH_FIRST_OR_SECOND_TEST_ANNOTATION_IN_SUPER_TO_SUB_ORDER_FOR_SUB_CLASS_WITH_FIRST_AND_SECOND_TEST_ANNOTATIONS.add(SubClassWithFirstAndSecondTestAnnotations.class);
  }

  private static <E> ArrayList<Class<? super E>> newArrayList()
  {
    return new ArrayList<Class<? super E>>();
  }

  /**
   * @throws java.lang.Exception
   */
  @BeforeClass
  public static void setUpBeforeClass() throws Exception
  {
  }

  /**
   * @throws java.lang.Exception
   */
  @AfterClass
  public static void tearDownAfterClass() throws Exception
  {
  }

  /**
   * @throws java.lang.Exception
   */
  @Before
  public void setUp() throws Exception
  {
  }

  /**
   * @throws java.lang.Exception
   */
  @After
  public void tearDown() throws Exception
  {
  }

  @Test(expected = IllegalAccessException.class)
  public final void testClasses() throws Exception
  {
    testNotAccessibleConstructor(Classes.class, this, Accessibility.PRIVATE, classArrayOf(), objectArrayOf());
  }

  @Test
  public void testHasAnySuperClassAnnotatedWith()
  {
    @SuppressWarnings({ "boxing", "unchecked" })
    final Boolean actual1_1 =
      Classes.hasAnySuperClassAnnotatedWith(SubClass.class, Object.class, true, FirstTestAnnotation.class);
    assertThat(actual1_1, is(equalTo(Boolean.FALSE)));

    @SuppressWarnings({ "boxing", "unchecked" })
    final Boolean actual1_2 =
      Classes.hasAnySuperClassAnnotatedWith(SubClass.class, Object.class, true, FirstTestAnnotation.class,
          SecondTestAnnotation.class);
    assertThat(actual1_2, is(equalTo(Boolean.FALSE)));

    @SuppressWarnings({ "boxing", "unchecked" })
    final Boolean actual2_1 =
      Classes.hasAnySuperClassAnnotatedWith(FirstClassWithSecondTestAnnotation.class, Object.class, true,
          FirstTestAnnotation.class);
    assertThat(actual2_1, is(equalTo(Boolean.FALSE)));

    @SuppressWarnings({ "boxing", "unchecked" })
    final Boolean actual2_2 =
      Classes.hasAnySuperClassAnnotatedWith(FirstClassWithSecondTestAnnotation.class, Object.class, true,
          SecondTestAnnotation.class);
    assertThat(actual2_2, is(equalTo(Boolean.TRUE)));

    @SuppressWarnings({ "boxing", "unchecked" })
    final Boolean actual2_3 =
      Classes.hasAnySuperClassAnnotatedWith(FirstClassWithSecondTestAnnotation.class, Object.class, true,
          FirstTestAnnotation.class, SecondTestAnnotation.class);
    assertThat(actual2_3, is(equalTo(Boolean.TRUE)));

    @SuppressWarnings({ "boxing", "unchecked" })
    final Boolean actual2_4 =
      Classes.hasAnySuperClassAnnotatedWith(FirstClassWithSecondTestAnnotation.class, Object.class, false,
          FirstTestAnnotation.class);
    assertThat(actual2_4, is(equalTo(Boolean.FALSE)));

    @SuppressWarnings({ "boxing", "unchecked" })
    final Boolean actual2_5 =
      Classes.hasAnySuperClassAnnotatedWith(FirstClassWithSecondTestAnnotation.class, Object.class, false,
          SecondTestAnnotation.class);
    assertThat(actual2_5, is(equalTo(Boolean.FALSE)));

    @SuppressWarnings({ "boxing", "unchecked" })
    final Boolean actual2_6 =
      Classes.hasAnySuperClassAnnotatedWith(FirstClassWithSecondTestAnnotation.class, Object.class, false,
          FirstTestAnnotation.class, SecondTestAnnotation.class);
    assertThat(actual2_6, is(equalTo(Boolean.FALSE)));

    @SuppressWarnings({ "boxing", "unchecked" })
    final Boolean actual3_1 =
      Classes.hasAnySuperClassAnnotatedWith(SecondClassWithFirstAndSecondTestAnnotations.class, Object.class, true,
          FirstTestAnnotation.class);
    assertThat(actual3_1, is(equalTo(Boolean.TRUE)));

    @SuppressWarnings({ "boxing", "unchecked" })
    final Boolean actual3_2 =
      Classes.hasAnySuperClassAnnotatedWith(SecondClassWithFirstAndSecondTestAnnotations.class, Object.class, true,
          FirstTestAnnotation.class);
    assertThat(actual3_2, is(equalTo(Boolean.TRUE)));

    @SuppressWarnings({ "boxing", "unchecked" })
    final Boolean actual3_3 =
      Classes.hasAnySuperClassAnnotatedWith(SecondClassWithFirstAndSecondTestAnnotations.class, Object.class, true,
          SecondTestAnnotation.class);
    assertThat(actual3_3, is(equalTo(Boolean.TRUE)));

    @SuppressWarnings({ "boxing", "unchecked" })
    final Boolean actual3_4 =
      Classes.hasAnySuperClassAnnotatedWith(SecondClassWithFirstAndSecondTestAnnotations.class, Object.class, true,
          FirstTestAnnotation.class, SecondTestAnnotation.class);
    assertThat(actual3_4, is(equalTo(Boolean.TRUE)));

    @SuppressWarnings({ "boxing", "unchecked" })
    final Boolean actual4_1 =
      Classes.hasAnySuperClassAnnotatedWith(ThirdClassWithNoTestAnnotation.class, Object.class, false,
          FirstTestAnnotation.class);
    assertThat(actual4_1, is(equalTo(Boolean.TRUE)));

    @SuppressWarnings({ "boxing", "unchecked" })
    final Boolean actual4_2 =
      Classes.hasAnySuperClassAnnotatedWith(ThirdClassWithNoTestAnnotation.class, Object.class, false,
          SecondTestAnnotation.class);
    assertThat(actual4_2, is(equalTo(Boolean.TRUE)));

    @SuppressWarnings({ "boxing", "unchecked" })
    final Boolean actual4_3 =
      Classes.hasAnySuperClassAnnotatedWith(ThirdClassWithNoTestAnnotation.class, Object.class, false,
          FirstTestAnnotation.class, SecondTestAnnotation.class);
    assertThat(actual4_3, is(equalTo(Boolean.TRUE)));

    @SuppressWarnings({ "boxing", "unchecked" })
    final Boolean actual4_4 =
      Classes.hasAnySuperClassAnnotatedWith(ThirdClassWithNoTestAnnotation.class, Object.class, true,
          FirstTestAnnotation.class);
    assertThat(actual4_4, is(equalTo(Boolean.TRUE)));

    @SuppressWarnings({ "boxing", "unchecked" })
    final Boolean actual4_5 =
      Classes.hasAnySuperClassAnnotatedWith(ThirdClassWithNoTestAnnotation.class, Object.class, true,
          SecondTestAnnotation.class);
    assertThat(actual4_5, is(equalTo(Boolean.TRUE)));

    @SuppressWarnings({ "boxing", "unchecked" })
    final Boolean actual4_6 =
      Classes.hasAnySuperClassAnnotatedWith(ThirdClassWithNoTestAnnotation.class, Object.class, true,
          FirstTestAnnotation.class, SecondTestAnnotation.class);
    assertThat(actual4_6, is(equalTo(Boolean.TRUE)));
  }

  @Test
  public final void testExtractSuperClassesInSubToSuperOrderClassOfTClassOfSBooleanCollectionOfClassOfQsuperT()
  {
    final List<Class<?>> classList = new ArrayList<Class<?>>();
    Classes.extractSuperClassesInSubToSuperOrder(SubClass.class, Object.class, true, classList);
    assertEquals(CLASS_LIST_IN_SUB_TO_SUPER_ORDER.size(), classList.size());
    for (int i = 0, size = CLASS_LIST_IN_SUB_TO_SUPER_ORDER.size(); i < size; i++)
    {
      assertThat(classList.get(i), is(equalTo((Object) CLASS_LIST_IN_SUB_TO_SUPER_ORDER.get(i))));
    }

    final Deque<Class<?>> classDeque = new ArrayDeque<Class<?>>();
    Classes.extractSuperClassesInSubToSuperOrder(SubClass.class, Object.class, true, classDeque);
    assertEquals(CLASS_LIST_IN_SUB_TO_SUPER_ORDER.size(), classDeque.size());
    for (final Class<? super SubClass> aClass : CLASS_LIST_IN_SUB_TO_SUPER_ORDER)
    {
      assertThat(classDeque.pop(), is(equalTo((Object) aClass)));
    }
  }

  @Test
  public final void testExtractSuperClassesInSubToSuperOrderClassOfTClassOfSBoolean()
  {
    final List<Class<?>> classList = Classes.extractSuperClassesInSubToSuperOrder(SubClass.class, Object.class, true);
    assertEquals(CLASS_LIST_IN_SUB_TO_SUPER_ORDER.size(), classList.size());
    for (int i = 0, size = CLASS_LIST_IN_SUB_TO_SUPER_ORDER.size(); i < size; i++)
    {
      assertThat(classList.get(i), is(equalTo((Object) CLASS_LIST_IN_SUB_TO_SUPER_ORDER.get(i))));
    }
  }

  @Test
  public final void testExtractSuperClassesInSuperToSubOrderClassOfTClassOfSBooleanCollectionOfClassOfQsuperT()
  {
    final List<Class<?>> classList = Classes.extractSuperClassesInSuperToSubOrder(SubClass.class, Object.class, true);
    assertEquals(CLASS_LIST_IN_SUPER_TO_SUB_ORDER.size(), classList.size());
    for (int i = 0, size = CLASS_LIST_IN_SUPER_TO_SUB_ORDER.size(); i < size; i++)
    {
      assertThat(classList.get(i), is(equalTo((Object) CLASS_LIST_IN_SUPER_TO_SUB_ORDER.get(i))));
    }

    final Deque<Class<?>> classDeque = new ArrayDeque<Class<?>>();
    Classes.extractSuperClassesInSuperToSubOrder(SubClass.class, Object.class, true, classDeque);
    assertEquals(CLASS_LIST_IN_SUPER_TO_SUB_ORDER.size(), classDeque.size());
    for (int i = 0, size = CLASS_LIST_IN_SUPER_TO_SUB_ORDER.size(); i < size; i++)
    {
      assertThat(classDeque.pop(), is(equalTo((Object) CLASS_LIST_IN_SUPER_TO_SUB_ORDER.get(i))));
    }
  }

  @Test
  public final void testExtractSuperClassesInSuperToSubOrderClassOfTClassOfSBoolean()
  {
    final List<Class<?>> classList = Classes.extractSuperClassesInSuperToSubOrder(SubClass.class, Object.class, true);
    assertEquals(CLASS_LIST_IN_SUPER_TO_SUB_ORDER.size(), classList.size());
    for (int i = 0, size = CLASS_LIST_IN_SUPER_TO_SUB_ORDER.size(); i < size; i++)
    {
      assertThat(classList.get(i), is(equalTo((Object) CLASS_LIST_IN_SUPER_TO_SUB_ORDER.get(i))));
    }
  }

  @Test
  public final void testExtractClassesWithAnnotationsInSubToSuperOrderClassOfTClassOfSBooleanCollectionOfClassOfQsuperTClassOfQextendsAArray0()
  {
    final List<Class<?>> classList1_1 = new ArrayList<Class<?>>();
    Classes.extractClassesWithAnnotationsInSubToSuperOrder(SubClassWithFirstTestAnnotation.class, Object.class, true,
        classList1_1, FirstTestAnnotation.class);
    final int size1_1 =
      CLASS_LIST_OF_SUB_CLASS_WITH_FIRST_TEST_ANNOTATION_IN_SUB_TO_SUPER_ORDER_FOR_SUB_CLASS_WITH_FIRST_TEST_ANNOTATION.size();
    assertEquals(size1_1, classList1_1.size());
    for (int i = 0; i < size1_1; i++)
    {
      assertThat(
          classList1_1.get(i),
          is(equalTo((Object) CLASS_LIST_OF_SUB_CLASS_WITH_FIRST_TEST_ANNOTATION_IN_SUB_TO_SUPER_ORDER_FOR_SUB_CLASS_WITH_FIRST_TEST_ANNOTATION.get(i))));
    }

    final Deque<Class<?>> classDeque1_1 = new ArrayDeque<Class<?>>();
    Classes.extractClassesWithAnnotationsInSubToSuperOrder(SubClassWithFirstTestAnnotation.class, Object.class, true,
        classDeque1_1, FirstTestAnnotation.class);
    assertEquals(size1_1, classDeque1_1.size());
    for (final Class<? super SubClassWithFirstTestAnnotation> aClass : CLASS_LIST_OF_SUB_CLASS_WITH_FIRST_TEST_ANNOTATION_IN_SUB_TO_SUPER_ORDER_FOR_SUB_CLASS_WITH_FIRST_TEST_ANNOTATION)
    {
      assertThat(classDeque1_1.pop(), is(equalTo((Object) aClass)));
    }
  }

  @Test
  public final void testExtractClassesWithAnnotationsInSubToSuperOrderClassOfTClassOfSBooleanCollectionOfClassOfQsuperTClassOfQextendsAArray1()
  {
    /* */
    final List<Class<?>> classList1_2 = new ArrayList<Class<?>>();
    Classes.extractClassesWithAnnotationsInSubToSuperOrder(SubClassWithFirstTestAnnotation.class, Object.class, true,
        classList1_2, SecondTestAnnotation.class);
    final int size1_2 =
      CLASS_LIST_OF_SUB_CLASS_WITH_SECOND_TEST_ANNOTATION_IN_SUB_TO_SUPER_ORDER_FOR_SUB_CLASS_WITH_FIRST_TEST_ANNOTATION.size();
    assertEquals(size1_2, classList1_2.size());
    for (int i = 0; i < size1_2; i++)
    {
      assertThat(
          classList1_2.get(i),
          is(equalTo((Object) CLASS_LIST_OF_SUB_CLASS_WITH_SECOND_TEST_ANNOTATION_IN_SUB_TO_SUPER_ORDER_FOR_SUB_CLASS_WITH_FIRST_TEST_ANNOTATION.get(i))));
    }

    final Deque<Class<?>> classDeque1_2 = new ArrayDeque<Class<?>>();
    Classes.extractClassesWithAnnotationsInSubToSuperOrder(SubClassWithFirstTestAnnotation.class, Object.class, true,
        classDeque1_2, SecondTestAnnotation.class);
    assertEquals(size1_2, classDeque1_2.size());
    for (final Class<? super SubClassWithFirstTestAnnotation> aClass : CLASS_LIST_OF_SUB_CLASS_WITH_SECOND_TEST_ANNOTATION_IN_SUB_TO_SUPER_ORDER_FOR_SUB_CLASS_WITH_FIRST_TEST_ANNOTATION)
    {
      assertThat(classDeque1_2.pop(), is(equalTo((Object) aClass)));
    }
  }

  @Test
  public final void testExtractClassesWithAnnotationsInSubToSuperOrderClassOfTClassOfSBooleanCollectionOfClassOfQsuperTClassOfQextendsAArray2()
  {
    /* */
    final List<Class<?>> classList2_1 = new ArrayList<Class<?>>();
    Classes.extractClassesWithAnnotationsInSubToSuperOrder(SubClassWithFirstAndSecondTestAnnotations.class,
        Object.class, true, classList2_1, FirstTestAnnotation.class);
    final int size2_1 =
      CLASS_LIST_OF_SUB_CLASS_WITH_FIRST_TEST_ANNOTATION_IN_SUB_TO_SUPER_ORDER_FOR_SUB_CLASS_WITH_FIRST_AND_SECOND_TEST_ANNOTATIONS.size();
    assertEquals(size2_1, classList2_1.size());
    for (int i = 0; i < size2_1; i++)
    {
      assertThat(
          classList2_1.get(i),
          is(equalTo((Object) CLASS_LIST_OF_SUB_CLASS_WITH_FIRST_TEST_ANNOTATION_IN_SUB_TO_SUPER_ORDER_FOR_SUB_CLASS_WITH_FIRST_AND_SECOND_TEST_ANNOTATIONS.get(i))));
    }

    final Deque<Class<?>> classDeque2_1 = new ArrayDeque<Class<?>>();
    Classes.extractClassesWithAnnotationsInSubToSuperOrder(SubClassWithFirstAndSecondTestAnnotations.class,
        Object.class, true, classDeque2_1, FirstTestAnnotation.class);
    assertEquals(size2_1, classDeque2_1.size());
    for (final Class<? super SubClassWithFirstAndSecondTestAnnotations> aClass : CLASS_LIST_OF_SUB_CLASS_WITH_FIRST_TEST_ANNOTATION_IN_SUB_TO_SUPER_ORDER_FOR_SUB_CLASS_WITH_FIRST_AND_SECOND_TEST_ANNOTATIONS)
    {
      assertThat(classDeque2_1.pop(), is(equalTo((Object) aClass)));
    }
  }

  @Test
  public final void testExtractClassesWithAnnotationsInSubToSuperOrderClassOfTClassOfSBooleanCollectionOfClassOfQsuperTClassOfQextendsAArray3()
  {
    final List<Class<?>> classList2_2 = new ArrayList<Class<?>>();
    Classes.extractClassesWithAnnotationsInSubToSuperOrder(SubClassWithFirstAndSecondTestAnnotations.class,
        Object.class, true, classList2_2, SecondTestAnnotation.class);
    final int size2_2 =
      CLASS_LIST_OF_SUB_CLASS_WITH_SECOND_TEST_ANNOTATION_IN_SUB_TO_SUPER_ORDER_FOR_SUB_CLASS_WITH_FIRST_AND_SECOND_TEST_ANNOTATIONS.size();
    assertEquals(size2_2, classList2_2.size());
    for (int i = 0; i < size2_2; i++)
    {
      assertThat(
          classList2_2.get(i),
          is(equalTo((Object) CLASS_LIST_OF_SUB_CLASS_WITH_SECOND_TEST_ANNOTATION_IN_SUB_TO_SUPER_ORDER_FOR_SUB_CLASS_WITH_FIRST_AND_SECOND_TEST_ANNOTATIONS.get(i))));
    }

    final Deque<Class<?>> classDeque2_2 = new ArrayDeque<Class<?>>();
    Classes.extractClassesWithAnnotationsInSubToSuperOrder(SubClassWithFirstAndSecondTestAnnotations.class,
        Object.class, true, classDeque2_2, SecondTestAnnotation.class);
    assertEquals(size2_2, classDeque2_2.size());
    for (final Class<? super SubClassWithFirstAndSecondTestAnnotations> aClass : CLASS_LIST_OF_SUB_CLASS_WITH_SECOND_TEST_ANNOTATION_IN_SUB_TO_SUPER_ORDER_FOR_SUB_CLASS_WITH_FIRST_AND_SECOND_TEST_ANNOTATIONS)
    {
      assertThat(classDeque2_2.pop(), is(equalTo((Object) aClass)));
    }
  }

  @SuppressWarnings("unchecked")
  @Test
  public final void testExtractClassesWithAnnotationsInSubToSuperOrderClassOfTClassOfSBooleanCollectionOfClassOfQsuperTClassOfQextendsAArray4()
  {
    /* */
    final List<Class<?>> classList3_1 = new ArrayList<Class<?>>();
    Classes.extractClassesWithAnnotationsInSubToSuperOrder(SubClassWithFirstTestAnnotation.class, Object.class, true,
        classList3_1, FirstTestAnnotation.class, SecondTestAnnotation.class);
    final int size3_1 =
      CLASS_LIST_OF_SUB_CLASS_WITH_FIRST_OR_SECOND_TEST_ANNOTATION_IN_SUB_TO_SUPER_ORDER_FOR_SUB_CLASS_WITH_FIRST_TEST_ANNOTATION.size();
    assertEquals(size3_1, classList3_1.size());
    for (int i = 0; i < size3_1; i++)
    {
      assertThat(
          classList3_1.get(i),
          is(equalTo((Object) CLASS_LIST_OF_SUB_CLASS_WITH_FIRST_OR_SECOND_TEST_ANNOTATION_IN_SUB_TO_SUPER_ORDER_FOR_SUB_CLASS_WITH_FIRST_TEST_ANNOTATION.get(i))));
    }

    final Deque<Class<?>> classDeque3_1 = new ArrayDeque<Class<?>>();
    Classes.extractClassesWithAnnotationsInSubToSuperOrder(SubClassWithFirstTestAnnotation.class, Object.class, true,
        classDeque3_1, FirstTestAnnotation.class, SecondTestAnnotation.class);
    assertEquals(size3_1, classDeque3_1.size());
    for (final Class<? super SubClassWithFirstTestAnnotation> aClass : CLASS_LIST_OF_SUB_CLASS_WITH_FIRST_OR_SECOND_TEST_ANNOTATION_IN_SUB_TO_SUPER_ORDER_FOR_SUB_CLASS_WITH_FIRST_TEST_ANNOTATION)
    {
      assertThat(classDeque3_1.pop(), is(equalTo((Object) aClass)));
    }
  }

  @SuppressWarnings("unchecked")
  @Test
  public final void testExtractClassesWithAnnotationsInSubToSuperOrderClassOfTClassOfSBooleanCollectionOfClassOfQsuperTClassOfQextendsAArray5()
  {
    /* */
    final List<Class<?>> classList3_2 = new ArrayList<Class<?>>();
    Classes.extractClassesWithAnnotationsInSubToSuperOrder(SubClassWithFirstAndSecondTestAnnotations.class,
        Object.class, true, classList3_2, FirstTestAnnotation.class, SecondTestAnnotation.class);
    final int size3_2 =
      CLASS_LIST_OF_SUB_CLASS_WITH_FIRST_OR_SECOND_TEST_ANNOTATION_IN_SUB_TO_SUPER_ORDER_FOR_SUB_CLASS_WITH_FIRST_AND_SECOND_TEST_ANNOTATIONS.size();
    assertEquals(size3_2, classList3_2.size());
    for (int i = 0; i < size3_2; i++)
    {
      assertThat(
          classList3_2.get(i),
          is(equalTo((Object) CLASS_LIST_OF_SUB_CLASS_WITH_FIRST_OR_SECOND_TEST_ANNOTATION_IN_SUB_TO_SUPER_ORDER_FOR_SUB_CLASS_WITH_FIRST_AND_SECOND_TEST_ANNOTATIONS.get(i))));
    }

    final Deque<Class<?>> classDeque3_2 = new ArrayDeque<Class<?>>();
    Classes.extractClassesWithAnnotationsInSubToSuperOrder(SubClassWithFirstAndSecondTestAnnotations.class,
        Object.class, true, classDeque3_2, FirstTestAnnotation.class, SecondTestAnnotation.class);
    assertEquals(size3_2, classDeque3_2.size());
    for (final Class<? super SubClassWithFirstAndSecondTestAnnotations> aClass : CLASS_LIST_OF_SUB_CLASS_WITH_FIRST_OR_SECOND_TEST_ANNOTATION_IN_SUB_TO_SUPER_ORDER_FOR_SUB_CLASS_WITH_FIRST_AND_SECOND_TEST_ANNOTATIONS)
    {
      assertThat(classDeque3_2.pop(), is(equalTo((Object) aClass)));
    }
  }

  @Test
  public final void testExtractClassesWithAnnotationsInSubToSuperOrderClassOfTClassOfSBooleanClassOfQextendsAArray0()
  {
    final List<Class<?>> classList1_1 =
      Classes.extractClassesWithAnnotationsInSubToSuperOrder(SubClassWithFirstTestAnnotation.class, Object.class, true,
          FirstTestAnnotation.class);
    final int size1_1 =
      CLASS_LIST_OF_SUB_CLASS_WITH_FIRST_TEST_ANNOTATION_IN_SUB_TO_SUPER_ORDER_FOR_SUB_CLASS_WITH_FIRST_TEST_ANNOTATION.size();
    assertEquals(size1_1, classList1_1.size());
    for (int i = 0; i < size1_1; i++)
    {
      assertThat(
          classList1_1.get(i),
          is(equalTo((Object) CLASS_LIST_OF_SUB_CLASS_WITH_FIRST_TEST_ANNOTATION_IN_SUB_TO_SUPER_ORDER_FOR_SUB_CLASS_WITH_FIRST_TEST_ANNOTATION.get(i))));
    }

    final Collection<Class<?>> classCollection1_1 =
      Classes.extractClassesWithAnnotationsInSubToSuperOrder(SubClassWithFirstTestAnnotation.class, Object.class, true,
          FirstTestAnnotation.class);

    assertEquals(size1_1, classCollection1_1.size());

    @SuppressWarnings({ "cast", "rawtypes", "unchecked" })
    final Collection<Class<?>> expected =
      (Collection<Class<?>>) ((Collection) CLASS_LIST_OF_SUB_CLASS_WITH_FIRST_TEST_ANNOTATION_IN_SUB_TO_SUPER_ORDER_FOR_SUB_CLASS_WITH_FIRST_TEST_ANNOTATION);

    assertThat(classCollection1_1, is(equalTo(expected)));
  }

  @Test
  public final void testExtractClassesWithAnnotationsInSubToSuperOrderClassOfTClassOfSBooleanClassOfQextendsAArray1()
  {
    /* */
    final List<Class<?>> classList1_2 =
      Classes.extractClassesWithAnnotationsInSubToSuperOrder(SubClassWithFirstTestAnnotation.class, Object.class, true,
          SecondTestAnnotation.class);
    final int size1_2 =
      CLASS_LIST_OF_SUB_CLASS_WITH_SECOND_TEST_ANNOTATION_IN_SUB_TO_SUPER_ORDER_FOR_SUB_CLASS_WITH_FIRST_TEST_ANNOTATION.size();
    assertEquals(size1_2, classList1_2.size());
    for (int i = 0; i < size1_2; i++)
    {
      assertThat(
          classList1_2.get(i),
          is(equalTo((Object) CLASS_LIST_OF_SUB_CLASS_WITH_SECOND_TEST_ANNOTATION_IN_SUB_TO_SUPER_ORDER_FOR_SUB_CLASS_WITH_FIRST_TEST_ANNOTATION.get(i))));
    }

    final Collection<Class<?>> classCollection1_2 =
      Classes.extractClassesWithAnnotationsInSubToSuperOrder(SubClassWithFirstTestAnnotation.class, Object.class, true,
          SecondTestAnnotation.class);
    assertEquals(size1_2, classCollection1_2.size());

    @SuppressWarnings({ "cast", "unchecked", "rawtypes" })
    final Collection<Class<?>> expected =
      (Collection<Class<?>>) ((Collection) CLASS_LIST_OF_SUB_CLASS_WITH_SECOND_TEST_ANNOTATION_IN_SUB_TO_SUPER_ORDER_FOR_SUB_CLASS_WITH_FIRST_TEST_ANNOTATION);

    assertThat(classCollection1_2, is(equalTo(expected)));
  }

  @Test
  public final void testExtractClassesWithAnnotationsInSubToSuperOrderClassOfTClassOfSBooleanClassOfQextendsAArray2()
  {
    /* */
    final List<Class<?>> classList2_1 =
      Classes.extractClassesWithAnnotationsInSubToSuperOrder(SubClassWithFirstAndSecondTestAnnotations.class,
          Object.class, true, FirstTestAnnotation.class);
    final int size2_1 =
      CLASS_LIST_OF_SUB_CLASS_WITH_FIRST_TEST_ANNOTATION_IN_SUB_TO_SUPER_ORDER_FOR_SUB_CLASS_WITH_FIRST_AND_SECOND_TEST_ANNOTATIONS.size();
    assertEquals(size2_1, classList2_1.size());
    for (int i = 0; i < size2_1; i++)
    {
      assertThat(
          classList2_1.get(i),
          is(equalTo((Object) CLASS_LIST_OF_SUB_CLASS_WITH_FIRST_TEST_ANNOTATION_IN_SUB_TO_SUPER_ORDER_FOR_SUB_CLASS_WITH_FIRST_AND_SECOND_TEST_ANNOTATIONS.get(i))));
    }

    final Collection<Class<?>> classCollection2_1 =
      Classes.extractClassesWithAnnotationsInSubToSuperOrder(SubClassWithFirstAndSecondTestAnnotations.class,
          Object.class, true, FirstTestAnnotation.class);
    assertEquals(size2_1, classCollection2_1.size());

    @SuppressWarnings({ "cast", "unchecked", "rawtypes" })
    final Collection<Class<?>> expected =
      (Collection<Class<?>>) ((Collection) CLASS_LIST_OF_SUB_CLASS_WITH_FIRST_TEST_ANNOTATION_IN_SUB_TO_SUPER_ORDER_FOR_SUB_CLASS_WITH_FIRST_AND_SECOND_TEST_ANNOTATIONS);

    assertThat(classCollection2_1, is(equalTo(expected)));
  }

  @Test
  public final void testExtractClassesWithAnnotationsInSubToSuperOrderClassOfTClassOfSBooleanClassOfQextendsAArray3()
  {
    final List<Class<?>> classList2_2 =
      Classes.extractClassesWithAnnotationsInSubToSuperOrder(SubClassWithFirstAndSecondTestAnnotations.class,
          Object.class, true, SecondTestAnnotation.class);
    final int size2_2 =
      CLASS_LIST_OF_SUB_CLASS_WITH_SECOND_TEST_ANNOTATION_IN_SUB_TO_SUPER_ORDER_FOR_SUB_CLASS_WITH_FIRST_AND_SECOND_TEST_ANNOTATIONS.size();
    assertEquals(size2_2, classList2_2.size());
    for (int i = 0; i < size2_2; i++)
    {
      assertThat(
          classList2_2.get(i),
          is(equalTo((Object) CLASS_LIST_OF_SUB_CLASS_WITH_SECOND_TEST_ANNOTATION_IN_SUB_TO_SUPER_ORDER_FOR_SUB_CLASS_WITH_FIRST_AND_SECOND_TEST_ANNOTATIONS.get(i))));
    }

    final Collection<Class<?>> classCollection2_2 =
      Classes.extractClassesWithAnnotationsInSubToSuperOrder(SubClassWithFirstAndSecondTestAnnotations.class,
          Object.class, true, SecondTestAnnotation.class);
    assertEquals(size2_2, classCollection2_2.size());

    @SuppressWarnings({ "cast", "unchecked", "rawtypes" })
    final Collection<Class<?>> expected =
      (Collection<Class<?>>) ((Collection) CLASS_LIST_OF_SUB_CLASS_WITH_SECOND_TEST_ANNOTATION_IN_SUB_TO_SUPER_ORDER_FOR_SUB_CLASS_WITH_FIRST_AND_SECOND_TEST_ANNOTATIONS);

    assertThat(classCollection2_2, is(equalTo(expected)));
  }

  @Test
  public final void testExtractClassesWithAnnotationsInSubToSuperOrderClassOfTClassOfSBooleanClassOfQextendsAArray4()
  {
    /* */
    @SuppressWarnings("unchecked")
    final List<Class<?>> classList3_1 =
      Classes.extractClassesWithAnnotationsInSubToSuperOrder(SubClassWithFirstTestAnnotation.class, Object.class, true,
          FirstTestAnnotation.class, SecondTestAnnotation.class);
    final int size3_1 =
      CLASS_LIST_OF_SUB_CLASS_WITH_FIRST_OR_SECOND_TEST_ANNOTATION_IN_SUB_TO_SUPER_ORDER_FOR_SUB_CLASS_WITH_FIRST_TEST_ANNOTATION.size();
    assertEquals(size3_1, classList3_1.size());
    for (int i = 0; i < size3_1; i++)
    {
      assertThat(
          classList3_1.get(i),
          is(equalTo((Object) CLASS_LIST_OF_SUB_CLASS_WITH_FIRST_OR_SECOND_TEST_ANNOTATION_IN_SUB_TO_SUPER_ORDER_FOR_SUB_CLASS_WITH_FIRST_TEST_ANNOTATION.get(i))));
    }

    @SuppressWarnings("unchecked")
    final Collection<Class<?>> classCollection3_1 =
      Classes.extractClassesWithAnnotationsInSubToSuperOrder(SubClassWithFirstTestAnnotation.class, Object.class, true,
          FirstTestAnnotation.class, SecondTestAnnotation.class);
    assertEquals(size3_1, classCollection3_1.size());

    @SuppressWarnings({ "cast", "unchecked", "rawtypes" })
    final Collection<Class<?>> expected =
      (Collection<Class<?>>) ((Collection) CLASS_LIST_OF_SUB_CLASS_WITH_FIRST_OR_SECOND_TEST_ANNOTATION_IN_SUB_TO_SUPER_ORDER_FOR_SUB_CLASS_WITH_FIRST_TEST_ANNOTATION);

    assertThat(classCollection3_1, is(equalTo(expected)));
  }

  @Test
  public final void testExtractClassesWithAnnotationsInSubToSuperOrderClassOfTClassOfSBooleanClassOfQextendsAArray5()
  {
    /* */
    @SuppressWarnings("unchecked")
    final List<Class<?>> classList3_2 =
      Classes.extractClassesWithAnnotationsInSubToSuperOrder(SubClassWithFirstAndSecondTestAnnotations.class,
          Object.class, true, FirstTestAnnotation.class, SecondTestAnnotation.class);
    final int size3_2 =
      CLASS_LIST_OF_SUB_CLASS_WITH_FIRST_OR_SECOND_TEST_ANNOTATION_IN_SUB_TO_SUPER_ORDER_FOR_SUB_CLASS_WITH_FIRST_AND_SECOND_TEST_ANNOTATIONS.size();
    assertEquals(size3_2, classList3_2.size());
    for (int i = 0; i < size3_2; i++)
    {
      assertThat(
          classList3_2.get(i),
          is(equalTo((Object) CLASS_LIST_OF_SUB_CLASS_WITH_FIRST_OR_SECOND_TEST_ANNOTATION_IN_SUB_TO_SUPER_ORDER_FOR_SUB_CLASS_WITH_FIRST_AND_SECOND_TEST_ANNOTATIONS.get(i))));
    }

    @SuppressWarnings("unchecked")
    final Collection<Class<?>> classCollection3_2 =
      Classes.extractClassesWithAnnotationsInSubToSuperOrder(SubClassWithFirstAndSecondTestAnnotations.class,
          Object.class, true, FirstTestAnnotation.class, SecondTestAnnotation.class);
    assertEquals(size3_2, classCollection3_2.size());

    @SuppressWarnings({ "cast", "unchecked", "rawtypes" })
    final Collection<Class<?>> expected =
      (Collection<Class<?>>) ((Collection) CLASS_LIST_OF_SUB_CLASS_WITH_FIRST_OR_SECOND_TEST_ANNOTATION_IN_SUB_TO_SUPER_ORDER_FOR_SUB_CLASS_WITH_FIRST_AND_SECOND_TEST_ANNOTATIONS);

    assertThat(classCollection3_2, is(equalTo(expected)));
  }

  @Test
  public final void testExtractClassesWithAnnotationsInSuperToSubOrderClassOfTClassOfSBooleanCollectionOfClassOfQsuperTClassOfQextendsAArray0()
  {
    final List<Class<?>> classList1_1 = new ArrayList<Class<?>>();
    Classes.extractClassesWithAnnotationsInSuperToSubOrder(SubClassWithFirstTestAnnotation.class, Object.class, true,
        classList1_1, FirstTestAnnotation.class);
    final int size1_1 =
      CLASS_LIST_OF_SUB_CLASS_WITH_FIRST_TEST_ANNOTATION_IN_SUPER_TO_SUB_ORDER_FOR_SUB_CLASS_WITH_FIRST_TEST_ANNOTATION.size();
    assertEquals(size1_1, classList1_1.size());
    for (int i = 0; i < size1_1; i++)
    {
      assertThat(
          classList1_1.get(i),
          is(equalTo((Object) CLASS_LIST_OF_SUB_CLASS_WITH_FIRST_TEST_ANNOTATION_IN_SUPER_TO_SUB_ORDER_FOR_SUB_CLASS_WITH_FIRST_TEST_ANNOTATION.get(i))));
    }

    final Deque<Class<?>> classDeque1_1 = new ArrayDeque<Class<?>>();
    Classes.extractClassesWithAnnotationsInSuperToSubOrder(SubClassWithFirstTestAnnotation.class, Object.class, true,
        classDeque1_1, FirstTestAnnotation.class);
    assertEquals(size1_1, classDeque1_1.size());
    for (final Class<? super SubClassWithFirstTestAnnotation> aClass : CLASS_LIST_OF_SUB_CLASS_WITH_FIRST_TEST_ANNOTATION_IN_SUPER_TO_SUB_ORDER_FOR_SUB_CLASS_WITH_FIRST_TEST_ANNOTATION)
    {
      assertThat(classDeque1_1.pop(), is(equalTo((Object) aClass)));
    }
  }

  @Test
  public final void testExtractClassesWithAnnotationsInSuperToSubOrderClassOfTClassOfSBooleanCollectionOfClassOfQsuperTClassOfQextendsAArray1()
  {
    /* */
    final List<Class<?>> classList1_2 = new ArrayList<Class<?>>();
    Classes.extractClassesWithAnnotationsInSuperToSubOrder(SubClassWithFirstTestAnnotation.class, Object.class, true,
        classList1_2, SecondTestAnnotation.class);
    final int size1_2 =
      CLASS_LIST_OF_SUB_CLASS_WITH_SECOND_TEST_ANNOTATION_IN_SUPER_TO_SUB_ORDER_FOR_SUB_CLASS_WITH_FIRST_TEST_ANNOTATION.size();
    assertEquals(size1_2, classList1_2.size());
    for (int i = 0; i < size1_2; i++)
    {
      assertThat(
          classList1_2.get(i),
          is(equalTo((Object) CLASS_LIST_OF_SUB_CLASS_WITH_SECOND_TEST_ANNOTATION_IN_SUPER_TO_SUB_ORDER_FOR_SUB_CLASS_WITH_FIRST_TEST_ANNOTATION.get(i))));
    }

    final Deque<Class<?>> classDeque1_2 = new ArrayDeque<Class<?>>();
    Classes.extractClassesWithAnnotationsInSuperToSubOrder(SubClassWithFirstTestAnnotation.class, Object.class, true,
        classDeque1_2, SecondTestAnnotation.class);
    assertEquals(size1_2, classDeque1_2.size());
    for (final Class<? super SubClassWithFirstTestAnnotation> aClass : CLASS_LIST_OF_SUB_CLASS_WITH_SECOND_TEST_ANNOTATION_IN_SUPER_TO_SUB_ORDER_FOR_SUB_CLASS_WITH_FIRST_TEST_ANNOTATION)
    {
      assertThat(classDeque1_2.pop(), is(equalTo((Object) aClass)));
    }
  }

  @Test
  public final void testExtractClassesWithAnnotationsInSuperToSubOrderClassOfTClassOfSBooleanCollectionOfClassOfQsuperTClassOfQextendsAArray2()
  {
    /* */
    final List<Class<?>> classList2_1 = new ArrayList<Class<?>>();
    Classes.extractClassesWithAnnotationsInSuperToSubOrder(SubClassWithFirstAndSecondTestAnnotations.class,
        Object.class, true, classList2_1, FirstTestAnnotation.class);
    final int size2_1 =
      CLASS_LIST_OF_SUB_CLASS_WITH_FIRST_TEST_ANNOTATION_IN_SUPER_TO_SUB_ORDER_FOR_SUB_CLASS_WITH_FIRST_AND_SECOND_TEST_ANNOTATIONS.size();
    assertEquals(size2_1, classList2_1.size());
    for (int i = 0; i < size2_1; i++)
    {
      assertThat(
          classList2_1.get(i),
          is(equalTo((Object) CLASS_LIST_OF_SUB_CLASS_WITH_FIRST_TEST_ANNOTATION_IN_SUPER_TO_SUB_ORDER_FOR_SUB_CLASS_WITH_FIRST_AND_SECOND_TEST_ANNOTATIONS.get(i))));
    }

    final Deque<Class<?>> classDeque2_1 = new ArrayDeque<Class<?>>();
    Classes.extractClassesWithAnnotationsInSuperToSubOrder(SubClassWithFirstAndSecondTestAnnotations.class,
        Object.class, true, classDeque2_1, FirstTestAnnotation.class);
    assertEquals(size2_1, classDeque2_1.size());
    for (final Class<? super SubClassWithFirstAndSecondTestAnnotations> aClass : CLASS_LIST_OF_SUB_CLASS_WITH_FIRST_TEST_ANNOTATION_IN_SUPER_TO_SUB_ORDER_FOR_SUB_CLASS_WITH_FIRST_AND_SECOND_TEST_ANNOTATIONS)
    {
      assertThat(classDeque2_1.pop(), is(equalTo((Object) aClass)));
    }
  }

  @Test
  public final void testExtractClassesWithAnnotationsInSuperToSubOrderClassOfTClassOfSBooleanCollectionOfClassOfQsuperTClassOfQextendsAArray3()
  {
    final List<Class<?>> classList2_2 = new ArrayList<Class<?>>();
    Classes.extractClassesWithAnnotationsInSuperToSubOrder(SubClassWithFirstAndSecondTestAnnotations.class,
        Object.class, true, classList2_2, SecondTestAnnotation.class);
    final int size2_2 =
      CLASS_LIST_OF_SUB_CLASS_WITH_SECOND_TEST_ANNOTATION_IN_SUPER_TO_SUB_ORDER_FOR_SUB_CLASS_WITH_FIRST_AND_SECOND_TEST_ANNOTATIONS.size();
    assertEquals(size2_2, classList2_2.size());
    for (int i = 0; i < size2_2; i++)
    {
      assertThat(
          classList2_2.get(i),
          is(equalTo((Object) CLASS_LIST_OF_SUB_CLASS_WITH_SECOND_TEST_ANNOTATION_IN_SUPER_TO_SUB_ORDER_FOR_SUB_CLASS_WITH_FIRST_AND_SECOND_TEST_ANNOTATIONS.get(i))));
    }

    final Deque<Class<?>> classDeque2_2 = new ArrayDeque<Class<?>>();
    Classes.extractClassesWithAnnotationsInSuperToSubOrder(SubClassWithFirstAndSecondTestAnnotations.class,
        Object.class, true, classDeque2_2, SecondTestAnnotation.class);
    assertEquals(size2_2, classDeque2_2.size());
    for (final Class<? super SubClassWithFirstAndSecondTestAnnotations> aClass : CLASS_LIST_OF_SUB_CLASS_WITH_SECOND_TEST_ANNOTATION_IN_SUPER_TO_SUB_ORDER_FOR_SUB_CLASS_WITH_FIRST_AND_SECOND_TEST_ANNOTATIONS)
    {
      assertThat(classDeque2_2.pop(), is(equalTo((Object) aClass)));
    }
  }

  @SuppressWarnings("unchecked")
  @Test
  public final void testExtractClassesWithAnnotationsInSuperToSubOrderClassOfTClassOfSBooleanCollectionOfClassOfQsuperTClassOfQextendsAArray4()
  {
    /* */
    final List<Class<?>> classList3_1 = new ArrayList<Class<?>>();
    Classes.extractClassesWithAnnotationsInSuperToSubOrder(SubClassWithFirstTestAnnotation.class, Object.class, true,
        classList3_1, FirstTestAnnotation.class, SecondTestAnnotation.class);
    final int size3_1 =
      CLASS_LIST_OF_SUB_CLASS_WITH_FIRST_OR_SECOND_TEST_ANNOTATION_IN_SUPER_TO_SUB_ORDER_FOR_SUB_CLASS_WITH_FIRST_TEST_ANNOTATION.size();
    assertEquals(size3_1, classList3_1.size());
    for (int i = 0; i < size3_1; i++)
    {
      assertThat(
          classList3_1.get(i),
          is(equalTo((Object) CLASS_LIST_OF_SUB_CLASS_WITH_FIRST_OR_SECOND_TEST_ANNOTATION_IN_SUPER_TO_SUB_ORDER_FOR_SUB_CLASS_WITH_FIRST_TEST_ANNOTATION.get(i))));
    }

    final Deque<Class<?>> classDeque3_1 = new ArrayDeque<Class<?>>();
    Classes.extractClassesWithAnnotationsInSuperToSubOrder(SubClassWithFirstTestAnnotation.class, Object.class, true,
        classDeque3_1, FirstTestAnnotation.class, SecondTestAnnotation.class);
    assertEquals(size3_1, classDeque3_1.size());
    for (final Class<? super SubClassWithFirstTestAnnotation> aClass : CLASS_LIST_OF_SUB_CLASS_WITH_FIRST_OR_SECOND_TEST_ANNOTATION_IN_SUPER_TO_SUB_ORDER_FOR_SUB_CLASS_WITH_FIRST_TEST_ANNOTATION)
    {
      assertThat(classDeque3_1.pop(), is(equalTo((Object) aClass)));
    }
  }

  @SuppressWarnings("unchecked")
  @Test
  public final void testExtractClassesWithAnnotationsInSuperToSubOrderClassOfTClassOfSBooleanCollectionOfClassOfQsuperTClassOfQextendsAArray5()
  {
    /* */
    final List<Class<?>> classList3_2 = new ArrayList<Class<?>>();
    Classes.extractClassesWithAnnotationsInSuperToSubOrder(SubClassWithFirstAndSecondTestAnnotations.class,
        Object.class, true, classList3_2, FirstTestAnnotation.class, SecondTestAnnotation.class);
    final int size3_2 =
      CLASS_LIST_OF_SUB_CLASS_WITH_FIRST_OR_SECOND_TEST_ANNOTATION_IN_SUPER_TO_SUB_ORDER_FOR_SUB_CLASS_WITH_FIRST_AND_SECOND_TEST_ANNOTATIONS.size();
    assertEquals(size3_2, classList3_2.size());
    for (int i = 0; i < size3_2; i++)
    {
      assertThat(
          classList3_2.get(i),
          is(equalTo((Object) CLASS_LIST_OF_SUB_CLASS_WITH_FIRST_OR_SECOND_TEST_ANNOTATION_IN_SUPER_TO_SUB_ORDER_FOR_SUB_CLASS_WITH_FIRST_AND_SECOND_TEST_ANNOTATIONS.get(i))));
    }

    final Deque<Class<?>> classDeque3_2 = new ArrayDeque<Class<?>>();
    Classes.extractClassesWithAnnotationsInSuperToSubOrder(SubClassWithFirstAndSecondTestAnnotations.class,
        Object.class, true, classDeque3_2, FirstTestAnnotation.class, SecondTestAnnotation.class);
    assertEquals(size3_2, classDeque3_2.size());
    for (final Class<? super SubClassWithFirstAndSecondTestAnnotations> aClass : CLASS_LIST_OF_SUB_CLASS_WITH_FIRST_OR_SECOND_TEST_ANNOTATION_IN_SUPER_TO_SUB_ORDER_FOR_SUB_CLASS_WITH_FIRST_AND_SECOND_TEST_ANNOTATIONS)
    {
      assertThat(classDeque3_2.pop(), is(equalTo((Object) aClass)));
    }
  }

  @Test
  public final void testExtractClassesWithAnnotationsInSuperToSubOrderClassOfTClassOfSBooleanClassOfQextendsAArray0()
  {
    final List<Class<?>> classList1_1 =
      Classes.extractClassesWithAnnotationsInSuperToSubOrder(SubClassWithFirstTestAnnotation.class, Object.class, true,
          FirstTestAnnotation.class);
    final int size1_1 =
      CLASS_LIST_OF_SUB_CLASS_WITH_FIRST_TEST_ANNOTATION_IN_SUPER_TO_SUB_ORDER_FOR_SUB_CLASS_WITH_FIRST_TEST_ANNOTATION.size();
    assertEquals(size1_1, classList1_1.size());
    for (int i = 0; i < size1_1; i++)
    {
      assertThat(
          classList1_1.get(i),
          is(equalTo((Object) CLASS_LIST_OF_SUB_CLASS_WITH_FIRST_TEST_ANNOTATION_IN_SUPER_TO_SUB_ORDER_FOR_SUB_CLASS_WITH_FIRST_TEST_ANNOTATION.get(i))));
    }

    final Collection<Class<?>> classCollection1_1 =
      Classes.extractClassesWithAnnotationsInSuperToSubOrder(SubClassWithFirstTestAnnotation.class, Object.class, true,
          FirstTestAnnotation.class);

    assertEquals(size1_1, classCollection1_1.size());

    @SuppressWarnings({ "cast", "unchecked", "rawtypes" })
    final Collection<Class<?>> expected =
      (Collection<Class<?>>) ((Collection) CLASS_LIST_OF_SUB_CLASS_WITH_FIRST_TEST_ANNOTATION_IN_SUPER_TO_SUB_ORDER_FOR_SUB_CLASS_WITH_FIRST_TEST_ANNOTATION);

    assertThat(classCollection1_1, is(equalTo(expected)));
  }

  @Test
  public final void testExtractClassesWithAnnotationsInSuperToSubOrderClassOfTClassOfSBooleanClassOfQextendsAArray1()
  {
    /* */
    final List<Class<?>> classList1_2 =
      Classes.extractClassesWithAnnotationsInSuperToSubOrder(SubClassWithFirstTestAnnotation.class, Object.class, true,
          SecondTestAnnotation.class);
    final int size1_2 =
      CLASS_LIST_OF_SUB_CLASS_WITH_SECOND_TEST_ANNOTATION_IN_SUPER_TO_SUB_ORDER_FOR_SUB_CLASS_WITH_FIRST_TEST_ANNOTATION.size();
    assertEquals(size1_2, classList1_2.size());
    for (int i = 0; i < size1_2; i++)
    {
      assertThat(
          classList1_2.get(i),
          is(equalTo((Object) CLASS_LIST_OF_SUB_CLASS_WITH_SECOND_TEST_ANNOTATION_IN_SUPER_TO_SUB_ORDER_FOR_SUB_CLASS_WITH_FIRST_TEST_ANNOTATION.get(i))));
    }

    final Collection<Class<?>> classCollection1_2 =
      Classes.extractClassesWithAnnotationsInSuperToSubOrder(SubClassWithFirstTestAnnotation.class, Object.class, true,
          SecondTestAnnotation.class);
    assertEquals(size1_2, classCollection1_2.size());

    @SuppressWarnings({ "cast", "unchecked", "rawtypes" })
    final Collection<Class<?>> expected =
      (Collection<Class<?>>) ((Collection) CLASS_LIST_OF_SUB_CLASS_WITH_SECOND_TEST_ANNOTATION_IN_SUPER_TO_SUB_ORDER_FOR_SUB_CLASS_WITH_FIRST_TEST_ANNOTATION);

    assertThat(classCollection1_2, is(equalTo(expected)));
  }

  @Test
  public final void testExtractClassesWithAnnotationsInSuperToSubOrderClassOfTClassOfSBooleanClassOfQextendsAArray2()
  {
    /* */
    final List<Class<?>> classList2_1 =
      Classes.extractClassesWithAnnotationsInSuperToSubOrder(SubClassWithFirstAndSecondTestAnnotations.class,
          Object.class, true, FirstTestAnnotation.class);
    final int size2_1 =
      CLASS_LIST_OF_SUB_CLASS_WITH_FIRST_TEST_ANNOTATION_IN_SUPER_TO_SUB_ORDER_FOR_SUB_CLASS_WITH_FIRST_AND_SECOND_TEST_ANNOTATIONS.size();
    assertEquals(size2_1, classList2_1.size());
    for (int i = 0; i < size2_1; i++)
    {
      assertThat(
          classList2_1.get(i),
          is(equalTo((Object) CLASS_LIST_OF_SUB_CLASS_WITH_FIRST_TEST_ANNOTATION_IN_SUPER_TO_SUB_ORDER_FOR_SUB_CLASS_WITH_FIRST_AND_SECOND_TEST_ANNOTATIONS.get(i))));
    }

    final Collection<Class<?>> classCollection2_1 =
      Classes.extractClassesWithAnnotationsInSuperToSubOrder(SubClassWithFirstAndSecondTestAnnotations.class,
          Object.class, true, FirstTestAnnotation.class);
    assertEquals(size2_1, classCollection2_1.size());

    @SuppressWarnings({ "cast", "unchecked", "rawtypes" })
    final Collection<Class<?>> expected =
      (Collection<Class<?>>) ((Collection) CLASS_LIST_OF_SUB_CLASS_WITH_FIRST_TEST_ANNOTATION_IN_SUPER_TO_SUB_ORDER_FOR_SUB_CLASS_WITH_FIRST_AND_SECOND_TEST_ANNOTATIONS);

    assertThat(classCollection2_1, is(equalTo(expected)));
  }

  @Test
  public final void testExtractClassesWithAnnotationsInSuperToSubOrderClassOfTClassOfSBooleanClassOfQextendsAArray3()
  {
    final List<Class<?>> classList2_2 =
      Classes.extractClassesWithAnnotationsInSuperToSubOrder(SubClassWithFirstAndSecondTestAnnotations.class,
          Object.class, true, SecondTestAnnotation.class);
    final int size2_2 =
      CLASS_LIST_OF_SUB_CLASS_WITH_SECOND_TEST_ANNOTATION_IN_SUPER_TO_SUB_ORDER_FOR_SUB_CLASS_WITH_FIRST_AND_SECOND_TEST_ANNOTATIONS.size();
    assertEquals(size2_2, classList2_2.size());
    for (int i = 0; i < size2_2; i++)
    {
      assertThat(
          classList2_2.get(i),
          is(equalTo((Object) CLASS_LIST_OF_SUB_CLASS_WITH_SECOND_TEST_ANNOTATION_IN_SUPER_TO_SUB_ORDER_FOR_SUB_CLASS_WITH_FIRST_AND_SECOND_TEST_ANNOTATIONS.get(i))));
    }

    final Collection<Class<?>> classCollection2_2 =
      Classes.extractClassesWithAnnotationsInSuperToSubOrder(SubClassWithFirstAndSecondTestAnnotations.class,
          Object.class, true, SecondTestAnnotation.class);
    assertEquals(size2_2, classCollection2_2.size());

    @SuppressWarnings({ "cast", "unchecked", "rawtypes" })
    final Collection<Class<?>> expected =
      (Collection<Class<?>>) ((Collection) CLASS_LIST_OF_SUB_CLASS_WITH_SECOND_TEST_ANNOTATION_IN_SUPER_TO_SUB_ORDER_FOR_SUB_CLASS_WITH_FIRST_AND_SECOND_TEST_ANNOTATIONS);

    assertThat(classCollection2_2, is(equalTo(expected)));
  }

  @Test
  public final void testExtractClassesWithAnnotationsInSuperToSubOrderClassOfTClassOfSBooleanClassOfQextendsAArray4()
  {
    /* */
    @SuppressWarnings("unchecked")
    final List<Class<?>> classList3_1 =
      Classes.extractClassesWithAnnotationsInSuperToSubOrder(SubClassWithFirstTestAnnotation.class, Object.class, true,
          FirstTestAnnotation.class, SecondTestAnnotation.class);
    final int size3_1 =
      CLASS_LIST_OF_SUB_CLASS_WITH_FIRST_OR_SECOND_TEST_ANNOTATION_IN_SUPER_TO_SUB_ORDER_FOR_SUB_CLASS_WITH_FIRST_TEST_ANNOTATION.size();
    assertEquals(size3_1, classList3_1.size());
    for (int i = 0; i < size3_1; i++)
    {
      assertThat(
          classList3_1.get(i),
          is(equalTo((Object) CLASS_LIST_OF_SUB_CLASS_WITH_FIRST_OR_SECOND_TEST_ANNOTATION_IN_SUPER_TO_SUB_ORDER_FOR_SUB_CLASS_WITH_FIRST_TEST_ANNOTATION.get(i))));
    }

    @SuppressWarnings("unchecked")
    final Collection<Class<?>> classCollection3_1 =
      Classes.extractClassesWithAnnotationsInSuperToSubOrder(SubClassWithFirstTestAnnotation.class, Object.class, true,
          FirstTestAnnotation.class, SecondTestAnnotation.class);
    assertEquals(size3_1, classCollection3_1.size());

    @SuppressWarnings({ "cast", "unchecked", "rawtypes" })
    final Collection<Class<?>> expected =
      (Collection<Class<?>>) ((Collection) CLASS_LIST_OF_SUB_CLASS_WITH_FIRST_OR_SECOND_TEST_ANNOTATION_IN_SUPER_TO_SUB_ORDER_FOR_SUB_CLASS_WITH_FIRST_TEST_ANNOTATION);

    assertThat(classCollection3_1, is(equalTo(expected)));
  }

  @Test
  public final void testExtractClassesWithAnnotationsInSuperToSubOrderClassOfTClassOfSBooleanClassOfQextendsAArray5()
  {
    /* */
    @SuppressWarnings("unchecked")
    final List<Class<?>> classList3_2 =
      Classes.extractClassesWithAnnotationsInSuperToSubOrder(SubClassWithFirstAndSecondTestAnnotations.class,
          Object.class, true, FirstTestAnnotation.class, SecondTestAnnotation.class);
    final int size3_2 =
      CLASS_LIST_OF_SUB_CLASS_WITH_FIRST_OR_SECOND_TEST_ANNOTATION_IN_SUPER_TO_SUB_ORDER_FOR_SUB_CLASS_WITH_FIRST_AND_SECOND_TEST_ANNOTATIONS.size();
    assertEquals(size3_2, classList3_2.size());
    for (int i = 0; i < size3_2; i++)
    {
      assertThat(
          classList3_2.get(i),
          is(equalTo((Object) CLASS_LIST_OF_SUB_CLASS_WITH_FIRST_OR_SECOND_TEST_ANNOTATION_IN_SUPER_TO_SUB_ORDER_FOR_SUB_CLASS_WITH_FIRST_AND_SECOND_TEST_ANNOTATIONS.get(i))));
    }

    @SuppressWarnings("unchecked")
    final Collection<Class<?>> classCollection3_2 =
      Classes.extractClassesWithAnnotationsInSuperToSubOrder(SubClassWithFirstAndSecondTestAnnotations.class,
          Object.class, true, FirstTestAnnotation.class, SecondTestAnnotation.class);
    assertEquals(size3_2, classCollection3_2.size());

    @SuppressWarnings({ "cast", "unchecked", "rawtypes" })
    final Collection<Class<?>> expected =
      (Collection<Class<?>>) ((Collection) CLASS_LIST_OF_SUB_CLASS_WITH_FIRST_OR_SECOND_TEST_ANNOTATION_IN_SUPER_TO_SUB_ORDER_FOR_SUB_CLASS_WITH_FIRST_AND_SECOND_TEST_ANNOTATIONS);

    assertThat(classCollection3_2, is(equalTo(expected)));
  }

  private static final int DEFAULT_NUMBER = 100;

  private static final String DEFAULT_NAME = "Kevin";

  private static final Double DEFAULT_DOUBLE_NUMBER = Double.valueOf(999.99);

  private static class ClassWithManyConstructors
  {
    private final int number;

    private final String name;

    private final Double doubleNumber;

    public ClassWithManyConstructors()
    {
      this.number = DEFAULT_NUMBER;
      this.name = DEFAULT_NAME;
      this.doubleNumber = DEFAULT_DOUBLE_NUMBER;
    }

    public ClassWithManyConstructors(final int number)
    {
      this.number = number;
      this.name = DEFAULT_NAME + number;
      this.doubleNumber = DEFAULT_DOUBLE_NUMBER;
    }

    public ClassWithManyConstructors(final String name)
    {
      this.number = name.hashCode();
      this.name = name;
      this.doubleNumber = DEFAULT_DOUBLE_NUMBER;
    }

    public ClassWithManyConstructors(final Double doubleNumber)
    {
      this.number = DEFAULT_NUMBER;
      this.name = DEFAULT_NAME;
      this.doubleNumber = doubleNumber;
    }

    public ClassWithManyConstructors(final int number, final String name)
    {
      this.number = number;
      this.name = name;
      this.doubleNumber = DEFAULT_DOUBLE_NUMBER;
    }

    public ClassWithManyConstructors(final String name, final Double doubleNumber)
    {
      this.number = DEFAULT_NUMBER;
      this.name = name;
      this.doubleNumber = doubleNumber;
    }

    public ClassWithManyConstructors(final int number, final Double doubleNumber)
    {
      this.number = number;
      this.name = DEFAULT_NAME;
      this.doubleNumber = doubleNumber;
    }

    public ClassWithManyConstructors(final int number, final String name, final Double doubleNumber)
    {
      this.number = number;
      this.name = name;
      this.doubleNumber = doubleNumber;
    }

    @Override
    public int hashCode()
    {
      final int prime = 31;
      int result = 1;
      result = prime * result + number;
      result = prime * result + (name == null ? 0 : name.hashCode());
      result = prime * result + (doubleNumber == null ? 0 : doubleNumber.hashCode());
      return result;
    }

    @Override
    public boolean equals(final Object obj)
    {
      if (this == obj)
      {
        return true;
      }
      if (!(obj instanceof ClassWithManyConstructors))
      {
        return false;
      }
      final ClassWithManyConstructors that = (ClassWithManyConstructors) obj;
      return this.number == that.number
          && (this.name == that.name || (null != this.name && this.name.equals(that.name)))
          && (this.doubleNumber == that.doubleNumber || (null != this.doubleNumber && this.doubleNumber.equals(that.doubleNumber)));
    }
  }

  @SuppressWarnings("boxing")
  @Test
  public final void testFindConstructor() throws IllegalArgumentException, InstantiationException,
      IllegalAccessException, InvocationTargetException
  {
    assertThat(Classes.findConstructor(ClassWithManyConstructors.class, java.lang.Math.class), is(nullValue()));

    final Constructor<ClassWithManyConstructors> constructor1 =
      Classes.findConstructor(ClassWithManyConstructors.class);
    final ClassWithManyConstructors classWithManyConstructors1_1 = new ClassWithManyConstructors();
    final ClassWithManyConstructors classWithManyConstructors1_2 = constructor1.newInstance();
    assertThat(classWithManyConstructors1_1.hashCode(), is(equalTo(classWithManyConstructors1_2.hashCode())));
    assertThat(classWithManyConstructors1_1, is(equalTo(classWithManyConstructors1_2)));

    final Constructor<ClassWithManyConstructors> constructor2 =
      Classes.findConstructor(ClassWithManyConstructors.class, int.class);
    final ClassWithManyConstructors classWithManyConstructors2_1 = new ClassWithManyConstructors(999);
    final ClassWithManyConstructors classWithManyConstructors2_2 = constructor2.newInstance(Integer.valueOf(999));
    assertThat(classWithManyConstructors2_1.hashCode(), is(equalTo(classWithManyConstructors2_2.hashCode())));
    assertThat(classWithManyConstructors2_1, is(equalTo(classWithManyConstructors2_2)));

    final Constructor<ClassWithManyConstructors> constructor3 =
      Classes.findConstructor(ClassWithManyConstructors.class, String.class);
    final ClassWithManyConstructors classWithManyConstructors3_1 = new ClassWithManyConstructors("Lee");
    final ClassWithManyConstructors classWithManyConstructors3_2 = constructor3.newInstance("Lee");
    assertThat(classWithManyConstructors3_1.hashCode(), is(equalTo(classWithManyConstructors3_2.hashCode())));
    assertThat(classWithManyConstructors3_1, is(equalTo(classWithManyConstructors3_2)));

    final Constructor<ClassWithManyConstructors> constructor4 =
      Classes.findConstructor(ClassWithManyConstructors.class, Double.class);
    final ClassWithManyConstructors classWithManyConstructors4_1 =
      new ClassWithManyConstructors(Double.valueOf(1234.5678));
    final ClassWithManyConstructors classWithManyConstructors4_2 = constructor4.newInstance(Double.valueOf(1234.5678));
    assertThat(classWithManyConstructors4_1.hashCode(), is(equalTo(classWithManyConstructors4_2.hashCode())));
    assertThat(classWithManyConstructors4_1, is(equalTo(classWithManyConstructors4_2)));

    final Constructor<ClassWithManyConstructors> constructor5 =
      Classes.findConstructor(ClassWithManyConstructors.class, int.class, String.class);
    final ClassWithManyConstructors classWithManyConstructors5_1 = new ClassWithManyConstructors(999, "Nobody");
    final ClassWithManyConstructors classWithManyConstructors5_2 =
      constructor5.newInstance(Integer.valueOf(999), "Nobody");
    assertThat(classWithManyConstructors5_1.hashCode(), is(equalTo(classWithManyConstructors5_2.hashCode())));
    assertThat(classWithManyConstructors5_1, is(equalTo(classWithManyConstructors5_2)));

    final Constructor<ClassWithManyConstructors> constructor6 =
      Classes.findConstructor(ClassWithManyConstructors.class, String.class, Double.class);
    final ClassWithManyConstructors classWithManyConstructors6_1 =
      new ClassWithManyConstructors("Kevin Lee", Double.valueOf(1234.5678));
    final ClassWithManyConstructors classWithManyConstructors6_2 =
      constructor6.newInstance("Kevin Lee", Double.valueOf(1234.5678));
    assertThat(classWithManyConstructors6_1.hashCode(), is(equalTo(classWithManyConstructors6_2.hashCode())));
    assertThat(classWithManyConstructors6_1, is(equalTo(classWithManyConstructors6_2)));

    final Constructor<ClassWithManyConstructors> constructor7 =
      Classes.findConstructor(ClassWithManyConstructors.class, int.class, Double.class);
    final ClassWithManyConstructors classWithManyConstructors7_1 =
      new ClassWithManyConstructors(2010, Double.valueOf(555.777));
    final ClassWithManyConstructors classWithManyConstructors7_2 =
      constructor7.newInstance(Integer.valueOf(2010), Double.valueOf(555.777));
    assertThat(classWithManyConstructors7_1.hashCode(), is(equalTo(classWithManyConstructors7_2.hashCode())));
    assertThat(classWithManyConstructors7_1, is(equalTo(classWithManyConstructors7_2)));

    final Constructor<ClassWithManyConstructors> constructor8 =
      Classes.findConstructor(ClassWithManyConstructors.class, int.class, String.class, Double.class);
    final ClassWithManyConstructors classWithManyConstructors8_1 =
      new ClassWithManyConstructors(11111, "Kevin SeongHyun Lee", Double.valueOf(98765.4321));
    final ClassWithManyConstructors classWithManyConstructors8_2 =
      constructor8.newInstance(Integer.valueOf(11111), "Kevin SeongHyun Lee", Double.valueOf(98765.4321));
    assertThat(classWithManyConstructors8_1.hashCode(), is(equalTo(classWithManyConstructors8_2.hashCode())));
    assertThat(classWithManyConstructors8_1, is(equalTo(classWithManyConstructors8_2)));
  }

  @Target({ ElementType.CONSTRUCTOR })
  @Retention(RetentionPolicy.RUNTIME)
  private static @interface TestConstructorAnnotation1
  {
  }

  @Target({ ElementType.CONSTRUCTOR })
  @Retention(RetentionPolicy.RUNTIME)
  private static @interface TestConstructorAnnotation2
  {
  }

  @Target({ ElementType.CONSTRUCTOR })
  @Retention(RetentionPolicy.RUNTIME)
  private static @interface TestConstructorAnnotation3
  {
  }

  private static class ClassWithTestConstructorAnnotation1
  {
    private final int number;

    private final String name;

    private final Double doubleNumber;

    @TestConstructorAnnotation1
    public ClassWithTestConstructorAnnotation1()
    {
      this.number = DEFAULT_NUMBER;
      this.name = DEFAULT_NAME;
      this.doubleNumber = DEFAULT_DOUBLE_NUMBER;
    }

    @Override
    public int hashCode()
    {
      final int prime = 31;
      int result = 1;
      result = prime * result + number;
      result = prime * result + (name == null ? 0 : name.hashCode());
      result = prime * result + (doubleNumber == null ? 0 : doubleNumber.hashCode());
      return result;
    }

    @Override
    public boolean equals(final Object obj)
    {
      if (this == obj)
      {
        return true;
      }
      if (!(obj instanceof ClassWithTestConstructorAnnotation1))
      {
        return false;
      }
      final ClassWithTestConstructorAnnotation1 that = (ClassWithTestConstructorAnnotation1) obj;
      return this.number == that.number
          && (this.name == that.name || (null != this.name && this.name.equals(that.name)))
          && (this.doubleNumber == that.doubleNumber || (null != this.doubleNumber && this.doubleNumber.equals(that.doubleNumber)));
    }
  }

  @Test
  public final void testFindConstructorWithAnnotation0() throws IllegalArgumentException, InstantiationException,
      IllegalAccessException, InvocationTargetException
  {
    assertThat(Classes.findConstructorWithAnnotation(ClassWithTestConstructorAnnotation1.class,
        TestConstructorAnnotation2.class), is(nullValue()));
    final Constructor<ClassWithTestConstructorAnnotation1> constructor1 =
      Classes.findConstructorWithAnnotation(ClassWithTestConstructorAnnotation1.class, TestConstructorAnnotation1.class);
    final ClassWithTestConstructorAnnotation1 classWithTestConstructorAnnotation1_1 =
      new ClassWithTestConstructorAnnotation1();
    final ClassWithTestConstructorAnnotation1 classWithTestConstructorAnnotation1_2 = constructor1.newInstance();
    assertThat(classWithTestConstructorAnnotation1_2, is(equalTo(classWithTestConstructorAnnotation1_1)));
  }

  private static class ClassWithTestConstructorAnnotation2
  {

    private final int number;

    private final String name;

    private final Double doubleNumber;

    @TestConstructorAnnotation1
    public ClassWithTestConstructorAnnotation2(final int number)
    {
      this.number = number;
      this.name = DEFAULT_NAME + number;
      this.doubleNumber = DEFAULT_DOUBLE_NUMBER;
    }

    @Override
    public int hashCode()
    {
      final int prime = 31;
      int result = 1;
      result = prime * result + number;
      result = prime * result + (name == null ? 0 : name.hashCode());
      result = prime * result + (doubleNumber == null ? 0 : doubleNumber.hashCode());
      return result;
    }

    @Override
    public boolean equals(final Object obj)
    {
      if (this == obj)
      {
        return true;
      }
      if (!(obj instanceof ClassWithTestConstructorAnnotation2))
      {
        return false;
      }
      final ClassWithTestConstructorAnnotation2 that = (ClassWithTestConstructorAnnotation2) obj;
      return this.number == that.number
          && (this.name == that.name || (null != this.name && this.name.equals(that.name)))
          && (this.doubleNumber == that.doubleNumber || (null != this.doubleNumber && this.doubleNumber.equals(that.doubleNumber)));
    }
  }

  @Test
  public final void testFindConstructorWithAnnotation1() throws IllegalArgumentException, InstantiationException,
      IllegalAccessException, InvocationTargetException
  {
    assertThat(Classes.findConstructorWithAnnotation(ClassWithTestConstructorAnnotation2.class,
        TestConstructorAnnotation2.class), is(nullValue()));
    final Constructor<ClassWithTestConstructorAnnotation2> constructor2 =
      Classes.findConstructorWithAnnotation(ClassWithTestConstructorAnnotation2.class, TestConstructorAnnotation1.class);
    final ClassWithTestConstructorAnnotation2 classWithTestConstructorAnnotation2_1 =
      new ClassWithTestConstructorAnnotation2(555);
    final ClassWithTestConstructorAnnotation2 classWithTestConstructorAnnotation2_2 =
      constructor2.newInstance(Integer.valueOf(555));
    assertThat(classWithTestConstructorAnnotation2_2, is(equalTo(classWithTestConstructorAnnotation2_1)));
  }

  private static class ClassWithTestConstructorAnnotation3
  {

    private final int number;

    private final String name;

    private final Double doubleNumber;

    @TestConstructorAnnotation2
    public ClassWithTestConstructorAnnotation3(final String name)
    {
      this.number = name.hashCode();
      this.name = name;
      this.doubleNumber = DEFAULT_DOUBLE_NUMBER;
    }

    @Override
    public int hashCode()
    {
      final int prime = 31;
      int result = 1;
      result = prime * result + number;
      result = prime * result + (name == null ? 0 : name.hashCode());
      result = prime * result + (doubleNumber == null ? 0 : doubleNumber.hashCode());
      return result;
    }

    @Override
    public boolean equals(final Object obj)
    {
      if (this == obj)
      {
        return true;
      }
      if (!(obj instanceof ClassWithTestConstructorAnnotation3))
      {
        return false;
      }
      final ClassWithTestConstructorAnnotation3 that = (ClassWithTestConstructorAnnotation3) obj;
      return this.number == that.number
          && (this.name == that.name || (null != this.name && this.name.equals(that.name)))
          && (this.doubleNumber == that.doubleNumber || (null != this.doubleNumber && this.doubleNumber.equals(that.doubleNumber)));
    }
  }

  @Test
  public final void testFindConstructorWithAnnotation2() throws IllegalArgumentException, InstantiationException,
      IllegalAccessException, InvocationTargetException
  {
    assertThat(Classes.findConstructorWithAnnotation(ClassWithTestConstructorAnnotation3.class,
        TestConstructorAnnotation1.class), is(nullValue()));
    final Constructor<ClassWithTestConstructorAnnotation3> constructor3 =
      Classes.findConstructorWithAnnotation(ClassWithTestConstructorAnnotation3.class, TestConstructorAnnotation2.class);
    final ClassWithTestConstructorAnnotation3 classWithTestConstructorAnnotation3_1 =
      new ClassWithTestConstructorAnnotation3("Kevin Lee");
    final ClassWithTestConstructorAnnotation3 classWithTestConstructorAnnotation3_2 =
      constructor3.newInstance("Kevin Lee");
    assertThat(classWithTestConstructorAnnotation3_2, is(equalTo(classWithTestConstructorAnnotation3_1)));
  }

  private static class ClassWithTestConstructorAnnotation4
  {

    private final int number;

    private final String name;

    private final Double doubleNumber;

    @TestConstructorAnnotation1
    public ClassWithTestConstructorAnnotation4(final Double doubleNumber)
    {
      this.number = DEFAULT_NUMBER;
      this.name = DEFAULT_NAME;
      this.doubleNumber = doubleNumber;
    }

    @Override
    public int hashCode()
    {
      final int prime = 31;
      int result = 1;
      result = prime * result + number;
      result = prime * result + (name == null ? 0 : name.hashCode());
      result = prime * result + (doubleNumber == null ? 0 : doubleNumber.hashCode());
      return result;
    }

    @Override
    public boolean equals(final Object obj)
    {
      if (this == obj)
      {
        return true;
      }
      if (!(obj instanceof ClassWithTestConstructorAnnotation4))
      {
        return false;
      }
      final ClassWithTestConstructorAnnotation4 that = (ClassWithTestConstructorAnnotation4) obj;
      return this.number == that.number
          && (this.name == that.name || (null != this.name && this.name.equals(that.name)))
          && (this.doubleNumber == that.doubleNumber || (null != this.doubleNumber && this.doubleNumber.equals(that.doubleNumber)));
    }
  }

  @Test
  public final void testFindConstructorWithAnnotation3() throws IllegalArgumentException, InstantiationException,
      IllegalAccessException, InvocationTargetException
  {
    assertThat(Classes.findConstructorWithAnnotation(ClassWithTestConstructorAnnotation4.class,
        TestConstructorAnnotation2.class), is(nullValue()));
    final Constructor<ClassWithTestConstructorAnnotation4> constructor4 =
      Classes.findConstructorWithAnnotation(ClassWithTestConstructorAnnotation4.class, TestConstructorAnnotation1.class);
    final ClassWithTestConstructorAnnotation4 classWithTestConstructorAnnotation4_1 =
      new ClassWithTestConstructorAnnotation4(Double.valueOf(11223344.556677));
    final ClassWithTestConstructorAnnotation4 classWithTestConstructorAnnotation4_2 =
      constructor4.newInstance(Double.valueOf(11223344.556677));
    assertThat(classWithTestConstructorAnnotation4_2, is(equalTo(classWithTestConstructorAnnotation4_1)));
  }

  private static class ClassWithTestConstructorAnnotation5
  {

    private final int number;

    private final String name;

    private final Double doubleNumber;

    @TestConstructorAnnotation2
    public ClassWithTestConstructorAnnotation5(final int number, final String name)
    {
      this.number = number;
      this.name = name;
      this.doubleNumber = DEFAULT_DOUBLE_NUMBER;
    }

    @Override
    public int hashCode()
    {
      final int prime = 31;
      int result = 1;
      result = prime * result + number;
      result = prime * result + (name == null ? 0 : name.hashCode());
      result = prime * result + (doubleNumber == null ? 0 : doubleNumber.hashCode());
      return result;
    }

    @Override
    public boolean equals(final Object obj)
    {
      if (this == obj)
      {
        return true;
      }
      if (!(obj instanceof ClassWithTestConstructorAnnotation5))
      {
        return false;
      }
      final ClassWithTestConstructorAnnotation5 that = (ClassWithTestConstructorAnnotation5) obj;
      return this.number == that.number
          && (this.name == that.name || (null != this.name && this.name.equals(that.name)))
          && (this.doubleNumber == that.doubleNumber || (null != this.doubleNumber && this.doubleNumber.equals(that.doubleNumber)));
    }
  }

  @Test
  public final void testFindConstructorWithAnnotation4() throws IllegalArgumentException, InstantiationException,
      IllegalAccessException, InvocationTargetException
  {
    assertThat(Classes.findConstructorWithAnnotation(ClassWithTestConstructorAnnotation5.class,
        TestConstructorAnnotation1.class), is(nullValue()));
    final Constructor<ClassWithTestConstructorAnnotation5> constructor5 =
      Classes.findConstructorWithAnnotation(ClassWithTestConstructorAnnotation5.class, TestConstructorAnnotation2.class);
    final ClassWithTestConstructorAnnotation5 classWithTestConstructorAnnotation5_1 =
      new ClassWithTestConstructorAnnotation5(99999, "Lee");
    final ClassWithTestConstructorAnnotation5 classWithTestConstructorAnnotation5_2 =
      constructor5.newInstance(Integer.valueOf(99999), "Lee");
    assertThat(classWithTestConstructorAnnotation5_2, is(equalTo(classWithTestConstructorAnnotation5_1)));
  }

  private static class ClassWithTestConstructorAnnotation6
  {

    private final int number;

    private final String name;

    private final Double doubleNumber;

    @TestConstructorAnnotation2
    public ClassWithTestConstructorAnnotation6(final String name, final Double doubleNumber)
    {
      this.number = DEFAULT_NUMBER;
      this.name = name;
      this.doubleNumber = doubleNumber;
    }

    @Override
    public int hashCode()
    {
      final int prime = 31;
      int result = 1;
      result = prime * result + number;
      result = prime * result + (name == null ? 0 : name.hashCode());
      result = prime * result + (doubleNumber == null ? 0 : doubleNumber.hashCode());
      return result;
    }

    @Override
    public boolean equals(final Object obj)
    {
      if (this == obj)
      {
        return true;
      }
      if (!(obj instanceof ClassWithTestConstructorAnnotation6))
      {
        return false;
      }
      final ClassWithTestConstructorAnnotation6 that = (ClassWithTestConstructorAnnotation6) obj;
      return this.number == that.number
          && (this.name == that.name || (null != this.name && this.name.equals(that.name)))
          && (this.doubleNumber == that.doubleNumber || (null != this.doubleNumber && this.doubleNumber.equals(that.doubleNumber)));
    }
  }

  @Test
  public final void testFindConstructorWithAnnotation5() throws IllegalArgumentException, InstantiationException,
      IllegalAccessException, InvocationTargetException
  {
    assertThat(Classes.findConstructorWithAnnotation(ClassWithTestConstructorAnnotation6.class,
        TestConstructorAnnotation1.class), is(nullValue()));
    final Constructor<ClassWithTestConstructorAnnotation6> constructor6 =
      Classes.findConstructorWithAnnotation(ClassWithTestConstructorAnnotation6.class, TestConstructorAnnotation2.class);
    final ClassWithTestConstructorAnnotation6 classWithTestConstructorAnnotation6_1 =
      new ClassWithTestConstructorAnnotation6("Kevin Lee", Double.valueOf(9876.5432));
    final ClassWithTestConstructorAnnotation6 classWithTestConstructorAnnotation6_2 =
      constructor6.newInstance("Kevin Lee", Double.valueOf(9876.5432));
    assertThat(classWithTestConstructorAnnotation6_2, is(equalTo(classWithTestConstructorAnnotation6_1)));
  }

  private static class ClassWithTestConstructorAnnotation7
  {

    private final int number;

    private final String name;

    private final Double doubleNumber;

    @TestConstructorAnnotation1
    public ClassWithTestConstructorAnnotation7(final int number, final Double doubleNumber)
    {
      this.number = number;
      this.name = DEFAULT_NAME;
      this.doubleNumber = doubleNumber;
    }

    @Override
    public int hashCode()
    {
      final int prime = 31;
      int result = 1;
      result = prime * result + number;
      result = prime * result + (name == null ? 0 : name.hashCode());
      result = prime * result + (doubleNumber == null ? 0 : doubleNumber.hashCode());
      return result;
    }

    @Override
    public boolean equals(final Object obj)
    {
      if (this == obj)
      {
        return true;
      }
      if (!(obj instanceof ClassWithTestConstructorAnnotation7))
      {
        return false;
      }
      final ClassWithTestConstructorAnnotation7 that = (ClassWithTestConstructorAnnotation7) obj;
      return this.number == that.number
          && (this.name == that.name || (null != this.name && this.name.equals(that.name)))
          && (this.doubleNumber == that.doubleNumber || (null != this.doubleNumber && this.doubleNumber.equals(that.doubleNumber)));
    }
  }

  @Test
  public final void testFindConstructorWithAnnotation6() throws IllegalArgumentException, InstantiationException,
      IllegalAccessException, InvocationTargetException
  {
    assertThat(Classes.findConstructorWithAnnotation(ClassWithTestConstructorAnnotation7.class,
        TestConstructorAnnotation2.class), is(nullValue()));
    final Constructor<ClassWithTestConstructorAnnotation7> constructor7 =
      Classes.findConstructorWithAnnotation(ClassWithTestConstructorAnnotation7.class, TestConstructorAnnotation1.class);
    final ClassWithTestConstructorAnnotation7 classWithTestConstructorAnnotation7_1 =
      new ClassWithTestConstructorAnnotation7(5678, Double.valueOf(9876.5432));
    final ClassWithTestConstructorAnnotation7 classWithTestConstructorAnnotation7_2 =
      constructor7.newInstance(Integer.valueOf(5678), Double.valueOf(9876.5432));
    assertThat(classWithTestConstructorAnnotation7_2, is(equalTo(classWithTestConstructorAnnotation7_1)));
  }

  private static class ClassWithTestConstructorAnnotation8
  {

    private final int number;

    private final String name;

    private final Double doubleNumber;

    @TestConstructorAnnotation2
    public ClassWithTestConstructorAnnotation8(final int number, final String name, final Double doubleNumber)
    {
      this.number = number;
      this.name = name;
      this.doubleNumber = doubleNumber;
    }

    @Override
    public int hashCode()
    {
      final int prime = 31;
      int result = 1;
      result = prime * result + number;
      result = prime * result + (name == null ? 0 : name.hashCode());
      result = prime * result + (doubleNumber == null ? 0 : doubleNumber.hashCode());
      return result;
    }

    @Override
    public boolean equals(final Object obj)
    {
      if (this == obj)
      {
        return true;
      }
      if (!(obj instanceof ClassWithTestConstructorAnnotation8))
      {
        return false;
      }
      final ClassWithTestConstructorAnnotation8 that = (ClassWithTestConstructorAnnotation8) obj;
      return this.number == that.number
          && (this.name == that.name || (null != this.name && this.name.equals(that.name)))
          && (this.doubleNumber == that.doubleNumber || (null != this.doubleNumber && this.doubleNumber.equals(that.doubleNumber)));
    }
  }

  @Test
  public final void testFindConstructorWithAnnotation7() throws IllegalArgumentException, InstantiationException,
      IllegalAccessException, InvocationTargetException
  {
    assertThat(Classes.findConstructorWithAnnotation(ClassWithTestConstructorAnnotation8.class,
        TestConstructorAnnotation1.class), is(nullValue()));
    final Constructor<ClassWithTestConstructorAnnotation8> constructor8 =
      Classes.findConstructorWithAnnotation(ClassWithTestConstructorAnnotation8.class, TestConstructorAnnotation2.class);
    final ClassWithTestConstructorAnnotation8 classWithTestConstructorAnnotation8_1 =
      new ClassWithTestConstructorAnnotation8(5678, "Kevin Lee", Double.valueOf(9876.5432));
    final ClassWithTestConstructorAnnotation8 classWithTestConstructorAnnotation8_2 =
      constructor8.newInstance(Integer.valueOf(5678), "Kevin Lee", Double.valueOf(9876.5432));
    assertThat(classWithTestConstructorAnnotation8_2, is(equalTo(classWithTestConstructorAnnotation8_1)));
  }

  private static class ClassHavingConstructorsWithTestConstructorAnnotation
  {

    private final int number;

    private final String name;

    private final Double doubleNumber;

    @TestConstructorAnnotation1
    @TestConstructorAnnotation3
    public ClassHavingConstructorsWithTestConstructorAnnotation()
    {
      this.number = DEFAULT_NUMBER;
      this.name = DEFAULT_NAME;
      this.doubleNumber = DEFAULT_DOUBLE_NUMBER;
    }

    @TestConstructorAnnotation2
    @TestConstructorAnnotation3
    public ClassHavingConstructorsWithTestConstructorAnnotation(final int number)
    {
      this.number = number;
      this.name = DEFAULT_NAME;
      this.doubleNumber = DEFAULT_DOUBLE_NUMBER;
    }

    @TestConstructorAnnotation3
    public ClassHavingConstructorsWithTestConstructorAnnotation(final String name)
    {
      this.number = DEFAULT_NUMBER;
      this.name = name;
      this.doubleNumber = DEFAULT_DOUBLE_NUMBER;
    }

    @TestConstructorAnnotation1
    @TestConstructorAnnotation2
    @TestConstructorAnnotation3
    public ClassHavingConstructorsWithTestConstructorAnnotation(final Double doubleNumber)
    {
      this.number = DEFAULT_NUMBER;
      this.name = DEFAULT_NAME;
      this.doubleNumber = doubleNumber;
    }

    @TestConstructorAnnotation1
    @TestConstructorAnnotation3
    public ClassHavingConstructorsWithTestConstructorAnnotation(final int number, final String name)
    {
      this.number = number;
      this.name = name;
      this.doubleNumber = DEFAULT_DOUBLE_NUMBER;
    }

    @TestConstructorAnnotation3
    public ClassHavingConstructorsWithTestConstructorAnnotation(final String name, final Double doubleNumber)
    {
      this.number = DEFAULT_NUMBER;
      this.name = name;
      this.doubleNumber = doubleNumber;
    }

    @TestConstructorAnnotation2
    @TestConstructorAnnotation3
    public ClassHavingConstructorsWithTestConstructorAnnotation(final int number, final Double doubleNumber)
    {
      this.number = number;
      this.name = DEFAULT_NAME;
      this.doubleNumber = doubleNumber;
    }

    @TestConstructorAnnotation1
    @TestConstructorAnnotation2
    @TestConstructorAnnotation3
    public ClassHavingConstructorsWithTestConstructorAnnotation(final int number, final String name,
        final Double doubleNumber)
    {
      this.number = number;
      this.name = name;
      this.doubleNumber = doubleNumber;
    }

    @Override
    public int hashCode()
    {
      final int prime = 31;
      int result = 1;
      result = prime * result + number;
      result = prime * result + (name == null ? 0 : name.hashCode());
      result = prime * result + (doubleNumber == null ? 0 : doubleNumber.hashCode());
      return result;
    }

    @Override
    public boolean equals(final Object obj)
    {
      if (this == obj)
      {
        return true;
      }
      if (!(obj instanceof ClassHavingConstructorsWithTestConstructorAnnotation))
      {
        return false;
      }
      final ClassHavingConstructorsWithTestConstructorAnnotation that =
        (ClassHavingConstructorsWithTestConstructorAnnotation) obj;
      return this.number == that.number
          && (this.name == that.name || (null != this.name && this.name.equals(that.name)))
          && (this.doubleNumber == that.doubleNumber || (null != this.doubleNumber && this.doubleNumber.equals(that.doubleNumber)));
    }
  }

  @SuppressWarnings({ "boxing" })
  @Test
  public void testFindAllConstructorsWithAnnotation() throws IllegalArgumentException, InstantiationException,
      IllegalAccessException, InvocationTargetException
  {
    assertThat(
        Classes.findAllConstructorsWithAnnotation(ClassHavingConstructorsWithTestConstructorAnnotation.class,
            Deprecated.class)
            .size(), is(0));

    final Set<Constructor<ClassHavingConstructorsWithTestConstructorAnnotation>> constructorSet =
      Classes.findAllConstructorsWithAnnotation(ClassHavingConstructorsWithTestConstructorAnnotation.class,
          TestConstructorAnnotation1.class);
    assertThat(constructorSet.size(), is(equalTo(sizeOfHavingAnnotation1)));
    for (final Constructor<ClassHavingConstructorsWithTestConstructorAnnotation> constructor : constructorSet)
    {
      final Class<?>[] paramTypes = constructor.getParameterTypes();
      final ClassHavingConstructorsWithTestConstructorAnnotation expectedObject =
        CLASS_HAVING_CONSTRUCTORS_WITH_TEST_CONSTRUCTOR_ANNOTATION1_MAP.get(paramTypes.length)
            .get(paramTypesToString(paramTypes));

      final ClassHavingConstructorsWithTestConstructorAnnotation result =
        constructor.newInstance(getParamObjects(paramTypes));
      assertThat(result, is(equalTo(expectedObject)));
    }

    final Set<Constructor<ClassHavingConstructorsWithTestConstructorAnnotation>> constructorSet2 =
      Classes.findAllConstructorsWithAnnotation(ClassHavingConstructorsWithTestConstructorAnnotation.class,
          TestConstructorAnnotation2.class);
    assertThat(constructorSet2.size(), is(equalTo(sizeOfHavingAnnotation2)));
    for (final Constructor<ClassHavingConstructorsWithTestConstructorAnnotation> constructor : constructorSet2)
    {
      final Class<?>[] paramTypes = constructor.getParameterTypes();
      final ClassHavingConstructorsWithTestConstructorAnnotation expectedObject =
        CLASS_HAVING_CONSTRUCTORS_WITH_TEST_CONSTRUCTOR_ANNOTATION2_MAP.get(paramTypes.length)
            .get(paramTypesToString(paramTypes));

      final ClassHavingConstructorsWithTestConstructorAnnotation result =
        constructor.newInstance(getParamObjects(paramTypes));
      assertThat(result, is(equalTo(expectedObject)));
    }

    final Set<Constructor<ClassHavingConstructorsWithTestConstructorAnnotation>> constructorSet3 =
      Classes.findAllConstructorsWithAnnotation(ClassHavingConstructorsWithTestConstructorAnnotation.class,
          TestConstructorAnnotation3.class);
    assertThat(constructorSet3.size(), is(equalTo(sizeOfHavingAnnotation3)));
    for (final Constructor<ClassHavingConstructorsWithTestConstructorAnnotation> constructor : constructorSet3)
    {
      final Class<?>[] paramTypes = constructor.getParameterTypes();
      final ClassHavingConstructorsWithTestConstructorAnnotation expectedObject =
        CLASS_HAVING_CONSTRUCTORS_WITH_TEST_CONSTRUCTOR_ANNOTATION3_MAP.get(paramTypes.length)
            .get(paramTypesToString(paramTypes));

      final ClassHavingConstructorsWithTestConstructorAnnotation result =
        constructor.newInstance(getParamObjects(paramTypes));
      assertThat(result, is(equalTo(expectedObject)));
    }

    @SuppressWarnings("unchecked")
    final Set<Constructor<ClassHavingConstructorsWithTestConstructorAnnotation>> constructorSet1_2 =
      Classes.findAllConstructorsWithAnnotation(ClassHavingConstructorsWithTestConstructorAnnotation.class,
          TestConstructorAnnotation1.class, TestConstructorAnnotation2.class);
    assertThat(constructorSet1_2.size(), is(equalTo(sizeOfHavingAnnotation1And2)));
    for (final Constructor<ClassHavingConstructorsWithTestConstructorAnnotation> constructor : constructorSet1_2)
    {
      final Class<?>[] paramTypes = constructor.getParameterTypes();
      final ClassHavingConstructorsWithTestConstructorAnnotation expectedObject =
        CLASS_HAVING_CONSTRUCTORS_WITH_TEST_CONSTRUCTOR_ANNOTATION1_2_MAP.get(paramTypes.length)
            .get(paramTypesToString(paramTypes));

      final ClassHavingConstructorsWithTestConstructorAnnotation result =
        constructor.newInstance(getParamObjects(paramTypes));
      assertThat(result, is(equalTo(expectedObject)));
    }
  }

  private static final List<Map<Class<?>, Object>> PARAM_MAP_LIST = new ArrayList<Map<Class<?>, Object>>();

  private static final List<Map<String, ClassHavingConstructorsWithTestConstructorAnnotation>> CLASS_HAVING_CONSTRUCTORS_WITH_TEST_CONSTRUCTOR_ANNOTATION1_MAP =
    new ArrayList<Map<String, ClassHavingConstructorsWithTestConstructorAnnotation>>();
  private static final int sizeOfHavingAnnotation1;

  private static final List<Map<String, ClassHavingConstructorsWithTestConstructorAnnotation>> CLASS_HAVING_CONSTRUCTORS_WITH_TEST_CONSTRUCTOR_ANNOTATION2_MAP =
    new ArrayList<Map<String, ClassHavingConstructorsWithTestConstructorAnnotation>>();
  private static final int sizeOfHavingAnnotation2;

  private static final List<Map<String, ClassHavingConstructorsWithTestConstructorAnnotation>> CLASS_HAVING_CONSTRUCTORS_WITH_TEST_CONSTRUCTOR_ANNOTATION3_MAP =
    new ArrayList<Map<String, ClassHavingConstructorsWithTestConstructorAnnotation>>();
  private static final int sizeOfHavingAnnotation3;

  private static final List<Map<String, ClassHavingConstructorsWithTestConstructorAnnotation>> CLASS_HAVING_CONSTRUCTORS_WITH_TEST_CONSTRUCTOR_ANNOTATION1_2_MAP =
    new ArrayList<Map<String, ClassHavingConstructorsWithTestConstructorAnnotation>>();
  private static final int sizeOfHavingAnnotation1And2;

  static
  {
    final int[] intValues = { DEFAULT_NUMBER, 5555, 1029384756, 999 };
    final Integer[] integerValues =
      { Integer.valueOf(intValues[0]), Integer.valueOf(intValues[1]), Integer.valueOf(intValues[2]),
          Integer.valueOf(intValues[3]) };
    final String[] stringValues = { DEFAULT_NAME, "Kevin SeongHyun Lee", "Nobody", "Kevin Lee" };
    final Double[] doubleValues =
      { DEFAULT_DOUBLE_NUMBER, Double.valueOf(2010.09), Double.valueOf(26.09), Double.valueOf(12345.6789) };

    int i = 0;
    Map<Class<?>, Object> map = new HashMap<Class<?>, Object>();
    map.put(int.class, integerValues[i]);
    map.put(String.class, stringValues[i]);
    map.put(Double.class, doubleValues[i]);
    PARAM_MAP_LIST.add(map);

    i++;
    map = new HashMap<Class<?>, Object>();
    map.put(int.class, integerValues[i]);
    map.put(String.class, stringValues[i]);
    map.put(Double.class, doubleValues[i]);
    PARAM_MAP_LIST.add(map);

    i++;
    map = new HashMap<Class<?>, Object>();
    map.put(int.class, integerValues[i]);
    map.put(String.class, stringValues[i]);
    map.put(Double.class, doubleValues[i]);
    PARAM_MAP_LIST.add(map);

    i++;
    map = new HashMap<Class<?>, Object>();
    map.put(int.class, integerValues[i]);
    map.put(String.class, stringValues[i]);
    map.put(Double.class, doubleValues[i]);
    PARAM_MAP_LIST.add(map);

    int size = 0;
    i = 0;
    Map<String, ClassHavingConstructorsWithTestConstructorAnnotation> classMap =
      new HashMap<String, ClassesTest.ClassHavingConstructorsWithTestConstructorAnnotation>();
    classMap.put("", new ClassHavingConstructorsWithTestConstructorAnnotation());
    CLASS_HAVING_CONSTRUCTORS_WITH_TEST_CONSTRUCTOR_ANNOTATION1_MAP.add(classMap);
    size += classMap.size();

    i++;
    classMap = new HashMap<String, ClassesTest.ClassHavingConstructorsWithTestConstructorAnnotation>();
    classMap.put(paramTypesToString(Double.class), new ClassHavingConstructorsWithTestConstructorAnnotation(
        doubleValues[i]));
    CLASS_HAVING_CONSTRUCTORS_WITH_TEST_CONSTRUCTOR_ANNOTATION1_MAP.add(classMap);
    size += classMap.size();

    i++;
    classMap = new HashMap<String, ClassesTest.ClassHavingConstructorsWithTestConstructorAnnotation>();
    classMap.put(paramTypesToString(int.class, String.class), new ClassHavingConstructorsWithTestConstructorAnnotation(
        intValues[i], stringValues[i]));
    CLASS_HAVING_CONSTRUCTORS_WITH_TEST_CONSTRUCTOR_ANNOTATION1_MAP.add(classMap);
    size += classMap.size();

    i++;
    classMap = new HashMap<String, ClassesTest.ClassHavingConstructorsWithTestConstructorAnnotation>();
    classMap.put(paramTypesToString(int.class, String.class, Double.class),
        new ClassHavingConstructorsWithTestConstructorAnnotation(intValues[i], stringValues[i], doubleValues[i]));
    CLASS_HAVING_CONSTRUCTORS_WITH_TEST_CONSTRUCTOR_ANNOTATION1_MAP.add(classMap);
    size += classMap.size();
    sizeOfHavingAnnotation1 = size;

    size = 0;
    classMap = new HashMap<String, ClassesTest.ClassHavingConstructorsWithTestConstructorAnnotation>();
    CLASS_HAVING_CONSTRUCTORS_WITH_TEST_CONSTRUCTOR_ANNOTATION2_MAP.add(classMap);
    i = 0;

    i++;
    classMap = new HashMap<String, ClassesTest.ClassHavingConstructorsWithTestConstructorAnnotation>();
    classMap.put(paramTypesToString(int.class), new ClassHavingConstructorsWithTestConstructorAnnotation(intValues[i]));
    classMap.put(paramTypesToString(Double.class), new ClassHavingConstructorsWithTestConstructorAnnotation(
        doubleValues[i]));
    CLASS_HAVING_CONSTRUCTORS_WITH_TEST_CONSTRUCTOR_ANNOTATION2_MAP.add(classMap);
    size += classMap.size();

    i++;
    classMap = new HashMap<String, ClassesTest.ClassHavingConstructorsWithTestConstructorAnnotation>();
    classMap.put(paramTypesToString(int.class, Double.class), new ClassHavingConstructorsWithTestConstructorAnnotation(
        intValues[i], doubleValues[i]));
    CLASS_HAVING_CONSTRUCTORS_WITH_TEST_CONSTRUCTOR_ANNOTATION2_MAP.add(classMap);
    size += classMap.size();

    i++;
    classMap = new HashMap<String, ClassesTest.ClassHavingConstructorsWithTestConstructorAnnotation>();
    classMap.put(paramTypesToString(int.class, String.class, Double.class),
        new ClassHavingConstructorsWithTestConstructorAnnotation(intValues[i], stringValues[i], doubleValues[i]));
    CLASS_HAVING_CONSTRUCTORS_WITH_TEST_CONSTRUCTOR_ANNOTATION2_MAP.add(classMap);
    size += classMap.size();
    sizeOfHavingAnnotation2 = size;

    size = 0;
    i = 0;
    classMap = new HashMap<String, ClassesTest.ClassHavingConstructorsWithTestConstructorAnnotation>();
    classMap.put("", new ClassHavingConstructorsWithTestConstructorAnnotation());
    CLASS_HAVING_CONSTRUCTORS_WITH_TEST_CONSTRUCTOR_ANNOTATION3_MAP.add(classMap);
    size += classMap.size();

    i++;
    classMap = new HashMap<String, ClassesTest.ClassHavingConstructorsWithTestConstructorAnnotation>();
    classMap.put(paramTypesToString(int.class), new ClassHavingConstructorsWithTestConstructorAnnotation(intValues[i]));
    classMap.put(paramTypesToString(String.class), new ClassHavingConstructorsWithTestConstructorAnnotation(
        stringValues[i]));
    classMap.put(paramTypesToString(Double.class), new ClassHavingConstructorsWithTestConstructorAnnotation(
        doubleValues[i]));
    CLASS_HAVING_CONSTRUCTORS_WITH_TEST_CONSTRUCTOR_ANNOTATION3_MAP.add(classMap);
    size += classMap.size();

    i++;
    classMap = new HashMap<String, ClassesTest.ClassHavingConstructorsWithTestConstructorAnnotation>();
    classMap.put(paramTypesToString(int.class, String.class), new ClassHavingConstructorsWithTestConstructorAnnotation(
        intValues[i], stringValues[i]));
    classMap.put(paramTypesToString(String.class, Double.class),
        new ClassHavingConstructorsWithTestConstructorAnnotation(stringValues[i], doubleValues[i]));
    classMap.put(paramTypesToString(int.class, Double.class), new ClassHavingConstructorsWithTestConstructorAnnotation(
        intValues[i], doubleValues[i]));
    CLASS_HAVING_CONSTRUCTORS_WITH_TEST_CONSTRUCTOR_ANNOTATION3_MAP.add(classMap);
    size += classMap.size();

    i++;
    classMap = new HashMap<String, ClassesTest.ClassHavingConstructorsWithTestConstructorAnnotation>();
    classMap.put(paramTypesToString(int.class, String.class, Double.class),
        new ClassHavingConstructorsWithTestConstructorAnnotation(intValues[i], stringValues[i], doubleValues[i]));
    CLASS_HAVING_CONSTRUCTORS_WITH_TEST_CONSTRUCTOR_ANNOTATION3_MAP.add(classMap);
    size += classMap.size();
    sizeOfHavingAnnotation3 = size;

    /* 1 and 2 */
    /* 0 */
    size = 0;
    i = 0;
    classMap = new HashMap<String, ClassesTest.ClassHavingConstructorsWithTestConstructorAnnotation>();
    classMap.put("", new ClassHavingConstructorsWithTestConstructorAnnotation());
    CLASS_HAVING_CONSTRUCTORS_WITH_TEST_CONSTRUCTOR_ANNOTATION1_2_MAP.add(classMap);
    size += classMap.size();

    /* 1 */
    i++;
    classMap = new HashMap<String, ClassesTest.ClassHavingConstructorsWithTestConstructorAnnotation>();

    classMap.put(paramTypesToString(int.class), new ClassHavingConstructorsWithTestConstructorAnnotation(intValues[i]));
    classMap.put(paramTypesToString(Double.class), new ClassHavingConstructorsWithTestConstructorAnnotation(
        doubleValues[i]));
    CLASS_HAVING_CONSTRUCTORS_WITH_TEST_CONSTRUCTOR_ANNOTATION1_2_MAP.add(classMap);
    size += classMap.size();

    /* 2 */
    i++;
    classMap = new HashMap<String, ClassesTest.ClassHavingConstructorsWithTestConstructorAnnotation>();
    classMap.put(paramTypesToString(int.class, String.class), new ClassHavingConstructorsWithTestConstructorAnnotation(
        intValues[i], stringValues[i]));
    classMap.put(paramTypesToString(int.class, Double.class), new ClassHavingConstructorsWithTestConstructorAnnotation(
        intValues[i], doubleValues[i]));
    CLASS_HAVING_CONSTRUCTORS_WITH_TEST_CONSTRUCTOR_ANNOTATION1_2_MAP.add(classMap);
    size += classMap.size();

    /* 3 */
    i++;
    classMap = new HashMap<String, ClassesTest.ClassHavingConstructorsWithTestConstructorAnnotation>();
    classMap.put(paramTypesToString(int.class, String.class, Double.class),
        new ClassHavingConstructorsWithTestConstructorAnnotation(intValues[i], stringValues[i], doubleValues[i]));
    CLASS_HAVING_CONSTRUCTORS_WITH_TEST_CONSTRUCTOR_ANNOTATION1_2_MAP.add(classMap);
    size += classMap.size();
    sizeOfHavingAnnotation1And2 = size;
  }

  private static Object getParamValue(final int index, final Class<?> aClass)
  {
    return PARAM_MAP_LIST.get(index)
        .get(aClass);
  }

  private static String paramTypesToString(final Class<?>... paramTypes)
  {
    final StringBuilder stringBuilder = new StringBuilder();
    for (final Class<?> aClass : paramTypes)
    {
      stringBuilder.append(aClass.getName());
    }
    return stringBuilder.toString();
  }

  private Object[] getParamObjects(final Class<?>[] paramTypes)
  {
    final List<Object> paramList = new ArrayList<Object>();
    for (final Class<?> aClass : paramTypes)
    {
      paramList.add(getParamValue(paramTypes.length, aClass));
    }
    return paramList.toArray();
  }

  @Test
  public void testClassArrayOf()
  {
    final Class<?>[] classes1 = Classes.classArrayOf();
    final Class<?>[] classes2 = Classes.classArrayOf();
    final Class<?>[] classes3 = Classes.classArrayOf();
    final Class<?>[] classes4 = Classes.classArrayOf();
    final Class<?>[] classes5 = Classes.classArrayOf();

    assertThat(classes1, is(equalTo(classes2)));
    assertThat(classes1, is(equalTo(classes3)));
    assertThat(classes1, is(equalTo(classes4)));
    assertThat(classes1, is(equalTo(classes5)));

    assertTrue(classes1 == classes2);
    assertTrue(classes1 == classes3);
    assertTrue(classes1 == classes4);
    assertTrue(classes1 == classes5);
  }

  @Test
  public void testClassArrayOfClasses()
  {
    final Class<?>[] expectedClasses = { String.class, Integer.class, Object.class, Date.class };
    final Class<?>[] result = Classes.classArrayOf(String.class, Integer.class, Object.class, Date.class);
    assertTrue(Arrays.deepEquals(expectedClasses, result));
  }

  @Test
  public void testObjectArrayOf()
  {
    final Object[] objects1 = Classes.objectArrayOf();
    final Object[] objects2 = Classes.objectArrayOf();
    final Object[] objects3 = Classes.objectArrayOf();
    final Object[] objects4 = Classes.objectArrayOf();
    final Object[] objects5 = Classes.objectArrayOf();

    assertThat(objects1, is(equalTo(objects2)));
    assertThat(objects1, is(equalTo(objects3)));
    assertThat(objects1, is(equalTo(objects4)));
    assertThat(objects1, is(equalTo(objects5)));

    assertTrue(objects1 == objects2);
    assertTrue(objects1 == objects3);
    assertTrue(objects1 == objects4);
    assertTrue(objects1 == objects5);
  }

  @Test
  public void testObjectArrayOfObjects()
  {
    final Date date = new Date();
    final Object[] expectedObjects = { "Kevin Lee", Integer.valueOf(12345), date };
    final Object[] result = Classes.objectArrayOf("Kevin Lee", Integer.valueOf(12345), date);
    assertTrue(Arrays.deepEquals(expectedObjects, result));
  }

}
