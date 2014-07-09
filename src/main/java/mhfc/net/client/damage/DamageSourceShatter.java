package mhfc.net.client.damage;

import net.minecraft.util.DamageSource;

public class DamageSourceShatter extends DamageSource {
    
    public DamageSourceShatter(String par1Str) {
        super(par1Str);
    }
    
    private static DamageSourceShatter instance=null;
    
    public static DamageSourceShatter instance(){
        if(instance==null){
            instance=(DamageSourceShatter) new DamageSourceShatter("shatter").setDamageBypassesArmor();
        }
        return instance;
    }
    
}
