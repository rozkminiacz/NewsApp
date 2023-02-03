package com.kotlintesting.news

import org.junit.jupiter.api.Test

/**
 * Created by jaroslawmichalik
 */
class TestFrameworkIsWorking {
  @Test
  fun `this one should pass`(){

  }

  @Test
  fun `this one should fail`(){
    throw AssertionError("fail")
  }

  @Test
  fun `this one should crash`(){
    throw Exception("crash")
  }
}