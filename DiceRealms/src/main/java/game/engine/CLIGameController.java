package main.java.game.engine;
import main.java.game.dice.*;

import java.util.*;
import main.java.game.collectibles.*;
import main.java.game.realms.*;
import main.java.game.creatures.*;
public class CLIGameController extends GameController{
    private GameBoard gameBoard;
    private Scanner sc;
//=======================================Constructor===================================    
    public CLIGameController(){
        gameBoard=new GameBoard();
        sc=new Scanner(System.in);
    }
//=======================================GameFlow======================================
public  void startGame(){

    while(!getGameStatus().isGameFinished()){
        
       
        
    }
    if(getGameScore(getActivePlayer()).getTotalGameScore()>getGameScore(getPassivePlayer()).getTotalGameScore())
        System.out.println(getActivePlayer().getPlayerType()+" Wins!!!");
    else
        System.out.println(getPassivePlayer().getPlayerType()+" Wins!!!");     
}
//===================================================
    public void startGame(){//TODO: Colors for Dice and only allow selection of dice that can be used
        //Delay to Show Game Name
        createDelay();
        createDelay();
        //Enter Player Name
        selectPlayerName(getActivePlayer(), 1);
        selectPlayerName(getActivePlayer(), 2);
        while(!getGameStatus().isGameFinished()){
            //Displaying ActivePlayer Name and Round Number,Grimore
            Player activePlayer=getActivePlayer();
            System.out.println("Round: "+getGameStatus().getGameRound());
            System.out.println(activePlayer.getPlayerName()+"'s Turn!!!");
            createDelay();
            displayPlayerGrimore(activePlayer);
            //Turn Loop
            while(getGameStatus().getTurn()<=3){
                System.out.println("Turn: "+getGameStatus().getTurn());
                createDelay();
                //Rolling Dice Ending if no more dice Avaiable
                System.out.println("Rolling dice......");
                rollDice();
                createDelay();
                if(getAvailableDice().length==0){
                    System.out.println("No available  dice found ending turn");
                    createDelay();
                    break;
                }
                //Display Dice and promting for TimeWarp
                displayAvailableDice();
                displayTimeWarpPrompt(activePlayer);
                //Selecting dice
                Dice selectedDice=selectDiceSequence(activePlayer);
                //Send Remaning Dice to forgotten Realm
                selectDice(selectedDice, activePlayer);
                System.out.println("Sending remaining Dice to forgoten realm.....");
                createDelay();
                displayForgottenRealmDice();
                //attacking
                attackSequence(activePlayer,selectedDice);
                //ArcaneBoost
                displayArcaneBoostPrompt(activePlayer);
                //Display Score Sheet after each turn
                System.out.println(getScoreSheet(activePlayer));
                createDelay();
                getGameStatus().incrementTurn();
            }
            switchPlayer();
            System.out.println("Passive player choose dice from forgotten realm later..");
            resetDiceStatus();     
        }


    }
    public void createDelay(){
        try{
            Thread.sleep(1500);
        }
        catch(Exception ex){
            System.out.println("Error in Thread Sleep");
            ex.printStackTrace();}
    }
    public void selectPlayerName(Player player,int playerNumber){
        String playerName="";
        while(playerName.isEmpty()){//While loop till user enters valid name
        System.out.println("Player"+playerNumber+" Enter Your Name:");    
        System.out.print("Name:");
        playerName=sc.next().trim();
        if(playerName.isEmpty())
            System.out.println("Player name cannot be blank ");
        if(playerName.toUpperCase().charAt('0')<'A'||playerName.toUpperCase().charAt('0')>'Z'){
            System.out.println("Player name cannot start with a special character!");
            playerName="";
        }
        }
        player.setPlayerName(playerName);
    }
    public void displayPlayerGrimore(Player player){
        System.out.println("Printing "+player.getPlayerName()+"'s Grimore");
        createDelay();
        System.out.println(getScoreSheet(player));
        System.out.println("Press Enter to continue:");
        sc.next();
    }
    public void displayAvailableDice(){
        Dice[]availableDice=getAvailableDice();
        for(int i=1;i<=availableDice.length;i++)
            System.out.print("("+i+") "+availableDice[i-1]+"  ");
        System.out.println();
    }
    public void displayTimeWarpPrompt(Player player){
        //Not Prompting if no TimeWarps Available
        if(player.getTimeWarps().size()==0)
            return;
        while(player.getTimeWarps().size()>0){  
        System.out.println("Would you like to use a TimeWarp?"); 
        System.out.println("You have x"+player.getTimeWarps().size()+" TimeWarps remaining");
        System.out.println("Enter YES or NO");
        System.out.print("Choice: ");
        String choice=""+sc.next().trim().toUpperCase().charAt(0);
        if(choice.equals("Y"))
            useTimeWarp(player);
        else if(choice.equals("N"))
            return;
        else
             System.out.println("Invalid input please enter YES or No!!");        
        }
        //If player used all TimeWarps
        if(player.getTimeWarps().size()==0)
            System.out.println("No more TimeWarps Avaialble");
    }
    public void useTimeWarp(Player player){
        player.removeTimeWarp();
        System.out.println("TimeWarp power activated!");
        System.out.println("Rerolling dice......");
        rollDice();
        createDelay();
        displayAvailableDice();
    }
    public Dice selectDiceSequence(Player player){
         //First select dice
         int selectedDiceIndex=displaySelectDicePromt(player)-1;//-1 Because Prompt starts from 1
         Dice selectedDice=getAvailableDice()[selectedDiceIndex];
         //Prompt Player to choose Arcane Dice color if chosen
         RealmColor selectedDiceColor=selectedDice.getDiceColor();    
         if(selectedDiceColor==RealmColor.WHITE){
             displayChooseArcaneDiceColorPrompt();
             selectedDiceColor=((ArcanePrism)selectedDice).getChosenColor(); 
         }
         return selectedDice;
    }
    public void attackSequence(Player player,Dice selectedDice){
        //Select Creature
        Creature selectedCreature;
        if(selectedDice.getDiceStatus()==DiceStatus.POWER_SELECTED
        &&selectedDice.getDiceColor()==RealmColor.RED
        &&((RedDice)selectedDice).getselectsDragon()!=-1){//-1 To prevent ArcaneBoost from entering
            RedRealm redRealm=player.getRedRealm();
            RedDice bonusDice=(RedDice)selectedDice;
            int dragonNumber=bonusDice.getselectsDragon();
            System.out.println("Attacking Dragon"+dragonNumber+".....");
            createDelay();
            switch(dragonNumber){
                case 1:selectedCreature= redRealm.getDragon1();
                case 2:selectedCreature= redRealm.getDragon2();
                case 3:selectedCreature= redRealm.getDragon3();
                case 4:selectedCreature= redRealm.getDragon4();
                default:System.out.println("An error occured in dragon selection");
            }
        }
        else
        selectedCreature=selectCreatureToAttack(player, selectedDice.getDiceColor());
        //Make a move
        boolean isMoveSuccessful=makeMove(player,new Move(selectedDice, selectedCreature));
        if(isMoveSuccessful)
            System.out.println("Attack was successfull!!");
        else
            System.out.println("Attack failed!!");
        //Check and get Possible Rewards
        checkForPossibleRewards(player,selectedDice.getDiceColor());    
    }
    public int displaySelectDicePromt(Player player){
        Dice[]availableDice=getAvailableDice();
        while(true){
            System.out.println("Choose a number between 1 and "+(availableDice.length)+" to select a dice");
            System.out.print("Choice: ");
            try{
                int choice=Integer.parseInt(sc.next().trim());
                if(1<=choice&&choice<=availableDice.length)
                    return choice;
                else
                    System.out.println("Please choose a number between 1 and "+(availableDice.length)+"!!");    

            }catch(Exception ex){
                System.out.println("Please enter a valid number");
            }
        }
    }
    public void displayForgottenRealmDice(){//TODO make gray
        Dice[]forgottenDice=getForgottenRealmDice();
        if(forgottenDice.length==0){
            System.out.println("No dice in Forgotten Realm");
            return;
        }
        for(Dice x:forgottenDice)
            System.out.println(x+"  ");
    }
    public void displayChooseArcaneDiceColorPrompt(){//TODO: cant selected if realm closed
        ArcanePrism whiteDice=gameBoard.getArcanePrism();
        System.out.println("Choose ArcanePrism color: ");
        System.out.println("Red Green Blue Magenta Yellow");
        while(true){
            System.out.print("Choice: ");
            String choice=sc.next().trim().toUpperCase();
            if(!choice.isEmpty()){
                switch (choice.charAt(0)) {
                    case 'R':whiteDice.setChosenColor(RealmColor.RED);return;
                    case 'G':whiteDice.setChosenColor(RealmColor.GREEN);return;
                    case 'B':whiteDice.setChosenColor(RealmColor.BLUE);return;
                    case 'M':whiteDice.setChosenColor(RealmColor.MAGENTA);return;
                    case 'Y':whiteDice.setChosenColor(RealmColor.YELLOW);return;
                    default:System.out.println("Please Enter A valid Color");
                }
            }
            else System.out.println("Choice cannot be blank");   
        }
    }
    public Creature selectCreatureToAttack(Player player,RealmColor color){
        switch (color) {
            case RED:displaySelectDragonPrompt(player);
            case GREEN:
            System.out.println("Attacking Gaia Gurdian....");
            createDelay();
            return player.getGreenRealm().getGaia();
            case BLUE:
            System.out.println("Attacking Hydra....");
            createDelay();
            return player.getBlueRealm().getHydra();
            case MAGENTA:
            System.out.println("Attacking Phoenix....");
            createDelay();
            return player.getMagentaRealm().getPhoenix();
            case YELLOW:
            System.out.println("Attacking Lion....");
            createDelay();
            return player.getYellowRealm().getLion();
            case WHITE:System.out.println("Error occured White Selection wasnt successful");    
        }
    }
    public Creature displaySelectDragonPrompt(Player player){//TODO: make sure possible attack and not dead
        int choice=0;
        System.out.println("Please choose which dragon to attack:");
        for(int i=1;i<=4;i++)
            System.out.print("("+i+") Dragon"+i+" ");
        System.out.println();
        while(true){
            try{
            System.out.print("Choice: ");
            choice=Integer.parseInt(sc.next().trim());
            if(choice>=1&&choice<=4)
                break;
            System.out.println("Please choose a number between 1 and 4");    
            }catch(Exception ex){
                System.out.println("Please enter a valid number");
            }
        }
        RedRealm redRealm=player.getRedRealm();
        gameBoard.getRedDice().selectsDragon(choice);
        System.out.println("Attacking Dragon"+choice+".....");
        createDelay();
        switch(choice){
            case 1:return redRealm.getDragon1();
            case 2:return redRealm.getDragon2();
            case 3:return redRealm.getDragon3();
            case 4:return redRealm.getDragon4();
            default:System.out.println("An error occured in dragon selection");return null;
        }
    }
    public void checkForPossibleRewards(Player player,RealmColor diceColor){
        //Checking which realm was attacked to receive rewards from
        Realms checkInRealm=null;
        switch(diceColor){
            case RED:checkInRealm=player.getRedRealm();break;
            case BLUE:checkInRealm=player.getBlueRealm();break;
            case GREEN:checkInRealm=player.getGreenRealm();break;
            case YELLOW:checkInRealm=player.getYellowRealm();break;
            case MAGENTA:checkInRealm=player.getMagentaRealm();break;
            case WHITE:System.out.println("Error checking reward in white realm");
        }
        if(!checkInRealm.isRewardAvailable())
            return;
        //TODO: fix implementation for arrays   
        Reward earnedReward=checkInRealm.getReward();
        switch(earnedReward.getRewardType()){
            case CREST:
            System.out.println("You earned an ElementalCrest!!");
            player.addElementalCrest((ElementalCrest)earnedReward);
            break;
            case POWER:
            if(earnedReward instanceof TimeWarp){
                System.out.println("You earned a TimeWarp power");
                player.addTimeWarp((TimeWarp)earnedReward);
            }
            else if(earnedReward instanceof ArcaneBoost){
                System.out.println("You earned an ArcaneBoost power");  
                player.addArcaneBoost((ArcaneBoost)earnedReward);
            }
            else
                System.out.println("An error has occured in Power Rewards?");
            
            break;
            case BONUS:
            if(earnedReward instanceof EssenceBonus)
                displaySelectEssenceBonusColorPromt(player,(EssenceBonus)earnedReward);
            else if(earnedReward instanceof Bonus)
                useColorBonusPrompt(player,(Bonus)earnedReward);    
            else
                System.out.println("An error has occured in Bonus Rewards?");
        }  
    }
    public void displaySelectEssenceBonusColorPromt(Player player,EssenceBonus bonus){
        System.out.println("You earned an EssenceBonus Choose which realm to attack ");//TODO: only attack available realms
         while(true){
            System.out.print("Choice: ");
            String choice=sc.next().trim().toUpperCase();
            if(!choice.isEmpty()){
                switch (choice.charAt(0)) {
                    case 'R':bonus.setEssenceBonusColor(RealmColor.RED);break;
                    case 'G':bonus.setEssenceBonusColor(RealmColor.GREEN);break;
                    case 'B':bonus.setEssenceBonusColor(RealmColor.BLUE);break;
                    case 'M':bonus.setEssenceBonusColor(RealmColor.MAGENTA);break;
                    case 'Y':bonus.setEssenceBonusColor(RealmColor.YELLOW);break;
                    default:System.out.println("Please Enter A valid Color");
                }
                useColorBonusPrompt(player, bonus);
            }
            else System.out.println("Choice cannot be blank");   
        }
    }
    public void useColorBonusPrompt(Player player,Bonus bonus){//TODO: CAN I USE COLOR BONUS IF DICE NOT AVAIABLE
        if(!(bonus instanceof EssenceBonus))
            System.out.println("You earned a "+bonus.getBonusColor()+" Color Bonus!!");
        Dice diceToBeUsed;
        switch(bonus.getBonusColor()){
            case RED:diceToBeUsed=selectRedColorBonusDragon(player);break;
            case GREEN:diceToBeUsed=selectGreenColorBonusGaia(player);break;
            case BLUE:
            BlueDice blueDice=new BlueDice(6);
            blueDice.setDiceStatus(DiceStatus.POWER_SELECTED);
            diceToBeUsed=blueDice;break;
            case MAGENTA:
            MagentaDice magentaDice=new MagentaDice(6);
            magentaDice.setDiceStatus(DiceStatus.POWER_SELECTED);
            diceToBeUsed=magentaDice;break;
            case YELLOW:
            YellowDice yellowDice=new YellowDice(6);
            yellowDice.setDiceStatus(DiceStatus.POWER_SELECTED);
            diceToBeUsed=yellowDice;break;
            case WHITE:diceToBeUsed=null;System.out.println("An error has occured in Essence color selection");
        }
        attackSequence(player, diceToBeUsed);
    }
    public Dice selectRedColorBonusDragon(Player player){//TODO: only attack avaiable dragon and dragon parts
        //Choosing which dragon
        int choice=0;
        System.out.println("Please choose which dragon to attack:");
        for(int i=1;i<=4;i++)
            System.out.print("("+i+") Dragon"+i+" ");
        System.out.println();
        while(true){
            try{
            System.out.print("Choice: ");
            choice=Integer.parseInt(sc.next().trim());
            if(choice>=1&&choice<=4)
                break;
            System.out.println("Please choose a number between 1 and 4");    
            }catch(Exception ex){
                System.out.println("Please enter a valid number");
            }
        }
        RedRealm redRealm=player.getRedRealm();
        Dragon dragon;
        switch(choice){
            case 1:dragon=redRealm.getDragon1();break;
            case 2:dragon=redRealm.getDragon2();break;
            case 3:dragon=redRealm.getDragon3();break;
            case 4:dragon=redRealm.getDragon4();break;
            default:System.out.println("An error occured in dragon selection");
        }
        //choosing which part
        System.out.println("Please Choose which part to attack");
        if(!dragon.isFaceKilled())
            System.out.print("Face  ");
        if(!dragon.isWingsKilled())
            System.out.println("Wings  ");
        if(!dragon.isTailKilled())
            System.out.println("Tail  ");
        if(!dragon.isHeartKilled())
            System.out.println("Heart  ");
        System.out.println();    
        int diceValue=-1;
        while(true){
            String selection=sc.next().toUpperCase().trim();
            if(!selection.isEmpty()){
                switch (selection.charAt(0)) {
                   case 'F':
                        if(!dragon.isFaceKilled())
                            diceValue=dragon.getFace();
                        else
                            System.out.println("Not a valid Selection");break;  
                   case 'W':
                        if(!dragon.isWingsKilled())
                            diceValue=dragon.getWings();
                        else
                            System.out.println("Not a valid Selection");break;
                   case 'T':
                         if(!dragon.isTailKilled())
                            diceValue=dragon.getTail();
                         else
                            System.out.println("Not a valid Selection");break;
                   case 'H':
                        if(!dragon.isHeartKilled())
                            diceValue=dragon.getHeart();
                        else
                            System.out.println("Not a valid Selection");break;
                }
                if(diceValue!=-1)
                break;
            }else System.out.println("Choice cant be blank");
        }     
        RedDice bonusDice=new RedDice(diceValue);    
        bonusDice.setDiceStatus(DiceStatus.POWER_SELECTED);
        bonusDice.selectsDragon(choice);
        return bonusDice; 
    }
    public Dice selectGreenColorBonusGaia(Player player){
        System.out.println("Please Choose Which Gaia Gurdian To Attack");
        Gaia gaia=player.getGreenRealm().getGaia();
        boolean[] gaiaHealth=gaia.getGurdiansHealth();
        for(int i=2;i<gaiaHealth.length;i++)
            if(gaiaHealth[i])
                System.out.print(i+" ");
        System.out.println(); 
        int choiceNum;    
        while(true){
            System.out.print("Choice: ");
            String choice=sc.next().trim();
            try{
                choiceNum=Integer.parseInt(choice);
                if(choiceNum>=2&&choiceNum<=12)
                    break;
                else
                    System.out.println("Please choose from the previous numbers");     
            }catch(Exception ex){System.out.println("Please Enter a  number");}
        }    
        GreenDice bonusDice=new GreenDice(choiceNum);
        bonusDice.setDiceStatus(DiceStatus.POWER_SELECTED);
        return bonusDice;
    }
    public void displayArcaneBoostPrompt(Player player){
        //No available dice or arcaneboosts
        if(player.getArcaneBoosts().size()==0
        ||getAvailableDice().length==0)
            return;

        while(player.getArcaneBoosts().size()>0&&getAvailableDice().length>0){  
        System.out.println("Would you like to use an ArcaneBoost?"); 
        System.out.println("You have x"+player.getArcaneBoosts().size()+" ArcaneBoosts remaining");
        System.out.println("Enter YES or NO");
        System.out.print("Choice: ");
        String choice=""+sc.next().trim().toUpperCase().charAt(0);
        if(choice.equals("Y"))
            useArcaneBoost(player);
        else if(choice.equals("N"))
            return;
        else
             System.out.println("Invalid input please enter YES or No!!");        
        }
        //If player used all ArcaneBoost or All Dice
        if(player.getArcaneBoosts().size()==0){
            System.out.println("No more ArcaneBoosts Avaialble"); 
            createDelay();
        }
        else if(getAvailableDice().length==0){
            System.out.println("No more Dice Available"); 
            createDelay();
        }

    }
    public void useArcaneBoost(Player player){
        Dice boostDice=selectDiceSequence(player);
        boostDice.setDiceStatus(DiceStatus.POWER_SELECTED);
        if(boostDice.getDiceColor()==RealmColor.RED)
            ((RedDice)boostDice).selectsDragon(-1);
        attackSequence(player, boostDice);    
    }
    public void passivePlayerSequence(Player player){
        System.out.println("Passive Player Choose from forgotten Realm");



    }
//============================================================================================================    
    public void resetDiceStatus(){
       Dice[] allDice= getAllDice();
       for(Dice x:allDice)
            x.setDiceStatus(DiceStatus.AVAILABLE);
    }
fqwfqwfqwfqw
    ffffa
   fqwfqwfqwfqw 
   qwfqwfqwfqwfqw
fqwfqwfqwfqwfqwfqw
//=======================================Methods=======================================
    public  boolean switchPlayer(){
        Player activePlayer=getActivePlayer();
        Player passivePlayer=getPassivePlayer();
        passivePlayer.setPlayerStatus(PlayerStatus.ACTIVE);
        activePlayer.setPlayerStatus(PlayerStatus.PASSIVE);
        System.out.println("Switching");
        return true;
    }

