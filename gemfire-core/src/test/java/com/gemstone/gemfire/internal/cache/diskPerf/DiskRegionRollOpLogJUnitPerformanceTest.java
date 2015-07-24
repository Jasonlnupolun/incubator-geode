/*=========================================================================
 * Copyright (c) 2010-2014 Pivotal Software, Inc. All Rights Reserved.
 * This product is protected by U.S. and international copyright
 * and intellectual property laws. Pivotal products are covered by
 * one or more patents listed at http://www.pivotal.io/patents.
 *=========================================================================
 */
package com.gemstone.gemfire.internal.cache.diskPerf;

import java.util.*;
import java.io.File;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import static org.junit.Assert.*;

import com.gemstone.gemfire.*;
import com.gemstone.gemfire.cache.Scope;
import com.gemstone.gemfire.internal.cache.*;
import com.gemstone.gemfire.test.junit.categories.IntegrationTest;

/**
 * Consolidated Disk Region Perftest. Overflow, Persist, OverflowWithPersist
 * modes are tested for Sync, AsyncWithBuffer and AsyncWithoutBufer writes.
 * Roling oplog is set to true with maxOplogSize = 20 mb
 *  
 */
@Category(IntegrationTest.class)
public class DiskRegionRollOpLogJUnitPerformanceTest extends DiskRegionTestingBase
{

  DiskRegionProperties diskProps = new DiskRegionProperties();

  LogWriter log = null;

  String stats = null;

  String stats_ForSameKeyputs = null;

  /**
   * To run DiskRegionRollOpLogPerfJUnitTest to produce the Perf numbers set
   * runPerfTest to true. Also ,one needs to set the VM heap size accordingly.
   * (For example:Default setting in build.xml is <jvmarg value="-Xmx256M"/>
   */
  boolean runPerfTest = false;

  @Before
  public void setUp() throws Exception
  {
    super.setUp();
    diskProps.setDiskDirs(dirs);
    log = ds.getLogWriter();
  }

  @After
  public void tearDown() throws Exception
  {
    super.tearDown();

  }

  //*********Test Cases **************
  //********Overflowonly tests *********
  @Test
  public void testOverflowSyncRollOlg1()
  {
    try {
      //Create four Dirs for Disk Dirs
      File file1 = new File("testOverflowSyncRollOlg1Dir1");
      file1.mkdir();
      file1.deleteOnExit();
      File file2 = new File("testOverflowSyncRollOlg1Dir2");
      file2.mkdir();
      file2.deleteOnExit();
      File file3 = new File("testOverflowSyncRollOlg1Dir3");
      file3.mkdir();
      file3.deleteOnExit();
      File file4 = new File("testOverflowSyncRollOlg1Dir4");
      file4.mkdir();
      file4.deleteOnExit();
      dirs = new File[4];
      dirs[0] = file1;
      dirs[1] = file2;
      dirs[2] = file3;
      dirs[3] = file4;

      diskProps.setRolling(true);
      diskProps.setMaxOplogSize(20971520);
      diskProps.setOverFlowCapacity(1000);
      region = DiskRegionHelperFactory.getSyncOverFlowOnlyRegion(cache,
          diskProps);
    }
    catch (Exception e) {
      e.printStackTrace();
      fail("failed in testOverflowSyncRollOlg1");
    }
    //Perf test for 1kb writes
    if (runPerfTest) {
      populateData0to60k();
      populateData60kto100k();
      System.out.println("OverflowWithSyncRollOlg1:: Stats for 1 kb writes :"
          + stats);
    }
    //Perf test for 1kb writes. Puting values on the same KEY
    /*
     * if(runPerfTest){ populateDataPutOnSameKey();
     * System.out.println("OverflowWithSync1:: Stats for 1 kb writes :"+
     * stats_ForSameKeyputs); }
     */
    //Deleting all the files and logs created during the test...
    deleteFiles();
  } //end of testOverflowSync1

