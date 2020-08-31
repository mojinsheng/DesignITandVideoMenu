package com.anwahh.designitandvideomenu.utils;

import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Process;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.LineNumberReader;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;

//TODO DOC whole class
public abstract class Logger {

    public static final String GLOBAL_TAG = "WT_SDK";
    public static final String STORE_TAG = "WT_SDK_STORE";
    public static final String TOOLS_TAG = "FSDK_TOOLS";

    private static int logLevel = Log.VERBOSE;
    private static volatile boolean isDebug = false;
    private static volatile boolean logToFile;

    //FIXME writable Logger implement has performance issue, design this better
    private static final WritableLogger writableLogger = new WritableLogger();

    public static void setLogEnable(boolean enable){
        isDebug = enable;
    }

    public static boolean isLogEnable(){
        return isDebug;
    }

    public static void setLogLevel(int level){
        logLevel = level;
    }

    public static int getLogLevel() {
        return logLevel;
    }

    public static boolean isVerboseEnabled() {
        return isDebug && logLevel <= Log.VERBOSE;
    }

    public static boolean isDebugEnabled() {
        return isDebug && logLevel <= Log.DEBUG;
    }

    public static boolean isInfoEnabled() {
        return isDebug && logLevel <= Log.INFO;
    }

    public static boolean isWarningEnabled() {
        return isDebug && logLevel <= Log.WARN;
    }

    public static boolean isErrorEnabled() {
        return isDebug && logLevel <= Log.ERROR;
    }

    public static boolean isLogToFile() {
        return logToFile;
    }

    /**
     * Enables or disables writable logger.
     *
     * @param context
     * @param enabled
     */
    public static synchronized void toggleWritableLogger(Context context, boolean enabled) {
        if (enabled) {
            writableLogger.init(context);
        } else {
            logToFile = false;
        }

        writableLogger.disable(!enabled);
    }

    public static WritableLogger getWritableLogger() {
        return writableLogger;
    }

    public static void verbose(String message) {
        if (isVerboseEnabled()) {
            doLog(Log.VERBOSE, null, message, null);
        }
    }

    public static void verbose(String tag, String message) {
        if (isVerboseEnabled()) {
            doLog(Log.VERBOSE, tag, message, null);
        }
    }

    public static void verbose(String tag, String message, Throwable e) {
        if (isVerboseEnabled()) {
            doLog(Log.VERBOSE, tag, message, new Object[]{e});
        }
    }

    public static void verbose(String message, Object param) {
        if (isVerboseEnabled()) {
            doLog(Log.VERBOSE, null, message, new Object[]{param});
        }
    }

    public static void verbose(String message, Object param1, Object param2) {
        if (isVerboseEnabled()) {
            doLog(Log.VERBOSE, null, message, new Object[]{param1, param2});
        }
    }

    public static void verbose(String message, Object... params) {
        if (isVerboseEnabled()) {
            doLog(Log.VERBOSE, null, message, params);
        }
    }

    public static void verbose(String tag, String message, Object param) {
        if (isVerboseEnabled()) {
            doLog(Log.VERBOSE, tag, message, new Object[]{param});
        }
    }

    public static void verbose(String tag, String message, Object param1, Object param2) {
        if (isVerboseEnabled()) {
            doLog(Log.VERBOSE, tag, message, new Object[]{param1, param2});
        }
    }

    public static void verbose(String tag, String message, Object... params) {
        if (isVerboseEnabled()) {
            doLog(Log.VERBOSE, tag, message, params);
        }
    }

    public static void debug(String message) {
        if (isDebugEnabled()) {
            doLog(Log.DEBUG, null, message, null);
        }
    }

    public static void debug(String tag, String message) {
        if (isDebugEnabled()) {
            doLog(Log.DEBUG, tag, message, null);
        }
    }

    public static void debug(String tag, String message, Throwable e) {
        if (isDebugEnabled()) {
            doLog(Log.DEBUG, tag, message, new Object[]{e});
        }
    }

    public static void debug(String message, Object param) {
        if (isDebugEnabled()) {
            doLog(Log.DEBUG, null, message, new Object[]{param});
        }
    }

    public static void debug(String message, Object param1, Object param2) {
        if (isDebugEnabled()) {
            doLog(Log.DEBUG, null, message, new Object[]{param1, param2});
        }
    }

    public static void debug(String message, Object... params) {
        if (isDebugEnabled()) {
            doLog(Log.DEBUG, null, message, params);
        }
    }

    public static void debug(String tag, String message, Object param) {
        if (isDebugEnabled()) {
            doLog(Log.DEBUG, tag, message, new Object[]{param});
        }
    }

    public static void debug(String tag, String message, Object param1, Object param2) {
        if (isDebugEnabled()) {
            doLog(Log.DEBUG, tag, message, new Object[]{param1, param2});
        }
    }

