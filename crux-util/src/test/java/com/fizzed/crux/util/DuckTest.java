/*
 * Copyright 2018 Fizzed, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.fizzed.crux.util;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import org.junit.Test;

public class DuckTest {
 
    @Test
    public void asLong() {
        assertThat(Duck.of(1).asLong(), is(1L));
        assertThat(Duck.of((short)1).asLong(), is(1L));
        assertThat(Duck.of((byte)1).asLong(), is(1L));
        assertThat(Duck.of((double)1.1d).asLong(), is(1L));
        assertThat(Duck.of("1").asLong(), is(1L));
        assertThat(Duck.of("0x1").asLong(), is(1L));
        assertThat(Duck.of("1.1").asLong(), is(1L));    // this throws an exception
    }
    
}