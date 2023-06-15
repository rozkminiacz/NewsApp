package com.kotlintesting.news

import io.kotest.core.spec.style.StringSpec

/**
 * Created by jaroslawmichalik on 15/06/2023
 */
class SomeSpec: StringSpec({

  "it should pass"{

  }

  "it should fail"{
    throw AssertionError(":(")
  }

  "it should crash"{
    throw Exception(":(((")
  }
})