    public  Dice[] rollDice(){
        Dice[] rolledDice=getAvailableDice();
        for(Dice currentDice:rolledDice)
            currentDice.rollDice();
        return rolledDice;
    }

    public  Dice[] getAvailableDice(){
        Dice[] allDice=getAllDice();
        int numberOfAvailableDice=0;
        for(Dice x:allDice)
            if(x.getDiceStatus()==DiceStatus.AVAILABLE)
                numberOfAvailableDice++;
       if(numberOfAvailableDice==0)
            return new Dice[0];
       Dice[] availableDice=new Dice[numberOfAvailableDice];

        for(int i=0,j=0;i<numberOfAvailableDice;j++)
            if(allDice[j].getDiceStatus()==DiceStatus.AVAILABLE)
                availableDice[i++]=allDice[j];

        return availableDice;        
    }
    
    public  Dice[] getAllDice(){
        return gameBoard.getAllDice();
    }

    public  Dice[] getForgottenRealmDice(){
        Dice[] allDice=getAllDice();
        int numberOfForgottenDice=0;
        for(Dice x:allDice)
            if(x.getDiceStatus()==DiceStatus.FORGOTTEN_REALM)
                numberOfForgottenDice++;

       Dice[] forgottenDice=new Dice[numberOfForgottenDice];

        for(int i=0,j=0;i<numberOfForgottenDice;j++)
            if(allDice[j].getDiceStatus()==DiceStatus.FORGOTTEN_REALM)
                forgottenDice[i++]=allDice[j];
                
        return forgottenDice;     
    }

