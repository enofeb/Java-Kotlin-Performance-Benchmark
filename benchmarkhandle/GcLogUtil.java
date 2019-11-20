package benchmarkhandle;

import java.lang.management.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.management.openmbean.CompositeData;
import javax.management.*;

import com.sun.management.GarbageCollectionNotificationInfo;
import com.sun.management.GcInfo;
import result.ConvertResultToFile;

public class GcLogUtil {

    static public void startLoggingGc() {
        // http://www.programcreek.com/java-api-examples/index.php?class=javax.management.MBeanServerConnection&method=addNotificationListener
        // https://docs.oracle.com/javase/8/docs/jre/api/management/extension/com/sun/management/GarbageCollectionNotificationInfo.html#GARBAGE_COLLECTION_NOTIFICATION
        for (GarbageCollectorMXBean gcMbean : ManagementFactory.getGarbageCollectorMXBeans()) {
            try {
                ManagementFactory.getPlatformMBeanServer().addNotificationListener(gcMbean.getObjectName(), listener,
                        null, null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    static public NotificationListener listener = new NotificationListener() {
        List<GCData> gcInfoList = new ArrayList<>();

        @Override
        public void handleNotification(Notification notification, Object handback) {
            if (notification.getType().equals(GarbageCollectionNotificationInfo.GARBAGE_COLLECTION_NOTIFICATION)) {
                // https://docs.oracle.com/javase/8/docs/jre/api/management/extension/com/sun/management/GarbageCollectionNotificationInfo.html
                CompositeData cd = (CompositeData) notification.getUserData();
                GarbageCollectionNotificationInfo gcNotificationInfo = GarbageCollectionNotificationInfo.from(cd);
                GcInfo gcInfo = gcNotificationInfo.getGcInfo();
                System.out.println("Start Time:" + gcInfo.getStartTime() + " End Time: " + gcInfo.getEndTime() +
                        " Before Mem: " + sumUsedMb(gcInfo.getMemoryUsageBeforeGc()) + "MB " +
                        "After Mem: " + sumUsedMb(gcInfo.getMemoryUsageAfterGc()) + "MB");
                ConvertResultToFile convertResultToFile = new ConvertResultToFile();
                GCData gcData = new GCData(gcInfo.getStartTime(), gcInfo.getEndTime(), gcInfo.getDuration(), sumUsedMb(gcInfo.getMemoryUsageBeforeGc()), sumUsedMb(gcInfo.getMemoryUsageAfterGc()),
                        sumUsedMb(gcInfo.getMemoryUsageBeforeGc())-sumUsedMb(gcInfo.getMemoryUsageAfterGc()));
                gcInfoList.add(gcData);
                convertResultToFile.writeGCResultToFile(gcInfoList, "Garbage Collector Results");
            }
        }
    };

    static private long sumUsedMb(Map<String, MemoryUsage> memUsages) {
        long sum = 0;
        for (MemoryUsage memoryUsage : memUsages.values()) {
            sum += memoryUsage.getUsed();
        }
        return sum / (1024 * 1024);
    }


    public static void calculationGC(GCData gc) {
        List<GCData> gcDataList = new ArrayList<>();
        gcDataList.add(gc);
        System.out.println(gcDataList.toString());
    }
}