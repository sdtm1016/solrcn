package org.nlp.impl;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

import org.nlp.dictionary.InitDictionary;
import org.nlp.dictionary.LastPart;
import org.nlp.dictionary.PartOne;
import org.nlp.dictionary.PartTwo;
import org.nlp.dictionary.WordAttribute;

/**
 * @author Tony
 * 
 */
public class TokenImpl {

	/** 存放字典 */
	private static HashMap<String, PartOne> dicMap;

	/** 存放分词结果 */
	private ArrayList<TokendWords> words = new ArrayList<TokendWords>();

	/** 待切分文字 */
	private static String txt;

	public static final String DEFAULT_STOPWORD = "　… ,.`-_=?'|\"(){}[]<>*#&^$@!~:;+/\\《》—－，。、：；！·？“”）（【】［］█●";
	public static final String PUNCTION = "。，！？；,!?;";

	// 繁简中文大小写英文数字
	public static final String GOODCHAR = "[\\u0030-\\u0039\\u0041-\\u005A\\u0061-\\u007A\\u4E00-\\u9FA5\\uFF10-\\uFF19]+";

	public TokenImpl() {
	}

	public static void initDic(String filePath) {
		InitDictionary init = new InitDictionary();
		// init.readDicFromDisk();
		if (dicMap == null) {
			System.out.println("Load the dictionary..." + filePath);
			init.loadFile(filePath);
			dicMap = InitDictionary.getFirstWordMap();
		}
	}

	public static String DropStopWords(String oldWords) {
		StringBuffer newWords = new StringBuffer();
		for (int i = 0; i < oldWords.length(); i++) {
			if (DEFAULT_STOPWORD.indexOf(oldWords.charAt(i)) == -1) {// 不是停用词
				newWords.append(oldWords.charAt(i));
			}
		}
		return newWords.toString();
	}

	// 保留中文英文字母和数字
	public static String DropBadWords(String src) {
		StringBuilder result = new StringBuilder();
		char[] srcChar;
		srcChar = src.toCharArray();
		for (int i = 0; i < srcChar.length; i++) {
			char c = srcChar[i];
			if (String.valueOf(c).matches(GOODCHAR)) {
				result.append(c);
			}
		}
		return result.toString();
	}

