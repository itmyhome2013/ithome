package com.farm.log4j.appender;

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.MDC;
import org.apache.log4j.spi.LoggingEvent;

import com.farm.console.server.contain.AloneApplogManagerInter;
import com.farm.web.spring.BeanFactory;

public class FarmDbAppender extends AppenderSkeleton {
	private final static AloneApplogManagerInter aloneIMP = (AloneApplogManagerInter) BeanFactory
			.getBean("alone_applogProxyId");

	@Override
	protected void append(LoggingEvent arg0) {
		String userid = MDC.get("USERID") != null ? MDC.get("USERID")
				.toString() : "NONE";
		String ip = MDC.get("IP") != null ? MDC.get("IP").toString() : "NONE";
		aloneIMP.log(arg0.getMessage().toString(), userid, arg0.getLevel()
				.toString(), arg0.getLocationInformation().getMethodName(),
				arg0.getLocationInformation().getClassName(), ip);
	}

	@Override
	public void close() {
		return;
	}

	@Override
	public boolean requiresLayout() {
		return false;
	}

}
