package rankingCs.br.td;

import java.util.Date;

public class Kill {
	
	private Date killDate;
	private Player player;
	private Player playerKilled;
	private String weaponName;
	
	public Date getKillDate() {
		return killDate;
	}
	public void setKillDate(Date killDate) {
		this.killDate = killDate;
	}
	public Player getPlayer() {
		return player;
	}
	public void setPlayer(Player player) {
		this.player = player;
	}
	public Player getPlayerKilled() {
		return playerKilled;
	}
	public void setPlayerKilled(Player playerKilled) {
		this.playerKilled = playerKilled;
	}
	public String getWeaponName() {
		return weaponName;
	}
	public void setWeaponName(String weaponName) {
		this.weaponName = weaponName;
	}
}
