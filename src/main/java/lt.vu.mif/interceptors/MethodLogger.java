package lt.vu.mif.interceptors;

import lt.vu.mif.entities.LogEntry;
import lt.vu.mif.persistence.LogEntriesDAO;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

@Interceptor
@LoggedInvocation
public class MethodLogger implements Serializable{
    @Inject
    private LogEntriesDAO logEntriesDAO;

    @AroundInvoke
    public Object logMethodInvocation(InvocationContext context) throws Exception {
        LogEntry logEntry = new LogEntry();
        logEntry.setUsername("user1");
        logEntry.setUserRights("guest");
        logEntry.setClassAndMethodName(context.getMethod().getDeclaringClass().getName() + "." + context.getMethod().getName());
        logEntry.setDatetime(new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss zZ").format(new Date()));
        logEntriesDAO.persist(logEntry);
        return context.proceed();
    }
}
