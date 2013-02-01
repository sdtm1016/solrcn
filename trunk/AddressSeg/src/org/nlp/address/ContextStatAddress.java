package org.nlp.address;

/**
 * Address Context State
 */
public class ContextStatAddress {

	private static int[][] transProbs;

	private static ContextStatAddress cs = new ContextStatAddress();

	public static ContextStatAddress getInstance() {
		return cs;
	}

	private ContextStatAddress() {
		int matrixLength = AddressType.values().length;
		transProbs = new int[matrixLength][];

		for (int i = 0; i < AddressType.values().length; ++i) {
			transProbs[i] = new int[matrixLength];
		}

		addTrob(AddressType.Start, AddressType.Street, 10000000);
		addTrob(AddressType.Start, AddressType.LandMark, 1000000);
		addTrob(AddressType.Start, AddressType.District, 100000000);
		addTrob(AddressType.Start, AddressType.Village, 1000000);
		addTrob(AddressType.Start, AddressType.Town, 100000);
		addTrob(AddressType.Start, AddressType.City, 1000000);
		addTrob(AddressType.Start, AddressType.County, 1000000);
		addTrob(AddressType.Start, AddressType.Country, 1000000);
		addTrob(AddressType.Start, AddressType.Province, 1000000);
		addTrob(AddressType.Start, AddressType.Municipality, 1000000);

		addTrob(AddressType.Province, AddressType.City, 100000000);
		addTrob(AddressType.Province, AddressType.County, 1000000);
		addTrob(AddressType.Province, AddressType.Town, 10);
		addTrob(AddressType.Province, AddressType.End, 10000000);
		addTrob(AddressType.Province, AddressType.SuffixMunicipality, -10000000);
		addTrob(AddressType.Municipality, AddressType.District, 1000000);
		addTrob(AddressType.Municipality, AddressType.County, 10000000);
		addTrob(AddressType.Municipality, AddressType.Street, 10000);
		
		
		addTrob(AddressType.Municipality, AddressType.End, 10000);
		addTrob(AddressType.City, AddressType.County, 10000000);
		addTrob(AddressType.City, AddressType.Town, 10000000);
		addTrob(AddressType.City, AddressType.City, 1000000);
		addTrob(AddressType.City, AddressType.Street, 100000000);
		addTrob(AddressType.City, AddressType.District, 10000000);
		addTrob(AddressType.City, AddressType.LandMark, 1000000);
		addTrob(AddressType.City, AddressType.Country, 1000);
		addTrob(AddressType.City, AddressType.End, 1000);
		addTrob(AddressType.City, AddressType.SuffixCity, -1000000);
		addTrob(AddressType.City, AddressType.SuffixLandMark, -100000);
		addTrob(AddressType.City, AddressType.SuffixStreet, -100000);
		addTrob(AddressType.City, AddressType.SuffixCounty, -100000);
		addTrob(AddressType.County, AddressType.Village, 10000);
		addTrob(AddressType.County, AddressType.Town, 100000);
		addTrob(AddressType.County, AddressType.Province, 100);
		addTrob(AddressType.County, AddressType.Street, 100000000);
		addTrob(AddressType.County, AddressType.District, 10000000);		
		addTrob(AddressType.County, AddressType.County, -900000000);
		addTrob(AddressType.County, AddressType.Other, -1000);
		addTrob(AddressType.County, AddressType.Unknow, -10000000);
		addTrob(AddressType.County, AddressType.End, 10000);
		addTrob(AddressType.County, AddressType.LandMark, 1000000000);
		addTrob(AddressType.County, AddressType.SuffixDistrict, -1000000000);
		addTrob(AddressType.County, AddressType.City, -1000000000);
		addTrob(AddressType.County, AddressType.SuffixCity, -1000000000);

		addTrob(AddressType.RelatedPos, AddressType.County, -1000);
		addTrob(AddressType.RelatedPos, AddressType.LandMark, 100000);
		addTrob(AddressType.RelatedPos, AddressType.End, 10);
		addTrob(AddressType.RelatedPos, AddressType.Municipality, 100);
		addTrob(AddressType.Street, AddressType.SuffixLandMark, 100);

		addTrob(AddressType.Town, AddressType.Village, 10000000);
		addTrob(AddressType.Town, AddressType.LandMark, 10000000);
		addTrob(AddressType.Town, AddressType.District, 10000000);
		addTrob(AddressType.Town, AddressType.Street, 100000000);
		addTrob(AddressType.Town, AddressType.SuffixLandMark, 10000000);
		addTrob(AddressType.Town, AddressType.End, 100);
		addTrob(AddressType.Town, AddressType.Town, 1);
		addTrob(AddressType.Town, AddressType.RelatedPos, 10000000);
		addTrob(AddressType.Town, AddressType.Village, 100000000);
		addTrob(AddressType.Town, AddressType.SuffixCounty, -100000000);
		addTrob(AddressType.StreetNo, AddressType.BuildingUnit, 10);
		addTrob(AddressType.StreetNo, AddressType.LandMark, 10000000);
		addTrob(AddressType.StreetNo, AddressType.BuildingNo, 100);
		addTrob(AddressType.StreetNo, AddressType.City, 10000);
		addTrob(AddressType.No, AddressType.District, 10000000);
		addTrob(AddressType.No, AddressType.RelatedPos, 10000);
		addTrob(AddressType.No, AddressType.LandMark, 10000000);
		addTrob(AddressType.No, AddressType.Street, 10000000);
		addTrob(AddressType.No, AddressType.City, -10000000);
		addTrob(AddressType.No, AddressType.SuffixBuildingUnit, 10000000);

		addTrob(AddressType.Street, AddressType.City, -1000000000);
		addTrob(AddressType.Street, AddressType.County, -10000000);
		addTrob(AddressType.Street, AddressType.Town, -1000000);
		addTrob(AddressType.Street, AddressType.Street, 1000000000);
		addTrob(AddressType.Street, AddressType.SuffixStreet, 10000);
		addTrob(AddressType.Street, AddressType.LandMark, 10000000);
		addTrob(AddressType.Street, AddressType.No, 10000);
		addTrob(AddressType.Street, AddressType.End, 1000000);
		addTrob(AddressType.Street, AddressType.City, -1000000);
		addTrob(AddressType.Village, AddressType.District, 1000);
		addTrob(AddressType.Village, AddressType.End, 100);
		addTrob(AddressType.Village, AddressType.Street, 100);
		addTrob(AddressType.Village, AddressType.LandMark, 100);
		addTrob(AddressType.LandMark, AddressType.No, 10000);
		addTrob(AddressType.LandMark, AddressType.End, 10000);
		addTrob(AddressType.LandMark, AddressType.Street, 10000000);
		addTrob(AddressType.LandMark, AddressType.StartSuffix, 10000000);
		addTrob(AddressType.LandMark, AddressType.RelatedPos, 100000);
		addTrob(AddressType.LandMark, AddressType.City, -900000000);
		addTrob(AddressType.LandMark, AddressType.County, -900000000);

		addTrob(AddressType.BuildingUnit, AddressType.District, 100000);
		addTrob(AddressType.BuildingUnit, AddressType.Province, 10);
		addTrob(AddressType.BuildingUnit, AddressType.Street, 10);
		addTrob(AddressType.District, AddressType.LandMark, 1000000);
		addTrob(AddressType.District, AddressType.City, -10000);
		addTrob(AddressType.District, AddressType.Village, 1000000);
		addTrob(AddressType.District, AddressType.Street, 1000000);
		addTrob(AddressType.District, AddressType.County, -1000000);
		addTrob(AddressType.District, AddressType.District, 1000000);
		addTrob(AddressType.District, AddressType.End, 1000000);
		addTrob(AddressType.SuffixBuildingNo, AddressType.County, 10000);
		addTrob(AddressType.IndicationFacility, AddressType.IndicationPosition,
				10000);
		addTrob(AddressType.IndicationFacility, AddressType.RelatedPos,
				100000000);
		addTrob(AddressType.IndicationPosition, AddressType.LandMark, 100000000);
		addTrob(AddressType.Unknow, AddressType.SuffixLandMark, 100000);
		addTrob(AddressType.Unknow, AddressType.Street, 100000);
		addTrob(AddressType.Unknow, AddressType.SuffixStreet, 100000);
		addTrob(AddressType.Unknow, AddressType.City, -1000000000);

		addTrob(AddressType.SuffixLandMark, AddressType.No, 100000);
		addTrob(AddressType.SuffixLandMark, AddressType.RelatedPos, 100000);
		addTrob(AddressType.SuffixLandMark, AddressType.County, -10000);

		addTrob(AddressType.County, AddressType.SuffixLandMark, -10000);
		addTrob(AddressType.County, AddressType.County, -10000);
		addTrob(AddressType.County, AddressType.SuffixCounty, -100000);
		addTrob(AddressType.Municipality, AddressType.SuffixCity, -100000);
		addTrob(AddressType.Municipality, AddressType.SuffixStreet, -100000);
		addTrob(AddressType.Municipality, AddressType.City, -1000000000);
		addTrob(AddressType.County, AddressType.SuffixStreet, -100000);
		addTrob(AddressType.Unknow, AddressType.Town, -100000);
		addTrob(AddressType.Unknow, AddressType.County, -100000);
		addTrob(AddressType.Other, AddressType.LandMark, 100000000);

		addTrob(AddressType.SuffixCity, AddressType.SuffixLandMark, -100000);
		addTrob(AddressType.City, AddressType.SuffixCity, -100000);
		addTrob(AddressType.No, AddressType.SuffixLandMark, 100000);

		addTrob(AddressType.SuffixDistrict, AddressType.County, -1000);
		addTrob(AddressType.Street, AddressType.County, -100000000);
		addTrob(AddressType.SuffixCity, AddressType.Street, 1000000000);
		addTrob(AddressType.SuffixCounty, AddressType.Town, 1000000);
		addTrob(AddressType.SuffixCounty, AddressType.Street, 1000000000);
		addTrob(AddressType.SuffixCounty, AddressType.County, -100000000);
		addTrob(AddressType.SuffixCounty, AddressType.City, -10000000);
		addTrob(AddressType.SuffixStreet, AddressType.City, -100000);
		addTrob(AddressType.SuffixCounty, AddressType.Village, 100000000);
		addTrob(AddressType.SuffixCounty, AddressType.Street, 10000000);
		addTrob(AddressType.SuffixCounty, AddressType.District, 100000000);
		addTrob(AddressType.SuffixCounty, AddressType.LandMark, 1000000000);
		addTrob(AddressType.SuffixTown, AddressType.Town, -100000000);
		addTrob(AddressType.SuffixTown, AddressType.County, -100000000);
		addTrob(AddressType.SuffixTown, AddressType.Village, 100000000);
		addTrob(AddressType.SuffixTown, AddressType.RelatedPos, -10000000);
		addTrob(AddressType.SuffixTown, AddressType.City, -100000);

		addTrob(AddressType.LandMark, AddressType.SuffixCounty, -900000000);
		addTrob(AddressType.LandMark, AddressType.SuffixCity, -900000000);
		addTrob(AddressType.SuffixLandMark, AddressType.County, -100000);
		addTrob(AddressType.SuffixLandMark, AddressType.Street, 10000000);
		addTrob(AddressType.StartSuffix, AddressType.LandMark, 20000);
		addTrob(AddressType.StartSuffix, AddressType.Town, -10000000);

	}

	public void addTrob(AddressType prev, AddressType cur, int val) {
		transProbs[prev.ordinal()][cur.ordinal()] = val;
	}

	/**
	 * get context possibility
	 * 
	 * @param nPrev
	 *            the previous POS
	 * @param nCur
	 *            the current POS
	 * @return the context possibility between two POSs
	 */
	public int getContextPossibility(AddressType prev, AddressType cur) {
		return transProbs[prev.ordinal()][cur.ordinal()];
	}

	public static int[][] getTransProbs() {
		return transProbs;
	}
}
