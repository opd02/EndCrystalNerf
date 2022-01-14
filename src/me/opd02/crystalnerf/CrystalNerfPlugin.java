package me.opd02.crystalnerf;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.plugin.java.JavaPlugin;

public class CrystalNerfPlugin extends JavaPlugin implements Listener {
	
	double nerfPercentage;

	public void onEnable(){
		this.getServer().getPluginManager().registerEvents(this, this);
		this.getConfig().options().copyDefaults(true);
		this.saveConfig();
		
		if(this.nerfPercentage < 0){
			
			this.nerfPercentage = 0;
			
			Bukkit.getServer().broadcastMessage(ChatColor.RED + "[CrystalNerf] config.yml has and invalid CrystalNerfPercentage, value set to 0.");
		
		}else{
		
			this.nerfPercentage = this.getConfig().getDouble("CrystalNerfPercentage");
			
		}
		
		Bukkit.getServer().broadcastMessage(ChatColor.GREEN + "[CrystalNerf] Nerf percentage has been set to " + this.nerfPercentage + "%.");
	}
	
	public void onDisable(){
		
	}
	
	@EventHandler 
	public void onPlayerDamage(EntityDamageByEntityEvent e){
		
		Entity ent = e.getEntity();
		Entity dam = e.getDamager();
		
		if(!ent.getType().equals(EntityType.PLAYER) || !dam.getType().equals(EntityType.ENDER_CRYSTAL)){
			return;
		}
		
		e.setDamage(e.getDamage() - (e.getDamage() * (this.nerfPercentage / 100)));
		
	}
	
	@EventHandler
	public void onPlayerDamaeAnchor(EntityDamageEvent e){
		if(e.getCause().equals(DamageCause.BLOCK_EXPLOSION)){
			if(this.getConfig().getBoolean("IncludeRespawnAnchorAndBed") == true){
				e.setDamage(e.getDamage() - (e.getDamage() * (this.nerfPercentage / 100)));
			}
		}
	}
}