    public  Move[] getAllPossibleMoves(Player player){
        BlueRealm playerBlueRealm=player.getBlueRealm();
        RedRealm  playerRedRealm=player.getRedRealm();
        GreenRealm playerGreenRealm=player.getGreenRealm();
        YellowRealm playerYellowRealm=player.getYellowRealm();
        MagentaRealm playerMagentaRealm=player.getMagentaRealm();

        Move[] blueRealmMoves=playerBlueRealm.getAllPossibleMoves();
        Move[] redRealmMoves=playerRedRealm.getAllPossibleMoves();
        Move[] greenRealmMoves=playerGreenRealm.getAllPossibleMoves();
        Move[] yellowRealmMoves=playerYellowRealm.getAllPossibleMoves();
        Move[] magentaRealmMoves=playerMagentaRealm.getAllPossibleMoves();

        return mergeMoves(redRealmMoves,greenRealmMoves,blueRealmMoves,magentaRealmMoves, yellowRealmMoves,new Move[0] );
    }

    public  Move[] getPossibleMovesForAvailableDice(Player player){
        ArcanePrism arcanePrism=gameBoard.getArcanePrism();
        BlueDice blueDice=gameBoard.getBlueDice();
        RedDice redDice=gameBoard.getRedDice();
        GreenDice greenDice=gameBoard.getGreenDice();
        YellowDice yellowDice=gameBoard.getYellowDice();
        MagentaDice magentaDice=gameBoard.getMagentaDice();

        
        Move[] redMoves=getPossibleMovesForADie(player, redDice);
        if(redDice.getDiceStatus()!=DiceStatus.AVAILABLE)
            redMoves=new Move[0];
        Move[] greenMoves=getPossibleMovesForADie(player, greenDice);
        if(greenDice.getDiceStatus()!=DiceStatus.AVAILABLE||arcanePrism.getDiceStatus()==DiceStatus.AVAILABLE)
            greenMoves=new Move[0];
        Move[] blueMoves=getPossibleMovesForADie(player, blueDice);
        if(blueDice.getDiceStatus()!=DiceStatus.AVAILABLE)
            blueMoves=new Move[0];    
        Move[] magentaMoves=getPossibleMovesForADie(player, magentaDice);
        if(magentaDice.getDiceStatus()!=DiceStatus.AVAILABLE)
            magentaMoves=new Move[0];
        Move[] yellowMoves=getPossibleMovesForADie(player, yellowDice);
        if(yellowDice.getDiceStatus()!=DiceStatus.AVAILABLE)
            yellowMoves=new Move[0];
        Move[] arcaneMoves=getPossibleMovesForADie(player, arcanePrism);
        if(arcanePrism.getDiceStatus()!=DiceStatus.AVAILABLE)
            arcaneMoves=new Move[0];

        return mergeMoves(redMoves, greenMoves,blueMoves,magentaMoves,yellowMoves,arcaneMoves);
    }

