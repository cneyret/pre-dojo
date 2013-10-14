package rankingCs.br.td;

import java.util.ArrayList;
import java.util.List;

public class Player {
	
	private String playerName;
	private int numberOfKills = 0;
	private int numberOfDeaths = 0;
	private List<Award> awards = new ArrayList<Award>();
	
	public String getPlayerName() {
		return playerName;
	}
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}
	public int getNumberOfKills() {
		return numberOfKills;
	}
	public void setNumberOfKills(int numberOfKills) {
		this.numberOfKills = numberOfKills;
	}
	public int getNumberOfDeaths() {
		return numberOfDeaths;
	}
	public void setNumberOfDeaths(int numberOfDeaths) {
		this.numberOfDeaths = numberOfDeaths;
	}
	public List<Award> getAwards() {
		return awards;
	}
	public void setAwards(List<Award> awards) {
		this.awards = awards;
	}
	
	// aux methods
	public void increaseKill(){
		this.numberOfKills++;
	}
	public void increaseDeath(){
		this.numberOfDeaths++;
	}
}
