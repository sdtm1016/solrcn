package org.nlp.lucene.index;


import java.util.Map;

import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.MergePolicy;
import org.apache.lucene.index.SegmentInfoPerCommit;
import org.apache.lucene.index.SegmentInfos;

public final class NoMergePolicy extends MergePolicy {

  /**
   * A singleton {@link NoMergePolicy} which indicates the index does not use
   * compound files.
   */
  public static final MergePolicy NO_COMPOUND_FILES = new NoMergePolicy(false);

  /**
   * A singleton {@link NoMergePolicy} which indicates the index uses compound
   * files.
   */
  public static final MergePolicy COMPOUND_FILES = new NoMergePolicy(true);

  private final boolean useCompoundFile;
  
  private NoMergePolicy(boolean useCompoundFile) {
    // prevent instantiation
    this.useCompoundFile = useCompoundFile;
  }
  
  public NoMergePolicy(){
	  this.useCompoundFile = false;
  }
  

  @Override
  public void close() {}

  @Override
  public MergeSpecification findMerges(MergeTrigger mergeTrigger, SegmentInfos segmentInfos) { return null; }

  @Override
  public MergeSpecification findForcedMerges(SegmentInfos segmentInfos,
             int maxSegmentCount, Map<SegmentInfoPerCommit,Boolean> segmentsToMerge) { return null; }

  @Override
  public MergeSpecification findForcedDeletesMerges(SegmentInfos segmentInfos) { return null; }

  @Override
  public boolean useCompoundFile(SegmentInfos segments, SegmentInfoPerCommit newSegment) { return useCompoundFile; }

  @Override
  public void setIndexWriter(IndexWriter writer) {}

  @Override
  public String toString() {
    return "NoMergePolicy";
  }
}