    public  Move[] getPossibleMovesForADie(Player player, Dice dice){
     
        RealmColor diceColor=dice.getDiceColor();
        int diceValue=dice.getValue();

        Move[] blueRealmMoves=new Move[0];
        Move[] redRealmMoves=new Move[0];
        Move[] greenRealmMoves=new Move[0];
        Move[] yellowRealmMoves=new Move[0];
        Move[] magentaRealmMoves=new Move[0];
        
        if(diceColor==RealmColor.BLUE||diceColor==RealmColor.WHITE){
            BlueRealm playerBlueRealm=player.getBlueRealm();
            blueRealmMoves=playerBlueRealm.getPossibleMovesForADie(diceValue, diceColor);
        }
        if(diceColor==RealmColor.RED||diceColor==RealmColor.WHITE){
            RedRealm  playerRedRealm=player.getRedRealm();
            redRealmMoves=playerRedRealm.getPossibleMovesForADie(diceValue, diceColor);
        }
        if(diceColor==RealmColor.GREEN||diceColor==RealmColor.WHITE){
            GreenRealm playerGreenRealm=player.getGreenRealm();
            int sumDiceValue=diceValue;
            
            if(diceColor==RealmColor.WHITE){
                GreenDice greenDice=gameBoard.getGreenDice();
                sumDiceValue+=greenDice.getValue();
            }
            else{
                ArcanePrism arcanePrism=gameBoard.getArcanePrism();
                sumDiceValue+=arcanePrism.getValue();
            }

            greenRealmMoves=playerGreenRealm.getPossibleMovesForADie(sumDiceValue, diceColor);
        }
        if(diceColor==RealmColor.YELLOW||diceColor==RealmColor.WHITE){
            YellowRealm playerYellowRealm=player.getYellowRealm();
            yellowRealmMoves=playerYellowRealm.getPossibleMovesForADie(diceValue, diceColor);
        }
        if(diceColor==RealmColor.MAGENTA||diceColor==RealmColor.WHITE){
            MagentaRealm playerMagentaRealm=player.getMagentaRealm();
            magentaRealmMoves=playerMagentaRealm.getPossibleMovesForADie(diceValue, diceColor);
        }

        return mergeMoves( redRealmMoves, greenRealmMoves,blueRealmMoves,magentaRealmMoves,yellowRealmMoves,new Move[0] );
    }

