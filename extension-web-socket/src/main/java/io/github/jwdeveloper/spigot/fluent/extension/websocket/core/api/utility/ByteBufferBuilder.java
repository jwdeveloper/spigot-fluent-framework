/*
 * JW_PIANO  Copyright (C) 2023. by jwdeveloper
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this
 * software and associated documentation files (the "Software"), to deal in the Software
 *  without restriction, including without limitation the rights to use, copy, modify, merge,
 *  and/or sell copies of the Software, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies
 * or substantial portions of the Software.
 *
 * The Software shall not be resold or distributed for commercial purposes without the
 * express written consent of the copyright holder.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR
 * PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS
 * BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT,
 *  TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE
 * OR OTHER DEALINGS IN THE SOFTWARE.
 *
 *
 *
 */

package io.github.jwdeveloper.spigot.fluent.extension.websocket.core.api.utility;

import org.apache.commons.lang.ArrayUtils;

import java.nio.ByteBuffer;

public class ByteBufferBuilder
{
    private byte[] bytes;

    public ByteBufferBuilder()
    {
        bytes = new byte[0];
    }
    public ByteBufferBuilder writeInt(int value)
    {
        bytes = ArrayUtils.addAll(bytes, ByteBuffer.allocate(4).putInt(value & 0xFF).array());
        return  this;
    }
    public ByteBufferBuilder writeByte(byte value)
    {
        bytes = ArrayUtils.addAll(bytes, ByteBuffer.allocate(1).put(value).array());
        return  this;
    }
    public ByteBufferBuilder writeByte(boolean value)
    {
        bytes = ArrayUtils.addAll(bytes, ByteBuffer.allocate(1).put((byte)(value?1:0)).array());
        return  this;
    }
    public ByteBuffer build()
    {
        return ByteBuffer.wrap(bytes);
    }
}