  @Test
  public void testOverflowASyncWithBufferRollOlg2()
  {
    try {
      //    Create four Dirs for Disk Dirs
      File file1 = new File("testOverflowASyncWithBufferRollOlg2Dir1");
      file1.mkdir();
      file1.deleteOnExit();
      File file2 = new File("testOverflowASyncWithBufferRollOlg2Dir2");
      file2.mkdir();
      file2.deleteOnExit();
      File file3 = new File("testOverflowASyncWithBufferRollOlg2Dir3");
      file3.mkdir();
      file3.deleteOnExit();
      File file4 = new File("testOverflowASyncWithBufferRollOlg2Dir4");
      file4.mkdir();
      file4.deleteOnExit();
      dirs = new File[4];
      dirs[0] = file1;
      dirs[1] = file2;
      dirs[2] = file3;
      dirs[3] = file4;

      diskProps.setTimeInterval(15000l);
      diskProps.setBytesThreshold(10000l);
      diskProps.setRolling(true);
      diskProps.setMaxOplogSize(20971520l);
      diskProps.setOverFlowCapacity(1000);
      region = DiskRegionHelperFactory.getAsyncOverFlowOnlyRegion(cache,
          diskProps);
    }
    catch (Exception e) {
      e.printStackTrace();
      fail("failed in testOverflowASyncWithBufferRollOlg2");
    }
    //Perf test for 1kb writes
    if (runPerfTest) {
      populateData0to60k();
      populateData60kto100k();
      System.out
          .println("OverflowASyncWithBufferRollOlg2:: Stats for 1 kb writes :"
              + stats);
    }
    //Perf test for 1kb writes. Puting values on the same KEY
    /*
     * if(runPerfTest){ populateDataPutOnSameKey();
     * System.out.println("OverflowASyncWithBuffer2:: Stats for 1 kb writes :"+
     * stats_ForSameKeyputs); }
     */
    //  Deleting all the files and logs created during the test...
    //deleteFiles();
  } //end of testOverflowASyncWithBuffer2

  @Test
  public void testOverflowASyncWithoutBufferRollOlg3()
  {
    try {
      //    Create four Dirs for Disk Dirs
      File file1 = new File("testOverflowASyncWithoutBufferRollOlg3Dir1");
      file1.mkdir();
      file1.deleteOnExit();
      File file2 = new File("testOverflowASyncWithoutBufferRollOlg3Dir2");
      file2.mkdir();
      file2.deleteOnExit();
      File file3 = new File("testOverflowASyncWithoutBufferRollOlg3Dir3");
      file3.mkdir();
      file3.deleteOnExit();
      File file4 = new File("testOverflowASyncWithoutBufferRollOlg3Dir4");
      file4.mkdir();
      file4.deleteOnExit();
      dirs = new File[4];
      dirs[0] = file1;
      dirs[1] = file2;
      dirs[2] = file3;
      dirs[3] = file4;

      diskProps.setTimeInterval(1000l);
      diskProps.setBytesThreshold(0l);
      diskProps.setRolling(true);
      diskProps.setMaxOplogSize(20971520l);
      diskProps.setOverFlowCapacity(1000);
      region = DiskRegionHelperFactory.getAsyncOverFlowOnlyRegion(cache,
          diskProps);
    }
    catch (Exception e) {
      e.printStackTrace();
      fail("failed in testOverflowASyncWithoutBufferRollOlg3");
    }
    //Perf test for 1kb writes
    if (runPerfTest) {
      populateData0to60k();
      populateData60kto100k();
      System.out
          .println("OverflowASyncWITHOUTBufferRollOlg3 (with DiskWriteAttributes Time-out of 1 Second):: Stats for 1 kb writes :"
              + stats);
    }
    // Perf test for 1kb writes. Puting values on the same KEY
    /*
     * if(runPerfTest){ populateDataPutOnSameKey();
     * System.out.println("OverflowASyncWITHOUTBuffer3:: Stats for 1 kb
     * writes:"+ stats_ForSameKeyputs); }
     */
    //  Deleting all the files and logs created during the test...
    deleteFiles();
  } //end of testOverflowASyncWithoutBuffer3

