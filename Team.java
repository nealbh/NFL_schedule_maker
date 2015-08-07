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
}