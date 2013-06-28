package org.nlp.lucene.index;

import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.MergeScheduler;


public final class NoMergeScheduler extends MergeScheduler {

//  /** The single instance of {@link NoMergeScheduler} */
//  public static final MergeScheduler INSTANCE = new NoMergeScheduler();

  public NoMergeScheduler() {
    // prevent instantiation
  }

  @Override
  public void close() {}

  @Override
  public void merge(IndexWriter writer) {}
}