    public Move[] mergeMoves(Move[] red,Move[] green,Move[] blue,Move[]magenta,Move[] yellow,Move[]white){
        int moveArraySize=0;
        moveArraySize+=red.length;
        moveArraySize+=green.length;
        moveArraySize+=blue.length;
        moveArraySize+=magenta.length;
        moveArraySize+=yellow.length;
        moveArraySize+=white.length;

        
        Move[]moves=new Move[moveArraySize];
        int index=0;
        //red
        for(int i=0;i<white.length;i++)
            if(white[i].getDice().getDiceColor()==RealmColor.RED)
                moves[index++]=white[i];
        for(Move x:red)
            moves[index++]=x;
        //Green
        for(int i=0;i<white.length;i++)
            if(white[i].getDice().getDiceColor()==RealmColor.GREEN)
                moves[index++]=white[i];
        for(Move x:green)
            moves[index++]=x;    
        //blue
        for(int i=0;i<white.length;i++)
            if(white[i].getDice().getDiceColor()==RealmColor.BLUE)
                moves[index++]=white[i];
        for(Move x:blue)
            moves[index++]=x;    
        //magenta
        for(int i=0;i<white.length;i++)
            if(white[i].getDice().getDiceColor()==RealmColor.MAGENTA)
                moves[index++]=white[i];
        for(Move x:magenta)
            moves[index++]=x;    
        //yellow
        for(int i=0;i<white.length;i++)
            if(white[i].getDice().getDiceColor()==RealmColor.YELLOW)
                moves[index++]=white[i];
        for(Move x:yellow)
            moves[index++]=x;    

        return moves;
    }

