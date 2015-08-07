import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

class Team{
	String name;
	int division_standing;
	public enum Confrence{
		AFC, NFC
	}
	public enum Division{
		East, North, South, West
	}
	Confrence conf;
	Division div;
	String [] schedule = new String[17];

	public Team(String name, Confrence conf, Division div, int division_standing){
		this.name = name;
		this.conf = conf;
		this.div = div;
		this.division_standing = division_standing;
	}

	public String getName(){
		return this.name;
	}

	public String [] getSchedule(){
		return schedule;
	}

	public void setSchedule(String [] sch){
		this.schedule = sch;
	}

	public void printSchedule(){
		System.out.println(this.getName());
		for(int i = 0 ; i < this.schedule.length ; ++i){
			int week = i + 1;
			System.out.println("week " + week + ": " + this.schedule[i]);
		}
	}
/*
	public static Team returnObjectByName(ArrayList a,String name){
		Team iter = null;
		for(int i = 0 ; i < a.size() ; ++i){
			iter = (Team)a.get(i);
			if(name.equals(iter.getName())) break;
		}
		return iter;
	}

	public static ArrayList init_teams(String city, String name, int prev, Scanner sc, ArrayList team, Confrence conf, Division div){
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
			System.out.println(t.getName() + " are on a bye in Week " + bye_week);
		}
	}

	public static void setGame(Team a , Team b, boolean isAHome, int week){
		String [] aSch = a.getSchedule();
		String [] bSch = b.getSchedule();
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
		int divOpp = rand.nextInt(3) + 1;
		System.out.println(divOpp);
		if(divOpp == 1){
			boolean home = rand.nextBoolean();
			Team t0 = (Team)div.get(0);
			Team t1 = (Team)div.get(1);
			String [] sch0 = t0.getSchedule();
			String [] sch1 = t1.getSchedule();
			int returnTrip = rand.nextInt(16);
			while(sch0[returnTrip] != null && sch1[returnTrip] != null) returnTrip = rand.nextInt(16);
			setGame(t0, t1, home, 16);
			setGame(t0, t1, !home, returnTrip);
		} else if (divOpp == 2){
			boolean home = rand.nextBoolean();
			Team t0 = (Team)div.get(0);
			Team t2 = (Team)div.get(2);
			String [] sch0 = t0.getSchedule();
			String [] sch2 = t2.getSchedule();
			int returnTrip = rand.nextInt(16);
			while(sch0[returnTrip] != null && sch2[returnTrip] != null) returnTrip = rand.nextInt(16);
			setGame(t0, t2, home, 16);
			setGame(t0, t2, !home, returnTrip);
		} else {
			boolean home = rand.nextBoolean();
			Team t0 = (Team)div.get(0);
			Team t3 = (Team)div.get(3);
			String [] sch0 = t0.getSchedule();
			String [] sch3 = t3.getSchedule();
			int returnTrip = rand.nextInt(16);
			while(sch0[returnTrip] != null && sch3[returnTrip] != null) returnTrip = rand.nextInt(16);
			setGame(t0, t3, home, 16);
			setGame(t0, t3, !home, returnTrip);
		}
	}

	public static void setDivisionGames(ArrayList teams){
		ArrayList divisions = new ArrayList<>();
		ArrayList div = new ArrayList<>();
		div.add(teams.get(0));
		for(int i = 1; i < teams.size() ; ++i){
		//	System.out.println(((Team)teams.get(i)).getName());
			if(i % 4 == 0){
				divisions.add(div);
				for(int j = 0 ; j < divisions.size() ; ++j){
					ArrayList a = (ArrayList)divisions.get(j);
					System.out.println("BEFORE!");
					for(int k = 0 ; k < a.size(); ++k){
						System.out.println(((Team)a.get(k)).getName());
					}
					System.out.println("AFTER!");
				}
				div.clear();
			}
			div.add(teams.get(i));
		}
	 	divisions.add(div);
	 	System.out.println(divisions.size());
		for(int i = 0 ; i < divisions.size() ; ++i) {
			ArrayList d = (ArrayList)divisions.get(i);
			System.out.println(d.size());
		//	setDivGame(d);
			for(int j = 0 ; j < d.size() ; ++j){
				Team a = (Team)d.get(j);
				String [] sch = a.getSchedule();
				System.out.println(a.getName());
		//		for(int k = 0 ; k < sch.length ; ++k){
		//			if(sch[k] != null){
		//				System.out.println("Week " + (k+1) + ": " + sch[k]);
		//			}
		//		}
			}
		}	

	}

	public static void printArray(ArrayList teams){
		for(int i = 0; i < teams.size() ; ++i){
			Team a = (Team)teams.get(i);
			System.out.println(a.getName());
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
		teams = init_teams("Buffalo","Bills", prev, sc, teams, Confrence.AFC, Division.East);
		teams = init_teams("Miami","Dolphins", prev, sc, teams, Confrence.AFC, Division.East);
		teams = init_teams("New England","Patroits", prev, sc, teams, Confrence.AFC, Division.East);
		teams = init_teams("New York","Jets", prev, sc, teams, Confrence.AFC, Division.East);
		// AFC North
		teams = init_teams("Baltimore","Ravens", prev, sc, teams, Confrence.AFC, Division.North);
		teams = init_teams("Cincinnatti","Bengals", prev, sc, teams, Confrence.AFC, Division.North);
		teams = init_teams("CLeveland","Browns", prev, sc, teams, Confrence.AFC, Division.North);
		teams = init_teams("Pittsburgh","Steelers", prev, sc, teams, Confrence.AFC, Division.North);
		// AFC South
		teams = init_teams("Houston","Texans", prev, sc, teams, Confrence.AFC, Division.South);
		teams = init_teams("Jacksonville","Jaguars", prev, sc, teams, Confrence.AFC, Division.South);
		teams = init_teams("Indianapolis","Colts", prev, sc, teams, Confrence.AFC, Division.South);
		teams = init_teams("Tennessee","Titans", prev, sc, teams, Confrence.AFC, Division.South);
		// AFC West
		teams = init_teams("Denver","Broncos", prev, sc, teams, Confrence.AFC, Division.West);
		teams = init_teams("Kansas City","Chiefs", prev, sc, teams, Confrence.AFC, Division.West);
		teams = init_teams("Oakland","Raiders", prev, sc, teams, Confrence.AFC, Division.West);
		teams = init_teams("San Diego","Chargers", prev, sc, teams, Confrence.AFC, Division.West);
		// NFC teams
		// NFC East
		teams = init_teams("Dallas","Cowboys", prev, sc, teams, Confrence.NFC, Division.East);
		teams = init_teams("New York","Giants", prev, sc, teams, Confrence.NFC, Division.East);
		teams = init_teams("Philadelphia","Eagles", prev, sc, teams, Confrence.NFC, Division.East);
		teams = init_teams("Washington","Redskins", prev, sc, teams, Confrence.NFC, Division.East);
		// NFC North
		teams = init_teams("Chicago","Bears", prev, sc, teams, Confrence.NFC, Division.North);
		teams = init_teams("Detroit","Lions", prev, sc, teams, Confrence.NFC, Division.North);
		teams = init_teams("Green Bay","Packers", prev, sc, teams, Confrence.NFC, Division.North);
		teams = init_teams("Minnesota","Vikings", prev, sc, teams, Confrence.NFC, Division.North);
		// NFC South
		teams = init_teams("Atlanta","Falcons", prev, sc, teams, Confrence.NFC, Division.South);
		teams = init_teams("Carolina","Panthers", prev, sc, teams, Confrence.NFC, Division.South);
		teams = init_teams("New Orleans","Saints", prev, sc, teams, Confrence.NFC, Division.South);
		teams = init_teams("Tampa Bay","Buccanners", prev, sc, teams, Confrence.NFC, Division.South);
		// NFC West
		teams = init_teams("Arizona","Cardinals", prev, sc, teams, Confrence.NFC, Division.West);
		teams = init_teams("San Francisco","49ers", prev, sc, teams, Confrence.NFC, Division.West);
		teams = init_teams("Seattle","Seahawks", prev, sc, teams, Confrence.NFC, Division.West);
		teams = init_teams("St.Louis","Rams", prev, sc, teams, Confrence.NFC, Division.West);

		setByeWeek(teams);
		setDivisionGames(teams);
	}
	*/
}