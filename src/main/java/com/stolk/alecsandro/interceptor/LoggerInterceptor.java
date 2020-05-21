package com.stolk.alecsandro.interceptor;

import javax.annotation.Priority;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.validation.ConstraintViolationException;
import java.util.logging.Logger;

@Interceptor
@Priority(1)
@com.stolk.alecsandro.interceptor.Logger
public class LoggerInterceptor {

    @AroundInvoke
    public Object treatException(InvocationContext context) throws Exception {
        Logger logger = Logger.getLogger(context.getTarget().getClass().getName());
        try {
            return context.proceed();
        } catch (Exception e) {
            if (e instanceof ConstraintViolationException) {
                logger.info(e.getMessage());
            } else {
                logger.severe(e.getMessage());
            }
            throw e;
        }
    }

}
