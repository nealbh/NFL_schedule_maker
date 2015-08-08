import java.util.Random;
import java.util.Scanner;
import java.util.ArrayList;
class NFL_schedule{
	public static Team returnObjectByName(ArrayList a,String name){
		Team iter = null;
		for(int i = 0 ; i < a.size() ; ++i){
			iter = (Team)a.get(i);
			if(name.equals(iter.getName())) break;
		}
		return iter;
	}

	public static ArrayList init_teams(String city, String name, int prev, Scanner sc, ArrayList team, Team.Confrence conf, Team.Division div){
		System.out.print("Where did the " +city + " "+ name + " finish in the " + conf +" "+ div + " in " + prev + " : ");
		int stand = sc.nextInt();
		while(stand < 1 || stand > 4){
			System.out.print("Please enter a number between 1-4 where the "+name+" finished the season in "+prev+": ");
			stand = sc.nextInt();
		}
		team.add(new Team(name, conf, div, stand));
		return team;
	}

	public static void setByeWeek(ArrayList teams){
		ArrayList<Team> bye_selection = new ArrayList<Team>();
		bye_selection.addAll(teams);

		for(int i = 0 ; i < teams.size(); ++i){
			int bye_week = (i / 4) + 4;
			Random rand = new Random();
			int bye_team = rand.nextInt(bye_selection.size());
			Team t = returnObjectByName(teams, bye_selection.get(bye_team).getName());
			bye_selection.remove(bye_team);
			String [] s = t.getSchedule();
			// array zero based, schedule one based
			s[bye_week-1] = "Bye";
			t.setSchedule(s);
	//		System.out.println(t.getName() + " are on a bye in Week " + bye_week);
		}
	}

	public static void setGame(Team a , Team b, boolean isAHome, int week){
		String [] aSch = a.getSchedule();
		String [] bSch = b.getSchedule();
		if(week == -1){
			Random r = new Random();
			week = r.nextInt(16);
			while(aSch[week] != null || bSch[week] != null){
				week = r.nextInt(16);
			}
		}
		if(isAHome){
			aSch[week] = b.getName();
			bSch[week] = "@ " + a.getName();
		} else {
			aSch[week] = "@ " + b.getName();
			bSch[week] = a.getName();
		}
		a.setSchedule(aSch);
		b.setSchedule(bSch);
	}

	public static void setDivGame(ArrayList div){
		Random rand = new Random();
		boolean home = rand.nextBoolean();
		int divOpp = rand.nextInt(3) + 1;
		Team a = (Team)div.get(0);
		Team b = (Team)div.get(divOpp);
		div.remove(divOpp);
		div.remove(0);
		Team c = (Team)div.get(0);
		Team d = (Team)div.get(1);
		setGame(a, b, home, 16);
		setGame(a, b, !home, -1);
		home = rand.nextBoolean();
		setGame(c, d, home, 16);
		setGame(c, d, !home, -1);
		home = rand.nextBoolean();
		setGame(a, c, home, -1);
		setGame(a, c, !home, -1);
		home = rand.nextBoolean();
		setGame(a, d, home, -1);
		setGame(a, d, !home, -1);
		home = rand.nextBoolean();
		setGame(b, c, home, -1);
		setGame(b, c, !home, -1);
		home = rand.nextBoolean();
		setGame(b, d, home, -1);
		setGame(b, d, !home, -1);
	}

	public static void setDivisionGames(ArrayList teams){
		ArrayList div = new ArrayList<>();
		div.add(teams.get(0));
		for(int i = 1; i < teams.size() ; ++i){
			if(i % 4 == 0){
				setDivGame(div);
				div.clear();
			}
			div.add(teams.get(i));
		}
		setDivGame(div);
	}

	public static void printArray(ArrayList teams){
		for(int i = 0; i < teams.size() ; ++i){
			Team a = (Team)teams.get(i);
			System.out.println(a.getName());
		}
	}

	/*
		http://www.pro-football-reference.com/blog/?p=521
		For the inter-conference matchups between divisions, the following schedule is used to determine which divisions will face off each year:

		1 -- 2002: West versus East, North versus South
		2 -- *2003: West versus North, East versus South
		0 -- 2004: West versus South, North versus East

		The pattern then repeats starting in 2005.

		* -- 2015 starting point
	*/

	public static void setInterConfMatchups(Team [] div1, Team [] div2){
		Random rand = new Random();
		for(int i = 0 ; i < div1.length ; ++i){
			Team a = div1[i];
			boolean isAHome = rand.nextBoolean();
			for(int j = 0 ; j < div2.length ; ++j){
				isAHome = !isAHome; 
				Team b = div2[j];
				setGame(a, b, isAHome, -1);
			}
		}
	}