	public ArrayList<TokendWords> tokenWord() {
		words.clear();
		ArrayList<Sentence> ps = Pretreatment.splitToSentence(txt);
		int position = 0;
		for (int i = 0; i < ps.size(); i++) {
			String p = ps.get(i).getSentence();
			if (!ps.get(i).isNeedSplit()) {
				String[] attri = { "null" };
				TokendWords tw = new TokendWords(p, 1l, attri, p.length(),
						position, txt.indexOf(p));
				words.add(tw);
				System.out.println("NoNeedSplit:" + p);
				position += p.length();
				continue;
			}

			// begin to token word
			String tempWord = "";
			for (int pos = 0; pos < p.length(); pos++, position++) {
				String firstWord = p.substring(pos, pos + 1);
				System.out.println("firstWord:" + firstWord);

				// 如果是字母数字或者-._符号
				if (StringUtil.isAlphanumeric(firstWord)) {

					tempWord += firstWord;
					if ((p.length() - pos) >= 2) {
						String word2 = p.substring(pos + 1, pos + 2);
						if (StringUtil.isAlphanumeric(word2))
							continue;
						else {
							String[] attri = { "null" };
							TokendWords tw = new TokendWords(tempWord, 1l,
									attri, tempWord.length(), position
											- tempWord.length() + 1,
									txt.indexOf(tempWord));
							words.add(tw);
							tempWord = "";
							continue;
						}
					} else {
						String[] attri = { "null" };
						TokendWords tw = new TokendWords(tempWord, 1l, attri,
								tempWord.length(), position - p.length(),
								txt.indexOf(tempWord));
						words.add(tw);
						tempWord = "";
						continue;
					}
				}

				System.out.println(words.size());

				System.out.println(dicMap.get(firstWord));

				try {
					if (dicMap.containsKey(firstWord)) {// contain first
														// word,test the secend
														// word
						String secendWord = p.substring(pos + 1, pos + 2);
						HashMap<String, PartTwo> secendWordMap = dicMap.get(
								firstWord).getNextWord();
						if (secendWordMap.containsKey(secendWord)) {// contain
																	// secend
																	// word
							PartTwo partTwo = secendWordMap.get(secendWord);
							if (partTwo.isFlag()) {// flag is true identify a
													// word
								String w = p.substring(pos, pos + 2);
								TokendWords tw = new TokendWords(w, partTwo
										.getWordAttri().getFreq(), partTwo
										.getWordAttri().getAttri(), w.length(),
										position, txt.indexOf(w));
								words.add(tw);
							}
							LastPart lastPart = secendWordMap.get(secendWord)
									.getLastPart();
							if (lastPart != null) {// has the LastPart like 生日快乐
								int maxLength = lastPart.getMaxLength();
								int j = 1;
								boolean b = false;
								ArrayList<String> leftWords = lastPart
										.getLastParts();
								for (; j <= maxLength; j++) {
									try {
										String leftWord = p.substring(pos + 2,
												pos + 2 + j);
										if (leftWords.contains(leftWord)) {// contained
																			// like
																			// 大学生
											String w = p.substring(pos, pos + 2
													+ j);
											int index = leftWords
													.indexOf(leftWord);
											WordAttribute wa = lastPart
													.getWordsRecord()
													.get(index);
											TokendWords tw = new TokendWords(w,
													wa.getFreq(),
													wa.getAttri(), w.length(),
													position, txt.indexOf(w));
											words.add(tw);
											b = true;
										}
									} catch (Exception e) {
										if (!b && !partTwo.isFlag()) {
											String[] attri = { "null" };
											TokendWords tw = new TokendWords(
													firstWord, 1l, attri,
													firstWord.length(),
													position,
													txt.indexOf(firstWord));
											words.add(tw);
										}
										break;
									}
								}
								if (!partTwo.isFlag() && !b) {
									String[] attri = { "null" };
									TokendWords tw = new TokendWords(firstWord,
											1l, attri, firstWord.length(),
											position, txt.indexOf(firstWord));
									words.add(tw);
								}
							}
						} else {
							if (words.size() != 0
									&& !words.get(words.size() - 1).getWord()
											.contains(firstWord)) {
								String[] attri = { "null" };
								TokendWords tw = new TokendWords(firstWord, 1l,
										attri, firstWord.length(), position,
										txt.indexOf(firstWord));
								words.add(tw);
							} else {
								String[] attri = { "null" };
								TokendWords tw = new TokendWords(firstWord, 1l,
										attri, firstWord.length(), position,
										txt.indexOf(firstWord));
								words.add(tw);
							}
						}
					} else {
						if (words.size() != 0
								&& !words.get(words.size() - 1).getWord()
										.contains(firstWord)) {
							String[] attri = { "null" };
							TokendWords tw = new TokendWords(firstWord, 1l,
									attri, firstWord.length(), position,
									txt.indexOf(firstWord));
							words.add(tw);
						} else {
							String[] attri = { "null" };
							TokendWords tw = new TokendWords(firstWord, 1l,
									attri, firstWord.length(), position,
									txt.indexOf(firstWord));
							words.add(tw);
						}
					}
				} catch (Exception e) {
					if (words.size() != 0
							&& !words.get(words.size() - 1).getWord()
									.contains(firstWord)) {
						String[] attri = { "null" };
						String w = p.substring(pos);
						TokendWords tw = new TokendWords(w, 1l, attri,
								w.length(), position, txt.indexOf(w));
						words.add(tw);
					} else {
						String[] attri = { "null" };
						String w = p.substring(pos);
						TokendWords tw = new TokendWords(w, 1l, attri,
								w.length(), position, txt.indexOf(w));
						words.add(tw);
					}
				}
			}
		}

		return words;
	}