    public  GameBoard getGameBoard(){
        return gameBoard;
    }

    public  Player getActivePlayer(){
        return gameBoard.getActivePlayer();
    }

    public  Player getPassivePlayer(){
        return gameBoard.getPassivePlayer();
    }

    public  ScoreSheet getScoreSheet(Player player){
        return player.getScoreSheet();
    }


    public  GameStatus getGameStatus(){
        return getGameBoard().getGameStatus();
    }

    public  GameScore getGameScore(Player player){
        return player.getGameScore();
    }

    public  TimeWarp[] getTimeWarpPowers(Player player){
        ArrayList<TimeWarp> timeWarp=player.getTimeWarps();

        if(timeWarp==null||timeWarp.isEmpty())//May change implementation based on tests
            return new TimeWarp[0];

        TimeWarp[] timeWarpOutput=new TimeWarp[timeWarp.size()];
        for(int i=0;i<timeWarpOutput.length;i++)
            timeWarpOutput[i]=timeWarp.get(i);

        return timeWarpOutput;    
    }

    public  ArcaneBoost[] getArcaneBoostPowers(Player player){
        ArrayList<ArcaneBoost> arcaneBoosts=player.getArcaneBoosts();

        if(arcaneBoosts==null||arcaneBoosts.isEmpty())//May change implementation based on tests
            return new ArcaneBoost[0];

        ArcaneBoost[] arcaneBoostOutput=new ArcaneBoost[arcaneBoosts.size()];
        for(int i=0;i<arcaneBoostOutput.length;i++)
            arcaneBoostOutput[i]=arcaneBoosts.get(i);
            
        return arcaneBoostOutput;    
    }