  //******** PersistOnly Tests ****************
  @Test
  public void testpersistSyncRollOlg4()
  {
    try {
      //    Create four Dirs for Disk Dirs
      File file1 = new File("testpersistSyncRollOlg4Dir1");
      file1.mkdir();
      file1.deleteOnExit();
      File file2 = new File("testpersistSyncRollOlg4Dir2");
      file2.mkdir();
      file2.deleteOnExit();
      File file3 = new File("testpersistSyncRollOlg4Dir3");
      file3.mkdir();
      file3.deleteOnExit();
      File file4 = new File("testpersistSyncRollOlg4Dir4");
      file4.mkdir();
      file4.deleteOnExit();
      dirs = new File[4];
      dirs[0] = file1;
      dirs[1] = file2;
      dirs[2] = file3;
      dirs[3] = file4;

      diskProps.setRolling(true);
      diskProps.setMaxOplogSize(20971520);
      region = DiskRegionHelperFactory.getSyncPersistOnlyRegion(cache,
          diskProps, Scope.LOCAL);
    }
    catch (Exception e) {
      e.printStackTrace();
      fail("failed in testpersistSyncRollOlg4");
    }
    //Perf test for 1kb writes
    if (runPerfTest) {
      populateData0to60k();
      populateData60kto100k();
      System.out.println("PersistOnlySyncRollOlg4:: Stats for 1 kb writes :"
          + stats);
    }
    //Perf test for 1kb writes. Puting values on the same KEY
    /*
     * if(runPerfTest){ populateDataPutOnSameKey();
     * System.out.println("PersistOnlySync4:: Stats for 1 kb writes :"+
     * stats_ForSameKeyputs); }
     */
    //  Deleting all the files and logs created during the test...
    deleteFiles();
    closeDown();
  } //end of testPersistSync4

  @Test
  public void testpersistASyncWithBufferRollOlg5()
  {
    try {
      //    Create four Dirs for Disk Dirs
      File file1 = new File("testpersistASyncWithBufferRollOlg5Dir1");
      file1.mkdir();
      file1.deleteOnExit();
      File file2 = new File("testpersistASyncWithBufferRollOlg5Dir2");
      file2.mkdir();
      file2.deleteOnExit();
      File file3 = new File("testpersistASyncWithBufferRollOlg5Dir3");
      file3.mkdir();
      file3.deleteOnExit();
      File file4 = new File("testpersistASyncWithBufferRollOlg5Dir4");
      file4.mkdir();
      file4.deleteOnExit();
      dirs = new File[4];
      dirs[0] = file1;
      dirs[1] = file2;
      dirs[2] = file3;
      dirs[3] = file4;

      diskProps.setBytesThreshold(10000l);
      diskProps.setTimeInterval(15000l);
      diskProps.setMaxOplogSize(20971520);
      diskProps.setRolling(true);
      region = DiskRegionHelperFactory.getAsyncPersistOnlyRegion(cache,
          diskProps);
    }
    catch (Exception e) {
      e.printStackTrace();
      fail("failed in testpersistASyncWithBufferRollOlg5");
    }
    //Perf test for 1kb writes
    if (runPerfTest) {
      populateData0to60k();
      populateData60kto100k();
      System.out
          .println("PersistASyncWithBufferRollOlg5:: Stats for 1 kb writes :"
              + stats);
    }
    //Perf test for 1kb writes. Puting values on the same KEY
    /*
     * if(runPerfTest){ populateDataPutOnSameKey();
     * System.out.println("OverflowASyncWithBuffer5:: Stats for 1 kb writes :"+
     * stats_ForSameKeyputs); }
     */
    //  Deleting all the files and logs created during the test...
    deleteFiles();
    closeDown();
  } //end of testPersistASyncWithBuffer5