	public static void determineinterConfMatchUps(Team [][] conf, int year){
		Team [] east = conf[0];
		Team [] north = conf[1];
		Team [] south = conf[2];
		Team [] west = conf[3];
		if(year == 0) {
			setInterConfMatchups(west, south);
			setInterConfMatchups(north, east);
		} else if (year == 1){
			setInterConfMatchups(west, east);
			setInterConfMatchups(north, south);
		} else {
			setInterConfMatchups(west, north);
			setInterConfMatchups(east, south);
		}
	}

	public static void setInterconfrenceGames(ArrayList teams, int year){
	//	ArrayList<ArrayList<Team>> nfc = new ArrayList<ArrayList<Team>>();
	//	ArrayList<ArrayList<Team>> afc = new ArrayList<ArrayList<Team>>();
	//	ArrayList div = new ArrayList();
		Team [][] nfc = new Team[4][4];
		int nfc_counter = 0;
		Team [][] afc = new Team[4][4];
		int afc_counter = 0;
		Team [] div = new Team[4];
		int div_counter = 0;
	//	div.add(teams.get(0));
		div[div_counter++] = (Team)teams.get(0);
		for(int i = 1 ; i < teams.size() ; ++i){
			Team iter = (Team)teams.get(i);
			if(i % 4 == 0){
	//			if (afc[.size()] < 4){
				if(afc[3][0] == null){
					afc[afc_counter++] = div;
				
	//				boolean isSuccessful = afc.add(div);
	//				for(int j = 0 ; j < div.size() ; ++j){
	//					Team a = (Team)div.get(j);
	//					System.out.println(a.getName());
	//				}
		/*
					System.out.println("IS ADDTION SUCCESSFUL : " + isSuccessful);
					System.out.println("The size of AFC is : " + afc.size());
					for(int j = 0 ; j < afc.size() ; ++j){
						System.out.println("j = " + j);
						ArrayList<Team> te = (ArrayList)afc.get(j);
						System.out.println(te.size());
						System.out.println("BEGIN DIVISION");
						for(int k = 0 ; k < te.size() ; ++k){
							Team a = (Team)te.get(k);
							System.out.println(a.getName());
						}
						System.out.println("END DIVISION");
					}
		*/
				} else {
				//	nfc.add(div);
					/*
					for(int j = 0 ; j < div.size() ; ++j){
						Team a = (Team)div.get(j);
						System.out.println(a.getName());
					}
					*/
					nfc[nfc_counter++] = div;
				}
				//div.clear();
				div = new Team[4];
				div_counter = 0;
				/*
				System.out.println(div.size());
				System.out.println("BIG TEST");
				for(int j = 0 ; j < div.size(); ++j){
					Team a = (Team)div.get(j);
					System.out.println(a.getName());
				}
				System.out.println("END TEST");
				*/
			}
			//div.add(iter);
			div[div_counter++] = iter;
		}
		nfc[nfc_counter] = div;
	/*	nfc.add(div);
		System.out.println(afc);
		System.out.println(nfc);
		for(int i = 0; i < afc.length ; ++i){
			Team [] division = afc[i];
			for(int j = 0 ; j < division.length; ++j){
				Team a = division[j];
				System.out.println(a.getName());
			}
		}
	/*
		for(int i = 0 ; i < afc.size() ; ++i){
			ArrayList<Team> east = null;
			ArrayList<Team> north = null;
			ArrayList<Team> d;
			if(i % 2== 0) east = (ArrayList)nfc.get(i);
			else if(i % 2== 1) north = (ArrayList)nfc.get(i);
			else d = (ArrayList)nfc.get(i);
			System.out.println(i);
			boolean a = east == north;
			System.out.println("Is get(0) == get(1) : " + a);
			/*
			for (int j = 0;  j < d.size(); ++j ) {
				Team a = (Team) d.get(j);
				System.out.println(a.getName());
			}
			
		}
	*/

		determineinterConfMatchUps(afc, year);
	/*
		System.out.println("THE LENGTH OF NFC IS : " + nfc.length);
		for(int z = 0 ; z < nfc.length ; ++z){
			Team [] y = nfc[z];
			for(int x = 0 ; x < y.length ; ++x){
				Team w = y[x];
				System.out.println(w.getName());
			}
		}
	*/
		determineinterConfMatchUps(nfc, year);
	}

	public static void printSchedule(ArrayList teams){
		for(int i = 0 ; i < teams.size() ; ++i){
			Team a = (Team)teams.get(i);
			String [] aSch = a.getSchedule();
			System.out.println(a.getName());
			for (int j = 0 ; j < aSch.length ; ++j){
				if(aSch[j] != null)
				System.out.println("Week "+ (j+1) + " : " + aSch[j]);
			}
		}
	}

