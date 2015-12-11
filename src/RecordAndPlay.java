import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;



public class RecordAndPlay {

	public static void main(String[] args){

		getOffersMerchant(200D, 3);
		
	}
	public static void getOffersMerchant(Double balance,Integer nbrMaxTable) {

		// ### Construction de la requete SQL ###
		// ######################################

		int j = 1;
		String SQL = "";
		List<String> FROM = new ArrayList<String>();
		List<String> WHERE = new ArrayList<String>();
		List<String> SUM = new ArrayList<String>();
		String GROUPBY = "";

		// FROM.add(String.format("esioox_offer_merchant t%s", j));

		for (int i = j; i <= nbrMaxTable; i++) {
			FROM.add(String.format("esioox_offer_merchant t%s", i));
			if (i != j) {
				WHERE.add(String.format("t%s.merchantId=t%s.merchantId", j, i));
			}
			SUM.add(String.format("t%s.valeur", i));
		}

		WHERE.add(StringUtils.join(SUM, "+"));
		GROUPBY = String.format("GROUP BY t%s.merchantId", j);
		SQL = String.format("SELECT t%s.merchantId AS mId FROM %s WHERE %s=%s %s", j, StringUtils.join(FROM, " JOIN "), StringUtils.join(WHERE, " AND "), balance, GROUPBY);
		
		System.out.println(SQL);
	}

}