  @Test
  public void testPersistASyncWithoutBufferRollOlg6()
  {
    try {
      //    Create four Dirs for Disk Dirs
      File file1 = new File("testPersistASyncWithoutBufferRollOlg6Dir1");
      file1.mkdir();
      file1.deleteOnExit();
      File file2 = new File("testPersistASyncWithoutBufferRollOlg6Dir2");
      file2.mkdir();
      file2.deleteOnExit();
      File file3 = new File("testPersistASyncWithoutBufferRollOlg6Dir3");
      file3.mkdir();
      file3.deleteOnExit();
      File file4 = new File("testPersistASyncWithoutBufferRollOlg6Dir4");
      file4.mkdir();
      file4.deleteOnExit();
      dirs = new File[4];
      dirs[0] = file1;
      dirs[1] = file2;
      dirs[2] = file3;
      dirs[3] = file4;

      diskProps.setTimeInterval(1000l);
      diskProps.setBytesThreshold(0l);
      diskProps.setRolling(true);
      diskProps.setMaxOplogSize(20971520);
      region = DiskRegionHelperFactory.getAsyncPersistOnlyRegion(cache,
          diskProps);
    }
    catch (Exception e) {
      e.printStackTrace();
      fail("failed in testPersistASyncWithoutBufferRollOlg6");
    }
    //Perf test for 1kb writes
    if (runPerfTest) {
      populateData0to60k();
      populateData60kto100k();
      System.out
          .println("PersistASyncWITHOUTBufferRollOlg6(with DiskWriteAttributes Time-out of 1 Second):: Stats for 1 kb writes :"
              + stats);
    }
    // Perf test for 1kb writes. Puting values on the same KEY
    /*
     * if(runPerfTest){ populateDataPutOnSameKey();
     * System.out.println("OverflowASyncWITHOUTBuffer6:: Stats for 1 kb writes
     * :"+ stats_ForSameKeyputs); }
     */
    //  Deleting all the files and logs created during the test...
    deleteFiles();
    closeDown();
  } //end of testPersistASyncWithoutBuffer

  //*************Persist with Overflow tests ****************
  @Test
  public void testPersistOverflowSyncRollOlg7()
  {
    try {
      //    Create four Dirs for Disk Dirs
      File file1 = new File("testPersistOverflowSyncRollOlg7Dir1");
      file1.mkdir();
      file1.deleteOnExit();
      File file2 = new File("testPersistOverflowSyncRollOlg7Dir2");
      file2.mkdir();
      file2.deleteOnExit();
      File file3 = new File("testPersistOverflowSyncRollOlg7Dir3");
      file3.mkdir();
      file3.deleteOnExit();
      File file4 = new File("testPersistOverflowSyncRollOlg7Dir4");
      file4.mkdir();
      file4.deleteOnExit();
      dirs = new File[4];
      dirs[0] = file1;
      dirs[1] = file2;
      dirs[2] = file3;
      dirs[3] = file4;

      diskProps.setRolling(true);
      diskProps.setMaxOplogSize(20971520l);
      diskProps.setOverFlowCapacity(1000);
      region = DiskRegionHelperFactory.getSyncOverFlowAndPersistRegion(cache,
          diskProps);
    }
    catch (Exception e) {
      e.printStackTrace();
      fail("failed in testPersistOverflowSyncRollOlg7");
    }
    //Perf test for 1kb writes
    if (runPerfTest) {
      populateData0to60k();
      populateData60kto100k();
      System.out
          .println("PersistOverflowWithSyncRollOlg7:: Stats for 1 kb writes :"
              + stats);
    }
    //Perf test for 1kb writes. Puting values on the same KEY
    /*
     * if(runPerfTest){ populateDataPutOnSameKey();
     * System.out.println("PersistOverflowWithSync7:: Stats for 1 kb writes :"+
     * stats_ForSameKeyputs); }
     */
    //  Deleting all the files and logs created during the test...
    deleteFiles();
    closeDown();
  } //end of testPersistOverflowSync