	public ArrayList<TokendWords> wordsFilter() {
		ArrayList<TokendWords> nw = new ArrayList<TokendWords>();

		// 先将第一个词添加到nw
		nw.add(words.get(0));
		// 开始遍历所有词
		for (int i = 0, j = 1; i < words.size() - 1; i++, j++) {
			TokendWords tw1 = words.get(i);
			TokendWords tw2 = words.get(j);

			// 如果下一个词和第一个词没有交集，且正好接在第一个词后面，直接添加到nw
			if (tw1.getPos() + tw1.getLength() == tw2.getPos()) {
				nw.add(tw2);
				continue;
			}

			// 如果第二个词长度不为1
			if (tw2.getLength() != 1) {
				// 获得词1和词2的长度较小的那个值
				int min = Math.min(tw1.getLength(), tw2.getLength());

				// 如果词1长度不等于词2长度
				if (tw1.getLength() != tw2.getLength()) {
					// 如果词1是以词2结尾的
					if (tw1.getWord().endsWith(tw2.getWord())) {// 是这样 这样
						// 否则，如果词2是以词1开头的
					} else if (tw2.getWord().startsWith(tw1.getWord())) {// 不完
																			// 不完全
						// 如果nw中最后一个词的长度小于等于词1长度，删除最后一个词
						if (nw.get(nw.size() - 1).getWord().length() <= tw1
								.getWord().length())
							nw.remove(nw.size() - 1);
						// 添加词2
						nw.add(words.get(j));
					} else {// 天气 大学生 //还有 有没有
						//如果词1频率比词2高
						if (tw1.getFreq() > tw2.getFreq()) {// 改写2 过高 高中
							String w1 = tw1.getWord();
							String w2 = tw2.getWord();
							int k = 0;
							int c = 0;
							//按照词1和词2的最小长度截取词2计算词1和词2交集
							for (; k < min; k++) {
								String temp = w2.substring(0, k + 1);
								if (w1.endsWith(temp))
									continue;
								else if (k == 0) {
									c++;
									continue;
								} else if (c == 1) {
									c--;
									break;
								} else
									break;
							}
							if (c == 1)
								k--;
							//如果词1和词2不交叉并且词1不包含词2，添加词2
							if (k == 0) {
								if (!words.get(i).getWord()
										.contains(words.get(j).getWord()))
									nw.add(words.get(j));
								continue;
							}
							//如果词1长度小于词2，将词1交叉部分去掉设置为nw最后一个词,添加词2到nw
							if (tw1.getLength() < tw2.getLength()) {// 使用 用户满意
								w1 = w1.substring(0, w1.length() - k);
								TokendWords tw = new TokendWords(tw1.getWord(),
										tw1.getFreq(), tw1.getAttri(),
										w1.length(), tw1.getPos(),
										txt.indexOf(tw1.getWord()));// tw1;
								tw.setWord(w1);
								nw.set(nw.size() - 1, tw);
								nw.add(tw2);
								continue;
							}
							//如果词1长度大于词2，将词2去掉交叉位置添加到nw
							w2 = w2.substring(k);
							TokendWords tw = new TokendWords(tw2.getWord(),
									tw2.getFreq(), tw2.getAttri(), w2.length(),
									tw2.getPos() + k,
									txt.indexOf(tw2.getWord()));
							tw.setWord(w2);
							nw.add(tw);
							continue;
						}
						//如果词1频率小于词2
						else if (tw1.getFreq() < tw2.getFreq()) {// 改写1 过高 高中
							String w1 = tw1.getWord();
							String w2 = tw2.getWord();
							int k = 0;
							for (; k < min; k++) {
								String temp = w2.substring(0, k + 1);
								if (w1.endsWith(temp))
									continue;
								else
									break;
							}
							if (k == 0) {
								if (!words.get(i).getWord()
										.contains(words.get(j).getWord()))
									nw.add(words.get(j));
								continue;
							} else if (k == 1) {// 乒乓球拍 拍卖
								//如果交叉长度为1，且词1长度大于词2 2个字，将词2从交叉处截断，添加到nw
								if (w1.length() - w2.length() >= 2) {
									w2 = w2.substring(k);
									TokendWords tw = new TokendWords(
											tw2.getWord(), tw2.getFreq(),
											tw2.getAttri(), w2.length(),
											tw2.getPos() + k, txt.indexOf(tw2
													.getWord()));
									tw.setWord(w2);
									nw.add(tw);
									continue;
								}
							}
							
							//如果nw最后一个词长度小于词1，将最后一个词设置为词1
							if (nw.get(nw.size() - 1).getWord().length() <= w1
									.length()) {
								w1 = w1.substring(0, w1.length() - k);
								TokendWords tw = new TokendWords(tw1.getWord(),
										tw1.getFreq(), tw1.getAttri(),
										w1.length(), tw1.getPos(),
										txt.indexOf(tw1.getWord()));// tw1;
								tw.setWord(w1);
								nw.set(nw.size() - 1, tw);
							}
							//添加词2
							nw.add(words.get(j));
							continue;
						}
					}
				} else {// length == length
					if (tw1.getFreq() > tw2.getFreq()) {// 改写2 过高 高中
						String w1 = tw1.getWord();
						String w2 = tw2.getWord();
						int k = 0;
						int c = 0;
						for (; k < min; k++) {
							String temp = w2.substring(0, k + 1);
							if (w1.endsWith(temp))
								continue;
							else if (k == 0) {
								c++;
								continue;
							} else if (c == 1) {
								c--;
								break;
							} else
								break;
						}
						if (c == 1)
							k--;
						if (k == 0) {
							if (!words.get(i).getWord()
									.contains(words.get(j).getWord()))
								nw.add(words.get(j));
							continue;
						}
						if (tw1.getLength() < tw2.getLength()) {// 使用 用户满意
							w1 = w1.substring(0, w1.length() - k);
							TokendWords tw = new TokendWords(tw1.getWord(),
									tw1.getFreq(), tw1.getAttri(), w1.length(),
									tw1.getPos(), txt.indexOf(tw1.getWord()));// tw1;
							tw.setWord(w1);
							nw.set(nw.size() - 1, tw);
							nw.add(tw2);
							continue;
						}

						w2 = w2.substring(k);
						TokendWords tw = new TokendWords(tw2.getWord(),
								tw2.getFreq(), tw2.getAttri(), w2.length(),
								tw2.getPos() + k, txt.indexOf(tw2.getWord()));
						tw.setWord(w2);
						nw.add(tw);
						continue;
					} else if (tw1.getFreq() < tw2.getFreq()) {// 改写1 过高 高中
						String w1 = tw1.getWord();
						String w2 = tw2.getWord();
						int k = 0;
						for (; k < min; k++) {
							String temp = w2.substring(0, k + 1);
							if (w1.endsWith(temp))
								continue;
							else
								break;
						}
						if (k == 0) {
							if (!words.get(i).getWord()
									.contains(words.get(j).getWord()))
								nw.add(words.get(j));
							continue;
						}
						if (nw.get(nw.size() - 1).getWord().length() <= w1
								.length()) {
							w1 = w1.substring(0, w1.length() - k);
							TokendWords tw = new TokendWords(tw1.getWord(),
									tw1.getFreq(), tw1.getAttri(), w1.length(),
									tw1.getPos(), txt.indexOf(tw1.getWord()));
							tw.setWord(w1);
							nw.set(nw.size() - 1, tw);
						}

						nw.add(words.get(j));
						continue;
					}
				}
			} else {// length == 1
				if (tw1.getPos() + tw1.getLength() == tw2.getPos()) {
					nw.add(tw2);
					continue;
				}
				String w1 = "";
				int size = nw.size();
				w1 = nw.get(size - 1).getWord();
				String w2 = words.get(j).getWord();
				if (w1.endsWith(w2)) {
				} else if (w1.contains(w2)) {
				} else {
					nw.add(words.get(j));
				}
			}
		}
		System.out.println("===========================");
		return nw;
	}

