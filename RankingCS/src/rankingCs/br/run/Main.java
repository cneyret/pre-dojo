package rankingCs.br.run;

import java.util.List;

import rankingCs.br.td.Match;
import rankingCs.br.td.Player;
import rankingCs.br.textReader.Reader;

public class Main {
	
	public static void main(String[] args) {
		Reader r = new Reader();
		List<Match> partidas = r.logReader();
		if(partidas!=null){
			for(Match m : partidas){
				System.out.println("********************************** Match Ranking "+m.getMatchId()+" **********************************");
				System.out.println("Start: "+m.getMatchStart());
				System.out.println("End: "+m.getMatchEnd());
				System.out.println("Winner: "+m.getMatchRanking().get(0).getPlayerName());
				System.out.println("Winner weapon of choice: "+m.getWinnerPreferredWeapon().getWeaponName());
				for(Player p : m.getMatchRanking()){
					System.out.println("******************************");
					System.out.println("Player: "+p.getPlayerName());
					System.out.println("Kills/Deaths: "+p.getNumberOfKills()+" / "+p.getNumberOfDeaths());
					System.out.println("Awards: "+p.getAwards());
					System.out.println("******************************");
				}
				System.out.println("******************************************************************************************************");
			}
		}
	}
}