  @Test
  public void testPersistOverflowASyncWithBufferRollOlg8()
  {
    try {
      //    Create four Dirs for Disk Dirs
      File file1 = new File("testPersistOverflowASyncWithBufferRollOlg8Dir1");
      file1.mkdir();
      file1.deleteOnExit();
      File file2 = new File("testPersistOverflowASyncWithBufferRollOlg8Dir2");
      file2.mkdir();
      file2.deleteOnExit();
      File file3 = new File("testPersistOverflowASyncWithBufferRollOlg8Dir3");
      file3.mkdir();
      file3.deleteOnExit();
      File file4 = new File("testPersistOverflowASyncWithBufferRollOlg8Dir4");
      file4.mkdir();
      file4.deleteOnExit();
      dirs = new File[4];
      dirs[0] = file1;
      dirs[1] = file2;
      dirs[2] = file3;
      dirs[3] = file4;

      diskProps.setTimeInterval(15000l);
      diskProps.setBytesThreshold(10000l);
      diskProps.setRolling(true);
      diskProps.setMaxOplogSize(20971520l);
      diskProps.setOverFlowCapacity(1000);
      region = DiskRegionHelperFactory.getAsyncOverFlowAndPersistRegion(cache,
          diskProps);
    }
    catch (Exception e) {
      e.printStackTrace();
      fail("failed in testPersistOverflowASyncWithBufferRollOlg8");
    }
    //Perf test for 1kb writes
    if (runPerfTest) {
      populateData0to60k();
      populateData60kto100k();
      System.out
          .println("PersistOverflowASyncWithBufferRollOlg8:: Stats for 1 kb writes :"
              + stats);
    }
    //Perf test for 1kb writes. Puting values on the same KEY
    /*
     * if(runPerfTest){ populateDataPutOnSameKey();
     * System.out.println("PersistOverflowASyncWithBuffer8:: Stats for 1 kb
     * writes :"+ stats_ForSameKeyputs); }
     */
    //  Deleting all the files and logs created during the test...
   // deleteFiles();
    closeDown();
  } //end of testpersistOverflowASyncWithBuffer8

  @Test
  public void testPersistOverflowASyncWithoutBufferRollOlg9()
  {
    try {
      //    Create four Dirs for Disk Dirs
      File file1 = new File("testPersistOverflowASyncWithoutBufferRollOlg9Dir1");
      file1.mkdir();
      file1.deleteOnExit();
      File file2 = new File("testPersistOverflowASyncWithoutBufferRollOlg9Dir2");
      file2.mkdir();
      file2.deleteOnExit();
      File file3 = new File("testPersistOverflowASyncWithoutBufferRollOlg9Dir3");
      file3.mkdir();
      file3.deleteOnExit();
      File file4 = new File("testPersistOverflowASyncWithoutBufferRollOlg9Dir4");
      file4.mkdir();
      file4.deleteOnExit();
      dirs = new File[4];
      dirs[0] = file1;
      dirs[1] = file2;
      dirs[2] = file3;
      dirs[3] = file4;

      diskProps.setTimeInterval(1000l);
      diskProps.setBytesThreshold(0l);
      diskProps.setRolling(true);
      diskProps.setMaxOplogSize(20971520l);
      diskProps.setOverFlowCapacity(1000);
      region = DiskRegionHelperFactory.getAsyncOverFlowAndPersistRegion(cache,
          diskProps);
    }
    catch (Exception e) {
      e.printStackTrace();
      fail("failed in testPersistOverflowASyncWithoutBufferRollOlg9");
    }
    //Perf test for 1kb writes
    if (runPerfTest) {
      populateData0to60k();
      populateData60kto100k();
      System.out
          .println("Persist-OverflowASyncWITHOUTBufferRollOlg9(with DiskWriteAttributes Time-out of 1 Second):: Stats for 1 kb writes :"
              + stats);
    }
    // Perf test for 1kb writes. Puting values on the same KEY
    /*
     * if(runPerfTest){ populateDataPutOnSameKey();
     * System.out.println("Persist-OverflowASyncWITHOUTBuffer9:: Stats for 1 kb
     * writes :"+ stats_ForSameKeyputs); }
     */
    //  Deleting all the files and logs created during the test...
    deleteFiles();
    closeDown();
  } //end of testPersistOverflowASyncWithoutBuffer9

