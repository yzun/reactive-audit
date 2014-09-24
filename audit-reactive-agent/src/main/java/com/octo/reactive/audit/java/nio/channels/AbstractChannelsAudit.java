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

package com.octo.reactive.audit.java.nio.channels;

import com.octo.reactive.audit.AbstractAudit;
import com.octo.reactive.audit.FactoryException;
import com.octo.reactive.audit.lib.AuditReactiveException;
import com.octo.reactive.audit.lib.Latency;
import org.aspectj.lang.JoinPoint;

import java.nio.channels.FileChannel;

@SuppressWarnings("RefusedBequest")
class AbstractChannelsAudit extends AbstractAudit
{
	@Override
    protected void latency(Latency latency, JoinPoint thisJoinPoint)
	{
		AuditReactiveException ex;
		if (thisJoinPoint.getTarget() instanceof FileChannel)
			ex = FactoryException.newFile(latency, thisJoinPoint);
		else
			ex = FactoryException.newNetwork(latency, thisJoinPoint);
		super.logLatency(latency, thisJoinPoint, ex);
	}

	@Override
	protected AuditReactiveException newException(Latency latency, JoinPoint thisJoinPoint)
	{
		return FactoryException.newNetwork(latency, thisJoinPoint);
	}
}
