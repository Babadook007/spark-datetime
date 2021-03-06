/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.sparklinedata.spark.dateTime

import com.github.nscala_time.time.Imports._
import TestSQLContext._
import org.joda.time.format.{ISODateTimeFormat, DateTimeFormatter}
import org.scalatest.{BeforeAndAfterAll, FunSuite}
import org.sparklinedata.spark.dateTime.Utils._

case class TRow(dt: String)

abstract class BaseTest extends FunSuite with BeforeAndAfterAll {

  val END_DATE = DateTime.parse("2015-06-23T17:27:43.769-07:00")
  val START_DATE = END_DATE - 30.day

  override def beforeAll() = {
    Functions.register(TestSQLContext)
    val fmt : DateTimeFormatter = ISODateTimeFormat.dateTime()

    val end = END_DATE
    val start = START_DATE
    val col = intervalToSeq((start to end) , 1.day).map(d => TRow(fmt.print(d)))

    val df = createDataFrame[TRow](sparkContext.parallelize(col))
    df.registerTempTable("input")


    val fmt1 = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")
    val col1 = intervalToSeq((start to end) , 1.day).map(d => TRow(fmt1.print(d)))

    val df1 = createDataFrame[TRow](sparkContext.parallelize(col1))
    df1.registerTempTable("input1")

  }
}
