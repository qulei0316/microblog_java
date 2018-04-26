package com.qulei.common.util;

import java.io.PrintWriter;
import java.io.StringWriter;

public class LogUtil {
    /**
     * 将错误栈输出到字符串
     * @param exception
     * @return
     */
    public static String getExceptionStackTrace(Exception exception) {
        String strStackTrace = null;

        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);

        exception.printStackTrace(printWriter);
        printWriter.flush();

        strStackTrace = stringWriter.toString();

        printWriter.close();

        return strStackTrace;
    }
}
