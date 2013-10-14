package rankingCs.br.textReader;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import rankingCs.br.td.Kill;
import rankingCs.br.td.Match;
import rankingCs.br.td.Player;

public class Reader {
	
	/**
	 * Initialize Reader class with given logPath
	 * @param logPath (String): logPath where the reader will lookpup for the gae log .txt file.
	 */
	public Reader(String logPath){
		this.logPath = logPath;
	}
	
	/**
	 * Initialize Reader class with default logPath.
	 */
	public Reader(){
		this.logPath = "logs\\log.txt";
	}
	
	private String logPath;
	
	/**
	 * Method for reading a game log .txt file and return the list of match results
	 * @return - List of Match results.
	 */
	public List<Match> logReader(){
		List<Match> matches = new ArrayList<Match>();
		
		BufferedReader readerLog = null;
		
		try {
			FileReader log = new FileReader(logPath);
			readerLog = new BufferedReader(log);
			String line = readerLog.readLine();
			
			Match match = null;
			while(line!=null){
				
				// splitting each log line by " - ", because we get the date in one side, and the action ocurred on the other side
				String[] logSplit = line.split(" - ");
				
				Date logDate = parseDate(logSplit[0]);
				
				String action = logSplit[1];
				
				// verify if its a match start
				if(action.startsWith("New match")){
					String matchId = action.replaceAll("[^0-9]+","");
					// create a new match and store into the matches list
					match = new Match();
					matches.add(match);
					match.setMatchId(matchId);
					match.setMatchStart(logDate);
				}
				// or if its a match end
				else if(action.endsWith(" has ended")){
					if(match!=null){
						match.closeMatch(logDate);
						match = null;
					}
					// error: match end without a start!
					else{
						readerLog.close();
						throw new LogParseException("Erro: Fim de partida sem o início!");
					}
				}
				// or player/world kill
				else{
					if(match!=null){
						String[] actionSplit = action.split("(\\s[k][i][l][l][e][d]\\s)|(\\s[u][s][i][n][g]\\s)|(\\s[b][y]\\s)");
						String killerName = actionSplit[0];
						String killedName = actionSplit[1];
						String weaponName = actionSplit[2];
						
						// ignore if its a <WORLD> kill
						if(!killerName.equalsIgnoreCase("<WORLD>")){
							Player killer = match.getPlayerFromRanking(killerName);
							if(killer==null){
								killer = new Player();
								killer.setPlayerName(killerName);
								match.getMatchRanking().add(killer);
							}
							killer.increaseKill();
							
							Player killed = match.getPlayerFromRanking(killedName);
							if(killed==null){
								killed = new Player();
								killed.setPlayerName(killedName);
								match.getMatchRanking().add(killed);
							}
							killed.increaseDeath();
							
							// create a kill inside the match
							Kill k = new Kill();
							k.setKillDate(logDate);
							k.setPlayer(killer);
							k.setPlayerKilled(killed);
							k.setWeaponName(weaponName);
							match.getListOfKills().add(k);
						}
					}
					// error: player action outside a match!
					else{
						readerLog.close();
						throw new LogParseException("Erro: Ação de jogador fora do contexto de uma partida!");
					}
				}
				line = readerLog.readLine();
			}
			
			readerLog.close();
		} catch (FileNotFoundException e) {
			throw new LogParseException("Erro: Arquivo de log não encontrado!");
		} catch (IOException e) {
			throw new LogParseException("Erro: Não foi possível ler o arquivo!");
		}
		
		return matches;
	}
	
	/**
	 * Simple Date utilitary method to read the date input from game log file.
	 * @param dateStr
	 * @return (Date): Date object from file date string.
	 */
	private Date parseDate(String dateStr){
		SimpleDateFormat formater = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		try {
			return formater.parse(dateStr);
		} catch (ParseException e) {
			throw new LogParseException("Erro: Formato de arquivo inválido!");
		}
	}

}
