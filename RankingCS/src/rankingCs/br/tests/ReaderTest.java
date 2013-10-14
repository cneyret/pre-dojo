package rankingCs.br.tests;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import rankingCs.br.td.Award;
import rankingCs.br.td.Match;
import rankingCs.br.td.Player;
import rankingCs.br.td.Weapon;
import rankingCs.br.textReader.LogParseException;
import rankingCs.br.textReader.Reader;

public class ReaderTest {
	
	@Test
	public void testReadingOK(){
		
		Reader r = new Reader();
		
		List<Match> matches = r.logReader();
		
		// verify if its only one match on the current .txt log
		Assert.assertTrue(matches.size()==1);
		
		for(Match m : matches){
			// verify kills/deaths and awards of each player on the match
			Player roman = m.getPlayerFromRanking("Roman");
			Assert.assertTrue(roman.getPlayerName().equals("Roman") && roman.getNumberOfKills()==5 && roman.getNumberOfDeaths()==0);
			Assert.assertTrue(roman.getAwards().size()==2 
					&& roman.getAwards().get(0).equals(Award.FLAWLESS_VICTORY)
					&& roman.getAwards().get(1).equals(Award.PENTAKILL));
			
			Player nick = m.getPlayerFromRanking("Nick");
			Assert.assertTrue(nick.getPlayerName().equals("Nick") && nick.getNumberOfKills()==0 && nick.getNumberOfDeaths()==5);
			Assert.assertTrue(nick.getAwards().size()==0);
			
			// verify wich player is the winner of the match
			Player winner = m.getMatchRanking().get(0);
			Assert.assertTrue(winner.getPlayerName().equalsIgnoreCase("Roman"));
			
			// verify the winner preferred weapon (weapon wich he killed most players)
			Weapon w = m.getWinnerPreferredWeapon();
			Assert.assertTrue(w.getWeaponName().equals("AK47"));
		}
		
	}
	
	@Test(expected=LogParseException.class)
	public void testErrorPlayerActionOutsideMatch(){
		Reader r = new Reader("logs\\log_erro_1.txt");
		r.logReader();
	}
	
	@Test(expected=LogParseException.class)
	public void testErrorMatchEndWihoutStart(){
		Reader r = new Reader("logs\\log_erro_2.txt");
		r.logReader();
	}

}