	public ArrayList<TokendWords> getTokendWords(String txt) {
		setTxt(txt);
		return getTokendWords();
	}

	public ArrayList<TokendWords> getTokendWords() {
		this.tokenWord();
		ArrayList<TokendWords> tw = this.wordsFilter();
		boolean b = checkSum(tw);
		if (!b) {
			this.setWords(tw);
			tw = wordsFilter();
		} else
			return tw;
		b = checkSum(tw);
		if (!b) {
			this.setWords(tw);
			return wordsFilter();
		}

		return tw;
	}

	public ArrayList<TokendWords> getTokendNewWords(String txt) {
		setTxt(txt);
		return getTokendNewWords();
	}

	public ArrayList<TokendWords> getTokendNewWords() {
		String[] attr = { "IDIOM" };
		ArrayList<String> NewWords = getNewWord();
		words.clear();
		for (int i = 0, len = NewWords.size(); i < len; i++) {
			words.add(new TokendWords(NewWords.get(i), 1, attr, NewWords.get(i)
					.length(), i, txt.indexOf(NewWords.get(i))));
		}
		return words;
	}

	public boolean checkSum(ArrayList<TokendWords> tw) {
		int counter = 0;
		for (int i = 0; i < tw.size(); i++) {
			TokendWords temp = tw.get(i);
			counter += temp.getLength();
		}
		if (counter == txt.length())
			return true;
		return false;
	}

	public static String loadFile(String filePath) {
		try {
			FileInputStream fs = new FileInputStream(filePath);
			InputStreamReader isr;
			isr = new InputStreamReader(fs, "utf-8");
			BufferedReader br = new BufferedReader(isr);
			StringBuilder sb = new StringBuilder();
			String s = "";
			while ((s = br.readLine()) != null) {
				sb.append(s);
			}
			return sb.toString();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public ArrayList<String> getNewWord(String txt) {
		getTokendWords(txt);
		return getNewWord();
	}

	public ArrayList<String> getNewWord() {

		StringBuilder NewWord = new StringBuilder();
		ArrayList<String> NewWords = new ArrayList<String>();

		if (words.size() < 1 && txt.length() > 0) {
			getTokendWords();
		}

		for (int i = 0; i < words.size(); i++) {
			TokendWords word = words.get(i);
			if (word.getLength() == 1 && !PUNCTION.contains(word.getWord())) {
				NewWord.append(word.getWord());
			} else {
				if (DropBadWords(NewWord.toString()).length() > 1) {
					NewWords.add(DropBadWords(NewWord.toString()));
				}
				NewWord.delete(0, NewWord.length());
			}
		}

		if (NewWord.length() > 1) {
			NewWords.add(NewWord.toString());
		}

		// for (int i = 0, len = NewWords.size(); i < len; i++) {
		// System.out.println("新词：" + NewWords.get(i));
		// }

		return NewWords;

	}

	public ArrayList<TokendWords> getWords() {
		return words;
	}

	public void setWords(ArrayList<TokendWords> words) {
		this.words = words;
	}

	public String getTxt() {
		return txt;
	}

	@SuppressWarnings("static-access")
	public void setTxt(String txt) {
		this.txt = txt;
	}
}