    public static void debug(String tag, String message, Object... params) {
        if (isDebugEnabled()) {
            doLog(Log.DEBUG, tag, message, params);
        }
    }

    public static void info(String message) {
        if (isInfoEnabled()) {
            doLog(Log.INFO, null, message, null);
        }
    }

    public static void info(String tag, String message) {
        if (isInfoEnabled()) {
            doLog(Log.INFO, tag, message, null);
        }
    }

    public static void info(String tag, String message, Throwable e) {
        if (isInfoEnabled()) {
            doLog(Log.INFO, tag, message, new Object[]{e});
        }
    }

    public static void info(String message, Object param) {
        if (isInfoEnabled()) {
            doLog(Log.INFO, null, message, new Object[]{param});
        }
    }

    public static void info(String message, Object param1, Object param2) {
        if (isInfoEnabled()) {
            doLog(Log.INFO, null, message, new Object[]{param1, param2});
        }
    }

    public static void info(String message, Object... params) {
        if (isInfoEnabled()) {
            doLog(Log.INFO, null, message, params);
        }
    }

    public static void info(String tag, String message, Object param) {
        if (isInfoEnabled()) {
            doLog(Log.INFO, tag, message, new Object[]{param});
        }
    }

    public static void info(String tag, String message, Object param1, Object param2) {
        if (isInfoEnabled()) {
            doLog(Log.INFO, tag, message, new Object[]{param1, param2});
        }
    }

    public static void info(String tag, String message, Object... params) {
        if (isInfoEnabled()) {
            doLog(Log.INFO, tag, message, params);
        }
    }

    public static void warning(String message) {
        if (isWarningEnabled()) {
            doLog(Log.WARN, null, message, null);
        }
    }

    public static void warning(String tag, String message) {
        if (isWarningEnabled()) {
            doLog(Log.WARN, tag, message, null);
        }
    }

    public static void warning(String tag, String message, Throwable e) {
        if (isWarningEnabled()) {
            doLog(Log.WARN, tag, message, new Object[]{e});
        }
    }

    public static void warning(String message, Object param) {
        if (isWarningEnabled()) {
            doLog(Log.WARN, null, message, new Object[]{param});
        }
    }

    public static void warning(String message, Object param1, Object param2) {
        if (isWarningEnabled()) {
            doLog(Log.WARN, null, message, new Object[]{param1, param2});
        }
    }

    public static void warning(String message, Object... params) {
        if (isWarningEnabled()) {
            doLog(Log.WARN, null, message, params);
        }
    }

    public static void warning(String tag, String message, Object param) {
        if (isWarningEnabled()) {
            doLog(Log.WARN, tag, message, new Object[]{param});
        }
    }

    public static void warning(String tag, String message, Object param1, Object param2) {
        if (isWarningEnabled()) {
            doLog(Log.WARN, tag, message, new Object[]{param1, param2});
        }
    }

    public static void warning(String tag, String message, Object... params) {
        if (isWarningEnabled()) {
            doLog(Log.WARN, tag, message, params);
        }
    }

    public static void error(String message) {
        if (isErrorEnabled()) {
            doLog(Log.ERROR, null, message, null);
        }
    }

    public static void error(String tag, String message) {
        if (isErrorEnabled()) {
            doLog(Log.ERROR, tag, message, null);
        }
    }

    public static void error(String tag, String message, Throwable e) {
        if (isErrorEnabled()) {
            doLog(Log.ERROR, tag, message, new Object[]{e});
        }
    }

    public static void error(String message, Object param) {
        if (isErrorEnabled()) {
            doLog(Log.ERROR, null, message, new Object[]{param});
        }
    }

    public static void error(String message, Object param1, Object param2) {
        if (isErrorEnabled()) {
            doLog(Log.ERROR, null, message, new Object[]{param1, param2});
        }
    }

    public static void error(String message, Object... params) {
        if (isErrorEnabled()) {
            doLog(Log.ERROR, null, message, params);
        }
    }

    public static void error(String tag, String message, Object param) {
        if (isErrorEnabled()) {
            doLog(Log.ERROR, tag, message, new Object[]{param});
        }
    }

    public static void error(String tag, String message, Object param1, Object param2) {
        if (isErrorEnabled()) {
            doLog(Log.ERROR, tag, message, new Object[]{param1, param2});
        }
    }

    public static void error(String tag, String message, Object... params) {
        if (isErrorEnabled()) {
            doLog(Log.ERROR, tag, message, params);
        }
    }

