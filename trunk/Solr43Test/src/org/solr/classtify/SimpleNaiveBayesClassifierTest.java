/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.solr.classtify;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.analysis.cjk.CJKAnalyzer;
import org.apache.lucene.classification.ClassificationResult;
import org.apache.lucene.classification.SimpleNaiveBayesClassifier;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.FieldType;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.AtomicReader;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.SlowCompositeReaderWrapper;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.SimpleFSDirectory;
import org.apache.lucene.util.BytesRef;
import org.apache.lucene.util.Version;
import org.junit.Test;

public class SimpleNaiveBayesClassifierTest {

	private IndexWriter indexWriter;
    String textFieldName = "text";
    String categoryFieldName = "cat";
    String booleanFieldName = "bool";
    Version matchVersion = Version.LUCENE_43;
    CJKAnalyzer analyzer = new CJKAnalyzer(matchVersion); 
    String newText = "Much is made of what the likes of Facebook, Google and Apple know about users. Truth is, Amazon may know more.";
    String dir = "z:/index";
    
	@Test
	public void buildIndex() throws IOException{
		
		
		
		

	    
		Directory d = new SimpleFSDirectory(new File(dir));
		
		IndexWriterConfig conf = new IndexWriterConfig(matchVersion, analyzer);
		indexWriter = new IndexWriter(d, conf);
		FieldType ft = new FieldType(TextField.TYPE_STORED);
	    ft.setStoreTermVectors(true);
	    ft.setStoreTermVectorOffsets(true);
	    ft.setStoreTermVectorPositions(true);

	    Document doc = new Document();
	    doc.add(new Field(textFieldName, "The traveling press secretary for Mitt Romney lost his cool and cursed at reporters " +
	        "who attempted to ask questions of the Republican presidential candidate in a public plaza near the Tomb of " +
	        "the Unknown Soldier in Warsaw Tuesday.", ft));
	    doc.add(new Field(categoryFieldName, "politics", ft));
	    doc.add(new Field(booleanFieldName, "false", ft));

	    indexWriter.addDocument(doc, analyzer);

	    doc = new Document();
	    doc.add(new Field(textFieldName, "Mitt Romney seeks to assure Israel and Iran, as well as Jewish voters in the United" +
	        " States, that he will be tougher against Iran's nuclear ambitions than President Barack Obama.", ft));
	    doc.add(new Field(categoryFieldName, "politics", ft));
	    doc.add(new Field(booleanFieldName, "false", ft));
	    indexWriter.addDocument(doc, analyzer);

	    doc = new Document();
	    doc.add(new Field(textFieldName, "And there's a threshold question that he has to answer for the American people and " +
	        "that's whether he is prepared to be commander-in-chief,\" she continued. \"As we look to the past events, we " +
	        "know that this raises some questions about his preparedness and we'll see how the rest of his trip goes.\"", ft));
	    doc.add(new Field(categoryFieldName, "politics", ft));
	    doc.add(new Field(booleanFieldName, "false", ft));
	    indexWriter.addDocument(doc, analyzer);

	    doc = new Document();
	    doc.add(new Field(textFieldName, "Still, when it comes to gun policy, many congressional Democrats have \"decided to " +
	        "keep quiet and not go there,\" said Alan Lizotte, dean and professor at the State University of New York at " +
	        "Albany's School of Criminal Justice.", ft));
	    doc.add(new Field(categoryFieldName, "politics", ft));
	    doc.add(new Field(booleanFieldName, "false", ft));
	    indexWriter.addDocument(doc, analyzer);

	    doc = new Document();
	    doc.add(new Field(textFieldName, "Standing amongst the thousands of people at the state Capitol, Jorstad, director of " +
	        "technology at the University of Wisconsin-La Crosse, documented the historic moment and shared it with the " +
	        "world through the Internet.", ft));
	    doc.add(new Field(categoryFieldName, "technology", ft));
	    doc.add(new Field(booleanFieldName, "true", ft));
	    indexWriter.addDocument(doc, analyzer);

	    doc = new Document();
	    doc.add(new Field(textFieldName, "So, about all those experts and analysts who've spent the past year or so saying " +
	        "Facebook was going to make a phone. A new expert has stepped forward to say it's not going to happen.", ft));
	    doc.add(new Field(categoryFieldName, "technology", ft));
	    doc.add(new Field(booleanFieldName, "true", ft));
	    indexWriter.addDocument(doc, analyzer);

	    doc = new Document();
	    doc.add(new Field(textFieldName, "More than 400 million people trust Google with their e-mail, and 50 million store files" +
	        " in the cloud using the Dropbox service. People manage their bank accounts, pay bills, trade stocks and " +
	        "generally transfer or store huge volumes of personal data online.", ft));
	    doc.add(new Field(categoryFieldName, "technology", ft));
	    doc.add(new Field(booleanFieldName, "true", ft));
	    indexWriter.addDocument(doc, analyzer);

	    indexWriter.commit();
	}
	
	@Test
	public void classtify() throws IOException{
		SimpleNaiveBayesClassifier classifier = new SimpleNaiveBayesClassifier();
		IndexReader reader = DirectoryReader.open(FSDirectory.open(new File(dir)));
		
		AtomicReader wrap = SlowCompositeReaderWrapper.wrap(reader);
		classifier.train(wrap, textFieldName, categoryFieldName, analyzer);
		ClassificationResult<BytesRef> assignClass = classifier.assignClass(newText);
		BytesRef assignedClass = assignClass.getAssignedClass();
		
		double score = assignClass.getScore();
		System.out.println(assignedClass.utf8ToString() + ","+score);
	}
	


}
