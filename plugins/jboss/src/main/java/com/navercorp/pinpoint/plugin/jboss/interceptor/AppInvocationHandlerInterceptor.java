/*
 * Copyright 2020 NAVER Corp.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.navercorp.pinpoint.plugin.jboss.interceptor;

import com.navercorp.pinpoint.bootstrap.context.MethodDescriptor;
import com.navercorp.pinpoint.bootstrap.context.SpanEventRecorder;
import com.navercorp.pinpoint.bootstrap.context.TraceContext;
import com.navercorp.pinpoint.bootstrap.interceptor.SpanEventSimpleAroundInterceptorForPlugin;
import com.navercorp.pinpoint.plugin.jboss.JbossConstants;

/**
 * The Class AppInvocationHandlerInterceptor.
 * 
 * @author <a href="mailto:suraj.raturi89@gmail.com">Suraj Raturi</a>
 */
public class AppInvocationHandlerInterceptor extends SpanEventSimpleAroundInterceptorForPlugin {

	/**
	 * Instantiates a new app invocation handler interceptor.
	 *
	 * @param traceContext
	 *            the trace context
	 * @param descriptor
	 *            the descriptor
	 */
	public AppInvocationHandlerInterceptor(TraceContext traceContext, MethodDescriptor descriptor) {
		super(traceContext, descriptor);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.navercorp.pinpoint.bootstrap.interceptor.
	 * SpanEventSimpleAroundInterceptorForPlugin#doInBeforeTrace(com.navercorp.
	 * pinpoint.bootstrap.context.SpanEventRecorder, java.lang.Object,
	 * java.lang.Object[])
	 */
	@Override
	protected void doInBeforeTrace(SpanEventRecorder recorder, Object target, Object[] args) throws Exception {
		recorder.recordServiceType(JbossConstants.JBOSS_METHOD);
		final String serverHostName = System.getProperty("jboss.host.name", "");
		recorder.recordEndPoint(serverHostName);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.navercorp.pinpoint.bootstrap.interceptor.
	 * SpanEventSimpleAroundInterceptorForPlugin#doInAfterTrace(com.navercorp.
	 * pinpoint.bootstrap.context.SpanEventRecorder, java.lang.Object,
	 * java.lang.Object[], java.lang.Object, java.lang.Throwable)
	 */
	@Override
	protected void doInAfterTrace(SpanEventRecorder recorder, Object target, Object[] args, Object result,
			Throwable throwable) throws Exception {
		recorder.recordApi(methodDescriptor);
		recorder.recordException(throwable);
	}

}