    public  boolean selectDice(Dice dice, Player player){//TODO  dont make set forgtten until after dice found
       Dice[] availableDice=getAvailableDice();
       boolean foundDice=false;
       for(Dice x:availableDice){
        if(x==dice){
            foundDice=true;
            x.setDiceStatus(DiceStatus.TURN_SELECTED);
        }
        else if(x.getValue()<dice.getValue())    
            x.setDiceStatus(DiceStatus.FORGOTTEN_REALM);
       }
        return foundDice;    
    }
    public  boolean makeMove(Player player, Move move){ 
        Dice moveDice=move.getDice();
        Creature moveCreature=move.getMoveCreature();
        RealmColor color=moveDice.getDiceColor();
        if(color==RealmColor.WHITE)
            color=((ArcanePrism)moveDice).getChosenColor();
        switch(color){
            case BLUE:
            return player.getBlueRealm().attack(moveDice.getValue(), moveCreature);
            case RED:
            return player.getRedRealm().attack(moveDice.getValue(), moveCreature);
            case GREEN:
            int sumValue=0;
            sumValue+=gameBoard.getGreenDice().getValue();
            sumValue+=gameBoard.getArcanePrism().getValue();  
            return player.getGreenRealm().attack(sumValue, moveCreature);
            case YELLOW:
            return player.getYellowRealm().attack(moveDice.getValue(), moveCreature);
            case MAGENTA:
            return player.getMagentaRealm().attack(moveDice.getValue(), moveCreature);
            case WHITE:System.out.println("Error Attacking WHite");
        }
        return false;
    }
}
