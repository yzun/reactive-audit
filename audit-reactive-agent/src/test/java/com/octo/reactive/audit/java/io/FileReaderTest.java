/*
 * Copyright 2014 OCTO Technology
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

package com.octo.reactive.audit.java.io;

import com.octo.reactive.audit.AuditReactive;
import com.octo.reactive.audit.IOTestTools;
import com.octo.reactive.audit.lib.FileAuditReactiveException;
import org.junit.Test;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import static com.octo.reactive.audit.TestTools.pop;
import static com.octo.reactive.audit.TestTools.push;

public class FileReaderTest extends InputStreamReaderTest
{
	@SuppressWarnings("RefusedBequest")
    @Override
	protected Reader newReader() throws IOException
	{
		push();
		File fileIn = IOTestTools.getTempFile();
		pop();
		return new FileReader(fileIn);
	}

	@SuppressWarnings("RefusedBequest")
    @Test(expected = FileAuditReactiveException.class)
	@Override
	public void New() throws IOException
	{
		AuditReactive.strict.commit();
		try (Reader in = new FileReader(IOTestTools.getTempFile()))
		{
			AuditReactive.off.commit();
		}
	}

	@Test(expected = FileAuditReactiveException.class)
	public void New_String() throws IOException
	{
		AuditReactive.strict.commit();
		try (Reader in = new FileReader(IOTestTools.getTempFile().getName()))
		{
			AuditReactive.off.commit();
		}
	}

	@Test(expected = FileAuditReactiveException.class)
	public void derived() throws IOException
	{
		class Derived extends FileReader
		{
			Derived() throws IOException
			{
				super(IOTestTools.getTempFile().getName());
			}
		}
		new Derived();
	}
}
