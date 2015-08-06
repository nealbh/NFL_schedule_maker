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

	public static Team returnObjectByName(ArrayList a,String name){
		Team iter = null;
		for(int i = 0 ; i < a.size() ; ++i){
			iter = (Team)a.get(i);
			if(name.equals(iter.getName())) break;
		}
		return iter;
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
			s[bye_week] = "Bye";
			t.setSchedule(s);
			System.out.println(t.getName() + " are on a bye on Week " + bye_week);
		}
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
	}
}