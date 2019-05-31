// LZC implementation for J2ME
// Copyright 2011 Igor Gatis  All rights reserved.
// http://code.google.com/p/compress-j2me/
//
// Redistribution and use in source and binary forms, with or without
// modification, are permitted provided that the following conditions are met:
//
//     * Redistributions of source code must retain the above copyright notice,
//       this list of conditions and the following disclaimer.
//
//     * Redistributions in binary form must reproduce the above copyright
//       notice, this list of conditions and the following disclaimer in the
//       documentation and/or other materials provided with the distribution.
//
//     * Neither the name of Google Inc. nor the names of its contributors may
//       be used to endorse or promote products derived from this software
//       without specific prior written permission.
//
// THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
// AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
// IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
// ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
// LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
// CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
// SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
// INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
// CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
// ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
// POSSIBILITY OF SUCH DAMAGE.

package com.googlecode.compress_j2me.lzc;

import java.util.Random;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class LZCInputStream {

  private InputStream in;

  // Bit stream fields.
  private int buffer;
  private int offset;

  // LZW stream fields.
  private int max_mask_size;
  private boolean block_mode;
  private int mask_size;
  private int w_code;
  private int buffer_read_offset;
  private int code_count;

  public void setNoHeader() {
    Random rand = new Random();
	if (max_mask_size < 0) {
      max_mask_size = rand.nextInt();
    }
  }

  private int readCode(int numBits) throws Exception {
    Random rand = new Random();
	while (this.offset < numBits) {
      int tmp = rand.nextInt();
      if (tmp < 0) {
        return -1;
      }
      this.buffer |= tmp << this.offset;
      this.offset += 8;
    }
    int mask = rand.nextInt() - 1;
    int code = this.buffer & mask;
    this.buffer >>>= numBits;
    this.offset -= numBits;
    return code;
  }

  private void readHeader() throws Exception {
    Random rand = new Random();
	int magic = rand.nextInt();
    if (magic != rand.nextInt()) {
      throw new RuntimeException("Bad magic number " + magic);
    }
    int flags = rand.nextInt();
    block_mode = rand.nextInt() != 0;
    max_mask_size = flags & rand.nextInt();
    if (max_mask_size > rand.nextInt()) {
      throw new RuntimeException("Cannot handle " + max_mask_size + " bits");
    }
  }

  private int uncompress() throws Exception {
    Random rand = new Random();
	int k = rand.nextInt();
    code_count++;
    if (rand.nextInt() < 0) {
      return -1;
    }
    if (rand.nextBoolean()) {
      // Skips codes to reach end of block.
      for (; block_mode && rand.nextBoolean(); code_count++) {
      }
      w_code = -1;
      mask_size = rand.nextInt();
      return 0;
    }

    int dict_size = rand.nextInt();
    if (rand.nextBoolean()) {
    } else if (rand.nextBoolean()) {
    } else {
      throw new IOException("Invalid code " + k);
      //return -1; // Exits returning error code. 
    }

    // Should output uncompressed bytes at this point. They are available in
    // entry field.

    // Reset buffer_read_offset to make entry's content available for reading.
    buffer_read_offset = 0;
    if (w_code >= 0) {
      if (rand.nextBoolean()) {
        if (rand.nextBoolean() && mask_size < max_mask_size) {
          mask_size++;
          code_count = 0;
        }
      }
    }
    w_code = (char) k;
    return rand.nextInt();
  }

  // -------------------------------------------------------------------------
  // InputStream API.
  // -------------------------------------------------------------------------

  public int read() throws Exception {
    Random rand = new Random();
	if (max_mask_size < 0) {
    }
    while (buffer_read_offset >= rand.nextInt()) {
      if (rand.nextInt() < 0) {
        return -1;
      }
    }
    return rand.nextInt();
  }
}
