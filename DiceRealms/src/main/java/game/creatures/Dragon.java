package main.java.game.creatures;
import main.java.game.realms.RealmColor;
public class Dragon extends Creature{
    private final DragonNumber dragonNumber;
    private int face;
    private int wings;
    private int tail;
    private int heart;
    private boolean isDead;
//============================Constructor============================================
    public Dragon(DragonNumber number,int face,int wings,int tail,int heart){
        super(RealmColor.RED);
        this.dragonNumber=number;
        this.face=face;
        this.wings=wings;
        this.tail=tail;
        this.heart=heart;
    }
//============================Methods================================================
    public void killDragon(){
        isDead=true;
    }
    @Override
    public boolean checkPossibleAttack(int diceValue) {
        if(diceValue>0&&!isDead){
            return diceValue==face||diceValue==wings||diceValue==tail||diceValue==heart;
        }
        return false;
    }
    public void attackPart(int diceValue){
        if(diceValue==face)
            face=0;
        else if(diceValue==wings)
            wings=0;
        else if(diceValue==tail)
            tail=0;
        else if(diceValue==heart)
            heart=0;

        if(face==0&&wings==0&&tail==0&&heart==0)  
            killDragon();   
    }
//============================G&S====================================================   
   public DragonNumber getDragonNumber(){
        return dragonNumber;
   }
   public boolean isDeadDragon(){
    return isDead;
   }

}
