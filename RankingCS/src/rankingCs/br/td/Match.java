package rankingCs.br.td;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class Match {
	
	private String matchId;
	private Date matchStart;
	private Date matchEnd;
	private List<Kill> listOfKills = new ArrayList<Kill>();
	private List<Player> matchRanking = new ArrayList<Player>();
	
	public String getMatchId() {
		return matchId;
	}
	public void setMatchId(String matchId) {
		this.matchId = matchId;
	}
	public Date getMatchStart() {
		return matchStart;
	}
	public void setMatchStart(Date matchStart) {
		this.matchStart = matchStart;
	}
	public List<Kill> getListOfKills() {
		return listOfKills;
	}
	public void setListOfKills(List<Kill> listOfKills) {
		this.listOfKills = listOfKills;
	}
	public Date getMatchEnd() {
		return matchEnd;
	}
	public void setMatchEnd(Date matchEnd) {
		this.matchEnd = matchEnd;
	}
	public List<Player> getMatchRanking() {
		return matchRanking;
	}
	public void setMatchRanking(List<Player> matchRanking) {
		this.matchRanking = matchRanking;
	}
	
	/**
	 * Finds a player inside match ranking given a name
	 * @param name (String): player name to find.
	 * @return (Player): Player object found with given name.
	 */
	public Player getPlayerFromRanking(String name){
		for(Player p : matchRanking){
			if(p.getPlayerName().equalsIgnoreCase(name)){
				return p;
			}
		}
		return null;
	}
	
	/**
	 * Closes a match, order the player ranking based on kills/deaths ratio and grant the players with awards
	 * @param dateEnd
	 */
	public void closeMatch(Date dateEnd){
		this.matchEnd = dateEnd;
		
		// order Ranking desc
		Collections.sort(this.matchRanking, new Comparator<Player>() {

			@Override
			public int compare(Player o1, Player o2) {
				int o1Ratio = o1.getNumberOfKills()-o1.getNumberOfDeaths();
				int o2Ratio = o2.getNumberOfKills()-o2.getNumberOfDeaths();
				
				if(o1Ratio < o2Ratio){
					return 1;
				}
				if(o1Ratio > o2Ratio){
					return -1;
				}
				
				return 0;
			}
			
		});
		
		// grant award to player based on their actions on this match
		this.grantAwards();
	}
	
	/**
	 * Search for the match winner preferred weapon
	 * @return (Weapon): Weapon object with date, like name and weapon usage.
	 */
	public Weapon getWinnerPreferredWeapon(){
		Player winner = this.matchRanking.get(0);
		
		List<Weapon> weaponUsage = new ArrayList<Weapon>();
		
		// create a array with the winner weapon usage
		for(Kill k : this.listOfKills){
			// only counts winner kills
			if(winner.equals(k.getPlayer())){
				Weapon w = this.auxGetWeaponByName(weaponUsage, k.getWeaponName());
				if(w==null){
					w = new Weapon();
					w.setWeaponName(k.getWeaponName());
					weaponUsage.add(w);
				}
				w.increaseWeaponUsage();
			}
		}
		
		// order by the number ok kills desc
		Collections.sort(weaponUsage, new Comparator<Weapon>() {

			@Override
			public int compare(Weapon o1, Weapon o2) {
				if(o1.getUsage() < o2.getUsage()){
					return 1;
				}
				if(o1.getUsage() > o2.getUsage()){
					return -1;
				}
				return 0;
			}
			
		});
		
		return weaponUsage.get(0);

	}
	
	private Weapon auxGetWeaponByName(List<Weapon> l, String weaponName){
		for(Weapon w : l){
			if(w.getWeaponName().equalsIgnoreCase(weaponName)){
				return w;
			}
		}
		return null;
	}
	
	/**
	 * Method to grant players awards
	 */
	private void grantAwards(){
		
		for(Player p : this.matchRanking){
			
			// FLAWLESS_VICTORY award - grants a award to a player with no deaths in a match
			if(p.getNumberOfDeaths()==0){
				p.getAwards().add(Award.FLAWLESS_VICTORY);
			}
			
			// PENTAKILL award - grants a award to a player that achieve 5 kills in 1 minute
			int killCount = 0;
			Date firstKill = null;
			List<Kill> playerKills = this.auxGetPlayerKills(p);
			for(Kill k : playerKills){
				if(firstKill==null){
					firstKill = k.getKillDate();
				}
				
				if((k.getKillDate().getTime()-firstKill.getTime()) > 60000){
					firstKill = null;
					killCount = 0;
				}else{
					killCount++;
				}
			}
			if(killCount>=5){
				p.getAwards().add(Award.PENTAKILL);
			}
			
			/*
			 * If you need to add support for more awards, do it here, and comment the award its been granted and the condition for granting it
			 */
		}
		
	}
	
	private List<Kill> auxGetPlayerKills(Player p){
		List<Kill> kills = new ArrayList<>();
		for(Kill k : this.listOfKills){
			if(k.getPlayer().equals(p)){
				kills.add(k);
			}
		}
		return kills;
	}
}
