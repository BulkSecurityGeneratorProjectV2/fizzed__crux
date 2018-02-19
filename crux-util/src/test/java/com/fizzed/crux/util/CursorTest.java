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

import java.util.Optional;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import org.junit.Test;

public class CursorTest {
    
    @Test
    public void parse() throws InterruptedException {
        Cursor cursor = Cursor.parse("l:0,o:0").orElse(null);
        
        assertThat(cursor.getOffset(), is(0L));
        assertThat(cursor.getLimit(), is(0L));
        assertThat(cursor.getBefore(), is(nullValue()));
        assertThat(cursor.getAfter(), is(nullValue()));
        assertThat(cursor.toString(), is("o:0,l:0"));
        
        cursor = Cursor.parse("o:99,l:100,a:123456789,b:223456789").orElse(null);
        
        assertThat(cursor.getOffset(), is(99L));
        assertThat(cursor.getLimit(), is(100L));
        assertThat(cursor.getBefore(), is(223456789L));
        assertThat(cursor.getAfter(), is(123456789L));
        assertThat(cursor.toString(), is("o:99,l:100,b:223456789,a:123456789"));
        
        assertThat(Cursor.parse(null), is(Optional.empty()));
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void badTag() throws InterruptedException {
        Cursor cursor = Cursor.parse("q:0,o:0").orElse(null);
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void noValue() throws InterruptedException {
        Cursor cursor = Cursor.parse("l:,o:0").orElse(null);
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void noValue2() throws InterruptedException {
        Cursor cursor = Cursor.parse("l,o:0").orElse(null);
    }
    
}