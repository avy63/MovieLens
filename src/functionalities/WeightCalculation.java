package functionalities;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.Inet4Address;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.JOptionPane;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.common.FastIDSet;
import org.apache.mahout.cf.taste.impl.common.LongPrimitiveIterator;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.common.Pair;

import dbconnection.DBConnection;

public class WeightCalculation {

	Map<Integer, Double> mappy;
	public Map<Long, Double> sortedMap;
	public static BufferedWriter bw = null;
	DecimalFormat df = new DecimalFormat("#.###");
	public static BufferedWriter bw1 = null;
	public static Map<Integer, Integer> numberofMovieesWatch = null;
	public static Map<Integer, Double> averageUserRating = null;
	public Map<Integer, List<Integer>> listofSimillaruser = new HashMap<Integer, List<Integer>>();
	public List<Pair<Integer, Pair<Integer, Double>>> simibetuser = new ArrayList<Pair<Integer, Pair<Integer, Double>>>();
	public DBConnection dbConnection;
	public Map<Integer, Map<Integer, Double>> similarityBetweenUsers = new HashMap<Integer, Map<Integer, Double>>();
	public Map<Integer, Double> action = new HashMap<Integer, Double>();
	public Map<Integer, Double> animation = new HashMap<Integer, Double>();
	public Map<Integer, Double> adventure = new HashMap<Integer, Double>();
	public Map<Integer, Double> childrens = new HashMap<Integer, Double>();
	public Map<Integer, Double> comedy = new HashMap<Integer, Double>();
	public Map<Integer, Double> crime = new HashMap<Integer, Double>();
	public Map<Integer, Double> documentary = new HashMap<Integer, Double>();
	public Map<Integer, Double> drama = new HashMap<Integer, Double>();
	public Map<Integer, Double> fantasy = new HashMap<Integer, Double>();
	public Map<Integer, Double> filmnoir = new HashMap<Integer, Double>();
	public Map<Integer, Double> horror = new HashMap<Integer, Double>();
	public Map<Integer, Double> musical = new HashMap<Integer, Double>();
	public Map<Integer, Double> mystery = new HashMap<Integer, Double>();
	public Map<Integer, Double> romance = new HashMap<Integer, Double>();
	public Map<Integer, Double> scifi = new HashMap<Integer, Double>();
	public Map<Integer, Double> thrillar = new HashMap<Integer, Double>();
	public Map<Integer, Double> war = new HashMap<Integer, Double>();
	public Map<Integer, Double> western = new HashMap<Integer, Double>();
	public List<Integer> userList;
	Connection coneConnection = null;
	public DataModel model;
	public int finaluserID;

	