    private static void doLog(int level, String tag, String message, Object[] params) {
        Throwable throwable = extractThrowable(params);
        if (throwable != null) {
            params = trimParams(params);
        }


        String formattedMsg = "";
        if (message != null && params != null) {
            formattedMsg = String.format(message, params);
        } else if (message != null) {
            formattedMsg = message;
        }

        String msgWithTrace = formatTrace(formattedMsg);

        String finalTag = tag != null ? GLOBAL_TAG : formatTag();

        if (throwable == null) {
            androidLog(level, finalTag, msgWithTrace);
        } else {
            //fix android log bug when meet UnknowHostException, this could be removed in the future when android team fix this bug
            if(throwable instanceof UnknownHostException){
                msgWithTrace = msgWithTrace + "\nException: " + throwable.toString();
            }
            androidLog(level, finalTag, msgWithTrace, throwable);
        }

        if (logToFile) {
            writeToFile(tag, msgWithTrace, throwable);
        }
    }

    private static void androidLog(int level, String finalTag, String msgWithTrace) {
        switch (level) {
            case Log.VERBOSE:
                Log.v(finalTag, msgWithTrace);
                break;
            case Log.DEBUG:
                Log.d(finalTag, msgWithTrace);
                break;
            case Log.INFO:
                Log.i(finalTag, msgWithTrace);
                break;
            case Log.WARN:
                Log.w(finalTag, msgWithTrace);
                break;
            case Log.ERROR:
                Log.e(finalTag, msgWithTrace);
                break;
        }
    }

    private static void androidLog(int level, String finalTag, String msgWithTrace, Throwable throwable) {
        switch (level) {
            case Log.VERBOSE:
                Log.v(finalTag, msgWithTrace, throwable);
                break;
            case Log.DEBUG:
                Log.d(finalTag, msgWithTrace, throwable);
                break;
            case Log.INFO:
                Log.i(finalTag, msgWithTrace, throwable);
                break;
            case Log.WARN:
                Log.w(finalTag, msgWithTrace, throwable);
                break;
            case Log.ERROR:
                Log.e(finalTag, msgWithTrace, throwable);
                break;
        }
    }

    private static Throwable extractThrowable(Object[] params) {
        if (params == null || params.length == 0)
            return null;

        Object last = params[params.length - 1];

        return last instanceof Throwable ? (Throwable) last : null;
    }

    private static Object[] trimParams(Object[] params) {
        if (params == null || params.length == 0){
            throw new IllegalArgumentException("params is null or empty");
        }

        Object[] trimmedParams = new Object[params.length - 1];
        System.arraycopy(params, 0, trimmedParams, 0, trimmedParams.length);

        return trimmedParams;
    }

    private static String formatTrace(String message) {
        return formatTrace(message, 4);
    }

    private static String formatTrace(String message, int level) {
        Throwable throwable = new Throwable();
        StackTraceElement traceElement = throwable.getStackTrace()[Math
                .min(level, throwable.getStackTrace().length - 1)]; // to prevent crashing when inlined by Proguard
        return "[(" + traceElement.getFileName() + ":" + traceElement.getLineNumber() + "): "
                + traceElement.getMethodName() + "()]: " + message;
    }

    private static String formatTag() {
        return GLOBAL_TAG + ": " + Thread.currentThread().getName();
    }

    public static void writeToFile(String tag, String message) {
        writeToFile(tag, message, null);
    }

    public static void writeToFile(String tag, String message, Throwable exception) {

        writableLogger.writeToFile(tag, message, exception);
    }

    public static boolean removeLogFile(File file) {
        return file.delete();
    }

    static class WritableLogger {
        private static final String TAG = GLOBAL_TAG + WritableLogger.class.getSimpleName();
        private File mLogFile;
        private SimpleDateFormat mSimpleDateFormat;
        private Date mDate;
        private Handler mHandler;
        private File externalStorageDir;

        /**
         * -1 init was never called
         * 0 is still initing in BG thread
         * 1 is inited
         */
        private volatile int mInited = -1;

        /**
         * If writablelogger has already been inited it's then easier to disable it here
         * if needed because of concurrency issue that might arose otherwise
         */
        private volatile boolean mDisabled;