  //************** test data population *******************
  public static int ENTRY_SIZE = 1024;

  /**
   * OP_COUNT can be increased/decrease as per the requirement. If required to
   * be set as higher value such as 1000000, one needs to set the VM heap size
   * accordingly. (For example:Default setting in build.xml is <jvmarg
   * value="-Xmx256M"/>
   *  
   */

  public static int OP_COUNT = 1000;

  public static boolean UNIQUE_KEYS = Boolean.getBoolean("DRP.UNIQUE_KEYS");

  public void populateData0to60k()
  {
    final byte[] value = new byte[ENTRY_SIZE];
    Arrays.fill(value, (byte)77);
    for (int i = 0; i < 60000; i++) {
      region.put("" + i, value);
      // System.out.println(i);
    }
    System.out.println(" done with putting first 60k entries");

  }

  public void populateData60kto100k()
  {
    //  Put for validation.
    putForValidation(region);
    final byte[] value = new byte[ENTRY_SIZE];
    Arrays.fill(value, (byte)77);
    long startTime = System.currentTimeMillis();
    for (int i = 60000; i < 100000; i++) {
      region.put("" + i, value);
      // System.out.println(i);
    }
    long endTime = System.currentTimeMillis();
    System.out.println(" done with putting");
    //  validate put operation
    validatePut(region);
    region.close(); // closes disk file which will flush all buffers
    float et = endTime - startTime;
    float etSecs = et / 1000f;
    float opPerSec = etSecs == 0 ? 0 : (40000 / (et / 1000f));
    float bytesPerSec = etSecs == 0 ? 0 : ((40000 * ENTRY_SIZE) / (et / 1000f));
    stats = "et=" + et + "ms writes/sec=" + opPerSec + " bytes/sec="
        + bytesPerSec;
    log.info(stats);
  }

  public void populateDataPutOnSameKey()
  {
    //  Put for validation.
    putForValidation(region);
    final byte[] value = new byte[ENTRY_SIZE];
    Arrays.fill(value, (byte)77);
    long startTime = System.currentTimeMillis();
    for (int i = 0; i < OP_COUNT; i++) {
      region.put("K", value);
    }
    long endTime = System.currentTimeMillis();
    System.out.println(" done with putting");
    //  validate put operation
    validatePut(region);
    region.close(); // closes disk file which will flush all buffers
    float et = endTime - startTime;
    float etSecs = et / 1000f;
    float opPerSec = etSecs == 0 ? 0 : (OP_COUNT / (et / 1000f));
    float bytesPerSec = etSecs == 0 ? 0
        : ((OP_COUNT * ENTRY_SIZE) / (et / 1000f));
    stats_ForSameKeyputs = "et=" + et + "ms writes/sec=" + opPerSec
        + " bytes/sec=" + bytesPerSec;
    log.info(stats_ForSameKeyputs);
  }

  protected static void deleteFiles()
  {
    for (int i = 0; i < 4; i++) {
      File[] files = dirs[i].listFiles();
      for (int j = 0; j < files.length; j++) {
        files[j].delete();
      }
    }

  }

}// end of DiskRegionRollOpLogPerfJUnitTest