	public WeightCalculation(int finaluserID) {
		this.finaluserID=finaluserID;
		

		// TODO Auto-generated constructor stub
		numberofMovieesWatch = new HashMap<Integer, Integer>();
		averageUserRating = new HashMap<Integer, Double>();
		dbConnection = new DBConnection();
		coneConnection = dbConnection.sqliteConncetion();
		try {
			model = new FileDataModel(new File("data/ratings.csv"));
			LongPrimitiveIterator userIDS = model.getUserIDs();
			userList = new ArrayList<Integer>();
			while (userIDS.hasNext()) {
				userList.add((int) (long) userIDS.nextLong());
			}
			for (int i = 0; i < userList.size(); i++) {
				FastIDSet itemIDs = model.getItemIDsFromUser(userList.get(i));
				long[] arrayitemID = itemIDs.toArray();
				numberofMovieesWatch.put(userList.get(i), arrayitemID.length);
				/*
				 * System.out.println("User: " + userList.get(i) + " " +
				 * numberofMovieesWatch.get(userList.get(i)));
				 */
			}
			Calculate();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, e);

		} catch (TasteException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, e);
		}

	}

	private void Calculate() {
		// TODO Auto-generated method stub
		for (int i = 0; i < userList.size(); i++) {
			action.put(userList.get(i), 0.0);
			animation.put(userList.get(i), 0.0);
			adventure.put(userList.get(i), 0.0);
			childrens.put(userList.get(i), 0.0);
			comedy.put(userList.get(i), 0.0);
			crime.put(userList.get(i), 0.0);
			documentary.put(userList.get(i), 0.0);
			drama.put(userList.get(i), 0.0);
			fantasy.put(userList.get(i), 0.0);
			filmnoir.put(userList.get(i), 0.0);
			horror.put(userList.get(i), 0.0);
			musical.put(userList.get(i), 0.0);
			mystery.put(userList.get(i), 0.0);
			romance.put(userList.get(i), 0.0);
			scifi.put(userList.get(i), 0.0);
			thrillar.put(userList.get(i), 0.0);
			war.put(userList.get(i), 0.0);
			western.put(userList.get(i), 0.0);
		}
		coneConnection = dbConnection.sqliteConncetion();
		try {
			String query = "SELECT * FROM ratings_moviegenre ";
			PreparedStatement preparedStatement = coneConnection
					.prepareStatement(query);
			ResultSet resultSet = preparedStatement.executeQuery();
			int count = 0;
			while (resultSet.next()) {
				int userID = resultSet.getInt("userID");
				int movieID = resultSet.getInt("movieId");
				String movieName = resultSet.getString("title");
				double rating = resultSet.getDouble("rating");
				String movieGenres = resultSet.getString("genres");
				if (movieGenres.contains("Action")) {
					double sum = action.get(userID);
					sum = sum + (rating / 5.0);
					action.put(userID, sum);
				}
				if (movieGenres.contains("Adventure")) {
					double sum = adventure.get(userID);
					sum = sum + (rating / 5.0);
					adventure.put(userID, sum);
				}
				if (movieGenres.contains("Animation")) {
					double sum = animation.get(userID);
					sum = sum + (rating / 5.0);
					animation.put(userID, sum);
				}
				if (movieGenres.contains("Children")) {
					double sum = childrens.get(userID);
					sum = sum + (rating / 5.0);
					childrens.put(userID, sum);
				}
				if (movieGenres.contains("Comedy")) {
					double sum = comedy.get(userID);
					sum = sum + (rating / 5.0);
					comedy.put(userID, sum);
				}
				if (movieGenres.contains("Crime")) {
					double sum = crime.get(userID);
					sum = sum + (rating / 5.0);
					crime.put(userID, sum);
				}
				if (movieGenres.contains("Documentary")) {
					double sum = documentary.get(userID);
					sum = sum + (rating / 5.0);
					documentary.put(userID, sum);
				}
				if (movieGenres.contains("Drama")) {
					double sum = drama.get(userID);
					sum = sum + (rating / 5.0);
					drama.put(userID, sum);
				}
				if (movieGenres.contains("Fantasy")) {
					double sum = fantasy.get(userID);
					sum = sum + (rating / 5.0);
					fantasy.put(userID, sum);
				}
				if (movieGenres.contains("Film-Noir")) {
					double sum = filmnoir.get(userID);
					sum = sum + (rating / 5.0);
					filmnoir.put(userID, sum);
				}
				if (movieGenres.contains("Horror")) {
					double sum = horror.get(userID);
					sum = sum + (rating / 5.0);
					horror.put(userID, sum);
				}
				if (movieGenres.contains("Musical")) {
					double sum = musical.get(userID);
					sum = sum + (rating / 5.0);
					musical.put(userID, sum);
				}
				if (movieGenres.contains("Mystery")) {
					double sum = mystery.get(userID);
					sum = sum + (rating / 5.0);
					mystery.put(userID, sum);
				}
				if (movieGenres.contains("Romance")) {
					double sum = romance.get(userID);
					sum = sum + (rating / 5.0);
					romance.put(userID, sum);
				}
				if (movieGenres.contains("Sci-Fi")) {
					double sum = scifi.get(userID);
					sum = sum + (rating / 5.0);
					scifi.put(userID, sum);
				}
				if (movieGenres.contains("Thriller")) {
					double sum = thrillar.get(userID);
					sum = sum + (rating / 5.0);
					thrillar.put(userID, sum);
				}
				if (movieGenres.contains("War")) {
					double sum = war.get(userID);
					sum = sum + (rating / 5.0);
					war.put(userID, sum);
				}
				if (movieGenres.contains("Western")) {
					double sum = western.get(userID);
					sum = sum + (rating / 5.0);
					western.put(userID, sum);
				}

				// System.out.println(userID+" "+movieID+" "+movieName+" "+rating+" "+movieGenres);
			}
			for (int i = 0; i < userList.size(); i++) {
				double val = 0.0;
				val = action.get(userList.get(i))
						/ numberofMovieesWatch.get(userList.get(i));
				action.put(userList.get(i), val);
				val = adventure.get(userList.get(i))
						/ numberofMovieesWatch.get(userList.get(i));
				adventure.put(userList.get(i), val);
				val = animation.get(userList.get(i))
						/ numberofMovieesWatch.get(userList.get(i));
				animation.put(userList.get(i), val);
				val = childrens.get(userList.get(i))
						/ numberofMovieesWatch.get(userList.get(i));
				childrens.put(userList.get(i), val);
				val = comedy.get(userList.get(i))
						/ numberofMovieesWatch.get(userList.get(i));
				comedy.put(userList.get(i), val);
				val = crime.get(userList.get(i))
						/ numberofMovieesWatch.get(userList.get(i));
				crime.put(userList.get(i), val);
				val = documentary.get(userList.get(i))
						/ numberofMovieesWatch.get(userList.get(i));
				documentary.put(userList.get(i), val);
				val = drama.get(userList.get(i))
						/ numberofMovieesWatch.get(userList.get(i));
				drama.put(userList.get(i), val);
				val = fantasy.get(userList.get(i))
						/ numberofMovieesWatch.get(userList.get(i));
				fantasy.put(userList.get(i), val);
				val = filmnoir.get(userList.get(i))
						/ numberofMovieesWatch.get(userList.get(i));
				filmnoir.put(userList.get(i), val);
				val = horror.get(userList.get(i))
						/ numberofMovieesWatch.get(userList.get(i));
				horror.put(userList.get(i), val);
				val = musical.get(userList.get(i))
						/ numberofMovieesWatch.get(userList.get(i));
				musical.put(userList.get(i), val);
				val = mystery.get(userList.get(i))
						/ numberofMovieesWatch.get(userList.get(i));
				mystery.put(userList.get(i), val);
				val = romance.get(userList.get(i))
						/ numberofMovieesWatch.get(userList.get(i));
				romance.put(userList.get(i), val);
				val = scifi.get(userList.get(i))
						/ numberofMovieesWatch.get(userList.get(i));
				scifi.put(userList.get(i), val);
				val = thrillar.get(userList.get(i))
						/ numberofMovieesWatch.get(userList.get(i));
				thrillar.put(userList.get(i), val);
				val = war.get(userList.get(i))
						/ numberofMovieesWatch.get(userList.get(i));
				war.put(userList.get(i), val);
				val = western.get(userList.get(i))
						/ numberofMovieesWatch.get(userList.get(i));
				western.put(userList.get(i), val);

			}
			CalculateListofSimilarUser();
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}

	private void CalculateListofSimilarUser() {
		// TODO Auto-generated method stub
		DecimalFormat df = new DecimalFormat("#.###");
		Pair<Integer, Double> pair;
		double sim = 0.0;
		for (int i = 0; i < userList.size(); i++) {
			// System.out.print("userID " + userList.get(i) + ": ");
			for (int j = 0; j < userList.size(); j++) {
				sim = Math.pow((action.get(userList.get(i)) - action
						.get(userList.get(j))), 2)
						+ Math.pow((animation.get(userList.get(i)) - animation
								.get(userList.get(j))), 2)
						+ Math.pow((adventure.get(userList.get(i)) - adventure
								.get(userList.get(j))), 2)
						+ Math.pow((childrens.get(userList.get(i)) - childrens
								.get(userList.get(j))), 2)
						+ Math.pow((comedy.get(userList.get(i)) - comedy
								.get(userList.get(j))), 2)
						+ Math.pow((crime.get(userList.get(i)) - crime
								.get(userList.get(j))), 2)
						+ Math.pow(
								(documentary.get(userList.get(i)) - documentary
										.get(userList.get(j))), 2)
						+ Math.pow((drama.get(userList.get(i)) - drama
								.get(userList.get(j))), 2)
						+ Math.pow((fantasy.get(userList.get(i)) - fantasy
								.get(userList.get(j))), 2)
						+ Math.pow((filmnoir.get(userList.get(i)) - filmnoir
								.get(userList.get(j))), 2)
						+ Math.pow((horror.get(userList.get(i)) - horror
								.get(userList.get(j))), 2)
						+ Math.pow((musical.get(userList.get(i)) - musical
								.get(userList.get(j))), 2)
						+ Math.pow((mystery.get(userList.get(i)) - mystery
								.get(userList.get(j))), 2)
						+ Math.pow((romance.get(userList.get(i)) - romance
								.get(userList.get(j))), 2)
						+ Math.pow((scifi.get(userList.get(i)) - scifi
								.get(userList.get(j))), 2)
						+ Math.pow((thrillar.get(userList.get(i)) - thrillar
								.get(userList.get(j))), 2)
						+ Math.pow((war.get(userList.get(i)) - war.get(userList
								.get(j))), 2)
						+ Math.pow((western.get(userList.get(i)) - western
								.get(userList.get(j))), 2);
				sim = Math.pow(sim, 0.5);
				if (userList.get(i) == userList.get(j)) {
					sim = 1000000000;
				}
				if (sim < 100) {
					sim = Double.parseDouble(df.format(sim));
				}
				pair = new Pair<Integer, Double>(userList.get(j), sim);

				/*
				 * //similarityBetweenUsers.put(userList.get(i),pair);
				 */simibetuser.add(new Pair<Integer, Pair<Integer, Double>>(
						userList.get(i), pair));
				// System.out.println(userList.get(i)+" "+userList.get(j)+" "+df.format(sim)
				// + " , ");
			}
			// System.out.println("\n");
		}
		try {
			int i = 0;
			// printSimilarityValue();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		calculteKmostsimilarUser();
	}

	private void printSimilarityValue() throws IOException {
		// TODO Auto-generated method stub
		try {
			bw = new BufferedWriter(new FileWriter("data/weightValue.csv"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (int i = 0; i < simibetuser.size(); i++) {
			bw.write(simibetuser.get(i).getFirst() + " "
					+ simibetuser.get(i).getSecond().getFirst() + " "
					+ simibetuser.get(i).getSecond().getSecond());
			/*
			 * System.out.println(simibetuser.get(i).getFirst() + " " +
			 * simibetuser.get(i).getSecond().getFirst() + " " +
			 * simibetuser.get(i).getSecond().getSecond())
			 */;
			bw.write("\n");
		}
		bw.close();

	}

	private void calculteKmostsimilarUser() {
		// TODO Auto-generated method stub
		int tempuser = 0;
		if (userList.contains(finaluserID)) {
			tempuser = finaluserID;
		}
        System.out.println(tempuser);
		int k = 10;
		mappy = new HashMap<Integer, Double>();
		listofSimillaruser = new HashMap<Integer, List<Integer>>();
		for (int i = 0; i < simibetuser.size(); i++) {

			int user = simibetuser.get(i).getFirst();
			if (tempuser == user) {
				mappy.put(simibetuser.get(i).getSecond().getFirst(), simibetuser.get(i).getSecond().getSecond());
			} 

		}
		mappy = sortByValue(mappy);
	 // printonlyone(mappy);
		List<Integer> ls = new ArrayList<Integer>();
		int count = 0;
		for (Map.Entry<Integer, Double> entry : mappy.entrySet()) {

			ls.add(entry.getKey());
			count++;
			if (count == k) {
				break;
			}
		}
		listofSimillaruser.put(tempuser, ls);
		similarityBetweenUsers.put(tempuser, mappy);

		try {
			// printNewSimMap();
			calulateAvgratingforalluser();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void calulateAvgratingforalluser() throws Exception {

		for (int i = 0; i < userList.size(); i++) {
			FastIDSet itemIDs = model.getItemIDsFromUser(userList.get(i));
			long[] arrayitemID = itemIDs.toArray();

			double sumofpref = 0.0;
			for (int j = 0; j < arrayitemID.length; j++) {
				// System.out.println(arrayitemID[j]);
				sumofpref = sumofpref
						+ model.getPreferenceValue(userList.get(i),
								arrayitemID[j]);
			}
			sumofpref = sumofpref / numberofMovieesWatch.get(userList.get(i));
			averageUserRating.put(userList.get(i),
					Double.parseDouble(df.format(sumofpref)));
			// System.out.println(userList.get(i) + " " + df.format(sumofpref));
		}
		calAllotherItems();
	}

	private void calAllotherItems() throws TasteException, Exception {
		// TODO Auto-generated method stub
		FastIDSet allItemIDs = getAllOtherItems(
				listofSimillaruser.get(finaluserID), finaluserID);
		System.out.println(" Wahid: "
				+ listofSimillaruser.get(finaluserID).toString());
		System.out.println(allItemIDs.toString());
		System.out.println(mappy.toString());
		long[] itemID = allItemIDs.toArray();
		Map<Long, Double> prefOfUser = new HashMap<Long, Double>();
		if (mappy != null) {
			if (mappy.containsKey(475)) {
				/*
				 * System.out.println(mappy.containsKey(475));
				 * System.out.println(mappy.get(475));
				 * System.out.println(averageUserRating.get(475));
				 * System.out.println(model.getPreferenceValue(475,6016));
				 */
			}
			for (int i = 0; i < itemID.length; i++) {
				double prefs = 0.0;
				double sumofsim = 0.0;
				for (Integer list : listofSimillaruser.get(finaluserID)) {
					if (model != null) {
						Float pref1 = model.getPreferenceValue(
								list.longValue(), itemID[i]);
						if (pref1 != null) {
							try {
								prefs = prefs
										+ mappy.get(list)
										* (pref1.doubleValue() - averageUserRating
												.get(list));
								if(itemID[i]==3836){
									System.out.println(itemID[i]+" "+prefs+" "+mappy.get(list)+" "+list+" "+averageUserRating.get(list));
								}
							} catch (Exception e) {
								System.out.println(pref1.doubleValue() + " "
										+ averageUserRating.get(list) + " "
										+ list);
								System.out.println(e.toString());
							}
							sumofsim = sumofsim + mappy.get(list);
							if(itemID[i]==3836){
								System.out.println(sumofsim);
							}
						}
					}
				}
				prefs = prefs / sumofsim;
				if(itemID[i]==3836){
					System.out.println("prefs: "+prefs);
				}
				prefs = prefs + averageUserRating.get(finaluserID);
				if(itemID[i]==3836){
					System.out.println("new prefs: "+prefs+" "+averageUserRating.get(finaluserID) );
				}
				/*
				 * System.out.println("User id:1 " + itemID[i] + " " +
				 * df.format(prefs));
				 */
				/*System.out.println("User id:1 " + itemID[i] + " "
						+ df.format(prefs));*/
				if (!Double.isNaN(prefs)) {
					prefOfUser.put(itemID[i],
							Double.parseDouble(df.format(prefs)));
				} else {
					prefOfUser.put(itemID[i], 0.0);
				}
			}
			sortedMap = sortByValue1(prefOfUser);
			printMap(sortedMap);
			printmovieList(movieListName);

		} else {
			System.out.println("mapp is null");
		}
	}
	private void printmovieList(Map<Long, String> movieListName2) {
		// TODO Auto-generated method stub
		for (Map.Entry<Long, String> entry : movieListName2.entrySet()){
			System.out.println(entry.getKey()+" "+entry.getValue());
		}
	}

	public Map<Long, Double> getSortedMap(){
		return sortedMap;
	}
	public Map<Long, String> movieList(){
		return movieListName;
	}
public Map<Long, String>movieListName;
	private  void printMap(Map<Long, Double> map) throws Exception {
		// TODO Auto-generated method stub
		int numberOfrecommendItem = 0;
		movieListName= new HashMap<Long, String>();
		for (Map.Entry<Long, Double> entry : map.entrySet()) {
			System.out.println("ItemID " + entry.getKey()
					+ " Prefrence Value: " + map.get(entry.getKey()));
			String query = "SELECT title FROM movies1 where movieId="+entry.getKey();
			PreparedStatement preparedStatement = coneConnection
					.prepareStatement(query);
			ResultSet resultSet = preparedStatement.executeQuery();
			if(resultSet!=null){
				movieListName.put(entry.getKey(),resultSet.getString("title"));
			}
			if (numberOfrecommendItem == 10) {
				break;
			} else {
				numberOfrecommendItem++;
			}
		}
	}

	private void printonlyone(Map<Integer, Double> map) {
		// TODO Auto-generated method stub
		for (Map.Entry<Integer, Double> entry : map.entrySet()) {
			System.out.println("Key : " + entry.getKey() + " Value : "
					+ entry.getValue());
		}

	}

	private void printNewSimMap() throws Exception {
		// TODO Auto-generated method stub
		bw1 = new BufferedWriter(new FileWriter("data/similarityval.csv"));
		for (Map.Entry<Integer, Map<Integer, Double>> t : this.similarityBetweenUsers
				.entrySet()) {
			Integer key = t.getKey();
			for (Map.Entry<Integer, Double> e : t.getValue().entrySet()) {
				// System.out.println("OuterKey:" + key + " InnerKey: " +
				// e.getKey()+ " VALUE:" +e.getValue());
				bw1.write(key + " " + e.getKey() + " " + e.getValue());
				bw1.write("\n");
			}
		}
		bw1.close();
	}

	private static Map<Integer, Double> sortByValue(
			Map<Integer, Double> unsortMap) {

		// 1. Convert Map to List of Map
		List<Map.Entry<Integer, Double>> list = new LinkedList<Map.Entry<Integer, Double>>(
				unsortMap.entrySet());

		// 2. Sort list with Collections.sort(), provide a custom Comparator
		// Try switch the o1 o2 position for a different order
		Collections.sort(list, new Comparator<Map.Entry<Integer, Double>>() {
			public int compare(Map.Entry<Integer, Double> o1,
					Map.Entry<Integer, Double> o2) {
				return (o1.getValue()).compareTo(o2.getValue());
			}
		});

		// 3. Loop the sorted list and put it into a new insertion order Map
		// LinkedHashMap
		Map<Integer, Double> sortedMap = new LinkedHashMap<Integer, Double>();
		for (Map.Entry<Integer, Double> entry : list) {
			sortedMap.put(entry.getKey(), entry.getValue());
		}

		/*
		 * //classic iterator example for (Iterator<Map.Entry<String, Integer>>
		 * it = list.iterator(); it.hasNext(); ) { Map.Entry<String, Integer>
		 * entry = it.next(); sortedMap.put(entry.getKey(), entry.getValue()); }
		 */

		return sortedMap;
	}

	public FastIDSet getAllOtherItems(List<Integer> theNeighborhood,
			int theUserID) throws TasteException, Exception {
		FastIDSet possibleItemIDs = new FastIDSet();
		DataModel model1 = new FileDataModel(new File("data/ratings.csv"));
		for (long userID : theNeighborhood) {

			possibleItemIDs.addAll(model1.getItemIDsFromUser(userID));
		}
		possibleItemIDs.removeAll(model1.getItemIDsFromUser(theUserID));
		return possibleItemIDs;
	}

	private static Map<Long, Double> sortByValue1(Map<Long, Double> prefOfUser) {
		// TODO Auto-generated method stub
		List<Map.Entry<Long, Double>> list = new LinkedList<Map.Entry<Long, Double>>(
				prefOfUser.entrySet());
		Collections.sort(list, new Comparator<Map.Entry<Long, Double>>() {
			public int compare(Map.Entry<Long, Double> o1,
					Map.Entry<Long, Double> o2) {
				return (o2.getValue()).compareTo(o1.getValue());
			}
		});
		Map<Long, Double> sortedMap = new LinkedHashMap<Long, Double>();
		for (Map.Entry<Long, Double> entry : list) {
			sortedMap.put(entry.getKey(), entry.getValue());
		}
		;
		return sortedMap;
	}

	/*public static void main(String[] args) {
		// TODO Auto-generated method stub
		WeightCalculation weightCalculation = new WeightCalculation(5);
		
	}*/

}