        /**
         * @param context
         * @return true if init was success
         */
        public synchronized void init(final Context context) {
            if (mInited != -1) {
                Logger.warning(TAG, "Init has already been called, returning...");
                return;
            }

            mInited = 0;
            Logger.debug(TAG, "Initing WritableLogger");
            startWorkerThread();

            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    try {
                        String state = Environment.getExternalStorageState();
                        if (!Environment.MEDIA_MOUNTED.equals(state))
                            throw new IllegalStateException("External storage not mounted. Returning...");

                        File externalFilesDir = getExternalFilesDir(context);

                        mLogFile = new File(externalFilesDir, "o7logger.txt");
                        createLogFile();

                        mSimpleDateFormat = new SimpleDateFormat("MM-dd HH:mm:ss.SSS");
                        mDate = new Date();

                        Logger.logToFile = true;
                        mInited = 1;


                        //final String deviceName = MD5decode.getDeviceName();
                        writeToFile(TAG, "\n", null);
                        writeToFile(TAG, "######################################################################", null);
                        writeToFile(TAG, "##### Clean App Start: " + mDate, null);

                       // writeToFile(TAG, "##### Device name: " + deviceName, null);
                        writeToFile(TAG, "##### Android version: " + Build.VERSION.SDK_INT, null);
                        writeToFile(TAG, "######################################################################\n", null);
                    } catch (IOException e) {
                        Logger.error(TAG, "", e);
                        mInited = -1;
                        Logger.logToFile = false;
                        disable(true);
                    }
                }
            });
        }

        private void createLogFile() throws IOException {
            if (!mLogFile.exists()) {
                mLogFile.createNewFile();
            }
            if (Logger.isDebugEnabled()) {
                Logger.debug(TAG, "Logfile created at location: %s", mLogFile.getAbsolutePath());
            }
        }

        private File getExternalFilesDir(Context context) {
            File dir;

            // Unity seems to have a problem returning this in
            // onCreate on clean install. So we retry a bit later
            // http://stackoverflow.com/questions/20547771/context-getexternalfilesdirnull-is-null
            while ((dir = context.getExternalFilesDir(null)) == null) {
                Logger.error(TAG, "Could not get context.getExternalFilesDir(null). Retrying...");
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            externalStorageDir = dir;

            return dir;
        }

        private void startWorkerThread() {
            if (mHandler != null) {
                Logger.warning(TAG, "handler thread already created, returning");
                return;
            }

            HandlerThread handlerThread = new HandlerThread("WritableLogger", Process.THREAD_PRIORITY_BACKGROUND);
            handlerThread.start();
            mHandler = new Handler(handlerThread.getLooper());
        }

        public synchronized void writeToFile(final String tag, final String message, final Throwable exception) {
            if (mInited != 1) {
                Logger.error(TAG, "Did you call Logger.enableWritableLogger()");
                return;
            }

            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    BufferedWriter buf = null;
                    try {
                        if (mDisabled) {
                            Logger.debug(TAG, "WritableLogger was disabled");
                            return;
                        }

                        checkFileSize();

                        mDate.setTime(System.currentTimeMillis());
                        String exceptionMsg = exception == null ? "" : Log.getStackTraceString(exception);

                        // BufferedWriter for performance, true to set append to file flag
                        buf = new BufferedWriter(new FileWriter(mLogFile, true));
                        buf.append(mSimpleDateFormat.format(mDate) + "  " + tag + ": " + message + " " + exceptionMsg);
                        buf.newLine();
                    } catch (IOException e) {
                        Logger.error(TAG, "WriteToFile() Write to o7logger.txt failed, disabling", e);
                        disable(true);
                        Logger.logToFile = false;
                    } finally {
                        if(buf != null) {
                            try {
                                buf.close();
                            }catch (IOException e){
                                Logger.warning(TAG, "Exception happen when trying to close BufferedWriter, this may cause a memory leak");
                            }
                        }
                    }
                }
            });
        }

        private void checkFileSize() {
            // If log file size is greater than 5mb
            if (mLogFile.length() / 1024 > 5120 && externalStorageDir != null) {

                try {
                    File logFileTemp = new File(externalStorageDir, "o7loggerTemp.txt");
                    logFileTemp.delete(); // Delete the previous log file

                    // Get the number of all the lines in the log file
                    LineNumberReader lnr = new LineNumberReader(new FileReader(mLogFile));
                    lnr.skip(Long.MAX_VALUE);
                    lnr.close();

                    int nrOfLinesInFile = lnr.getLineNumber() + 1; // +1 because counting starts with 0
                    int lineCounter = 0;

                    BufferedReader reader = new BufferedReader(new FileReader(mLogFile));
                    BufferedWriter writer = new BufferedWriter(new FileWriter(logFileTemp, true));

                    String currentLine;
                    while ((currentLine = reader.readLine()) != null) {
                        // Only write second half of the lines from the log file to the temp file
                        if (lineCounter > nrOfLinesInFile / 2) {
                            writer.append(currentLine);
                            writer.newLine();
                        }

                        lineCounter++;
                    }

                    writer.close();
                    reader.close();

                    // Copy the temp file to the original log file

                } catch (FileNotFoundException e) {
                    Logger.debug(TAG, "Logger writable Logger checkFileSize exception", e);
                } catch (IOException e) {
                    Logger.debug(TAG, "Logger writable Logger checkFileSize exception", e);
                }
            }
        }

        public void disable(boolean disabled) {
            mDisabled = disabled;
        }

        public File getWritableLogFile() {
            return mLogFile;
        }
    }
}