package main.java.game.creatures;
import main.java.game.realms.RealmColor;
public class Dragon extends Creature{
    private final DragonNumber dragonNumber;
    private int face;
    private int wings;
    private int tail;
    private int heart;
    private boolean isDead;
//=======================================Constructor===================================
    public Dragon(DragonNumber number,int face,int wings,int tail,int heart){
            super(RealmColor.RED);
            this.dragonNumber=number;
            this.face=face;
            this.wings=wings;
            this.tail=tail;
            this.heart=heart;
        }
//=======================================Methods=======================================
    @Override
    public boolean checkPossibleAttack(int diceValue) {//TODO: check Dice>0 <=6 and Throw Error
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

    public void killDragon(){
        isDead=true;
    }
//=======================================Get&Set=======================================
    public DragonNumber getDragonNumber(){
        return dragonNumber;
    }
    public boolean isDeadDragon(){
        return isDead;
    }
    public int getFace(){
        return face;
    }
    public int getWings(){
        return wings;
    }
    public int getTail(){
        return tail;
    }
    public int getHeart(){
        return heart;
    }
    public boolean isFaceKilled(){
        return face==0;
    }   
    public boolean isWingsKilled(){
        return wings==0;
    }
    public boolean isTailKilled(){
        return tail==0;
    }
    public boolean isHeartKilled(){
        return heart==0;
    }
//=======================================Display=======================================
@Override public String toString(){
    return "Dragon";
}
}
