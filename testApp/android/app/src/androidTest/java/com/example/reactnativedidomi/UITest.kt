package com.example.reactnativedidomi

import org.junit.runner.RunWith
import org.junit.runners.Suite

@RunWith(Suite::class)
@Suite.SuiteClasses(
        UIMethodsTest::class,
        UIGettersTest::class,
        UIGettersParamsTest::class,
        UISettersTest::class)
class UITest {}