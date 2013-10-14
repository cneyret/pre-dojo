package rankingCs.br.td;

public class Weapon {
	
	private String weaponName;
	private int usage=0;;
	
	public String getWeaponName() {
		return weaponName;
	}
	public void setWeaponName(String weaponName) {
		this.weaponName = weaponName;
	}
	public int getUsage() {
		return usage;
	}
	public void setUsage(int usage) {
		this.usage = usage;
	}
	
	// aux methods
	public void increaseWeaponUsage(){
		this.usage++;
	}
	
}