	public static void main (String [] args){
		Scanner sc = new Scanner(System.in);
		System.out.print("What year do you want a schedule for? YYYY : ");
		int year = sc.nextInt();
		ArrayList<Team> teams = new ArrayList<Team>(32);
		int prev = year - 1;
	
		// AFC teams
		// AFC East
		teams = init_teams("Buffalo","Bills", prev, sc, teams, Team.Confrence.AFC, Team.Division.East);
		teams = init_teams("Miami","Dolphins", prev, sc, teams, Team.Confrence.AFC, Team.Division.East);
		teams = init_teams("New England","Patroits", prev, sc, teams, Team.Confrence.AFC, Team.Division.East);
		teams = init_teams("New York","Jets", prev, sc, teams, Team.Confrence.AFC, Team.Division.East);
		// AFC North
		teams = init_teams("Baltimore","Ravens", prev, sc, teams, Team.Confrence.AFC, Team.Division.North);
		teams = init_teams("Cincinnatti","Bengals", prev, sc, teams, Team.Confrence.AFC, Team.Division.North);
		teams = init_teams("CLeveland","Browns", prev, sc, teams, Team.Confrence.AFC, Team.Division.North);
		teams = init_teams("Pittsburgh","Steelers", prev, sc, teams, Team.Confrence.AFC, Team.Division.North);
		// AFC South
		teams = init_teams("Houston","Texans", prev, sc, teams, Team.Confrence.AFC, Team.Division.South);
		teams = init_teams("Jacksonville","Jaguars", prev, sc, teams, Team.Confrence.AFC, Team.Division.South);
		teams = init_teams("Indianapolis","Colts", prev, sc, teams, Team.Confrence.AFC, Team.Division.South);
		teams = init_teams("Tennessee","Titans", prev, sc, teams, Team.Confrence.AFC, Team.Division.South);
		// AFC West
		teams = init_teams("Denver","Broncos", prev, sc, teams, Team.Confrence.AFC, Team.Division.West);
		teams = init_teams("Kansas City","Chiefs", prev, sc, teams, Team.Confrence.AFC, Team.Division.West);
		teams = init_teams("Oakland","Raiders", prev, sc, teams, Team.Confrence.AFC, Team.Division.West);
		teams = init_teams("San Diego","Chargers", prev, sc, teams, Team.Confrence.AFC, Team.Division.West);
		// NFC teams
		// NFC East
		teams = init_teams("Dallas","Cowboys", prev, sc, teams, Team.Confrence.NFC, Team.Division.East);
		teams = init_teams("New York","Giants", prev, sc, teams, Team.Confrence.NFC, Team.Division.East);
		teams = init_teams("Philadelphia","Eagles", prev, sc, teams, Team.Confrence.NFC, Team.Division.East);
		teams = init_teams("Washington","Redskins", prev, sc, teams, Team.Confrence.NFC, Team.Division.East);
		// NFC North
		teams = init_teams("Chicago","Bears", prev, sc, teams, Team.Confrence.NFC, Team.Division.North);
		teams = init_teams("Detroit","Lions", prev, sc, teams, Team.Confrence.NFC, Team.Division.North);
		teams = init_teams("Green Bay","Packers", prev, sc, teams, Team.Confrence.NFC, Team.Division.North);
		teams = init_teams("Minnesota","Vikings", prev, sc, teams, Team.Confrence.NFC, Team.Division.North);
		// NFC South
		teams = init_teams("Atlanta","Falcons", prev, sc, teams, Team.Confrence.NFC, Team.Division.South);
		teams = init_teams("Carolina","Panthers", prev, sc, teams, Team.Confrence.NFC, Team.Division.South);
		teams = init_teams("New Orleans","Saints", prev, sc, teams, Team.Confrence.NFC, Team.Division.South);
		teams = init_teams("Tampa Bay","Buccanners", prev, sc, teams, Team.Confrence.NFC, Team.Division.South);
		// NFC West
		teams = init_teams("Arizona","Cardinals", prev, sc, teams, Team.Confrence.NFC, Team.Division.West);
		teams = init_teams("San Francisco","49ers", prev, sc, teams, Team.Confrence.NFC, Team.Division.West);
		teams = init_teams("Seattle","Seahawks", prev, sc, teams, Team.Confrence.NFC, Team.Division.West);
		teams = init_teams("St.Louis","Rams", prev, sc, teams, Team.Confrence.NFC, Team.Division.West);

		setByeWeek(teams);
		setDivisionGames(teams);
		setInterconfrenceGames(teams, year % 3);
		printSchedule(teams);
	}


	/*
		http://www.pro-football-reference.com/blog/?p=521
		The rotation of intra-conference pairings is as follows:

		3 -- 2002: AFC West vs. NFC West, AFC North vs. NFC South, AFC South vs. NFC East, AFC East vs. NFC North
		2 -- *2003: AFC West vs. NFC North, AFC North vs. NFC West, AFC South vs. NFC South, AFC East vs. NFC East
		0 -- 2004: AFC West vs. NFC South, AFC North vs. NFC East, AFC South vs. NFC North, AFC East vs. NFC West
		1 -- 2005: AFC West vs. NFC East, AFC North vs. NFC North, AFC South vs. NFC West, AFC East vs. NFC South

		The pattern then repeats starting in 2006.
	*/
}
